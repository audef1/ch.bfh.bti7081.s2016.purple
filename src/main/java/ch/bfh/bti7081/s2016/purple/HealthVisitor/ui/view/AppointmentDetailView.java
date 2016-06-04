package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.AppointmentState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.FinishedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.RunningState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ReportEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.GoogleMapsComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.ReportComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

public class AppointmentDetailView extends BaseView{
	public static final String NAME = "AppointmentDetail";
	public static final String VIEW_NAME = "Termindetails";

	//States
	public static final String CONFIRM_END = "Ende bestätigen";
	public static final String CLOSE = "Abschliessen";
	public static final String ARRIVED = "Ankunft bestätigen";

	//Report
	public static final String EDIT_REPORT = "Report bearbeiten";
	public static final String CREATE_REPORT = "Report erstellen";
	public static final String NO_REPORT = "Kein Report vorhanden";
	public static final String LAST_REPORT = "Letzter Report";

	//Client
	public static final String CLIENTDETAILS = "Patientendetails";
	public static final String CLIENTDESCRIPTION = "Kurzbeschrieb";
	public static final String SAVE_CLIENTDETAILS = "Details speichern";

	//Other
	public static final String NO_APPOINTMENT = "Heute keinen Termin gefunden";
	public static final String TASKLIST = "Aufgabenliste";
	public static final String SAVE = "Speichern";
	public static final String ERMERGENCY_CONTACTS = "Notfallkontakte des Patienten";

	private static final Logger logger = LogManager.getLogger(AppointmentDetailView.class);
	private final AppointmentDetailController controller;
	AppointmentDao appointmentDao;
	private  VerticalLayout general;
	private ReportEntity currentReport;
	private AppointmentEntity appointment;
	
