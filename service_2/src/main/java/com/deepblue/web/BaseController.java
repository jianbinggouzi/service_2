package com.deepblue.web;

import javax.servlet.http.HttpServletRequest;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.User;

public class BaseController {

	protected static final String ERROR_MSG_KEY = "errMsg";

	/**
	 * 获取保存在Session中的用户数据
	 * 
	 * @param request
	 * @return
	 */
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(ConfigVars.USER_CONTEXT);
	}

	/**
	 * 保存用户数据到Session中
	 * 
	 * @param request
	 * @param user
	 */
	protected void setSessionUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(ConfigVars.USER_CONTEXT, user);
	}

	protected final String getAppbaseUrl(HttpServletRequest request, String url) {
		return request.getContextPath() + url;
	}

}
