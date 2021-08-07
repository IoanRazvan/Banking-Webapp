package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static util.EntityManagerFactorySingleton.getEntityManagerFactoryInstance;

public class Repository<T> {
    protected EntityManagerFactory emf = getEntityManagerFactoryInstance();
    public void save(T entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(entity);
            trans.commit();
        } finally {
            em.close();
        }
    }

    public void update(T entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(entity);
            trans.commit();
        } finally {
            em.close();
        }
    }
}
