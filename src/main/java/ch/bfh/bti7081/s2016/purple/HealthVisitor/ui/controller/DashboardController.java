package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.DashboardView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class DashboardController extends BaseController {
    private  DashboardView dashboardView;
    public DashboardController(DashboardView dv){
        this.dashboardView = dv;
    }

}
