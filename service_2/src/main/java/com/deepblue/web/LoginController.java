package com.deepblue.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.User;
import com.deepblue.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
		System.out.println("LoginController:注入userService");
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping("/doLogin")
	public ModelAndView login(HttpServletRequest request, User user) {
		System.out.println("收到登录请求");
		User _user = userService.getUserByUserName(user.getUserName());
		ModelAndView view = new ModelAndView();
		view.setViewName("forward:/login.jsp");
		System.out.println("userName:" + user.getUserName() + " password:" + user.getPassword());
		if (_user == null) {
			view.addObject("errorMsg", "用户不存在");
			System.out.println("用户名不存在");
		} else if (!_user.getPassword().equals(user.getPassword())) {
			view.addObject("errorMsg", "用户名/密码错误");
			System.out.println("用户名/密码错误");
			System.out.println("_userName:" + _user.getUserName() + " _password:" + _user.getPassword());
		} else {

			userService._getUserById(_user.getUserId());
			System.out.println("" + 2 + "-------------");

			System.out.println("-----------");
			_user.setLastIp(request.getRemoteAddr());
			_user.setLastVisit(new Date());
			userService.loginSuccess(_user);
			setSessionUser(request, _user);
			String lastUrl = (String) request.getSession().getAttribute(ConfigVars.LAST_URL);
			request.getSession().removeAttribute(ConfigVars.LAST_URL);
			if (StringUtils.isEmpty(lastUrl)) {
				lastUrl = "/index.jsp";
			}
			view.setViewName("redirect:" + lastUrl);
		}
		return view;
	}

	/**
	 * 用户注销
	 * 
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/doLogout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute(ConfigVars.USER_CONTEXT);
		return "forward:/index.jsp";
	}

}
