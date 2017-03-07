package com.imm.common.util.property;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.imm.common.base.context.ApplicationContext;

 
public class PropertiesUtilAdmin extends PropertiesUtil {

	@Override
	protected String getAppConfigPath() {
		log.info("Get app file path for Admin .....................");
		return "config/admin/app.properties";
	}
	
	@Override
	protected boolean hasModule() {
		FileSystemXmlApplicationContext context = ApplicationContext.admin.getSpringContext();
		return context != null;
	}

}
