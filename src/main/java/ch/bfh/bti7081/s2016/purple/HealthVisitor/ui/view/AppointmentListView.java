package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.util.List;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentEventProvider;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;

import com.vaadin.ui.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

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

		general = new VerticalLayout();
		general.setSpacing(true);
		general.setMargin(false);

		Label listTitle = new Label("Termine für ");
		listTitle.setStyleName("h1");

		HealthVisitorEntity user = controller.getUser();
		List<AppointmentEntity> items = user.getAppointments();
		
		//final BeanItemContainer<AppointmentEntity> container = new BeanItemContainer<AppointmentEntity>(AppointmentEntity.class, items);
		
		HorizontalLayout calnav = new HorizontalLayout();
		calnav.setSpacing(true);
		Button week = new Button("Woche");
		Button today = new Button("Heute");
		calnav.addComponents(week, today);
		
		Calendar cal = new Calendar(new AppointmentEventProvider(items));
		cal.setSizeFull();
		cal.setResponsive(true);
		cal.setFirstVisibleHourOfDay(cal.getFirstVisibleHourOfDay());
		general.addComponents(calnav, cal);
		
		// TODO Add patients and appointments from db to the list
		//addSelectionListener((clickEvent -> getUI().getNavigator().navigateTo(AppointmentDetailView.NAME)));
		return general;
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
