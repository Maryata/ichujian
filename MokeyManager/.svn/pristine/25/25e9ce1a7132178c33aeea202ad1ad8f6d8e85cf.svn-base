package com.org.mokey.analyse.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/6/7.
 */
public interface FreeCallDao {
    List<Map<String, Object>> callingBill_ek(String sDate, String eDate);

    /**
     * 话单详情查询
     *
     * @param sDate
     * @param eDate
     * @param phone
     * @return
     */
    List<Map<String, Object>> selectCallDetailList(String sDate, String eDate, String phone,String fyid);
    List<Map<String,Object>> callingBill(String sDate, String eDate);
    List<Map<String,Object>> callingBill_fy(String sDate, String eDate);
}
