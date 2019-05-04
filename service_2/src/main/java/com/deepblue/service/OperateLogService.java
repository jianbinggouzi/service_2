package com.deepblue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepblue.cons.ConfigVars;
import com.deepblue.dao.DynamicsDao;
import com.deepblue.dao.LetterDao;
import com.deepblue.dao.OperateLogDao;
import com.deepblue.dao.PostDao;
import com.deepblue.dao.UserDao;
import com.deepblue.domain.Dynamics;
import com.deepblue.domain.Letter;
import com.deepblue.domain.OperateLog;
import com.deepblue.domain.Post;
import com.deepblue.domain.User;

@Service
public class OperateLogService {

	private LetterDao letterDao;

	private DynamicsDao dynamicsDao;

	private PostDao postDao;

	private OperateLogDao operateLogDao;

	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	@Autowired
	public void setOperateLogDao(OperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}

	/**
	 * 对数据库中相应信件/动态/实例的记录数进行修改，相关操作(OperateLog.operateType)：点赞\回复\分享\收藏\打赏\感谢数
	 * 
	 * @param operateLog
	 */
	public void changeLogOnEntity(OperateLog operateLog) {
		User sendUser = userDao.get(operateLog.getSender().getUserId());
		String toUserId = operateLog.getReceiver().getUserId();
		User toUser = userDao.get(toUserId);
		int operateType = operateLog.getOperateType();
		// 不改数据库了
		switch (operateLog.getEntityType()) {
		case ConfigVars.TYPE_LETTER:
			Letter letter = letterDao.get(operateLog.getEntityId());
			letterDao.changeLogOnLetter(letter, operateType, sendUser, toUser);
			break;
		case ConfigVars.TYPE_DYNAMICS:
			Dynamics dynamics = dynamicsDao.get(operateLog.getEntityId());
			dynamicsDao.changeLogOnDynamics(dynamics, operateType, sendUser, toUser);
			break;
		case ConfigVars.TYPE_POST:
			Post post = postDao.get(operateLog.getEntityId());
			postDao.chaneLogOnPost(post, operateType, sendUser, toUser);
			break;
		}
	}

	/**
	 * 保存用户记录
	 * 
	 * @param operateLog
	 */
	public void addOperateLog(OperateLog operateLog) {
		operateLogDao.saveOrUpdate(operateLog);
	}

}
