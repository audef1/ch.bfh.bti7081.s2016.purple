package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.CalendarComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentListController;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Layout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initializes the CalendarView with the params sent from the previous view (session)
 * @author tgdflto1 on 20/05/16.
 */

@SuppressWarnings("serial")
public class AppointmentListView extends BaseView {

	public static final String NAME = "AppointmentList";
	public static final String VIEW_NAME = "Terminliste";

	private static final Logger logger = LogManager.getLogger(AppointmentListView.class);
	private final AppointmentListController controller;

	public AppointmentListView() {
		super();
		logger.debug("arrived on appointment list view");
		controller = new AppointmentListController(this);
		layout = new StandardLayout(this);
	}

	/**
	 * Calls the CalendarComponent with the attribute "calendar_view" from the session
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Layout initView() {
		String type = (String) VaadinSession.getCurrent().getSession().getAttribute("calendar_view");
		return new CalendarComponent(controller.getAppointments(), type);
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {	}
}
