package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import java.util.Collection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.RichTextArea;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ClientDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ContactDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ReportDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.TaskDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ContactEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ReportEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.TaskEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentDetailView;

public class AppointmentDetailController extends BaseController {

	private static final Logger logger = LogManager.getLogger(AppointmentDetailController.class);
	private TaskDao taskDao = TaskDao.getInstance();
	public AppointmentDetailController(AppointmentDetailView view) {
		super(view);
	}

	public AppointmentEntity getCurrentAppointment() {
		return AppointmentDao.getInstance().getCurrentAppointment();
	}
	
	public Collection<ContactEntity> getContactsByClient(ClientEntity client){
		return ContactDao.getInstance().getContactsByClient(client);
	}

	public void saveDetails(Button button, AppointmentEntity appointmentEntity, String description) {
		ClientEntity client = appointmentEntity.getClient();
		logger.debug("setting details for " + client.getFullName() + " to: " + description);
		client.setDetails(description);
		new ClientDao().update(client);
		button.setDescription("Gespeichert.");
	}

	public void save(DateField arrival, DateField end, RichTextArea text, AppointmentEntity appointment,
			ReportEntity report) {
		logger.debug("written text is: " + text.getValue());
		logger.debug(arrival.getValue());
		logger.debug(end.getValue());
		boolean existing = true;
		if (report == null) {
			existing = false;
			report = new ReportEntity();
		}
		report.setAppointment(appointment);
		Date arrivalDate = arrival.getValue();
		logger.debug("saving arrival to db: " + arrivalDate.toString());
		report.setDescription(text.getValue());
		report.setStart(arrivalDate.getTime());
		Date leaveDate = end.getValue();
		report.setEnd(leaveDate.getTime());
		ReportDao reportDao = ReportDao.getInstance();
		if (existing)
			reportDao.update(report);
		else
			reportDao.persist(report);
	}

	public void saveReportTime(ReportEntity report, AppointmentEntity appointment, Button button) {
		ReportDao dao = ReportDao.getInstance();
		if (report != null) {
			if (button.getCaption().equals("Ankunft bestätigen")) {
				report.setStart(new Date(System.currentTimeMillis()).getTime());
				report.setEnd(new Date(System.currentTimeMillis()).getTime());
			} else
				report.setEnd(new Date(System.currentTimeMillis()).getTime());
			dao.update(report);
		} else {
			report = new ReportEntity();
			report.setAppointment(appointment);
			report.setStart(new Date(System.currentTimeMillis()).getTime());
			dao.persist(report);
		}

	}

	public void checkTask(Collection<TaskEntity> tasks) {
    	for (TaskEntity task: tasks){
    		task.setChecked(true);
    		taskDao.update(task);
    	}
	}

	public void uncheckTask(Collection<TaskEntity> tasks) {
    	for (TaskEntity task: tasks){
    		task.setChecked(false);
    		taskDao.update(task);
    	} 	
	}
}
