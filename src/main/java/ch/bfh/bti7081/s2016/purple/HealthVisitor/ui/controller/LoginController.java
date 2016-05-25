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


    public LoginController(LoginView view) {
        super(view);
    }

    public boolean authenticate(String userMail, String userPassword) {
         return super.authService.authenticate(userMail, userPassword);
    }
}
