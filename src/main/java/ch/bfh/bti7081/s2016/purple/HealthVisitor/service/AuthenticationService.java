package ch.bfh.bti7081.s2016.purple.HealthVisitor.service;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.HealthVisitorDao;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.UI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.soap.SOAPBinding;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class AuthenticationService {
    private static Logger logger = LogManager.getLogger(AuthenticationService.class);
    private HealthVisitorEntity user;
    private VaadinSession session;
    private WrappedSession httpSession;
    private static String USER = "user";

    public AuthenticationService(UI ui) {
        this.httpSession = VaadinService.getCurrentRequest().getWrappedSession();
        this.session = ui.getSession();
        this.user = (HealthVisitorEntity) httpSession.getAttribute(USER);
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
        session.setAttribute(USER, null);
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
        this.httpSession.setAttribute(USER, user);
        this.session.setAttribute(USER, user);
    }
}
