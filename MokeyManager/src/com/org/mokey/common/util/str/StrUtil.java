package com.org.mokey.common.util.str;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Encoder;

import com.org.mokey.common.util.DateUtils;

/**
 * @function 
 * @author <a href="mailto:zylgang@sina.com">liugang</a>
 * @create 2007-9-1
 * @version 1.0
 * @logs 2007-9-1����::
 */
public class StrUtil {
    Log log = LogFactory.getLog(this.getClass());
    public static String[] TIME_288  = new String[]{"0000","0005","0010","0015","0020","0025","0030",
        "0035","0040","0045","0050","0055","0100","0105","0110","0115","0120","0125","0130",
        "0135","0140","0145","0150","0155","0200","0205","0210","0215","0220","0225","0230",
        "0235","0240","0245","0250","0255","0300","0305","0310","0315","0320","0325","0330",
        "0335","0340","0345","0350","0355","0400","0405","0410","0415","0420","0425","0430",
        "0435","0440","0445","0450","0455","0500","0505","0510","0515","0520","0525","0530",
        "0535","0540","0545","0550","0555","0600","0605","0610","0615","0620","0625","0630",
        "0635","0640","0645","0650","0655","0700","0705","0710","0715","0720","0725","0730",
        "0735","0740","0745","0750","0755","0800","0805","0810","0815","0820","0825","0830",
        "0835","0840","0845","0850","0855","0900","0905","0910","0915","0920","0925","0930",
        "0935","0940","0945","0950","0955","1000","1005","1010","1015","1020","1025","1030",
        "1035","1040","1045","1050","1055","1100","1105","1110","1115","1120","1125","1130",
        "1135","1140","1145","1150","1155","1200","1205","1210","1215","1220","1225","1230",
        "1235","1240","1245","1250","1255","1300","1305","1310","1315","1320","1325","1330",
        "1335","1340","1345","1350","1355","1400","1405","1410","1415","1420","1425","1430",
        "1435","1440","1445","1450","1455","1500","1505","1510","1515","1520","1525","1530",
        "1535","1540","1545","1550","1555","1600","1605","1610","1615","1620","1625","1630",
        "1635","1640","1645","1650","1655","1700","1705","1710","1715","1720","1725","1730",
        "1735","1740","1745","1750","1755","1800","1805","1810","1815","1820","1825","1830",
        "1835","1840","1845","1850","1855","1900","1905","1910","1915","1920","1925","1930",
        "1935","1940","1945","1950","1955","2000","2005","2010","2015","2020","2025","2030",
        "2035","2040","2045","2050","2055","2100","2105","2110","2115","2120","2125","2130",
        "2135","2140","2145","2150","2155","2200","2205","2210","2215","2220","2225","2230",
        "2235","2240","2245","2250","2255","2300","2305","2310","2315","2320","2325","2330",
        "2335","2340","2345","2350","2355"};
    /**
     * ����С��λ
     * @create 2007-11-8
     * @author
     * @param d
     * @param scale
     * @return
     * @version 1.0
     * @logs 2007-11-8����::
     */
    public static double  foramatNumber(double d, int scale) {
        String s = "";
        java.text.NumberFormat format = NumberFormat.getNumberInstance() ;
        format.setMaximumFractionDigits(scale);
        format.setMinimumFractionDigits(scale);
        format.setGroupingUsed(false);
        s = format.format(d);
        return Double.parseDouble(s);
    }
    /**
     * ��һ������ת����һ��List
     * @param objs
     * @return
     */
    public static List arrayToList(Object[] objs) {
        List list = new ArrayList();
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                list.add(objs[i]);
            }
        }
        return list;
    }

    /**
     * ��һ���ַ��շָ���ָ��һ������
     * @param str
     * @param split
     * @return
     * TODO
     */
    public static Object[] strToArray(String str, String split) {
        if(str == null || split == null || str.equals("")) {
            return new Object[0];
        } else if(str != null && split != null && str.indexOf(split) == -1) {
            return new Object[]{str};
        } else {
            return str.split(split);
        }
    }
    /**
     * �ַ��͵�����ת�� from yyyy-mm-dd to yyyymmdd
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param ymd
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static String getyyyyMMdd(String ymd) {
        if(ymd ==null || ymd.equals("") || ymd.length() < 4) {
            return null;
        }
        if(ymd.length() == 4) {
            return ymd;
        }
        String[] datas = ymd.split("-");
        String ss = "";
        for(int i = 0; i < datas.length; i++) {
            ss += datas[i];
        }
        return ss;
    }

    /**123.118.18.235
     * 219.237.16.55
     * �ַ��͵�����ת�� from yyyymmdd to yyyy-mm-dd
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param ymd
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static String getyyyy2MM2dd(String ymd) {
        if(ymd ==null || ymd.equals("") || ymd.length() < 4) return null;
        String y = "";
        String m = "";
        String d = "";
        if(ymd.length() >= 4) {
            y = ymd.substring(0,4);
        }
        if(ymd.length() >= 6) {
            m = ymd.substring(4,6);
        }
        if(ymd.length() >= 8) {
            d = ymd.substring(6,8);
        }
        if(ymd.length() >= 0 && ymd.length() <=4) {
            return y;
        }
        if(ymd.length() >= 4 && ymd.length() <=6) {
            return y + "-" + m;
        }
        if(ymd.length() >= 6 && ymd.length() <=8) {
            return y + "-" + m + "-" + d;
        }
        return null;
    }

    /**
     * ��ȡĳ�°������
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param year
     * @param month
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public static int getDays(int year, int month) {
        if(month == 2) {
            if((year % 4 ==0 && year % 100 == 0) || (year % 4 ==0 || year % 100 == 0)) {
                return 29;
            } else {
                return 28;
            }
        }
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            return 31;
        }
        if(month == 4 || month == 6 || month == 9 || month == 11 ) {
            return 30;
        }
        return 30;
    }

    /**
     * ���һ�������
     * @create 2009-3-11
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static int getDaysInYear(String years) {
        int year = Integer.parseInt(years);
        if((year % 4 ==0 && year % 100 == 0) || (year % 4 ==0 || year % 100 == 0)) {
            return 366;
        } else {
            return 365;
        }
    }
    /**
     * ��ȡ����ÿѮ����ֹ����,��0��ʼ
     * @create 2009-3-11
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static int[][] getScopeIndexOfYearByTenDays(String year) {
        int[][] indexs = new int[36][2];
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), 0, 1);
        for(int i = 0; i < indexs.length; i++) {
            if(i % 3 == 0) {
                indexs[i][0] = cal.get(Calendar.DAY_OF_YEAR) - 1;
                cal.add(Calendar.DAY_OF_YEAR, 9);
                indexs[i][1] = cal.get(Calendar.DAY_OF_YEAR) -1;
            } 
            if(i % 3 == 1) {
                indexs[i][0] = cal.get(Calendar.DAY_OF_YEAR);
                cal.add(Calendar.DAY_OF_YEAR, 9);
                indexs[i][1] = cal.get(Calendar.DAY_OF_YEAR);
            } 
            if(i % 3 == 2) {
                indexs[i][0] = cal.get(Calendar.DAY_OF_YEAR) + 1;
                if(i != 35) {
                    cal.set(Integer.parseInt(year), (i / 3) + 1, 1);
                    indexs[i][1] = cal.get(Calendar.DAY_OF_YEAR) - 2;
                } else {
                    cal.set(Integer.parseInt(year) + 1, 0, 1);
                    indexs[i][1] = getDaysInYear(year) - 1;
                }
            } 
        }
        return indexs;
    }

    public static int[] getDaysofTenDays(String year) {
        int[] days = new int[36];
        int[][] dayScop = getScopeIndexOfYearByTenDays(year);
        for(int i = 0; i <  dayScop.length; i++) {
            days[i] = (dayScop[i][1] - dayScop[i][0]) + 1;
        }
        return days;
    }
    /**
     * ��ȡ����ÿ�µ���ֹ����,��0��ʼ
     * @create 2009-3-11
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static int[][] getScopeIndexOfYearByMonth(String year) {
        int[][] indexs = new int[12][2];
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), 0, 1);
        for(int i = 0; i < indexs.length; i++) {
            indexs[i][0] = cal.get(Calendar.DAY_OF_YEAR) - 1;
            int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.add(Calendar.DAY_OF_YEAR, days);
            if(i == 11) {
                indexs[i][1] = getDaysInYear(year) - 1;
            } else {
                indexs[i][1] = cal.get(Calendar.DAY_OF_YEAR) - 2 ;
            }
            cal.set(Calendar.MONTH, i + 1);
        }
        return indexs;
    }
    /**
     * ��ȡ�������/��С����:0���,1��С
     * @create 2009-3-11
     * @author
     * @param data
     * @param f
     * @param t
     * @param type
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static int getDataIndex(Double[] data, int f, int t, int type) {
        int index = f;
        double d = data[f].doubleValue();
        for(int i = f; i <= t; i++) {
            if(type == 0) {
                if(d < data[i].doubleValue()) {
                    d = data[i].doubleValue();
                    index = i;
                }
            } else if(type == 1){
                if(d > data[i].doubleValue()) {
                    d = data[i].doubleValue();
                    index = i;
                }
            }
        }
        return index;
    }
    /**
     * ��ȡ��ѯ���������Զ����
     * @create 2009-3-11
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static String[] getQueryDayValues(String year) {
        String[] days = new String[getDaysInYear(year)];
        Calendar scal = Calendar.getInstance();
        Calendar ecal = Calendar.getInstance();
        scal.set(Integer.parseInt(year),0, 1, 0, 0, 0);
        ecal.set(Integer.parseInt(year),11, 31, 0, 0, 0);
        int i = 0;
        while(scal.before(ecal)) {
            String month = (scal.get(Calendar.MONTH) + 1) > 9 ? ("" + (scal.get(Calendar.MONTH) + 1)) : ("0" + (scal.get(Calendar.MONTH) + 1));
            String day = (scal.get(Calendar.DAY_OF_MONTH)) > 9 ? ("" + (scal.get(Calendar.DAY_OF_MONTH))) : ("0" + (scal.get(Calendar.DAY_OF_MONTH)));
            days[i++] = "VAL" + month + day;
            scal.add(Calendar.DAY_OF_YEAR, 1);
        }
        days[i] = "val1231";
        return days;
    }
    /**
     * ��ȡ��ѯ���������Զ����
     * @create 2009-3-11
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static String getQueryDayValuesString(String year) {
        StringBuffer sf = new StringBuffer();
        Calendar scal = Calendar.getInstance();
        Calendar ecal = Calendar.getInstance();
        scal.set(Integer.parseInt(year),0, 1, 0, 0, 0);
        ecal.set(Integer.parseInt(year),11, 31, 0, 0, 0);
        while(scal.before(ecal)) {
            String month = (scal.get(Calendar.MONTH) + 1) > 9 ? ("" + (scal.get(Calendar.MONTH) + 1)) : ("0" + (scal.get(Calendar.MONTH) + 1));
            String day = (scal.get(Calendar.DAY_OF_MONTH)) > 9 ? ("" + (scal.get(Calendar.DAY_OF_MONTH))) : ("0" + (scal.get(Calendar.DAY_OF_MONTH)));
            sf.append("VAL").append(month).append(day).append(",");
            scal.add(Calendar.DAY_OF_YEAR, 1);
        }
        sf.append("val1231");
        return sf.toString();
    }

    /**
     * ��������ȡָ���������Ӧ��������
     * @create Mar 18, 2009
     * @author bluton
     * @param index
     * @return
     * @version 1.0
     * @logs Mar 18, 2009
     */
    public static String[] getYmdByIndex(String year) {
        String[] array = new String[getDaysInYear(year)];
        Calendar scal = Calendar.getInstance();
        Calendar ecal = Calendar.getInstance();
        scal.set(Integer.parseInt(year),0, 1, 0, 0, 0);
        ecal.set(Integer.parseInt(year),11, 31, 0, 0, 0);
        int i = 0;
        while(scal.before(ecal)) {
            String month = (scal.get(Calendar.MONTH) + 1) > 9 ? ("" + (scal.get(Calendar.MONTH) + 1)) : ("0" + (scal.get(Calendar.MONTH) + 1));
            String day = (scal.get(Calendar.DAY_OF_MONTH)) > 9 ? ("" + (scal.get(Calendar.DAY_OF_MONTH))) : ("0" + (scal.get(Calendar.DAY_OF_MONTH)));
            array[i++] = year + "-" + month + "-" + day;
            scal.add(Calendar.DAY_OF_YEAR, 1);
        }
        String month = "" + (scal.get(Calendar.MONTH) + 1);
        array[i] = year + "-" + month + "-31";
        return array;
    }
    /**
     * ��ȡ��ѯ���������Զ����
     * @create 2009-3-11
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static String getQuerySumDayValuesString(String year) {
        StringBuffer sf = new StringBuffer();
        Calendar scal = Calendar.getInstance();
        Calendar ecal = Calendar.getInstance();
        scal.set(Integer.parseInt(year),0, 1, 0, 0, 0);
        ecal.set(Integer.parseInt(year),11, 31, 0, 0, 0);
        while(scal.before(ecal)) {
            String month = (scal.get(Calendar.MONTH) + 1) > 9 ? ("" + (scal.get(Calendar.MONTH) + 1)) : ("0" + (scal.get(Calendar.MONTH) + 1));
            String day = (scal.get(Calendar.DAY_OF_MONTH)) > 9 ? ("" + (scal.get(Calendar.DAY_OF_MONTH))) : ("0" + (scal.get(Calendar.DAY_OF_MONTH)));
            sf.append("SUM(VAL").append(month).append(day).append("),");
            scal.add(Calendar.DAY_OF_YEAR, 1);
        }
        sf.append("SUM(val1231)");
        return sf.toString();
    }
    /**
     * ��ȡ��ѯ���������Զ�������
     * @create 2009-3-11
     * @author
     * @param year
     * @return
     * @version 1.0
     * @logs 2009-3-11
     */
    public static String[] getQuerySumDayValues(String year) {
        String[] days = new String[getDaysInYear(year)];
        Calendar scal = Calendar.getInstance();
        Calendar ecal = Calendar.getInstance();
        scal.set(Integer.parseInt(year),0, 1, 0, 0, 0);
        ecal.set(Integer.parseInt(year),11, 31, 0, 0, 0);
        int i = 0;
        while(scal.before(ecal)) {
            String month = (scal.get(Calendar.MONTH) + 1) > 9 ? ("" + (scal.get(Calendar.MONTH) + 1)) : ("0" + (scal.get(Calendar.MONTH) + 1));
            String day = (scal.get(Calendar.DAY_OF_MONTH)) > 9 ? ("" + (scal.get(Calendar.DAY_OF_MONTH))) : ("0" + (scal.get(Calendar.DAY_OF_MONTH)));
            days[i++] = "SUM(VAL" + month + day + ")";
            scal.add(Calendar.DAY_OF_YEAR, 1);
        }
        days[i] = "sum(val1231)";
        return days;
    }
    /**
     * �ָ��ַ�
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param source
     * @param split
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static String[] splitString(String source, String split) {
        if (source == null || source.length() == 0) {
            return new String[0];
        }
        StringTokenizer token = new StringTokenizer(source, split);
        int count = token.countTokens();
        if (count <= 0) {
            return new String[0];
        }
        String[] s = new String[count];
        for (int i = 0; i < count; i++) {
            s[i] = token.nextToken().trim();
        }
        return s;
    }

    /**
     * ����·ݷ��ظ������ڵļ���
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param month
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static String getMonthInQuarter(int month) {
        if(month >12 || month < 0) {
            return "01";
        }
        String qurater = "01";
        switch (month) {
        case 1: case 2: case 3:
            qurater = "01";
            break;
        case 4: case 5: case 6:
            qurater = "02";
            break;
        case 7: case 8: case 9:
            qurater = "03";
            break;
        case 10: case 11: case 12:
            qurater = "04";
            break;
        default:
            qurater = "01";
        break;
        }
        return qurater;
    }

    /**
     * ����·ݷ��ظ��·����ڵİ���
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param month
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static String getMonthInSyear(int month) {
        if(month >12 || month < 0) {
            return "01";
        }
        String qurater = "01";
        switch (month) {
        case 1: case 2: case 3: case 4: case 5: case 6:
            qurater = "01";
            break;        
        case 7: case 8: case 9: case 10: case 11: case 12:
            qurater = "02";
            break;

        default:
            qurater = "01";
        break;
        }
        return qurater;
    }

    /**
     * ���ص��¼���֮ǰ�����·�
     * ��������2���򷵻� []{01,02,03}
     * @create 2007-9-1
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param month
     * @return
     * @version 1.0
     * @logs 2007-9-1����::
     */
    public String[] getMonthsInquarter(int month) {
        if(month >12 || month < 0) {
            return new String[]{};
        }
        String[] months = null;
        switch (month) {
        case 1: case 2: case 3:
            months = new String[]{"01","02","03"};
            break;
        case 4: case 5: case 6:
            months = new String[]{"01","02","03","04","05","06"};
            break;
        case 7: case 8: case 9:
            months = new String[]{"01","02","03","04","05","06","07","08","09"};
            break;
        case 10: case 11: case 12:
            months = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
            break;
        default:
            months = new String[]{};
        break;
        }
        return months;
    }
    /**
     * ���ص�ǰʵ����һ���е�����,��0��ʼ.һ���00:00��ʼ��23:59����
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param hhmm ��ǰʵ����ַ���ʽ
     * @param dot ʵ����� 96���288��ȵ�
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static int getCurrentTimeIndex(String hhmm, int dot) {
        if(hhmm == null || hhmm.length() < 4 || dot <=0 || dot >=24*36*36) {
            return 0;
        }
        //һ���еķ�����
        int minPerDot = 24*60 / dot;
        //��ǰСʱ
        int hour = Integer.parseInt(hhmm.substring(0,2));
        //��ǰ����
        int min = Integer.parseInt(hhmm.substring(2,4));
        return (hour * 60 + min) / minPerDot;
    }

    /**
     * ���ص�ǰʵ��ı�׼��ʵ��.һ���00:00��ʼ��23:59����
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param hhmm
     * @param dot
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static String getCurrentDotTime(String hhmm, int dot) {
        if(hhmm == null || hhmm.length() < 4 || dot <= 0 || dot >= 24*36*36) {
            return "0000";
        }
        //һ���еķ�����
        int minPerDot = 24*60 / dot;
        //��ǰСʱ
        String hour = hhmm.substring(0,2);
        //��ǰ����
        int min = Integer.parseInt(hhmm.substring(2,4));
        String minute = "0000" + ((min/minPerDot) * minPerDot);
        String timeStr = hour + minute.substring(minute.length()-2);
        return timeStr;
    }

    /**
     * ��ȡΨһ��־��
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public static String uniqueCode(){
        // TODO Auto-generated method stub\
        Date date1 = new Date();
        String uniqueCode = null;
        try {
            double rand = Math.random();
            String  randStr = String.valueOf(rand);
            String  zeroStr = "000000";            //appending string 
            int pos1 = randStr.indexOf(".");   //start position of dividing string

            if(pos1 < 0) {
                pos1 = 0;
            } else if(pos1 == 0) {
                pos1 = 1;
            } else if(pos1 > 0) {
                pos1 += 1;
            }
            randStr = randStr.substring(pos1);
            int pos2 = 6;                      //end   position of dividing string
            if(randStr.length() >= pos2) {
                randStr=randStr.substring(0, pos2);
            } else {
                int endPos = pos2 - randStr.length();
                randStr = randStr+zeroStr.substring(0, endPos);
            }

            long timeSys = System.currentTimeMillis();
            String timeStr = String.valueOf(timeSys);
            if(timeStr.length() < 13) {
                timeStr = "0" + timeStr;
            }
            uniqueCode = timeStr + randStr;       
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date date2 = new Date();
        return uniqueCode;
    } 

    /**
     * ���ַ����MD5��,����Base64��������
     * @create 2007-8-22
     * @author <a href="mailto:zylgang@sina.com">liugang
     * @param key
     * @return
     * @version 1.0
     * @logs 2007-8-22����::
     */
    public final static String md5(String key){
        MessageDigest dlg = null;
        BASE64Encoder encode = new BASE64Encoder();
        if (key == null){
            return null;
        }
        try {
            if (dlg == null){
                dlg = MessageDigest.getInstance("MD5");
            } 
            byte[] res = dlg.digest(key.getBytes());
            return encode.encode(res);          
        } catch (NoSuchAlgorithmException e) {          
            throw new IllegalArgumentException("get md5 digest error");         
        }
    }       
    /**
     * ����������
     * @create 2008-12-3
     * @author
     * @param pwd_len
     * @return
     * @version 1.0
     * @logs 2008-12-3
     */
    public static String genRandomPass(int pwd_len){
        final int  maxNum = 49;
        int i;  //��ɵ������
        int count = 0; //��ɵ�����ĳ���
        String[] str = {"2", "3", "4", "5", "6", "7", "8", "9", "a", "b", 
                "c", "d", "e", "f", "g", "h", "j", "k", "m", "n",
                "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
                "z", "@", "#", "$", "&", "*", "A", "B", "D", "E",
                "F", "G", "H", "L", "M", "N", "Q", "R", "T", "Y"};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while(count < pwd_len){
            //��������ȡ���ֵ����ֹ��ɸ���
            i = Math.abs(r.nextInt(maxNum));  //��ɵ������Ϊ36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count ++;
            }
        }
        return pwd.toString();
    }

    /**
     * ���������������������
     * @create 2009-1-5
     * @author
     * @param from
     * @param to
     * @return
     * @version 1.0
     * @logs 2009-1-5
     */
    public static int getDaysBetweenTwoDay(Date from, Date to) {
        if(to.before(from)) {
            return 0;
        }
        long times = to.getTime() - from.getTime();
        return (int)(((times/1000)/60)/60)/24;
    }

    /**
     * �����������ڵ�����
     * @create 2009-1-5
     * @author
     * @param from
     * @param to
     * @return
     * @version 1.0
     * @logs 2009-1-5
     */
    public static int getDaysBetweenTwoDay(String from, String to) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        try {
            return getDaysBetweenTwoDay(sf.parse(from), sf.parse(to));
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * �����������
     * @create 2008-12-3
     * @author
     * @return
     * @version 1.0
     * @logs 2008-12-3
     */
    public static int getSerialNumber(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        int number = Integer.parseInt(date.substring(0, 4));
        number += Integer.parseInt(date.substring(5, 7));
        number += Integer.parseInt(date.substring(8, 10));
        number += Integer.parseInt(date.substring(11, 13));
        number += Integer.parseInt(date.substring(14, 16));
        number = Integer.parseInt(number + date.substring(17));
        return number;
    }
    /**
     * ��ݴ������ֹ���ڣ���ʶ�����ڵ�ʱ��Σ��������0��ʶ�������1��ʶ
     * @create 2009-3-5
     * @author
     * @param dots
     * @param type
     * @version 1.0
     * @logs 2009-3-5
     */
    public static void setSingnalOfDate(String[] dots, Date fromDate, Date endDate, String year) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(fromDate);
        int syear = calStart.get(Calendar.YEAR);
        int smonth = calStart.get(Calendar.MONTH);
        int sday = calStart.get(Calendar.DAY_OF_MONTH);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endDate);
        int eyear = calEnd.get(Calendar.YEAR);
        int emonth = calEnd.get(Calendar.MONTH);
        int eday = calEnd.get(Calendar.DAY_OF_MONTH);
        int sxun = 1;
        int exun = 3;
        String svalue = "1";
        String evalue = "1";
        if(syear == eyear && eyear != Integer.parseInt(year)) return;
        if(sday >=1 && sday <= 10) {
            sxun = 0;
            if(sday > 1) svalue = "0";
        } else if(sday >10 && sday <= 20) {
            sxun = 1;
            if(sday > 11) svalue = "0";
        } else if(sday >20){
            sxun = 2;
            if(sday > 21) svalue = "0";
        }
        if(eday >=1 && eday <= 10) {
            exun = 0;
            if(eday < 10) evalue = "0";
        } else if(eday >10 && eday <= 20) {
            exun = 1;
            if(eday < 20) evalue = "0";
        } else if(eday >20){
            exun = 2;
            if(eday < calEnd.getMaximum(Calendar.DAY_OF_MONTH)) evalue = "0";
        }
        if(syear == eyear) {
            for(int i = smonth * 3 + sxun; i <= emonth * 3 + exun; i++) {
                dots[i] = "1";
            }
            dots[smonth * 3 + sxun] = svalue;
            dots[emonth * 3 + exun] = evalue;
        } else if(syear < eyear && String.valueOf(syear).equals(year)) {
            for(int i = smonth * 3 + sxun; i < dots.length; i++) {
                dots[i] = "1";
            }
            dots[smonth * 3 + sxun] = svalue;
        } else if(syear < eyear && String.valueOf(eyear).equals(year)) {
            for(int i = 0; i <= emonth * 3 + exun; i++) {
                dots[i] = "1";
            }
//          dots[smonth * 3 + sxun] = svalue;
            dots[emonth * 3 + exun] = evalue;
        }
    }
    /**
     * ��ݴ������ֹ���ڣ���ʶ�����ڵ�ʱ��Σ��������0��ʶ�������1��ʶ
     * @create 2009-3-5
     * @author
     * @param dots
     * @param type
     * @version 1.0
     * @logs 2009-3-5
     */
    public static void setSingnalOfDateByMonth(String[] dots, Date fromDate, Date endDate, String year, String month) {
        Calendar scal = Calendar.getInstance();
        scal.setTime(fromDate);
        int syear = scal.get(Calendar.YEAR);
        Calendar ecal = Calendar.getInstance();
        ecal.setTime(endDate);
        int eyear = ecal.get(Calendar.YEAR);

        int years = Integer.parseInt(year);
        int months = Integer.parseInt(month);

        //��ʼʱ����������ڲ��ڲ�ѯ���
        if(syear == eyear && syear != years) return;
        if(syear == eyear) {
        } 
        if(syear < eyear && syear < years) {
            scal.set(years, 0, 1);
        }
        if(syear < eyear && eyear > years) {
            ecal.set(years, 11, 31);
        }
        int smonth = scal.get(Calendar.MONTH);
        int emonth = ecal.get(Calendar.MONTH);
        if(smonth == emonth && smonth + 1 != months) {
            return;
        }

        if(emonth == smonth) {
//          ecal.add(Calendar.DAY_OF_MONTH, 1);
        }
        if((emonth > smonth) && (emonth > (months - 1))) {
            int maxDay = getDays(years, months);
            ecal.set(years, months - 1, maxDay);
        } 
        if((emonth > smonth) && (smonth < (months - 1))) {
            scal.set(years, months - 1, 1);
        } 
        int sday = scal.get(Calendar.DAY_OF_MONTH);
        int eday = ecal.get(Calendar.DAY_OF_MONTH);
        for(int i = sday - 1; i < eday; i++) {
            dots[i] = "1";
        }
    }

    public static void setSingnalOfDateByWeek(String[] dots, String year, Long weekIndex, Date fromDate, Date endDate) {
        String[] date = DateUtils.getFirstAndLastDayOfWeek(Integer.valueOf(year), weekIndex.intValue(), Calendar.TUESDAY);
        Calendar scal = Calendar.getInstance();
        scal.setTime(fromDate);
        Calendar ecal = Calendar.getInstance();
        ecal.setTime(endDate);
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        Date fDate = null;
        Date eDate = null;
        try {
            fDate = formate.parse(date[0]);
            eDate = formate.parse(date[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(scal.getTime().getTime() < ecal.getTime().getTime() && scal.getTime().getTime() < fDate.getTime()) {
            scal.setTime(fDate);
        }
        if(scal.getTime().getTime() < ecal.getTime().getTime() && ecal.getTime().getTime() > eDate.getTime()) {
            ecal.setTime(eDate);
        }
        int sday = scal.get(Calendar.DAY_OF_WEEK);
        int eday = ecal.get(Calendar.DAY_OF_WEEK);
        while(sday != eday) {
            if(sday >= 3) {
                dots[sday - 3] = "1";
            } else if(sday < 3) {
                dots[sday + 4] = "1";
            }
            scal.add(Calendar.DATE, 1);
            sday = scal.get(Calendar.DAY_OF_WEEK);
        }
        if(eday >= 3) {
            dots[eday - 3] = "1";
        } else if(sday < 3) {
            dots[eday + 4] = "1";
        }
    }

    /**
     * ��ݴ������ֹ���ں�������������������
     * @create 2009-3-10
     * @author
     * @param fromDate
     * @param endDate
     * @param cap
     * @return
     * @version 1.0
     * @logs 2009-3-10
     */
    public static void setCapToArray(Double[] data, Date fromDate, Date endDate, double cap, String year) {
        Calendar scal = Calendar.getInstance();
        scal.setTime(fromDate);
        Calendar ecal = Calendar.getInstance();
        ecal.setTime(endDate);
        if(scal.get(Calendar.YEAR) == ecal.get(Calendar.YEAR) && scal.get(Calendar.YEAR) != Integer.parseInt(year)) return;
        if(scal.get(Calendar.YEAR) == ecal.get(Calendar.YEAR)) {
            ecal.add(Calendar.DAY_OF_YEAR, 1);
        }
        if(scal.get(Calendar.YEAR) < ecal.get(Calendar.YEAR) && scal.get(Calendar.YEAR) < Integer.parseInt(year)) {
            scal.set(Integer.parseInt(year), 0, 1);
            ecal.add(Calendar.DAY_OF_YEAR, 1);
        }
        if(scal.get(Calendar.YEAR) < ecal.get(Calendar.YEAR) && ecal.get(Calendar.YEAR) > Integer.parseInt(year)) {
            ecal.set(Integer.parseInt(year) + 1, 0, 1);
        }
        while(scal.before(ecal)) {
            data[scal.get(Calendar.DAY_OF_YEAR) - 1] += cap;
            scal.add(Calendar.DAY_OF_YEAR, 1);
        }
    }   
    /**
     * ��ݴ������ֹ���ں�������ȡָ���µĸ��յļ�������
     * @create 2009-3-10
     * @author
     * @param fromDate
     * @param endDate
     * @param cap
     * @return
     * @version 1.0
     * @logs 2009-3-10
     */
    public static void setCapToArrayForMonth(Double[] data, Date fromDate, Date endDate, double cap, String year, String month) {
        Calendar scal = Calendar.getInstance();
        scal.setTime(fromDate);
        int syear = scal.get(Calendar.YEAR);
        Calendar ecal = Calendar.getInstance();
        ecal.setTime(endDate);
        int eyear = ecal.get(Calendar.YEAR);


        int years = Integer.parseInt(year);
        int months = Integer.parseInt(month);

        //��ʼʱ����������ڲ��ڲ�ѯ���
        if(syear == eyear && syear != years) return;
        if(syear == eyear) {
        } 
        if(syear < eyear && syear < years) {
            scal.set(years, 0, 1);
        }
        if(syear < eyear && eyear > years) {
            ecal.set(years, 11, 31);
        }
        int smonth = scal.get(Calendar.MONTH);
        int emonth = ecal.get(Calendar.MONTH);
        if(smonth == emonth && smonth + 1 != months) {
            return;
        }

        if(emonth == smonth) {
//          ecal.add(Calendar.DAY_OF_MONTH, 1);
        }
        if((emonth > smonth) && (emonth > (months - 1))) {
            int maxDay = getDays(years, months);
            ecal.set(years, months - 1, maxDay);
        } 
        if((emonth > smonth) && (smonth < (months - 1))) {
            scal.set(years, months - 1, 1);
        } 
        ecal.add(Calendar.DAY_OF_MONTH, 1);
        while(scal.before(ecal)) {
            data[scal.get(Calendar.DAY_OF_MONTH) - 1] += cap;
            scal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    /**
     * ��ݴ������ֹ���ں��������ü��޵���
     * ��ʽΪ24*����
     * @create 2009-3-10
     * @author
     * @param fromDate
     * @param endDate
     * @param cap
     * @return
     * @version 1.0
     * @logs 2009-3-10
     */
    public static void setEngToArray(Double[] data, Date fromDate, Date endDate, double cap, String year) {
        Calendar scal = Calendar.getInstance();
        scal.setTime(fromDate);
        Calendar ecal = Calendar.getInstance();
        ecal.setTime(endDate);
        if(scal.get(Calendar.YEAR) == ecal.get(Calendar.YEAR) && scal.get(Calendar.YEAR) != Integer.parseInt(year)) return;
        if(scal.get(Calendar.YEAR) == ecal.get(Calendar.YEAR)) {
            ecal.add(Calendar.DAY_OF_YEAR, 1);
        }
        if(scal.get(Calendar.YEAR) < ecal.get(Calendar.YEAR) && scal.get(Calendar.YEAR) < Integer.parseInt(year)) {
            scal.set(Integer.parseInt(year), 0, 1);
            ecal.add(Calendar.DAY_OF_YEAR, 1);
        } 
        if(scal.get(Calendar.YEAR) < ecal.get(Calendar.YEAR) && ecal.get(Calendar.YEAR) > Integer.parseInt(year)) {
            ecal.set(Integer.parseInt(year) + 1, 0, 1);
        }
        while(scal.before(ecal)) {
            data[scal.get(Calendar.DAY_OF_YEAR) - 1] = data[scal.get(Calendar.DAY_OF_YEAR) - 1] + (cap * 24);
            scal.add(Calendar.DAY_OF_YEAR, 1);
        }
    }
    /**
     * ��ȡָ������Ӧֵ��ע���������Ϊ�ַ��ͣ�
     * @create Mar 10, 2009
     * @author bluton
     * @param array
     * @return
     * @version 1.0
     * @logs Mar 10, 2009
     */
    public static String calculateArray(String[] array) {
        return getMaxByArray(array);
    }

    /**
     * ��ȡ���ֵ
     * @create Mar 10, 2009
     * @author bluton
     * @param array
     * @version 1.0
     * @logs Mar 10, 2009
     */
    public static String getMaxByArray(String[] array) {
        Double maxNum = null;
        if(array == null) {
            return null;
        }
        for(int i = 0; i < array.length; i++) {
            if(array[i] == null || array[i].equals("")) {
                continue;
            }
            if(maxNum == null) {
                maxNum = Double.valueOf(array[i]);
            } else {
                Double num = Double.valueOf(array[i]);
                if(num.doubleValue() > maxNum.doubleValue()) {
                    maxNum = num;
                }
            }
        }
        if(maxNum == null) {
            return null;
        }
        return maxNum.toString();
    }

    /**
     * ��ȡ���ֵ(��ֵ��)
     * @create Mar 10, 2009
     * @author bluton
     * @param array
     * @version 1.0
     * @logs Mar 10, 2009
     */
    public static Double getMaxByArray(Double[] array) {
        Double maxNum = null;
        if(array == null) {
            return null;
        }
        for(int i = 0; i < array.length; i++) {
            if(array[i] == null || array[i].equals("")) {
                continue;
            }
            if(maxNum == null) {
                maxNum = Double.valueOf(array[i]);
            } else {
                Double num = Double.valueOf(array[i]);
                if(num.doubleValue() > maxNum.doubleValue()) {
                    maxNum = num;
                }
            }
        }
        if(maxNum == null) {
            return null;
        }
        return maxNum;
    }

    public static String[] getValAsMonth(int year, int month) {
        int num = getDays(year, month);
        String[] vals = new String[num];
        for(int i = 0; i < vals.length; i++) {
            String val = "VAL" + (i < 9 ? "0" + (i + 1) : (i + 1));
            vals[i] = val;
        }
        return vals;
    }
    /**
     * ���������֯��ѯ����
     * @create Mar 24, 2009
     * @author bluton
     * @param year
     * @param month
     * @return
     * @version 1.0
     * @logs Mar 24, 2009
     */
    public static String getValAsMonthAsString(int year, int month) {
        int num = getDays(year, month);
        StringBuffer sql = new StringBuffer();
        for(int i = 0; i < num; i++) {
            String val = "VAL" + (i < 9 ? "0" + (i + 1) : (i + 1));
            sql.append(val).append(",");
        }
        return sql.toString();
    }
    /**
     * ���������֯��ѯ����
     * @create Mar 24, 2009
     * @author bluton
     * @param year
     * @param month
     * @return
     * @version 1.0
     * @logs Mar 24, 2009
     */
    public static String getSumValAsMonthAsString(int year, int month) {
        int num = getDays(year, month);
        StringBuffer sql = new StringBuffer();
        for(int i = 0; i < num; i++) {
            String val = "sum(VAL" + (i < 9 ? "0" + (i + 1) : (i + 1));
            sql.append(val).append("),");
        }
        return sql.toString();
    }
    /**
     * ��ȡ��Сֵ
     * @create Mar 10, 2009
     * @author bluton
     * @param array
     * @version 1.0
     * @logs Mar 10, 2009
     */
    public static String getMinByArray(String[] array) {
        Double minNum = null;
        if(array == null) {
            return null;
        }
        for(int i = 0; i < array.length; i++) {
            if(array[i] == null || array[i].equals("") || !StrUtil.isNumberic(array[i])) {
                continue;
            }
            if(minNum == null) {
                minNum = Double.valueOf(array[i]);
            } else {
                Double num = Double.valueOf(array[i]);
                if(num.doubleValue() < minNum.doubleValue()) {
                    minNum = num;
                }
            }
        }
        if(minNum == null) {
            return null;
        }
        return minNum.toString();
    }
    /**
     * ��ȡ��Сֵ�±�
     * @create Mar 10, 2009
     * @author bluton
     * @param array
     * @version 1.0
     * @logs Mar 10, 2009
     */
    public static int getMinByArray(Double[] array) {
        Double minNum = null;
        int index = 0 ;
        if(array == null) {
            return index;
        }
        for(int i = 0; i < array.length; i++) {
            if(array[i] == null || array[i].equals("")) {
                continue;
            }
            if(minNum == null) {
                minNum = array[i];
            } else {
                Double num = array[i];
                if(num.doubleValue() < minNum.doubleValue()) {
                    minNum = num;
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * ��ȡƽ��ֵ
     * @create Mar 10, 2009
     * @author bluton
     * @param array
     * @version 1.0
     * @logs Mar 10, 2009
     */
    public static String getAvgByArray(String[] array) {
        Double totalNum = null;
        if(array == null) {
            return null;
        }
        int count = 0;
        for(int i = 0; i < array.length; i++) {
            if(array[i] == null || array[i].equals("")) {
                continue;
            }
            if(totalNum == null) {
                totalNum = Double.valueOf(array[i]);
            } else {
                Double num = Double.valueOf(array[i]);
                totalNum += num;
            }
            count ++;
        }
        if(totalNum == null ||  count == 0) {
            return null;
        }
        Double ave = totalNum.doubleValue()/count;
        return ave.toString();

    }
    /**
     * �ж��Ƿ�������
     * @create Mar 10, 2009
     * @author bluton
     * @param str
     * @return
     * @version 1.0
     * @logs Mar 10, 2009
     */
    public static boolean isNumberic(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }

    /**
     * ��ʽ���ڵĸ�ʽ
     * @create May 4, 2009
     * @author bluton
     * @param flag
     * @param date
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static String formateDate(int flag, String date) {
        if(flag == 0) {
            return date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);
        } 
        String[] newDates = date.split("-");
        return newDates[0] + newDates[1] + newDates[2];
    }
    /**
     * ��ʽ���ڵĸ�ʽ
     * @create May 4, 2009
     * @author bluton
     * @param flag
     * @param date
     * @return
     * @version 1.0
     * @logs May 4, 2009
     */
    public static String formateToYmd(String date) {
        if(date == null || date.equals("")) {
            return "";
        }
        String[] newDates = date.split("-");
        String day = newDates[2].substring(0,newDates[2].indexOf(" "));
        return newDates[0] + (Integer.parseInt(newDates[1]) < 10 ? "0" + Integer.parseInt(newDates[1]) : newDates[1]) 
        + (Integer.parseInt(day) < 10 ? "0" + Integer.parseInt(day) : day);
    }
    public static String arrayToStr(String[] src,String sp){
    	if(src==null||src.length==0){
    		return  null;
    	}
    	StringBuffer sb =new StringBuffer();
    	int length=src.length-1;
    	for (int i = 0; i < src.length; i++) {
    		if(i==length){
    			sb.append(src[i]);
    		}else{
    			sb.append(src[i]).append(",");
    		}
    		
		}
    	return sb.toString();
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < TIME_288.length; i++) {
//            System.out.println("VAL" + TIME_288[i++] + "  NUMBER(10,2),");
//            i++;
//        }
//        String a = "12334.a";
//        char d='.';
//        for(int i = 0; i < a.length(); i++) {
//            char b = a.charAt(i);
//            int c = (int) b;
//            if((c < 48 || c > 57) && c !=46) {
//                a = a.substring(0, i);
//            }
//        }
//      String a = "20090410";
//      SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");
//      SimpleDateFormat formate1 = new SimpleDateFormat("yyyy-MM-dd");
//      try {
//      Date date = formate.parse("2009403");
//      } catch (ParseException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//      }
//      String[] sss = new String[31];
//      for(int i = 0; i < sss.length; i++) {
//      sss[i] = "";
//      }
//      SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

//      try {
//      setSingnalOfDateByMonth(sss, sf.parse("20090102"), sf.parse("20090202"), "2009", "1");
////    setSingnalOfDate(sss, sf.parse("20090401"), sf.parse("20090525"));
//      } catch(ParseException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//      }
//      for(int i = 0; i < sss.length; i++) {
//      System.out.print(sss[i] == null ? "|" : "" + sss[i]);
//      }
//      String[] ddd = getQueryDayValues("2008");
//      for(String string : ddd) {
//      System.out.print(string + " ");
//      }
//      int[][] s = getScopeIndexOfYearByTenDays("2008");
//      for(int i = 0; i < s.length; i++) {
//      }

//    }

}
