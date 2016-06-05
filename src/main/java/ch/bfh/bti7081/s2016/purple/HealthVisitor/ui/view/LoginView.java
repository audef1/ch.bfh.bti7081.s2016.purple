package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.validator.PasswordValidator;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.SessionController;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class LoginView extends BaseView{

    public static final String USERNAME = "Benutzername";
    public static final String EMAIL = "E-Mail";
    public static final String ERROR_EMAIL = "Bitte geben Sie eine gültige E-Mail Adresse ein!";
    public static final String PASSWORD = "Kennwort";
    public static final String LOGIN = "Login";
    public static final String WRONG_CREDENTIALS = "Login oder Kennwort falsch";
    public static final String THREE_HUNDRED_PX = "300px";


    private final Logger logger = LogManager.getLogger(LoginView.class);
    public static final String VIEW_NAME = "Login";
    public static final String NAME = "Login";
    private  SessionController controller;
    private  TextField user;
    private  PasswordField password;
    private  Button magicLoginButton;
    private Panel loginpanel;


    public LoginView(){
        super();
        controller = new SessionController(this);
        logger.debug("this is the loginview");
        layout = new StandardLayout(this);
    }

    @Override
    public Layout initView() {

        user = new TextField("");
        user.setWidth(THREE_HUNDRED_PX);
        user.setRequired(true);
        user.setCaption(USERNAME);
        user.setInputPrompt(EMAIL);
        user.addValidator(new EmailValidator(ERROR_EMAIL));
        user.setInvalidAllowed(false);

        password = new PasswordField("");
        password.setWidth(THREE_HUNDRED_PX);
        password.setRequired(true);
        password.setCaption(PASSWORD);
        password.addValidator(new PasswordValidator());
        password.setInvalidAllowed(true);

        magicLoginButton = new Button(LOGIN);
        magicLoginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        magicLoginButton.addClickListener((click -> validate()));

        // The Layout for the Login Form
    	loginpanel = new Panel("");
        FormLayout content = new FormLayout();
        content.addStyleName("loginpanel");
        content.addComponent(user);
        content.addComponent(password);
        content.addComponent(magicLoginButton);
        content.setSizeUndefined(); // Shrink to fit
        content.setMargin(true);
        loginpanel.setContent(content);

        // The Layout for the Login-Part
        VerticalLayout fields = new VerticalLayout(loginpanel);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setSpacing(true);
        viewLayout.setMargin(new MarginInfo(true, true, true, true));
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        
        return viewLayout;
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
        user.focus();
    }

    private void validate(){
        Notification.show("received", Notification.Type.ASSISTIVE_NOTIFICATION);
        if(!password.isValid() || !user.isValid()){
            Notification.show(WRONG_CREDENTIALS,
                    Notification.Type.WARNING_MESSAGE);
            return;
        }
        String userMail = user.getValue();
        String userPassword = password.getValue();
        logger.debug("password is: "+userPassword);
        logger.debug("username is: "+ userMail);
        if(controller.authenticate(userMail, userPassword))
            getUI().getNavigator().navigateTo(DashboardView.NAME);

    }
}
