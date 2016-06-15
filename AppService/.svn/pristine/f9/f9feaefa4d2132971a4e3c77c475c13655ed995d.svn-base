package com.sys.ekey.index.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/3/1.
 */
public interface IndexService {

    /**
     * 用户首页APP布局接口
     *
     * @param uid     用户id
     * @param supCode 用户激活码
     * @return
     */
    List<Map<String, Object>> indexAppLayout(String uid, String supCode);

    /**
     * 首页广告
     *
     * @return
     */
    List<Map<String, Object>> advertInfo();

    /**
     * 更多app
     *
     * @param uid     用户ID
     * @param supCode 供应商代码
     * @return
     */
    List<Map<String, Object>> more(String uid, String supCode);

    /**
     * 用户点击APP历史纪录
     *
     * @param uid  用户id
     * @param imei 用户imei
     * @return
     */
    List<Map<String, Object>> appUsingHis(String uid, String imei);

    /**
     * 用户定制首页
     *
     * @param uid 用户ID
     * @param aid 应用ID
     * @param op  操作 0：减 1：加
     * @return 是否成功
     */
    boolean customHis(String uid, String aid, String op);

    /**
     * 记录用户点击APP的行为
     *
     * @param aid  应用id
     * @param uid  用户id
     * @param imei 用户imei
     */
    void appUsingRecord(String aid, String uid, String imei);

    /**
     * 查询所有按键的点击次数
     *
     * @param imei 用户IMEI
     * @param date 日期
     * @return
     */
    Map<String, Integer> countOfEachKeys(String imei, String date);

    /**
     * 键位app
     * @param key 取值 1、2、3、4
     * @param supCode 供应商代码
     * @return
     */
    List<Map<String,Object>> appByKey(String key, String supCode);

    /**
     * 键微信息
     * @param key 键位 取值 1/2/3/4
     * @param supCode 供应商代码
     * @return
     */
    List<Map<String,Object>> keyInfo(String key, String supCode);

    /**
     * 查询现有的所有首页应用总数，如果总数于参数不相等则返回所有应用，否则返回0
     * @param cnt
     * @return
     */
    List<Map<String,Object>> getAllAppOrNot(String cnt);
}
