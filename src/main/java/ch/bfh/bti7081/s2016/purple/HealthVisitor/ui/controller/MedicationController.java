package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.vaadin.data.util.BeanItem;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.MedicationDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.MedicationView;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class MedicationController extends BaseController {
	
	private MedicationDao medicationDao = MedicationDao.getInstance();
	
    public MedicationController(MedicationView view) {
        super(view);
    }
    
    public Collection<MedicationEntity> getMedicationForDay(){
		HealthVisitorEntity user = this.getUser();
		List<AppointmentEntity> appointments = user.getAppointments();
		Collection<MedicationEntity> medications = new ArrayList<MedicationEntity>();
		for (AppointmentEntity appointment : appointments){
			medications.addAll(appointment.getMedications());
		}
    	return medications;
    }
    
    public void check(Collection<MedicationEntity> medications){
    	for (MedicationEntity medication: medications){
    		medication.setChecked(true);
    		medicationDao.persist(medication);
    	}
    }
    public void uncheck(Collection<MedicationEntity> medications){
    	for (MedicationEntity medication: medications){
    		medication.setChecked(false);
    		medicationDao.persist(medication);
    	} 	
    }
}
