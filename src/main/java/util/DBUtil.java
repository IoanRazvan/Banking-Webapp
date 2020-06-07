package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankAppPU");

    public static EntityManagerFactory getEmfactory() {
        return emf;
    }
}
