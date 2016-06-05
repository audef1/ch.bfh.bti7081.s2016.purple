package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.BaseController;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.PatientListController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.FooterComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.HeaderComponent;

/**
 * Created by tgdflto1 on 25/05/16.
 */
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
		return controller;
	}
	
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		
	}
}
