package com.deepblue.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_board")
public class Board extends BaseDomain {
	@Id
	@Column(name = "board_id")
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String boardId;

	@Column(name = "board_name")
	private String boardName;

	@Column(name = "board_desc")
	private String boardDesc;

	@Column(name = "letter_num")
	private long letterNum;

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}

	public void setLetterNum(long letterNum) {
		this.letterNum = letterNum;
	}

	public String getBoardId() {
		return boardId;
	}

	public String getBoardName() {
		return boardName;
	}

	public String getBoardDesc() {
		return boardDesc;
	}

	public long getLetterNum() {
		return letterNum;
	}
}
