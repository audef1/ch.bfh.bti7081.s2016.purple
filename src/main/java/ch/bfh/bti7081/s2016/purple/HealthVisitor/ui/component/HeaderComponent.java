package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class HeaderComponent extends HorizontalLayout {


    public HeaderComponent(BaseView view) {

        NavigationButton logout = new NavigationButton("Logout", "Logout", FontAwesome.SIGN_OUT);
        NavigationButton back = new NavigationButton("zurück", "Dashboard", FontAwesome.BACKWARD);
        NavigationButton home = new NavigationButton("", "Dashboard", FontAwesome.HOME);

        Label pagename = new Label(view.getViewName());
        Image logo = new Image("Logo", new ThemeResource("images/Logo_HealthVisitor.png"));
        HorizontalLayout buttons = new HorizontalLayout();
        
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
            Label loggedin = new Label("angemeldet als: " + hv.getFullName());
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
