package com.org.mokey.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @function ������صĹ���
 * @author <a href="mailto:zylgang@sina.com">liugang</a>
 * @create 2008-6-5
 * @version 1.0
 * @logs 2008-6-5����::
 */
public class DateUtils {
    /**
     * ��һ���ƶ���ʼ���ڵ���䰴���·ݻ��ֳ�һ����ʼʱ��,�������
     * @create 2007-11-7
     * @author
     * @param from
     * @param to
     * @return Like {{froms},{ends}}
     * from:2008-05-04 to:2008-07-09
     * {{2008-05-04,2008-06-01,2008-07-01},{2008-05-31,2008-06-30,2008-07-09}}
     * @version 1.0
     * @logs 2007-11-7����::
     */
    public static Date[][] getFromEndDate(Date from, Date to) {

        Calendar fCal = Calendar.getInstance();
        fCal.setTime(from);
        Calendar tCal = Calendar.getInstance();
        tCal.setTime(to);
        if(!fCal.before(tCal)) {
            return null;
        }
        int index = 0;
        while(fCal.before(tCal)) {
            index++;
            fCal.add(Calendar.MONTH, 1);
        }
        fCal.setTime(from);
        Date[][] date = new Date[2][];
        Date[] fDate = new Date[index];
        Date[] tDate = new Date[index];
        int count = 0;
        while(count < index) {
            //��һ��ѭ��,������ʼ�·ݲ�һ��
            if(count == 0 && index != 1) {
                fDate[count] = fCal.getTime();
                int days = getDaysOfMonth(fCal.get(Calendar.YEAR), fCal.get(Calendar.MONTH) + 1);
                fCal.set(Calendar.DAY_OF_MONTH, days);
                tDate[count] = fCal.getTime();
                //��ʼ�·ݲ�֮һ��ûѭ���������
            } else if(count != 0 && count < index -1){
                fCal.add(Calendar.DAY_OF_MONTH, 1);
                fDate[count] = fCal.getTime();
                int days = getDaysOfMonth(fCal.get(Calendar.YEAR), fCal.get(Calendar.MONTH) + 1);
                fCal.set(Calendar.DAY_OF_MONTH, days);
                tDate[count] = fCal.getTime();
                //��ʼ�·ݲ�һ�µ���ѭ������ͬ�·�ʱ
            } else if(count != 0 && count == index -1){
                fCal.add(Calendar.DAY_OF_MONTH, 1);
                fDate[count] = fCal.getTime();
                tDate[count] = tCal.getTime();
                //��ʼ�·�һ��
            } else {
                fDate[count] = fCal.getTime();
                tDate[count] = tCal.getTime();
            }
            count++;
        }
        date[0] = fDate;
        date[1] = tDate;
        return date;
    }

