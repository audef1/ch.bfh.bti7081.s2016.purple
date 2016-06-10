package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;

/**
 * Created by tgdflto1 on 22/05/16.
 */
public class HealthVisitorDao extends GenericDao<HealthVisitorEntity, Integer>{

    public HealthVisitorEntity findByEmail(String mail){
        TypedQuery<HealthVisitorEntity> query = entityManager.
                createQuery("SELECT p FROM person p  WHERE TYPE(p) = :klass AND p.email = :email",
                        HealthVisitorEntity.class);
        try{
            return query.setParameter("klass", HealthVisitorEntity.class).setParameter("email", mail).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public HealthVisitorEntity findById(String id){
        HealthVisitorEntity hv = entityManager.find(HealthVisitorEntity.class, id);
        entityManager.close();
        return hv;
    }
}
