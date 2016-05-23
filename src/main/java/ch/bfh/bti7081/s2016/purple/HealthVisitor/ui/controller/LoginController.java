package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.HealthVisitorDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.LoginView;
import com.vaadin.addon.jpacontainer.JPAContainer;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Created by tgdflto1 on 20/05/16.
 */
public class LoginController extends BaseController{

    private final LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
    }

    public HealthVisitorEntity authenticate(String email, String password) {
        HealthVisitorEntity hve = new HealthVisitorDao().findByEmail(email);
        if (hve == null)
            return null;
        if (hve.getEmail().equals(email) && hve.getPassword().equals(password)) {
            setUser(hve);
            return hve;
        }
        return null;
    }
}
