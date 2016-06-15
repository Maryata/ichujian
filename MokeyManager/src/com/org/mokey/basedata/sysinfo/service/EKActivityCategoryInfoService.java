package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动管理
 */
public interface EKActivityCategoryInfoService {
    /**
     * e键 ： 活动分类  ： 获取列表
     *
     * @param page
     * @return
     */
    Map<String, Object> ekActivityCategoryInfoList(int page);

    /**
     * 添加  活动分类
     *
     * @param id
     * @param name
     * @param logo
     */
    void addInfo(String id, String name, String logo, String type, String headLineImg);

    /**
     * 删除  活动分类
     *
     * @param id
     */
    void toDel(String id);

    /**
     * 更新 ： 活动分类
     *
     * @param id
     * @param name
     * @param logo
     * @param type
     */
    void updateInfo(String id, String name, String logo, String type, String headLineImg);


    /**
     * 查询非当前活动分类的其他所有活动
     *
     * @param page
     * @param cid
     * @param title
     * @param imgType
     * @return
     */
    Map<String, Object> getAllActivityCategoryInfo(int page, String cid, String title, String ccid, String imgType);

    /**
     * 获取 活动全部分类
     *
     * @return
     */
    List<Map<String, Object>> activityCategoryList(String type);

    /**
     * 获取当前分类的活动
     *
     * @param page
     * @param cid
     * @param title
     * @return
     */
    Map<String, Object> getCurrActivityCaInfo(int page, String cid, String title);

    /**
     * 活动分类 维护
     *  @param cid
     * @param aid
     * @param removeGid
     * @param order
     * @param title
     * @param subtitle
     */
    void handleActivity(String cid, String aid, String removeGid, String order, String tagid, String title, String subtitle);

    /**
     * 活动首页  ：  活动分类
     *
     * @param page
     * @return
     */
    Map<String, Object> ekActivityCategoryList(int page, List ids);

    /**
     * 活动首页  ：  中间 分类
     *
     * @param page
     * @param ids
     * @return
     */
    Map<String, Object> ekActivityInfoList(int page, List ids);

}
