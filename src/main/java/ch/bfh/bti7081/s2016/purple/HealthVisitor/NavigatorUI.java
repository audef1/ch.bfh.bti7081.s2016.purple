package ch.bfh.bti7081.s2016.purple.HealthVisitor;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.Views.DashboardView;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.Views.MainView;

@Theme("mytheme")
@Widgetset("ch.bfh.bti7081.s2016.purple.HealthVisitor.MyAppWidgetset")
public class NavigatorUI extends UI {
	Navigator navigator;
    protected static final String MAINVIEW = "main";
    protected static final String DASHBOARDVIEW = "dashboard";

    
    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView(DASHBOARDVIEW, new DashboardView());
        navigator.addView(MAINVIEW, new MainView());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
