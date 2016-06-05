package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import java.util.List;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.AppointmentListView;

public class AppointmentListController extends BaseController {
//	private final List<AppointmentEntity> appointments;
    public AppointmentListController(AppointmentListView view){
        super(view);
//        this.appointments = getUser().getAppointments();

    }

    @SuppressWarnings("rawtypes")
	public List getAppointments() {
        return AppointmentDao.getInstance().getAppointments();
    }
}
