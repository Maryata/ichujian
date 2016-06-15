package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/7.
 * e键 ： 活动首页
 */
public interface EKActivityIndexDao {

    /**
     * 活动头条 原有数据
     * @return
     */
    List<Map<String,Object>> getoldheadLineList();

    /**
     * 删除  活动头条中间表  多余数据
     * @param id
     */
    void deleteHeadLine(String id);

    /**
     * 添加  活动头条中间表
     * @param eid
     * @param name
     * @param type
     * @param logo
     * @param order
     */
    void addHeadLine(String eid, String name, String type, String logo, String order);

    /**
     * 更新  活动头条中间表
     * @param id
     * @param eid
     * @param name
     * @param type
     * @param logo
     * @param order
     */
    void updateHeadLine(String id, String eid, String name, String type, String logo, String order);

    /**
     * 查询  活动分类中间表  原数据
     * @return
     */
    List<Map<String,Object>> getoldActivityInfoList();

    /**
     *  删除   活动分类中间表
     * @param c_id
     */
    void deleteActivityInfo(String c_id);

    /**
     * 添加  活动分类中间表
     * @param cid
     * @param order
     */
    void addActivityInfo(String cid, String order);

    /**
     * 更新  活动分类中间表
     * @param id
     * @param cid
     * @param order
     */
    void updateActivityInfo(String id, String cid, String order);

    /**
     * 查询 ： 活动详情中间表 页面   c_cid = 1 或  2   原数据
     * @return
     */
    List<Map<String,Object>> getoldActivityList();

    /**
     * 删除  ：  活动详情  中间 表    多余数据
     * @param c_id
     */
    void deleteActivity(String c_id);

    /**
     * 添加  活动详情   中间表
     * @param aid
     * @param ccid
     * @param tagid
     * @param order
     */
    void addActivity(String aid, String ccid, String tagid, String order);

    /**
     * 更新活动详情中间表
     * @param id
     * @param aid
     * @param ccid
     * @param tagid
     * @param order
     */
    void updateActivity(String id, String aid, String ccid, String tagid, String order);
}
