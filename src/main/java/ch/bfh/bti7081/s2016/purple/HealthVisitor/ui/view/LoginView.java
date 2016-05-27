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
public class LoginView extends BaseView{

    private final Logger logger = LogManager.getLogger(LoginView.class);
    public static final String NAME ="Login";
    private LoginController controller;
    private TextField user;
    private PasswordField password;
    private Button magicLoginButton;
    private Panel loginpanel;
    
    public LoginView(){
        super(NAME);
        controller = new LoginController(this);
        logger.debug("this is the loginview");
    }

    @Override
    protected Layout initView() {
    	    	
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
        viewLayout.setCaption("Ready to login?");
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);

        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        return viewLayout;
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
        
        if(controller.authenticate(userMail, userPassword))
            getUI().getNavigator().navigateTo(DashboardView.NAME);

    }
}
