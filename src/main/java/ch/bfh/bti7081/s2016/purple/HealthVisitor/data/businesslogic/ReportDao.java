package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by tgdflto1 on 31/05/16.
 */
public class ReportDao implements Dao {
    private static Logger logger = LogManager.getLogger(HealthVisitorDao.class);
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);
    private final EntityManager entityManager;
    public ReportDao(){
        entityManager = factory.createEntityManager();
    }

    @Override
    public void update(Object entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void persist(Object entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

    }

    @Override
    public void remove(Object entity) {

    }

    @Override
    public Object findById(Object id) {
        return null;
    }
}
