package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.MedicationEntity;

public class MedicationDao implements Dao<MedicationEntity, Integer> {

	private static Logger logger = LogManager.getLogger(MedicationDao.class);
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);
    private final EntityManager entityManager;
    
    private static MedicationDao instance;
    
	public MedicationDao() {
		entityManager = factory.createEntityManager();
	}
	
	public static MedicationDao getInstance(){
		if (instance == null){
			instance = new MedicationDao();
		}
		return instance;
	}

	@Override
	public void persist(MedicationEntity entity) {
		logger.debug("Persist medication " + entity);
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
	}

	@Override
	public void remove(MedicationEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MedicationEntity findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
