package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.MedicationController;

import com.vaadin.client.ui.FontIcon;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
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
    public static final String NAME ="Medication";
    private static final Logger logger = LogManager.getLogger(MedicationView.class);
    private final MedicationController controller;


    public MedicationView(){
        super();
        logger.debug("arrived on dashboard view");
        controller = new MedicationController(this);
        layout = new StandardLayout(this);
    }

    @Override
    public Layout initView() {
        VerticalLayout general = new VerticalLayout();
        general.setSpacing(true);
        general.setMargin(true);

        Grid grid = new Grid(getContainer());
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addSelectionListener(new SelectionListener(){
			@Override
			public void select(SelectionEvent event) {
		        controller.check(getItems(event.getAdded()));
		        controller.uncheck(getItems(event.getRemoved()));
			}
//			private Collection<BeanItem<MedicationEntity>> getItems(Set<Object> itemIds) {
			private Collection<MedicationEntity> getItems(Set<Object> itemIds) {
	            List<MedicationEntity> items = new ArrayList<MedicationEntity>();
	            for (Object id : itemIds) {
	            	BeanItem<MedicationEntity> beanItem = (BeanItem<MedicationEntity>) grid.
                            getContainerDataSource().getItem(id);
	            	items.add(beanItem.getBean());
	            }
	            return items;
	        }
        });
        general.addComponents(grid);
      
        return general;
    }
    
    protected BeanItemContainer<MedicationEntity> getContainer(){
    	Collection<MedicationEntity> medications = this.controller.getMedicationForDay();
        return new BeanItemContainer<>(MedicationEntity.class, medications);
    }

    @Override
    public String getViewName() {
        return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //TODO set a focus
    }
}
