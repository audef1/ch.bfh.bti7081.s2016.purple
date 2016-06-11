package ch.bfh.bti7081.s2016.purple.HealthVisitor.test;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ContactDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ClientDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ClientEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ContactEntity;
import junit.framework.TestCase;

import java.util.Collection;

/**
 * Created by gimmie7 on 08/06/16.
 */
public class ContactDaoTest extends TestCase {

    public void testGetContactsByClientPositiveCase() throws Exception {
    	ClientEntity client = ClientDao.getInstance().findById(55);
    	assertNotNull(client);
    	
        Collection<ContactEntity> list = ContactDao.getInstance().getContactsByClient(client);
        assertNotNull(list);
        assertEquals(3, list.size());
    }
    
    public void testGetContactsByClientNegativeCase() throws Exception {
    	ClientEntity client = ClientDao.getInstance().findById(893);
    	assertNotNull(client);
    	
        Collection<ContactEntity> list = ContactDao.getInstance().getContactsByClient(client);
        assertNotNull(list);
        assertEquals(0, list.size());
    }
}