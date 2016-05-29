package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;

import java.io.Serializable;

/**
 * Created by tgdflto1 on 29/05/16.
 */
public class CancelledState implements AppoinmentState, Serializable{
    @Override
    public void doAction(AppointmentEntity context) {

    }
}
