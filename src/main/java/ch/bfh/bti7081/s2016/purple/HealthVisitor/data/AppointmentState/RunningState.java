package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;

import java.io.Serializable;

/**
 * @author tgdflto1
 */
public class RunningState implements AppointmentState, Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2496109430502338860L;
	@Override
    public void doAction(AppointmentEntity context) {
        AppointmentDao appoinmentDao = AppointmentDao.getInstance();
        context.setState(new FinishedState());
        appoinmentDao.update(context);
    }
    public String toString(){
    	return "RUNNING";
    }
}
