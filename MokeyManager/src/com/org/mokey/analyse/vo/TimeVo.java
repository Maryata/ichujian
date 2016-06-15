package com.org.mokey.analyse.vo;

/**
 * Created by vpc on 2016/1/26.
 */
public class TimeVo {
    private String timeString;
    private Long activeNum;
    private Long activationNum;
    private Long downloadNum;
    private Long registerNum;
    private Long stayingNum;

    public String getTimeString () {
        return timeString;
    }

    public void setTimeString (String timeString) {
        this.timeString = timeString;
    }

    public Long getActiveNum () {
        return activeNum;
    }

    public void setActiveNum (Long activeNum) {
        this.activeNum = activeNum;
    }

    public Long getActivationNum () {
        return activationNum;
    }

    public void setActivationNum (Long activationNum) {
        this.activationNum = activationNum;
    }

    public Long getDownloadNum () {
        return downloadNum;
    }

    public void setDownloadNum (Long downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Long getRegisterNum () {
        return registerNum;
    }

    public void setRegisterNum (Long registerNum) {
        this.registerNum = registerNum;
    }

    public Long getStayingNum () {
        return stayingNum;
    }

    public void setStayingNum (Long stayingNum) {
        this.stayingNum = stayingNum;
    }
}
