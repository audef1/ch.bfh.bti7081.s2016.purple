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
		DashboardButtonComponent btGeneral = new DashboardButtonComponent("Allgemeiner Notruf (112)", FontAwesome.PLUS_SQUARE);
		btGeneral.setWidth("100%");
		general.addComponent(btGeneral, 0, 0, 1, 0);
		Resource resGeneral = new ExternalResource("tel://112");
		final ResourceReference rrGeneral = ResourceReference.create(resGeneral, btGeneral, "Allgemeiner Notruf (112)");
		btGeneral.addClickListener(clickevent -> Page.getCurrent().open(rrGeneral.getURL(), null));
		
		// Create Button to call the police
		DashboardButtonComponent btPolice = new DashboardButtonComponent("Polizei (117)", FontAwesome.CAB);
		general.addComponent(btPolice, 0, 1);
		Resource resPolice = new ExternalResource("tel://117");
		final ResourceReference rrPolice = ResourceReference.create(resPolice, btPolice, "Polizei (117)");
		btPolice.addClickListener(clickevent -> Page.getCurrent().open(rrPolice.getURL(), null));
		
		// Create Button to call the ambulance
		DashboardButtonComponent btAmbulance = new DashboardButtonComponent("Ambulanz (144)", FontAwesome.AMBULANCE);
		general.addComponent(btAmbulance, 1, 1);
		Resource resAmbulance = new ExternalResource("tel://144");
		final ResourceReference rrAmbulance = ResourceReference.create(resAmbulance, btAmbulance, "Ambulanz (144)");
		btGeneral.addClickListener(clickevent -> Page.getCurrent().open(rrAmbulance.getURL(), null));
		
		// Create Button to call the firefighters
		DashboardButtonComponent btFireFighter = new DashboardButtonComponent("Feuerwehr (118)", FontAwesome.FIRE_EXTINGUISHER);
		general.addComponent(btFireFighter, 0, 2);
		Resource resFireFighter = new ExternalResource("tel://118");
		final ResourceReference rrFireFighter = ResourceReference.create(resFireFighter, btFireFighter, "Feuerwehr (118)");
		btGeneral.addClickListener(clickevent -> Page.getCurrent().open(rrFireFighter.getURL(), null));
		
		// Create Button to call REGA
		DashboardButtonComponent btRega = new DashboardButtonComponent("REGA (1414)", FontAwesome.H_SQUARE);
		general.addComponent(btRega, 1, 2);
		Resource resRega = new ExternalResource("tel://1414");
		final ResourceReference rrRega = ResourceReference.create(resRega, btRega, "Feuerwehr (118)");
		btGeneral.addClickListener(clickevent -> Page.getCurrent().open(rrRega.getURL(), null));
		
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
