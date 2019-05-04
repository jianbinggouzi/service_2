package com.deepblue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.OperateLog;
import com.deepblue.domain.User;

@Repository
public class OperateLogDao extends BaseDao<OperateLog> {
	private String GET_USE_RECEIVE_OPERATES = "from OperateLog where receiver.userId = ?";

	private String GET_USER_SEND_OPERATES = "from OperateLog where sender.userId = ?";

	private String GET_OPEARTES_COUNT_BY_TYPE_AND_SENDER = "from OperateLog where sender.userId = ? and operateType = ? ";

	private String GET_OPERATE_COUNT_BY_TYPE_AND_RECEIVER = "from OperateLog where receiver.userId = ? and operateType = ?";

	/**
	 * 获取指定用户收到的指定类型的操作数
	 * 
	 * @param userId
	 * @param operateType
	 * @return
	 */
	public long getOperateCountByReceiver(String userId, int operateType) {
		String hql = "select count(*) " + GET_OPERATE_COUNT_BY_TYPE_AND_RECEIVER;
		List list = getHibernateTemplate().find(hql, userId, operateType);
		return list == null ? 0 : (Long) list.get(0);
	}

	/**
	 * 获取指定用户发出的指定类型的操作数
	 * 
	 * @param userId
	 * @param operateType
	 * @return
	 */
	public long getOperateCountBySender(String userId, int operateType) {
		String hql = "select count(*) " + GET_OPEARTES_COUNT_BY_TYPE_AND_SENDER;
		List list = getHibernateTemplate().find(hql, userId, operateType);
		return list == null ? 0 : (Long) list.get(0);
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