	public AppointmentDetailView() {
		super();
		logger.debug("arrived on appointment detail view");
		controller = new AppointmentDetailController(this);
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		String strReportButtonName;
		String strArrivalButtonName = ARRIVED;
		general = new VerticalLayout();
		general.setMargin(true);
		general.setSpacing(true);

		//get appointment from session (calendarview)
		if (VaadinSession.getCurrent().getSession().getAttribute("appointment") != null){
			this.appointment = (AppointmentEntity) VaadinSession.getCurrent().getSession().getAttribute("appointment");
			VaadinSession.getCurrent().getSession().setAttribute("appointment", null);
		}else
			this.appointment = controller.getCurrentAppointment();

	
		if(this.appointment == null){
			general.addComponent(new Label(NO_APPOINTMENT));
		}else{
			
			// two column layout with topnav
			HorizontalLayout topnav = new HorizontalLayout();
			topnav.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
			general.addComponent(topnav);
			
			GridLayout top = new GridLayout(2,1);
			top.setWidth("100%");
			top.setColumnExpandRatio(0, 0.5f);
			top.setColumnExpandRatio(1, 0.5f);
			general.addComponent(top);
			
			GridLayout bottom = new GridLayout(2,1);
			bottom.setWidth("100%");
			bottom.setColumnExpandRatio(0, 0.5f);
			bottom.setColumnExpandRatio(1, 0.5f);
			general.addComponent(bottom);
		
		
			// topnav
			Button buttonArrival = new Button(strArrivalButtonName);
			buttonArrival.setWidth("200px");
			
			topnav.addComponent(buttonArrival);	
			
			// infopanel (top left)
			Panel infopanel = new Panel("Termininformationen");
			infopanel.setSizeFull();
			VerticalLayout infopanelContent = new VerticalLayout();
			infopanelContent.addComponent(new Label(appointment.getDate()));
		
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern("HH:mm");
			infopanelContent.addComponent(new Label(dateFormat.format(appointment.getStartTime()) + " - " + dateFormat.format(appointment.getEndTime())));
			
			infopanel.setContent(infopanelContent);
			VerticalLayout topleft = new VerticalLayout();
			topleft.setSpacing(true);
			GoogleMap map = new GoogleMapsComponent(appointment.getAddress() +" "+ appointment.getPlace());
			
			topleft.addComponent(infopanel);
			topleft.addComponent(map);
			
			top.addComponent(topleft, 0, 0);
			
			
			// user information (top right)
			
			
			// checklist (bottom left)
			
			
			// reporting (bottom right)
			
			

			currentReport = appointment.getReport();
			
			if (currentReport != null && currentReport.getStart() > 0)
				strArrivalButtonName = CONFIRM_END;
			


			// Create Checklist
			Grid checklist = new Grid();
			checklist.setColumns(TASKLIST);

			// Create Column last report
			VerticalLayout reportContainer = new VerticalLayout();
			reportContainer.setWidth("300px");
			
			Label lastReport = new Label(LAST_REPORT);
			lastReport.setStyleName("h2");
			reportContainer.addComponent(lastReport);

			if(currentReport != null){
				reportContainer.addComponent(new Label(currentReport.getDescription()));
				strReportButtonName = EDIT_REPORT;
			}else{
				reportContainer.addComponent(new Label(NO_REPORT));
				strReportButtonName = CREATE_REPORT;
			}


			// Create button to show the form of the new report
			Button btnNewReport = new Button(strReportButtonName);
			btnNewReport.addClickListener(clickevent -> new ReportComponent(appointment, currentReport, controller));
			btnNewReport.setEnabled(!appointment.isPlanned());

			// Add clicklistener to button Arrival
			buttonArrival.addClickListener(clickevent -> btnArrivalClicked(buttonArrival,
					btnNewReport, appointment, currentReport));

			// Create button to show patient details
			Button buttonDetail = new Button(CLIENTDETAILS);
			// TODO: Show Patient data

			// Create Column Info and emergency-contact
			Label lblBeschriebTitle = new Label(CLIENTDESCRIPTION);
			lblBeschriebTitle.setStyleName("h2");
			Button saveClientDetails = new Button(SAVE_CLIENTDETAILS);
			saveClientDetails.setEnabled(false);

	//		Show short description about the patient
			TextArea description = new TextArea();
			description.setValue(appointment.getClient().getDetails());
			description.addTextChangeListener(click -> {
				saveClientDetails.setCaption(SAVE);
				saveClientDetails.setEnabled(true);
			});

			saveClientDetails.addClickListener(click -> controller.saveDetails(saveClientDetails, appointment, description.getValue()));
			Button btnEmergencyContact = new Button(ERMERGENCY_CONTACTS);
			VerticalLayout patientDetails = new VerticalLayout(description, saveClientDetails);

			// TODO: EmerencyContactView

			// Set the data-Layout
			GridLayout data = new GridLayout(3, 5);
			data.setSpacing(true);
			data.setMargin(false);
			data.setSizeFull();

			data.addComponent(checklist, 0, 3, 0, 4);
			data.addComponent(lastReport, 1, 0);
			data.setComponentAlignment(lastReport, Alignment.TOP_LEFT);
			data.addComponent(reportContainer, 1, 1, 1, 2);
			data.setComponentAlignment(reportContainer, Alignment.TOP_LEFT);
			data.addComponent(btnNewReport, 1, 3);
			data.setComponentAlignment(btnNewReport, Alignment.TOP_LEFT);
			data.addComponent(buttonDetail, 1, 4);
			data.setComponentAlignment(buttonDetail, Alignment.TOP_LEFT);
			data.addComponent(lblBeschriebTitle, 2, 0);
			data.setComponentAlignment(lblBeschriebTitle, Alignment.TOP_LEFT);
			data.addComponent(patientDetails, 2, 1, 2, 3);
			data.setComponentAlignment(patientDetails, Alignment.TOP_LEFT);
			data.addComponent(btnEmergencyContact, 2, 4);
			data.setComponentAlignment(btnEmergencyContact, Alignment.TOP_LEFT);

			general.addComponent(data);
		}
		return general;
	}

	
	// clicklistener of button arrival. activate the button "new report" and associate the clicklistener
	private void btnArrivalClicked(Button btnArrival, Button btnReport, AppointmentEntity appointment, ReportEntity report){
		btnReport.setEnabled(true);
		AppointmentState currentState = appointment.getState();
		logger.debug("STATE is: " + currentState);
		if (currentState instanceof RunningState) {
			btnArrival.setCaption(CLOSE);
			btnReport.setCaption(EDIT_REPORT);
		} else if (currentState instanceof PlannedState) {
			btnArrival.setCaption(CONFIRM_END);
		} else if (currentState instanceof FinishedState) {
			btnArrival.setEnabled(false);
			btnReport.setEnabled(false);
		} else {
			logger.debug("STATE not found");
		}
		controller.saveReportTime(currentReport, appointment);
		appointment.doAction(appointment);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}
}