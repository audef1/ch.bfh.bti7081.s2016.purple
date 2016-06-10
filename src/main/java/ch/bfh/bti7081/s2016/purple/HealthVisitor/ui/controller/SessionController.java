package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;

/**
 * @author tgdflto1
 */
public class SessionController extends BaseController {


    public SessionController(BaseView view) {
        super(view);
    }

    public boolean authenticate(String userMail, String userPassword) {
        return super.authService.authenticate(userMail, userPassword);
    }

    public void invalidate() {
        super.authService.invalidate();
    }
}
