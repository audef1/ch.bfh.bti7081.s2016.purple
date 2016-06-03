package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;

import java.io.Serializable;

/**
 * Created by tgdflto1 on 29/05/16.
 */
public class CancelledState implements AppointmentState, Serializable{
    @Override
    public void doAction(AppointmentEntity context) {

    }
}
