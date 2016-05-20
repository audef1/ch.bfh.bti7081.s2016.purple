package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public abstract class BaseController {
    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    protected String userMail;
}
