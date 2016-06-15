package com.org.mokey.common.util.number;

/**
 * @function ������
 * @author <a href="mailto:zylgang@sina.com">liugang</a>
 * @create 2011-12-8
 * @version 1.0
 * @logs 2011-12-8
 */
public class BubbleSort {    
    /**
     * int ��������
     * 
     * @param int []
     *            data ��������
     * @param int
     *            dir ������ 0����С������ 1���ɴ�С
     */
    public void sort(int[] data, int dir) {
        int temp;
        for(int i = 0; i < data.length; i++) {
            for(int j = data.length - 1; j > i; j--) {
                if(dir == 0) {
                    if(data[j] < data[j - 1]) {
                        swap(data, j, j - 1);
                    }
                } else {
                    if(data[j] > data[j - 1]) {
                        swap(data, j, j - 1);
                    }
                }
            }
        }
    }

    /**
     * int ��������ʱ����λ��
     * 
     * @param int []
     *            data ��������
     * @param int
     *            i ����λ��
     * @param int
     *            j ����λ��
     */
    public void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * double ��������
     * 
     * @param double []
     *            data ��������
     * @param int
     *            dir ������ 0����С������ 1���ɴ�С
     */
    public void sort(double[] data, int dir) {
        // double temp;
        for(int i = 0; i < data.length; i++) {
            for(int j = data.length - 1; j > i; j--) {
                if(dir == 0) {
                    if(data[j] < data[j - 1]) {
                        swap(data, j, j - 1);
                    }
                } else {
                    if(data[j] > data[j - 1]) {
                        swap(data, j, j - 1);
                    }
                }
            }
        }
    }

    /**
     * double ��������ʱ����λ��
     * 
     * @param double []
     *            data ��������
     * @param int
     *            i ����λ��
     * @param int
     *            j ����λ��
     */
    public void swap(double[] data, int i, int j) {
        double temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * String[][] ��������
     * 
     * @param double []
     *            data ��������
     * @param int
     *            dir ������ 0����С������ 1���ɴ�С
     */
    public void sort(String[][] data, int dir, int pos) {
        // double temp;
        for(int i = 0; i < data.length; i++) {
            for(int j = data.length - 1; j > i; j--) {
                String j1Ele = data[j][pos];
                String j2Ele = data[j - 1][pos];
                if(j1Ele == null) {
                    j1Ele = "0";
                }
                if(j2Ele == null) {
                    j2Ele = "0";
                }
                if(dir == 0) {
                    if(Double.parseDouble(j1Ele) < Double.parseDouble(j1Ele)) {
                        swap(data, j, j - 1);
                    }
                } else {
                    if(Double.parseDouble(j1Ele) > Double.parseDouble(j2Ele)) {
                        swap(data, j, j - 1);
                    }
                }
            }
        }
    }

    /**
     * String ��������ʱ����λ��
     * 
     * @param String[] []
     *            data ��������
     * @param int
     *            i ����λ��
     * @param int
     *            j ����λ��
     */
    public void swap(String[][] data, int i, int j) {
        String[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * String[][] ��������
     * 
     * @param double []
     *            data ��������
     * @param int
     *            dir ������ 0����С������ 1���ɴ�С
     */
    public void sort(double[][] data, int dir, int pos) {
        // double temp;
        for(int i = 0; i < data.length; i++) {
            for(int j = data.length - 1; j > i; j--) {
                double j1Ele = data[j][pos];
                double j2Ele = data[j - 1][pos];
                // if(j1Ele==null)j1Ele="0";
                // if(j2Ele==null)j2Ele="0";
                if(dir == 0) {
                    if(j1Ele < j2Ele) {
                        swap(data, j, j - 1);
                    }
                } else {
                    if(j1Ele > j2Ele) {
                        swap(data, j, j - 1);
                    }
                }
            }
        }
    }

    /**
     * double ��������ʱ����λ��
     * 
     * @param double []
     *            data ��������
     * @param int
     *            i ����λ��
     * @param int
     *            j ����λ��
     */
    public void swap(double[][] data, int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
