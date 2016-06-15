package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/7.
 * e键 ： 活动首页
 */
public interface EKActivityIndexService {
    /**
     * 活动首页 ： 添加
     * @param headLine
     * @param activityInfo
     * @param activity
     */
    void save(String headLine, String activityInfo, String activity);

    /**
     * 活动头条中间表  ：  查询
     * @return
     */
    List<Map<String,Object>> getoldheadLineList();

    /**
     * 活动分类中间表 ： 查询
     * @return
     */
    List<Map<String,Object>> getoldActivityInfoList();

    /**
     * 活动详情中间表 ：  查询
     * @return
     */
    List<Map<String,Object>> getoldActivityList();
}
