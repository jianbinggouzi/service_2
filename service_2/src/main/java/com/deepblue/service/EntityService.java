package com.deepblue.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepblue.cons.ConfigVars;
import com.deepblue.dao.BoardDao;
import com.deepblue.dao.DynamicsDao;
import com.deepblue.dao.LetterDao;
import com.deepblue.dao.OperateLogDao;
import com.deepblue.dao.Page;
import com.deepblue.dao.PostDao;
import com.deepblue.dao.UserDao;
import com.deepblue.domain.Dynamics;
import com.deepblue.domain.EntityBaseDomain;
import com.deepblue.domain.Letter;
import com.deepblue.domain.Post;
import com.deepblue.domain.User;

@Service
public class EntityService {

	private LetterDao letterDao;

	private DynamicsDao dynamicsDao;

	private OperateLogDao operateLogDao;

	private BoardDao boardDao;

	private PostDao postDao;

	private UserDao userDao;

	/**
	 * 添加实体
	 * 
	 * @param f_type
	 *            类型
	 * @param entity
	 */
	public void addEntity(int f_type, EntityBaseDomain entity, User user) {
		Date date = new Date();
		entity.setUser(user);
		entity.setCreateTime(date);
		Post mainPost = null;
		switch (f_type) {
		case ConfigVars.TYPE_LETTER:
			Letter letter = (Letter) entity;
			mainPost = letter.getMainPost();
			mainPost.setCreateTime(date);
			postDao.save(mainPost);
			letterDao.save(letter);
			break;
		case ConfigVars.TYPE_DYNAMICS:
			Dynamics dynamics = (Dynamics) entity;
			mainPost = dynamics.getMainPost();
			mainPost.setCreateTime(date);
			postDao.save(mainPost);
			dynamicsDao.save(dynamics);
			break;
		case ConfigVars.TYPE_POST:
			Post post = (Post) entity;
			postDao.save(post);
			break;
		}
	}

	/**
	 * 删除实体
	 * 
	 * @param f_type
	 * @param entity
	 */
	public void removeEntity(int f_type, Object entity) {
		switch (f_type) {
		case ConfigVars.TYPE_LETTER:
			letterDao.remove((Letter) entity);
			break;
		case ConfigVars.TYPE_DYNAMICS:
			dynamicsDao.remove((Dynamics) entity);
			break;
		case ConfigVars.TYPE_POST:
			postDao.remove((Post) entity);
			break;
		}
	}

	/**
	 * 获取用户发出的实体
	 * 
	 * @param f_type
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedSendEntity(int f_type, User user, int pageNo, int pageSize) {
		switch (f_type) {
		case ConfigVars.TYPE_LETTER:
			return letterDao.getUserLetters(user.getUserId(), pageNo, pageSize, ConfigVars.LETTER_TYPE_SEND);
		case ConfigVars.TYPE_DYNAMICS:
			return dynamicsDao.queryDynamiceBuUserId(user.getUserId(), pageNo, pageSize);
		case ConfigVars.TYPE_POST:
			return postDao.getUserSendPosts(user.getUserId(), pageNo, pageSize);
		}
		return null;
	}

	/**
	 * 获取用户收到的实体
	 * 
	 * @param f_type
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedReceiveEntity(int f_type, User user, int pageNo, int pageSize) {
		switch (f_type) {
		case ConfigVars.TYPE_LETTER:
			return letterDao.getUserLetters(user.getUserId(), pageNo, pageSize, ConfigVars.LETTER_TYPE_RECEIVE);
		case ConfigVars.TYPE_POST:
			return postDao.getUserReceivePosts(user.getUserId(), pageNo, pageSize);
		}
		return null;
	}

	@Autowired
	public void setLetterDao(LetterDao letterDao) {
		this.letterDao = letterDao;
	}

	@Autowired
	public void setDynamicsDao(DynamicsDao dynamicsDao) {
		this.dynamicsDao = dynamicsDao;
	}

	@Autowired
	public void setOperateLogDao(OperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}

	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
