package com.imm.common.base.context;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

 import com.imm.common.exception.SystemException;

/**
 * This is a container of application configurations.
 * The config file of this application can be specified in web.xml, or use default
 * value: /WEB-INF/classes/config/{web/admin/wap}app.properties
 * 
 *
 */
public abstract class ApplicationContext {
	
	protected String rootPath = null;
	protected Short systemEnvironment = null;
	protected Properties configItems  = new Properties();
	
	/**
	 * An instance of spring file system application context.
	 */
	protected FileSystemXmlApplicationContext springContext = null;

	public static ApplicationContext admin  = new ApplicationContextAdmin();
	
	/**
	 * do initialization.
	 * 
	 * @param rootDirPath
	 * @param appFilePath
	 * @param springConfigPath
	 */
	public void initialize(String rootDirPath, String appFilePath, String springConfigPath) {
		rootPath = rootDirPath;
		
		//step 1: load application configuration items
		if (StringUtils.isEmpty(appFilePath)) {
			appFilePath = getAppConfigPath();
		}
		final File appFile = new File(rootPath, appFilePath);
		
		try {
			configItems.load(new FileInputStream(appFile));
		} catch (Exception ex) {
			// write logs
			throw new SystemException(
					"Can not get application config file! Normally it should be app.properties.", ex);
		}
		 
		//step 2: initialize spring context
		if (StringUtils.isEmpty(springConfigPath)) {
			springConfigPath = getSpringConfigPath();
		}
		final File fileSpring = new File(rootPath, springConfigPath);
		final File fileQuartz = new File(rootPath, getQuartzConfigPath());
		
		String[] configPathes = null;
		if (fileQuartz.exists()) {
			configPathes = new String[] {"file:" + fileSpring.getAbsolutePath(), "file:" + fileQuartz.getAbsolutePath()};
		} else {
			configPathes = new String[] {"file:" + fileSpring.getAbsolutePath()};
		}
		springContext = new FileSystemXmlApplicationContext(configPathes);

		//step 3: parse properties
		parseProperties(configItems);
	}
	
 	protected abstract String getAppConfigPath();
	
 	protected abstract String getSpringConfigPath();
	
 	protected abstract String getQuartzConfigPath();
	
 	protected abstract String getLog4jConfigPath();

	private void parseProperties(Properties configItems) {
		//system env.
		String systemEnvStr = configItems.getProperty("system.env");
		if (systemEnvStr != null && systemEnvStr.trim().equalsIgnoreCase("prod")) {
			systemEnvironment = ApplicationConstants.APP_ENV_PROD;
		} else if (systemEnvStr != null && systemEnvStr.trim().equalsIgnoreCase("beta")) {
			systemEnvironment = ApplicationConstants.APP_ENV_BETA;
		} else if (systemEnvStr != null && systemEnvStr.trim().equalsIgnoreCase("test")) {
			systemEnvironment = ApplicationConstants.APP_ENV_TEST;
		} else {
			systemEnvironment = ApplicationConstants.APP_ENV_DEV;
		}
	}
	
	/**
	 * get application root path.
	 * @return
	 */
	public String getRootPath() {
		return rootPath;
	}
	
	/**
	 * get Log4j file path. It is full path.
	 * @return
	 */
	public String getLog4jConfigFile() {
		File file = new File(rootPath, getLog4jConfigPath());
		return file.getAbsolutePath();
	}
	
	/**
 	 * @return
	 */
	public Short getSystemEnvironment() {
		return systemEnvironment;
	}
	
	
	/**
	 * get Spring context from which we can get beans.
	 * @return
	 */
	public FileSystemXmlApplicationContext getSpringContext() {
		return springContext;
	}

	/**
	 * Get a bean object from spring container.
	 * @param beanName
	 * @return
	 */
	public Object getSpringBean(String beanName) {
		return springContext.getBean(beanName);
	}
	
	/**
	 * Get a bean object with specified class from spring container.
	 * @param beanName
	 * @param clz
	 * @return a bean object with specified class.
	 */
	public Object getSpringBean(String beanName, Class clz) {
		return springContext.getBean(beanName, clz);
	}
}
