package com.org.mokey.common.util.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;

//import org.bluton.common.DateUtil;



/**
 * �ۺϷ���-->�޹����ʹ���-->������. 
 * @author hhw
 * @create 2011-12-6 
 * @version 1.00
 */
public class StatUtil {
    /**
     * 
     * ����Java�ļ����Ͳ��ܹ���ȷ�ĶԸ�����������㣬����������ṩ��
     * 
     * ȷ�ĸ��������㣬�����Ӽ��˳���������롣
     * 
     */
    public static final int ROUND_PERCENT=2;//�ٷֱ�Сλ��

    public static final int ROUND_OPERATE=2;//ҵ�����㱣��λ��


    public static final int POWER_VAL=2;//ƽ������
    // Ĭ�ϳ����㾫��
    private static final int DEF_DIV_SCALE = 10;
    // ����಻��ʵ��
    private StatUtil() {

    }
    /**
     * 
     * �ṩ��ȷ�ļӷ����㡣
     * 
     * @param v1
     *            ������
     * 
     * @param v2
     *            ����
     * 
     * @return ��������ĺ�
     * 
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();

    }
    /**
     * 
     * �ṩ��ȷ�ļ������㡣
     * 
     * @param v1
     *            ������
     * 
     * @param v2
     *            ����
     * 
     * @return ��������Ĳ�
     * 
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 
     * �ṩ��ȷ�ĳ˷����㡣
     * 
     * @param v1
     *            ������
     * 
     * @param v2
     *            ����
     * 
     * @return ��������Ļ�
     * 
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    /**
     * 
     * �ṩ����ԣ���ȷ�ĳ����㣬�����������ʱ����ȷ�� С����Ժ�10λ���Ժ�������������롣
     * 
     * @param v1
     *            ������
     * @param v2
     *            ����
     * @return �����������
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }
    /**
     * �ṩ����ԣ���ȷ�ĳ����㡣�����������ʱ����scale����ָ �����ȣ��Ժ�������������롣
     * 
     * @param v1
     *            ������
     * @param v2
     *            ����
     * @param scale
     *            ��ʾ��ʾ��Ҫ��ȷ��С����Ժ�λ��
     * @return �����������
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
        }
        if(v2==0){
            return 0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * �ṩ��ȷ��С��λ�������봦�?
     * 
     * @param v
     *            ��Ҫ�������������
     * @param scale
     *            С��������λ
     * @return ���������Ľ��
     */
    public static double round(double v, int scale) {
        if(!Double.isNaN(v)&&!Double.isInfinite(v)){
            if (scale < 0) {
                throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
            }
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }else{
            return 0;
        }    
    }


    /**
     * �ٷֱ���������
     * @param double a 
     * 
     */
    public static double roundPercent(double a) {
        double de=0;      
        if(!Double.isInfinite(a)&&!Double.isInfinite(a)){
            de=StatUtil.round(a,StatUtil.ROUND_PERCENT);
        }
        return de;
    }
    /**
     * ҵ��������������
     * @param double a 
     * 
     */
    public static double roundOperate(double a) {
        double de=0;      
        if(!Double.isInfinite(a)&&!Double.isInfinite(a)){
            de=StatUtil.round(a,StatUtil.ROUND_OPERATE);
        }
        return de;
    }

    /**
     * �ٷֱ�����
     * @param double d1
     * @param double d2
     */
    public static double countPercent(double d1,double d2) {
        double   per=0;
        per=StatUtil.countPercent(d1,d2,StatUtil.ROUND_PERCENT);
        return per;
    }
    /**
     * �ٷֱ�����
     * @param double d1
     * @param double d2
     */
    public static double countPercent(double d1,double d2,int precision) {

        double   per=0;
        if(d2!=0&&!Double.isInfinite(d2)&&!Double.isNaN(d2)&&!Double.isNaN(d1)&&!Double.isInfinite(d2)){
            per=StatUtil.div(d1,d2)*100;
            per=StatUtil.round(per,precision);
        }
        return per;
    }
    /**
     * �ٷֱ�����
     * @param double[] srcArr 
     */
    public static double[] countPercent(double[] srcArr) {
        double[] cArr=null;
        double   total=0;
        if(srcArr!=null){
            cArr=new double[srcArr.length];
            total=StatUtil.sum(srcArr);
            if(total!=0&&!Double.isInfinite(total)&&!Double.isNaN(total)){
                for(int i=0;i<cArr.length;i++){
                    if(!Double.isNaN(srcArr[i])){
                        cArr[i]=StatUtil.countPercent(srcArr[i],total);
                    }
                }  
            }
        }
        return cArr;
    }

