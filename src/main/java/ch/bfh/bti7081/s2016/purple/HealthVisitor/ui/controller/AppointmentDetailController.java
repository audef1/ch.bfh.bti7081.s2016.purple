package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ClientDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentDetailView;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppointmentDetailController extends BaseController {
	private static final Logger logger = LogManager.getLogger(AppointmentDetailController.class);

	public AppointmentDetailController(AppointmentDetailView view){
		super(view);
	}

	public AppointmentEntity getCurrentAppointment() {
		return new AppointmentDao().getCurrentAppointment();
	}

	public void saveDetails(Button button, AppointmentEntity appointmentEntity, String description) {
		ClientEntity client = appointmentEntity.getClient();
		logger.debug("setting details for " + client.getFullName() + " to: "+description);
		client.setDetails(description);
		new ClientDao().persist(client);
		button.setCaption("saved");
		button.setEnabled(false);
	}
}
