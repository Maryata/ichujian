package com.sys.service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 */
public interface ExchangeService {
    /**
     * 换膜成功标识
     *
     * @param phone        手机号
     * @param express      快递公司
     * @param address      地址
     * @param consignee    收件人
     * @param telNum       联系号码
     * @param imei         设备号
     * @param out_trade_no 订单号
     * @param uid
     *@param payType @return 是否记录成功
     */
    int save(String phone,
             String express,
             String address,
             String consignee,
             String telNum, String imei, String pCode, String expressCode, String out_trade_no, String uid, String payType);

    /**
     * 换膜记录
     *
     * @param imei 设备标识符
     * @return 换膜记录结果
     */
    Map<String, Object> record(String imei);

    /**
     * 更新换膜记录,标识为成功--支付宝
     *
     * @param out_trade_no 订单号
     * @return 是否成功
     */
    int update(String out_trade_no);

    /**
     * 更新换膜记录,标识为成功--微信
     *
     * @param map 微信回调信息
     * @return 是否成功
     */
    Map<String, String> update(Map<String,String> map);
}
