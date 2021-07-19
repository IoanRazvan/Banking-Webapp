package repository;

import business.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static util.EntityManagerFactorySingleton.getEntityManagerFactoryInstance;

public class UserRepositoryImpl implements UserRepository {
    private EntityManagerFactory emf = getEntityManagerFactoryInstance();
    @Override
    public void save(User u) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(u);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return exists("select 1 from User u where u.username = :username",
                Collections.singletonMap("username", username));
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return exists("select 1 from User u where u.phoneNumber = :phoneNumber",
                Collections.singletonMap("username", phoneNumber));
    }

    @Override
    public boolean existsByPhoneNumberAndNotId(String phoneNumber, Integer id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("phoneNumber", phoneNumber);
        return exists("select 1 from User u where u.phoneNumber = :phoneNumber and u.id <> :id", parameters);
    }

    @Override
    public boolean existsByUsernameAndNotId(String username, Integer id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("username", username);
        return exists("select 1 from User u where u.username = :username and u.id <> :id", parameters);
    }

    private boolean exists(String queryString, Map<String, Object> parameters) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery(queryString);
        for (Map.Entry<String, Object> param : parameters.entrySet())
            query.setParameter(param.getKey(), param.getValue());
        boolean result = query.getResultList().size() == 1;
        em.close();
        return result;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        try {
            User u = query.getSingleResult();
            return Optional.of(u);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
}
