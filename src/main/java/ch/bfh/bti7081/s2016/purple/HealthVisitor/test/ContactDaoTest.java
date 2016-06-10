package ch.bfh.bti7081.s2016.purple.HealthVisitor.test;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.ContactDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.ContactEntity;
import junit.framework.TestCase;

import java.util.Collection;

/**
 * Created by gimmie7 on 08/06/16.
 */
public class ContactDaoTest extends TestCase {

    public void testGetContactsByClientId() throws Exception {
    	int id = 55;
    	ContactDao ad = ContactDao.getInstance();
        Collection<ContactEntity> list = ad.getContactsByClientId(id);
        System.out.println(list.size());
        assertNotNull(list);
        assertEquals(2, list.size());
    }
}