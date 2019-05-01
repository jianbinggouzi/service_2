package com.deepblue.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.OperateLog;
import com.deepblue.domain.User;

@Repository
public class OperateLogDao extends BaseDao<OperateLog> {
	private String GET_USE_RECEIVE_OPERATES = "from OperateLog where receiver.userId = ?";

	private String GET_USER_SEND_OPERATES = "from OperateLog where sender.userId = ?";

	/**
	 * 保存用户记录
	 * 
	 * @param send_user
	 * @param receive_user
	 * @param entityId
	 * @param entityType
	 * @param operateType
	 */
	public void addOperateLog(User send_user, User receive_user, String entityId, int entityType, int operateType) {
		OperateLog operateLog = new OperateLog();
		operateLog.setEntityId(entityId);
		operateLog.setSender(send_user);
		operateLog.setReceiver(receive_user);
		operateLog.setOperateType(operateType);
		operateLog.setTime(new Date());
		operateLog.setEntityType(entityType);
		save(operateLog);
	}

	/**
	 * 获取用户发出的操作
	 * 
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedSendOperate(User user, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_USER_SEND_OPERATES, pageNo, pageSize, user.getUserId());
	}

	/**
	 * 获取用户收到的操作
	 * 
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedReceiveOperate(User user, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_USE_RECEIVE_OPERATES, pageNo, pageSize, user.getUserId());
	}
}
