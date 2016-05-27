package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.*;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public abstract class BaseController {
    protected AuthenticationService authService;
    protected AppointmentService appointmentService;
    protected HealthVisitorEntity user;
    protected BaseView view;
    private static final Logger logger = LogManager.getLogger(BaseController.class);

    public BaseController(BaseView view) {
        this.view = view;
        logger.debug("setting user");
        authService = new AuthenticationService();
        appointmentService = new AppointmentService();
        if(authService.isAuthenticated()) user = authService.getUser();

    }

    public HealthVisitorEntity getUser(){
        return getAuthService().getUser();
    }

    public AuthenticationService getAuthService() {
        return authService;
    }
    
    public AppointmentService getAppointmentService(){
    	return appointmentService;
    }
}
