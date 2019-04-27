package com.deepblue.dao;

import java.util.Iterator;

import org.springframework.stereotype.Repository;

import com.deepblue.domain.Board;

@Repository
public class BoardDao extends BaseDao<Board> {

	private static final String GET_BOARD_NUM = "select count(f.boardId) from Board f";

	public long getBoardNum() {
		Iterator iterator = getHibernateTemplate().iterate(GET_BOARD_NUM);
		return ((Long) iterator.next());
	}
}
