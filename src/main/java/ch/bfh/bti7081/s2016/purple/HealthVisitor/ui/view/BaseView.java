package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.FooterComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.HeaderComponent;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public abstract class BaseView extends CustomComponent implements View {
	protected VerticalLayout vl;

	public BaseView(String pageName) {
		vl = new VerticalLayout();
		vl.setSizeFull();
		vl.addComponent(new HeaderComponent(pageName)); // Header
		vl.addComponent(initView()); // Custom content
		vl.addComponent(new FooterComponent()); // Footer
		setCompositionRoot(vl);

	}

	protected abstract Layout initView();

}
