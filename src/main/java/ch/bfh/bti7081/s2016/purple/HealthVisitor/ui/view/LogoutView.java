package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.SessionController;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 27/05/16.
 */
public class LogoutView extends BaseView {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3007272125972318403L;
	public static final String SEE_YOU_SOON = "Vielen Dank und bis zum nächsten mal.";
    public static final String BACK_TO_LOGIN = "Zurück zum Login";
    private final Logger logger = LogManager.getLogger(LoginView.class);
    	public static final String VIEW_NAME ="Logout";
    	public static final String NAME ="Logout";

    public LogoutView() {
            super();
            logger.debug("this is the loginview");
            layout = new StandardLayout(this);
        new SessionController(this).invalidate();
        }

        @Override
        public Layout initView() {
            VerticalLayout hl = new VerticalLayout();
            hl.setMargin(true);

            Label label = new Label(SEE_YOU_SOON);
            label.setStyleName("h1");
            Button button = new Button(BACK_TO_LOGIN);
            button.addClickListener(click -> getUI().getNavigator().navigateTo("Login"));
            hl.addComponent(label);
            hl.addComponent(button);
            return hl;
        }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }
    
    @Override
	public String getName() {
		return NAME;
	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

	
}
