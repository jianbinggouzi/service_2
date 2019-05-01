package com.deepblue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.Post;

@Repository
public class PostDao extends BaseDao<Post> {
	private static final String GET_POST = "from Post where postId = ?";
	private static final String DELETE_POST = "delete from Post where postId = ?";

	private static final String GET_USER_SEND_POST = "from Post where fromUser = ? order by createTime desc";

	private static final String GET_USER_RECEIVE_POST = "from Post where toUser = ? order by createTime desc";

	/**
	 * 获取指定用户发出的评论
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getUserSendPosts(String userId, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_USER_SEND_POST, pageNo, pageSize, userId);
	}

	/**
	 * 获取用户收到评论
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getUserReceivePosts(String userId, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_USER_RECEIVE_POST, pageNo, pageSize, userId);
	}

	/**
	 * 获取指定Post
	 * 
	 * @param postId
	 * @return
	 */
	public Post getPagedPosts(String postId) {
		List<Post> list = (List<Post>) getHibernateTemplate().find(GET_POST, postId);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 删除指定Post
	 * 
	 * @param postId
	 */
	public void deletePost(String postId) {
		getHibernateTemplate().bulkUpdate(DELETE_POST, postId);
	}
}
