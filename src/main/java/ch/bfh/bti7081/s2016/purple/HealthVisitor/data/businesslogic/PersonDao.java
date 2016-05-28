package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

/**
 * Created by tgdflto1 on 27/05/16.
 */
public interface PersonDao<E> extends Dao {
    E findByEmail(String email);

}
