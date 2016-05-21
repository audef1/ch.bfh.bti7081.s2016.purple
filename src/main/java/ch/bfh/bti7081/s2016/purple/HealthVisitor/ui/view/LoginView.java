package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.LoginController;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
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
        
        Label lbl = new Label();
        
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

        VerticalLayout fields = new VerticalLayout(logo, lbl, user, password, magicLoginButton);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setSpacing(true);
        viewLayout.setMargin(true);
        viewLayout.setCaption("Ready to login?");
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_LEFT);
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
        //TODO validate this shit in the db
        if(userMail.length() > 1 && userPassword.length() > 1){
            getSession().setAttribute("userMail", userMail);
            controller.setUserMail(userMail);
            password.setValue("");
            logger.debug("both values ok, redirecting to dashboard");
            getUI().getNavigator().navigateTo(DashboardView.NAME);
        }
    }

    private class PasswordValidator implements com.vaadin.data.Validator {
        @Override
        public void validate(Object o) throws InvalidValueException {
            String enteredPassword = (String) o;
            if(enteredPassword.length() > 10){
                logger.debug("password ok");
                Notification.show("Password Correct redirecting now",
                        Notification.Type.ASSISTIVE_NOTIFICATION);

            }else{
                Notification.show("Please enter valid information",
                        Notification.Type.ERROR_MESSAGE);            }
        }
    }
}
