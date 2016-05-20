package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.DashboardController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class DashboardView extends CustomComponent implements View {
    public static final String NAME ="Dashboard";
    private static final Logger logger = LogManager.getLogger(DashboardView.class);
    private final DashboardController controller;

    private final Button exampleButton;
    public DashboardView(){
        logger.debug("arrived on dashboard view");
        controller = new DashboardController(this);
        //TODO add some magic buttons doing almost nothing
        exampleButton = new Button("Hello World");
        exampleButton.setSizeFull();
        exampleButton.setHeight("50px");
        exampleButton.setWidth("300px");
        exampleButton.setDescription("magic");

        VerticalLayout fields = new VerticalLayout(exampleButton);
        fields.setSpacing(true);
        fields.setCaption("hmm... what now");
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setCaption("Ready to login?");
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        //TODO set some focus
        exampleButton.focus();
    }
}
