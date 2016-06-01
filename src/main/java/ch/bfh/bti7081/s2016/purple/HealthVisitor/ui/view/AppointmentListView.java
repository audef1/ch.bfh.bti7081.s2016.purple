package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;

import com.vaadin.ui.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.BasicEvent;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentListController;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class AppointmentListView extends BaseView {
	public static final String NAME = "AppointmentList";
	public static final String VIEW_NAME = "Terminliste";
	public static final int FIRST_HOUR = 8;
	public static final int LAST_HOUR = 19;

	private static final Logger logger = LogManager.getLogger(AppointmentListView.class);
	private final AppointmentListController controller;
	private VerticalLayout general;
	
	private BeanItemContainer<BasicEvent> container;

	public AppointmentListView() {
		super();
		logger.debug("arrived on appointment list view");
		controller = new AppointmentListController(this);

		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {

		general = new VerticalLayout();
		general.setSpacing(true);
		general.setMargin(true);

		Label listTitle = new Label("Termine für ");
		listTitle.setStyleName("h1");

		HealthVisitorEntity user = controller.getUser();
		List<AppointmentEntity> items = controller.getAppointments();
		logger.debug("getting appointments: " + items);
		container = new BeanItemContainer<>(BasicEvent.class);
		this.addAppointments(items);
				
		HorizontalLayout calnav = new HorizontalLayout();
		calnav.setSpacing(true);
		Button week = new Button("Woche");
		Button today = new Button("Heute");
		calnav.addComponents(week, today);
		
		Calendar cal = new Calendar();
		cal.setReadOnly(true);
		cal.setSizeFull();
		cal.setResponsive(true);
		cal.setFirstVisibleHourOfDay(FIRST_HOUR);
	    cal.setLastVisibleHourOfDay(LAST_HOUR);
	    cal.setContainerDataSource(container, "caption", "description", "start", "end", "styleName");
	
		general.addComponents(calnav, cal);
		
		// TODO Add patients and appointments from db to the list
		//addSelectionListener((clickEvent -> getUI().getNavigator().navigateTo(AppointmentDetailView.NAME)));
		return general;
	}
	
	public void addAppointments(List<AppointmentEntity> items) {
       
		if (items !=null && !items.isEmpty()){
			for (AppointmentEntity appointment : items) {
	        	
				String name = appointment.getClient().getFullName();
	        	String description = appointment.getAddress();
				Date start = appointment.getStartTime();
				Date end = appointment.getEndTime();
				
				logger.debug("added appointment to calendar: " + name + " - " + start + " - " + end);
	            container.addBean(new BasicEvent(name, description, start, end));
	        }
	        container.sort(new Object[]{"start"}, new boolean[] { true });
        }
		
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
