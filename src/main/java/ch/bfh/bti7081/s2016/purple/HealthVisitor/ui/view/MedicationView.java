package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.MedicationController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
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

        return general;
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
