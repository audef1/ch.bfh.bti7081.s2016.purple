package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.DashboardButtonComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.DashboardController;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class DashboardView extends BaseView{
	public static final String NAME = "Dashboard";
	public static final String VIEW_NAME = NAME;
	private static final Logger logger = LogManager.getLogger(DashboardView.class);
	private Layout layout;

	public DashboardView() {
		super();
		logger.debug("arrived on dashboard view");
		controller = new DashboardController(this);
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		
		layout = new CssLayout();

		GridLayout grid = new GridLayout(3, 2);
		grid.setSpacing(true);
		grid.setMargin(true);
		grid.addStyleName("ourcustomlayout");

		DashboardButtonComponent btAppointmentNow = new DashboardButtonComponent("Aktueller Termin", FontAwesome.CLOCK_O);
		btAppointmentNow.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentDetailView.NAME));
		grid.addComponent(btAppointmentNow);
		
		DashboardButtonComponent btAppointmentToday = new DashboardButtonComponent("Termine heute", FontAwesome.CALENDAR_TIMES_O);
		btAppointmentToday.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentListView.NAME));
		grid.addComponent(btAppointmentToday);

		DashboardButtonComponent btAppointmentList = new DashboardButtonComponent("Terminliste", FontAwesome.CALENDAR);
		btAppointmentList.addClickListener(clickevent -> getUI().getNavigator().navigateTo(AppointmentListView.NAME));
		grid.addComponent(btAppointmentList);
		
		DashboardButtonComponent btEmergency = new DashboardButtonComponent("Alarm", FontAwesome.HEARTBEAT);
		btEmergency.addClickListener(clickevent -> getUI().getNavigator().navigateTo(EmergencyView.NAME));
		grid.addComponent(btEmergency);
		
		DashboardButtonComponent btPatients = new DashboardButtonComponent("Meine Patienten", FontAwesome.USERS);
		btPatients.addClickListener(clickevent -> getUI().getNavigator().navigateTo(PatientListView.NAME));
		grid.addComponent(btPatients);
		
		DashboardButtonComponent btMedcation = new DashboardButtonComponent("Medikamente heute", FontAwesome.MEDKIT);
		btMedcation.addClickListener(clickevent -> getUI().getNavigator().navigateTo(MedicationView.NAME));
		grid.addComponent(btMedcation);
		
		// Set the root layout
		layout.addComponent(grid);
		return layout;
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO set a focus
	}
}
