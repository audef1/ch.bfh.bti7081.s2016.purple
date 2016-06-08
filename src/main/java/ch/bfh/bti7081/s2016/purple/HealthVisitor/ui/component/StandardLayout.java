package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by tgdflto1 on 27/05/16.
 */
public class StandardLayout extends VerticalLayout{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9130035755533241915L;

	public StandardLayout(BaseView view){
        this.setSizeFull();
        this.setSpacing(true);
        this.addComponent(new HeaderComponent(view)); // Header
        this.addComponent(view.initView()); // Custom content
        this.addComponent(new FooterComponent()); // Footer
        view.setCompositionRoot(this);

    }
}
