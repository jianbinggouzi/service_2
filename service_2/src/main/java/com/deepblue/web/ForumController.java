package com.deepblue.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.Dynamics;
import com.deepblue.domain.Letter;
import com.deepblue.domain.Post;
import com.deepblue.service.EntityService;

@Controller
@RequestMapping("/forum")
public class ForumController extends BaseController {

	private EntityService entityService;

	@Autowired
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	/**
	 * 发送信件
	 * 
	 * @param request
	 * @param letter
	 * @return
	 */
	@RequestMapping("/send_letter")
	public ModelAndView sendForum(HttpServletRequest request, Letter letter) {
		ModelAndView view = new ModelAndView();
		view.setViewName("forward:/single-result.jsp");
		view.addObject("result", "success");
		try {
			entityService.addEntity(ConfigVars.TYPE_LETTER, letter, getSessionUser(request));
		} catch (Exception e) {
			view.addObject("result", "failed");
		}
		return view;
	}

	/**
	 * 发送动态
	 * 
	 * @param request
	 * @param dynamics
	 * @return
	 */
	@RequestMapping("/send_dynamics")
	public ModelAndView sendForum(HttpServletRequest request, Dynamics dynamics) {
		ModelAndView view = new ModelAndView();
		view.setViewName("forward:/single-result.jsp");
		view.addObject("result", "success");
		try {
			entityService.addEntity(ConfigVars.TYPE_DYNAMICS, dynamics, getSessionUser(request));
		} catch (Exception e) {
			view.addObject("result", "failed");
		}
		return view;
	}

	/**
	 * 发送内容/评论
	 * 
	 * @param request
	 * @param post
	 * @return
	 */
	@RequestMapping("/send_post")
	public ModelAndView sendPost(HttpServletRequest request, Post post) {
		ModelAndView view = new ModelAndView();
		view.setViewName("forward:/single-result.jsp");
		view.addObject("result", "success");
		try {
			entityService.addEntity(ConfigVars.TYPE_POST, post, getSessionUser(request));
		} catch (Exception e) {
			view.addObject("result", "failed");
		}
		return view;
	}

}
