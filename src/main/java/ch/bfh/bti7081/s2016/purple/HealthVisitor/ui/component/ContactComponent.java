package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ContactEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ReportEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentDetailView;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author tgdflto1
 */
public class ContactComponent extends CustomComponent {
	private final static Logger logger = LogManager.getLogger(ContactComponent.class);
    private AppointmentDetailController controller;
    private Set<ContactEntity> contacts;

    public ContactComponent(Collection<ContactEntity> contacts){
    	this.contacts = new HashSet<ContactEntity>(contacts);
        initialize();
    }

    private void initialize() {
        final Window window = new Window("Notfallkontakte");
        window.setWidth("70%");
        window.setHeight("50%");
        window.setModal(true);
        window.setResizable(false);
        window.setDraggable(false);

        final FormLayout content = new FormLayout();
        content.setMargin(true);
       
		final BeanItemContainer<ContactEntity> container = new BeanItemContainer<>(ContactEntity.class, contacts);

		container.removeContainerProperty("id");
		container.removeContainerProperty("password");
		container.removeContainerProperty("responsibleHealthVisitor");
		container.removeContainerProperty("fullName");
		container.removeContainerProperty("clients");
		container.removeContainerProperty("dateOfBirth");
		
		Grid grid = new Grid(container);
		grid.setSizeFull();
		grid.setColumnOrder("lastName", "firstName", "relation", "email", "details");
		grid.sort("lastName");
		grid.getColumn("lastName").setHeaderCaption("Nachname");
		grid.getColumn("firstName").setHeaderCaption("Vorname");
		grid.getColumn("relation").setHeaderCaption("Beziehung");
		grid.getColumn("email").setHeaderCaption("E-Mail Adresse");
		grid.getColumn("details").setHeaderCaption("Mobile Nr.");

		content.addComponents(grid);

        window.setContent(content);
        
        UI.getCurrent().addWindow(window);
        
        window.center();
    }
}
