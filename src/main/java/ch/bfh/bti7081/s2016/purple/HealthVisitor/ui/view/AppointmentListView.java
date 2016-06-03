package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.util.GregorianCalendar;
import java.util.List;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.events.AppointmentEvent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.CalendarComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;

import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
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

	private static final Logger logger = LogManager.getLogger(AppointmentListView.class);
	private final AppointmentListController controller;
	private VerticalLayout general;

	public AppointmentListView() {
		super();
		logger.debug("arrived on appointment list view");
		controller = new AppointmentListController(this);
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		return new CalendarComponent(controller.getAppointments(), "today");
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		
	}
}
