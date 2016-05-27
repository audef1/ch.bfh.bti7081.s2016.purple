package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.DashboardButtonComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.DashboardController;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class DashboardView extends BaseView{
	public static final String NAME = "Dashboard";
	public static final String VIEW_NAME = NAME;
	private static final Logger logger = LogManager.getLogger(DashboardView.class);
	private final DashboardController controller;
	private CssLayout layout;

	public DashboardView() {
		super(VIEW_NAME);
		logger.debug("arrived on dashboard view");
		controller = new DashboardController(this);

		// TODO outsource into an xml/html file
	}

	@Override
	protected Layout initView() {
		
		layout = new CssLayout();

		GridLayout grid = new GridLayout(3, 2);
		grid.setSpacing(true);
		grid.addStyleName("ourcustomlayout");

		DashboardButtonComponent btMedcation = new DashboardButtonComponent("Medikamente heute", FontAwesome.MEDKIT);
		btMedcation.addClickListener(clickevent -> getUI().getNavigator().navigateTo(MedicationView.NAME));
		grid.addComponent(btMedcation);

		DashboardButtonComponent btAppointmentToday = new DashboardButtonComponent("Termine heute", FontAwesome.CALENDAR_TIMES_O);
		btAppointmentToday.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentListView.NAME));
		grid.addComponent(btAppointmentToday);

		DashboardButtonComponent btAppointmentNow = new DashboardButtonComponent("Aktueller Termin", FontAwesome.CLOCK_O);
		btAppointmentNow.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentDetailView.NAME));
		grid.addComponent(btAppointmentNow);

		DashboardButtonComponent btPatients = new DashboardButtonComponent("Meine Patienten", FontAwesome.USERS);
		btPatients.addClickListener(clickevent -> getUI().getNavigator().navigateTo(PatientListView.NAME));
		grid.addComponent(btPatients);

		DashboardButtonComponent btAppointmentList = new DashboardButtonComponent("Terminliste", FontAwesome.CALENDAR);
		btAppointmentList.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentListView.NAME));
		grid.addComponent(btAppointmentList);
		
		// TODO: Alarmpage
				/*
				 * Button btEmergency = new Button("Alarm");
				 * btEmergency.addClickListener(clickevent ->
				 * getUI().getNavigator().navigateTo(EmergencyView.NAME));
				 * grid.addComponent(btEmergency);
				 */
	
		// Set the root layout
		layout.addComponent(grid);
		return layout;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO set a focus
	}
}
