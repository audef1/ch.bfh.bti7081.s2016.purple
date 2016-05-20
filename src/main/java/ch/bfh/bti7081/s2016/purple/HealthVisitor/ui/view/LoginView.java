package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.LoginController;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class LoginView extends CustomComponent implements View, Button.ClickListener {

    private final Logger logger = LogManager.getLogger(LoginView.class);
    public static final String NAME ="";
    private final LoginController controller;
    private final TextField user;
    public LoginView(){
        controller = new LoginController(this);
        logger.debug("this is the loginview");

        user = new TextField("");
        user.setWidth("300px");
        user.setRequired(true);
        user.setInputPrompt("Your email (eg. foo@bar.com)");
        user.addValidator(new EmailValidator("Please enter a valid email"));
        user.setInvalidAllowed(false);



        VerticalLayout fields = new VerticalLayout(user);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

    }
}
