package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 商城任务
 * Created by vpc on 2016/4/19.
 */
public interface EKShopTaskInfoDao {
    /**
     * 查询 商城任务列表
     *
     * @param page
     * @param type
     * @param subType
     * @return
     */
    Map<String, Object> list(int page, String type, String subType);

    /**
     * 商城任务 保存
     *
     * @param map
     */
    void save(Map<String, Object> map);

    /**
     * 加载 查询条件数据
     *
     * @return
     */
    Map<String, Object> loadInfo();

    /**
     * 查询 子类型
     *
     * @return
     */
    List<Map<String, Object>> subTypeInfo();

    /**
     * 查询一条数据
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> taskList(String id);

    Map<String, Object> loadInfos(String type);

    /**
     * 添加任务
     *
     * @param args
     */
    void addTask(List<Object> args);

    /**
     * 更新认为
     *
     * @param args
     */
    void updateTask(List<Object> args);


    /**
     * 获取特殊任务列表
     *
     * @param page
     * @return
     */
    Map<String, Object> getTSTask(int page);

    /**
     * 查询特殊任务中间表 一条数据
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> selectOne(String id,String tid);

    /**
     * 查询指定 的APP
     *
     * @return
     */
    List<Map<String, Object>> getAppList();

    /**
     * 查询活动
     *
     * @return
     */
    List<Map<String, Object>> getActList();

    /**
     * 修改特殊任务
     *
     * @param id
     * @param sdate
     * @param edate
     * @param gid
     */
    void updateInfo(String id, String sdate, String edate, String gid,String tid);

    /**
     * 游戏列表
     *
     * @return
     */
    List<Map<String, Object>> getGameList();

    /**
     * 按键设置列表
     *
     * @return
     */
    List<Map<String, Object>> getSettingList();
}
