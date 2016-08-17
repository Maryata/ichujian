package com.sys.ekey.rebate.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/7/25.
 */
public interface RebateService {

    /**
     * 我的财富
     *
     * @param uid 用户id
     * @return
     */
    List<Map<String, Object>> myWealth(String uid);

    /**
     * 财富明细
     *
     * @param uid 用户id
     * @return
     */
    List<Map<String, Object>> wealthDetail(String uid);

    /**
     * 财富明细（按月统计）
     *
     * @param uid  用户id
     * @param type 查询的时间类型：1.当前月 2.所有月
     * @return
     */
    List<Map<String, Object>> wealthDetailByMonth(String uid, String type);

    /**
     * 我的人脉
     *
     * @param uid 用户id
     * @return
     */
    List<Map<String, Object>> myReference(String uid);

    /**
     * 龙虎榜
     *
     * @param type 查询的时间类型：1.当前月 2.所有月
     * @return
     */
    List<Map<String, Object>> rankList(String type);

    /**
     * 提现明细
     *
     * @param uid 用户id
     * @return
     */
    List<Map<String, Object>> withdrawDetail(String uid);

    /**
     * 申请提现
     *
     * @param uid          用户id
     * @param amount       提现金额
     * @param pay_type     充值账号的类型 1.微信 2.支付宝
     * @param pay_account  充值账号
     * @param withdraw_pwd 提现密码
     */
    String withdraw(String uid, String amount, String pay_type, String pay_account, String withdraw_pwd);

    /**
     * 校验身份证
     *
     * @param uid    用户id
     * @param id_num 身份证号码
     * @return
     */
    String authId(String uid, String id_num);

    /**
     * 保存商户信息
     *
     * @param param       商户基本信息
     * @param license_img 营业执照照片
     * @param shop_img    店铺照片
     * @return
     */
    String saveBusinessInfo(Map<String, Object> param, String[] license_img, String[] shop_img);

    /**
     * 获取商户信息
     *
     * @param uid 用户id
     * @return
     */
    List<Map<String, Object>> businessInfo(String uid);

    /**
     * 申请售后屏保
     *
     * @param param 用户手机信息
     * @param img   图片
     * @return
     */
    String activeInsurance(Map<String, Object> param, String[] img);

    /**
     * 查询是否已激活售后屏保
     *
     *
     * @param reqMap
     * @param uid  用户id
     * @param code 激活码
     * @return
     */
    Map<String, Object> isActed(Map<String, Object> reqMap, String uid, String code);

    /**
     * 申请维修
     *
     * @param uid  用户id
     * @param code 激活码
     * @param img  图片
     * @return
     */
    String applyForRepair(String uid, String code, String[] img);

    /**
     * 申请明细
     *
     * @param uid  用户id
     * @param code 激活码
     * @return
     */
    Map<String, Object> applyDetail(String uid, String code);
}
