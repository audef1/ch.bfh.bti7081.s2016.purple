package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.AuthenticationService;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class HeaderComponent extends HorizontalLayout {


    public HeaderComponent(BaseView view) {

        NavigationButton logout = new NavigationButton("Logout", "Logout", FontAwesome.SIGN_OUT);
        NavigationButton home = new NavigationButton("", "Dashboard", FontAwesome.HOME);

        home.setStyleName("v-button-primary");
        logout.setStyleName("v-button-danger");
        
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
        //if (view.getViewName().equals("Login") || view.getViewName().equals("Logout")){
        	
        	if (!view.getViewName().equals("Dashboard")){
        		buttons.addComponent(home);
        	}
        	
        	if (null !=  VaadinSession.getCurrent().getSession().getAttribute("oldview")){
        		String oldview = (String) VaadinSession.getCurrent().getSession().getAttribute("oldview");
                NavigationButton back = new NavigationButton("zurück", oldview, FontAwesome.BACKWARD);
        		buttons.addComponent(back);
            	VaadinSession.getCurrent().getSession().setAttribute("oldview", null);
        	}
        	
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
