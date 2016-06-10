package ch.bfh.bti7081.s2016.purple.HealthVisitor.service;

import java.util.List;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;

/**
 * @author tgdflto1
 */
public class AppointmentService {

    //TODO: 2016.05.28 gimmie7 - check if this is still used
    @SuppressWarnings("unchecked")
	public List<AppointmentEntity> getAppointments(){
    	return AppointmentDao.getInstance().getAppointments();
    }
}
