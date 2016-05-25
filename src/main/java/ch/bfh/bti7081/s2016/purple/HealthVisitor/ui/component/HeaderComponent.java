package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class HeaderComponent extends HorizontalLayout {
    public HeaderComponent(String pageName){
        this.setWidth("100%");
        this.setSizeFull();
        this.setWidth("100%");
        this.setMargin(new MarginInfo(false, true, false, false));
        ThemeResource resource = new ThemeResource("images/Logo_HealthVisitor.png");
        Image logo = new Image("Logo", resource);
        logo.setWidth("300px");
        logo.setCaption("");
        this.addComponent(new Label(pageName));
        this.addComponent(logo);
        this.setComponentAlignment(logo, Alignment.TOP_RIGHT);
    }
}
