package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tgdflto1 on 22/05/16.
 */
public class AppointmentDao implements Dao{
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);

    public AppointmentDao() { }

    public List<AppointmentEntity> getAppointments(){
        EntityManager em = factory.createEntityManager();
        TypedQuery<AppointmentEntity> query = em.createQuery("SELECT * FROM appointment WHERE a.hv_id = :hvid", AppointmentEntity.class);
        
        int hvid = new AuthenticationService().getUser().getId();
        
        try{
        	return query.setParameter("hvid", hvid).getResultList();
        }catch(NoResultException e){
        	return null;
        }
    }

    @Override
    public void persist(Object entity) {
    }

    @Override
    public void remove(Object entity) {
    }

    @Override
    public Object findById(Object id) {
        EntityManager em = factory.createEntityManager();
        AppointmentEntity appointment = em.find(AppointmentEntity.class, id);
        em.close();
        return appointment;
    }
}
