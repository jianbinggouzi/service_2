package com.deepblue.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepblue.dao.LoginLogDao;
import com.deepblue.dao.UserDao;
import com.deepblue.domain.LoginLog;
import com.deepblue.domain.User;
import com.deepblue.exception.UserExistException;

@Service
public class UserService {

	private UserDao userDao;

	private LoginLogDao loginLogDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		System.out.println("UserService:注入userDao");
	}

	@Autowired
	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
		System.out.println("UserService:注入loginLogDao");
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @throws UserExistException
	 */
	public void register(User user) throws UserExistException {
		User user2 = userDao.getUsersByName(user.getUserName());
		if (user2 != null) {
			throw new UserExistException("用户名存在");
		} else {
			user.setCredit(200);
			user.setUserType(User.USER_NO_VIP);
			userDao.save(user);
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public void update(User user) {
		userDao.update(user);
	}

	/**
	 * 根据用户名查询用户实例
	 * 
	 * @param userName
	 * @return
	 */
	public User getUserBuUserName(String userName) {
		return userDao.getUsersByName(userName);
	}

	/**
	 * 根据用户Id获取用户实例
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(String userId) {
		return userDao.get(userId);
	}

	/**
	 * 锁定用户
	 * 
	 * @param userName
	 */
	public void lockUser(String userName) {
		User user = userDao.getUsersByName(userName);
		user.setLocked(User.USER_LOCK);
		userDao.update(user);
	}

	/**
	 * 解锁用户
	 * 
	 * @param userName
	 */
	public void unlockUser(String userName) {
		User user = userDao.getUsersByName(userName);
		user.setLocked(User.USER_UNLOCK);
		userDao.update(user);
	}

	/**
	 * 对用户名进行模糊查询
	 * 
	 * @param userName
	 * @return
	 */
	public List<User> queryUserByUserName(String userName) {
		return userDao.queryUserByName(userName);
	}

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public List<User> getAllUsers() {
		return userDao.loadAll();
	}

	/**
	 * 登录成功
	 * 
	 * @param user
	 */
	public void loginSuccess(User user) {
		LoginLog loginLog = new LoginLog();
		loginLog.setUser(user);
		loginLog.setIp(user.getLastIp());
		loginLog.setTime(new Date());
		userDao.update(user);
		loginLogDao.save(loginLog);
	}

}
