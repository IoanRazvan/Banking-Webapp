package data;

import business.Account;
import business.User;
import util.DBUtil;

import javax.persistence.*;
import java.util.List;

public class AccountDB {
    public static void insert(Account account) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(account);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void deleteAccountById(Integer accountId) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        String qString = "DELETE FROM Account a WHERE a.accountId = :accountId";
        Query q = em.createQuery(qString);
        q.setParameter("accountId", accountId);
        try {
            trans.begin();
            q.executeUpdate();
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static Account getAccountById(Integer accountId) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        try {
            Account account = em.find(Account.class, accountId);
            return account;
        } finally {
            em.close();
        }
    }

    public static void update(Account account) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(account);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static List<Account> getAccountsOfUser(User user) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT a FROM Account a WHERE a.owner = :user";
        TypedQuery<Account> q = em.createQuery(qString, Account.class);
        q.setParameter("user", user);

        List<Account> accounts;

        try {
            accounts = q.getResultList();
            if (accounts == null || accounts.isEmpty())
                accounts = null;
        } finally {
            em.close();
        }

        return accounts;
    }

    public static void updateMainAccount(User u, Integer newMainAccount) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        String qString1 = "UPDATE Account a SET a.mainAccount = FALSE WHERE a.owner = :user";
        String qString2 = "UPDATE Account a SET a.mainAccount = TRUE WHERE a.accountId = :accountId";

        Query q1 = em.createQuery(qString1);
        Query q2 = em.createQuery(qString2);

        q1.setParameter("user", u);
        q2.setParameter("accountId", newMainAccount);

        try {
            trans.begin();
            q1.executeUpdate();
            q2.executeUpdate();
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static List<Account> getAllNonMainAccounts(User u) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT a FROM Account a WHERE a.mainAccount = false AND a.owner = :user";

        TypedQuery<Account> q = em.createQuery(qString, Account.class);
        q.setParameter("user", u);

        List<Account> accounts;
        try {
            accounts = q.getResultList();
            if (accounts == null || accounts.size() == 0)
                return null;
        } finally {
            em.close();
        }

        return accounts;
    }

    public static Account getMainAccount(User u) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT a FROM Account a WHERE a.mainAccount = true AND a.owner = :user";
        TypedQuery<Account> query = em.createQuery(qString, Account.class);
        query.setParameter("user", u);
        Account acc = null;
        try {
            acc = query.getSingleResult();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            em.close();
        }

        return acc;
    }


    public static void refundMoney() {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        List<Account> accounts = TransactionDB.getAccounts();

            if (accounts != null) {
                for (Account a : accounts) {
                    Float amount = TransactionDB.getRefundMoney(a);
                    System.out.println(amount);
                    a.setSold(a.getSold() + amount);
                    AccountDB.update(a);
                }
            }
            em.close();
        }

    public static List<Account> getMainAccountEntries(User u) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT a FROM Account a WHERE a.mainAccount = true AND a.owner != :user";
        TypedQuery<Account> query = em.createQuery(qString, Account.class);
        query.setParameter("user", u);
        List<Account> mainAccounts;
        try {
            mainAccounts = query.getResultList();
            if (mainAccounts == null || mainAccounts.size() == 0)
                return null;
        } finally {
            em.close();
        }

        return mainAccounts;
    }
}
