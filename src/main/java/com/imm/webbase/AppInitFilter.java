package com.imm.webbase;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.imm.common.base.context.ApplicationContext;
import com.imm.common.log.Log4jConfig;
import com.imm.common.log.LogFactory;
import com.imm.common.util.property.PropertiesUtil;

 

public class AppInitFilter implements Filter {
	
	public void init(FilterConfig config) throws ServletException {
		//init application context
		String rootDirPath = config.getServletContext().getRealPath("/");
		String appFilePath = config.getServletContext().getInitParameter("appConfigLocation");
		String springConfigPath = config.getServletContext().getInitParameter("contextConfigLocation");
		ApplicationContext.admin.initialize(rootDirPath, appFilePath, springConfigPath);

		//init log4j configuration
		Log4jConfig.configure(ApplicationContext.admin.getLog4jConfigFile());
		LogFactory.getLogger(AppInitFilter.class)
				.info("log4j file path : " + ApplicationContext.admin.getLog4jConfigFile());
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		this.setCommonUrl(request);
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {
		LogFactory.getLogger(AppInitFilter.class).debug("AppInitFilter destroyed!");
	}
	
	/**
	 * 获取到公用的一些URL，并放到request中.
	 * @param request
	 */
	private void setCommonUrl(ServletRequest request) {
		request.setAttribute("staticServer", "http://" + PropertiesUtil.admin.staticServer);
		request.setAttribute("staticVersion", PropertiesUtil.admin.staticVersion);
		request.setAttribute("homePage", getHomePage(request));
	}
	
	/**
	 * 获取主站的URL地址。
	 * @param request
	 * @return
	 */
	private String getHomePage(ServletRequest request) {
		final HttpServletRequest req = (HttpServletRequest)request;
		StringBuffer url = req.getRequestURL();
		String homePage = "http://www.jrjia.cn";
		int index = url.indexOf("manage");
		if (index != -1) {
			homePage = url.substring(0, index-1);
			homePage = homePage.replace("admin", "www");
		}
		return homePage;
	}
}