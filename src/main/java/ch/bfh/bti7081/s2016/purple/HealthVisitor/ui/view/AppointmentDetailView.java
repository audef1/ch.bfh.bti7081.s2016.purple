package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.AppointmentState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.ClosedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.FinishedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.RunningState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ReportDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ReportEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.TaskEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.events.AppointmentEvent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.GoogleMapsComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.ReportComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.listener.OrganizeTasks;

public class AppointmentDetailView extends BaseView {
	private static final long serialVersionUID = -3442651787982820693L;
	public static final String NAME = "AppointmentDetail";
	public static final String VIEW_NAME = "Termindetails";

	// States
	public static final String CONFIRM_END = "Ende bestätigen";
	public static final String CLOSE = "Abschliessen";
	public static final String ARRIVED = "Ankunft bestätigen";

	// Report
	public static final String EDIT_REPORT = "Rapport bearbeiten";
	public static final String CREATE_REPORT = "Rapport erstellen";
	public static final String NO_REPORT = "Kein Rapport vorhanden";
	public static final String LAST_REPORT = "Aktueller Rapport";

	// Client
	public static final String CLIENTDETAILS = "Patientendetails";
	public static final String CLIENTDESCRIPTION = "Kurzbeschrieb";
	public static final String SAVE_CLIENTDETAILS = "Details speichern";

	// Other
	public static final String NO_APPOINTMENT = "Heute keinen Termin gefunden";
	public static final String TASKLIST = "Aufgabenliste";
	public static final String SAVE = "Speichern";
	public static final String ERMERGENCY_CONTACTS = "Notfallkontakte des Patienten";

	private static final Logger logger = LogManager.getLogger(AppointmentDetailView.class);
	private AppointmentDetailController controller;
	private VerticalLayout general;
	private ReportEntity currentReport;
	private AppointmentEntity appointment;

