package com.imm.webbase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.imm.common.base.context.ApplicationConstants;
import com.imm.common.base.context.ApplicationContext;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;

 
public class BaseExceptionHandler implements HandlerExceptionResolver {

	private Log log = LogFactory.getLogger(BaseExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		log.warn("Handle exception:: " + ex.getClass().getName());
		
		Map<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("exception", ex);
		infoMap.put("user", null);
		 
		
		//output exception to string buffer
		StringWriter sbr = new StringWriter();
		ex.printStackTrace(new PrintWriter(sbr));
		
		if (!ApplicationConstants.APP_ENV_PROD.equals(ApplicationContext.admin.getSystemEnvironment())
				&& !ApplicationConstants.APP_ENV_BETA.equals(ApplicationContext.admin.getSystemEnvironment())) {
			request.setAttribute("detailErrorMessage", sbr.toString());
		}
		
		log.error(sbr.toString());
		
		return new ModelAndView("common/error");
	}
}
