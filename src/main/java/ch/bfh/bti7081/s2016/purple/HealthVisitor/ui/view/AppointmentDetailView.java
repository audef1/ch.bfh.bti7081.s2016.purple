package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.util.Date;
import java.util.Locale;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class AppointmentDetailView extends BaseView{
	public static final String NAME = "AppointmentDetail";
	public static final String VIEW_NAME = "Termindetails";

	private static final Logger logger = LogManager.getLogger(AppointmentDetailView.class);
	private final AppointmentDetailController controller;
	private  VerticalLayout general;

	public AppointmentDetailView() {
		super();
		logger.debug("arrived on appointment detail view");
		controller = new AppointmentDetailController(this);
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		general = new VerticalLayout();
		general.setMargin(new MarginInfo(false, false, true, true));
		general.setSpacing(true);

		AppointmentEntity appointment = controller.getCurrentAppointment();
		if(appointment == null){
			general.addComponent(new Label("Heute keinen Termin gefunden"));
		}else{


			Label lblHeader = new Label(appointment.getDate() +", " + appointment.getStartTime() +
					"-"+appointment.getEndTime()+" - "+appointment.getClient().getFullName());
			lblHeader.setStyleName("h1");

			Button btnArrival = new Button("Ankunft bestätigen");
			boolean stateNull = (appointment.getState() == null );
			boolean isPlanned = appointment.getState().getClass().equals(PlannedState.class);
			btnArrival.setEnabled(stateNull || isPlanned);
			btnArrival.setWidth("200px");

	//		Google-Maps-Implementation
			LatLon pos = new LatLon(46.9648208, 7.453848);

			GoogleMap map = new GoogleMap("AIzaSyCnqIoyh9ULI3b6rtkCYXPdMXRqivaH714", null, "german");
			map.setSizeFull();
			map.addMarker("Wankdorffeldstrasse 102, 3014 Bern", pos, false, null);
			map.setMinZoom(4);
			map.setMaxZoom(16);
			map.setCenter(pos);
			map.setZoom(16);

	//		Create Checklist
			Grid checklist = new Grid();
			checklist.setColumns("Aufgabenliste");

	//		Create Column last report
			Label lastReport = new Label("Letzter Rapport");
			lastReport.setStyleName("h2");

	//		Show the last report
			Label report = new Label("Hier steht der letzte Rapport.");

	//		Create button to show the form of the new report
			Button btnNewReport = new Button("Neuer Rapport erfassen");
			if (btnArrival.getCaption().equals("Ende bestätigen")){
				btnNewReport.setEnabled(true);
			} else {
				btnNewReport.setEnabled(false);
			}


	//		Add clicklistener to button Arrival
			btnArrival.addClickListener(clickevent -> {
				btnArrivalClicked(btnArrival, btnNewReport);
				appointment.doAction(appointment);
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
			data.addComponent(report, 1, 1, 1, 2);
			data.setComponentAlignment(report, Alignment.TOP_LEFT);
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
	private void newReport() {
		final Window window = new Window("Neuer Rapport erstellen");
		window.setWidth("90%");
		final FormLayout content = new FormLayout();
		
		DateField arrival = new DateField();
		arrival.setCaption("Behandlungsbeginn:");
		arrival.setDateFormat("dd.MM.yyyy HH:mm");
		arrival.setLocale(new Locale("de", "CH"));
		arrival.setResolution(arrival.RESOLUTION_MIN);
		content.addComponent(arrival);
		
		DateField end = new DateField();
		end.setCaption("Ende der Behandlung");
		end.setDateFormat("dd.MM.yyyy HH:mm");
		end.setLocale(new Locale("de", "CH"));
		end.setResolution(end.RESOLUTION_MIN);
		end.addValidator(new DateRangeValidator("Ende muss nach dem start liegen", arrival.getValue(), arrival.getRangeEnd(), Resolution.DAY));
		content.addComponent(end);
		
		RichTextArea text = new RichTextArea();
		text.setDescription("aktueller Rapport");
		text.setImmediate(true);
		text.setSizeFull();
		text.addValidator(new StringLengthValidator("Bitte geben Sie einen Text ein", 30, Integer.MAX_VALUE, false));
		content.addComponent(text);
		
		Button btnSave = new Button("Speichern", FontAwesome.SAVE);
		btnSave.addClickListener(clickevent -> {
				save(arrival, end, text);
				window.close();
				});
		content.addComponent(btnSave);
		
		window.setContent(content);
		UI.getCurrent().addWindow(window);
	}

	
//	clicklistener of button arrival. activate the button "new report" and associate the clicklistener
	private void btnArrivalClicked(Button btn, Button report){
		if (!btn.getCaption().equals("Ende bestätigen")) {

			btn.setCaption("Ende bestätigen");
//			TODO: Persistence arrival time to the db!
			report.setEnabled(true);
			report.addClickListener(clickevent -> newReport());
		} else {
			btn.setEnabled(false);
//			TODO: Persitence end time to the db!
		}
	}
	
	private void save(DateField arrival, DateField end, RichTextArea text){
		logger.debug("written text is: "+text.getValue());
		logger.debug(arrival.getValue());
		logger.debug(end.getValue());
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
