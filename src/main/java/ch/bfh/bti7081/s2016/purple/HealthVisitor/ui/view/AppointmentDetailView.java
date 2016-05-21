package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;

public class AppointmentDetailView extends CustomComponent implements View {
	public static final String NAME = "AppointmentDetail";
	private static final Logger logger = LogManager.getLogger(AppointmentDetailView.class);
	private final AppointmentDetailController controller;
	
	public AppointmentDetailView(){
		logger.debug("arrived on appointment detail view");
		controller = new AppointmentDetailController(this);
		
		
	}
	
	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO Auto-generated method stub
		
	}

}
