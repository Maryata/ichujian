package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动分类
 */
public interface EKActivityCategoryInfoDao {
    /**
     * e键 ：活动分类　：　获取列表
     *
     * @param page
     * @return
     */
    Map<String, Object> ekActivityCategoryInfoList(int page);

    /**
     * 添加 活动分类
     *
     * @param id
     * @param name
     * @param logo
     */
    void addInfo(String id, String name, String logo, String type, String headLineImg);

    /**
     * 删除  ：活动分类
     *
     * @param id
     */
    void toDel(String id);

    /**
     * 更新  ： 活动分类
     *
     * @param id
     * @param name
     * @param logo
     * @param type
     */
    void updateInfo(String id, String name, String logo, String type, String headLineImg);

    /**
     * 查询 所有  活动分类
     *
     * @return
     */
    List<Map<String, Object>> getAllCaList();

    /**
     * 获取全部活动分类
     *
     * @return
     */
    List<Map<String, Object>> activityCategoryList(String type);

    /**
     * 查询非当前活动分类的其他所有活动
     *
     * @return
     */
    Map<String, Object> getAllActivityCategoryInfo(int page, String cid, String title, String ccid, String imgType);


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
     * 查询活动是否存在
     * @param cid
     * @param aid
     * @return
     */
   /* int isExist(String cid, String aid);*/

    /**
     * 不存在  新增
     *  @param cid
     * @param aid
     * @param order
     * @param title
     * @param subtitle
     */
    void addIndex(String cid, String aid, String order, String tagid, String title, String subtitle);

    /**
     * 存在  更新
     *  @param cid
     * @param aid
     * @param order
     * @param title
     * @param subtitle
     */

    void updateIndex(String cid, String aid, String order, String tagid, String title, String subtitle);

    /**
     * 删除
     *
     * @param cid
     * @param id
     */

    void removeIndex(String cid, String id);

    int isExitActivity(String cid, String aid);

    /**
     * 活动首页  ：  活动分类
     *
     * @param page
     * @return
     */
    Map<String, Object> ekActivityCategoryList(int page, List ids);

    /**
     * 活动首页  ： 活动分类  中间
     *
     * @param page
     * @param ids
     * @return
     */
    Map<String, Object> ekActivityInfoList(int page, List ids);

    /**
     * 删除活动分类  ：    查询活动头条中是否存在
     * @param id
     * @return
     */
    int isExitHeadLine(String id);

    /**
     * 删除活动头条中相关的信息
     * @param id
     */
    void removeHeadLine(String id);
    /**
     * 删除首页活动分类  ：    查询首页活动分类中是否存在
     * @param id
     * @return
     */
    int isExitActivityIndexCate(String id);
    /**
     * 删除首页活动分类中相关的信息
     * @param id
     */
    void removeActivityIndexCate(String id);

    /**
     * 删除“分类活动中间表”T_EK_ACTIVITY_CATEGORY_ACTIVIT表中当前分类的所有数据
     * @param id
     */
    void removeActByCid(String id);
}
