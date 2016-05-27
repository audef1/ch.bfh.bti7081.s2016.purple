package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;

/**
 * Created by tgdflto1 on 26/05/16.
 */
class LogoutButton extends Button {

    public LogoutButton(){
        super("Logout");
        this.setData("Logout");
        this.addClickListener(click -> {getUI().getNavigator().navigateTo("logout");});

    }

}
