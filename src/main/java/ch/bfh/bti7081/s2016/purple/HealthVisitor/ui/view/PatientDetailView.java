package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
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
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.PatientDetailController;

@SuppressWarnings("serial")
public class PatientDetailView extends BaseView {

	public static final String NAME = "PatientDetailView";
	public static final String VIEW_NAME = "Patientendetails";
	private static final String ERMERGENCY_CONTACTS = "Notfallkontakte des Patienten";
	private static final String SAVE = "Speichern";
	private static final String CLIENTDESCRIPTION = "Kurzbeschrieb";
	private static final String SAVE_CLIENTDETAILS = "Details speichern";
	private PatientDetailController controller;
	
	private static final Logger logger = LogManager.getLogger(PatientListView.class);

	public PatientDetailView() {
		super();
		//this.controller = new PatientDetailController(this);
		logger.debug("arrived on patient detail view");
		this.controller = new PatientDetailController(this);
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		VerticalLayout general = new VerticalLayout();
		general.setMargin(true);
		general.setSpacing(true);
		
		// get patient from session
		if (VaadinSession.getCurrent().getSession().getAttribute("patient") != null) {
			ClientEntity patient = (ClientEntity) VaadinSession.getCurrent().getSession().getAttribute("patient");
			VaadinSession.getCurrent().getSession().setAttribute("patient", null);

			
			
			// two column layout with topnav and buttom column
			HorizontalLayout topnav = new HorizontalLayout();
			topnav.setSizeFull();
			
			GridLayout grid = new GridLayout(2, 1);
			grid.setSpacing(true);
			grid.setWidth("100%");
			grid.setColumnExpandRatio(0, 0.5f);
			grid.setColumnExpandRatio(1, 0.5f);
			
			GridLayout bottomgrid = new GridLayout(1,1);
			bottomgrid.setSpacing(true);
			bottomgrid.setWidth("100%");
			bottomgrid.setColumnExpandRatio(0, 1.0f);
			
			general.addComponents(topnav, grid, bottomgrid);
			
			
			// topnav
			Button buttonEmergencyContact = new Button(ERMERGENCY_CONTACTS);
			topnav.addComponent(buttonEmergencyContact);
			
			// patient information (top left)
			Panel infopanel = new Panel("Patienteninformationen");
			infopanel.setSizeFull();
			VerticalLayout infopanelContent = new VerticalLayout();
			infopanelContent.setSizeFull();
			infopanelContent.setMargin(true);
			infopanelContent.setSpacing(true);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern("HH:mm");

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
						
			ptable.addItem(new Object[]{new Label("<b>Name:</b>", ContentMode.HTML), patient.getFullName()}, 1);
			ptable.addItem(new Object[]{new Label("<b>Geburtsdatum:</b>", ContentMode.HTML), dob.format(patient.getDateOfBirth())}, 2);

			ptable.setColumnWidth("key", 130);
						
			ptable.setPageLength(ptable.size());
			infopanelContent.addComponent(ptable);
						
			Button saveClientDetails = new Button(SAVE_CLIENTDETAILS);
			saveClientDetails.setEnabled(false);

			TextArea description = new TextArea();
			description.setSizeFull();
			description.setCaption(CLIENTDESCRIPTION);
			description.setValue(patient.getDetails());
			
			infopanelContent.addComponents(description, saveClientDetails);
			
			infopanel.setContent(infopanelContent);

			VerticalLayout topleft = new VerticalLayout();
			topleft.setSpacing(true);
		
			topleft.addComponent(infopanel);

			grid.addComponent(topleft, 0, 0);
			

			// medication information (top right)
			Panel medipanel = new Panel("Medikamente");
			infopanel.setSizeFull();
			VerticalLayout medipanelContent = new VerticalLayout();
			medipanelContent.setSpacing(true);
			medipanelContent.setMargin(true);

			// add stuff here
			
			medipanel.setContent(medipanelContent);

			VerticalLayout topright = new VerticalLayout();
			topright.setSpacing(true);
		
			topright.addComponent(medipanel);
			grid.addComponent(topright, 1, 0);
			
			// next appointments (bottom)
			Panel appointmentpanel = new Panel("Nächste Termine");
			infopanel.setSizeFull();
			VerticalLayout appointmentpanelContent = new VerticalLayout();
			appointmentpanelContent.setSizeFull();
			appointmentpanelContent.setMargin(true);
			
			Collection<AppointmentEntity> appointments = getController().getAppointments(patient);
			final BeanItemContainer<AppointmentEntity> container = new BeanItemContainer<>(AppointmentEntity.class, appointments);

			container.removeContainerProperty("client");
			container.removeContainerProperty("endLong");
			container.removeContainerProperty("startLong");
			container.removeContainerProperty("id");
			container.removeContainerProperty("tasks");
			container.removeContainerProperty("medications");
			container.removeContainerProperty("planned");
			container.removeContainerProperty("hv");
			container.removeContainerProperty("healthVisitor");
			container.removeContainerProperty("report");
			container.removeContainerProperty("state");
			container.removeContainerProperty("stateName");
			
			Grid nextappointments = new Grid(container);
			nextappointments.setColumnOrder("date", "startTime", "endTime", "address", "place");
			nextappointments.sort("date");
			nextappointments.getColumn("date").setHeaderCaption("Datum");
			nextappointments.getColumn("startTime").setHeaderCaption("Beginn");
			nextappointments.getColumn("endTime").setHeaderCaption("Ende");
			nextappointments.getColumn("address").setHeaderCaption("Adresse");
			nextappointments.getColumn("place").setHeaderCaption("Ort");

			nextappointments.setSizeFull();
			nextappointments.setHeightMode(HeightMode.CSS);
			nextappointments.setHeightByRows(container.size());
			
			appointmentpanelContent.addComponents(nextappointments);
			
			nextappointments.addSelectionListener((clickEvent -> {
				// set back button
				BaseView currentView = (BaseView) getUI().getNavigator().getCurrentView();
				VaadinSession.getCurrent().getSession().setAttribute("oldview", currentView.getName());
				
				// handover appointment data to detail view 
				AppointmentEntity appointment = container.getItem(nextappointments.getSelectedRow()).getBean();
				VaadinSession.getCurrent().getSession().setAttribute("appointment", appointment);
				VaadinSession.getCurrent().getSession().setAttribute("patient", patient);
				
				// forward to detaiview
				getUI().getNavigator().navigateTo(AppointmentDetailView.NAME);
			}));

			appointmentpanel.setContent(appointmentpanelContent);
			
			VerticalLayout bottom = new VerticalLayout();
			bottom.setSpacing(true);
		
			bottom.addComponent(appointmentpanel);
			bottomgrid.addComponent(bottom);
			
			
			// listeners
			saveClientDetails.addClickListener(
					click -> getController().saveDetails(saveClientDetails, patient, description.getValue()));

			description.addTextChangeListener(click -> {
				saveClientDetails.setCaption(SAVE);
				saveClientDetails.setEnabled(true);
			});

		}
		else {
			general.addComponent(new Label("Keine Patientendaten vorhanden"));
		}
		
		return general;
	}

	public PatientDetailController getController() {
		return controller;
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}

	@Override
	public String getName() {
		return NAME;
	}
}