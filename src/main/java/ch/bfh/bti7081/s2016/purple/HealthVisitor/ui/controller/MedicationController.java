package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import java.util.ArrayList;
import java.util.Collection;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.MedicationDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.MedicationView;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class MedicationController extends BaseController {
	
	private MedicationDao medicationDao = MedicationDao.getInstance();
	private AppointmentDao appointmentDao = AppointmentDao.getInstance();
	
    public MedicationController(MedicationView view) {
        super(view);
    }
    
    public Collection<MedicationEntity> getMedicationForDay(){
		Collection<AppointmentEntity> appointments = appointmentDao.getTodaysAppointmentsByHealthVisitor(this.getUser());
		Collection<MedicationEntity> medications = new ArrayList<>();
		for (AppointmentEntity appointment : appointments){
			medications.addAll(appointment.getMedications());
		}
    	return medications;
    }
    
    public void check(Collection<MedicationEntity> medications){
    	for (MedicationEntity medication: medications){
    		medication.setChecked(true);
    		medicationDao.update(medication);
    	}
    }
    public void uncheck(Collection<MedicationEntity> medications){
    	for (MedicationEntity medication: medications){
    		medication.setChecked(false);
    		medicationDao.update(medication);
    	} 	
    }
}
