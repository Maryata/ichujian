package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/22.
 * 首页  ：  ApP 定制管理
 */
public interface EKIndexSuppIndexAppDao {
    /**
     * ApP 定制管理  :  list
     *
     * @return
     */
    Map<String, Object> list(int page, String code, String aid);

    /**
     * ApP 定制管理:供应商查询
     *
     * @return
     */
    List<Map<String, Object>> getSuppList();

    /**
     * ApP 定制管理:APP查询
     *
     * @return
     */
    List<Map<String, Object>> getAppLsit();

    /**
     * ApP 定制管理  :  添加list
     *
     * @return
     */
    Map<String, Object> addList(int page, String code);

    /**
     * ApP 定制管理  :  添加
     *
     * @return
     */

    void add(String code, String aid, String order);

    /**
     * ApP 定制管理  :  删除
     *
     * @return
     */
    void toDel(String id);

    /**
     * ApP 定制管理  : 查询一条信息
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> selectOne(String id);

    /**
     * ApP 定制管理  :  更新
     *
     * @return
     */
    void update(List<Object> args);

    /**
     * 根据供应商code和应用id查询定制APP是否存在
     * @param code
     * @param aid
     * @return
     */
    int isExists(String code, String aid);

    /**
     * 更新已存在的定制APP
     * @param code
     * @param aid
     * @param order
     */
    void updateExists(String code, String aid, String order);
}
