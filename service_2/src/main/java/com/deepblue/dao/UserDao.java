package com.deepblue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.User;

@Repository
public class UserDao extends BaseDao<User> {
	private static final String GET_USER_BY_NAME = "from User u where u.userName = ?";
	private static final String QUERY_USER_BY_NAME = "from User u where u.userName like ?";

	/**
	 * 按昵称返回用户
	 * 
	 * @param userName
	 * @return
	 */
	public User getUsersByName(String userName) {
		List<User> users = (List<User>) getHibernateTemplate().find(GET_USER_BY_NAME, userName);
		return users.size() > 0 ? users.get(0) : null;
	}

	/**
	 * 按昵称查询用户
	 * 
	 * @param userName
	 * @return
	 */
	public List<User> queryUserByName(String userName) {
		List<User> users = (List<User>) getHibernateTemplate().find(QUERY_USER_BY_NAME, userName + "%");
		return users;
	}
}
