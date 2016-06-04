package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ReportEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tgdflto1 on 31/05/16.
 */
public class ReportDao extends GenericDao<ReportEntity, Integer> {
    @SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(HealthVisitorDao.class);
    private static ReportDao instance;
    
    public static ReportDao getInstance(){
    	if(instance == null) instance = new ReportDao();
    	return instance;
    }
    public ReportDao(){
    	
    }
    
}
