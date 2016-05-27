package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class FooterComponent extends HorizontalLayout {
    public FooterComponent(){
        Label copyright = new Label("© Copyright by Raphael Suter, Florian Auderset, Remo Häusler, Mathias Rudolf, Tobias Flühmann");
        this.addComponent(copyright);
        this.setStyleName("footer");
        this.setMargin(new MarginInfo(true, false, true, false));
        this.setComponentAlignment(copyright, Alignment.BOTTOM_CENTER);
    }
}