	public AppointmentDetailView() {
		super();
		logger.debug("arrived on appointment detail view");
		this.controller = new AppointmentDetailController(this);
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		String strReportButtonName;
		general = new VerticalLayout();
		general.setMargin(true);
		general.setSpacing(true);

		// get appointment from session (calendarview)
		if (VaadinSession.getCurrent().getSession().getAttribute("appointment") != null) {
			this.appointment = (AppointmentEntity) VaadinSession.getCurrent().getSession().getAttribute("appointment");
			VaadinSession.getCurrent().getSession().setAttribute("appointment", null);
		} else
			this.appointment = getController().getCurrentAppointment();

		if (this.appointment == null) {
			general.addComponent(new Label(NO_APPOINTMENT));
		} else {

//			if (appointment.getState() == null)
//				appointment.doAction(appointment);

			// two column layout with topnav
			HorizontalLayout topnav = new HorizontalLayout();
			topnav.setSizeFull();
			general.addComponent(topnav);

			GridLayout top = new GridLayout(2, 1);
			top.setSpacing(true);
			top.setWidth("100%");
			top.setColumnExpandRatio(0, 0.5f);
			top.setColumnExpandRatio(1, 0.5f);
			general.addComponent(top);

			GridLayout bottom = new GridLayout(2, 1);
			bottom.setSpacing(true);
			bottom.setWidth("100%");
			bottom.setColumnExpandRatio(0, 0.5f);
			bottom.setColumnExpandRatio(1, 0.5f);
			general.addComponent(bottom);

			// topnav
			Button buttonArrival = new Button(timeButtonName());
			buttonArrival.setWidth("200px");
			buttonArrival.setStyleName("v-button-friendly");

			topnav.addComponent(buttonArrival);
			topnav.setComponentAlignment(buttonArrival, Alignment.MIDDLE_RIGHT);

			// infopanel (top left)
			Panel infopanel = new Panel("Termininformationen");
			infopanel.setSizeFull();
			VerticalLayout infopanelContent = new VerticalLayout();
			infopanelContent.setSizeFull();
			infopanelContent.setMargin(true);

			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern("HH:mm");

			Table table = new Table();
			table.setSizeFull();
			table.addContainerProperty("key", Label.class, null);
			table.addContainerProperty("value", String.class, null);
			table.setColumnWidth("key", 80);
			table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
			table.addStyleName("v-table-borderless");
			table.addStyleName("borderless");
			table.addStyleName("no-stripes");
			table.addStyleName("v-table-no-stripes");
			table.addStyleName("no-vertical-lines");
			table.addStyleName("v-table-no-vertical-lines");

			table.addItem(new Object[] { new Label("<b>Datum:</b>", ContentMode.HTML), appointment.getDate() }, 1);
			table.addItem(new Object[] { new Label("<b>Zeit:</b>", ContentMode.HTML),
					dateFormat.format(appointment.getStartTime()) + " - "
							+ dateFormat.format(appointment.getEndTime()) },
					2);
			table.addItem(new Object[] { new Label("<b>Adresse:</b>", ContentMode.HTML),
					appointment.getAddress() + ", " + appointment.getPlace() }, 3);

			table.setPageLength(table.size());
			infopanelContent.addComponent(table);

			infopanel.setContent(infopanelContent);
			VerticalLayout topleft = new VerticalLayout();
			topleft.setSpacing(true);
			String place = appointment.getPlace().split(" ")[1];
			GoogleMap map = new GoogleMapsComponent(appointment.getAddress() + " " + place);

			topleft.addComponent(infopanel);
			topleft.addComponent(map);

			top.addComponent(topleft, 0, 0);

			// user information (top right)
			Panel patientpanel = new Panel("Patienteninformationen");
			infopanel.setSizeFull();
			VerticalLayout patientpanelContent = new VerticalLayout();
			patientpanelContent.setSpacing(true);
			patientpanelContent.setMargin(true);

			Button saveClientDetails = new Button(SAVE_CLIENTDETAILS);
			saveClientDetails.setEnabled(false);

			TextArea description = new TextArea();
			description.setSizeFull();
			description.setCaption(CLIENTDESCRIPTION);
			description.setValue(appointment.getClient().getDetails());
			
			Table ptable = new Table();
			ptable.setSizeFull();
			ptable.addContainerProperty("key", Label.class, null);
			ptable.addContainerProperty("value",  String.class, null);
			ptable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
			ptable.addStyleName("v-table-borderless");
			ptable.addStyleName("borderless");
			ptable.addStyleName("no-stripes");
			ptable.addStyleName("v-table-no-stripes");
			ptable.addStyleName("no-vertical-lines");
			ptable.addStyleName("v-table-no-vertical-lines");
			
			SimpleDateFormat dob = new SimpleDateFormat();
			dob.applyPattern("dd.MM.YYYY");
			
			ptable.addItem(new Object[]{new Label("<b>Name:</b>", ContentMode.HTML), appointment.getClient().getFullName()}, 1);
			ptable.addItem(new Object[]{new Label("<b>Geburtsdatum:</b>", ContentMode.HTML), dob.format(appointment.getClient().getDateOfBirth())}, 2);
			ptable.setColumnWidth("key", 130);
			
			ptable.setPageLength(ptable.size());
			patientpanelContent.addComponent(ptable);
			patientpanelContent.addComponent(description);
			patientpanelContent.addComponent(saveClientDetails);

			patientpanel.setContent(patientpanelContent);

			VerticalLayout topright = new VerticalLayout();
			topright.setSpacing(true);

			HorizontalLayout patientbuttons = new HorizontalLayout();
			patientbuttons.setSpacing(true);

			Button buttonDetail = new Button(CLIENTDETAILS);
			Button buttonEmergencyContact = new Button(ERMERGENCY_CONTACTS);
			patientbuttons.addComponent(buttonDetail);
			patientbuttons.addComponent(buttonEmergencyContact);

			topright.addComponent(patientpanel);
			topright.addComponent(patientbuttons);

			top.addComponent(topright, 1, 0);

			// checklist (bottom left)
			Panel checklistpanel = new Panel(TASKLIST);
			infopanel.setSizeFull();
			VerticalLayout checklistpanelContent = new VerticalLayout();
			checklistpanelContent.setSpacing(true);
			checklistpanelContent.setMargin(true);

			Collection<TaskEntity> tasks = appointment.getTasks();
			logger.debug("tasks " + tasks);
			BeanItemContainer<TaskEntity> container = new BeanItemContainer<TaskEntity>(TaskEntity.class, tasks);

			Grid checklist = new Grid(container);
			checklist.setColumnOrder("name");
			checklist.setColumns("name");
			checklist.setSizeFull();
			if (tasks.size() > 0) {
				checklist.setHeightMode(HeightMode.ROW);
				checklist.setHeightByRows(tasks.size());
			}

			// Handle selection of tasks
			checklist.setSelectionMode(com.vaadin.ui.Grid.SelectionMode.MULTI);
			checklist.addSelectionListener(new OrganizeTasks(getController(), checklist));

			checklist.addItemClickListener(event -> {
				showTaskDetail(container.getItem(event.getItemId()).getBean());
			});

			tasks.stream().filter(task -> task.isChecked()).forEach(checklist::select);

			checklistpanelContent.addComponent(checklist);
			checklistpanel.setContent(checklistpanelContent);

			VerticalLayout bottomleft = new VerticalLayout();

			bottomleft.addComponent(checklistpanel);
			bottom.addComponent(bottomleft, 0, 0);

			// reporting (bottom right)
			currentReport = appointment.getReport();

			Panel reportpanel = new Panel(LAST_REPORT);
			reportpanel.setSizeFull();
			VerticalLayout reportpanelContent = new VerticalLayout();
			reportpanelContent.setSpacing(true);
			reportpanelContent.setMargin(true);

			if (currentReport != null) {
				reportpanelContent.addComponent(new Label(currentReport.getDescription(), ContentMode.HTML));
				strReportButtonName = EDIT_REPORT;
			} else {
				reportpanelContent.addComponent(new Label(NO_REPORT));
				strReportButtonName = CREATE_REPORT;
			}

			Button btnNewReport = new Button(strReportButtonName);
			AppointmentState state = appointment.getState();
			btnNewReport.setEnabled(!(state instanceof FinishedState));

			Label lblState = new Label(appointment.getStateName());
			reportpanelContent.addComponent(lblState);

			reportpanelContent.addComponent(btnNewReport);
			reportpanel.setContent(reportpanelContent);

			VerticalLayout bottomright = new VerticalLayout();
			bottomright.setSpacing(true);
			bottomright.addComponent(reportpanel);

			bottom.addComponent(bottomright, 1, 0);

			// Listeners
			buttonArrival.addClickListener(clickevent -> {
				createNewReport();
				if (currentReport != null)
					btnNewReport.setCaption(EDIT_REPORT);
				btnArrivalClicked(buttonArrival, btnNewReport, appointment, currentReport);
				buttonArrival.setCaption(timeButtonName());
			});

			saveClientDetails.addClickListener(
					click -> getController().saveDetails(saveClientDetails, appointment, description.getValue()));

			description.addTextChangeListener(click -> {
				saveClientDetails.setCaption(SAVE);
				saveClientDetails.setEnabled(true);
			});

			btnNewReport.addClickListener(clickevent -> {
				createNewReport();
				if (currentReport != null)
					btnNewReport.setCaption(EDIT_REPORT);
				new ReportComponent(appointment, currentReport, getController());
			});

			// TODO: Show Patient data
			buttonDetail.addClickListener(click -> {
            	VaadinSession.getCurrent().getSession().setAttribute("patient", this.appointment.getClient());
            	VaadinSession.getCurrent().getSession().setAttribute("appointment", this.appointment);
            	
            	//set back button
            	BaseView currentView = (BaseView) getUI().getNavigator().getCurrentView();
            	VaadinSession.getCurrent().getSession().setAttribute("oldview", currentView.getName());
            	getUI().getNavigator().navigateTo(PatientDetailView.NAME);
			});

			// TODO: EmerencyContactView
			// buttonEmergencyContact.addClickListener(click -> { do some stuff
			// here... });

		}
		return general;
	}

