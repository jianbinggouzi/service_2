package com.deepblue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepblue.dao.BoardDao;

@Service
public class BoardService {

	private BoardDao boardDao;

	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	/**
	 * 获取所有板块
	 * 
	 * @return
	 */
	public List getAllBoards() {
		return boardDao.loadAll();
	}

}
