package com.deepblue.dao;

import org.springframework.stereotype.Repository;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.Letter;
import com.deepblue.domain.User;

@Repository
public class LetterDao extends BaseDao<Letter> {
	private static final String GET_BOARD_DIGEST_LETTERS = "from Letter where boardId = ? and digest>=0 order by digest desc";
	private static final String GET_BOARD_TIME_LETTERS = "from Letter where boardId = ? order by createTime desc";
	private static final String QUERY_LETTERS_BY_TITLE = "from Letter where letterTitle like ? order by digest desc";

	private static final String GET_USER_LETTERS = "from Letter where user.userId = ? and letterType = ? order by createTime desc";
	private static final String GET_USER_ALL_LETTERS = "from Letter where user.userId = ? order by createTime desc";

	/**
	 * 获取用户所有信件
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param letterType
	 *            不区分收到发出时用-1
	 * @return
	 */
	public Page getUserLetters(String userId, int pageNo, int pageSize, int letterType) {
		if (letterType == ConfigVars.LETTER_TYPE_RECEIVE || letterType == ConfigVars.LETTER_TYPE_SEND)
			return pageQueryByHQL(GET_USER_LETTERS, pageNo, pageSize, userId, letterType);
		return pageQueryByHQL(GET_USER_ALL_LETTERS, pageNo, pageSize, userId);

	}

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
		return pageQueryByHQL(GET_BOARD_DIGEST_LETTERS, pageNo, pageSize, boardId);
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
		return pageQueryByHQL(GET_BOARD_TIME_LETTERS, pageNo, pageSize, boardId);
	}

	public Page queryLetterByTitle(String title, int pageNo, int pageSize) {
		return pageQueryByHQL(QUERY_LETTERS_BY_TITLE, pageNo, pageSize, title);
	}

	/**
	 * 对相应信件实例的点赞\回复\分享\收藏\打赏\感谢数进行修改
	 * 
	 * @param letter
	 * @param operateType
	 * @param sendUser
	 * @param toUser
	 */
	public void changeLogOnLetter(Letter letter, int operateType, User sendUser, User toUser) {
		letter = get(letter.getId());
		if (operateType == ConfigVars.OPERATE_DIGEST) {
			letter.setDigests(letter.getDigests() + 1);
		} else if (operateType == ConfigVars.OPERATE_REPLIES) {
			letter.setLetterReplies(letter.getLetterReplies() + 1);
		} else if (operateType == ConfigVars.OPERATE_COLLECT) {
			letter.setCollects(letter.getCollects() + 1);
		} else if (operateType == ConfigVars.OPERATE_REWARD) {
			// 打赏
		} else if (operateType == ConfigVars.OPERATE_SHARE) {
			letter.setLetterShares(letter.getLetterShares() + 1);
		} else if (operateType == ConfigVars.OPERATE_THANK) {
			letter.setThanks(letter.getThanks() + 1);
		}
		update(letter);
	}
}
