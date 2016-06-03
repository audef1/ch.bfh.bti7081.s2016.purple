package ch.bfh.bti7081.s2016.purple.HealthVisitor.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.server.VaadinSession;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.HealthVisitorDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class AuthenticationService {
    private static Logger logger = LogManager.getLogger(AuthenticationService.class);
    private HealthVisitorEntity user;
    private VaadinSession session;

    private static String SESSION_KEY = "user";

    public AuthenticationService() {
        this.session = VaadinSession.getCurrent();
        this.user = (HealthVisitorEntity) session.getAttribute(SESSION_KEY);
    }

    public boolean authenticate(String email, String password) {
        HealthVisitorEntity hve = new HealthVisitorDao().findByEmail(email);
        if (hve == null)
            return false;
        if (hve.getEmail().equals(email) && hve.getPassword().equals(password)) {
            setUser(hve);
            return true;
        }
        return false;
    }

    public void invalidate(){
        session.setAttribute(SESSION_KEY, null);
    }

    public boolean isAuthenticated(){
        return this.user != null;
    }

    public HealthVisitorEntity getUser() {
        logger.debug("user returned: "+ user.hashCode());
        return user;
    }

    private void setUser(HealthVisitorEntity user) {
        logger.debug("user set: "+ user);
        this.user = user;
        this.session.setAttribute(SESSION_KEY, user);
    }
}
