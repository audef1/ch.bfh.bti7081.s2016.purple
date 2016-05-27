package ch.bfh.bti7081.s2016.purple.HealthVisitor;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.TestEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.DbImport;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.InitializeBasicEntities;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentDetailView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentListView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.DashboardView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.LoginView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.MedicationView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.PatientListView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.annotation.WebServlet;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add CustomComp to the user interface and
 * initialize non-CustomComp functionality.
 */
@Theme("mytheme")
@Widgetset("ch.bfh.bti7081.s2016.purple.HealthVisitor.MyAppWidgetset")
public class HealthVisitorUI extends UI {
	public static final String PERSISTENCE_UNIT_NAME = "EclipseLink_JPA";

	Navigator navigator;
	static final Logger logger = LogManager.getLogger(HealthVisitorUI.class);

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		new Navigator(this, this);

		// The view
		getNavigator().addView(LoginView.NAME, LoginView.class);
		getNavigator().addView(DashboardView.NAME, DashboardView.class);
		getNavigator().addView(AppointmentListView.NAME,
				AppointmentListView.class);
		getNavigator().addView(AppointmentDetailView.NAME,
				AppointmentDetailView.class);
		getNavigator().addView(PatientListView.NAME, PatientListView.class);
		getNavigator().addView(MedicationView.NAME, MedicationView.class);
		// register al views in the navigator --> acts like a dispatcher

		// TODO: maybe useful for unit testing. remove otherwise
		// InitializeBasicEntities initiUtilities =
		// InitializeBasicEntities.getInstance();
		// initiUtilities.initializeBasicUser();

		// initial import from csv files
		logger.debug("start importing test data");
		DbImport.importFromFile("import/person.csv", "PERSON", true);
		DbImport.importFromFile("import/appointment.csv", "APPOINTMENT", true);

		if (new AuthenticationService().isAuthenticated()) {
			getNavigator().navigateTo(DashboardView.NAME);
		} else {
			getNavigator().navigateTo(LoginView.NAME);
		}

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = HealthVisitorUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
