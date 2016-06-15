package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：首页分类管理
 */
public interface EKActivityIndexCategoryService {
    /**
     * 查询 所有活动分类信息
     * @param page
     * @param title
     * @return
     */
    Map<String,Object> getAllInfo(int page, String title);
    /**
     * 查询 已添加的活动分类信息
     * @param page
     * @param title
     * @return
     */
    Map<String,Object> getCurrInfo(int page, String title);

    /**
     * 首页分类维护
     * @param sid
     * @param removeGid
     * @param order
     */
    void handleIndexCategory(String sid, String removeGid, String order);
}
