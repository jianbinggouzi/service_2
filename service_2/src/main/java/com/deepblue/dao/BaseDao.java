package com.deepblue.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.Assert;

public class BaseDao<T> {

	private Class<T> entityClass;

	private HibernateTemplate hibernateTemplate;

	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
		System.out.println("BaseDao:加载" + entityClass.toString() + "Dao");
	}

	/**
	 * 根据实体加载PO实例
	 * 
	 * @param id
	 * @return
	 */
	public T load(Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 根据实体获取PO实例
	 * 
	 * @param id
	 * @return
	 */
	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 加载全部PO实例
	 * 
	 * @return
	 */
	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 保存PO实例
	 * 
	 * @param entity
	 */
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 删除PO实例
	 * 
	 * @param entity
	 */
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 更新PO实例
	 * 
	 * @param entity
	 */
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * 执行HQL查询
	 * 
	 * @param hql
	 * @return
	 */
	public List find(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 执行HQL查询 带参数
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List find(String hql, Object... params) {
		return getHibernateTemplate().find(hql, params);
	}

	/**
	 * 对延迟加载的PO进行实例化
	 * 
	 * @param object
	 */
	public void initialize(Object object) {
		getHibernateTemplate().initialize(object);
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public Session getSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

	public Query createQuery(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query;
	}

	/**
	 * 根据hql分页查询
	 * 
	 * @param hql
	 * @param pageNo
	 *            从1开始
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page pageQueryByHQL(String hql, int pageNo, int pageSize, Object... params) {
		String countQueryString = "select count (*)" + removeSelect(removeOrders(hql));
		List listCount = getHibernateTemplate().find(countQueryString, params);
		long totalCount = (Long) (listCount.get(0));

		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, params);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 根据示例分页查询
	 * 
	 * @param example
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page pageQueryByExample(Object example, int pageNo, int pageSize) {
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<T> _totalCount = (List<T>) getHibernateTemplate().findByExample(example);
		long totalCount = (Long) (_totalCount.get(0));
		List list = getHibernateTemplate().findByExample(example, pageNo, pageSize);
		return new Page(startIndex, totalCount, pageSize, list);
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
