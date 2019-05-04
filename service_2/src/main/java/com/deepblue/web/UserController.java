package com.deepblue.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.User;
import com.deepblue.exception.UserExistException;
import com.deepblue.service.OperateLogService;
import com.deepblue.service.UserService;
import com.deepblue.utils.JsonUtils;
import com.google.gson.Gson;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private UserService userService;

	private OperateLogService operateLogService;

	@Autowired
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
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
	@RequestMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute(ConfigVars.USER_CONTEXT);
		return "forward:/index.jsp";
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, User user) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/success");
		try {
			userService.register(user);
		} catch (UserExistException e) {
			view.addObject("errorMsg", "此用户名已存在");
			view.setViewName("forward:/register.jsp");
		}
		setSessionUser(request, user);
		return view;
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping("info")
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response, String userId) {
		User user = userService.getUserById(userId);
		Gson gson = JsonUtils.getSkipIdGson(
				new String[] { "userId", "password", "userPhone", "userType", "lastVisit", "lastIp" }, null);
		try {
			response.getWriter().write(gson.toJson(user));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 查看指定用户收到的点赞 感谢 信件回复次数
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping("received_operate")
	public void getUserReceivedOperate(HttpServletRequest request, HttpServletResponse response, String userId) {
		User user = userService.getUserById(userId);
		StringBuilder receiverString = new StringBuilder();
		receiverString.append("{").append("\"digest\":").append("\"")
				.append(operateLogService.getOperateCountByReceiver(userId, ConfigVars.OPERATE_DIGEST)).append("\",")
				.append("\"thanks\":").append("\"")
				.append(operateLogService.getOperateCountByReceiver(userId, ConfigVars.OPERATE_THANK)).append("\",")
				.append("\"replies\":").append("\"")
				.append(operateLogService.getOperateCountByReceiver(userId, ConfigVars.OPERATE_REPLIES)).append("\"")
				.append("}");
	}

}
