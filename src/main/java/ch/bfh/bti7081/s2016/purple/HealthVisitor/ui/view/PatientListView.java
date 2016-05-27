package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.PatientListController;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class PatientListView extends BaseView {
    public static final String NAME ="PatientList";
    public static final String VIEW_NAME ="Patientenliste";

    private static final Logger logger = LogManager.getLogger(PatientListView.class);
    private final PatientListController controller;

    

    public PatientListView(){
        super(VIEW_NAME);
        logger.debug("arrived on appointment list view");
        this.controller = new PatientListController(this);
    }

    @Override
    protected Layout initView() {
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
        Label listTitle = new Label("Patienten von ");
        listTitle.setStyleName("h1");

        title.addComponent(listTitle);

        Grid list = new Grid();
        list.setColumns("Patient", "Adresse", "Ort", "Beginn", "Ende");

        // TODO Add patients and appointments from db to the list

        general.addComponents(topLeft, title, list);
        list.addSelectionListener((clickEvent ->
                getUI().getNavigator().navigateTo(AppointmentDetailView.NAME)));


        return general;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //TODO set a focus
    }
}
