package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        TypedQuery<HealthVisitorEntity> searchUser = em.
                createQuery("SELECT p from person p where Type(p) = :klass AND  p.email = :email", HealthVisitorEntity.class);
        List<HealthVisitorEntity> result = searchUser.setParameter("klass", HealthVisitorEntity.class).
                setParameter("email", "test@user.ch").getResultList();
        if(result.size() == 0){
            em.getTransaction().begin();
            HealthVisitorEntity hv = new HealthVisitorEntity("Test", "User", new Date(), "test@user.ch", "secret_2016", 0, 0, new Date()) ;
            ClientEntity ce = new ClientEntity("hans", "wurst", new Date(), "hans@wurst.ch", "secret_2016", hv);
            em.persist(ce);

            em.persist(hv);
            Set<ClientEntity> alce = new HashSet<>();
            alce.add(ce);
            hv.setClients(alce);
            em.getTransaction().commit();
        }

    }


}
