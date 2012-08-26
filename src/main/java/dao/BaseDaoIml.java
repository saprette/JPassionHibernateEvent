package dao;

import exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import java.io.Serializable;
import java.util.List;

/**
 * User: sam
 * Date: 8/15/12
 * Time: 6:24 PM
 */
@SuppressWarnings("unchecked")
public class BaseDaoIml<T, K extends Serializable> implements BaseDao<T, K> {

    private static final Logger logger = Logger.getLogger(BaseDaoIml.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction tx;
    private final Class<T> entityClass;

    public BaseDaoIml(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public K create(T object) throws DaoException {
        K key = null;
        try {
            startWorkUnit();
            key = (K) session.save(object);
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
        return key;
    }

    @Override
    public void saveOrUpdate(T object) throws DaoException {
        try {
            startWorkUnit();
            session.saveOrUpdate(object);
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
    }

    @Override
    public T find(K key) throws DaoException {
        T object = null;
        try {
            startWorkUnit();
            object = (T) session.get(entityClass, key);
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
        return object;
    }

    @Override
    public List<T> findAll() throws DaoException {
        List<T> objects = null;
        try {
            startWorkUnit();
            objects = session.createCriteria(entityClass).list();
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
        return objects;
    }

    @Override
    public List<T> findByCriterions(Criterion... criterions) throws DaoException {
        List<T> objects = null;
        try {
            startWorkUnit();
            Criteria criteria = session.createCriteria(entityClass);
            for (Criterion criterion : criterions) {
                criteria.add(criterion);
            }
            objects = criteria.list();
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
        return objects;
    }

    @Override
    public List<T> findByExample(String[] excludeProperties, boolean likeSearch, T... examples) throws DaoException {
        List<T> objects = null;
        try {
            startWorkUnit();
            Criteria criteria = session.createCriteria(entityClass);
            for (T example : examples) {
                Example e = Example.create(example);
                if (likeSearch) {
                    e.enableLike();
                }
                for (String property : excludeProperties) {
                    e.excludeProperty(property);
                }
                criteria.add(e);
            }
            objects = criteria.list();
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
        return objects;
    }

    @Override
    public List<T> findFromHQL(String hql) throws DaoException {
        List<T> objects = null;
        try {
            startWorkUnit();
            objects = session.createQuery(hql).list();
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
        return objects;
    }

    @Override
    public void update(T object) throws DaoException {
        try {
            startWorkUnit();
            session.update(object);
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
    }

    @Override
    public void delete(T object) throws DaoException {
        try {
            startWorkUnit();
            session.delete(object);
            commitWorkUnit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            cleanAfterWorkUnit();
        }
    }

    private void cleanAfterWorkUnit() {
        tx = null;
        if (session != null) {
            session.close();
        }
        session = null;
    }

    private void handleException(HibernateException e) throws DaoException {
        if (tx != null) {
            tx.rollback();
        }
        logger.error("DaoException", e);
        throw new DaoException();
    }

    private void commitWorkUnit() {
        tx.commit();
    }

    private void startWorkUnit() throws DaoException {
        if (entityClass == null) {
            throw new DaoException();
        }
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
    }
}
