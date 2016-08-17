package com.sys.service;

import java.util.Date;
import java.util.List;

public interface UseService {

    //用户使用的APP
    void UseApp(String imei, String type, String key, String clicktype, String package_name, String app_name, Date actiondate, String choosetype, String uid);

    //用户使用按键的情况
    void Usekey(String imei, String type, String key, Date actiondate);

    void Usekey(String imei, String type, String key, Date actiondate, String clicktype, String choosetype);

    //公共按键使用
    void UseCommon(String imei, String type, Date actiondate);

    //获取供应商URL
    List getSupplierUrl(String code);

    //帮助信息
    String getHelpInfo(String num, String type);

    /**
     * 获取供应商的用户注册协议或免费通话问题帮助
     *
     * @param sup  供应商代码
     * @param type 查询内容：1.用户注册协议，2.免费通话问题帮助
     * @return
     */
    String ekHelpInfo(String sup, String type);

    /**
     * 查询用户使用1/2/3/4号键的次数
     * @param uid 用户id
     * @param key 按键号码
     * @return
     */
    Integer countOfUsingKey(String uid, String key);
}
