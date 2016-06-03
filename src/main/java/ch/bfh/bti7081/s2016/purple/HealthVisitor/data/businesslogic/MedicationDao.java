package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;

public class MedicationDao extends GenericDao<MedicationEntity, Integer> {

	private static Logger logger = LogManager.getLogger(MedicationDao.class);
    
    private static MedicationDao instance;

	public static MedicationDao getInstance(){
		if (instance == null){
			instance = new MedicationDao();
		}
		return instance;
	}
}
