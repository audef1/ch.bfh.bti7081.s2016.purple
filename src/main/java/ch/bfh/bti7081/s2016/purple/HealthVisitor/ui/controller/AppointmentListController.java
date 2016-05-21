package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentListView;

public class AppointmentListController extends BaseController {
	private final  AppointmentListView appointmentListView;
	
    public AppointmentListController(AppointmentListView alv){
        this.appointmentListView = alv;
    }
}
