package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class HeaderComponent extends HorizontalLayout {

    private Label pagename;
    private NavigationButton logout;
    private NavigationButton back;
    private NavigationButton home;
    private Image logo;
    private HorizontalLayout buttons;
    private Label loggedin;


    public HeaderComponent(BaseView view) {

        logout = new NavigationButton("Logout", "Logout", FontAwesome.SIGN_OUT);
        back = new NavigationButton("zurück", "Dashboard", FontAwesome.BACKWARD);
        home = new NavigationButton("", "Dashboard", FontAwesome.HOME);

        pagename = new Label(view.getViewName());
        logo = new Image("Logo", new ThemeResource("images/Logo_HealthVisitor.png"));
        buttons = new HorizontalLayout();
        
        this.setWidth("100%");
        this.setSizeFull();
        this.setMargin(new MarginInfo(false, true, false, false));

        logo.setWidth("300px");
        logo.setCaption("");
        pagename.addStyleName("pagename");
        buttons.setSpacing(true);

        if (new AuthenticationService().isAuthenticated()){
        	buttons.addComponent(home);
            buttons.addComponent(back);
            buttons.addComponent(logout);
            buttons.setComponentAlignment(logout, Alignment.MIDDLE_LEFT);
            AuthenticationService as = new AuthenticationService();
    	    HealthVisitorEntity hv = as.getUser();
    	    loggedin = new Label("angemeldet als: " + hv.getFullName());
            buttons.addComponent(loggedin);
        }
       
        this.addComponent(pagename);
        this.addComponent(buttons);
        this.addComponent(logo);
        this.setComponentAlignment(pagename, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);

    }


}
