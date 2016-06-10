package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import java.io.Serializable;

/**
 * @author tgdflto1 on 27/05/16.
 */
interface Dao<E, K extends Serializable> {

    E update(E entity);
    E persist(E entity);
    void remove(E entity);
    E refresh(E entity);
    E findById(K id);
    E updateOrPersist(E entity);
}
