package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import java.util.Collection;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.MedicationDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.MedicationView;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public class MedicationController extends BaseController {
	
	private MedicationDao medicationDao = MedicationDao.getInstance();
	public MedicationController(MedicationView view) {
        super(view);
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
