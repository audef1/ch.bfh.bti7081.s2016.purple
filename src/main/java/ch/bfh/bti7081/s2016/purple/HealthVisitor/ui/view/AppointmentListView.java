package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.events.AppointmentEvent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentListController;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class AppointmentListView extends BaseView {
	public static final String NAME = "AppointmentList";
	public static final String VIEW_NAME = "Terminliste";
	public static final String WEEK = "Woche";
	public static final String TODAY = "Heute";
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

		List<AppointmentEntity> items = controller.getAppointments();
		logger.debug("getting appointments: " + items);
		container = new BeanItemContainer<>(BasicEvent.class);
		this.addAppointments(items);
				
		HorizontalLayout calnav = new HorizontalLayout();
		calnav.setSpacing(true);
		Calendar cal = new Calendar();
		
		Button week = new Button(WEEK);
		Button today = new Button(TODAY);

		week.addClickListener((ClickListener) event -> {
            GregorianCalendar calendar = new GregorianCalendar();
            WeekClickHandler handler = (WeekClickHandler) cal.getHandler(WeekClick.EVENT_ID);
            handler.weekClick(new WeekClick(cal, calendar.get(GregorianCalendar.WEEK_OF_YEAR),
					calendar.get(GregorianCalendar.YEAR)));
        });

		today.addClickListener((ClickListener) event -> {
            GregorianCalendar calendar = new GregorianCalendar();
            BasicDateClickHandler handler = (BasicDateClickHandler) cal.
                    getHandler(DateClickEvent.EVENT_ID);
            handler.dateClick(new DateClickEvent(cal,calendar.getTime()));
        });
		
		calnav.addComponents(week, today);
		cal.setReadOnly(true);
		cal.setSizeFull();
		cal.setResponsive(true);
		cal.setFirstVisibleHourOfDay(FIRST_HOUR);
	    cal.setLastVisibleHourOfDay(LAST_HOUR);
	    cal.setContainerDataSource(container, "caption", "description", "start", "end", "styleName");
	
	    cal.setHandler((EventClickHandler) event -> logger.
				debug("event clicked: " + event.getCalendarEvent()));

		general.addComponents(calnav, cal);
		// TODO Add patients and appointments from db to the list
		//addSelectionListener((clickEvent -> getUI().getNavigator().navigateTo(AppointmentDetailView.NAME)));
		return general;
	}
	
	public void addAppointments(List<AppointmentEntity> items) {
		if (items == null || items.isEmpty()) return;
		for (AppointmentEntity appointment : items) {
			container.addBean(new AppointmentEvent(appointment));
		}
		container.sort(new Object[]{"start"}, new boolean[] { true });
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}
}
