package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.BaseView;

/**
 * Created by tgdflto1 on 27/05/16.
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
