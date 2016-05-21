package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.DashboardController;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class DashboardView extends CustomComponent implements View {
	public static final String NAME = "Dashboard";
	private static final Logger logger = LogManager.getLogger(DashboardView.class);
	private final DashboardController controller;

	private final VerticalLayout general;

	public DashboardView() {
		logger.debug("arrived on dashboard view");
		controller = new DashboardController(this);

		// TODO outsource into an xml/html file

		general = new VerticalLayout();
		general.setMargin(new MarginInfo(false, false, true, true));
		general.setSpacing(true);

		ThemeResource resource = new ThemeResource("images/Logo_HealthVisitor.png");
		Image logo = new Image("Logo", resource);
		logo.setWidth("300px");
		logo.setCaption("");

		Label lblHeader = new Label("Dashboard");
		lblHeader.setStyleName("header");

		GridLayout grid = new GridLayout(3, 2);
		grid.setSpacing(true);
		grid.addStyleName("ourcustomlayout");

		Button btMedcation = new Button("Medikamente heute");
		btMedcation.addClickListener(clickevent -> getUI().getNavigator().navigateTo(MedicationView.NAME));
		grid.addComponent(btMedcation);

		Button btAppointmentToday = new Button("Termine heute");
		btAppointmentToday.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentListView.NAME));
		grid.addComponent(btAppointmentToday);

		Button btAppointmentNow = new Button("Aktueller Termin");
		btAppointmentNow.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentDetailView.NAME));
		grid.addComponent(btAppointmentNow);

		Button btPatients = new Button("Meine Patienten");
		btPatients.addClickListener(clickevent -> getUI().getNavigator().navigateTo(PatientListView.NAME));
		grid.addComponent(btPatients);

		// TODO: Alarmpage
		/*
		 * Button btEmergency = new Button("Alarm");
		 * btEmergency.addClickListener(clickevent ->
		 * getUI().getNavigator().navigateTo(EmergencyView.NAME));
		 * grid.addComponent(btEmergency);
		 */

		Button btAppointmentList = new Button("Terminliste");
		btAppointmentList.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentListView.NAME));
		grid.addComponent(btAppointmentList);

		// The Layout for the Logo
		GridLayout top = new GridLayout(2, 1);
		top.setSizeFull();
		top.addComponent(lblHeader, 0, 0);
		top.addComponent(logo, 1, 0);
		top.setComponentAlignment(logo, Alignment.TOP_RIGHT);
		top.setMargin(new MarginInfo(true, true, false, true));

		// Set the root layout
		general.addComponent(top);
		general.addComponent(grid);
		setCompositionRoot(general);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO set a focus
	}
}
