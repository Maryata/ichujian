package com.org.mokey.analyse.service.impl;

import com.org.mokey.analyse.dao.FreeCallDao;
import com.org.mokey.analyse.entiy.CallingBill;
import com.org.mokey.analyse.service.FreeCallService;
import com.org.mokey.util.StrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/6/7.
 */
@Service
public class FreeCallServiceImpl implements FreeCallService {

    private static final Log LOGGER = LogFactory.getLog(FreeCallServiceImpl.class);

    @Autowired
    private FreeCallDao freeCallDao;

    @Override
    public List<CallingBill> getCallingBill(String sDate, String eDate) {
        List<CallingBill> dataList = new ArrayList<>();
//        List<Map<String, Object>> callingBill = freeCallDao.callingBill(sDate, eDate);
        List<Map<String, Object>> callingBill_ek = freeCallDao.callingBill_ek(sDate, eDate);
        List<Map<String, Object>> callingBill_fy = freeCallDao.callingBill_fy(sDate, eDate);
        Map<String, Map<String, Object>> tempMap = new LinkedHashMap<>();
        if (StrUtils.isNotEmpty(callingBill_fy)) {
            for (Map<String, Object> map : callingBill_fy) {
                String c_date = StrUtils.emptyOrString(map.get("C_DATE"));
                tempMap.put(c_date, map);
            }
        }
        if (StrUtils.isNotEmpty(callingBill_ek)) {
            for (Map<String, Object> map : callingBill_ek) {
                String c_date = StrUtils.emptyOrString(map.get("C_DATE"));
                Integer total_fy = StrUtils.zeroOrInt(map.get("TOTAL_FY"));
                Map<String, Object> map_ek = tempMap.get(c_date);
                map_ek.put("TOTAL_EK", total_fy);
            }
        }
        if (StrUtils.isNotEmpty(tempMap)) {
            Long sum_ek = 0l;
            Long sum_fy = 0l;
            for (Map.Entry<String, Map<String, Object>> entry : tempMap.entrySet()) {
                String date = entry.getKey();
                Map<String, Object> map = entry.getValue();
                CallingBill bill = new CallingBill();
                bill.setDate(date);
                Long total_ek = StrUtils.zeroOrLong(map.get("TOTAL_EK"));
                Long total_fy = StrUtils.zeroOrLong(map.get("TOTAL_FY"));
                sum_ek += total_ek;
                sum_fy += total_fy;
                bill.setEkCallingTime(total_ek);
                bill.setFyCallingTime(total_fy);
                dataList.add(bill);
            }
            CallingBill sum_bill = new CallingBill();
            sum_bill.setDate("合 计");
            sum_bill.setEkCallingTime(sum_ek);
            sum_bill.setFyCallingTime(sum_fy);
            dataList.add(sum_bill);
        }
        return dataList;
    }

    /**
     * 免费通话 话单详情查询
     * @param sDate
     * @param eDate
     * @param phone
     * @return
     */
    @Override
    public List<Map<String,Object>> selectCallDetailList(String sDate, String eDate, String phone,String fyid) {
        List<Map<String,Object>> selectCallDetailList  = freeCallDao.selectCallDetailList(sDate, eDate, phone, fyid);
        return selectCallDetailList;

    }
}
