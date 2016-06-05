package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;

import java.io.Serializable;

/**
 * Created by tgdflto1 on 29/05/16.
 */
public class FinishedState implements AppointmentState, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8963546666566875895L;
	@Override
    public void doAction(AppointmentEntity context) {
        AppointmentDao appoinmentDao = AppointmentDao.getInstance();
        context.setState(new ClosedState());
        appoinmentDao.update(context);
    }
    public String toString(){
        return "FINISHED";
    }

}
