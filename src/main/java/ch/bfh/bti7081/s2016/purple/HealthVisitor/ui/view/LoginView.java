package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.validator.PasswordValidator;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.LoginController;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.themes.Reindeer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class LoginView extends CustomComponent implements View{

    private final Logger logger = LogManager.getLogger(LoginView.class);
    public static final String NAME ="Login";
    private final LoginController controller;
    private final TextField user;
    private final PasswordField password;
    private final Button magicLoginButton;
    
    public LoginView(){
        controller = new LoginController(this);
        logger.debug("this is the loginview");
        
        ThemeResource resource = new ThemeResource("images/Logo_HealthVisitor.png");
        Image logo = new Image("Logo", resource);
        logo.setWidth("300px");
        logo.setCaption("");
        
        Label lblHeader = new Label("Login");
        lblHeader.setStyleName("header");
        
        user = new TextField("");
        user.setWidth("300px");
        user.setRequired(true);
        user.setCaption("Benutzername");
        user.setInputPrompt("E-Mail");
        user.addValidator(new EmailValidator("Bitte geben Sie eine gültige E-Mail Adresse ein!"));
        user.setInvalidAllowed(false);

        password = new PasswordField("");
        password.setWidth("300px");
        password.setRequired(true);
        password.setCaption("Kennwort");
        password.addValidator(new PasswordValidator());
        password.setInvalidAllowed(true);

        magicLoginButton = new Button("Login");
        magicLoginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        magicLoginButton.addClickListener((clickEvent -> {validate();}));

        // The Layout for the Logo
        GridLayout top = new GridLayout(2, 1);
        top.setSizeFull();
        top.addComponent(lblHeader, 0, 0);
        top.addComponent(logo, 1, 0);
        top.setComponentAlignment(logo, Alignment.TOP_RIGHT);
        top.setMargin(new MarginInfo(false, false, true, true));
        
        // The Layout for the Login-Part
        VerticalLayout fields = new VerticalLayout(user, password, magicLoginButton);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(top, fields);
        viewLayout.setSizeFull();
        viewLayout.setSpacing(true);
        viewLayout.setMargin(new MarginInfo(true, true, true, true));
        viewLayout.setCaption("Ready to login?");
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        user.focus();
    }


    private void validate(){
        Notification.show("received", Notification.Type.ASSISTIVE_NOTIFICATION);
        if(!password.isValid() || !user.isValid()){
            Notification.show("Login oder Kennwort falsch",
                    Notification.Type.WARNING_MESSAGE);
            return;
        }
        String userMail = user.getValue();
        String userPassword = password.getValue();
        logger.debug("password is: "+userPassword);
        logger.debug("username is: "+ userMail);
        HealthVisitorEntity hve = controller.authenticate(userMail, userPassword);
        if(hve != null){
            getSession().setAttribute("userMail", userMail);
            password.setValue("");
            logger.debug("both values ok, redirecting to dashboard");
            getUI().getNavigator().navigateTo(DashboardView.NAME);
        }
    }
}
