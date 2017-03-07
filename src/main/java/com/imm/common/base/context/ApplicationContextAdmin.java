package com.imm.common.base.context;

public class ApplicationContextAdmin extends ApplicationContext {

	private static final String DEFAULT_APP_CONFIG_PATH    = "/WEB-INF/classes/config/admin/app.properties";
	private static final String DEFAULT_LOG4J_CONF_PATH    = "/WEB-INF/classes/config/admin/log4j.xml";
	private static final String DEFAULT_SPRING_CONFIG_PATH = "/WEB-INF/classes/config/admin/application-context.xml";
	private static final String DEFAULT_QUARTZ_CONFIG_PATH = "/WEB-INF/classes/config/admin/application-context-jobs.xml";
	
	@Override
	protected String getAppConfigPath() {
		return DEFAULT_APP_CONFIG_PATH;
	}
	
	@Override
	protected String getSpringConfigPath() {
		return DEFAULT_SPRING_CONFIG_PATH;
	}
	
	@Override
	protected String getQuartzConfigPath() {
		return DEFAULT_QUARTZ_CONFIG_PATH;
	}
	
	@Override
	protected String getLog4jConfigPath() {
		return DEFAULT_LOG4J_CONF_PATH;
	}
}
