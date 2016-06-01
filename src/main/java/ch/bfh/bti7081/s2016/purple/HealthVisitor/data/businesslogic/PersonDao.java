package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import java.io.Serializable;

/**
 * Created by tgdflto1 on 27/05/16.
 */
public interface PersonDao<E, K extends Serializable> extends Dao<E, K> {
    E findByEmail(String email);

}
