package ch.bfh.bti7081.s2016.purple.HealthVisitor.events;

import com.vaadin.ui.components.calendar.event.BasicEvent;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;

public class AppointmentEvent extends BasicEvent {

	protected AppointmentEntity appointment;
	
	public AppointmentEvent(AppointmentEntity appointment){
		super(appointment.getClient().getFullName(), appointment.getAddress(), appointment.getStartTime(), appointment.getEndTime());
		this.appointment = appointment;
	}

	public AppointmentEntity getAppointment() {
		return appointment;
	}
}