package data;

import business.Account;
import business.User;
import util.DBUtil;

import javax.persistence.*;
import java.util.List;

public class UserDB {
    public static User getUserById(int userId) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        try {
            User u = em.find(User.class, userId);
            return u;
        } finally {
            em.close();
        }
    }

    public static User getUserByUsername(String username) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT u from User u WHERE u.username = :username";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("username", username);

        User user = null;
        try {
            user = q.getSingleResult();
        } catch (NoResultException ex) {
            user = null;
        } finally {
            em.close();
        }
        return user;
    }

    public static User getUserByPhoneNumber(String phoneNumber) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        String qString = "SELECT u from User u WHERE u.phoneNumber = :phoneNumber";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("phoneNumber", phoneNumber);

        User user = null;
        try {
            user = q.getSingleResult();
        } catch (NoResultException ex) {
            user = null;
        } finally {
            em.close();
        }

        return user;
    }

    public static void insert(User user) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception ex) {
            System.out.println(ex);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void update(User user) {
        EntityManager em = DBUtil.getEmfactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            System.out.println(ex);
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
