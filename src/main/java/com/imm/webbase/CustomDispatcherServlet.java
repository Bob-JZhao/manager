package com.imm.webbase;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * 自定义Spring <code>DispatcherServlet</code>.
 *   主要的目的是共享环境变量：contextConfigLocation.
 * @author laiss
 * @version 1.0
 */
public class CustomDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Overridden method to get "contextConfigLocation" from servlet context, instead of from servlet config.
	 */
	public String getContextConfigLocation() {
		String springConfigPath = getServletContext().getInitParameter("contextConfigLocation");
		if (!StringUtils.isEmpty(springConfigPath)) {
			return springConfigPath;
		}
		return super.getContextConfigLocation();
	}
}
