package com.sys.ekey.freecall.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/4/12.
 */
public interface FeiyuCloudService {

    /**
     * 新增飞语云账号相关信息
     *
     * @param regid     e键用户的regid
     * @param phonenum  手机号
     * @param resultMap 返回参数
     * @param fc
     */
    int addAccount(String regid, String phonenum, Map<String, Object> resultMap, String fc);

    /**
     * 给用户增加通话时间
     *
     * @param fyAccountId 飞语账号
     * @param supcode
     */
    void grantUserCallTime(Object fyAccountId, String supcode);

    /**
     * 新增飞语云账号出错的日志记录
     *
     * @param regid     e键用户的regid
     * @param resultMap 返回参数
     */
    void addAccountLogError(String regid, Map<String, Object> resultMap, String type);

    /**
     * 修改绑定手机
     *
     * @param regId       e键用户的regid
     * @param fyAccountId 飞语用户id
     * @param phonenum    手机号
     */
    void modifyPhone(String regId, String fyAccountId, String phonenum);

    /**
     * 是否启用帐户
     *
     * @param fyAccountId 飞语帐号
     * @return
     */
    boolean isEnableAccount(String fyAccountId);

    /**
     * 获取帐号的最大可通话分钟数,同时记录授权信息
     *
     * @param fyAccountId
     * @param parameterMap
     * @return
     */
    int getMaxCallMinuteByFyAccountId(String fyAccountId, Map<String, Object> parameterMap);

    /**
     * 插入话单记录，并将剩余时间进行更新
     *
     * @param map
     */
    void insertCallHis(Map<String, Object> map);

    /**
     * 套餐列表
     *
     * @return
     */
    List<Map<String, Object>> combo();

    /**
     * 查询用户信息
     *
     * @param regId 注册id
     * @return
     */
    List<Map<String, Object>> userInfo(String regId);

    /**
     * 查询没有飞语云账号的老用户
     *
     * @return
     */
    List<Map<String, Object>> getExistsUserIsNotFyAcc();

    /**
     * 根据商户订单号查询订单
     *
     * @param out_trade_no 商户订单号
     * @return
     */
    List<Map<String, Object>> getOrderByTradeNo(String out_trade_no);

    /**
     * 查询套餐详情
     *
     * @param id 套餐id
     * @return
     */
    List<Map<String, Object>> getComboById(String id);

    /**
     * 生成订单
     *
     * @param out_trade_no 商户订单号
     * @param fyAccountId  飞语账号
     * @param total_fee    订单总金额
     * @param c_time       订单总时间
     * @param payType      支付类型：1.微信，2.支付宝
     */
    void insertOrder(String out_trade_no, String fyAccountId, String total_fee, String c_time, String payType, String c_type);

    /**
     * 更新订单支付状态
     *
     * @param out_trade_no 商户订单号
     */
    void updatePayStatus(String out_trade_no);

    /**
     * 记录支付宝回调日志
     *
     * @param params 回调参数
     */
    void logAlipayNotify(Map<String, String> params);

    /**
     * 查询订单状态
     * 更新订单信息
     * 记录微信支付日志
     *
     * @param parameterMap
     * @return
     */
    Map<String, String> updatePayStatus(Map<String, String> parameterMap, String payType);

    /**
     * 记录微信通知
     *
     * @param parameterMap
     */
    void logWeChartPay(Map<String, String> parameterMap);

    /**
     * 通话记录
     *
     * @param fyAccountId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> callHistory(String fyAccountId, int pageNumber, int pageSize);

    List<Map<String, Object>> feiyuAccHasntTime();

    /**
     * 获取手机号归属地
     *
     * @param phone 要查询的手机号
     * @return
     */
    String phoneArea(String phone);

    /**
     * 记录免费通话行为
     *
     * @param uid     用户id
     * @param imei    用户imei
     * @param fyAccId 飞语账号id
     * @param flag    本次行为的标记（时间戳）
     * @param sup     供应商
     * @param callee  被叫号码
     */
    void callingRec(String uid, String imei, String fyAccId, String flag, String sup, String callee);

    /**
     * 更新通话记录
     *
     * @param etime  结束时间
     * @param elapse 通话时长
     * @param flag   行为标记
     */
    void updateCallingRec(String etime, String elapse, String flag);

    /**
     * 获取免费通话App 套餐
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> getAppComboById(String id);

    /**
     * 免费通话错误日志
     *
     * @param caller   主叫手机号
     * @param callee   被叫手机号
     * @param code     错误码
     * @param errorMsg 错误信息
     */
    void recodeErrorCode(String caller, String callee, String code, String errorMsg);
}
