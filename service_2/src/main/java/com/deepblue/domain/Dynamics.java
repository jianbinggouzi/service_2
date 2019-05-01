package com.deepblue.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_dynamics")
public class Dynamics extends EntityBaseDomain {

	@Column(name = "dynamics_title")
	private String dynamicsTitle;

	@Column(name = "views")
	private int views;

	@Column(name = "replies")
	private int replies;

	@Column(name = "digests")
	private int digests;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post mainPost;

	@Column(name = "collects")
	private int collects;

	public int getCollects() {
		return collects;
	}

	public void setCollects(int collects) {
		this.collects = collects;
	}

	public Post getMainPost() {
		return mainPost;
	}

	public void setMainPost(Post mainPost) {
		this.mainPost = mainPost;
	}

	@Transient
	private Set<Post> comments;

	public String getDynamicsTitle() {
		return dynamicsTitle;
	}

	public void setDynamicsTitle(String dynamicsTitle) {
		this.dynamicsTitle = dynamicsTitle;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getDigests() {
		return digests;
	}

	public void setDigests(int digests) {
		this.digests = digests;
	}

	public Set<Post> getComments() {
		return comments;
	}

	public void setComments(Set<Post> comments) {
		this.comments = comments;
	}

}
