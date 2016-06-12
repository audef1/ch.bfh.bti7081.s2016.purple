package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.events.AppointmentEvent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentDetailView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentListView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("serial")
public class CalendarComponent extends VerticalLayout {

	private BeanItemContainer<BasicEvent> container;
	private static final Logger logger = LogManager.getLogger(AppointmentListView.class);
	private static final String WEEK = "Woche";
	private static final String TODAY = "Heute";
	private static final String MONTH = "Monat";
	private static final int FIRST_HOUR = 8;
	private static final int LAST_HOUR = 19;

	public CalendarComponent(List<AppointmentEntity> items, String type) {
		
		this.setSpacing(true);
		this.setMargin(true);

		logger.debug("getting appointments: " + items);
		container = new BeanItemContainer<>(BasicEvent.class);
		this.addAppointments(items);
				
		HorizontalLayout calnav = new HorizontalLayout();
		calnav.setSpacing(true);
		
		Calendar cal = new Calendar();
		cal.setReadOnly(true);
		cal.setSizeFull();
		cal.setResponsive(true);
		cal.setFirstVisibleHourOfDay(FIRST_HOUR);
	    cal.setLastVisibleHourOfDay(LAST_HOUR);
	    cal.setContainerDataSource(container, "caption", "description", "start", "end", "styleName");

		cal.setHandler((EventClickHandler) event -> {
			AppointmentEvent ae = (AppointmentEvent) event.getCalendarEvent();
			VaadinSession.getCurrent().getSession().setAttribute("appointment", ae.getAppointment());
			BaseView currentView = (BaseView) getUI().getNavigator().getCurrentView();
			VaadinSession.getCurrent().getSession().setAttribute("oldview", currentView.getName());
			getUI().getNavigator().navigateTo(AppointmentDetailView.NAME);
		});

		GregorianCalendar g = new GregorianCalendar();
		logger.debug("selected calendar type: " + type);
		if (type.equals("today")) {
			CalendarComponentEvents.DateClickHandler dch = (BasicDateClickHandler) cal.getHandler(DateClickEvent.EVENT_ID);
			dch.dateClick(new DateClickEvent(cal, g.getTime()));
			dch.dateClick(new DateClickEvent(cal, g.getTime()));
		} else {
			WeekClickHandler handler = (WeekClickHandler) cal.getHandler(WeekClick.EVENT_ID);
			handler.weekClick(new WeekClick(cal, g.get(GregorianCalendar.WEEK_OF_YEAR),
					g.get(GregorianCalendar.YEAR)));
		}


		Button week = new Button(WEEK);
		Button today = new Button(TODAY);
		Button month = new Button(MONTH);

		week.addClickListener((ClickListener) event -> {
            GregorianCalendar calendar = new GregorianCalendar();
            WeekClickHandler handler = (WeekClickHandler) cal.getHandler(WeekClick.EVENT_ID);
			handler.weekClick(new WeekClick(cal, calendar.get(GregorianCalendar.WEEK_OF_YEAR),
					calendar.get(GregorianCalendar.YEAR)));
		});

		today.addClickListener((ClickListener) event -> {
            GregorianCalendar calendar = new GregorianCalendar();
            BasicDateClickHandler handler = (BasicDateClickHandler) cal.getHandler(DateClickEvent.EVENT_ID);
            handler.dateClick(new DateClickEvent(cal,calendar.getTime()));
        });
		
		month.addClickListener((ClickListener) event -> {
			// set month view
			GregorianCalendar calendar = new GregorianCalendar();
		    calendar.setTime(new Date());
	        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
	        Date startDate = calendar.getTime();
	        
	        GregorianCalendar calendar2 = new GregorianCalendar();
	        calendar2.set(GregorianCalendar.DAY_OF_MONTH, calendar2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
	        Date endDate = calendar2.getTime();
	        
			cal.setStartDate(startDate);
			cal.setEndDate(endDate);
		});
		
		calnav.addComponents(month, week, today);
		this.addComponents(calnav, cal);
	}


	private void addAppointments(List<AppointmentEntity> items) {
		if (items == null || items.isEmpty()) return;
		for (AppointmentEntity appointment : items) {
			container.addBean(new AppointmentEvent(appointment));
		}
		container.sort(new Object[]{"start"}, new boolean[] { true });
	}
	
}
