package com.org.mokey.common.util.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LabelAndDataUtil {
    public static final String[] HEADER_96 =
        new String[]{ "00:00", "00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45", "02:00",
        "02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00",
        "05:15", "05:30", "05:45", "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45", "08:00",
        "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00",
        "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00",
        "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00",
        "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00",
        "20:15", "20:30", "20:45", "21:00", "21:15", "21:30", "21:45", "22:00", "22:15", "22:30", "22:45", "23:00",
        "23:15", "23:30", "23:45" };
    /**
     * ��ת�У����ڵ���excel
     * @param title��data.length+1
     * @param lables
     * @param data
     * @return
     */
    public static String[][] getExcelData(String[] title, String[] labels, String[][] data) {
        String[][] rtn = new String[labels.length][title.length];
        if(title.length != data.length + 1) {
            return rtn;
        }
        for (int i = 0; i < rtn.length; i++) {
            rtn[i][0] = labels[i];
            for (int j = 1; j < rtn[i].length; j++) {
                rtn[i][j] = data[j - 1][i];
            }
        }
        return rtn;
    }
    /**
     * ����ѯ������������������������
     * @param dates
     * @return
     */
    public static String[][] getConDatas(String[] dates, List<Map<String, String[]>> datas) {
        String[][] rtn = new String[datas.size()][dates.length * 96];
        for (int i = 0; i < datas.size(); i++) {
            for (int j = 0; j < dates.length; j++) {
                String[] tmp = datas.get(i).get(dates[j]);
                if(tmp!= null) {
                    System.arraycopy(tmp, 0, rtn[i], j * 96, tmp.length);
                }
            }
        }
        return rtn;
    }
    /**
     * ����ѯ������������������������
     * @param dates
     * @return
     */
    public static String[] getConDatas(String[] dates, Map<String, String[]> datas,int length) {
    	String[] rtn = new String[dates.length * length];
    		for (int j = 0; j < dates.length; j++) {
    			String[] tmp = datas.get(dates[j]);
    			if(tmp!= null) {
    				System.arraycopy(tmp, 0, rtn, j * length, tmp.length);
    			}
    		}
    	return rtn;
    }
    /**
     * ��ʼʱ��yyyy-mm-dd������ʱ�䣬����
     * @return
     */
    public static String[] getLabelByDate(String fromDate, String endDate, String[] dots) {
        String[] datas = getDatesArrays(fromDate, endDate);
        String[] rtn = new String[datas.length * dots.length];
        for (int i = 0, k = 0; i < datas.length; i++) {
            for (int j = 0; j < dots.length; j++) {
                rtn[k++] = datas[i] + " " + dots[j];
            }
        }
        return rtn;
    }

    /**
     * yyyy-mm-dd
     * @param fromDate
     * @param endDate
     * @return
     */
    public static String[] getDatesArrays(String fromDate, String endDate) {
        if(fromDate.equals(endDate)) {
            return new String[]{fromDate.replaceAll("-", "")};
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        try {
            Date fdate = sdf.parse(fromDate);
            Date edate = sdf.parse(endDate);
            Calendar f = Calendar.getInstance();
            f.setTime(fdate);
            Calendar e = Calendar.getInstance();
            e.setTime(edate);
            e.add(Calendar.DAY_OF_MONTH, 1);
            while(f.before(e)) {
                String year = f.get(Calendar.YEAR) + "";
                String month = (f.get(Calendar.MONTH) + 1) < 10 ? "0" + (f.get(Calendar.MONTH) + 1) : "" + (f.get(Calendar.MONTH) + 1);
                String day = (f.get(Calendar.DAY_OF_MONTH)) < 10 ? "0" + (f.get(Calendar.DAY_OF_MONTH)) : "" + (f.get(Calendar.DAY_OF_MONTH));
                list.add(year + "" + month + "" + day);
                f.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] rtn = new String[list.size()];
        list.toArray(rtn);
        return rtn;
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        String[] labels = LabelAndDataUtil.getLabelByDate("2011-01-12", "2011-01-13", HEADER_96);
        String[] dates = LabelAndDataUtil.getDatesArrays("2011-01-12", "2011-01-13");
        List<Map<String, String[]>> datass = new ArrayList<Map<String,String[]>>();// 
        String[] title = new String[]{"A","B","C"};
        for (int a = 0; a < title.length; a++) {
            for (int i = 0; i < dates.length; i++) {
                String[] tmp = new String[96];
                for (int j = 0; j < tmp.length; j++) {
                    tmp[j] = (i + j) + "";
                }
//                System.out.println(datass.size());
                if(a >= datass.size() && i == 0) {
                    datass.add(a, new HashMap<String, String[]>());
                }
//                System.out.println(datass.size());
                Map<String, String[]> dad = datass.get(a);
                dad.put(dates[i], tmp);
            }
        }
        String[][] r = LabelAndDataUtil.getConDatas(dates, datass);
      for (int i = 0; i < r.length; i++) {
      for (int j = 0; j < r[i].length; j++) {
          System.out.print(r[i][j] + "   ");
      }
      System.out.println();
  }
//        String[][] data = new String[3][4 * 96];
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[i].length; j++) {
//                data[i][j] = i + j + "";
//            }
//        }
//        String[] title = new String[]{"Time","A","B","C"};
//        String[][] r = LabelAndDataUtil.getExcelData(title, labels, data);
//        for (int i = 0; i < r.length; i++) {
//            for (int j = 0; j < r[i].length; j++) {
//                System.out.print(r[i][j] + "   ");
//            }
//            System.out.println();
//        }
    }
}