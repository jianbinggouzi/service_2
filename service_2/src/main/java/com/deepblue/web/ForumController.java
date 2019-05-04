package com.deepblue.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.deepblue.cons.ConfigVars;
import com.deepblue.dao.Page;
import com.deepblue.domain.Dynamics;
import com.deepblue.domain.Letter;
import com.deepblue.domain.Post;
import com.deepblue.domain.User;
import com.deepblue.service.BoardService;
import com.deepblue.service.EntityService;
import com.deepblue.service.UserService;
import com.deepblue.utils.JsonUtils;
import com.google.gson.Gson;

@Controller
@RequestMapping("/forum")
public class ForumController extends BaseController {

	private EntityService entityService;

	private UserService userService;

	private BoardService boardService;

	@Autowired
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

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
	 * 发送评论
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

	/**
	 * 查看指定用户的Forum，通过Session中的user和提交的userId进行比对限制
	 * 
	 * @param request
	 * @param Ftype
	 *            Forum类型：信件 动态 评论
	 * @param userId
	 * @param type
	 *            发出/收到
	 * @return
	 */
	@RequestMapping(path = "/check_user_form")
	public void checkForum(HttpServletRequest request, HttpServletResponse response, int Ftype, String userId, int type,
			int pageNo, int pageSize) {
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		System.out.println("/check_user_form");
		User user = getSessionUser(request);
		User _user = null;
		int flag = 0;// 0为用户查看自己 1为用户查看其他用户
		List resultList = null;
		if (!user.getUserId().equals(userId)) {
			flag = 1;
			try {
				userService.getUserById(userId);
			} catch (Exception e) {
				return;
			}
		}
		if (flag == 0) {
			if (type == ConfigVars.LETTER_TYPE_SEND) {
				resultList = entityService.getPagedSendEntity(Ftype, user, pageNo, pageSize).getData();
			} else if (type == ConfigVars.LETTER_TYPE_RECEIVE) {
				resultList = entityService.getPagedReceiveEntity(Ftype, user, pageNo, pageSize).getData();
			} else if (type == ConfigVars.POST_TYPE_SEND) {
				resultList = entityService.getPagedSendEntity(Ftype, user, pageNo, pageSize).getData();
			} else if (type == ConfigVars.POST_TYPE_RECEIVE) {
				resultList = entityService.getPagedReceiveEntity(Ftype, user, pageNo, pageSize).getData();
			} else if (Ftype == ConfigVars.TYPE_DYNAMICS) {
				resultList = entityService.getPagedSendEntity(Ftype, user, pageNo, pageSize).getData();
			} else if (Ftype == ConfigVars.OPERATE_REWARD) {
				// 查看打赏记录
			} else if (Ftype == ConfigVars.OPERATE_COLLECT) {
				// 查看用户收藏
			}
		} else if (flag == 1) {
			if (type == ConfigVars.LETTER_TYPE_SEND) {
				resultList = entityService.getPagedSendEntity(Ftype, _user, pageNo, pageSize).getData();
			} else if (type == ConfigVars.LETTER_TYPE_RECEIVE) {
				resultList = entityService.getPagedReceiveEntity(Ftype, _user, pageNo, pageSize).getData();
			} else if (Ftype == ConfigVars.TYPE_DYNAMICS) {
				resultList = entityService.getPagedSendEntity(Ftype, _user, pageNo, pageSize).getData();
			} else if (Ftype == ConfigVars.OPERATE_REWARD) {

			} else if (Ftype == ConfigVars.OPERATE_COLLECT) {

			}
		}
		Map<String, List> res = new HashMap<String, List>();
		res.put("result", resultList);
		for (int i = 0; i < resultList.size(); i++) {
			// System.out.println("" + resultList.get(i));

		}
		Gson gson = JsonUtils.getSkipIdGson(new String[] { "password", "lastVisit", "lastIp" }, null);
		try {
			response.getWriter().write(gson.toJson(res));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查看所有评论
	 * 信件和动态中内容为Post类型，评论内容也是Post类型，所以只需EntityService中的postDao，查询时，以post.lastPost进行定位
	 * 
	 * @param response
	 * @param PostId
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("get_comments")
	public void checkEntityComments(HttpServletResponse response, String postId, int pageNo, int pageSize) {
		Page comments = entityService.getComments(postId, pageNo, pageSize);
		Gson gson = new JsonUtils().getSkipIdGson(new String[] {}, null);
		try {
			response.getWriter().write(gson.toJson(comments));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 获取所有板块
	 * 
	 * @param response
	 */
	@RequestMapping("get_all_boards")
	public void getAllBoards(HttpServletResponse response) {
		Gson gson = new JsonUtils().getSkipIdGson(new String[] {}, null);
		Map<String, List> res = new HashMap<String, List>();
		res.put("result", boardService.getAllBoards());
		try {
			response.getWriter().write(gson.toJson(res));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
