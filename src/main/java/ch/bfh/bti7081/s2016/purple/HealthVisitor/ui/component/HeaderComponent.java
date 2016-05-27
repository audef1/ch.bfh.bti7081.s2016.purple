package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class HeaderComponent extends HorizontalLayout {

    private Label pagename;
    private LogoutButton logout;
    private Image logo;
    private HorizontalLayout buttons;


    public HeaderComponent(String pageName){
       
    	logout =  new LogoutButton();
    	pagename = new Label(pageName);
    	logo = new Image("Logo", new ThemeResource("images/Logo_HealthVisitor.png"));
    	buttons = new HorizontalLayout();
    	
    	this.setWidth("100%");
        this.setSizeFull();
        this.setMargin(new MarginInfo(false, true, false, false));
        
        logo.setWidth("300px");
        logo.setCaption("");
        pagename.addStyleName("pagename");
        buttons.addComponent(logout);
        buttons.setComponentAlignment(logout, Alignment.MIDDLE_LEFT);
        
        this.addComponent(pagename);
        this.addComponent(buttons);
        this.addComponent(logo);
        this.setComponentAlignment(pagename, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);
    }
}
