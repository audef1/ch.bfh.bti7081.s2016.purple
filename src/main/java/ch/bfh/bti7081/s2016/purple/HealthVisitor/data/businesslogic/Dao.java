package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

/**
 * Created by tgdflto1 on 27/05/16.
 */
public interface Dao<E, K> {
    void persist(E entity);
    void remove(E entity);
    E findById(K id);
}
