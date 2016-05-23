package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentListView;

import java.util.List;

public class AppointmentListController extends BaseController {
	private final  AppointmentListView appointmentListView;
	private final List<AppointmentEntity> appointments;
    public AppointmentListController(AppointmentListView alv){
        this.appointmentListView = alv;
        this.appointments = getUser().getAppointments();

    }

    public List<AppointmentEntity> getAppointments() {

        //breaking termine heute...
        return appointments;
    }
}