    /**
     * ����ֵ��ƽ��
     * @param double a ����
     * @param double b ����
     * 
     */
    public static double power(double a,double b) {
        double de=0;
        if(a>0&&!Double.isInfinite(a)&&!Double.isInfinite(b)
                &&!Double.isNaN(a)&&!Double.isNaN(b)){
            de=Math.pow(a,b);
        }
        return de;
    }
    /**
     * ����ֵ��ƽ�����ֵ
     * @param  double a ƽ��������
     */
    public static double sqrt(double a) {
        double de=0;
        // TODO Auto-generated method stub
        if(a>0&&!Double.isNaN(a)&&!Double.isInfinite(a)){
            de=Math.sqrt(a);
        }
        return de;
    }

    /**
     * ����������ƽ��ֵ
     * @param  Object [] srcObj �������
     */
    public static double avg(double [] srcObj) {
        double total=0;
        double avg=0;
        double length=0;
        // TODO Auto-generated method stub
        if(srcObj!=null){
            total=StatUtil.sum(srcObj);
            length=srcObj.length;
            avg=StatUtil.div(total,length);
        }
        return avg;
    }
    /**
     * �����������
     * @param  Object [] srcObj �������
     */
    public static double sum(Object [] srcObj) {
        double total=0;
        // TODO Auto-generated method stub
        if(srcObj!=null){
            for(int i=0;i<srcObj.length;i++){
                Object iobj=srcObj[i];
                if(iobj!=null){
                    if(!iobj.equals("")){
                        total+=Double.valueOf(iobj.toString()).doubleValue();
                    }
                }
            }
        }
        return total;
    }

    /**
     * double�����������
     * @param  Object [] srcObj �������
     */
    public static double sum(double [] srcObj) {
        double total=0;
        // TODO Auto-generated method stub
        if(srcObj!=null){
            for(int i=0;i<srcObj.length;i++){
                double iobj=srcObj[i];
                if(!Double.isNaN(iobj)&&!Double.isInfinite(iobj)){
                    total=StatUtil.add(total,iobj);
                }
            }
        }
        return total;
    }

    /**
     * �����������
     * @param  Object [][] srcObj �������
     * @param  int column  ��͵���
     */
    public static double sum(Object [][] srcObj,int column) {
        double total=0;
        // TODO Auto-generated method stub
        if(srcObj!=null){
            for(int i=0;i<srcObj.length;i++){
                Object iobj=srcObj[i][column];
                if(iobj!=null){
                    if(!iobj.equals("")){
                        total+=Double.valueOf(iobj.toString()).doubleValue();
                    }
                }
            }
        }
        return total;
    }

    /**
     * DOUBLE���ָ�ʽ��
     * @param double d 
     * */
    public static String doubleToStr(double d) {
        String str = "";
        try{
            DecimalFormat format=new DecimalFormat("###0.00");
            str = format.format(d);
        }catch(Exception e){e.printStackTrace();}
        return str;
    }
    /**
     * DOUBLE���ָ�ʽ��
     * @param double d
     * @param int    scale    
     * */
    public static String doubleToStr(double d,int scale) {
        String str = "";
        try{
            BigDecimal big=new BigDecimal(d);
            str=big.setScale(scale,1).toString();
        }catch(Exception e){e.printStackTrace();}
        return str;
    }

    /**
     * ��������ļ�ֵ
     * @param double []  arr1;
     * @param int       direct 0:max value 1:min value;
     * 
     */
    public static double takeExtremum(double[] arr1,int direct) {
        // TODO Auto-generated method stub
        double maxValue=0;
        try{
            double tempVal=0;
            if(arr1!=null){
                for(int i=0;i<arr1.length;i++){
                    if(i==0){
                        tempVal=arr1[i];
                    }
                    if(tempVal<arr1[i]&&direct==0){
                        tempVal=arr1[i];
                    }
                    if(tempVal>arr1[i]&&direct==1){
                        tempVal=arr1[i];
                    }
                }
            }
            maxValue=tempVal;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return maxValue;
    } 
    /**
     * 
     *������Ԫ�����
     * 
     * @param v1
     *            ������
     * 
     * @param v2
     *            ����
     * 
     * @return �����������Ļ�
     * 
     */
    public static double[] mul(double[] v1, double[] v2) {
        double[] retArr=null;
        if(v1!=null&&v2!=null){
            if(v1.length==v2.length){
                retArr=new double[v1.length];
                for(int i=0;i<retArr.length;i++){
                    retArr[i]=StatUtil.mul(v1[i],v2[i]);
                }
            }
        }
        return retArr;
    }
    /**
     * ����ֵ��ƽ�����ֵ
     */
    public void  test() {


        double de=0.87965;
        //Object [] obj1=new Object[]{"1","2","3","4"};
        //Object [] obj2=new Object[]{"5","6","7","8"};

    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StatUtil   react=new StatUtil();
        react.test();
    } 

}
