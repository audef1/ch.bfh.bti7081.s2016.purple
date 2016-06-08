package ch.bfh.bti7081.s2016.purple.HealthVisitor.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.MedicationView;

public class MedicationService {

	private static MedicationService instance;
    private static final Logger logger = LogManager.getLogger(MedicationService.class);
	
	private AppointmentDao appointmentDao = AppointmentDao.getInstance();
	
	public static MedicationService getInstance(){
		if (instance == null){
			instance = new MedicationService();
		}
		return instance;
	}
	
	public Collection<MedicationEntity> getMedicationForDay(HealthVisitorEntity healthVisitor){
		Collection<AppointmentEntity> appointments = appointmentDao.getTodaysAppointmentsByHealthVisitor(healthVisitor);
		Map<String, MedicationEntity> medications = new HashMap<String, MedicationEntity>();
		for (AppointmentEntity appointment : appointments){
			for (MedicationEntity medication : appointment.getMedications()){
				String hash = medication.hash();
				MedicationEntity m = medications.get(hash);
				if (m != null){
					m.setAmount(m.getAmount() + medication.getAmount());	
				} else {
					try {
						m = medication.clone();
					} catch (CloneNotSupportedException e) {
						// Todo
						logger.error("Could not clone medication entitiy!");
					}
					medications.put(hash, m);
				}
			}
		}
    	return medications.values();
	}

}
