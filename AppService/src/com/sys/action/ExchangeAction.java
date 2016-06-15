package com.sys.action;

import com.sys.commons.AbstractAction;
import com.sys.service.ExchangeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 */
@Component
public class ExchangeAction extends AbstractAction {
    @Autowired
    private ExchangeService exchangeService;

//    public String execute() {
//        String phone = getParameter("phone");
//        String express = getParameter("express");
//        String address = getParameter("address");
//        String consignee = getParameter("consignee");
//        String telNum = getParameter("telNum");
//        String imei = getParameter("imei");
//        // 产品代码
//        String pCode = getParameter("pCode");
//        // 物流公司代码
//        String expressCode = getParameter("expressCode");
//
//        int count = exchangeService.save(phone, express, address, consignee, telNum, imei, pCode, expressCode);
//
//        Map<String, String> result = new HashMap<String, String>();
//
//        result.put("success", String.valueOf(count));
//
//        try {
//            writeToResponse(JSONObject.fromObject(result).toString());
//
//            OrderVo orderVo = new OrderVo();
//            orderVo.setSerialId("E" + System.currentTimeMillis());
//            orderVo.setAddress(address);
//            orderVo.setConsignee(consignee);
//            try {
//                orderVo.setCreateDate(ApDateTime.getDateTime("yyyy-MM-dd hh:mm:ss",System.currentTimeMillis()));
//            } catch (Exception e) {
//                log.error("时间获取错误",e);
//            }
//            orderVo.setExpressCode(expressCode);
//            orderVo.setpCode(pCode);
//            orderVo.setTelNum(telNum);
//            orderVo.setPhone(phone);
//            ErpInfo.addOrder(orderVo);
//        } catch (IOException e) {
//            log.error("写出结果错误", e);
//        }
//
//        return NONE;
//    }

    public String record() {
        Map<String, Object> result;
        String imei = getParameter("imei");

        result = exchangeService.record(imei);

        try {
            writeToResponse(JSONObject.fromObject(result).toString());
        } catch (IOException e) {
            log.error("写出结果错误", e);
        }

        return NONE;
    }
}
