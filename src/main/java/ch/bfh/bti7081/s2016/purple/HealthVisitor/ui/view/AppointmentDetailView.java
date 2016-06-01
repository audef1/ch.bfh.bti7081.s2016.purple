package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.RunningState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.ReportComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ReportEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;

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





	private static final Logger logger = LogManager.getLogger(AppointmentDetailView.class);
	private final AppointmentDetailController controller;
	private  VerticalLayout general;

	private ReportEntity currentReport;
	AppointmentDao appointmentDao;
	
	public AppointmentDetailView() {
		super();
		logger.debug("arrived on appointment detail view");
		controller = new AppointmentDetailController(this);
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		appointmentDao = AppointmentDao.getInstance();
		String strReportButtonName;
		String strArrivalButtonName = ARRIVED;
		general = new VerticalLayout();
		general.setMargin(new MarginInfo(false, false, true, true));
		general.setSpacing(true);

		AppointmentEntity appointment = controller.getCurrentAppointment();
		if(appointment == null){
			general.addComponent(new Label("Heute keinen Termin gefunden"));
		}else{
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern("HH:mm");

			Label lblHeader = new Label(appointment.getDate() +", " + dateFormat.format(appointment.getStartTime()) +
					"-"+dateFormat.format(appointment.getEndTime())+" - "+appointment.getClient().getFullName());
			lblHeader.setStyleName("h1");
			
			currentReport = appointment.getReport();
			
			if (currentReport != null && currentReport.getStart() > 0) strArrivalButtonName = CONFIRM_END;
			Button buttonArrival = new Button(strArrivalButtonName);
			boolean stateNull = (appointment.getState() == null );
			boolean isPlanned = false;
			if(!stateNull) isPlanned = appointment.getState().getClass().equals(PlannedState.class);
			buttonArrival.setWidth("200px");

	//		Google-Maps-Implementation
			LatLon pos = new LatLon(46.9648208, 7.453848);

			GoogleMap map = new GoogleMap("AIzaSyCnqIoyh9ULI3b6rtkCYXPdMXRqivaH714", null, "german");
			map.setSizeFull();
			map.addMarker(appointment.getAddress() +" "+ appointment.getPlace(), pos, false, null);
			map.setMinZoom(4);
			map.setMaxZoom(16);
			map.setCenter(pos);
			map.setZoom(16);

	//		Create Checklist
			Grid checklist = new Grid();
			checklist.setColumns("Aufgabenliste");

	//		Create Column last report
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


	//		Create button to show the form of the new report
			Button btnNewReport = new Button(strReportButtonName);
			btnNewReport.addClickListener(clickevent -> new ReportComponent(appointment, currentReport, controller));
			btnNewReport.setEnabled(!isPlanned);



	//		Add clicklistener to button Arrival
			buttonArrival.addClickListener(clickevent -> {
				btnArrivalClicked(buttonArrival, btnNewReport, appointment, currentReport);
			});

	//		Create button to show patient details
			Button buttonDetail = new Button("Patientendetails");
	//		TODO: Show Patient data


	//		Create Column Info and emergency-contact
			Label lblBeschriebTitle = new Label("Kurzbeschrieb");
			lblBeschriebTitle.setStyleName("h2");
			Button saveClientDetails = new Button("Details speichern");
			saveClientDetails.setEnabled(false);

	//		Show short description about the patient
			TextArea description = new TextArea();
			description.setValue(appointment.getClient().getDetails());
			description.addTextChangeListener(click -> {
				saveClientDetails.setCaption("Speichern");
				saveClientDetails.setEnabled(true);
			});

			saveClientDetails.addClickListener(click -> controller.saveDetails(saveClientDetails, appointment, description.getValue()));
			Button btnEmergencyContact = new Button("Notfallkontakte des Patienten");
			VerticalLayout patientDetails = new VerticalLayout(description, saveClientDetails);

	//		TODO: EmerencyContactView
			//		btnEmergencyContact.addClickListener(clickevent -> getUI().getNavigator().navigateTo(emergencyContactView.NAME));


			// The Layout for title and arrival button
			GridLayout top = new GridLayout(2, 2);
			top.setColumnExpandRatio(0, 10);
			top.setColumnExpandRatio(1, 0);
			top.setSizeFull();
			top.addComponent(lblHeader, 0, 0);
			top.setComponentAlignment(lblHeader, Alignment.MIDDLE_LEFT);
			top.addComponent(buttonArrival, 1, 0);
			top.setComponentAlignment(buttonArrival, Alignment.MIDDLE_RIGHT);
			top.setMargin(new MarginInfo(true, true, false, false));


	//		Set the data-Layout
			GridLayout data = new GridLayout(3, 5);
			data.setSpacing(true);
			data.setMargin(false);
			data.setSizeFull();

			data.addComponent(map, 0, 0, 0, 2);
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

	//		data.addComponents(map, checklist);

			// Set the root layout
			general.addComponent(top);
			general.addComponent(data);
		}

		return general;
	}

	
//	clicklistener of button arrival. activate the button "new report" and associate the clicklistener
	private void btnArrivalClicked(Button btnArrival, Button btnReport, AppointmentEntity appointment, ReportEntity report){
		btnReport.setEnabled(true);
		if (btnArrival.getCaption().equals(CONFIRM_END)) {
			btnArrival.setCaption(CLOSE);
			btnReport.setCaption(EDIT_REPORT);
		}else if(btnArrival.getCaption().equals(ARRIVED)){
			btnArrival.setCaption(CONFIRM_END);
		} else if(btnArrival.getCaption().equals(CLOSE)) {
			btnArrival.setEnabled(false);
			btnReport.setEnabled(false);
		}
		controller.saveReportTime(currentReport, appointment);
		appointment.doAction(appointment);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return VIEW_NAME;
	}

}
