package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.MedicationService;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.MedicationController;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.listener.SelectMedicationListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * @author tgdflto1 on 20/05/16.
 */
public class MedicationView extends BaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3630629153886169591L;
	public static final String VIEW_NAME = "Medication";
    public static final String NAME = "Medication";
    public static final String LABEL_NO_MEDICATIONS = "Heute gibt es keine Medikamente zum mitnehmen!";
    public static final String LABEL_INFO = "Medikamente die Sie heute mitbringen müssen:";
    public static final String LABEL_PACKAGES = "Packung(en)";
    private static final Logger logger = LogManager.getLogger(MedicationView.class);
    private final MedicationController controller;
    
    private final MedicationService medicationService = MedicationService.getInstance();
    
    public MedicationView(){
        super();
        logger.debug("arrived on medication view");
        controller = new MedicationController(this);
        layout = new StandardLayout(this);
    }

    @SuppressWarnings("serial")
	@Override
    public Layout initView() {
        VerticalLayout general = new VerticalLayout();
        general.setSpacing(true);
        general.setMargin(true);
        

		Label info = new Label(LABEL_INFO);
		general.addComponent(info);

    	Collection<MedicationEntity> medications = this.medicationService.getMedicationForDay(controller.getUser());
    	if (medications.size() == 0){
    		Label message = new Label(LABEL_NO_MEDICATIONS);
    		general.addComponent(message);
    	} else {
	    	BeanItemContainer<MedicationEntity> container = new BeanItemContainer<>(MedicationEntity.class, medications);
	        
	        Grid grid = new Grid(container);
	        grid.setSizeFull();
	        grid.setColumnOrder("amount", "name");
	        grid.setColumns("amount", "name");
	        grid.getColumn("amount").setHeaderCaption(LABEL_PACKAGES);
	        
	        grid.setSelectionMode(SelectionMode.MULTI);
            grid.addSelectionListener(new SelectMedicationListener(controller, grid));

			medications.stream().filter(MedicationEntity::isChecked).forEach(grid::select);
			general.addComponent(grid);
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
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //TODO set a focus
    }
	
}