	private void showTaskDetail(TaskEntity task) {
		final Window window = new Window("Aufgabe");
		window.setWidth(300.0f, Unit.PIXELS);
        window.setResizable(false);
        window.setDraggable(false);
        final VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        final Label label = new Label(
        		"<h2>" + task.getName() + "</h2>"
        		+ "<p>" + task.getDescription() + "</p>"
        );
        label.setContentMode(ContentMode.HTML);
        content.addComponent(label);
        window.setContent(content);
        window.setModal(true);
        UI.getCurrent().addWindow(window);
        window.center();
	}

	// Method to create a new report if it's missing.
	private void createNewReport() {
		ReportDao dao = new ReportDao();
		if (currentReport == null) {
			currentReport = new ReportEntity();
			currentReport.setAppointment(appointment);
			Long now = System.currentTimeMillis() / 1000L;
			currentReport.setStart(now);
			dao.persist(currentReport);
		}
	}

	// clicklistener of button arrival. activate the button "new report" and
	// associate the clicklistener
	private void btnArrivalClicked(Button btnArrival, Button btnReport, AppointmentEntity appointment,
			ReportEntity report) {
		btnReport.setEnabled(true);
		AppointmentState currentState = appointment.getState();
		logger.debug("STATE is: " + currentState);
		if (currentState instanceof PlannedState) {
			btnReport.setCaption(EDIT_REPORT);
		} else if (currentState instanceof FinishedState) {
			btnArrival.setEnabled(false);
			btnReport.setEnabled(false);
		} else if (currentState instanceof ClosedState) {
			btnArrival.setEnabled(false);
			btnReport.setEnabled(false);
			logger.debug("State is CLOSED. Why we are here?");
		} else {
			logger.debug("STATE not found");
		}
		getController().saveReportTime(currentReport, appointment, btnArrival);
		appointment.doAction(appointment);
	}
	
	private String timeButtonName() {
		AppointmentState currentState = appointment.getState();
		if (currentState instanceof RunningState) {
			return CONFIRM_END;
		} else if (currentState instanceof PlannedState) {
			return ARRIVED;
		} else if (currentState instanceof FinishedState) {
			return CLOSE;
		} else if (currentState instanceof ClosedState) {
			logger.debug("State is CLOSED. Why we are here?");
		} else {
			logger.debug("STATE not found");
		}
		return ARRIVED;
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
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
	}

	public AppointmentDetailController getController() {
		return controller;
	}

	public void setController(AppointmentDetailController controller) {
		this.controller = controller;
	}

}