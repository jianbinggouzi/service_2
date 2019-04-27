package com.deepblue.dao;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.Letter;

@Repository
public class LetterDao extends BaseDao<Letter> {
	private static final String GET_BOARD_DIGEST_LETTERS = "from Letter t where t.boardId = ? and digest>=0 order by digest desc";
	private static final String GET_BOARD_TIME_LETTERS = "from Letter where boardId = ? order by createTime desc";
	private static final String QUERY_LETTERS_BY_TITLE = "from Letter where letterTitle like ? order by digest desc";

	/**
	 * 按照点赞数降序排序某板块所有信件后，获取指定页的信件
	 * 
	 * @param boardId
	 * @param pageNo
	 *            页号 从1开始
	 * @param pageSize
	 * @return
	 */
	public Page getBoardDigestLetters(String boardId, int pageNo, int pageSize) {
		return pageQuery(GET_BOARD_DIGEST_LETTERS, pageNo, pageSize, boardId);
	}

	/**
	 * 按照时间降序排序某板块所有信件后，获取指定页的信件
	 * 
	 * @param boardId
	 * @param pageNo
	 *            页号 从1开始
	 * @param pageSize
	 * @return
	 */
	public Page getBoardTimeLetters(String boardId, int pageNo, int pageSize) {
		return pageQuery(GET_BOARD_TIME_LETTERS, pageNo, pageSize, boardId);
	}

	public Page queryLetterByTitle(String title, int pageNo, int pageSize) {
		return pageQuery(QUERY_LETTERS_BY_TITLE, pageNo, pageSize, title);
	}
}
