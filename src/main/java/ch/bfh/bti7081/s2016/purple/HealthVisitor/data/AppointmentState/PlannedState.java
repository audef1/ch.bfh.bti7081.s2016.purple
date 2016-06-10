package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;

import java.io.Serializable;

/**
 * @author tgdflto1
 */
public class PlannedState implements AppointmentState, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -791162284396585686L;

	@Override
    public void doAction(AppointmentEntity context) {
        AppointmentDao appoinmentDao = AppointmentDao.getInstance();
        context.setState(new RunningState());
        appoinmentDao.update(context);
    }
    
    public String toString(){
    	return "PLANNED";
    }
}
