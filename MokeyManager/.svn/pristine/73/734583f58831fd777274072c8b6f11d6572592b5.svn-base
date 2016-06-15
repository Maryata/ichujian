package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动头条
 */
public interface EKActivityHeadLineDao {
    /**
     * 活动头条   查询活动分类   所有信息
     *
     * @param page
     * @param title
     * @return
     */
    Map<String, Object> getAllInfo(int page, String title);

    /**
     * 活动头条   查询活动头条中间表   所有活动分类信息
     *
     * @param page
     * @param title
     * @return
     */
    Map<String, Object> getCurrInfo(int page, String title, String type);

    /**
     * 活动头条   查询活动   所有信息
     *
     * @param page
     * @param title
     * @return
     */
    Map<String, Object> getAllActivityInfo(int page, String title);

    /**
     * 查询活动条中间表  是否存在这条数据
     *
     * @param sid
     * @return
     */
    int isExitHeadLine(String sid, String type);

    /**
     * 添加数据到活动条中间表
     *
     * @param sid
     * @param order
     * @param name
     * @param img
     */
    void addHeadLine(String sid, String order, String name, String img, String type);

    /**
     * 更新活动头条中间表中得数据
     *
     * @param sid
     * @param order
     * @param name
     * @param img
     */
    void updateHeadLine(String sid, String order, String name, String img, String type);

    /**
     * 删除活动条中间表的数据
     *
     * @param id
     */
    void removeHeadLine(String id,String type);
}
