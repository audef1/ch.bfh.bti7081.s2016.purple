package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ReportEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ClientDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ReportDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentDetailView;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppointmentDetailController extends BaseController {
	private static final Logger logger = LogManager.getLogger(AppointmentDetailController.class);

	public AppointmentDetailController(AppointmentDetailView view) {
		super(view);
	}

	public AppointmentEntity getCurrentAppointment() {
		return new AppointmentDao().getCurrentAppointment();
	}

	public void saveDetails(Button button, AppointmentEntity appointmentEntity, String description) {
		ClientEntity client = appointmentEntity.getClient();
		logger.debug("setting details for " + client.getFullName() + " to: " + description);
		client.setDetails(description);
		new ClientDao().persist(client);
		button.setCaption("saved");
		button.setEnabled(false);
	}

	public void save(DateField arrival, DateField end, RichTextArea text, AppointmentEntity appointment) {
		logger.debug("written text is: " + text.getValue());
		logger.debug(arrival.getValue());
		logger.debug(end.getValue());
		ReportEntity report = new ReportEntity();
		report.setAppointment(appointment);
		report.setDescription(text.getValue());
		report.setStart(arrival.getValue());
		report.setEnd(end.getValue());
		new ReportDao().persist(report);
	}

	public void saveReportTime(ReportEntity report, AppointmentEntity appointment) {
		ReportDao dao = new ReportDao();
		if (report != null) {
			report.setEnd(new Date(System.currentTimeMillis() / 1000));
			dao.update(report);
		} else {
			report = new ReportEntity();
			report.setAppointment(appointment);
			report.setStart(new Date(System.currentTimeMillis() / 1000));
			dao.persist(report);
		}
		
	}
}
