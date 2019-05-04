package com.deepblue.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deepblue.cons.ConfigVars;
import com.deepblue.domain.EntityBaseDomain;
import com.deepblue.domain.OperateLog;
import com.deepblue.service.EntityService;
import com.deepblue.service.OperateLogService;

@Controller
@RequestMapping("/operate")
public class OperateController extends BaseController {
	private OperateLogService operateService;

	private EntityService entityService;

	@Autowired
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	@Autowired
	public void setOperateService(OperateLogService operateService) {
		this.operateService = operateService;
	}

	/**
	 * 其他用户对相关实体进行操作并记录 允许的操作类型：点赞\回复\分享\收藏\打赏\感谢
	 * 
	 * @param request
	 * @param response
	 * @param operateLog
	 */
	@RequestMapping("other")
	public void otherOperateOnEntity(HttpServletRequest request, HttpServletResponse response, OperateLog operateLog) {
		if (!getSessionUser(request).getUserId().equals(operateLog.getSender().getUserId()))
			return;

		// 开始记录
		operateService.changeLogOnEntity(operateLog);
		operateService.addOperateLog(operateLog);
		try {
			response.getWriter().write("success");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户自己对相关实体进行操作 允许的操作类型：删除/修改 需要严格检测 修改时,operateLog.text不能为null
	 * 
	 * @param request
	 * @param response
	 * @param operateLog
	 */
	@RequestMapping("self")
	public void selfOperateOnEntity(HttpServletRequest request, HttpServletResponse response, OperateLog operateLog) {
		if (!(getSessionUser(request).getUserId().equals(operateLog.getSender().getUserId()))
				|| !(getSessionUser(request).getUserId().equals(operateLog.getReceiver().getUserId())))
			return;
		EntityBaseDomain entity = entityService.getEntityById(operateLog.getEntityType(), operateLog.getEntityId());
		if (entity == null || !entity.getUser().getUserId().equals(getSessionUser(request).getUserId()))
			return;
		switch (operateLog.getOperateType()) {
		case ConfigVars.OPERATE_EDIT:
			entityService.updatePostOfEntity(operateLog.getEntityType(), entity, operateLog.getText());
			break;
		case ConfigVars.OPERATE_DELETE:
			entityService.removeEntity(operateLog.getEntityType(), entity);
			break;
		}
		try {
			response.getWriter().write("success");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
