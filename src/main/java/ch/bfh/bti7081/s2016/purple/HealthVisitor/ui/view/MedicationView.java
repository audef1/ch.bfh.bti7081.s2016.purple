package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.MedicationController;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class MedicationView extends BaseView {
	public static final String VIEW_NAME ="Medication";
    public static final String NAME ="Medication";
    private static final Logger logger = LogManager.getLogger(MedicationView.class);
    private final MedicationController controller;
    
    public MedicationView(){
        super();
        logger.debug("arrived on medication view");
        controller = new MedicationController(this);
        layout = new StandardLayout(this);
    }

    @Override
    public Layout initView() {
        VerticalLayout general = new VerticalLayout();
        general.setSpacing(true);
        general.setMargin(true);


    	Collection<MedicationEntity> medications = this.controller.getMedicationForDay();
    	BeanItemContainer<MedicationEntity> container = new BeanItemContainer<>(MedicationEntity.class, medications);
        
        Grid grid = new Grid(container);
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addSelectionListener(new SelectionListener(){
			@Override
			public void select(SelectionEvent event) {
				logger.debug("selection event triggered");
		        controller.check(getItems(event.getAdded()));
		        controller.uncheck(getItems(event.getRemoved()));
			}
//			private Collection<BeanItem<MedicationEntity>> getItems(Set<Object> itemIds) {
			private Collection<MedicationEntity> getItems(Set<Object> itemIds) {
	            List<MedicationEntity> items = new ArrayList<>();
	            for (Object id : itemIds) {
	            	BeanItem<MedicationEntity> beanItem = (BeanItem<MedicationEntity>) grid.
                            getContainerDataSource().getItem(id);
	            	items.add(beanItem.getBean());
	            }
	            return items;
	        }
        });

        medications.stream().filter(medication -> medication.isChecked()).forEach(grid::select);
        general.addComponents(grid);
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
