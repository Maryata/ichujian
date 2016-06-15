package com.sys.ekey.activity.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/3/3.
 */
public interface ActivityService {

    /**
     * 查询活动头条
     *
     * @return
     */
    List<Map<String, Object>> headLine();

    /**
     * 查询首页分类
     *
     * @return
     */
    List<Map<String, Object>> indexCategory();

    /**
     * 查询指定分类详情
     *
     * @param cid       合集id
     * @param pageIndex 页码
     * @param pSize     每页显示数量
     * @param isIndex   是否首页的分类
     * @return
     */
    List<Map<String, Object>> categoryDetail(String cid, String pageIndex, String pSize, String isIndex);

    /**
     * 活动用户行为记录
     *
     * @param uid  用户ID
     * @param aid  活动活专题ID
     * @param type 00：点赞 01：收藏 02：浏览活动 03：打开专题 04：参与活动 05 ：取消收藏 06：取消点赞
     * @param imei 设备 imei
     * @return 是否记录成功
     */
    boolean userAction(String uid, String aid, String type, String imei);

    /**
     * 活动相关列表（收藏、参与记录）
     *
     * @param uid       用户id
     * @param pageIndex 页码
     * @param pSize     每页显示数量
     * @param tableName 相关表名
     * @return
     */
    List<Map<String, Object>> listAboutAct(String uid, String pageIndex, String pSize, String tableName);

    /**
     * 活动详情
     *
     * @param aid 活动ID
     * @param uid 用户ID
     *            @param  imei
     * @return 活动详情
     */
    Map<String, Object> activityDetail(String aid,String uid,String imei);

    /**
     * 用户删除其参与的活动
     *
     * @param uid 用户id
     * @param aid 活动ID
     */
    void delActAttended(String uid, String aid);
}
