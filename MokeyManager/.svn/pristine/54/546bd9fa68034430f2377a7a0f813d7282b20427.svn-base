package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 首页分类管理
 */
public interface EKActivityIndexCategoryDao {
    /**
     * 查询  所有活动分类信息
     * @param page
     * @param title
     * @return
     */
    Map<String,Object> getAllInfo(int page, String title);
    /**
     * 查询 当前已添加的活动分类信息
     *
     * @param page
     * @param title
     * @return
     */
    Map<String,Object> getCurrInfo(int page, String title);

    /**
     * 查询   ： 添加的数据是否在中间表
     * @param sid
     * @return
     */
    int isExitIndexCategory(String sid);

    /**
     * 添加信息到中间表
     * @param sid
     * @param order
     */
    void addIndexCategory(String sid, String order);

    /**
     * 更新数据到中间表
     * @param sid
     * @param order
     */
    void updateIndexCategory(String sid, String order);

    /**
     * 删除中间表数据
     * @param id
     */
    void removeIndexCategory(String id);
}
