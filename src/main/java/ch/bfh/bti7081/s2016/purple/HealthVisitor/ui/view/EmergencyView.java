package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ResourceReference;
import com.vaadin.server.Responsive;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.DashboardButtonComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.EmergencyController;

public class EmergencyView extends BaseView {
	public static final String NAME ="Emergency";
	private static final Logger logger = LogManager.getLogger(MedicationView.class);

	public static final String EMERGENCY = "Allgemeiner Notruf (112)";
	public static final String EMERGENCY_NUMBER = "tel://112";
	public static final String POLICE_NUMBER = "tel://117";
	public static final String POLICE = "Polizei (117)";
	public static final String AMBULANCE = "Ambulanz (144)";
	public static final String AMBULANCE_NUMBER = "tel://144";
	public static final String FIRE_DEPARTMENT = "Feuerwehr (118)";
	public static final String FIRE_DEPARTMENT_NUMBER = "tel://118";
	public static final String REGA = "REGA (1414)";
	public static final String REGA_NUMBER = "tel://1414";

	private final EmergencyController controller;
    private GridLayout general;
    
    
    public EmergencyView(){
    	super();
    	logger.debug("arrived on emergency view");
    	controller = new EmergencyController(this);
    	layout = new StandardLayout(this);
    }
	
    
	@Override
	public Layout initView() {
		general = new GridLayout(2, 3);
		general.setSpacing(true);
		general.setMargin(true);
		Responsive.makeResponsive(general);
		//general.setComponentAlignment(general, Alignment.MIDDLE_CENTER);
		
		
		// Create Button to call the general emergency number of Europe
		DashboardButtonComponent btGeneral = new DashboardButtonComponent(EMERGENCY, FontAwesome.PLUS_SQUARE);
		btGeneral.setWidth("100%");
		general.addComponent(btGeneral, 0, 0, 1, 0);
		Resource resGeneral = new ExternalResource(EMERGENCY_NUMBER);
		final ResourceReference rrGeneral = ResourceReference.create(resGeneral, btGeneral, EMERGENCY);
		btGeneral.addClickListener(clickevent -> Page.getCurrent().open(rrGeneral.getURL(), null));
		
		// Create Button to call the police
		DashboardButtonComponent btPolice = new DashboardButtonComponent(POLICE, FontAwesome.CAB);
		general.addComponent(btPolice, 0, 1);
		Resource resPolice = new ExternalResource(POLICE_NUMBER);
		final ResourceReference rrPolice = ResourceReference.create(resPolice, btPolice, POLICE);
		btPolice.addClickListener(clickevent -> Page.getCurrent().open(rrPolice.getURL(), null));
		
		// Create Button to call the ambulance
		DashboardButtonComponent btAmbulance = new DashboardButtonComponent(AMBULANCE, FontAwesome.AMBULANCE);
		general.addComponent(btAmbulance, 1, 1);
		Resource resAmbulance = new ExternalResource(AMBULANCE_NUMBER);
		final ResourceReference rrAmbulance = ResourceReference.create(resAmbulance, btAmbulance,  AMBULANCE);
		btAmbulance.addClickListener(clickevent -> Page.getCurrent().open(rrAmbulance.getURL(), null));
		
		// Create Button to call the firefighters
		DashboardButtonComponent btFireFighter = new DashboardButtonComponent(FIRE_DEPARTMENT, FontAwesome.FIRE_EXTINGUISHER);
		general.addComponent(btFireFighter, 0, 2);
		Resource resFireFighter = new ExternalResource(FIRE_DEPARTMENT_NUMBER);
		final ResourceReference rrFireFighter = ResourceReference.create(resFireFighter, btFireFighter, FIRE_DEPARTMENT);
		btFireFighter.addClickListener(clickevent -> Page.getCurrent().open(rrFireFighter.getURL(), null));
		
		// Create Button to call REGA
		DashboardButtonComponent btRega = new DashboardButtonComponent(REGA, FontAwesome.H_SQUARE);
		general.addComponent(btRega, 1, 2);
		Resource resRega = new ExternalResource(REGA_NUMBER);
		final ResourceReference rrRega = ResourceReference.create(resRega, btRega, REGA);
		btRega.addClickListener(clickevent -> Page.getCurrent().open(rrRega.getURL(), null));
		
		return general;
	}
	
	
	@Override
	public String getViewName() {
		return NAME;
	}
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
