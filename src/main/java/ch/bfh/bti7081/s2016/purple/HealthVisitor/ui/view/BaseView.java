package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.BaseController;

/**
 * @author tgdflto1 on 25/05/16.
 */

@SuppressWarnings("serial")
public abstract class BaseView extends CustomComponent implements View {

	public BaseController controller;
	public Layout layout;

	public abstract Layout initView();

	public abstract String getViewName();
	
	public abstract String getName();
	
	@Override
	public void setCompositionRoot(Component compositionRoot) {
		super.setCompositionRoot(compositionRoot);
	}

	public BaseController getController() {
		return this.controller;
	}
	
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		
	}
}
