package util;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;

public class EntityManagerFactorySingleton {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankAppPU", Collections.singletonMap(PersistenceUnitProperties.VALIDATION_MODE, "callback"));

    private EntityManagerFactorySingleton() {}

    public static EntityManagerFactory getEntityManagerFactoryInstance() {
        return emf;
    }
}
