package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Notification;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.LoginView;

public class PasswordValidator implements com.vaadin.data.Validator {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3300213985229801002L;
	
	private final Logger logger = LogManager.getLogger(LoginView.class);
    @Override
    public void validate(Object o) throws InvalidValueException {
        String enteredPassword = (String) o;
        if(enteredPassword.length() > 10){
            logger.debug("password ok");
            Notification.show("Password Correct redirecting now",
                    Notification.Type.ASSISTIVE_NOTIFICATION);

        }else{
            Notification.show("Please enter valid information",
                    Notification.Type.ERROR_MESSAGE);            }
    }
}
