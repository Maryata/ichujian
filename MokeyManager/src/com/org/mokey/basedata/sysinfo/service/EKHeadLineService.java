package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动头条
 */
public interface EKHeadLineService {
    /**
     * e键 ： 活动头条  ： 获取列表
     * @param
     * @return
     */
    Map<String,Object> ekHeadLineList(int page);

    /**
     * 删除  活动头条
     * @param id
     */
    void toDel(String id);
    /**
     * 更新 ： 活动头条
     * @param id
     * @param logo
     */
    void updateHeadLine(String id, String name, String order, String type, String logo);

    /**
     * 添加 ： 活动头条
     * @param id
     * @param logo

     */
    void addHeadLine(String id, String name, String order, String type, String logo);



}

