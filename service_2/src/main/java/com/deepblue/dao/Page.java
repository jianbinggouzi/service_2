package com.deepblue.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page implements Serializable {

	private static int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE; // 分页大小

	private long start; // 当前页在data中的位置 从0开始

	private List data; // 数据

	private long totalCount; // 总记录数

	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	public Page(long start, long totalSize, int pageSize, List data) {
		this.start = start;
		this.totalCount = totalSize;
		this.pageSize = pageSize;
		this.data = data;
	}

	/**
	 * 获取总页数 从1开始
	 * 
	 * @return
	 */
	public long getTotalPageCount() {
		return (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
	}

	/**
	 * 获取当前页码 从1开始
	 * 
	 * @return
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 是否有下一页
	 * 
	 * @return
	 */
	public boolean isHasNextPage() {
		return getCurrentPageNo() < getTotalPageCount();
	}

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return getCurrentPageNo() > 1;
	}

	/**
	 * 获取所指定的页的第一条数据在data中的位置
	 * 
	 * @param pageNo
	 * @return
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	public static int getDEFAULT_PAGE_SIZE() {
		return DEFAULT_PAGE_SIZE;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getStart() {
		return start;
	}

	public List getData() {
		return data;
	}

	public long getTotalCount() {
		return totalCount;
	}

}
