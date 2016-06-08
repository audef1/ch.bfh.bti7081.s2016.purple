package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;

public class PatientDetailView extends BaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7480991664922494897L;
	public static final String NAME = "PatientDetailView";
	public static final String VIEW_NAME = "Patientendetails";

	private static final Logger logger = LogManager.getLogger(PatientListView.class);

	private ClientEntity patient;
	
	public PatientDetailView() {
		super();
		//this.controller = new PatientDetailController(this);
		logger.debug("arrived on patient detail view");
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		VerticalLayout general = new VerticalLayout();
		general.setSpacing(true);
		general.setMargin(true);
		
		// get patient from session
		if (VaadinSession.getCurrent().getSession().getAttribute("patient") != null) {
			this.patient = (ClientEntity) VaadinSession.getCurrent().getSession().getAttribute("patient");
			VaadinSession.getCurrent().getSession().setAttribute("patient", null);
		
			general.addComponent(new Label(this.patient.getFullName()));
		
		}
		else {
			general.addComponent(new Label("Keine Patientendaten vorhanden"));
		}
		
		return general;
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