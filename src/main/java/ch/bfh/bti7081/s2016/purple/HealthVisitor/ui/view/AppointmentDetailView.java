package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.util.Locale;

import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;

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


		Label lblHeader = new Label("Termin Detail");
		lblHeader.setStyleName("header");

		Button btBack = new Button("Zurück");
		btBack.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btBack.addClickListener((clickEvent -> getUI().getNavigator().navigateTo(DashboardView.NAME)));


		Button btnArrival = new Button("Ankunft bestätigen");
		

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
		btnArrival.addClickListener(clickevent ->
		btnArrivalClicked(btnArrival, btnNewReport));

//		Vreate button to show patient details
		Button btnDetails = new Button("Patientendetails");
//		TODO: Show Patient data


//		Create Column Info and emergency-contact
		Label lblBeschriebTitle = new Label("Kurzbeschrieb");
		lblBeschriebTitle.setStyleName("h2");

//		Show short description about the patient
		Label lblBeschrieb = new Label("Hier steht der Kurzbeschrieb über den Patienten");

		Button btnEmergencyContact = new Button("Notfallkontakte des Patienten");
//		TODO: EmerencyContactView
		//		btnEmergencyContact.addClickListener(clickevent -> getUI().getNavigator().navigateTo(emergencyContactView.NAME));


		// The Layout for the Logo
		HorizontalLayout hl = new HorizontalLayout(btBack, lblHeader);
		hl.setSpacing(true);
		hl.setMargin(new MarginInfo(true, false, true, false));

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(btnArrival);
		vl.setComponentAlignment(btnArrival, Alignment.TOP_LEFT);
		vl.setMargin(new MarginInfo(false, false, true, false));
		vl.setSpacing(true);

		GridLayout top = new GridLayout(2, 2);
		top.setSizeFull();
		top.addComponent(hl, 0, 0);
		top.addComponent(vl, 1, 0);
//		top.addComponent(lblTitle, 0, 1);
		top.setMargin(new MarginInfo(false, true, false, false));


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
		data.addComponent(lblBeschrieb, 2, 1, 2, 3);
		data.setComponentAlignment(lblBeschrieb, Alignment.TOP_LEFT);
		data.addComponent(btnEmergencyContact, 2, 4);
		data.setComponentAlignment(btnEmergencyContact, Alignment.TOP_LEFT);

//		data.addComponents(map, checklist);




		// Set the root layout
		general.addComponent(top);
		general.addComponent(data);

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
		btnSave.addClickListener(clickevent ->
				save(arrival, end, text));
		content.addComponent(btnSave);
		
		window.setContent(content);
		UI.getCurrent().addWindow(window);
	}

	
//	clicklistener of button arrival. activate the button "new report" and associate the clicklistener
	private void btnArrivalClicked(Button btn, Button report){
		btn.setCaption("Ende bestätigen");
		report.setEnabled(true);
		report.addClickListener(clickevent -> newReport());
		// TODO Persistence Current DateTime to the DB.
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
