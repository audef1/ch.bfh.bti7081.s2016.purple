package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tgdflto1 on 22/05/16.
 */
public class HealthVisitorDao {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);

    public HealthVisitorDao() {
    }

    public HealthVisitorEntity findByEmail(String mail){

        EntityManager em = factory.createEntityManager();
//        em.getTransaction().begin();
//        em.createQuery("SELECT * FROM PERSON WHERE email = ").executeUpdate();
//        em.persist(new Person("Jeanne Calment", 122));
//        em.persist(new Person("Sarah Knauss", 119));
//        em.persist(new Person("Lucy Hannah", 117));
//        em.getTransaction().commit();
        TypedQuery<HealthVisitorEntity> query = em.
                createQuery("SELECT p FROM person p WHERE TYPE(p) = :klass AND p.email = :email",
                        HealthVisitorEntity.class);
        try{
            return query.setParameter("klass", HealthVisitorEntity.class).setParameter("email", mail).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public HealthVisitorEntity findById(String id){
        EntityManager em = factory.createEntityManager();
        TypedQuery<HealthVisitorEntity> query = em.
                createQuery("SELECT p FROM person p WHERE TYPE(p) = :klass AND p.id = :id",
                        HealthVisitorEntity.class);
        try{
            return query.setParameter("klass", HealthVisitorEntity.class).setParameter("id", id).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
}