    /**
     * ��ȡĳ�°������
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param year
     * @param month
     * @return �����µ�����
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public static int getDaysOfMonth(int year, int month) {
        if(month == 2) {
            if((year % 4 ==0 && year % 100 == 0) || (year % 4 ==0 || year % 100 == 0)) {
                return 29;
            } else {
                return 28;
            }
        }
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if(month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        return 30;
    }

    /**
     * ��ȡĳ�°������
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param year
     * @param month
     * @return �����µ�����
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public static int getDaysOfMonth(String syear, String smonth) {
        if(syear == null || smonth == null) {
            return 0;
        }
        int year = Integer.parseInt(syear);
        int month = Integer.parseInt(smonth);
        return getDaysOfMonth(year, month);
    }

    /**
     * ��һ���ַ� ��һ�ָ�ʽת������һ�ָ�ʽ������
     * @create 2008-6-5
     * @author
     * @param dateStr Ҫת���������ַ�
     * @param fromFormat ��:yyyyMM,yyyyMMdd,yyyy-MM-dd
     * @param toFormat ��ϵ fromFormat
     * @return ��toFormatһ�µ�����
     * @version 1.0
     * @logs 2008-6-5����::
     */
    public static String changeDateFormat(String dateStr, String fromFormat, String toFormat) {
        try {
            return new SimpleDateFormat(toFormat).format(new SimpleDateFormat(fromFormat).parse(dateStr));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
    /**
     * ���һ�������
     * @create 2007-11-10
     * @author
     * @param year default 1000 - 5000
     * @return 
     * @version 1.0
     * @logs 2007-11-10����::
     */
    public static int getDaysOfYear(int year) {
        if(year < 1000 || year > 5000) {
            return 365;
        }
        if((year % 4 ==0 && year % 100 == 0) || (year % 4 ==0 || year % 100 == 0)) {
            return 366;
        } else {
            return 365;
        }
    }

    /**
     * ���һ�������
     * @create 2007-11-10
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2007-11-10����::
     */
    public static int getDaysOfYear(String syear) {
        if(syear == null || syear.equals("")) {
            return 365;
        }
        int year = 1980;
        try {
            year = Integer.parseInt(syear);
        } catch(Exception e) {
            return 365;
        }
        if((year % 4 ==0 && year % 100 == 0) || (year % 4 ==0 || year % 100 == 0)) {
            return 366;
        } else {
            return 365;
        }
    }

    /**
     * ���һ��ʣ������
     * @create 2007-11-10
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2007-11-10����::
     */
    public static int getRestDaysOfYear(int year, int month, int day) {
        int days = 0;
        for(int i = month; i <= 12; i++) {
            days += getDaysOfMonth(year, i);
        }
        return days - day + 1;
    }
    /**
     * ���һ��ʣ������
     * @create 2007-11-10
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2007-11-10����::
     */
    public static int getRestDaysOfYear(String syear, String smonth, String sday) {
        if(syear == null || smonth == null || sday == null) {
            return 0;
        }
        int year = Integer.parseInt(syear);
        int month = Integer.parseInt(smonth);
        int day = Integer.parseInt(sday);
        return getRestDaysOfYear(year, month,day);
    }
    /**
     * ȡ��һ��ʣ������
     * @create 2008-6-5
     * @author
     * @param ymdStr
     * @param format yyyyMMdd  yyyy-MM-dd
     * @return
     * @version 1.0
     * @logs 2008-6-5����::
     */
    public static int getRestDaysOfYear(String ymdStr, String format) {
        if(ymdStr == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(format).parse(ymdStr));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return getRestDaysOfYear(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }
    /**
     * ȡ��һ��ʣ������
     * @create 2008-6-5
     * @author
     * @param date
     * @return
     * @version 1.0
     * @logs 2008-6-5����::
     */
    public static int getRestDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getRestDaysOfYear(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * ����ָ����ݸ��µ�����
     * @create Feb 23, 2009
     * @author bluton
     * @param year
     * @return
     * @version 1.0
     * @logs Feb 23, 2009
     */
    public static int[] getEveryDaysOfYear(int year) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int[] daysOfYear = new int[12];
        cal.set(java.util.Calendar.YEAR, year);       
        for(int i = 0; i < 12; i++) {
            cal.set(java.util.Calendar.MONTH, i);
            int n = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);   
            daysOfYear[i] = n;
        }
        return daysOfYear;
    }

    /**
     * ��ȡָ������и��յ�����
     * @create Apr 28, 2009
     * @author bluton
     * @param year
     * @return
     * @version 1.0
     * @logs Apr 28, 2009
     */
    public static int[] getWeekOfYear(int year) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, year); 
        cal2.set(java.util.Calendar.YEAR, year);
        int[] daysOfYear = new int[cal.getActualMaximum(Calendar.DAY_OF_YEAR)];
        int[] days = new int[12];
        for(int i = 0; i < 12; i++) {
            cal.set(java.util.Calendar.MONTH, i);
            int n = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);   
            days[i] = n;
        }
        int k = 0;
        for(int i = 0; i < days.length; i++) {
            cal2.set(java.util.Calendar.MONTH, i);
            for(int j = 0; j < days[i]; j ++) {
                cal2.set(Calendar.DATE, (j + 1));
                int dayOfWeek = cal2.get(Calendar.DAY_OF_WEEK);  
                daysOfYear[k] = dayOfWeek;
                if(k >= daysOfYear.length) {
                    break;
                }
                k++;
            }
        }
        return daysOfYear;
    }

    /**
     * ��ȡָ������и��յ�����
     * @create Apr 28, 2009
     * @author bluton
     * @param year
     * @return
     * @version 1.0
     * @logs Apr 28, 2009
     */
    public static int[][] getWeekOfEveryMonth(int year) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, year); 
        cal2.set(java.util.Calendar.YEAR, year);
        int[][] daysOfYear = new int[12][31];
        int[] days = new int[12];
        for(int i = 0; i < 12; i++) {
            cal.set(java.util.Calendar.MONTH, i);
            int n = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);   
            days[i] = n;
        }
        for(int i = 0; i < days.length; i++) {
            cal2.set(java.util.Calendar.MONTH, i);
            for(int j = 0; j < days[i]; j ++) {
                cal2.set(Calendar.DATE, (j + 1));
                daysOfYear[i][j] = cal2.get(Calendar.DAY_OF_WEEK);
            }
        }
        return daysOfYear;
    }


    /**
     * ��ȡ�ܵĿ�ʼ���������
     * @create May 4, 2009
     * @author bluton
     * @param year
     * @param week
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static String[] getFirstAndLastDayOfWeek(int year, int week) {
        String[] dayOfWeek = new String[2];
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        String firstDay = formate.format(cal.getTime());
        if(cal.get(Calendar.YEAR) < year) {
            dayOfWeek[0] = year + "-01-01"; 
        } else {
            dayOfWeek[0] = firstDay;
        }
        cal.roll(Calendar.DAY_OF_WEEK, 6);
        String lastDay = formate.format(cal.getTime());
        if(cal.get(Calendar.YEAR) > year) {
            dayOfWeek[1] = year + "-12-" + getDaysOfMonth(year, 12);
        } else {
            dayOfWeek[1] = lastDay;
        }
        return dayOfWeek;
    }

    /**
     * ��ȡ�ܵĿ�ʼ���������
     * @create May 4, 2009
     * @author bluton
     * @param year
     * @param week
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static String[] getFirstAndLastDayOfWeek(int year, int week, int type) {
        String[] dayOfWeek = new String[2];
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(type);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        String firstDay = formate.format(cal.getTime());
        if(cal.get(Calendar.YEAR) < year) {
            dayOfWeek[0] = year + "-01-01"; 
        } else {
            dayOfWeek[0] = firstDay;
        }
        cal.roll(Calendar.DAY_OF_WEEK, 6);
        String lastDay = formate.format(cal.getTime());
        if(cal.get(Calendar.YEAR) > year) {
            dayOfWeek[1] = year + "-12-" + getDaysOfMonth(year, 12);
        } else {
            dayOfWeek[1] = lastDay;
        }
        return dayOfWeek;
    }

    /**
     * ��ȡָ����ʼ��������ڵļ��������
     * @create May 4, 2009
     * @author bluton
     * @param firstDay
     * @param lastDay
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static List<String> getDistancDayOfWeek(String firstDay, String lastDay) {
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
        try {
            cal.setTime(formate.parse(firstDay));
            calE.setTime(formate.parse(lastDay));
            while(!cal.equals(calE)) {
                list.add(formate.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            list.add(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * ��ȡָ����ʼ��������ڵļ��������
     * @create May 4, 2009
     * @author bluton
     * @param firstDay
     * @param lastDay
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static List<String> getDistancDayOfWeek(String firstDay, String lastDay, String type) {
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateResult = new SimpleDateFormat(type);
        List<String> list = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
        try {
            cal.setTime(formate.parse(firstDay));
            calE.setTime(formate.parse(lastDay));
            while(!cal.equals(calE)) {
                list.add(formateResult.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            list.add(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * ��ȡһ��������Ӧ������
     * @create May 4, 2009
     * @author bluton
     * @param firstDay
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static List<String> getWeekOfDayArray(String firstDay, String lastDay) {
        //��ȡ���ڼ��
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
        List<String> list = new ArrayList<String>();
        int week = 0;
        int index = 0;
        try {
            cal.setTime(formate.parse(firstDay));
            calE.setTime(formate.parse(lastDay));
            while(!cal.equals(calE)) {
                week = cal.get(Calendar.DAY_OF_WEEK);
                list.add(weeks[week - 1]);
                cal.add(Calendar.DAY_OF_YEAR, 1);
                index ++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * ��ȡָ�����ڶ�Ӧ����
     * @create May 4, 2009
     * @author bluton
     * @param firstDay
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static String getWeekOfDays(String day) {
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int week = 0;
        try {
            cal.setTime(formate.parse(day));
            week = cal.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weeks[week - 1];
    }
    /**
     * ��ȡָ�����ڶ�Ӧ����
     * @create May 4, 2009
     * @author bluton
     * @param firstDay
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static String getWeekOfDays(String day, String type) {
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int week = 0;
        try {
            cal.setTime(formate.parse(day));
            week = cal.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(type != null && type.equals("number")) {
            return (week) + "";
        } else  if(type != null) {
            return weeks[week - 1];
        } 
        return weekSing[week - 1];
    }

    /**
     * ��ȡָ�����ڶ�Ӧ�����ڼ����
     * @create May 4, 2009
     * @author bluton
     * @param firstDay
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static int getDistancOfDate(String firstDay, String lastDay) {
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
        int i = 1;
        try {
            cal.setTime(formate.parse(firstDay));
            calE.setTime(formate.parse(lastDay));
            while(!cal.equals(calE)) {
                cal.add(Calendar.DAY_OF_YEAR, 1);
                i ++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /*public static DateEntity queryNowDate() {
        Calendar cal = Calendar.getInstance();
        String year = cal.get(Calendar.YEAR) + "";
        String month = (((cal.get(Calendar.MONTH) + 1) + 100) + "").substring(1, 3);
        String day = (((cal.get(Calendar.DATE) + 1) + 100) + "").substring(1, 3);
        String week = cal.get(Calendar.WEEK_OF_YEAR) + "";
        DateEntity entity = new DateEntity();
        entity.setYear(year);
        entity.setMonth(month);
        entity.setDay(day);
        entity.setWeek_year(week);
        entity.setWeek_month(cal.get(Calendar.WEEK_OF_MONTH) + "");
        return entity;
    }*/

    /**
     * �ж��Ƿ��ѹ��ϱ�ʱ��
     * @param time
     * @return
     */
    public static boolean isCanreport(String time) {
        //-1Ϊ������
        if(time.equals("-1")) {
            return true;
        }
        SimpleDateFormat sf1 = new SimpleDateFormat("HHmm");
        int now = Integer.parseInt(sf1.format(new Date()));
        int ctl = Integer.parseInt(time);
        System.out.println(now);
        System.out.println(ctl);
        return now < ctl;
    }
    public static void main(String[] args) throws Exception {
//      DateUtils.getFirstAndLastDayOfWeek(2009,53);
//      DateUtils.getWeekOfDay("2009-01-05", "2009-01-06");
        System.out.println(DateUtils.isCanreport("2000"));
    }
    private static final String[] weeks = new String[]{"������","����һ","���ڶ�","������","������","������","������"};
    private static final String[] weekSing = new String[]{"��","һ","��","��","��","��","��"};
}
