package com.imm.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


 
public final class DateUtil {

	  public static final int SECOND = 1000;
	  public static final int MINUTE = SECOND * 60;
	  public static final int HOUR = MINUTE * 60;
	  public static final int DAY = HOUR * 24;
	  public static final int WEEK = DAY * 7;
	  public static final int YEAR = DAY * 365;

	  public static final long GMT_VIETNAM_TIME_OFFSET = HOUR * 7;

	  private static long SERVER_TIME_OFFSET = 0;

	  /*
	   * 日期的格式
	   */
	  /** 日期格式，ddMMyyyy: "dd-MM-yyyy" */
	  public static final String ddMMyyyyStr = "dd-MM-yyyy";
	  
	  /** 日期格式，yyyyMMdd: "yyyy-MM-dd" */
	  public static final String yyyyMMddStr = "yyyy-MM-dd";
	  
	  /** 日期格式，yyyyMMdd: "yyyy-M-d" */
	  public static final String yyyyMdStr = "yyyy-M-d";

	  /** 日期格式，yyyyMMddhhmmss: "yyyy-MM-dd HH:mm:ss" */
	  public static final String yyyyMMddhhmmssStr = "yyyy-MM-dd HH:mm:ss";
	  
	  /** 日期格式，yyyyMMddhhmmsss: "yyyy-MM-dd HH:mm:ss:s" */
	  public static final String yyyyMMddhhmmsssStr = "yyyy-MM-dd HH:mm:ss:s";
	  
	  /** 日期格式，MMddhhmm: "MM-dd HH:mm" */
	  public static final String MMddhhmmStr = "MM-dd HH:mm";
	  
	  /** 日期格式，yyyymmddhhmm: "yyyy-MM-dd HH:mm" */
	  public static final String yyyymmddhhmmStr = "yyyy-MM-dd HH:mm";
	  
	  /** 日期格式，yy: "yy" */
	  public static final String yyStr = "yy";
	  
	  /** 日期格式，yyyyMM: "yyyy-MM" */
      public static final String yyyyMM = "yyyyMM";
      
      /** 日期格式，yyyyMMdd: "yyyyMMdd" */
      public static final String yyyyMMdd = "yyyyMMdd";
	  
	  private static DateFormat ddMMyyyyFormat = new SimpleDateFormat(ddMMyyyyStr);
	  private static DateFormat yyyyMMddFormat = new SimpleDateFormat(yyyyMMddStr);
	  private static DateFormat yyyyMdFormat = new SimpleDateFormat(yyyyMdStr);
	  private static DateFormat yyyymmddhhmmssFormat = new SimpleDateFormat(
	  		yyyyMMddhhmmssStr );
	  
	  private static DateFormat yyyymmddhhmmsssFormat = new SimpleDateFormat(
	  		yyyyMMddhhmmsssStr );
	  
