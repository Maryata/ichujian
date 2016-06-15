package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 */
public interface EKIndexModelDao {
    /**
     * e键 ： 首页  ：查询所有的app应用
     * @param page
     * @param name
     * @return
     */
    Map<String,Object> getAllApp(int page, String name);

    /**
     * e键 ： 首页  ： 查询当前app应用
     * @param page
     * @param name
     * @return
     */
    Map<String,Object> getCurrApp(int page, String name);

    /**
     * e键 ： 首页  ： 查询中间表中是否存在
     * @param cid
     * @return
     */
    int isExist(String cid);

    /**
     * e键 ： 首页  ： 添加
     * @param cid
     * @param order
     * @param isEdit
     */
    void add(String cid, String order, String isEdit);

    /**
     * e键 ： 首页  ： 更新
     * @param cid
     * @param order
     * @param isEdit
     */
    void update(String cid, String order, String isEdit);

    /**
     * e键 ： 首页  ： 移除
     * @param id
     */
    void remove(String id);
}
