package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.AppointmentState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.FinishedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.RunningState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;

/**
 * This class implements the Data Access Object Pattern concept from Java
 * It extends the GenericDAO which defines a few basic methods.
 *
 * @author tfluehmann
 **/
public class AppointmentDao extends GenericDao<AppointmentEntity, Integer>{
    private static Logger logger = LogManager.getLogger(AppointmentDao.class);
    private static AppointmentDao instance;
    
    public static AppointmentDao getInstance(){
    	if(instance == null) instance = new AppointmentDao();
    	return instance;
    }
    protected AppointmentDao(){ }

    @SuppressWarnings("rawtypes")
	public List getAppointments() {
//        TypedQuery<AppointmentEntity> query = entityManager.
//                createQuery("SELECT a FROM appointment a LEFT JOIN a.client c LEFT JOIN a.hv h WHERE a.hv = :hv AND c.id IS NOT NULL" ,
//                        AppointmentEntity.class);
        //TODO re-implement with a typed query
        Query query = entityManager.
                createNativeQuery("SELECT * FROM APPOINTMENT LEFT JOIN PERSON AS CLIENT ON APPOINTMENT.CLIENT_ID = CLIENT.ID LEFT JOIN PERSON AS HV ON APPOINTMENT.HV_ID = HV.ID WHERE CLIENT.TYPE = 'K' AND HV.TYPE = 'H' AND APPOINTMENT.HV_ID ="+new AuthenticationService().getUser().getId(), AppointmentEntity.class);
        try{
           // query.setParameter("hv", new AuthenticationService().getUser());
        	return query.getResultList();
        }catch(NoResultException e){
            logger.debug("no appointments found " + e.getMessage());
            return null;
        }
    }

    /**
     * @param healthVisitor
	 * gets the appointments between the current time and the next day
	 * Timezone is UTC
	 *
     * @return
     */
    public Collection<AppointmentEntity> getTodaysAppointmentsByHealthVisitor(HealthVisitorEntity healthVisitor)
    {
    	TypedQuery<AppointmentEntity> query = entityManager.createQuery(
    		"SELECT a "
    		+ "FROM appointment AS a "
    		+ "WHERE a.hv = :hv "
    		+ "AND a.startTime BETWEEN :startTime AND :endTime "
    		+ "AND a.client IS NOT NULL ORDER BY a.startTime, a.endTime ASC",
            AppointmentEntity.class
        );

    	LocalDate now = LocalDate.now();
    	ZoneOffset offset = ZoneOffset.UTC;
    	long startTime = now.atStartOfDay().toEpochSecond(offset);
    	long endTime = startTime + (24 * 60 * 60);

    	Collection<AppointmentEntity> appointments = null;
    	try {
    		appointments = query.
                setParameter("hv", healthVisitor).
                setParameter("startTime", startTime).
                setParameter("endTime", endTime)
                .getResultList();
    	} catch (NoResultException e){
    		logger.debug("No todays appointment found " + e.getMessage());
    	}
    	return appointments;
    }

	/**
	 * Gets the logged in user from the auth service and passes it to
	 *
	 * @return null or the first matching AppointmentEntity
	 * @link getCurrentAppointment(HealthVisitorEntity healthVisitor)
	 */
	public AppointmentEntity getCurrentAppointment() {
    	return getCurrentAppointment(new AuthenticationService().getUser());
	}

	/**
	 *
	 * @param healthVisitor
	 * @return first appointment is in status null, planned, running or finished
	 */
    public AppointmentEntity getCurrentAppointment(HealthVisitorEntity healthVisitor) {

    	Collection<AppointmentEntity> appointments = getTodaysAppointmentsByHealthVisitor(healthVisitor);
        if (appointments != null) {
            for (AppointmentEntity appointment : appointments) {
                AppointmentState state = appointment.getState();
                logger.debug("STATE grabbed" + state);
                if ((state == null) || (state instanceof PlannedState) ||
                        (state instanceof RunningState) || state instanceof FinishedState)
                    return appointment;
            }
        }

    	logger.debug("No current appointment found");
    	return null;
	}

	/**
	 * @param  patient clientEntity we need the appointments for.
	 * @code It looks for the appointments related to the given patient.
	 * the startTime is a long from the current time (seconds).
	 * @return List of appointments for the given patient, empty if no appointments
	 */
	public Collection<AppointmentEntity> getAppointmentsByPatient(ClientEntity patient) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a ");
		sb.append("FROM appointment AS a ");
		sb.append("WHERE a.client = :client ");
		sb.append("AND a.startTime > :startTime ");
		sb.append("AND a.client IS NOT NULL ORDER BY a.startTime");

    	TypedQuery<AppointmentEntity> query = entityManager.createQuery(
				sb.toString(),
				AppointmentEntity.class
        );
    	
    	long startTime = System.currentTimeMillis() / 1000L;
    	Collection<AppointmentEntity> appointments = null;
    	try {
    		appointments = query.
                setParameter("client", patient).
                setParameter("startTime", startTime ).
                getResultList();
    	} catch (NoResultException e){
    		logger.debug("No appointments found " + e.getMessage());
    	}
    	return appointments;
    }
}
