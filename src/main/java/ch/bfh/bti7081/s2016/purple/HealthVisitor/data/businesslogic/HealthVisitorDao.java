package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tgdflto1 on 22/05/16.
 */
public class HealthVisitorDao extends GenericDao<HealthVisitorEntity, Integer>{
    private static Logger logger = LogManager.getLogger(HealthVisitorDao.class);

    public HealthVisitorEntity findByEmail(String mail){
        TypedQuery<HealthVisitorEntity> query = entityManager.
                createQuery("SELECT p FROM person p  WHERE TYPE(p) = :klass AND p.email = :email",
                        HealthVisitorEntity.class);
        try{
            HealthVisitorEntity hv = query.setParameter("klass", HealthVisitorEntity.class).setParameter("email", mail).getSingleResult();
            return hv;
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
