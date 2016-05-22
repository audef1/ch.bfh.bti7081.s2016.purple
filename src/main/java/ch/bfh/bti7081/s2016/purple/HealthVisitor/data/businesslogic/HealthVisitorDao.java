package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tgdflto1 on 22/05/16.
 */
public class HealthVisitorDao {
    private static final String PERSISTENCE_UNIT_NAME = "LOGIN";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public HealthVisitorEntity findByEmail(String mail){
        EntityManager em = factory.createEntityManager();
//        em.getTransaction().begin();
//        em.createQuery("SELECT * FROM PERSON WHERE email = ").executeUpdate();
//        em.persist(new Person("Jeanne Calment", 122));
//        em.persist(new Person("Sarah Knauss", 119));
//        em.persist(new Person("Lucy Hannah", 117));
//        em.getTransaction().commit();


        /**
         *
         *
         *   public Country getCountryByName(EntityManager em, String name) {
         TypedQuery<Country> query = em.createQuery(
         "SELECT c FROM Country c WHERE c.name = :name", Country.class);
         return query.setParameter("name", name).getSingleResult();
         }
         */
        TypedQuery<HealthVisitorEntity> query = em.createQuery("Select p.email, p.password from PERSON p WHERE TYPE = H AND email = :email", HealthVisitorEntity.class);
        return  (HealthVisitorEntity) query.setParameter("email", mail).getSingleResult();
    }
}
