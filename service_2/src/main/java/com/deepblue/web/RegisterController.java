package com.deepblue.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.deepblue.domain.User;
import com.deepblue.exception.UserExistException;
import com.deepblue.service.UserService;

@Controller
public class RegisterController extends BaseController {

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
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

}
