package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/22.
 * 首页  ：  定制APP管理
 */
public interface EKIndexSuppIndexAppService {
    /**
     * 首页 ：  定制APP  list
     *
     * @param page
     * @return
     */
    Map<String, Object> list(int page, String code, String aid);

    /**
     * 定制APP :  供应商查询
     *
     * @return
     */
    List<Map<String, Object>> getSuppList();

    /**
     * 定制APP ： app应用查询
     *
     * @return
     */
    List<Map<String, Object>> getAppLsit();

    /**
     * 首页 ：  定制APP  添加list
     *
     * @param page
     * @return
     */
    Map<String, Object> addList(int page, String code);

    /**
     * 首页 ：  定制APP  添加
     *
     * @param
     * @return
     */
    void add(String aid, String code, String order);

    /**
     * 首页 ：  定制APP  删除
     *
     * @param
     * @return
     */
    void toDel(String id);

    /**
     * 查询一条信息
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> selectOne(String id);
    /**
     * 首页 ：  定制APP  更新
     *
     * @param
     * @return
     */
    void update(List<Object> args);
}
