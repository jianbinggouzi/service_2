package com.deepblue.cons;

public class ConfigVars {

	/**
	 * 用户信息
	 */
	public static final String USER_CONTEXT = "USER_CONTEXT";

	/**
	 * 上一页
	 */
	public static final String LAST_URL = "toUrl";

	/**
	 * 分页大小
	 */
	public static final int PAGE_SIZE = 5;

	/**
	 * 类型：信件
	 */
	public static final int TYPE_LETTER = 0;

	/**
	 * 查询用户发出的信件
	 */
	public static final int LETTER_TYPE_SEND = 0;

	/**
	 * 查询用户收到的信件
	 */
	public static final int LETTER_TYPE_RECEIVE = 1;

	/**
	 * 类型：动态
	 */
	public static final int TYPE_DYNAMICS = 1;

	/**
	 * 类型：文本内容
	 */
	public static final int TYPE_POST = 2;

	/**
	 * 查询用户发出的对话
	 */
	public static final int POST_TYPE_SEND = 2;

	/**
	 * 查询用户收到的对话
	 */
	public static final int POST_TYPE_RECEIVE = 3;

	public static final int OPERATE_REPLIES = 0;

	public static final int OPERATE_SHARE = 1;

	public static final int OPERATE_DIGEST = 2;

	public static final int OPERATE_THANK = 3;

	public static final int OPERATE_COLLECT = 4;

	public static final int OPERATE_REWARD = 5;

	public static final int OPERATE_TIP = 6;

	public static final int OPERATE_DELETE = 7;

	public static final int OPERATE_EDIT = 8;

	public static final String RESULTLIST = "resultList";

}
