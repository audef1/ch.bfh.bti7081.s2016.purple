package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentListController;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class AppointmentListView extends CustomComponent implements View {
    public static final String NAME ="AppointmentList";
    private static final Logger logger = LogManager.getLogger(AppointmentListView.class);
    private final AppointmentListController controller;

    private final GridLayout grid;

    public AppointmentListView(){
        logger.debug("arrived on appointment list view");
        controller = new AppointmentListController(this);

        //TODO outsource into an xml/html file
        //TODO add some magic buttons doing almost nothing
        
        grid = new GridLayout(4, 2);
        grid.addStyleName("ourcustomlayout");
        grid.setSpacing(true);

        grid.setMargin(true);

        VerticalLayout general = new VerticalLayout();
        general.setSpacing(true);
        general.setMargin(true);
        
        HorizontalLayout topLeft = new HorizontalLayout();
        
        Button btBack = new Button("Zurück");
        btBack.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        btBack.addClickListener((clickEvent -> 
        	getUI().getNavigator().navigateTo(DashboardView.NAME)));
        
        topLeft.addComponent(btBack);
        
        HorizontalLayout title = new HorizontalLayout();
        Label listTitle = new Label("Termine für ");
        listTitle.setStyleName("h1");
        
        title.addComponent(listTitle);
        
        Grid list = new Grid();
        list.setColumns("Patient", "Adresse", "Ort", "Beginn", "Ende");
        
        general.addComponents(topLeft, title, list);
        
        grid.addComponent(general);
        
        setCompositionRoot(general);
        
        list.addSelectionListener((clickEvent ->
        		getUI().getNavigator().navigateTo(AppointmentDetailView.NAME)));
        
        /*
        for(String name : buttonNames){
            Button exampleButton = new Button(name);
            exampleButton.setSizeFull();
            exampleButton.setHeight("50px");
            exampleButton.setWidth("300px");
            exampleButton.setDescription("magic");
            grid.addComponent(exampleButton);
        }*/

        /*
        VerticalLayout fields = new VerticalLayout(grid);
        fields.setSpacing(true);
        fields.setCaption("hmm... what now");
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();
		*/
		
        // The view root layout
        /*VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setCaption("Ready to login?");
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);*/
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //TODO set a focus
    }
}
