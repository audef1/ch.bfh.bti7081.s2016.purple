package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.AppoinmentState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.RunningState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.net.www.content.text.Generic;

import javax.persistence.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by tgdflto1 on 22/05/16.
 */
public class AppointmentDao extends GenericDao<AppointmentEntity, Integer>{
    private static Logger logger = LogManager.getLogger(AppointmentDao.class);
    private static final int MAX_RESULTS = 100;
    
    private static AppointmentDao instance;
    
    public static AppointmentDao getInstance(){
    	if(instance == null) instance = new AppointmentDao();
    	return instance;
    }
    private AppointmentDao(){ }

    public List<AppointmentEntity> getAppointments(){
//        TypedQuery<AppointmentEntity> query = entityManager.
//                createQuery("SELECT a FROM appointment a LEFT JOIN a.client c LEFT JOIN a.hv h WHERE a.hv = :hv AND c.id IS NOT NULL" ,
//                        AppointmentEntity.class);
        //TODO re-implement with a typed query
        Query query = entityManager.
                createNativeQuery("SELECT * FROM APPOINTMENT LEFT JOIN PERSON AS CLIENT ON APPOINTMENT.CLIENT_ID = CLIENT.ID LEFT JOIN PERSON AS HV ON APPOINTMENT.HV_ID = HV.ID WHERE CLIENT.TYPE = 'K' AND HV.TYPE = 'H' AND APPOINTMENT.HV_ID ="+new AuthenticationService().getUser().getId(), AppointmentEntity.class);
        try{
           // query.setParameter("hv", new AuthenticationService().getUser());
        	List<AppointmentEntity> appointments = query.getResultList();
            return appointments;
        }catch(NoResultException e){
            logger.debug("no appointments found " + e.getMessage());
            return null;
        }
    }

    /**
     * TODO ZoneOffset
     * @param healthVisitor
     * @return
     */
    public Collection<AppointmentEntity> getTodaysAppointmentsByHealthVisitor(HealthVisitorEntity healthVisitor)
    {
    	TypedQuery<AppointmentEntity> query = entityManager.createQuery(
    		"SELECT a.* "
    		+ "FROM appointment AS a "
    		+ "WHERE a.hv = :hv "
    		+ "AND a.startTime BETWEEN :startTime AND :endTime",
            AppointmentEntity.class
        );
    	
    	LocalDate now = LocalDate.now();
    	ZoneOffset offset = null; 
    	long startTime = now.atStartOfDay().toEpochSecond(offset);
    	long endTime = startTime + (24 * 60 * 60);
    
    	Collection<AppointmentEntity> appointments =  query.
                setParameter("hv", healthVisitor).
                setParameter("startTime", startTime).
                setParameter("endTime", endTime)
                .getResultList();
    	
    	return appointments;
    }

    public AppointmentEntity getCurrentAppointment() {
        TypedQuery<AppointmentEntity> query = entityManager.
                createQuery("SELECT a FROM appointment AS a WHERE a.hv = :hv AND a.startTime" +
                        " BETWEEN :today AND :tomorrow AND a.client IS NOT NULL ORDER BY a.startTime, a.endTime ASC",
                        AppointmentEntity.class);
        try{
            long oneDay = 24*60*60;
            long currentTime = System.currentTimeMillis()/1000;
            long timeYesterday =  currentTime-oneDay;
            long timeTomorrow =  currentTime+oneDay;
            logger.debug("timeNow = "+timeYesterday);
            logger.debug("timeTomorrow = "+timeTomorrow);

            List<AppointmentEntity> appointments =  query.
                    setParameter("hv", new AuthenticationService().getUser()).
                    setParameter("today", timeYesterday).
                    setParameter("tomorrow", timeTomorrow).getResultList();
            AppointmentEntity appointmentToReturn = null;

            for(AppointmentEntity appointment : appointments){
                AppoinmentState state = appointment.getState();
                if((state == null) || (state instanceof  PlannedState) || (state instanceof RunningState)){
                    appointmentToReturn = appointment;
                    break;
                }
            }
            return appointmentToReturn;
        }catch(NoResultException e){
            logger.debug("no current appointment found " + e.getMessage());
            return null;
        }


    }

}
