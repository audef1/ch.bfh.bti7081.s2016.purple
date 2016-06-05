package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;

import java.io.Serializable;

/**
 * Created by tgdflto1 on 29/05/16.
 */
public class ClosedState implements AppointmentState, Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7251139570890699244L;
	@Override
    public void doAction(AppointmentEntity context) {

    }
    public String toString(){
        return "CLOSED";
    }
}
