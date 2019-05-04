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
@Table(name = "t_letter")
public class Letter extends EntityBaseDomain {

	@Column(name = "board_id")
	private String boardId;

	@Column(name = "letter_title")
	private String letterTitle;

	@Column(name = "letter_views")
	private int views;

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

	@Column(name = "letter_thanks")
	private int thanks;

	@Transient
	private Set<Post> comments;

	@Column(name = "letter_type")
	private int letterType;

	public int getThanks() {
		return thanks;
	}

	public void setThanks(int thanks) {
		this.thanks = thanks;
	}

	public int getLetteType() {
		return letterType;
	}

	public void setLetterType(int letterType) {
		this.letterType = letterType;
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

	public void setViews(int views) {
		this.views = views;
	}

	public int getViews() {
		return views;
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
