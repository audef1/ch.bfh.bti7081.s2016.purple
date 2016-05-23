package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.PersonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * Created by tgdflto1 on 23/05/16.
 */
public class InitializeBasicEntities {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);
    private static InitializeBasicEntities instance;

    private InitializeBasicEntities(){}

    public static InitializeBasicEntities getInstance(){
        if(instance == null){
            instance = new InitializeBasicEntities();
        }
        return instance;
    }


    public void initializeBasicUser(){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(new HealthVisitorEntity("Test", "User", new Date(), "test@user.ch", "secret_2016", 0, 0, new Date()));
        em.getTransaction().commit();
    }


}
