package com.imm.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;

 import com.imm.business.service.UserMngService;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.base.context.ApplicationContext;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.webbase.AdminConstants;

/**
 * Base class of all controllers.
 * @author laiss
 *
 */
public abstract class BaseController {
	
	protected static final DateFormat dateFormatDefault = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	final static Log log = LogFactory.getLogger(BaseController.class);
			
	protected Integer getPageNumber(Integer pageNum) {
		if (pageNum == null || pageNum.intValue() < 1) {
			pageNum = 1; // default is the first page!
		}
		return pageNum;
	}
	
	protected Integer getPageSize(HttpServletRequest request) {
		String pageSizeStr = request.getParameter("pageSize");
		Integer pageSize = AdminConstants.DEFAULT_PAGESIZE;
		
		if (!StringUtils.isEmpty(pageSizeStr)) {
			pageSize = Integer.valueOf(pageSizeStr);
		}
		return pageSize;
	}
	
	protected String getSearchText(HttpServletRequest request, ModelMap model) {
		String searchText = request.getParameter("searchText");
		if (searchText != null) {
			searchText = searchText.trim();
		}
		model.addAttribute("searchText", searchText);
		return searchText;
	}
	
	protected String getInputParam(HttpServletRequest request, ModelMap model, String paramName) {
		String paramValue = request.getParameter(paramName);
		if (paramValue != null) {
			paramValue = paramValue.trim();
		}
		model.addAttribute(paramName, paramValue);
		return paramValue;
	}
	
	protected Date getDateParam(HttpServletRequest request, ModelMap model, String dateStr) {
		String dateString = getInputParam(request, model, dateStr);
		if (StringUtils.isNotEmpty(dateString)) {
			try {
				return dateFormatDefault.parse(dateString);
			} catch (ParseException pe) {
				model.addAttribute("errMsg", "日期格式错误！" + pe.getLocalizedMessage());
				log.warn("Date format error!", pe);
			}
		}
		return null;
	}
	
	protected HttpSession getRequestSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		return session;
	}
	
	/**
	 * 获取登录用户对象
	 * @return
	 */
	protected UserMngVo getLoginUser() {
		UserDetails userDetails = null;
		final UserMngService userMngService = (UserMngService) ApplicationContext.admin.getSpringBean("userMngService");
		try {
			userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		if (null == userDetails) {
			return null;
		}
		UserMngVo userMngVo = userMngService.getUserByLoginId(userDetails.getUsername());
		if (null == userMngVo) {
			return null;
		}
		return userMngVo;
	}
	
	// output to response
	protected void printContentToResponse(HttpServletResponse response, Object content) {
		try {
			response.getWriter().print(content);
		} catch (IOException ex) {
			log.error("Error happened when printing content to http response!", ex);
		}
	}
}
