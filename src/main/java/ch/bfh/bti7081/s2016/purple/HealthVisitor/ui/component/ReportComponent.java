package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ReportEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentDetailView;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Locale;

/**
 * Created by tgdflto1 on 01/06/16.
 */
public class ReportComponent extends CustomComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5373128523929612097L;
	@SuppressWarnings("unused")
	private final static Logger logger = LogManager.getLogger(ReportComponent.class);
    private ReportEntity report;
    private AppointmentEntity appointment;
    private AppointmentDetailController controller;

    public ReportComponent(AppointmentEntity appointment, ReportEntity report, AppointmentDetailController controller){
        this.controller = controller;
        this.appointment = appointment;
        this.report = report;
        initialize();
    }

    private void initialize() {
        String datePattern = "dd.MM.yyyy HH:mm";
        Locale swissGerman = new Locale("de", "CH");

        final Window window = new Window("Rapport bearbeiten");
        window.setWidth("90%");
        window.setModal(true);
        final FormLayout content = new FormLayout();
        content.setMargin(true);

        Date startDate = new Date(report.getStart());
        DateField arrival = new DateField();
        arrival.setValue(startDate);
        arrival.setCaption("Behandlungsbeginn:");
        arrival.setDateFormat(datePattern);
		arrival.setLocale(swissGerman);
        arrival.setResolution(Resolution.MINUTE);
        content.addComponent(arrival);

        Date endDate = new Date(report.getEnd());
        DateField end = new DateField();
        end.setValue(endDate);
        end.setCaption("Ende der Behandlung");
        end.setDateFormat(datePattern);
		end.setLocale(swissGerman);
        end.setResolution(Resolution.MINUTE);
        end.addValidator(new DateRangeValidator("Ende muss nach dem start liegen", arrival.getValue(), arrival.getRangeEnd(), Resolution.DAY));
        content.addComponent(end);

        RichTextArea text = new RichTextArea();
        text.setDescription("aktueller Rapport");
        if(report.getDescription() != null) text.setValue(report.getDescription());
        text.setImmediate(true);
        text.setSizeFull();
        text.addValidator(new StringLengthValidator("Bitte geben Sie einen Text ein", 10, Integer.MAX_VALUE, false));
        content.addComponent(text);

        Button btnSave = new Button("Speichern", FontAwesome.SAVE);
        btnSave.addClickListener(clickevent -> {
            controller.save(arrival, end, text, appointment, report);
            window.close();
            UI.getCurrent().getNavigator().navigateTo(AppointmentDetailView.NAME);
        });
        content.addComponent(btnSave);

        window.setContent(content);
        UI.getCurrent().addWindow(window);
        
        window.center();
    }
}
