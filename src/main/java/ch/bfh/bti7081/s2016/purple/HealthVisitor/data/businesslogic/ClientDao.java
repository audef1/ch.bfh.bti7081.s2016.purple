package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ClientEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by tgdflto1 on 29/05/16.
 */
public class ClientDao implements PersonDao{
    private static Logger logger = LogManager.getLogger(HealthVisitorDao.class);
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);
    private final EntityManager entityManager;

    public ClientDao(){
        entityManager = factory.createEntityManager();
    }
    @Override
    public Object findByEmail(String email) {
        return null;
    }

    @Override
    public void persist(Object entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
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
