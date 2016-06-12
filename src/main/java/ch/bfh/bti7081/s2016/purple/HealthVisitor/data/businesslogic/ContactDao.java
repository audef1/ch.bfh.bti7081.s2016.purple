package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ContactEntity;

/**
 * @author gimmie7
 */
public class ContactDao extends GenericDao<ContactEntity, Integer> {
	private static Logger logger = LogManager.getLogger(AppointmentDao.class);
    private static ContactDao instance;
	
	public static ContactDao getInstance(){
    	if(instance == null) instance = new ContactDao();
    	return instance;
    }


	/**
	 * @param client
	 * @return a collection of contacts
	 */
	@SuppressWarnings("unchecked")
	public Collection<ContactEntity> getContactsByClient(ClientEntity client) {
		Collection<ContactEntity> list = null;
		
		try {
			//hint: joins with typed query suck, that's why native query is used here.
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT p.* FROM PERSON p ");
			sb.append("INNER JOIN CLI_CON c ON p.ID = c.CON_ID ");
			sb.append("WHERE p.TYPE = 'C' ");
			sb.append("AND c.CLI_ID = ");
			sb.append(client.getId());

			Query query = entityManager.
					createNativeQuery(sb.toString(),
							ContactEntity.class);
			
			list = query.getResultList();

    	} catch (NoResultException e){
    		logger.debug("No contacts found for this client." + e.getMessage());
    	}
		
		return list;
    }
}
