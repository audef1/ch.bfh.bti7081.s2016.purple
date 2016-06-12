package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
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
	
	public Collection<MedicationEntity> findAllByClient(ClientEntity client){
		// Use native query for joining tables more easely
		Query query = entityManager.createNativeQuery(
    		"SELECT m.* "
    		+ "FROM medication AS m "
    		+ "JOIN appointment a ON a.id = m.appointment_id "
    		+ "WHERE a.client_id = '" + client.getId() + "'",
    		MedicationEntity.class
        );

    	Collection<MedicationEntity> medications = null;
    	try {
    		medications = query.getResultList();
    	} catch (NoResultException e){
    		logger.debug("No medications found for client " + e.getMessage());
    	}
    	return medications;
	}
	
}
