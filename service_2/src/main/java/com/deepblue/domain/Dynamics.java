package com.deepblue.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_dynamicses")
public class Dynamics extends BaseDomain {

	@Id
	@Column(name = "dynamics_id")
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String dynamicsId;

	@Column(name = "dynamics_title")
	private String dynamicsTitle;

	@Column(name = "create_time")
	private Date createDate;

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

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public String getDynamicsId() {
		return dynamicsId;
	}

	public void setDynamicsId(String dynamicsId) {
		this.dynamicsId = dynamicsId;
	}

	public String getDynamicsTitle() {
		return dynamicsTitle;
	}

	public void setDynamicsTitle(String dynamicsTitle) {
		this.dynamicsTitle = dynamicsTitle;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
