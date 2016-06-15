package com.org.mokey.analyse.vo;

import java.util.List;

/**
 * Created by vpc on 2016/1/19.
 */
public class SupVo {
    private String supCode;
    private String supName;

    private List<TimeVo> l_timeVo;

    public String getSupCode () {
        return supCode;
    }

    public void setSupCode (String supCode) {
        this.supCode = supCode;
    }

    public String getSupName () {
        return supName;
    }

    public void setSupName (String supName) {
        this.supName = supName;
    }

    public List<TimeVo> getL_timeVo() {
        return l_timeVo;
    }

    public void setL_timeVo(List<TimeVo> l_timeVo) {
        this.l_timeVo = l_timeVo;
    }
}
