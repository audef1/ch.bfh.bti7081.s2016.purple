package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;

import java.io.Serializable;

/**
 * @author tgdflto1
 * interface for all appointmentstates to make them implement doAction
 */
public interface AppointmentState extends Serializable {
    void doAction(AppointmentEntity context);
}
