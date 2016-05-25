package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;

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
		super(VIEW_NAME);
		logger.debug("arrived on appointment list view");
		controller = new AppointmentListController(this);

		// TODO outsource into an xml/html file

	}

	@Override
	protected Layout initView() {

		general = new VerticalLayout();
		general.setSpacing(true);
		general.setMargin(false);

		ThemeResource resource = new ThemeResource("images/Logo_HealthVisitor.png");
		Image logo = new Image("Logo", resource);
		logo.setWidth("300px");
		logo.setCaption("");


		Button btBack = new Button("Zurück");
		btBack.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btBack.addClickListener((clickEvent -> getUI().getNavigator().navigateTo(DashboardView.NAME)));

		Label listTitle = new Label("Termine für ");
		listTitle.setStyleName("h1");


		final BeanItemContainer<AppointmentEntity> ds =
				new BeanItemContainer<AppointmentEntity>(AppointmentEntity.class, controller.getAppointments());
		Grid grid = new Grid("Employees", ds);
		grid.setColumns("address");


		// The Layout for the Logo
		GridLayout top = new GridLayout(2, 1);
		top.setSizeFull();
		HorizontalLayout hl = new HorizontalLayout(btBack);
		hl.setSpacing(true);
		top.addComponent(hl, 0, 0);
		top.addComponent(logo, 1, 0);
		top.setComponentAlignment(logo, Alignment.TOP_RIGHT);
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
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO set a focus
	}
}
