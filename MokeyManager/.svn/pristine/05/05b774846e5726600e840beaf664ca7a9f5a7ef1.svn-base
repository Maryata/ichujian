package com.org.mokey.analyse.entiy;

/**
 * Created by Maryn on 2016/6/7.
 */
public class CallingBill {

    private String date;

    private Long ekCallingTime;

    private Long fyCallingTime;

    private Long difference;

    private String color;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getEkCallingTime() {
        return ekCallingTime;
    }

    public void setEkCallingTime(Long ekCallingTime) {
        this.ekCallingTime = ekCallingTime;
    }

    public Long getFyCallingTime() {
        return fyCallingTime;
    }

    public void setFyCallingTime(Long fyCallingTime) {
        this.fyCallingTime = fyCallingTime;
    }

    public Long getDifference() {
        return fyCallingTime - ekCallingTime;
    }

    public String getColor() {
        if (fyCallingTime - ekCallingTime < 0) {
            this.color = "green";
        } else if (fyCallingTime - ekCallingTime > 0) {
            this.color = "red";
        }
        return color;
    }

}
