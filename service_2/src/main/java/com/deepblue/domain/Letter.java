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
@Table(name = "t_letter")
public class Letter extends BaseDomain {

	@Id
	@Column(name = "letter_id")
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String letterId;

	@Column(name = "board_id")
	private String boardId;

	@Column(name = "letter_title")
	private String letterTitle;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private String userId;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "letter_views")
	private int letterViews;

	@Column(name = "letter_replies")
	private int letterReplies;

	@Column(name = "letter_shares")
	private int letterShares;

	@Column(name = "letter_digests")
	private int digests;

	@Column(name = "letter_collects")
	private int collects;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post mainPost;

	@Transient
	private Set<Post> comments;

	public void setLetterId(String letterId) {
		this.letterId = letterId;
	}

	public String getLetterId() {
		return letterId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getBoardId() {
		return this.boardId;
	}

	public void setLetterTitle(String letterTitle) {
		this.letterTitle = letterTitle;
	}

	public String getLetterTitle() {
		return letterTitle;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setLetterViews(int letterViews) {
		this.letterViews = letterViews;
	}

	public int getLetterViews() {
		return letterViews;
	}

	public void setLetterReplies(int letterReplies) {
		this.letterReplies = letterReplies;
	}

	public int getLetterReplies() {
		return letterReplies;
	}

	public void setLetterShares(int letterShares) {
		this.letterShares = letterShares;
	}

	public int getLetterShares() {
		return letterShares;
	}

	public int getCollects() {
		return collects;
	}

	public void setCollects(int collects) {
		this.collects = collects;
	}

	public int getDigests() {
		return digests;
	}

	public void setDigests(int digests) {
		this.digests = digests;
	}

	public void setMainPost(Post mainPost) {
		this.mainPost = mainPost;
	}

	public Post getMainPost() {
		return mainPost;
	}

	public void setComments(Set<Post> comments) {
		this.comments = comments;
	}

	public Set<Post> getComments() {
		return comments;
	}

}
