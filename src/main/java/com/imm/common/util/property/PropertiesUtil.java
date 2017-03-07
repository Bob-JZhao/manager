package com.imm.common.util.property;

import java.util.Properties;

import org.springframework.util.StringUtils;

import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;


public abstract class PropertiesUtil {
	
	/** 静�?�文件的版本�?  */
	public String staticVersion = null;
	
	/** 静�?�文件的服务器路�?  */
	public String staticServer = null;

	/** 主站服务器域�?  */
	public String webServer = null;
	
	/** M站服务器域名  */
	public String mobServer = null;
	
	
	/** 是否是生产环�?  */
	public String systemEnv = null;
	
 	public String imagesUrl = null;
	
 	public Integer referrerCookieTime = null;
	
 	public Integer userSessionTime = null;
	
 	public Integer userCaptchaExpire = null;
	
 	public Integer userPasswordExpire = null;
	
 	public String appIos =null;
	public String appAndroid = null;
	
 	public String personalInfo_url = null;
	
 	public String backGround_url = null;
	/** personal_info */
	public String personalInfo_url_temp = null;
	
	/** 后台上传的图片的目录路径  */
	public String backGround_url_temp = null;
	
  
	
	protected final Properties prop = new Properties();
	
	public static PropertiesUtil admin  = new PropertiesUtilAdmin();
	
	final Log log = LogFactory.getLogger(PropertiesUtil.class);
	
	protected PropertiesUtil() {
		
		// 判断是否�?要加载对应模块的属�?�文�?
		if (!hasModule()) {
			return;
		}
		
		// 加载属�?�文�?
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(getAppConfigPath()));
			
			staticVersion = prop.getProperty("static_version");
			
			staticServer = prop.getProperty("static_server");
			
			webServer = prop.getProperty("web_server");
			if (StringUtils.isEmpty(webServer)) {
				webServer = "www.jrjia.cn";
			} else {
				webServer = webServer.trim();
			}
			
			mobServer = prop.getProperty("mob_server");
			if (StringUtils.isEmpty(mobServer)) {
				mobServer = "m.jrjia.cn";
			} else {
				mobServer = mobServer.trim();
			}
			
			systemEnv = prop.getProperty("system.env");
			
			imagesUrl = prop.getProperty("images_url");
			
			String referrerCookieTimeStr = prop.getProperty("referrer.cookie.time");
			if (!StringUtils.isEmpty(referrerCookieTimeStr)) {
				referrerCookieTime = Integer.valueOf(referrerCookieTimeStr.trim());
			}
			
			String userSessionTimeStr = prop.getProperty("user.session.time");
			if (!StringUtils.isEmpty(userSessionTimeStr)) {
				userSessionTime = Integer.valueOf(userSessionTimeStr.trim());
			}
			
			String userCaptchaExpireStr = prop.getProperty("user.captcha.expire");
			if (!StringUtils.isEmpty(userCaptchaExpireStr)) {
				userCaptchaExpire = Integer.valueOf(userCaptchaExpireStr.trim());
			}
			
			String userPasswordExpireStr = prop.getProperty("user.password.expire");
			if (!StringUtils.isEmpty(userPasswordExpireStr)) {
				userPasswordExpire = Integer.valueOf(userPasswordExpireStr.trim());
			}
			
			 
			/** personal_info */
			  personalInfo_url = prop.getProperty("personalInfo_url");
			
			/** 后台上传的图片的目录路径  */
			 backGround_url = prop.getProperty("backGround_url");
			 
			 /** personal_info */
			  personalInfo_url_temp = prop.getProperty("personalInfo_url_temp");
			
			/** 后台上传的图片的目录路径  */
			 backGround_url_temp = prop.getProperty("backGround_url_temp");
			
		} catch (Exception ex) {
			ex.printStackTrace(); // because log4j will be initialized after app.properties loaded.
			log.warn("load app.properties Exception !", ex);
		}
	}
	
	// 获取应用程序配置文件路径
	protected abstract String getAppConfigPath();
	
	// 判断是否�?要加载该模块
	protected abstract boolean hasModule();
	
	// 获取属�?�文件对�?
	protected Properties getProperties() {
		return this.prop;
	}
}
