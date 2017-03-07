package com.imm.common.base.context;

/**
 * 整个应用系统的常量类.
 * @author laiss
 * @version 1.0
 */
public final class ApplicationConstants {
	
	/** Environment of system running: dev, test or prod */
	public static Short APP_ENV_PROD = 0;
	public static Short APP_ENV_DEV  = 1;
	public static Short APP_ENV_TEST = 2;
	public static Short APP_ENV_BETA = 3;
	
	public static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	/** 降序  */
	public static String DESC_SORT = "DESC";
	/** 升序  */
	public static String ASC_SORT = "ASC";
	
	/** 通联默认单位为 分 ，定义该参数转换为元 */
	public final static float MONEY_UNIT = 100.0f;
	
	/** 元转换为分的单位 */
	public static final int CENT_PER_YUAN = 100;
	
	/** 一年12个月  */
	public final static int MONTHS_PER_YEAR = 12;
	
	/** 一年365天 */
	public final static int DAYS_PER_YEAR = 365;
	
	/** 一个月30天 */
	public final static int DAYS_PER_MONTHS = 30;
		
}
