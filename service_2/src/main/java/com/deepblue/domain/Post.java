package com.deepblue.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_post")
public class Post extends EntityBaseDomain {

	@Column(name = "post_text")
	private String postText;

	@Column(name = "last_post_id")
	private Post lastPost;

	@ManyToOne
	@JoinColumn(name = "to_user_id")
	private User toUser;

	@Column(name = "digest")
	private int digest;

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public Post getLastPost() {
		return lastPost;
	}

	public void setLastPost(Post lastPost) {
		this.lastPost = lastPost;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public int getDigest() {
		return digest;
	}

	public void setDigest(int digest) {
		this.digest = digest;
	}

}
