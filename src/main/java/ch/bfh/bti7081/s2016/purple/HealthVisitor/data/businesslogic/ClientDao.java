package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ClientEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 29/05/16.
 */
public class ClientDao extends GenericDao<ClientEntity, Integer> {
    private static Logger logger = LogManager.getLogger(HealthVisitorDao.class);
}
