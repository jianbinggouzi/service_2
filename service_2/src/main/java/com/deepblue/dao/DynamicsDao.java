package com.deepblue.dao;

import org.springframework.stereotype.Repository;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.Dynamics;
import com.deepblue.domain.User;

@Repository
public class DynamicsDao extends BaseDao<Dynamics> {
	private static final String GET_DYNAMICS_BY_USER_ID = "from Dynamics where user.userId = ?";

	/**
	 * 查询指定用户的所有动态
	 * 
	 * @param userId
	 * @param pageNo
	 *            页号 从1开始
	 * @param pageSize
	 * @return
	 */
	public Page queryDynamiceBuUserId(String userId, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_DYNAMICS_BY_USER_ID, pageNo, pageSize, userId);
	}

	/**
	 * 对相应动态实例的点赞\回复\收藏数进行修改
	 * 
	 * @param dynamics
	 * @param operateType
	 * @param sendUser
	 * @param toUser
	 */
	public void changeLogOnDynamics(Dynamics dynamics, int operateType, User sendUser, User toUser) {
		dynamics = get(dynamics.getId());
		if (operateType == ConfigVars.OPERATE_DIGEST) {
			dynamics.setDigests(dynamics.getDigests() + 1);
		} else if (operateType == ConfigVars.OPERATE_REPLIES) {
			dynamics.setReplies(dynamics.getReplies() + 1);
		} else if (operateType == ConfigVars.OPERATE_COLLECT) {
			dynamics.setCollects(dynamics.getCollects() + 1);
		}
		update(dynamics);
	}
}
