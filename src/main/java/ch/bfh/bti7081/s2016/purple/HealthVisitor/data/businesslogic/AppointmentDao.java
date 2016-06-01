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
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        TypedQuery<AppointmentEntity> query = entityManager.
                createQuery("SELECT a FROM appointment a LEFT JOIN a.client c LEFT JOIN a.hv WHERE a.hv = :hv AND c.id IS NOT NULL LIMIT 10" ,
                        AppointmentEntity.class);
        try{
            query.setParameter("hv", new AuthenticationService().getUser());
        	List<AppointmentEntity> appointments = query.getResultList();
            return appointments;
        }catch(NoResultException e){
            logger.debug("no appointments found " + e.getMessage());
            return null;
        }
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
