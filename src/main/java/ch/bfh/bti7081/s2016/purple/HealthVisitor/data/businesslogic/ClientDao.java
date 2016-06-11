package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;

/**
 * @author tgdflto1
 */
public class ClientDao extends GenericDao<ClientEntity, Integer> {
	private static Logger logger = LogManager.getLogger(AppointmentDao.class);
    private static ClientDao instance;
	
	public static ClientDao getInstance(){
    	if(instance == null) instance = new ClientDao();
    	return instance;
    }
	
    public ClientEntity findById(String id){
    	ClientEntity c = entityManager.find(ClientEntity.class, id);
        entityManager.close();
        return c;
    }
}
