package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ClientDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.PatientDetailView;

public class PatientDetailController extends BaseController {
	private static final Logger logger = LogManager.getLogger(AppointmentDetailController.class);

	private AppointmentDao appointments = AppointmentDao.getInstance();
	
	public PatientDetailController(PatientDetailView view) {
		super(view);
	}

	public AppointmentEntity getCurrentAppointment() {
		return AppointmentDao.getInstance().getCurrentAppointment();
	}

	public void saveDetails(Button button, ClientEntity patient, String description) {
		patient.setDetails(description);
		new ClientDao().update(patient);
		button.setDescription("Gespeichert");
	}
	
	public Collection<AppointmentEntity> getAppointments(ClientEntity patient){
		return appointments.getAppointmentsByPatient(patient);
	}
}
