package com.org.mokey.analyse.service;

import com.org.mokey.analyse.entiy.CallingBill;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/6/7.
 */
public interface FreeCallService {

    List<CallingBill> getCallingBill(String sDate, String eDate);

    /**
     * 通话详情查询
     *
     * @param sDate
     * @param eDate
     * @param phone
     * @return
     */
    List<Map<String,Object>> selectCallDetailList(String sDate, String eDate, String phone,String fyid);
}
