package ch.bfh.bti7081.s2016.purple.HealthVisitor.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public class AppointmentService {
    private static Logger logger = LogManager.getLogger(AppointmentService.class);

    //TODO: 2016.05.28 gimmie7 - check if this is still used
    public List<AppointmentEntity> getAppointments(){
    	return AppointmentDao.getInstance().getAppointments();
    }
}
