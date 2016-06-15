package com.org.mokey.analyse.vo;

import java.text.DecimalFormat;

/**
 * e键-键位数据
 *
 * Created by Maryn on 2016/4/5.
 */
public class EkeyKeyBean {

    private long usingUser = 0L;// 使用人数

    private long usingTimes = 0L;// 使用次数

    private double timesPerUser;// 平均数（次/人）

    public long getUsingUser() {
        return usingUser;
    }

    public void setUsingUser(long usingUser) {
        this.usingUser = usingUser;
    }

    public long getUsingTimes() {
        return usingTimes;
    }

    public void setUsingTimes(long usingTimes) {
        this.usingTimes = usingTimes;
    }

    public double getTimesPerUser() {
        DecimalFormat df = new DecimalFormat("#.00");
//        return usingTimes==0?0:(usingUser==0?0:(Double.parseDouble(df.format((double)usingTimes/usingUser))));
        return Double.parseDouble(df.format((double)usingTimes/usingUser));
    }

}
