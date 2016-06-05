package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;

public class MedicationDao extends GenericDao<MedicationEntity, Integer> {

    private static MedicationDao instance;

	public static MedicationDao getInstance(){
		if (instance == null){
			instance = new MedicationDao();
		}
		return instance;
	}
}
