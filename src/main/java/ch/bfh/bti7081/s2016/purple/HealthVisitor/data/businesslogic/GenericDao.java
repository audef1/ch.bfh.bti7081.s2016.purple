package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.HealthVisitorUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by tgdflto1 on 01/06/16.
 */
public class GenericDao<E, K extends Serializable>  implements Dao<E, K> {
    protected static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(HealthVisitorUI.PERSISTENCE_UNIT_NAME);
    private static Logger logger = LogManager.getLogger(AppointmentDao.class);
    protected final EntityManager entityManager;

    public GenericDao(){
        this.entityManager = factory.createEntityManager();
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    protected Class<E> entityClass;

    @Override
    public E update(E entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public E persist(E entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
//        entityManager.refresh(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void remove(E entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public E refresh(E entity) {
        entityManager.getTransaction().begin();
        entityManager.refresh(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public E findById(K id) {
        entityManager.getTransaction().begin();
        E entity = entityManager.find(entityClass, id);
        entityManager.getTransaction().commit();
        return entity;
    }

	@Override
	public E updateOrPersist(E entity) {
		if(entityManager.contains(entity))
			return update(entity);
		else
			return persist(entity);
	}
}
