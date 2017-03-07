package com.imm.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

 
public class CalculateUtil {

	
	/**
	 * float 相加
	 * @param dd
	 * @return
	 */
    public static float floatAdd(float... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (float n : dd) {
            result = result.add(new BigDecimal(Float.toString(n)));
        }
        return result.floatValue();
    }
    
    /**
     * float 相加 HALF_EVEN
     * @param scale
     * @param dd
     * @return
     */
    public static float floatAdd(int scale ,float... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (float n : dd) {
            result = result.add(new BigDecimal(Float.toString(n)));
        }
        return result.setScale(scale, RoundingMode.HALF_EVEN).floatValue();
    }

    /**
     * float 相加 
     * @param scale
     * @param mode
     * @param dd
     * @return
     */
    public static float floatAdd(int scale, RoundingMode mode ,float... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (float n : dd) {
            result = result.add(new BigDecimal(Float.toString(n)));
        }
        return result.setScale(scale, mode).floatValue();
    }
    
    /**
     * float相加
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static float floatAdd(float d1, float d2) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        return bd1.add(bd2).floatValue();
    }
    
    /**
     * 相加 取位 d1+d2 n.scale HALF_EVEN
     * 
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static float floatAdd(float d1, float d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        return bd1.add(bd2).setScale(scale, RoundingMode.HALF_EVEN).floatValue();

    }
    
    /**
     * 相加 取位 d1+d2 n.scale
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static float floatAdd(float d1, float d2, int scale, RoundingMode mode) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        return bd1.add(bd2).setScale(scale, mode).floatValue();

    }
    
    /**
     * float 相减
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleSubtract(Double d1, Double d2) {
        return new BigDecimal(Double.toString(d1)).subtract(new BigDecimal(Double.toString(d2))).doubleValue();
    }
    
    /**
	 * float 相减
	 * d1 - d2
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static float floatSubtract(float d1, float d2, int scale) {
    	BigDecimal b = new BigDecimal(Float.toString(d1)).subtract(new BigDecimal(Float.toString(d2)));
    	return b.setScale(scale, RoundingMode.HALF_EVEN).floatValue();
    }

    /**
     * float 相减
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static float floatSubtract(float d1, float d2, int scale, RoundingMode mode) {
    	BigDecimal b = new BigDecimal(Float.toString(d1)).subtract(new BigDecimal(Float.toString(d2)));
    	return b.setScale(scale, mode).floatValue();
    }

    /**
     * 格式化数字
     * @param d1
     * @param scale
     * @return
     */
    public static float formatNubmer(float d1, int scale) {
        BigDecimal bd = new BigDecimal(Float.toString(d1));
        return bd.setScale(scale, RoundingMode.HALF_EVEN).floatValue();
    }
    
    /**
     * 格式化数字 含取舍条件
     * @param d
     * @param mode
     * @param d1
     * @return
     */
    public static float formatNubmer(float d1, int scale, RoundingMode mode) {
        BigDecimal bd = new BigDecimal(Float.toString(d1));
        return bd.setScale(scale, mode).floatValue();
    }
    
    /**
     * 格式化数字
     * @param d1
     * @param scale
     * @param formatter
     * @return
     */
    public static String formatNubmer(float d1, int scale, String formatter) {
        BigDecimal bd = new BigDecimal(Float.toString(d1));
        float dd = bd.setScale(scale, RoundingMode.HALF_EVEN).floatValue();
        DecimalFormat df = new DecimalFormat(formatter);
        return df.format(dd);
    }
    
    /**
     * 格式化数字
     * @param d1
     * @param scale
     * @param mode
     * @param formatter
     * @return
     */
    public static String formatNubmer(float d1, int scale, RoundingMode mode, String formatter) {
        BigDecimal bd = new BigDecimal(Float.toString(d1));
        float dd = bd.setScale(scale, mode).floatValue();
        DecimalFormat df = new DecimalFormat(formatter);
        return df.format(dd);
    }

    /**
     * 货币形式格式字符串 四舍五入
     * @param ####,###0.00
     * @param value
     */
    public static String formatNumberWithString(String formatter, String value) {
        BigDecimal bd = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat(formatter);
        return df.format(bd);
    }
    
    /**
     * 相除
     * @param d1
     * @param d2
     * @return
     */
    public static float floatDivide(float d1, float d2) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        return bd1.divide(bd2).floatValue();
    }
    
    /**
     * 相除 取位 d1/d2 n.scale HALF_EVEN
     * 
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static float floatDivide(float d1, float d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        BigDecimal bd3 = bd1.divide(bd2, 10, RoundingMode.HALF_EVEN);
        return bd3.setScale(scale, RoundingMode.HALF_EVEN).floatValue();
    }
    
    /**
     * 相除 取位 d1/d2 n.scale
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static float floatDivide(float d1, float d2, int scale, RoundingMode mode) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        BigDecimal bd3 = bd1.divide(bd2, 10, mode);
        return bd3.setScale(scale, mode).floatValue();
    }
    
    /**
     * 相乘
     * @param d1
     * @param d2
     * @return
     */
    public static float floatMultiply(float d1, float d2) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        return bd1.multiply(bd2).floatValue();
    }

    /**
     * 相乘  取位 d1*d2 n.scale HALF_EVEN
     * 
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static float floatMultiply(float d1, float d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        return bd1.multiply(bd2).setScale(scale, RoundingMode.HALF_EVEN).floatValue();
    }
    
    /**
     * 相乘 取位 d1*d2 n.scale
     * @param d1
     * @param d2
     * @param scale
     * @param mode
     * @return
     */
    public static float floatMultiply(float d1, float d2, int scale, RoundingMode mode) {
        BigDecimal bd1 = new BigDecimal(Float.toString(d1));
        BigDecimal bd2 = new BigDecimal(Float.toString(d2));
        return bd1.multiply(bd2).setScale(scale, mode).floatValue();
    }
    
    /**
     * 将整数转化为四位数字，从后面开始追加，前面不足的补0 如1 返回0001
     * @param number
     * @return
     */
    public static String formatFourNumber(int number) {
    	NumberFormat nf = new DecimalFormat("0000");
        return nf.format(number);
    }

    public static void main(String[] args) {
         String b = formatNumberWithString("#.##","32.535641");
         System.out.println(b);
         
         System.out.println("\\\"");
    }

}
