package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;

/**
 * Created by tgdflto1 on 29/05/16.
 */
public interface AppoinmentState {
    public void doAction(AppointmentEntity context);
}
