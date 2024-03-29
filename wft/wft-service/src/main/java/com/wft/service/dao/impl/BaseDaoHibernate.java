package com.wft.service.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wft.service.dao.IDao;

/**
 * This class serves as the Base class for all other Daos - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.</p>
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS)
public class BaseDaoHibernate<T> extends HibernateDAO implements IDao<T> {
	/**
	 * logger
	 */
	private static final Logger logger = Logger
			.getLogger(BaseDaoHibernate.class);

	private Class<T> type;

	/**
	 * This method could be override to give the column names used to sort the
	 * list
	 * 
	 * @return the column used to sort the list of type T
	 */
	public String[] getNaturalSortOrders() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wft.service.dao.IDao#findRestrictedList(int,
	 * int, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findRestrictedList(final int startPosition,
			final int nbElements, final String orderBy, final String orderSens) {
		return (List<T>) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(type);

						criteria.setFirstResult(startPosition);
						criteria.setMaxResults(nbElements);
						if (orderSens.equalsIgnoreCase("ASC")) {
							criteria.addOrder(Order.asc(orderBy));
						} else {
							criteria.addOrder(Order.desc(orderBy));
						}

						List<T> results = criteria.list();
						return results;
					}
				});
	}

	/**
	 * Constructeur avec le type d'objet interroge par le DAO
	 * 
	 * @param type
	 */
	public BaseDaoHibernate(Class<T> _type) {
		this.type = _type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wft.service.dao.IDao#add(java.lang.Object)
	 */
	public T add(T _o) {
		if (logger.isDebugEnabled()) {
			logger.debug("add object of class " + type.getName() + " : " + _o);
		}
		this.getHibernateTemplate().save(_o);
		return _o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wft.service.dao.IDao#update(java.lang.Object)
	 */
	public T update(T _o) {
		if (logger.isDebugEnabled()) {
			logger.debug("update object of class " + type.getName() + " : "
					+ _o);
		}
		this.getHibernateTemplate().saveOrUpdate(_o);
		return _o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wft.service.dao.IDao#findById(java.io.Serializable
	 * )
	 */
	@SuppressWarnings("unchecked")
	public T findById(Serializable _id) {
		if (logger.isDebugEnabled()) {
			logger.debug("find object of class " + type.getName()
					+ " with id : " + _id);
		}
		T o = (T) this.getHibernateTemplate().get(type, _id);
		if (logger.isDebugEnabled()) {
			logger.debug("found " + o);
		}
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wft.service.dao.IDao#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		final String[] sortOrders = getNaturalSortOrders();

		return (List<T>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(type);
						if (sortOrders != null) {
							for (int i = 0; i < sortOrders.length; i++) {
								crit.addOrder(Order.asc(sortOrders[i]));
							}
						}
						return crit.list();
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wft.service.dao.IDao#count()
	 */
	public int count() {
		return ((Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(type);
						crit.setProjection(Projections.rowCount());
						return (Integer) crit.list().get(0);
					}
				})).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wft.service.dao.IDao#delete(java.io.Serializable)
	 */
	public void delete(Serializable _id) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete object of class : " + type.getName()
					+ " with id : " + _id);
		}
		this.getHibernateTemplate().delete(findById(_id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wft.service.dao.IDao#getType()
	 */
	public Class<T> getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wft.service.dao.IDao#delete(java.lang.Object)
	 */
	public void delete(T _o) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete object of class : " + type.getName() + " : "
					+ _o);
		}
		this.getHibernateTemplate().delete(_o);
	}

	public List<T> findByPropertyValue(final String propertyName,
			final Object propertyValue) {
		return (List<T>) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(type);

						criteria.add(Restrictions.eq(propertyName,
								propertyValue));

						List<T> results = criteria.list();
						return results;
					}
				});
	}
}
