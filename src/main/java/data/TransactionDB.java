package data;

import business.Account;
import business.Transaction;
import business.TransactionPK;
import business.User;
import util.DBUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TransactionDB {
    public static void insert(Transaction tr) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(tr);
            trans.commit();
        } catch (Exception ex) {
            System.out.println(ex);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void deleteDeclinedTransactions() {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        String qString = "DELETE FROM Transaction t WHERE t.status = 'declined'";

        Query q = em.createQuery(qString);
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



    public static List<Account> getAccounts() {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT tr.sourceAccount FROM Transaction tr WHERE tr.status='declined'";
        TypedQuery<Account> query = em.createQuery(qString, Account.class);

        List<Account> accountsThatNeedRefund;
        try {
            accountsThatNeedRefund = query.getResultList();
            if (accountsThatNeedRefund == null || accountsThatNeedRefund.size() == 0)
                return null;
        } finally {
            em.close();
        }

        return  accountsThatNeedRefund;
    }

    public static List<Transaction> getTransactionsByOwnershipAndCurrency(User u, String ownership, String currency) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "";
        if(ownership.equals("target")){
            qString = "SELECT tr FROM Transaction tr WHERE tr.targetAccount.owner = :user AND tr.status = 'accepted' AND tr.targetAccount.currency = :currency";
        } else if (ownership.equals("source")){
            qString = "SELECT tr FROM Transaction tr WHERE tr.sourceAccount.owner = :user AND tr.status = 'accepted' AND tr.sourceAccount.currency = :currency";
        }
        Query query = em.createQuery(qString);
        query.setParameter("user", u);
        query.setParameter("currency", currency);
        List<Transaction> result;
        try {
            result = query.getResultList();
            if(result == null || result.size() == 0)
                return null;
        } finally {
            em.close();
        }
        return result;
    }

    public static List<Transaction> getTransactionsByTargetAccountOwner(User user) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT t from Transaction t WHERE t.targetAccount.owner = :user";
        TypedQuery<Transaction> query = em.createQuery(qString, Transaction.class);
        query.setParameter("user", user);
        List<Transaction> transactions;
        try {
            transactions = query.getResultList();
            if(transactions == null || transactions.size() == 0)
                return null;
        } finally {
            em.close();
        }
        return transactions;
    }

    public static List<Transaction> getTransactionsBySourceAccountOwner(User user) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT t from Transaction t WHERE t.sourceAccount.owner = :user";
        TypedQuery<Transaction> query = em.createQuery(qString, Transaction.class);
        query.setParameter("user", user);
        List<Transaction> transactions;
        try {
            transactions = query.getResultList();
            if(transactions == null || transactions.size() == 0)
                return null;
        } finally {
            em.close();
        }
        return transactions;
    }

    public static List<Transaction> getWaitingTransactions(User u) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT t FROM Transaction t WHERE t.targetAccount.owner = :user AND t.status = 'waiting' ";
        TypedQuery<Transaction> query = em.createQuery(qString, Transaction.class);
        query.setParameter("user", u);
        List<Transaction> transactions;
        try {
            transactions = query.getResultList();
            if(transactions == null || transactions.size() == 0)
                return null;
        } finally {
            em.close();
        }
        return  transactions;
    }

    public static Transaction getByPrimaryKey(TransactionPK trPk) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();

        try {
           Transaction tr = em.find(Transaction.class, trPk);
            return tr;
        } finally {
            em.close();
        }
    }


    public static void update(Transaction tr) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(tr);
            trans.commit();
        } catch(Exception ex)  {
            System.out.println(ex);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unsafe")
    public static Float getRefundMoney(Account ac) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT sum(tr.amount) FROM Transaction tr WHERE tr.status = 'declined' AND tr.sourceAccount = :account";
        TypedQuery<Double> query = em.createQuery(qString, Double.class);

        query.setParameter("account", ac);
        Double amount;
        try {
            amount = query.getSingleResult();
            if (amount == null)
                return 0.0f;
        } finally {
            em.close();
        }

        return (float)amount.doubleValue();
    }
}