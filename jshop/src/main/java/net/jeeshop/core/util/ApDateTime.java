package net.jeeshop.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * 本 API 主要针对<b>日期</b> or <b>时间</b>的运算,时常会用到的,而开发出来的 mrthod<br>
 * 所有提供出来的 method 全部均设为 static<br>
 * 
 * @author 陈彦男
 * @version 1.0
 */
/**
 * @author robert.huang
 *   
 */
/**
 * @author robert.huang
 * 
 */
public class ApDateTime {

	/**
	 * YYYY-MM-dd HH:mm:ss.SSS
	 */
	public final static String DATE_TIME_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String DATE_TIME_Sec = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_TIME_SORT = "yyyyMMddHHmmss";
	public final static String DATE_TIME_YMD = "yyyy-MM-dd";
	public final static String DATE_TIME_YYMMDD = "yyMMdd";
	public final static String DATE_TIME_YYYYMMDD = "yyyyMMdd";
	public final static String DATE_TIME_HHMMSS = "HHmmss";
	public final static String DATE_TIME_YM = "yyyy-MM";
	public final static String DATE_TIME_Y = "yyyy";
	public final static String DATE_TIME_YMDs="yyyy年MM月dd日";//yyyy年MM月dd日
	//private static final Logger log = Logger.getLogger(ApDateTime.class);

	/**
	 * 将公元年的日期转成 java.util.Calendar 的型态回传回去
	 * 
	 * @param calendar
	 *            java.util.Calendar数据型态
	 * @param strDate
	 *            传入的日期,只支持公元年且长度为８码
	 * @return java.util.Calendar
	 * @exception java.lang.Exception
	 * @since 主要提供给内部程序的相关 method 所用
	 * 
	 * <pre>
	 * </pre>
	 */
	static protected java.util.Calendar getDateCalendar(
			java.util.Calendar calendar, String strDate) throws Exception {

		if (strDate != null) {
			calendar.set(java.util.Calendar.YEAR, new Integer(strDate
					.substring(0, 4)).intValue());
			calendar.set(java.util.Calendar.MONTH, new Integer(strDate
					.substring(4, 6)).intValue() - 1);
			calendar.set(java.util.Calendar.DATE, new Integer(strDate
					.substring(6, 8)).intValue());
		}
		return (calendar);
	}

	/**
	 * 将时间转成 java.util.Calendar 的型态回传回去
	 * 
	 * @param calendar
	 *            java.util.Calendar数据型态
	 * @param strTme
	 *            传入的时间
	 * @return java.util.Calendar
	 * @exception java.lang.Exception
	 * @since 主要提供给内部程序的相关 method 所用
	 * 
	 * <pre>
	 * </pre>
	 */
	static protected java.util.Calendar getTimeCalendar(
			java.util.Calendar calendar, String strTme) throws Exception {

		if (strTme != null) {
			calendar.set(java.util.Calendar.HOUR_OF_DAY, new Integer(strTme
					.substring(0, 2)).intValue());
			calendar.set(java.util.Calendar.MINUTE, new Integer(strTme
					.substring(2, 4)).intValue());
			calendar.set(java.util.Calendar.SECOND, new Integer(strTme
					.substring(4, 6)).intValue());
		}
		return (calendar);
	}

	/**
	 * 依 format 的格式编辑"日期"及"时间"
	 * 
	 * @param formatLayout
	 *            编辑的格式,请参照 java.text.SimpleDateFormat 说明
	 * @param strDate
	 *            传入的日期,只支持公元年且长度为８码
	 * @param strTme
	 *            传入的时间,长度为６码
	 * @return java.lang.String
	 * @exception java.lang.Exception
	 * @since 主要是利用 java.text.SimpleDateFormat() 所提供多样的日期及时间编辑格式<br>
	 *        所閞发的 method()<br>
	 *        若传入的 format 格式不在 java.text.SimpleDateFormat 所定义中,会Exception<br>
	 *        若传入的 日期 or 时间长度不符,会Exception<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(&quot;1.yyyy.MM.dd hh:mm:ss &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;yyyy.MM.dd hh:mm:ss&quot;, &quot;20030101&quot;, &quot;120000&quot;) );
	 * 	 System.out.println(&quot;2.yyyy.MM.dd G 'at' hh:mm:ss z &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;yyyy.MM.dd G 'at' hh:mm:ss z&quot;, &quot;20041010&quot;, null) );
	 * 	 System.out.println(&quot;3.EEE, MMM d, ''yy &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;EEE, MMM d, ''yy&quot;, null, &quot;240000&quot;) );
	 * 	 System.out.println(&quot;4.h:mm a &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;h:mm a&quot;, null, &quot;293456&quot;) );
	 * 	 System.out.println(&quot;5.hh 'o''clock' a, zzzz &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;hh 'o''clock' a, zzzz&quot;, null, null) );
	 * 	 System.out.println(&quot;6.K:mm a, z &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;K:mm a, z&quot;, null, null) );
	 * 	 System.out.println(&quot;7.yyyyy.MMMMM.dd GGG hh:mm aaa &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;yyyyy.MMMMM.dd GGG hh:mm aaa&quot;, null, null) );
	 * 	 //  
	 * 	 // 此行的 format &quot;YYY&quot; 因没有support此种格式,会导致 Exception 
	 * 	 System.out.println(&quot;8.YYY.DD HH:MM:SS &gt;&gt;&gt;&gt;&gt;&quot; + mdsDateTime.getDateTime(&quot;YYY.DD HH:MM:SS&quot;, null, null) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  1.yyyy.MM.dd hh:mm:ss &gt;&gt;&gt;&gt;&gt;2003.01.02 12:00:00
	 *  2.yyyy.MM.dd G 'at' hh:mm:ss z &gt;&gt;&gt;&gt;&gt;2004.10.10 公元 at 01:36:45 CST
	 *  3.EEE, MMM d, ''yy &gt;&gt;&gt;&gt;&gt;星期六, 三月 6, '04
	 *  4.h:mm a &gt;&gt;&gt;&gt;&gt;5:34 下午
	 *  5.hh 'o''clock' a, zzzz &gt;&gt;&gt;&gt;&gt;01 o'clock 下午, 中国标准时间
	 *  6.K:mm a, z &gt;&gt;&gt;&gt;&gt;1:36 下午, CST
	 *  7.yyyyy.MMMMM.dd GGG hh:mm aaa &gt;&gt;&gt;&gt;&gt;02004.三月.05 公元 01:36 下午
	 *  e:::java.lang.IllegalArgumentException: Illegal pattern character 'Y'
	 * </pre>
	 */
	static public String getDateTime(String formatLayout, String strDate,
			String strTime) throws Exception {

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar = ApDateTime.getDateCalendar(calendar, strDate);
		calendar = ApDateTime.getTimeCalendar(calendar, strTime);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatLayout);

