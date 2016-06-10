package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * @author tgdflto1
 */
public class FooterComponent extends HorizontalLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3827164233007717671L;

	public FooterComponent(){
        Label copyright = new Label("© Copyright by Raphael Suter, Florian Auderset, Remo Häusler, Mathias Rudolf, Tobias Flühmann");
        this.addComponent(copyright);
        this.setStyleName("footer");
        this.setMargin(new MarginInfo(true, false, true, false));
        this.setComponentAlignment(copyright, Alignment.BOTTOM_CENTER);
    }
}
