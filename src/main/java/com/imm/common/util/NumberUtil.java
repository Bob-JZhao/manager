 package com.imm.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

	/**
	 * 验证是否是数字 
	 * @param str 字符串
	 * @return true 是数字，false 不是
	 */
	public static boolean isNumber(String str){
        String regex = "[0-9]*";
		return validateNum(str, regex);
    }
	
	/**
	 * 验证是否是正小数 
	 * @param str 字符串
	 * @return true 是正小数，false 不是
	 */
	public static boolean isPositiveDecimal(String str) {
		String regex = "[+]{0,1}\\d+\\.\\d*|[+]{0,1}\\d*\\.\\d+";
		return validateNum(str, regex);
	}
	
	
	private static boolean validateNum(String str,String regex){
		Pattern pattern  = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(str);  
		return isNum.matches();
	}
	
}
