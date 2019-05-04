package com.deepblue.dao;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.OperateLog;
import com.deepblue.domain.User;

@Repository
public class OperateLogDao extends BaseDao<OperateLog> {
	private String GET_USE_RECEIVE_OPERATES = "from OperateLog where receiver.userId = ?";

	private String GET_USER_SEND_OPERATES = "from OperateLog where sender.userId = ?";

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
