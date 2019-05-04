package com.deepblue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.Post;
import com.deepblue.domain.User;

@Repository
public class PostDao extends BaseDao<Post> {
	private static final String GET_POST_BY_ID = "from Post where postId = ?";

	private static final String DELETE_POST_BY_ID = "delete from Post where postId = ?";

	private static final String GET_POST_BY_LAST_ID = "from Post where lastPostId = ?";

	private static final String GET_USER_SEND_POST = "from Post where user.userId = ? order by createTime desc";

	private static final String GET_USER_RECEIVE_POST = "from Post where toUser.userId = ? order by createTime desc";

	/**
	 * 根据lastId获取所有post
	 * 
	 * @param lastId
	 * @return
	 */
	public Page getPagedPostsByLastId(String lastId, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_POST_BY_LAST_ID, pageNo, pageSize, lastId);
	}

	/**
	 * 获取指定用户发出的评论
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPostsBySender(String userId, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_USER_SEND_POST, pageNo, pageSize, userId);
	}

	/**
	 * 获取指定用户收到评论
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPostsByReceiver(String userId, int pageNo, int pageSize) {
		return pageQueryByHQL(GET_USER_RECEIVE_POST, pageNo, pageSize, userId);
	}

	/**
	 * 获取指定Post
	 * 
	 * @param postId
	 * @return
	 */
	public Post getPagedPosts(String postId) {
		List<Post> list = (List<Post>) getHibernateTemplate().find(GET_POST_BY_ID, postId);
		return list == null ? null : list.get(0);
	}

	/**
	 * 删除指定Post
	 * 
	 * @param postId
	 */
	public void deletePost(String postId) {
		getHibernateTemplate().bulkUpdate(DELETE_POST_BY_ID, postId);
	}

	/**
	 * 对相应文本实例的点赞数进行修改
	 * 
	 * @param post
	 * @param operateType
	 * @param sendUser
	 * @param toUser
	 */
	public void chaneLogOnPost(Post post, int operateType, User sendUser, User toUser) {
		post = get(post.getId());
		if (operateType == ConfigVars.OPERATE_DIGEST) {
			post.setDigest(post.getDigest() + 1);
		}
		update(post);
	}
}
