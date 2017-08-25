package com.hxy.base.utils;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换工具类
 * 时间转换工具类
 * @author shuang
 *
 */
public final class DateConverUtil {
	/**
	 * 当前该系统提供的一些日期时间格式——————使用的是枚举
	 */
	public enum TimeType {
		/**"yyyy-MM-dd HH:mm:ss"*/type1("yyyy-MM-dd HH:mm:ss")
		,/**"yyyy/MM/dd HH:mm:ss"*/type2("yyyy/MM/dd HH:mm:ss")
		,/**"yyyy.MM.dd HH:mm:ss"*/type3("yyyy.MM.dd HH:mm:ss")
		,/**"yyMMdd HH:mm:ss"*/type4("yyyyMMdd HH:mm:ss")
		,/**"yyyy年MM月dd日 HH:mm:ss"*/type5("yyyy年MM月dd日 HH:mm:ss")
		
		,/**"yyyy-MM-dd HH:mm"*/type11("yyyy-MM-dd HH:mm")
		,/**"yyyy/MM/dd HH:mm"*/type21("yyyy/MM/dd HH:mm")
		,/**"yyyy.MM.dd HH:mm"*/type31("yyyy.MM.dd HH:mm")
		,/**"yyMMdd HH:mm"*/type41("yyyyMMdd HH:mm")
		,/**"yyyy年MM月dd日 HH:mm"*/type51("yyyy年MM月dd日 HH:mm")
		
		,/**"yyyy-MM-dd"*/type111("yyyy-MM-dd")
		,/**"yyyy/MM/dd"*/type211("yyyy/MM/dd")
		,/**"yyyy.MM.dd"*/type311("yyyy.MM.dd")
		,/**"yyMMdd"*/type411("yyyyMMdd")
		,/**"yyyy年MM月dd日"*/type511("yyyy年MM月dd日")
		
		,/**"yyyy-MM-dd"*/type1111("yyyy-MM")
		,/**"yyyy/MM/dd"*/type2111("yyyy/MM")
		,/**"yyyy.MM.dd"*/type3111("yyyy.MM")
		,/**"yyMMdd"*/type4111("yyyyMM")
		,/**"yyyy年MM月dd日"*/type5111("yyyy年MM月")
		
		,/**"yyyy-MM-dd"*/type11111("yyyy")
		,/**"HH:mm:ss"*/type6("HH:mm:ss");
		
		private final String value;
		public String getValue() {
			return value;
		}
		TimeType(String value) {
			this.value = value;
		}
	}

	/**
	 * 参数设定或系统特定格式的字符串 转换成   日期时间格式
	 * @param sdate 传入的字符串。例如：2013-11-05 
	 * @param toTypes 传入的字符串时间类型格式，例如：yyyy-MM-dd HH:mm:ss 
	 * @return Date日期对象
	 * 
	 *  示例：String time="2013-11-01 11:22";
	 *	 DateConverUtil.dd1(time);或者DateConverUtil.dd1(time,"yyyy-MM-dd HH:mm");
	 */
	public static Date getDbyST(String sdate, String... toTypes){
		Date date=null;
		if(StringUtils.hasText(sdate)){
			boolean a=true;
			if(a)
				for(String toType:toTypes){
					try {
						date=getDateFormat(toType).parse(sdate);
						a=false;
						break;
					} catch (Exception e) {
						continue;
					}
				}
			if(a)
				for(TimeType toType: TimeType.values()){
					try {
						date=getDateFormat(toType.getValue()).parse(sdate);
						a=false;
						break;
					} catch (Exception e) {
						continue;
					}
				}
		}	
		return date;
	}
	/**
	 * 日期时间格式 转换成  参数设定或系统特定格式的字符串 
	 * @param type 传入的转换成的日期格式   , DateConverUtil.TimeType中有一部分日期时间格式
	 * @return 字符串类型
	 * 
	 * 示例：DateConverUtil.dd2(new Date(),DateConverUtil.TimeType.type2.getValue()) 或者 DateConverUtil.dd2(new Date(),"yyyy-MM-dd H:m:s") ;
	 */
	public static String getSbyDT(Date date, String type){
		try {
			return getDateFormat(type).format(date);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 参数设定或系统特定格式的日期时间字符串  转换成  参数设定字符串格式的日期时间 
	 * @param sdate 传入的日期字符串
	 * @param targetType 传入的转换后的参数设定日期时间 格式
	 * @param sourceType 传入的转换前的日期时间 格式
	 * @return 字符串类型
	 * 
	 * 示例：DateConverUtil.dd3("2015-2-11","yyyy年MM月dd日","yyyy-M-dd") ;
	 */
	public static String getSbySST(String sdate, String targetType, String... sourceType){
		try {
			return getSbyDT(getDbyST(sdate, sourceType),targetType);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 获取当前系统时间
	 * @return
	 */
	public static Date getNowTime(){
		Calendar calendar= Calendar.getInstance();
		return calendar.getTime();
	}
	/**
	 * 用指定的年、月、日构造日期对象
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 日期对象
	 */
	public static Date getTimeByYMD(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);	
		return calendar.getTime();
	}
	/**
	 * 设为严格格式
	 * @param type 传入的格式
	 * @return SimpleDateFormat的对象
	 */
	public static DateFormat getDateFormat(String type){
		DateFormat dateFormat=new SimpleDateFormat(type);
		dateFormat.setLenient(false);
		return dateFormat;
	}
	
	/**
	 * 计算时间
	 * @param date    日期
	 * @param field   类型 如按秒计算为： Calendar.SECOND
	 * @param amount  计算量
	 * @return
	 * @throws Exception
	 * 例子：计算当前时间的前10秒的时间？
	 * TimeCalculate(new Date(), Calendar.SECOND, -10)
	 * 例子：计算当前时间的后10秒的时间？
	 * TimeCalculate(new Date(), Calendar.SECOND, 10)
	 */
	public static Date TimeCalculate(Date date, int field, int amount) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(field, amount);
			return cal.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	/** 
	 * 取得当月天数 
	 * */  
	public static int getCurrentMonthLastDay(){  
	    Calendar a = Calendar.getInstance();
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
	    int maxDate = a.get(Calendar.DATE);
	    return maxDate;  
	} 
	/** 
	 * 得到指定月的天数 
	 * */  
	public static int getMonthLastDay(int year, int month){  
	    Calendar a = Calendar.getInstance();
	    a.set(Calendar.YEAR, year);
	    a.set(Calendar.MONTH, month - 1);
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
	    int maxDate = a.get(Calendar.DATE);
	    return maxDate;  
	}
}