		return (sdf.format(calendar.getTime()));
	}

	/**
	 * 依 format 的格式编辑"日期"及"时间"
	 * 
	 * @param formatLayout
	 *            编辑的格式,请参照 java.text.SimpleDateFormat 说明
	 * @param Millis
	 *            自 19700101 所经过的秘数
	 * @return java.lang.String
	 * @exception java.lang.Exception
	 * @since 参考 static public String getDateTime(String, String, String) 说明<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(&quot;1.yyyy.MM.dd hh:mm:ss &gt;&gt;&gt;&gt;&gt;&quot; + ApDateTime.getDateTime(&quot;yyyy.MM.dd hh:mm:ss&quot;, 86400000) );
	 * 	 System.out.println(&quot;2.yyyy.MM.dd G 'at' hh:mm:ss z &gt;&gt;&gt;&gt;&gt;&quot; + ApDateTime.getDateTime(&quot;yyyy.MM.dd G 'at' hh:mm:ss z&quot;, ApDateTime.getNowDateTime()) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  1.yyyy.MM.dd hh:mm:ss &gt;&gt;&gt;&gt;&gt;1970.01.02 08:00:00
	 *  2.yyyy.MM.dd G 'at' hh:mm:ss z &gt;&gt;&gt;&gt;&gt;2004.03.26 公元 at 01:50:48 CST
	 * </pre>
	 */
	static public String getDateTime(String formatLayout, long Millis)
			throws Exception {

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTimeInMillis(Millis);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatLayout);
		return (sdf.format(calendar.getTime()));
	}

	/**
	 * 取得自 1970年01月01日00:00 至指定日期所经过的秒数
	 * 
	 * @param strDate
	 *            公元日期
	 * @return long
	 * @exception java.lang.Exception
	 * @since
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println( ApDateTime.getDateTime(&quot;20040101&quot;) );
	 * 	 System.out.println( ApDateTime.getDateTime(&quot;200A0101&quot;) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  1072951039473
	 *  e:::java.lang.NumberFormatException: For input string: &quot;200A&quot;
	 * </pre>
	 */
	static public long getDateTime(String strDate) throws Exception {

		java.util.Calendar c = ApDateTime.getDateCalendar(java.util.Calendar
				.getInstance(), strDate);
		return (c.getTime().getTime());

	}

	/**
	 * 取得自 1970年01月01日00:00:00.000 至指定日期所经过的秒数
	 * 
	 * @param strDate
	 *            公元日期
	 * @return long
	 * @exception java.lang.Exception
	 * @since
	 * 
	 * <pre>
	 * 范例说明: try {
	 * 	System.out.println(ApDateTime.getDateTime(&quot;2004-01-01 11:05:36.323&quot;));
	 * } catch (Exception e) {
	 * 	System.out.println(&quot;e:::&quot; + e);
	 * }
	 * </pre>
	 */
	public static long getDateTimeS(String strDate) throws Exception {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		if (strDate != null) {
			int l = strDate.length();
			calendar.set(java.util.Calendar.YEAR, new Integer(strDate
					.substring(0, 4)).intValue());
			calendar.set(java.util.Calendar.MONTH, new Integer(strDate
					.substring(5, 7)).intValue() - 1);
			calendar.set(java.util.Calendar.DATE, new Integer(strDate
					.substring(8, 10)).intValue());
			if(l>10){//超出10位,解析时分秒
				calendar.set(java.util.Calendar.HOUR_OF_DAY, new Integer(strDate
						.substring(11, 13)).intValue());
				calendar.set(java.util.Calendar.MINUTE, new Integer(strDate
						.substring(14, 16)).intValue());
				calendar.set(java.util.Calendar.SECOND, new Integer(strDate
						.substring(17, 19)).intValue());
			}
			if(l>19){//超出19位 解析毫秒
				calendar.set(java.util.Calendar.MILLISECOND, new Integer(strDate
						.substring(20, 23)).intValue());
			}
		}
		return (calendar.getTime().getTime());

	}

	/**
	 * 把 yyyy-MM-dd HH:mm:ss.SSS的数据转变为long型
	 * 
	 * @return long 返回日期对应的数据值
	 * @throws ParseException
	 */
	public static long getTime(String strDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yyyy-MM-dd HH:mm:ss.SSS");
		java.util.Date date = format.parse(strDate);
		return date.getTime();
	}

	/**
	 * @param strDate
	 *            输入的日期数据 比如 ： 12：00：00
	 * @param pattern
	 *            格式 “HH:mm:dd“
	 * @return long 日期点的数值
	 * @throws ParseException
	 */
	public static long getTime(String strDate, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(pattern);
		java.util.Date date = null;
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 根据日期值(字符串)及格式取得对应的时间对象
	 * 
	 * @param strDate
	 * @param pattern
	 * @return java.utl.Date
	 * @throws ParseException
	 */
	public static Date getDate(String strDate, String pattern)
			throws ParseException {
		if (StrUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		}
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(pattern);
		java.util.Date date = format.parse(strDate);
		return date;
	}

	/**
	 * 取得现在的日期及时间
	 * 
	 * @param formatLayout
	 *            日期及时间的数据格式
	 * @return java.lang.String
	 * @exception java.lang.Exception
	 * @since 取得现在的日期及时间,并依所传入的 format 格式编辑回传回去<br>
	 *        若传入的 format=null 则以内定的格式 "yyyy-MM-dd HH:mm:ss.0" 回传回去<br>
	 *        format 的格式请参考 java.text.SimpleDateFormat()<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println( ApDateTime.getNowDateTime(null) );            
	 * 	 System.out.println( ApDateTime.getNowDateTime(&quot;yyyy.MM.dd G 'at' hh:mm:ss&quot;) );            
	 * 	 //
	 * 	 // 此行的 format &quot;YY&quot; 因没有support此种格式,会导致 Exception 
	 * 	 System.out.println( ApDateTime.getNowDateTime(&quot;YY.MM.dd G 'at' hh:mm:ss&quot;) );            
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  输出结果:
	 *  2004-03-25 10:29:17.0
	 *  2004.03.25 公元 at 10:29:17
	 *  e:::java.lang.IllegalArgumentException: Illegal pattern character 'Y'
	 * </pre>
	 */
	static public String getNowDateTime(String formatLayout) {

		java.text.SimpleDateFormat sdf;
		if (formatLayout == null || formatLayout.length() <= 0)
			sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
		else
			sdf = new java.text.SimpleDateFormat(formatLayout);

		return (sdf.format(new java.util.Date()));
	}

	/**
	 * 取得自 1970年01月01日00:00 至现在所经过的秒数
	 * 
	 * @param none
	 * @return long
	 * @exception java.lang.Exception
	 * @since
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 System.out.println(&quot;now time:&quot;+ApDateTime.getNowDateTime() );
	 *  //
	 *  输出结果:
	 *  now time:1080176416359
	 * </pre>
	 */
	static public long getNowDateTime() {

		return (System.currentTimeMillis());
	}

	/**
	 * 计算两个日期所间隔的天数
	 * 
	 * @param strDate1
	 *            开始的日期
	 * @param strDate2
	 *            结束的日期
	 * @return int
	 * @exception java.lang.Exception
	 * @since 计算 date1 及 date2 两个日期的相差天数, 相差的天数有可能为正值或负值<br>
	 *        若正值表 date2 > date1<br>
	 *        若负值表 date2 < date1<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 System.out.println( &quot;diff=&quot;+ApDateTime.computerDiffDate(&quot;20020101&quot;, &quot;20030102&quot;) );
	 * 	 System.out.println( &quot;diff=&quot;+ApDateTime.computerDiffDate(&quot;20040301&quot;, &quot;20040228&quot;) );
	 * 	 System.out.println( &quot;diff=&quot;+ApDateTime.computerDiffDate(&quot;20041002&quot;, &quot;20041002&quot;) );
	 *  //
	 *  输出结果:
	 *  diff=366
	 *  diff=-2
	 *  diff=0
	 * </pre>
	 */
	static public int computerDiffDate(String strDate1, String strDate2)
			throws Exception {

		// java.text.SimpleDateFormat sdf = new
		// java.text.SimpleDateFormat("yyyyMMdd");

		java.util.Calendar c1 = ApDateTime.getDateCalendar(java.util.Calendar
				.getInstance(), strDate1);
		java.util.Calendar c2 = ApDateTime.getDateCalendar(java.util.Calendar
				.getInstance(), strDate2);

		int day = (int) ((c2.getTime().getTime() - c1.getTime().getTime()) / 86400000);

		return (day);
	}

	/**
	 * 取得与日期 strDate 所差 dayDiff 天数的新日期
	 * 
	 * @param formatLayout
	 *            回传回去新日期的数据格式
	 * @param strDate
	 *            开始日期(格式为yyyyMMDD)
	 * @param dayDiff
	 *            日期的位移差,可为正值or负值
	 * @return java.lang.String
	 * @exception java.lang.Exception
	 * @since 取得 strDate 日期加 dayDiff 天数或 减 dayDiff 天数而得到一个新的日期<br>
	 *        并会以 formatLayout 格式回传<br>
	 *        若传入的 strDate or formatLayout 数据有误会产生一个 Exception<br>
	 *        formatLayout 的格式请参考 java.text.SimpleDateFormat()
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println( ApDateTime.getDateDiff_ForDay(&quot;yyyy/MM/dd&quot;, &quot;20040623&quot;, 10) );
	 * 	 System.out.println( ApDateTime.getDateDiff_ForDay(&quot;yyyy-MM-dd&quot;, &quot;20040301&quot;, -3) );
	 * 	 //
	 * 	 // 此行的 format &quot;2003AAAA&quot; 因没有support此种格式,会导致 Exception 
	 * 	 System.out.println( ApDateTime.getDateDiff(&quot;yyyy-MM-dd&quot;, &quot;2003AAAA&quot;, 4) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  2004/07/03
	 *  2004-02-27
	 *  e:::java.lang.NumberFormatException: For input string: &quot;AA&quot;
	 * </pre>
	 */
	static public String getDateDiff_ForDay(String formatLayout,
			String strDate, int dayDiff) throws Exception {

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar = ApDateTime.getDateCalendar(calendar, strDate);
		calendar.add(java.util.Calendar.DAY_OF_MONTH, dayDiff);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatLayout);

		return (sdf.format(calendar.getTime()));
	}

	public static String getDateDiff_ForHour(String formatLayout,
			String strDate, int hourDiff) throws Exception {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar = ApDateTime.getDateCalendar(calendar, strDate);
		// System.out.println("改边时间"+hourDiff+"strDate:"+strDate);
		calendar.add(java.util.Calendar.HOUR_OF_DAY, hourDiff);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatLayout);

		return (sdf.format(calendar.getTime()));
	}

	/**
	 * 返回已添加指定时间间隔的日期
	 * 
	 * @param interval
	 *            表示要添加的时间间隔("y":年;"d":天;"m":月;如有必要可以自行增加)
	 * @param number
	 *            表示要添加的时间间隔的个数
	 * @param date
	 *            java.util.Date()
	 * @return String yyyy-MM-dd HH:mm:ss格式的日期字串
	 * @author pepsi.liao
	 */
	public static String dateAdd(String interval, int number,
			java.util.Date date) {
		String strTmp = "";
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		// 加若干年
		if (interval.equals("y")) {
			int currYear = gc.get(Calendar.YEAR);
			gc.set(Calendar.YEAR, currYear + number);
		}
		// 加若干月
		else if (interval.equals("m")) {
			int currMonth = gc.get(Calendar.MONTH);
			gc.set(Calendar.MONTH, currMonth + number);
		}
		// 加若干天
		else if (interval.equals("d")) {
			int currDay = gc.get(Calendar.DATE);
			gc.set(Calendar.DATE, currDay + number);
		}
		// 加若小时
		else if (interval.equals("h")) {
			int currDay = gc.get(Calendar.HOUR);
			gc.set(Calendar.HOUR, currDay + number);
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		strTmp = bartDateFormat.format(gc.getTime());
		return strTmp;
	}

	/**
	 * 返回已添加指定时间间隔的日期
	 * 
	 * @param interval
	 *            表示要添加的时间间隔("y":年;"d":天;"m":月;如有必要可以自行增加)
	 * @param number
	 *            表示要添加的时间间隔的个数
	 * @param date
	 *            java.util.Date()
	 * @param dateFormat
	 *            返回的日期格式
	 * 
	 * @return String 默认为yyyy-MM-dd HH:mm:ss.SSS格式的日期字串
	 * @author pepsi.liao
	 */
	public static String dateAdd(String interval, int number,
			java.util.Date date, String dateFormat) {
		String strTmp = "";
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss sss";
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		// 加若干年
		if (interval.equals("y")) {
			int currYear = gc.get(Calendar.YEAR);
			gc.set(Calendar.YEAR, currYear + number);
		}
		// 加若干月
		else if (interval.equals("m")) {
			int currMonth = gc.get(Calendar.MONTH);
			gc.set(Calendar.MONTH, currMonth + number);
		}
		// 加若干天
		else if (interval.equals("d")) {
			int currDay = gc.get(Calendar.DATE);
			gc.set(Calendar.DATE, currDay + number);
		}
		// 加若小时
		else if (interval.equals("h")) {
			int currDay = gc.get(Calendar.HOUR);
			gc.set(Calendar.HOUR, currDay + number);
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(dateFormat);
		strTmp = bartDateFormat.format(gc.getTime());
		return strTmp;
	}

	/**
	 * 返回已添加指定时间间隔的日期
	 * 
	 * @param interval
	 *            表示要添加的时间间隔("y":年;"d":天;"m":月;如有必要可以自行增加)
	 * @param number
	 *            表示要添加的时间间隔的个数
	 * @param date
	 *            java.util.Date()
	 * @param dateStr
	 *            计算的基准日期(字符串格式, 日期格式为: yyyy-MM-dd HH:mm:ss sss)
	 * 
	 * @return String 默认为yyyy-MM-dd HH:mm:ss.SSS格式的日期字串
	 * @author pepsi.liao
	 */
	public static String dateAdd(String interval, int number, String dateStr,
			String dateFormat) {

		SimpleDateFormat bartDateFormat = new SimpleDateFormat(dateFormat);
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss sss";
		}
		Date date = new Date();
		try {
			date = bartDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String strTmp = "";
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		// 加若干年
		if (interval.equals("y")) {
			int currYear = gc.get(Calendar.YEAR);
			gc.set(Calendar.YEAR, currYear + number);
		}
		// 加若干月
		else if (interval.equals("m")) {
			int currMonth = gc.get(Calendar.MONTH);
			gc.set(Calendar.MONTH, currMonth + number);
		}
		// 加若干周
		else if (interval.equals("w")) {
			int currWeek = gc.get(Calendar.WEEK_OF_MONTH);
			gc.set(Calendar.WEEK_OF_MONTH, currWeek + number);
		}
		// 加若干天
		else if (interval.equals("d")) {
			int currDay = gc.get(Calendar.DATE);
			gc.set(Calendar.DATE, currDay + number);
		}
		// 加若小时
		else if (interval.equals("h")) {
			int currDay = gc.get(Calendar.HOUR);
			gc.set(Calendar.HOUR, currDay + number);
		}
		// 加若分钟
		else if (interval.equals("mm")) {
			int currMinute = gc.get(Calendar.MINUTE);
			gc.set(Calendar.MINUTE, currMinute + number);
		}
		// 加若干秒
		else if (interval.equals("s")) {
			int currSecond = gc.get(Calendar.SECOND);
			gc.set(Calendar.SECOND, currSecond + number);
		}
		bartDateFormat = new SimpleDateFormat(dateFormat);
		strTmp = bartDateFormat.format(gc.getTime());
		return strTmp;
	}

	/**
	 * 取得与日期 strDate所差 weekDiff 几个星期的新日期
	 * 
	 * @param formatLayout
	 *            回传回去新日期的数据格式
	 * @param strDate
	 *            开始日期(格式为yyyyMMDD)
	 * @param weekDiff
	 *            日期的位移周差,可为正值or负值
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since 取得 strDate 日期加 weekDiff 周数或 减 weekDiff 周数而得到一个新的日期<br>
	 *        回传的的值放入Vector中格式均为 yyyyMMdd<br>
	 *        回传的 Vector.get(0) 为此周的第一天,会依formatLayout的格式存放<br>
	 *        回传的 Vector.get(1) 为此周的最后一天,会依formatLayout的格式存放<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println( &quot;11&gt;&gt;&quot; + ApDateTime.getDateDiff_ForWeek(&quot;yyyyMMdd&quot;, &quot;20040601&quot;, 2) );
	 * 	 System.out.println( &quot;22&gt;&gt;&quot; + ApDateTime.getDateDiff_ForWeek(&quot;yyyyMMdd&quot;, &quot;20040105&quot;, -4) );
	 * 	 System.out.println( &quot;33&gt;&gt;&quot; + ApDateTime.getDateDiff_ForWeek(&quot;yyyyMMdd&quot;, &quot;20041223&quot;, 2) );
	 * 	 System.out.println( &quot;44&gt;&gt;&quot; + ApDateTime.getDateDiff_ForWeek(&quot;yyyyMMdd&quot;, &quot;200412&quot;, 2) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  11&gt;&gt;[20040613, 20040619]
	 *  22&gt;&gt;[20031207, 20031213]
	 *  33&gt;&gt;[20050102, 20050108]
	 *  e:::java.lang.StringIndexOutOfBoundsException: String index out of range: 8
	 * </pre>
	 */
	static public Vector<String> getDateDiff_ForWeek(String formatLayout,
			String strDate, int weekDiff) throws Exception {

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar = getDateCalendar(calendar, strDate);
		calendar.add(java.util.Calendar.WEEK_OF_MONTH, weekDiff);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatLayout);

		int weekDay = calendar.get(java.util.Calendar.DAY_OF_WEEK);

		String start = getDateDiff_ForDay(
				formatLayout, sdf.format(calendar.getTime()), weekDay * (-1)
						+ 1);
		String end = getDateDiff_ForDay(
				formatLayout, sdf.format(calendar.getTime()), 7 - weekDay);

		Vector<String> vv = new Vector<String>();
		vv.add(start);
		vv.add(end);

		return (vv);
	}

	/**
	 * 取得与日期 strDate所差 monthDiff 几个月的新日期
	 * 
	 * @param formatLayout
	 *            回传回去新日期的数据格式
	 * @param strDate
	 *            开始日期(格式为yyyyMMDD)
	 * @param monthDiff
	 *            日期的月份位移差,可为正值or负值
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since 回传的的值放入Vector中格式均为 yyyyMMdd<br>
	 *        回传的 Vector.get(0) 为此月份的第一天,会依formatLayout的格式在最后多二码为"01"<br>
	 *        回传的 Vector.get(1) 为此月份的最后一天会依formatLayout的格式在最后多二码为此月的最后一天日<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(ApDateTime.getDateDiff_ForMonth(&quot;yyyyMM&quot;, &quot;20040623&quot;, 2) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForMonth(&quot;yyyy-MM-&quot;, &quot;20040301&quot;, -3) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForMonth(&quot;yyyyMM&quot;, &quot;200403&quot;, -3) );
	 * 	 //
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  [20040801, 20040831]
	 *  [2003-12-01, 2003-12-31]
	 *  e:::java.lang.StringIndexOutOfBoundsException: String index out of range: 8
	 * </pre>
	 */
	static public Vector<String> getDateDiff_ForMonth(String formatLayout,
			String strDate, int monthDiff) throws Exception {

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar = ApDateTime.getDateCalendar(calendar, strDate);
		calendar.add(java.util.Calendar.MONTH, monthDiff);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatLayout);

		int maxDay = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

		Vector<String> vv = new Vector<String>();
		vv.add(sdf.format(calendar.getTime()) + "01");
		vv.add(sdf.format(calendar.getTime()) + new Integer(maxDay));

		return (vv);
	}

	/**
	 * 取得与日期 strDate所差 yearDiff 几年的新日期
	 * 
	 * @param formatLayout
	 *            回传回去新日期的数据格式
	 * @param strDate
	 *            开始日期(格式为yyyyMMDD)
	 * @param yearDiff
	 *            日期的月份位移差,可为正值or负值
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since 回传的的值放入Vector中格式均为 yyyyMMdd<br>
	 *        回传的 Vector.get(0) 为此年的第一天,会依formatLayout的格式在最后多二码为"0101"<br>
	 *        回传的 Vector.get(1) 为此年的最后一天会依formatLayout的格式在最后多二码为"1231"<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(ApDateTime.getDateDiff_ForYear(&quot;yyyy&quot;, &quot;20041011&quot;, 5) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForYear(&quot;yyyy&quot;, &quot;20041011&quot;, -3) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForYear(&quot;yyyy&quot;, &quot;200410&quot;, -3) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  [20090101, 20091231]
	 *  [20010101, 20011231]
	 *  e:::java.lang.StringIndexOutOfBoundsException: String index out of range: 8
	 * </pre>
	 */
	static public Vector<String> getDateDiff_ForYear(String formatLayout,
			String strDate, int yearDiff) throws Exception {

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar = ApDateTime.getDateCalendar(calendar, strDate);
		calendar.add(java.util.Calendar.YEAR, yearDiff);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatLayout);

		Vector<String> vv = new Vector<String>();
		vv.add(sdf.format(calendar.getTime()) + "0101");
		vv.add(sdf.format(calendar.getTime()) + "1231");

		return (vv);
	}

	/**
	 * 取得与日期 startDate 与 endDate 每隔 diffDay 的 formatLayout 的格式
	 * 
	 * @param formatLayout
	 *            回传回去新日期的数据格式
	 * @param startDate
	 *            开始日期(格式为yyyyMMDD)
	 * @param endDate
	 *            开始日期(格式为yyyyMMDD)
	 * @param diffDay
	 *            间隔的天数,要为正值
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since 回传的的值放入Vector中格式依 formatLayout<br>
	 *        放入 Vector 的数据不会有一样的(因程序会将其处理掉)<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(ApDateTime.getDateDiff_ForUserDefine(&quot;MM/dd&quot;, &quot;20041011&quot;, &quot;20041121&quot;, 10) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForUserDefine(&quot;yyyy/MM/dd&quot;, &quot;20041211&quot;, &quot;20050121&quot;, 7) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForUserDefine(&quot;yyyy-MM&quot;, &quot;20041210&quot;, &quot;20050416&quot;, 25) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForUserDefine(&quot;yyyy&quot;, &quot;20041210&quot;, &quot;20090416&quot;, 365) );
	 * 	 System.out.println(ApDateTime.getDateDiff_ForUserDefine(&quot;yyyyMMdd&quot;, &quot;2004&quot;, &quot;20090909&quot;, 365) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 } 
	 *  //
	 *  输出结果:
	 *  [10/11, 10/21, 10/31, 11/10, 11/20]
	 *  [2004/12/11, 2004/12/18, 2004/12/25, 2005/01/01, 2005/01/08, 2005/01/15]
	 *  [2004-12, 2005-01, 2005-02, 2005-03, 2005-04]
	 *  [2004, 2005, 2006, 2007, 2008]
	 *  e:::java.lang.StringIndexOutOfBoundsException: String index out of range: 6
	 * </pre>
	 */
	static public Vector<String> getDateDiff_ForUserDefine(String formatLayout,
			String startDate, String endDate, int jumpDay) throws Exception {

		java.util.Calendar endCalendar = java.util.Calendar.getInstance();
		endCalendar = ApDateTime.getDateCalendar(endCalendar, endDate);
		endCalendar.add(java.util.Calendar.DAY_OF_MONTH, 0);

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String endString = sdf.format(endCalendar.getTime());

		java.util.Hashtable<String, String> ht = new java.util.Hashtable<String, String>();
		Vector<String> vv = new Vector<String>();
		jumpDay = (jumpDay <= 0) ? 1 : jumpDay;
		for (int i = 0; i < 10000; i += jumpDay) {
			java.util.Calendar startCalendar = java.util.Calendar.getInstance();
			startCalendar = ApDateTime
					.getDateCalendar(startCalendar, startDate);
			startCalendar.add(java.util.Calendar.DAY_OF_MONTH, i);

			String startString = sdf.format(startCalendar.getTime());
			if (endString.compareTo(startString) < 0)
				break;

			String str = new java.text.SimpleDateFormat(formatLayout)
					.format(startCalendar.getTime());
			if (ht.put(str, "") == null)
				vv.add(str);
		}

		return (vv);
	}

	/**
	 * 将公元年４码转换成民国年３码
	 * 
	 * @param strDate
	 *            公元年
	 * @param startPos
	 *            公元年,的起始位置
	 * @param endPos
	 *            公元年,的结束位置
	 * @return java.lang.String
	 * @exception java.lang.Exception
	 * @since
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(&quot;11&gt;&gt;&quot;+com.grandsys.commons.ApDateTime.changeYYtoYYYY(&quot;0901122&quot;, 0, 3));
	 * 	 System.out.println(&quot;22&gt;&gt;&quot;+com.grandsys.commons.ApDateTime.changeYYtoYYYY(&quot;90-11-22&quot;, 0, 2));
	 * 	 System.out.println(&quot;33&gt;&gt;&quot;+com.grandsys.commons.ApDateTime.changeYYtoYYYY(&quot;1/11/22&quot;, 0, 1));
	 * 	 System.out.println(&quot;44&gt;&gt;&quot;+com.grandsys.commons.ApDateTime.changeYYtoYYYY(&quot;-10/11/22&quot;, 0, 3));
	 * 	
	 * 	 System.out.println(&quot;AA&gt;&gt;&quot;+com.grandsys.commons.ApDateTime.changeYYYYtoYY(&quot;2004/11/22&quot;, 0, 4));
	 * 	 System.out.println(&quot;BB&gt;&gt;&quot;+com.grandsys.commons.ApDateTime.changeYYYYtoYY(&quot;1834/11/22&quot;, 0, 4));
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;&gt;&gt;&quot;+e);            
	 * 	 }
	 *  //
	 *  输出结果:
	 *  11&gt;&gt;20011122
	 *  22&gt;&gt;2001-11-22
	 *  33&gt;&gt;1912/11/22
	 *  44&gt;&gt;1901/11/22
	 *  AA&gt;&gt;093/11/22
	 *  &gt;&gt;java.lang.Exception:  ApDateTime.java changeYYYYtoYYY() 转出的民国年为负值,欲转的资料=1834/11/22 
	 * </pre>
	 */
	static public String changeYYYYtoYY(String strDate, int startPos, int endPos)
			throws Exception {

		String str1 = strDate.substring(0, startPos);
		String str2 = strDate.substring(startPos, endPos);
		String str3 = strDate.substring(endPos, strDate.length());

		int yy = new Integer(str2).intValue() - 1911;
		if (yy < 0)
			throw new Exception(
					" ApDateTime.java changeYYYYtoYYY() 转出的民国年为负值,欲转的资料="
							+ strDate);

		String str = (yy < 99) ? new String("0") + new Integer(yy).toString()
				: new Integer(yy).toString();

		return (str1 + str + str3);
	}

	/**
	 * 将民国年(２码或３码)转换成公元年４码<br>
	 * strDate之民国年可为负号,表示民国以前<br>
	 * 请参略 公元年转换成民国年之 method changeYYtoYYYY()之说明<br>
	 */
	static public String changeYYtoYYYY(String strDate, int startPos, int endPos)
			throws Exception {

		String str1 = strDate.substring(0, startPos);
		String str2 = strDate.substring(startPos, endPos);
		String str3 = strDate.substring(endPos, strDate.length());

		int yy = new Integer(str2).intValue() + 1911;

		return (str1 + new Integer(yy).toString() + str3);
	}

	/**
	 * 请参略 公元年转换成民国年之 method changeYYtoYYYY()之说明<br>
	 * 所不同的只有传入的 strDate 及回传值址为 ArrayList()<br>
	 */
	static public ArrayList<String> changeYYYYtoYY(ArrayList<String> strDate,
			int startPos, int endPos) throws Exception {

		ArrayList<String> aList = new ArrayList<String>();
		for (int i = 0, nnn = strDate.size(); i < nnn; i++) {
			String str = (String) strDate.get(i);
			aList.add(changeYYYYtoYY(str, startPos, endPos));
		}
		return (aList);
	}

	/**
	 * 请参略 公元年转换成民国年之 method changeYYtoYYYY()之说明<br>
	 * 所不同的只有传入的 strDate 及回传值址为 ArrayList()<br>
	 */
	static public ArrayList<String> changeYYtoYYYY(ArrayList<String> strDate,
			int startPos, int endPos) throws Exception {

		ArrayList<String> aList = new ArrayList<String>();
		for (int i = 0, nnn = strDate.size(); i < nnn; i++) {
			String str = (String) strDate.get(i);
			aList.add(changeYYtoYYYY(str, startPos, endPos));
		}
		return (aList);
	}

	/**
	 * 将数字的民国年如 0901205 转成 "九十" , "十二" , "五"
	 * 
	 * @param strDate
	 *            民国年格式如 0901205
	 * @return ArrayList
	 * @exception java.lang.Exception
	 * @since 只可处理 0800101 ~ 0990101 之间的转换<br>
	 *        回传的数据放入 ArrayList 分别 a<br>
	 *        Vector.get(0) ..... 为 "年"<br>
	 *        Vector.get(1) ..... 为 "月"<br>
	 *        Vector.get(2) ..... 为 "日"<br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(ApDateTime.getChineseYMD(&quot;0930823&quot;).toString());
	 * 	 System.out.println(ApDateTime.getChineseYMD(&quot;0991231&quot;).toString());
	 * 	 System.out.println(ApDateTime.getChineseYMD(&quot;0400112&quot;).toString());
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;&gt;&gt;&gt;&quot;+e);            
	 * 	 }
	 *  输出结果:
	 *  [九十三, 八, 二十三]
	 *  [九十九, 十二, 三十一]
	 *  [yy, 一, 十二]
	 * </pre>
	 */
	static public ArrayList<String> getChineseYMD(String strDate)
			throws Exception {

		String yyStr[] = new String[] { "yy", "八十一", "八十二", "八十三", "八十四",
				"八十五", "八十六", "八十七", "八十八", "八十九", "九十", "九十一", "九十二", "九十三",
				"九十四", "九十五", "九十六", "九十七", "九十八", "九十九", "一百" };
		String mmStr[] = new String[] { "mm", "一", "二", "三", "四", "五", "六",
				"七", "八", "九", "十", "十一", "十二" };
		String ddStr[] = new String[] { "dd", "一", "二", "三", "四", "五", "六",
				"七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七",
				"十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六",
				"二十七", "二十八", "二十九", "三十", "三十一", "三十二" };

		int yy = new Integer(strDate.substring(0, 3)).intValue();
		int mm = new Integer(strDate.substring(3, 5)).intValue();
		int dd = new Integer(strDate.substring(5, 7)).intValue();

		yy = (81 <= yy && yy <= 99) ? yy - 80 : 0;
		mm = (1 <= mm && mm <= 12) ? mm : 0;
		dd = (1 <= dd && dd <= 31) ? dd : 0;

		ArrayList<String> aList = new ArrayList<String>();
		aList.add(yyStr[yy]);
		aList.add(mmStr[mm]);
		aList.add(ddStr[dd]);

		return (aList);
	}

	/**
	 * 组成HTML的Select..Option所要的时间数据格式回传回去
	 * 
	 * @param HHMM
	 *            "HH"or"hh" 取得"时"的资料,"MM"or"mm"取得"分"的资料
	 * @param Item
	 *            select欲停留的位置
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since 回传的资料有三组 ArrayList 分别 add 至 Vector <br>
	 *        Vector.get(0) ..... 为 HTML 之 select....option 的 value 值 <br>
	 *        Vector.get(1) ..... 为 HTML 之 select...option 的 data值亦显示于画面的数据 <br>
	 *        Vector.get(2) ..... 为 HTML 之 select...option 欲显示第几行的值 <br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(&quot;时=&quot;+ApDateTime.putTimeSelectOption(&quot;HH&quot;, &quot;1&quot;).toString());
	 * 	 System.out.println(&quot;分=&quot;+ApDateTime.putTimeSelectOption(&quot;mm&quot;, &quot;4&quot;).toString());
	 * 	 System.out.println(&quot;分=&quot;+ApDateTime.putTimeSelectOption(&quot;mm&quot;, &quot;aa&quot;).toString());
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;&gt;&gt;&gt;&quot;+e);            
	 * 	 }
	 *  输出结果:
	 *  时=[[0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23], 2]
	 *  分=[[0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60], [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59], 5]
	 *  &gt;&gt;&gt;java.lang.NumberFormatException: For input string: &quot;aa&quot;
	 * </pre>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public Vector<String> putTimeSelectOption(String HHMM, String Item)
			throws Exception {

		ArrayList<String> aListValue = new ArrayList<String>();
		ArrayList<String> aListData = new ArrayList<String>();
		int ptr = 1;
		int count = 1;

		if (HHMM.equals("hh") || HHMM.equals("HH"))
			count = 24;
		if (HHMM.equals("mm") || HHMM.equals("MM"))
			count = 60;

		for (int i = 0; i < count; i++) {
			aListValue.add(new Integer(i).toString());
			aListData.add(new Integer(i).toString());
			if (Item != null && new Integer(Item).intValue() == i)
				ptr = i;
		}

		Vector vv = new Vector();
		vv.add(aListValue);
		vv.add(aListData);
		vv.add(new Integer(ptr).toString());

		return (vv);
	}

	/**
	 * 组成HTML的Select..Option所要的日期格式数据格式回传回去
	 * 
	 * @param Format
	 *            "BYyyyyMMdd"->为依"天"计, "BYyyyyMM"->为依"月"计, "BYyyyy"->为依"年"计
	 * @param startDate
	 *            开始日期(格式为yyyyMMDD)
	 * @param dataLayout
	 *            欲显示的日期格式
	 * @param Item
	 *            select欲停留的位置
	 * @param Size
	 *            欲显示的日期格式之Select...Optin 的下拉资料数
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since 回传的资料有三组 ArrayList 分别 add 至 Vector <br>
	 *        Vector.get(0) ..... 为 HTML 之 select....option 的 value 值 <br>
	 *        Vector.get(1) ..... 为 HTML 之 select...option 的 data值亦显示于画面的数据 <br>
	 *        Vector.get(2) ..... 为 HTML 之 select...option 欲显示第几行的值 <br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println( &quot;11 &gt;&gt;&quot; + com.grandsys.commons.ApDateTime.putDateSelectOption(&quot;BYyyyyMMdd&quot;, &quot;20030504&quot;, &quot;yyyy-MM-dd&quot;, &quot;&quot;, 2) );
	 * 	 System.out.println( &quot;22 &gt;&gt;&quot; + com.grandsys.commons.ApDateTime.putDateSelectOption(&quot;BYyyyyMM&quot;, &quot;20031004&quot;, &quot;yyyy-MM&quot;, &quot;200312&quot;, -3) );
	 * 	 System.out.println( &quot;33 &gt;&gt;&quot; + com.grandsys.commons.ApDateTime.putDateSelectOption(&quot;BYyyyyMM&quot;, &quot;20040304&quot;, &quot;yyyy-MM&quot;, &quot;200404&quot;, 3) );
	 * 	
	 * 	 Vector vv = com.grandsys.commons.ApDateTime.putDateSelectOption(&quot;BYyyyyMMdd&quot;, &quot;20030504&quot;, &quot;yyyy-MM-dd&quot;, &quot;&quot;, 2);
	 * 	 System.out.println(&quot;AA &gt;&gt;&quot; + com.grandsys.commons.ApDateTime.changeYYYYtoYY((ArrayList)vv.get(0), 0, 4));
	 * 	
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;&gt;&gt;&gt;&quot;+e);            
	 * 	 }    
	 *  输出结果:
	 *  11 &gt;&gt;[[20030504, 20030505], [2003-05-04, 2003-05-05], 1]
	 *  22 &gt;&gt;[[200310, 200309, 200308], [2003-10, 2003-09, 2003-08], 1]
	 *  33 &gt;&gt;[[200403, 200404, 200405], [2004-03, 2004-04, 2004-05], 2]
	 *  AA &gt;&gt;[0920504, 0920505]
	 *  &gt;&gt;&gt;java.lang.Exception:  ApDateTime.java putDateSelectOption() Format=
	 * </pre>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public Vector putDateSelectOption(String Format, String startDate,
			String dataLayout, String Item, int Size) throws Exception {

		ArrayList aListValue = new ArrayList();
		ArrayList aListData = new ArrayList();
		int ptr = 1;

		String value = new String();
		String data = new String();
		for (int i = 0, nnn = Math.abs(Size); i < nnn; i++) {
			int diff = (Size < 0) ? i * (-1) : i;
			if (Format.equals("BYyyyyMMdd")) {
				value = getDateDiff_ForDay("yyyyMMdd", startDate, diff);
				data = getDateDiff_ForDay(dataLayout, startDate, diff);
			} else if (Format.equals("BYyyyyMM")) {
				value = getDateDiff_ForMonth("yyyyMM", startDate, diff).get(0)
						.toString();
				data = getDateDiff_ForMonth(dataLayout, startDate, diff).get(0)
						.toString();

				value = value.substring(0, value.length() - 2);
				data = data.substring(0, data.length() - 2);
			} else {
				throw new Exception(
						" ApDateTime.java putDateSelectOption() Format="
								+ Format + " Error!!");
			}
			aListValue.add(value);
			aListData.add(data);
			if (Item != null && Item.equals(value))
				ptr = i + 1;
		}
		Vector vv = new Vector();
		vv.add(aListValue);
		vv.add(aListData);
		vv.add(new Integer(ptr).toString());

		return (vv);
	}

	/**
	 * 取得起迄日期的 年月 的日期
	 * 
	 * @param startDate
	 *            开始日期(民国年)
	 * @param endDate
	 *            结束日期(民国年)
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since 回传的 Vector 两两成一组分别为,本月的起日,本月的迄日 <br>
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(&quot;1-&gt;&quot; + ApDateTime.getStartEndDateYY(&quot;0920105&quot;, &quot;0920120&quot;) );
	 * 	 System.out.println(&quot;2-&gt;&quot; + ApDateTime.getStartEndDateYY(&quot;0930305&quot;, &quot;0931120&quot;) );
	 * 	 System.out.println(&quot;3-&gt;&quot; + ApDateTime.getStartEndDateYY(&quot;0920105&quot;, &quot;0930120&quot;) );
	 * 	 }
	 * 	 catch(Exception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 }
	 *  输出结果:
	 *  1-&gt;[0920105, 0920120]
	 *  2-&gt;[0930305, 0930331, 0930401, 0930430, 0930501, 0930531, 0930601, 0930630, 0930701, 0930731, 0930801, 0930831, 0930901, 0930930, 0931001, 0931031, 0931101, 0931120]
	 *  3-&gt;[0920105, 0920131, 0920201, 0920228, 0920301, 0920331, 0920401, 0920430, 0920501, 0920531, 0920601, 0920630, 0920701, 0920731, 0920801, 0920831, 0920901, 0920930, 0921001, 0921031, 0921101, 0921130, 0921201, 0921231, 0930101, 0930120]
	 * </pre>
	 */
	static public Vector<String> getStartEndDateYY(String startDate,
			String endDate) throws Exception {

		String ss = changeYYtoYYYY(startDate,
				0, 3);
		String ee = changeYYtoYYYY(endDate, 0,
				3);

		Vector<String> vv = getStartEndDateYYYY(ss, ee);
		Vector<String> vvData = new Vector<String>();
		for (int i = 0, nnn = vv.size(); i < nnn; i += 2) {
			vvData.add(changeYYYYtoYY(
					(String) vv.get(i), 0, 4));
			vvData.add(changeYYYYtoYY(
					(String) vv.get(i + 1), 0, 4));
		}
		return (vvData);
	}

	/**
	 * 取得起迄日期的 年月 的日期
	 * 
	 * @param startDate
	 *            开始日期(公元年)
	 * @param endDate
	 *            结束日期(公元年)
	 * @return Vector
	 * @exception java.lang.Exception
	 * @since
	 * 
	 * <pre>
	 *  范例说明:
	 *  请参考 getStartEndDateYY() 范例说明
	 * </pre>
	 */
	static public Vector<String> getStartEndDateYYYY(String startDate,
			String endDate) throws Exception {

		Vector<String> vvData = new Vector<String>();
		for (int i = 0; i < 1200; i++) {
			Vector<String> vv = getDateDiff_ForMonth("yyyyMM", startDate, i);

			String ss = (i == 0) ? startDate : (String) vv.get(0);
			String ee = (endDate.compareTo((String) vv.get(1)) <= 0) ? endDate
					: (String) vv.get(1);

			vvData.add(ss);
			vvData.add(ee);

			if (endDate.compareTo((String) vv.get(1)) <= 0)
				break;
		}
		return (vvData);
	}

	/**
	 * 依传入的 format 取得起迄日期中的日期
	 * 
	 * @param dateS
	 *            开始日期(8位公元年,格式为 yyyyMMdd)
	 * @param dateE
	 *            结束日期(8位公元年,格式为 yyyyMMdd)
	 * @param format
	 *            请参考 java.text.SimpleDateFormat() 格式说明
	 * @return ArrayList
	 * @exception java.lang.Exception
	 * @since
	 * 
	 * <pre>
	 *  范例说明:
	 * 	 try{
	 * 	 System.out.println(&quot;A&gt;&gt;&quot;+ApDateTime.getDays(&quot;20040220&quot;, &quot;20040302&quot;, &quot;yyyy-MM-dd&quot;) );
	 * 	 System.out.println(&quot;B&gt;&gt;&quot;+ApDateTime.getDays(&quot;20040220&quot;, &quot;20040302&quot;, &quot;yy-MM-dd&quot;) );
	 * 	 System.out.println(&quot;C&gt;&gt;&quot;+ApDateTime.getDays(&quot;20040220&quot;, &quot;20040302&quot;, &quot;D&quot;) );
	 * 	 System.out.println(&quot;D&gt;&gt;&quot;+ApDateTime.getDays(&quot;20040220&quot;, &quot;20040302&quot;, &quot;YYYY-MM-dd&quot;) );
	 * 	 }
	 * 	 catch(E xception e){
	 * 	 System.out.println(&quot;e:::&quot;+e);            
	 * 	 } 
	 *  输出结果:
	 * 	 A&gt;&gt;[2004-02-20, 2004-02-21, 2004-02-22, 2004-02-23, 2004-02-24, 2004-02-25, 2004-02-26, 2004-02-27, 2004-02-28, 2004-02-29, 2004-03-01]
	 * 	 B&gt;&gt;[04-02-20, 04-02-21, 04-02-22, 04-02-23, 04-02-24, 04-02-25, 04-02-26, 04-02-27, 04-02-28, 04-02-29, 04-03-01]
	 * 	 C&gt;&gt;[51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61]
	 * 	 e:::java.lang.IllegalArgumentException: Illegal pattern character 'Y'
	 * </pre>
	 */
	static public ArrayList<String> getDays(String dateS, String dateE,
			String format) {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);

		java.util.Calendar c1 = java.util.Calendar.getInstance();
		c1.set(java.lang.Integer.parseInt(dateE.substring(0, 4)),
				java.lang.Integer.parseInt(dateE.substring(4, 6)) - 1,
				java.lang.Integer.parseInt(dateE.substring(6, 8)) - 1);
		String ss = sdf.format(c1.getTime().clone());

		ArrayList<String> al = new ArrayList<String>();
		if (dateS.equals("") || dateE.equals(""))
			return al;
		c1 = java.util.Calendar.getInstance();
		c1.set(java.lang.Integer.parseInt(dateS.substring(0, 4)),
				java.lang.Integer.parseInt(dateS.substring(4, 6)) - 1,
				java.lang.Integer.parseInt(dateS.substring(6, 8)) - 1);

		for (; !sdf.format(c1.getTime()).equals(ss);) {
			c1.add(java.util.Calendar.DAY_OF_YEAR, 1);
			// --pgmodeSystem.out.println(""+sdf.format(c1.getTime()));
			al.add(sdf.format(c1.getTime()));
		}
		return al;
	}

	// /**
	// * @param args the command line arguments
	// */
	// public static void main(String[] args) {
	// try{
	// // System.out.println("A>>"+ApDateTime.getDays("20040220", "20040302",
	// "yyyy-MM-dd") );
	// // System.out.println("B>>"+ApDateTime.getDays("20040220", "20040302",
	// "yy-MM-dd") );
	// // System.out.println("B>>"+ApDateTime.getDays("20040220", "20040302",
	// "D") );
	// // System.out.println("B>>"+ApDateTime.getDays("20040220", "20040302",
	// "YYYY-MM-dd") );
	// System.out.println("C.>>"+ApDateTime.getNowDateTime(DATE_TIME_SSS));
	// }
	// catch(Exception e){
	// e.printStackTrace();
	// System.out.println("e:::"+e);
	// }
	// }
	/**
	 * 返回某个日期的年,月,日,日,分,秒中的任一个 范例说明: 输入:getDateYMDHMS("2008-11-11
	 * 10:22:23.222","year") 返回:2008
	 * 
	 * @param dateStr
	 *            日期字串,格式如:yyyy-MM-dd hh:mm:ss.SSS
	 * @param returnType
	 *            返回类型,主要有以下几种:year,month,day,hour,minute,second
	 * 
	 */
	public static int getDateYMDHMS(String dateStr, String returnType)
			throws Exception {
		Date d = new Date(getTime(dateStr));
		if (returnType.equals("year") || returnType.equals("month")
				|| returnType.equals("day") || returnType.equals("hour")
				|| returnType.equals("minute") || returnType.equals("second")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(Calendar.MILLISECOND, 0);

			// Calendar calendar = GregorianCalendar.getInstance();

			calendar.setTime(d);
			if (returnType.equals("year")) {
				return calendar.get(Calendar.YEAR);
			} else if (returnType.equals("month")) {
				return calendar.get(Calendar.MONTH) + 1;
			} else if (returnType.equals("day")) {
				return calendar.get(Calendar.DATE);
			} else if (returnType.equals("hour")) {
				return calendar.get(Calendar.HOUR_OF_DAY);
			} else if (returnType.equals("minute")) {
				return calendar.get(Calendar.MINUTE);
			} else
				return calendar.get(Calendar.SECOND);
		} else {
			throw new IllegalArgumentException(
					"返回类型必须为:year,month,day,hour,minute,second六种之一");
		}
	}

	/**
	 * 返回某个日期的年,月,日,日,分,秒中的任一个 范例说明: 输入:getDateYMDHMS(1228962143222,"year")
	 * 返回:2008
	 * 
	 * @param dateStr
	 *            日期字串,格式如:yyyy-MM-dd hh:mm:ss.SSS
	 * @param returnType
	 *            返回类型,主要有以下几种:year,month,day,hour,minute,second
	 * 
	 */
	public static int getDateYMDHMS(long dateTime, String returnType) {

		Date d = new Date(dateTime);
		if (returnType.equals("year") || returnType.equals("month")
				|| returnType.equals("day") || returnType.equals("hour")
				|| returnType.equals("minute") || returnType.equals("second")) {

			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(d);
			if (returnType.equals("year")) {
				return calendar.get(Calendar.YEAR);
			} else if (returnType.equals("month")) {
				return calendar.get(Calendar.MONTH) + 1;
			} else if (returnType.equals("day")) {
				return calendar.get(Calendar.DATE);
			} else if (returnType.equals("hour")) {
				return calendar.get(Calendar.HOUR_OF_DAY);
			} else if (returnType.equals("minute")) {
				return calendar.get(Calendar.MINUTE);
			} else
				return calendar.get(Calendar.SECOND);
		} else {
			throw new IllegalArgumentException(
					"返回类型必须为:year,month,day,hour,minute,second六种之一");
		}
	}
	
	/**
	 * 
	 * @param minDate
	 * @param p_end
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
			min.setTime(sdf.parse(minDate));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

	        max.setTime(sdf.parse(maxDate));
	        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        Calendar curr = min;
        while (curr.before(max)) {
         result.add(sdf.format(curr.getTime()));
         curr.add(Calendar.MONTH, 1);
        }
        if(result.isEmpty()){
        	result.add(maxDate);
        }
        return result;
    }
	
	/**
	 * 
	 * @param minDate
	 * @param p_end
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static List<String> getDayBetween(String minDate, String maxDate) {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
			min.setTime(sdf.parse(minDate));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), min.get(Calendar.DAY_OF_MONTH));

	        max.setTime(sdf.parse(maxDate));
	        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), max.get(Calendar.DAY_OF_MONTH));

		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar curr = min;
        while (curr.before(max)) {
         result.add(sdf.format(curr.getTime()));
         curr.add(Calendar.DAY_OF_MONTH, 1);
        }
        result.add(sdf.format(max.getTime()));
        return result;
    }

	/**
	 * 根据两个日期值计算之间的差距
	 * 
	 * @param startDate
	 *            开始日期 endDate 结束日期
	 * @param strForamt
	 *            D 表示返回的为天：时：分：秒 H 表示返回的为时：分：秒
	 * @return String格式的 格式为 时：分：秒
	 * @throws Exception
	 */
	public static String getValueBetweenSDateAndEDate(Date startDate,
			Date endDate, String strFormat) throws Exception {

		long tempTotalMills = Math.abs(endDate.getTime() - startDate.getTime()); // 获得毫秒数
		long totalSeconds = tempTotalMills / 1000; // 获得总共差距的秒数
		long seconds = totalSeconds % 60; // s 秒数
		long minutes = totalSeconds % 3600 / 60; // m 分钟数
		long hours = totalSeconds / 3600; // h 总小时数
		long days = 0; // 天数 // 月数 // 年数
		String returnStr = hours + ":" + minutes + ":" + seconds;
		if (strFormat == null || strFormat.length() <= 0) {
			return returnStr;
		} else if (strFormat.equals("H")) {
			return returnStr;
		} else if (strFormat.equals("D")) {
			hours = totalSeconds % (24 * 3600) / 3600;
			days = totalSeconds / (24 * 3600);
			returnStr = days + "天 " + hours + ":" + minutes + ":" + seconds;
			return returnStr;
		} else {
			return returnStr;
		}

	}

	/**
	 * 得到当前日期相差月份的最后1天日期
	 * 
	 * @param monthDiff
	 *            相差月份
	 * @author robert.huang
	 */
	static public String getDateDiff_ForMonth(int monthDiff) throws Exception {

		String nowDate = getNowDateTime("yyyyMMdd");
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar = ApDateTime.getDateCalendar(calendar, nowDate);
		calendar.add(java.util.Calendar.MONTH, monthDiff);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM");

		int maxDay = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

		String date = sdf.format(calendar.getTime()) + "-"
				+ new Integer(maxDay);

		return date;
	}

	/**
	 * 根据类型单位 判断两个日期之间的差距是否满足该单位，如果满足则返回true 否则返回false
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @param type
	 *            H\M\W\M\Y
	 * @return
	 */
	public static boolean judgeDiffDate(String firstDate, String secondDate,
			String type) {
		boolean result = false;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date first_Date = sdf.parse(firstDate);
			Date second_Daate = sdf.parse(secondDate);
			java.util.Calendar first_calendar = java.util.Calendar
					.getInstance();
			java.util.Calendar second_calendar = java.util.Calendar
					.getInstance();
			first_calendar.setTime(first_Date);
			second_calendar.setTime(second_Daate);
			java.util.Calendar minCalendar = null;
			java.util.Calendar maxCalendar = null;
			// 取两个日期的教小值
			if (first_calendar.getTimeInMillis() > second_calendar
					.getTimeInMillis()) {
				minCalendar = second_calendar;
				maxCalendar = first_calendar;
			} else {
				minCalendar = first_calendar;
				maxCalendar = second_calendar;
			}

			// 小时为单位
			if ("H".equals(type)) {

				minCalendar.add(java.util.Calendar.HOUR_OF_DAY, 1);

			} else if ("D".equals(type)) {// 天为单位
				minCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

			} else if ("W".equals(type)) { // 周为单位
				minCalendar.add(java.util.Calendar.WEEK_OF_MONTH, 1);

			} else if ("M".equals(type)) { // 月为单位
				minCalendar.add(java.util.Calendar.MONTH, 1);

			} else if ("Y".equals(type)) {// 年为单位
				minCalendar.add(java.util.Calendar.YEAR, 1);
			}
			// 如果
			if (minCalendar.getTimeInMillis() > maxCalendar.getTimeInMillis()) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 根据比较单位类型 判断两个日期是否在该单位的差距内，（即两个日期值的差值小于改单位值）如果满足则返回true 否则返回false
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @param type
	 *            H\M\W\M\Y
	 * @return
	 */
	public static boolean judgeDiffDate(long firstDateMills,
			long secondDateMills, String type) {
		boolean result = false;
		try {

			java.util.Calendar minCalendar = java.util.Calendar.getInstance();
			java.util.Calendar maxCalendar = java.util.Calendar.getInstance();
			// 取两个日期的教小值
			if (firstDateMills > secondDateMills) {
				minCalendar.setTimeInMillis(secondDateMills);
				maxCalendar.setTimeInMillis(firstDateMills);
			} else {
				minCalendar.setTimeInMillis(firstDateMills);
				maxCalendar.setTimeInMillis(secondDateMills);
			}
			// 拆解计算间隔 - type
			int amount = 1;// 时间比较差值单位
			String timeType = "D";// 默认时间间隔单位为天
			if (type != null && type.length() > 1) {
				try {
					String amountStr = type.substring(0, type.length() - 1);
					amount = Integer.parseInt(amountStr);
				} catch (RuntimeException e) {
					amount = 1;// 如果转换失败，则单位值默认为1
				}
				timeType = type.substring(type.length() - 1);// 取得计算的时间单位,MI/H/D/W/M/Y
			} else {
				timeType = type;
			}

			if ("m".equals(timeType)) {// 分钟为单位
				minCalendar.add(java.util.Calendar.MINUTE, amount);
			}
			// 小时为单位
			else if ("H".equals(timeType)) {
				minCalendar.add(java.util.Calendar.HOUR_OF_DAY, amount);

			} else if ("D".equals(timeType)) {// 天为单位
				minCalendar.add(java.util.Calendar.DAY_OF_MONTH, amount);

			} else if ("W".equals(timeType)) { // 周为单位
				minCalendar.add(java.util.Calendar.WEEK_OF_MONTH, amount);

			} else if ("M".equals(timeType)) { // 月为单位
				minCalendar.add(java.util.Calendar.MONTH, amount);

			} else if ("Y".equals(timeType)) {// 年为单位
				minCalendar.add(java.util.Calendar.YEAR, amount);
			}
			// 如果较小值加上了比较的单位的毫秒数 大于较大值 则证明两个数值之间的差距不足一单位值 返回true
			if (minCalendar.getTimeInMillis() > maxCalendar.getTimeInMillis()) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 根据传入日期，取得其当周第一天，最后一天
	 * 
	 * @param day
	 *            格式yyyy-MM-dd 长度小于10或者空的，默认为当天
	 * @return MAP KEY：woy 所在日期年的第几周，first 所在周的第一天，end 所在周的最后一天
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public static Map getWeekOfYearByDay(String day) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(c.MONDAY);
		if (day == null || day.trim().length() < 10)
			day = ApDateTime.getNowDateTime("yyyy-MM-dd");

		if (day.length() > 0) {
			day = day.substring(0, 10);
		}
		String[] arr = day.split("-");
		int y = Integer.parseInt(arr[0]);
		int m = Integer.parseInt(arr[1]);
		int d = Integer.parseInt(arr[2]);
		c.set(y, m - 1, d);
		Map rm = new HashMap();
		int woy = c.get(Calendar.WEEK_OF_YEAR);// 所在日期年的第几周
		String mon = ""; // 所在周的周一
		String sun = "";// 所在周的周日
		String nextMon = "";// 所在周的下周一
		String nextSun = "";// 所在周的下周日
		String preSun = "";// 上周日
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		try {
			mon = ApDateTime.getDateTime("yyyy-MM-dd", c.getTimeInMillis());
		} catch (Exception e) {
		}
		c.set(c.DAY_OF_WEEK, c.SUNDAY);
		try {
			sun = ApDateTime.getDateTime("yyyy-MM-dd", c.getTimeInMillis());
		} catch (Exception e) {
		}
		c.set(c.WEEK_OF_YEAR, woy + 1);
		try {
			nextSun = ApDateTime.getDateTime("yyyy-MM-dd", c.getTimeInMillis());
		} catch (Exception e) {
		}
		c.set(c.DAY_OF_WEEK, c.MONDAY);
		try {
			nextMon = ApDateTime.getDateTime("yyyy-MM-dd", c.getTimeInMillis());
		} catch (Exception e) {
		}
		c.set(c.DAY_OF_WEEK, c.SUNDAY);
		c.set(c.WEEK_OF_YEAR, woy - 1);
		try {
			preSun = ApDateTime.getDateTime("yyyy-MM-dd", c.getTimeInMillis());
		} catch (Exception e) {
		}
		rm.put("woy", woy);
		rm.put("first", mon);
		rm.put("end", sun);
		rm.put("mon", mon);
		rm.put("sun", sun);
		rm.put("preSun", preSun);
		rm.put("nextMon", nextMon);
		rm.put("nextSun", nextSun);
		rm.put("nextEnd", nextSun);
		rm.put("preEnd", preSun);
		return rm;
	}

	/**
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param weekOfMonth
	 *            这个月的第几周
	 * @param dayOfWeek
	 *            星期几
	 * @return
	 */
	public static String weekdatetodata(int year, int month, int weekOfMonth,
			int dayOfWeek) {
		Calendar c = Calendar.getInstance();
		// 计算出 x年 y月 1号 是星期几
		c.set(year, month - 1, 1);
		// 如果i_week_day =1 的话 实际上是周日
		int i_week_day = c.get(Calendar.DAY_OF_WEEK);
		int sumDay = 0;
		// dayOfWeek+1 就是星期几（星期日 为 1）
		if (i_week_day == 1) {
			sumDay = (weekOfMonth - 1) * 7 + dayOfWeek + 1;
		} else {
			sumDay = 7 - i_week_day + 1 + (weekOfMonth - 1) * 7 + dayOfWeek + 1;
		}
		// 在1号的基础上加上相应的天数
		c.set(Calendar.DATE, sumDay);
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		return sf2.format(c.getTime());
	}

	public static void main(String[] args) {
		System.out.println(ApDateTime.getWeekOfYearByDay(""));
	}

}