package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
		String strReportButtonName = "";
		String strArrivalButtonName = "Ankunft bestätigen";
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
			
			if (currentReport != null){
				if (currentReport.getStart() > 0) strArrivalButtonName = "Ende bestätigen";
			}
			
			Button btnArrival = new Button(strArrivalButtonName);
			boolean stateNull = (appointment.getState() == null );
			boolean isPlanned = false;
			if(!stateNull) isPlanned = appointment.getState().getClass().equals(PlannedState.class);
			btnArrival.setEnabled(stateNull || isPlanned);
			btnArrival.setWidth("200px");

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
			
			Label lastReport = new Label("Letzter Rapport");
			lastReport.setStyleName("h2");
			reportContainer.addComponent(lastReport);
//			List<ReportEntity> allReports = appointment.getReports();
			if(currentReport != null){
				reportContainer.addComponent(new Label(currentReport.getDescription()));
				strReportButtonName = "Rapport bearbeiten";
			}else{
				reportContainer.addComponent(new Label("Kein alter Report vorhanden."));
				strReportButtonName = "Neuen Rapport erfassen";
			}


	//		Create button to show the form of the new report
			Button btnNewReport = new Button(strReportButtonName);
			btnNewReport.addClickListener(clickevent -> newReport(appointment));
			if (btnArrival.getCaption().equals("Ende bestätigen")){
				btnNewReport.setEnabled(true);
			} else {
				btnNewReport.setEnabled(false);
			}


	//		Add clicklistener to button Arrival
			btnArrival.addClickListener(clickevent -> {
				btnArrivalClicked(btnArrival, btnNewReport, appointment, currentReport);
			});

	//		Create button to show patient details
			Button btnDetails = new Button("Patientendetails");
	//		TODO: Show Patient data


	//		Create Column Info and emergency-contact
			Label lblBeschriebTitle = new Label("Kurzbeschrieb");
			lblBeschriebTitle.setStyleName("h2");
			Button saveClientDetails = new Button("Details speichern");
			saveClientDetails.setEnabled(false);

	//		Show short description about the patient
			TextArea description = new TextArea();
			description.setValue(appointment.getClient().getDetails());
			description.addTextChangeListener(click -> {saveClientDetails.setCaption("Speichern"); saveClientDetails.setEnabled(true);});

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
			top.addComponent(btnArrival, 1, 0);
			top.setComponentAlignment(btnArrival, Alignment.MIDDLE_RIGHT);
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
			data.addComponent(btnDetails, 1, 4);
			data.setComponentAlignment(btnDetails, Alignment.TOP_LEFT);
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



	//	Create pop-up window to enter a new report
	private void newReport(AppointmentEntity appointmentEntity) {
		String datePattern = "dd.MM.yyyy HH:mm";
//		Locale swissGerman = new Locale("de", "CH");
		SimpleDateFormat df = new SimpleDateFormat(datePattern);
		
		final Window window = new Window("Rapport bearbeiten");
		window.setWidth("90%");
		final FormLayout content = new FormLayout();
		if(appointmentEntity == null) logger.debug("appointment null");
		if(appointmentEntity.getReport() == null) logger.debug("report null");
		//if(appointmentEntity.getReport().getStart() == null) logger.debug("starttime null");
		
		AppointmentEntity refreshedAppointmentEntity = AppointmentDao.getInstance().findById(appointmentEntity.getId());
		Date startDate = new Date(refreshedAppointmentEntity.getReport().getEnd());
		DateField arrival = new DateField(df.format(startDate));
		arrival.setCaption("Behandlungsbeginn:");
		arrival.setDateFormat(datePattern);
//		arrival.setLocale(swissGerman);
		arrival.setResolution(arrival.RESOLUTION_MIN);
		content.addComponent(arrival);
		
		Date endDate = new Date(refreshedAppointmentEntity.getReport().getEnd());
		DateField end = new DateField(df.format(endDate));
		end.setCaption("Ende der Behandlung");
		end.setDateFormat(datePattern);
//		end.setLocale(swissGerman);
		end.setResolution(end.RESOLUTION_MIN);
		end.addValidator(new DateRangeValidator("Ende muss nach dem start liegen", arrival.getValue(), arrival.getRangeEnd(), Resolution.DAY));
		content.addComponent(end);
		
		RichTextArea text = new RichTextArea();
		text.setDescription("aktueller Rapport");
		text.setImmediate(true);
		text.setSizeFull();
		text.addValidator(new StringLengthValidator("Bitte geben Sie einen Text ein", 10, Integer.MAX_VALUE, false));
		content.addComponent(text);
		
		Button btnSave = new Button("Speichern", FontAwesome.SAVE);
		btnSave.addClickListener(clickevent -> {
				controller.save(arrival, end, text, refreshedAppointmentEntity);
				window.close();
				getUI().getNavigator().navigateTo(NAME);
				});
		content.addComponent(btnSave);
		
		window.setContent(content);
		UI.getCurrent().addWindow(window);
	}

	
//	clicklistener of button arrival. activate the button "new report" and associate the clicklistener
	private void btnArrivalClicked(Button btnArrival, Button btnReport, AppointmentEntity appointmentEntity, ReportEntity report){
		if (!btnArrival.getCaption().equals("Ende bestätigen")) {
			btnArrival.setCaption("Ende bestätigen");
			
			btnReport.setCaption("Rapport bearbeiten");
		} else {
			btnArrival.setEnabled(false);
		}
		btnReport.setEnabled(true);
		
		controller.saveReportTime(currentReport, appointmentEntity);
		
		appointmentEntity.doAction(appointmentEntity);
//		appointmentDao.update(appointmentEntity);
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
