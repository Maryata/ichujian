package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动头条
 */

public interface EKActivityHeadLineService {
    /**
     * 活动头条   查询活动分类   所有信息
     * @param page
     * @param title
     * @return
     */
    Map<String,Object> getAllInfo(int page, String title);

    /**
     * 活动头条   查询活动头条中间表   所有活动分类信息
     * @param page
     * @param title
     * @return
     */
    Map<String,Object> getCurrInfo(int page, String title,String type);

    /**
     * 活动头条   查询活动   所有信息
     * @param page
     * @param title
     * @return
     */
    Map<String,Object> getAllActivityInfo(int page, String title);

    /**
     * 活动头条  维护
     * @param sid
     * @param name
     * @param img
     * @param removeGid
     * @param order
     */
    void handleActivityHeadLine(String sid, String name, String img, String removeGid, String order,String type);
}