	  private static DateFormat mmddhhmmFormat = new SimpleDateFormat(
	  		MMddhhmmStr );
	  private static DateFormat yyyymmddhhmmFormat = new SimpleDateFormat(
	  		yyyymmddhhmmStr );
	  private static DateFormat yyFormat = new SimpleDateFormat(
	  		yyStr );
	  private static DateFormat dateFormat = SimpleDateFormat.getDateInstance(
	      SimpleDateFormat.DEFAULT);
	  private static DateFormat datetimeFormat = SimpleDateFormat.
	      getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.DEFAULT);
	  
	  private static DateFormat yyyyMMFormat = new SimpleDateFormat(
	          yyyyMM );
	  
	  private static DateFormat yyyyMMdd_Format = new SimpleDateFormat(yyyyMMdd);
	  
	  private DateUtil() {
	  }

	  public static String getDateDDMMYYYY(Date date) {
	  	if( date != null ){
	  		return ddMMyyyyFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }
	  /**
	   * 给date添加(days,hours,minutes , seconds)时间偏移
	   * @param date
	   * @param days
	   * @param hours
	   * @param minutes
	   * @param seconds
	   * @return Date
	   */
	  public static Date Add(Date date , int days , int hours , int minutes , int seconds) {

	      Date dt = date;
	      if(dt != null)
	      {
	      	Calendar calendar = Calendar.getInstance(); 
	      	calendar.setTime(dt); 
	    
			if(days != 0)
			{
			    calendar.add(Calendar.DATE,days);	
			}
			if(hours != 0)
			{
			    calendar.add(Calendar.HOUR ,hours);	
			}
			if(minutes != 0)
			{
			    calendar.add(Calendar.MINUTE ,minutes);	
			}
			if(seconds != 0)
			{
			    calendar.add(Calendar.SECOND ,seconds);	
			}
			dt = calendar.getTime() ;
	      }
	      return dt;
	  }
	  
	  /**
	   * 得到 2005-01-01 00:00:00的短日期
	   * @param date
	   * @return
	   */
	  public static Date getShortDate(Date date) {
		  	if( date != null ){
		        Date dt = (Date)date.clone() ;
		        
		        dt.setHours( 0);
		        dt.setMinutes( 0);
		        dt.setSeconds( 0);

		  		return dt;
		  	} else {
		  		return null;
		  	}
		  }
	  
	  public static String getDateYYYYMMDD(Date date) {
	  	if( date != null ){
	  		return yyyyMMddFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }

	  public static String getDateYYYYMD(Date date) {
		  	if( date != null ){
		  		return yyyyMdFormat.format(date);
		  	} else {
		  		return "";
		  	}
		  }

	  public static String getDateYYYYMMDDHHMMSSS(Date date ){
	  	if( date != null ){
	  		return yyyymmddhhmmsssFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }

	  public static String getDateYYYYMMDDHHMMSS(Date date) {
	  	if( date != null ) {
	  		return yyyymmddhhmmssFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }
	  
	  public static String getDateYY(Date date) {
	  	if( date != null ) {
	  		return yyFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }

	  public static String getDateSearch(Date date) {
	  	if( date != null ) {
	  		return mmddhhmmFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }

	  public static String getDateNoSencond(Date date) {
	  	if( date != null ) {
	  		return yyyymmddhhmmFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }
	  
	  public static String getYearAndMonth(Date date) {
	        if( date != null ) {
	            return yyyyMMFormat.format(date);
	        } else {
	            return "";
	        }
	      }
	  
	  public static String getDateyyyyMMddStr(Date date) {
          if( date != null ) {
              return yyyyMMdd_Format.format(date);
          } else {
              return "";
          }
        }
	  
	  public static String formatDate(Date date) {
	  	if( date != null ) {
	  		return dateFormat.format(date);
	  	} else {
	  		return "";
	  	}
	    
	  }

	  public static String formatDateTime(Date date) {
	  	if( date != null ) {
	  		return datetimeFormat.format(date);
	  	} else {
	  		return "";
	  	}
	  }

	  public static Timestamp getCurrentGMTTimestamp() {
	    return new Timestamp(System.currentTimeMillis() - SERVER_TIME_OFFSET);
	  }

	  public static void updateCurrentGMTTimestamp(Timestamp timeToUpdate) {
	    timeToUpdate.setTime(System.currentTimeMillis() - SERVER_TIME_OFFSET);
	  }

	  public static Date getVietnamDateFromGMTDate(Date date) {
	    return new Date(date.getTime() + GMT_VIETNAM_TIME_OFFSET);
	  }

	  public static Date convertGMTDate(Date gmtDate, int hourOffset) {
	    return new Date(gmtDate.getTime() + hourOffset * HOUR);
	  }

	  public static Timestamp convertGMTTimestamp(Timestamp gmtTimestamp,
	                                              int hourOffset) {
	    return new Timestamp(gmtTimestamp.getTime() + hourOffset * HOUR);
	  }

	  public static Timestamp getCurrentGMTTimestampExpiredYear(int offsetYear) {
	    //return new Timestamp(System.currentTimeMillis() + offsetYear*(365*24*60*60*1000));
	    Calendar now = Calendar.getInstance();
	    now.add(Calendar.YEAR, offsetYear);
	    return new Timestamp(now.getTime().getTime());
	  }

	  public static Timestamp getCurrentGMTTimestampExpiredMonth(int offsetMonth) {
	    Calendar now = Calendar.getInstance();
	    now.add(Calendar.MONTH, offsetMonth);
	    return new Timestamp(now.getTime().getTime());
	  }
	  
	  public static Timestamp getCurrentGMTTimestampExpiredDay(int offsetDay) {
		    Calendar now = Calendar.getInstance();
		    now.add(Calendar.DAY_OF_YEAR, offsetDay);
		    return new Timestamp(now.getTime().getTime());
		  }
	  /**
		 * @param Calendar
		 * @return String 2001/12/13 Format
		 */
	    public static String CalendarToStr(Calendar cal) {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	        if (cal != null) {
	            Date date = cal.getTime();
	            return format.format(date);
	        } else {
	            return "";
	        }
	    }

	    /**
		 * @param Calendar
		 * @return a Sunday Calendar in the Week
		 */
	    public static Calendar starCalOfWeek(Calendar day) {
			int temp = day.get(Calendar.DAY_OF_WEEK);
	        switch (temp) {
	            case 1:
	                return day;
	            case 2:
	                day.add(Calendar.DATE, -1);
	                return day;
	            case 3:
	                 day.add(Calendar.DATE, -2);
	                return day;
	            case 4:
	                 day.add(Calendar.DATE, -3);
	                return day;
	            case 5:
	                 day.add(Calendar.DATE, -4);
	                return day;
	            case 6:
	                 day.add(Calendar.DATE, -5);
	                return day;
	            case 7:
	                 day.add(Calendar.DATE, -6);
	                return day;
	            default:
	                return  day;
	        }
	    }

	    /**
		 * @param Calendar
		 * @return a Satday Calendar in the Week
		 */
	    public static Calendar endCalOfWeek(Calendar day) {
			int temp = day.get(Calendar.DAY_OF_WEEK);
	        switch (temp) {
	            case 1:
	                 day.add(Calendar.DATE, 6);
	                return day;
	            case 2:
	                day.add(Calendar.DATE, 5);
	                return day;
	            case 3:
	                day.add(Calendar.DATE, 4);
	                return day;
	            case 4:
	                day.add(Calendar.DATE, 3);
	                return day;
	            case 5:
	                day.add(Calendar.DATE, 2);
	                return day;
	            case 6:
	                day.add(Calendar.DATE, 1);
	                return day;
	            case 7:
	                return day;
	            default:
	                return day;
	        }
	    }

		public static Date StrToDate(String str) {
	        if(str.length() == 0) {
	            return null;
	        }
	        int start = str.indexOf('-');
	        String year = str.substring(0, start);
	        start ++;
	        int start1 = str.indexOf('-', start);
	        String month = str.substring(start, start1);
	        String day = str.substring(start1 + 1);
	        Calendar cal = Calendar.getInstance();

	        //cal.setTime(null);

	        cal.set(Calendar.YEAR, Integer.parseInt(year));
	        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
	        cal.set(Calendar.DATE, Integer.parseInt(day));
	        
	        return new Timestamp( cal.getTime().getTime() );
		}
		
		public static Date StrToDateTime(String str) {
	        if(StringUtils.isEmpty(str)) {
	            return null;
	        }
	        Date date = null;
			try {
				date = yyyymmddhhmmssFormat.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        return date;
		}

		public static Calendar StrToCalendar(String str) {
	        if(str.length() == 0) {
	            return null;
	        }
	        int start = str.indexOf('/');
	        String year = str.substring(0, start);
	        start ++;
	        int start1 = str.indexOf('/', start);
	        String month = str.substring(start, start1);
	        String day = str.substring(start1 + 1);
	        Date date = new Date();
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
	        cal.set(Calendar.DATE, Integer.parseInt(day));
	        return cal;
		}

	    /**
		 * @param Calendar
		 * @return String Sun Mon etc. Format
		 */
	    public static String dayOfWeek(Calendar day) {
			int temp = day.get(Calendar.DAY_OF_WEEK);
	        switch (temp) {
	            case 1:
	                return "Sun";
	            case 2:
	                return "Mon";
	            case 3:
	                return "Tue";
	            case 4:
	                return "Wed";
	            case 5:
	                return "Thu";
	            case 6:
	                return "Fri";
	            case 7:
	                return "Sat";
	            default:
	                return "";
	        }
		}

	    /**
		 * @param String Date Format 2001/12/13
		 * @return String Sun Mon etc. Format
		 */
	    public static String dayOfWeek(String inDay) {
	        Calendar cal = Calendar.getInstance();

	        cal.setTime(StrToDate(inDay));
			int temp = cal.get(Calendar.DAY_OF_WEEK);
	        switch (temp) {
	            case 1:
	                return "Sun";
	            case 2:
	                return "Mon";
	            case 3:
	                return "Tue";
	            case 4:
	                return "Wed";
	            case 5:
	                return "Thu";
	            case 6:
	                return "Fri";
	            case 7:
	                return "Sat";
	            default:
	                return "";
	        }
		}

	    public static Timestamp StrToTimestamp(String timestampStr,String pattern)
	            throws ParseException {
	        java.util.Date date = null;
	        SimpleDateFormat format = new SimpleDateFormat(pattern);
	        try {
	            date = format.parse(timestampStr);
	        } catch (ParseException e) {
	                throw e;
	        }
	        return date == null ? null : new Timestamp(date.getTime());
	    }
	    /**
	     * 将字符串型按一定的格式转换成日期型
	     * @param sDate	//字符串型
	     * @param pattern	//格式
	     * @return	//返回日期型
	     * @throws ParseException	//异常抛出
	     */
	    public static Date StrToDate(String sDate,String pattern)
        	throws ParseException {
		    java.util.Date date = null;
		    SimpleDateFormat format = new SimpleDateFormat(pattern);
		    try {
		        date = format.parse(sDate);
		    } catch (ParseException e) {
		            throw e;
		    }
		    return date == null ? null : date;
		}

	    /**
	     * 将字符串型按一定的格式转换成日期型
	     * @param sDate	//字符串型
	     * @param vPattern	//格式:如果输入值为""，则缺省赋值为"yyyy-MM-dd"
	     * @return	//返回boolean型
	     * @throws ParseException	//异常抛出
	     */
	    public static boolean IsDate(String sDate,String vPattern) {
	    	boolean bDate = true;
	    	String pattern;
	    	
	    	if(vPattern == null || vPattern.length() ==0 ) {
	    	    pattern = "yyyy-MM-dd";
	    	}else {
	    	    pattern = vPattern;
	    	}
	    	
		    java.util.Date date = null;
		    SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
		    try {
		        date = format.parse(sDate);
		        bDate = true;
		    } catch (ParseException e) {
		        bDate = false;  
		    }
	    
		    return bDate;
		}

	    /*
	     * 返回当前日期(时间)
	     */
	    public static Date getNowDateTime()
	    {
	    	Date dnow = new Date();    	
	    	
	    	return dnow;
	    }
	    
	    /*
	     * Liusj 2005-10-27
	     * 为不影响排序,新增和修改产品,包括修改目录,都统一把排序时间
	     * 设置成一个固定的值,'2005-08-19'
	     */
	    public static Date getDateTimeForSortDate()
	    {
	    	Date dnow = new Date();  
	    	dnow = StrToDate( "2005-08-19" );
	    	
	    	return dnow;
	    }
	    
	    /*
	     * 返回当前日期(时间)
	     */
	    public static Calendar getNowCalendar()
	    {
	    	Calendar calendar = Calendar.getInstance();   	
	    	
	    	return calendar;
	    }
	    
	    /**
	     * 取得当前的日期
	     * @return Date //返回当前日期
	     */
	    public static Date getNowDate(){
	    	Date dnow = new Date();
	    	return dnow;

	    	
	    }
	    public static long getLongTime() {
	        return System.currentTimeMillis();
	    }
	    
	    /**
	     * 取得两个时间的间隔，单位：毫秒
	     * @param dFirst	//第一个时间
	     * @param dSecond	//第二个时间
	     * @return long	//返回时间间隔，单位：毫秒
	     */
	    public static long getDateSpace( Date dFirst,Date dSecond){
	    	long lspace = 0;
	    	
	    	if( dFirst != null && dSecond != null ){
	    		lspace = dFirst.getTime() - dSecond.getTime();
	    	}
	    	
	    	return lspace;
	    }
	    
	    /**
	     * 取得两个时间的间隔，单位：秒
	     * @param dFirst	//第一个时间
	     * @param dSecond	//第二个时间
	     * @return long	//返回时间间隔，单位：秒
	     */
	    public static long getDateSpaceSecond( Date dFirst,Date dSecond){
	    	long lspace = 0;
	    	
	    	if( dFirst != null && dSecond != null ){
	    		lspace = (dFirst.getTime() - dSecond.getTime())/1000;
	    	}
	    	
	    	return lspace;
	    }
	    
	    /** 
	     * 获取两个时间的时间查 如1天2小时30分钟
	     * */
	    public static String getDatePoor(Date endDate, Date nowDate) {
	        long nd = 1000 * 24 * 60 * 60;
	        long nh = 1000 * 60 * 60;
	        long nm = 1000 * 60;
	        // long ns = 1000;
	        // 获得两个时间的毫秒时间差异
	        long diff = endDate.getTime() - nowDate.getTime();
	        // 计算差多少天
	        long day = diff / nd;
	        // 计算差多少小时
	        long hour = diff % nd / nh;
	        // 计算差多少分钟
	        long min = diff % nd % nh / nm;
	        // 计算差多少秒//输出结果
	        // long sec = diff % nd % nh % nm / ns;
	        return day + "天" + hour + "小时" + min + "分钟";
	    }
	    
	    /**
	     * 当前时间是否在一个日期时间范围内
	     * @param curDate
	     * @param vStartDate
	     * @param vEndDate
	     * @return
	     */
	    public static boolean between(Date curDate , Date vStartDate , Date vEndDate) {
	        boolean bRet = false;

	        if(curDate != null) {
		        if(vStartDate == null && vEndDate == null) {
		            bRet = true;
		        }else if(vStartDate == null) {
		            if(!curDate.after(vEndDate) ) {
		                bRet = true;
		            }
		        }else if(vEndDate == null) {
		            if(!curDate.before(vStartDate) ) {
		                bRet = true;
		            }
		        }else if(!curDate.before(vStartDate) && !curDate.after(vEndDate) ) {
		            bRet = true;
		        }
	        }
	        
	        return bRet;
	    }

	    /** 以下是Vip Club's Job用到的日期函数 */

	    /**
	     * 取得时间
	     */
	    public static Date getDate(Date date, int time, String status) {
	        Calendar tempdate = Calendar.getInstance();
	        tempdate.setTime(date);
	        if (status.equals("YEAR")) {
	            tempdate.add(Calendar.YEAR, time);
	        } else if (status.equals("MONTH")) {
	            tempdate.add(Calendar.MONTH, time);
	        } else {
	            tempdate.add(Calendar.DATE, time);
	        }
	        Date yesterday = tempdate.getTime();
	        return yesterday;
	    }

	    /**
	     * 常用的格式化日期
	     * 
	     * @param date
	     * @return
	     */
	    public static String getDateTimeNormal(java.util.Date date) {
	        return formatDateByFormat(date, "yyyy-MM-dd HH:mm:ss");
	    }

	    /**
	     * 以指定的格式来格式化日期
	     * 
	     * @param date
	     * @param format
	     * @return
	     */
	    public static String formatDateByFormat(java.util.Date date, String format) {
	        String result = "";
	        if (date != null) {
	            try {
	                SimpleDateFormat sdf = new SimpleDateFormat(format);
	                result = sdf.format(date);
	            } catch (Exception ex) {

	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }

	    /**
	     * 将字符串转换成Double
	     * 
	     * @param obj
	     * @return Double
	     */
	    public static double DNVL(Object obj) {
	        if (obj == null) {
	            return new Double(0.0).doubleValue();
	        }
	        return ((Double) obj).doubleValue();
	    }
	    
	    /**
	     * 时间想减
	     * @param date1 被减数
	     * @param date2 减数
	     * @return
	     */
	    public static long dateSub(Date date1, Date date2){
	    	  if(date2 == null) date2 = new Date();
	    	  long  day=(date1.getTime()-date2.getTime())/(24*60*60*1000);   
//	    	  System.out.println("相差的日期:"   +   day); 
	    	  return day;
	    }
	    /**
	     * 时间想减
	     * @param date1 被减数
	     * @param date2 减数
	     * @return
	     */
	    public static long dateSub(Date date1){
	    	return dateSub(date1, null);
	    }
	    
	    /**
		   * 给date添加月的时间偏移
		   * @param date
		   * @param month 月
		   * @return Date
		   */
		  public static Date AddMonth(Date date , int month) {

		      Date dt = date;
		      if(dt != null)
		      {
		      	Calendar calendar = Calendar.getInstance(); 
		      	calendar.setTime(dt); 
		    
				if(month != 0)
				{
				    calendar.add(Calendar.MONTH ,month);	
				}
				dt = calendar.getTime() ;
		      }
		      return dt;
		  }
		  
		  public static Date AddYear(Date date , int year) {

		      Date dt = date;
		      if(dt != null)
		      {
		      	Calendar calendar = Calendar.getInstance(); 
		      	calendar.setTime(dt); 
		    
				if(year != 0)
				{
				    calendar.add(Calendar.YEAR, year);	
				}
				dt = calendar.getTime() ;
		      }
		      return dt;
		  }
		  
		/**
		 * 获取本周的周一.
		 * @return
		 */
		public static Date getMondayForCurrentWeek(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}
		
		/**
		 * 获取指定时间的零点零分零秒的时间对象。
		 * @param date
		 * @return
		 */
		public static Date getZeroClockOfCurrentDate(Date date) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}
		public static Long getLianLCahsWithFinishTime( ) {
			//到账时间计算：工作日14:00前的提现申请当天到账，14:00以后的申请下一个工作日12点左右到账。
			//非工作日的提现申请下一个工作日12点左右到账
			Calendar cal = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.HOUR_OF_DAY, 14);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			int temp = cal.get(Calendar.DAY_OF_WEEK);
			if(temp == 1  ){// 周日
				cal.set(Calendar.HOUR_OF_DAY, 12);
				cal.add(Calendar.DAY_OF_MONTH,1);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				return cal.getTime().getTime()/1000 ;
			}
			if(temp == 7){ //周六
				cal.set(Calendar.HOUR_OF_DAY, 12);
				cal.add(Calendar.DAY_OF_MONTH,2);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				return cal.getTime().getTime()/1000 ;
			}
			else{
				if(cal.after(cal2)){
					cal.add(Calendar.DAY_OF_MONTH,1);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					return cal.getTime().getTime()/1000 ;   
		        }
				else{
					System.out.println(cal.get(Calendar.HOUR_OF_DAY)) ;
					cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+3);
					return cal.getTime().getTime()/1000 ;
				}
			}
	       
		}
		/**
		 * daySub:方法描述  自然天数的相差值   需要都取0点的时间 再计算
		 * @author zhaojunbao
		 * @param startDate
		 * @param endDate
		 * @return
		 * @since JDK 1.7
		 */
		public static long daySub(Date startDate ,Date endDate){
			 
			 Date date1 = getZeroClockOfCurrentDate(startDate);
			 Date date2 = getZeroClockOfCurrentDate(endDate);
	    	  long  day=(date1.getTime()-date2.getTime())/(24*60*60*1000);   
//	    	  System.out.println("相差的日期:"   +   day); 
	    	  return day;
		}
		
		public static void main(String args[]) throws ParseException {
//			String dateStr = "2015-08-30 23:00:05";
//			Date date = yyyymmddhhmmssFormat.parse(dateStr);
//			Date monday = getMondayForCurrentWeek(date);
//			
//			System.out.println("输入时间:" + dateStr);
//			System.out.println("周一时间:" + yyyymmddhhmmssFormat.format(monday)); 
			
			String dateStr = "20151219153825";
			Date date = yyyymmddhhmmssFormat.parse("2016-03-02 16:22:36");
			DateUtil.formatDateByFormat(date,"yyyyMMddHHmmss");
			
			System.out.println();
			Long ss = getLianLCahsWithFinishTime();
			 SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")  ;
				String result = sdf3.format(new Date(ss*1000l));
				System.out.println(daySub(new Date(),date));
				 
			//	System.out.println(result) ;
		}
}