package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.util.List;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
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

		// TODO outsource into an xml/html file
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {

		general = new VerticalLayout();
		general.setSpacing(true);
		general.setMargin(false);

		Label listTitle = new Label("Termine für ");
		listTitle.setStyleName("h1");

		//TODO: get appointments only for current health visitor (use getAppointments() from HealthVisitorEntity?)
		List<AppointmentEntity> items = controller.getAppointments();
		final BeanItemContainer<AppointmentEntity> container =
				new BeanItemContainer<AppointmentEntity>(AppointmentEntity.class, items);
		
		Grid grid = new Grid(container);
		grid.setColumnOrder("address", "duration");
		
		// The Layout for the Logo
		GridLayout top = new GridLayout(2, 1);
		top.setSizeFull();
		top.setMargin(new MarginInfo(true, true, false, true));

		VerticalLayout title = new VerticalLayout();
		title.addComponent(listTitle);
		title.setMargin(new MarginInfo(false, true, true, true));
		title.setSpacing(true);
		title.addComponent(grid);

		// TODO Add patients and appointments from db to the list
		general.addComponents(top, title);
		grid.addSelectionListener((clickEvent -> getUI().getNavigator().navigateTo(AppointmentDetailView.NAME)));
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
