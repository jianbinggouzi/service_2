package com.deepblue.dao;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.LoginLog;

@Repository
public class LoginLogDao extends BaseDao<LoginLog> {
	public void save(LoginLog loginLog) {
		getHibernateTemplate().save(loginLog);
	}
}
