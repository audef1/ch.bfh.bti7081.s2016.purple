package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentListView;

import java.util.List;

public class AppointmentListController extends BaseController {
//	private final List<AppointmentEntity> appointments;
    public AppointmentListController(AppointmentListView view){
        super(view);
//        this.appointments = getUser().getAppointments();

    }

    public List<AppointmentEntity> getAppointments() {
        return AppointmentDao.getInstance().getAppointments();
    }
}
