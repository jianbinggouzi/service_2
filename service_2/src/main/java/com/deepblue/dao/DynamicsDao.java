package com.deepblue.dao;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.Dynamics;

@Repository
public class DynamicsDao extends BaseDao<Dynamics> {
	private static final String GET_DYNAMICS_BY_USER_ID = "from Dynamics where user.uesr_id = ?";

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
		return pageQuery(GET_DYNAMICS_BY_USER_ID, pageNo, pageSize, userId);
	}
}
