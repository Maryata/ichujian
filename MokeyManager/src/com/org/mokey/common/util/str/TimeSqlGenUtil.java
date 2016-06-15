package com.org.mokey.common.util.str;

public final class TimeSqlGenUtil {

    /**
     * �õ�����ʱ�̵��ֶ����
     * @param dotNum    ʱ�̵���
     * @return
     */
    public static String getDotTimeString(int dotNum) {
        StringBuffer sb = new StringBuffer();
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("VAL" + iStr + jStr + ",");
                k++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }
    public static String getDotMTimeString(int dotNum) {
        StringBuffer sb = new StringBuffer();
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("VAL" + iStr + jStr + "/1000,");
                k++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String[] getDotTimeArray(int dotNum) {
        String[] data = new String[dotNum];
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                data[k] = "VAL" + iStr + jStr;
                k++;
            }
        }
        return data;
    }

    public static String[] getDotTimeArray(int dotNum, String prefixStr) {
        String[] data = new String[dotNum];
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                data[k] = prefixStr + iStr + jStr;
                k++;
            }
        }
        return data;
    }

    /**
     * �õ�����ʱ�̵��ֶ�ȡsum����ַ�
     * @param dotNum    ʱ�̵���
     * @return
     */
    public static String getSumDotTimeString(int dotNum) {
        StringBuffer sb = new StringBuffer();
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {

                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("SUM(VAL" + iStr + jStr + "),");
                k++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }
    /**
     * �õ�����ʱ�̵��ֶ�ȡsum����ַ�
     * @param dotNum    ʱ�̵���
     * @return
     */
    public static String getSumMDotTimeString(int dotNum) {
        StringBuffer sb = new StringBuffer();
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("SUM(VAL" + iStr + jStr + ")/1000,");
                k++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }
    /**
     * �õ�����ʱ�̵��ֶ�ȡsum�ٳ�ϵ�����ַ�
     * @param dotNum    ʱ�̵���
     * @return
     */
    public static String getSumRateDotTimeString(int dotNum,double rate) {
        StringBuffer sb = new StringBuffer();
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("SUM(VAL" + iStr + jStr + ")*"+rate+",");
                k++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }
    /**
     * �õ�����ʱ�̵��ֶ�ȡsum����ַ�
     * @param dotNum    ʱ�̵���
     * @return
     */
    public static String getAVGDotTimeString(int dotNum) {
        StringBuffer sb = new StringBuffer();
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("AVG(VAL" + iStr + jStr + "),");
                k++;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * �õ�����ʼ�㿪ʼ�����ڶ�����ʼ��ǰһ��ʱ�̵�ĸ���ʱ�̵��ֶ����
     * @param dotNum    ʱ�̵���Ŀ
     * @param fcstimeno  ��ʼ��λ��
     * @return
     */
    public static String getDotTimeString(int dotNum, int fcstimeno) {
        StringBuffer sb = new StringBuffer();
        String iStr = "";
        String jStr = "";
        int k = 0;
        int td = 288 * 5 / dotNum;
        //�õ�a.*
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                k += (288 / dotNum);
                if (k <= fcstimeno) {
                    continue;
                }
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("a.VAL" + iStr + jStr + ",");
            }
        }
        //�õ�b.*
        k = 0;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                iStr = "0" + i;
            } else {
                iStr = Integer.toString(i);
            }
            for (int j = 0; j < 60; j += td) {
                k += (288 / dotNum);
                if (k > fcstimeno) {
                    continue;
                }
                if (j < 10) {
                    jStr = "0" + j;
                } else {
                    jStr = Integer.toString(j);
                }
                sb.append("b.VAL" + iStr + jStr + ",");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * ��ȡָ����ݸ��յ��ַ�����
     * @create Feb 23, 2009
     * @author bluton
     * @return
     * @version 1.0
     * @logs Feb 23, 2009
     */
    public static String[] getDayStringArr(String prifex, int year) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int[] daysOfMonth = new int[12];
        cal.set(java.util.Calendar.YEAR, year);      
        int daysOfYear = cal.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);   
        for(int i = 0; i < 12; i++) {
            cal.set(java.util.Calendar.MONTH, i);
            int n = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);   
            daysOfMonth[i] = n;
        }
        String [] daysString = new String[daysOfYear];
        int index = 0;
        for(int i = 1; i <= daysOfMonth.length; i++) {
            String month = "";
            if(i < 10) {
                month = "0" + i;
            } else {
                month = i + "";
            }
            for(int j = 1; j <= daysOfMonth[i - 1]; j++) {
                String day = "";
                if(j < 10) {
                    day = "0" + j;
                } else {
                    day = j + "";
                }
                daysString[index] = prifex + month + day;
                index ++;
            }
        }
        return daysString;
    }
    
//    public static void main(String[] args) {
//      System.out.println(TimeSqlGenUtil.getDotTimeString(288));
//      System.out.println(TimeSqlGenUtil.getDotTimeArray(96));
//      System.out.println(TimeSqlGenUtil.getDotTimeString(48));
//      System.out.println(TimeSqlGenUtil.getDotTimeString(24));
//      System.out.println("\n\n");
//      System.out.println(TimeSqlGenUtil.getSumDotTimeString(288));
//      System.out.println(TimeSqlGenUtil.getSumDotTimeString(96));
//      System.out.println(TimeSqlGenUtil.getSumDotTimeString(48));
//      System.out.println(TimeSqlGenUtil.getSumDotTimeString(24));
//    }
}
