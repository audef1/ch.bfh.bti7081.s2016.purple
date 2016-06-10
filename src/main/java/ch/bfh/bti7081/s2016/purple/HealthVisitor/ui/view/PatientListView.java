package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.StandardLayout;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.PatientListController;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * @author tgdflto1
 */
public class PatientListView extends BaseView {

	public static final String NAME = "PatientList";
	public static final String VIEW_NAME = "Patientenliste";

	private static final Logger logger = LogManager.getLogger(PatientListView.class);

	public PatientListView() {
		super();
		this.controller = new PatientListController(this);
		logger.debug("arrived on appointment list view");
		layout = new StandardLayout(this);
	}

	@Override
	public Layout initView() {
		VerticalLayout general = new VerticalLayout();
		general.setSpacing(true);
		general.setMargin(true);

		Label listTitle = new Label("Patienten von " + controller.getUser().getFullName());
		listTitle.setStyleName("h1");
		
		HealthVisitorEntity user = controller.getUser();
		Set<ClientEntity> clients = user.getClients();
		final BeanItemContainer<ClientEntity> container = new BeanItemContainer<>(ClientEntity.class, clients);

		container.removeContainerProperty("details");
		container.removeContainerProperty("id");
		container.removeContainerProperty("password");
		container.removeContainerProperty("responsibleHealthVisitor");
		container.removeContainerProperty("fullName");
		
		Grid grid = new Grid(container);
		grid.setSizeFull();
		grid.setColumnOrder("lastName", "firstName", "email");
		grid.sort("lastName");
		grid.getColumn("lastName").setHeaderCaption("Nachname");
		grid.getColumn("firstName").setHeaderCaption("Vorname");
		grid.getColumn("email").setHeaderCaption("E-Mail Adresse");
		grid.getColumn("dateOfBirth").setHeaderCaption("Geburtsdatum");
		
//		Shows all available Property-IDs for the Client Entity. Only for selection of removements.
//		logger.debug("ContainerPropertys:" + container.getContainerPropertyIds());

		general.addComponents(listTitle, grid);
		
		grid.addSelectionListener((clickEvent -> {
			// set back button
			BaseView currentView = (BaseView) getUI().getNavigator().getCurrentView();
			VaadinSession.getCurrent().getSession().setAttribute("oldview", currentView.getName());
			
			// handover patient data to detail view 
			ClientEntity patient = container.getItem(grid.getSelectedRow()).getBean();
			VaadinSession.getCurrent().getSession().setAttribute("patient", patient);
			
			// forward to detaiview
			getUI().getNavigator().navigateTo(PatientDetailView.NAME);
		}));

		return general;
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
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO set a focus
	}

}
