package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e 键 ： 游戏  ：  游戏攻略分类
 */
public interface EKGameStrategyCateService {


    // 获取游戏攻略分类（下拉菜单）
    public List<Map<String, Object>> getGameStrategyCateList();

    /**
     * 分页显示游戏攻略分类
     * @param page 分页
     * @return
     */
    public Map<String, Object> gameStrategyCateList(int page);

    /**
     * 新增游戏游戏攻略分类
     * @param id 主键
     * @param name 分类名称 
     * @param logo logo
     * @param order 排序
     * @param modifier 修改人
     */
    public void addGameStrategyCate(String id, String name, String logo, String order,
                            String modifier);

    /**
     * 删除分类
     * @param id 分类id
     */
    public void delGameStrategyCate(String id);

    /**
     * 更新游戏游戏攻略分类
     * @param id 主键
     * @param name 分类名称 
     * @param logo logo
     * @param order 排序
     * @param modifier 修改人
     */
    public void updateGameStrategyCate(String id, String name, String logo,
                               String order, String modifier);

    /**
     * 查询非当前游戏攻略分类的其他所有游戏
     * @param page 分页
     * @param cid 分类id
     * @param name 游戏名称
     * @param gameStrategyCate 游戏初始分类
     * @return
     */
    public Map<String, Object> getAllGame(int page, String cid, String name,
                                          String gameStrategyCate);

    /**
     * 查询当前游戏攻略分类的游戏
     * @param page 分页
     * @param cid 分类id
     * @param name 游戏名称
     * @return
     */
    public Map<String, Object> getCurrStrategyCateGame(int page, String cid, String name);

    /**
     * 游戏攻略分类维护 
     * @param cid 分类id
     * @param sid 游戏id
     * @param removeGid 移除出当前分类的游戏id
     * @param order 排序
     */
    public void handleStrategyCate(String cid, String sid, String removeGid,
                           String order);

}
