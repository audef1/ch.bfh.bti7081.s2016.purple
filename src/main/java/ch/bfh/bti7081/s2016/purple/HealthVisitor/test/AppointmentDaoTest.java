package ch.bfh.bti7081.s2016.purple.HealthVisitor.test;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import junit.framework.TestCase;

import java.util.List;

/**
 * Created by tgdflto1 on 03/06/16.
 */
public class AppointmentDaoTest extends TestCase {


    public void magic(){

    }

    public void testGetAppointments() throws Exception {
        AppointmentDao ad = AppointmentDao.getInstance();
        assertTrue(ad.getAppointments() instanceof List);
    }

    public void testGetTodaysAppointmentsByHealthVisitor() throws Exception {

    }

    public void testGetCurrentAppointment() throws Exception {

    }

    public void testGetCurrentAppointment1() throws Exception {

    }
}