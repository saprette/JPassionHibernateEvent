package dao;

import exceptions.DaoException;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

/**
 * User: sam
 * Date: 8/15/12
 * Time: 6:19 PM
 */
public interface BaseDao<T, K extends Serializable> {


    public K create(T object) throws DaoException;

    public void saveOrUpdate(T object) throws DaoException;

    public T find(K key) throws DaoException;

    public List<T> findAll() throws DaoException;

    public List<T> findByCriterions(Criterion... criterions) throws DaoException;

    public List<T> findByExample(String[] excludeProperties, boolean likeSearch, T... examples) throws DaoException;

    public List<T> findFromHQL(String hql) throws DaoException;

    public void update(T object) throws DaoException;

    public void delete(T object) throws DaoException;


}
