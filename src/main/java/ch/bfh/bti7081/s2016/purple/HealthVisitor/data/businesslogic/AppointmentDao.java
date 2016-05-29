package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tgdflto1 on 22/05/16.
 */
public class AppointmentDao implements Dao{
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);
    private static Logger logger = LogManager.getLogger(AppointmentDao.class);
    public AppointmentDao() { }

    public List<AppointmentEntity> getAppointments(){
        EntityManager em = factory.createEntityManager();
        TypedQuery<AppointmentEntity> query = em.createQuery("SELECT a FROM appointment AS a WHERE a.hv = :hv" , AppointmentEntity.class);
        try{
        	return query.setParameter("hv", new AuthenticationService().getUser()).getResultList();
        }catch(NoResultException e){
            logger.debug("no appointments found " + e.getMessage());
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

    public void update(AppointmentEntity appointmentEntity) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(appointmentEntity);
        em.getTransaction().commit();
        em.close();
    }

    public AppointmentEntity getCurrentAppointment() {
        EntityManager em = factory.createEntityManager();
        TypedQuery<AppointmentEntity> query = em.
                createQuery("SELECT a FROM appointment AS a WHERE a.hv = :hv AND a.startTime" +
                        " BETWEEN :today AND :tomorrow ORDER BY a.startTime, a.endTime ASC",
                        AppointmentEntity.class);
        try{
            long oneDay = 24*60*60;
            long timeNow =  System.currentTimeMillis()/1000-oneDay;
            long timeTomorrow =  timeNow+oneDay;
            logger.debug("timeNow = "+timeNow);
            logger.debug("timeTomorrow = "+timeTomorrow);

            AppointmentEntity appointment=  query.setParameter("hv", new AuthenticationService().getUser()).
                    setParameter("today", timeNow).setParameter("tomorrow", timeTomorrow).getSingleResult();
            appointment.getClient(); 
            return appointment;
        }catch(NoResultException e){
            logger.debug("no current appointment found " + e.getMessage());
            return null;
        }


    }

}
