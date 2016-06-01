package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.ReportEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by tgdflto1 on 31/05/16.
 */
public class ReportDao extends GenericDao<ReportEntity, Integer> {
    private static Logger logger = LogManager.getLogger(HealthVisitorDao.class);
    private static ReportDao instance;
    
    public static ReportDao getInstance(){
    	if(instance == null) instance = new ReportDao();
    	return instance;
    }
    private ReportDao(){
    	
    }
    
}
