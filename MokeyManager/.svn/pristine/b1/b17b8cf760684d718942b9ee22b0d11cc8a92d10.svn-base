package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 游戏 ： 游戏攻略分类
 */
public interface EKGameStrategyCateDao {

    // 获取游戏攻略分类（下拉菜单）
    public List<Map<String, Object>> getGameStrategyCateList();

    // 分页显示游戏攻略分类
    public Map<String, Object> gameGiftStrategyCateList(int page);

    // 新增游戏游戏攻略分类
    public void addGameGiftStrategyCate(String id, String name, String logo, String order, String modifier);

    // 删除分类
    public void delGameGiftStrategyCate(String id);

    // 更新游戏游戏攻略分类
    public void updateGameGiftStrategyCate(String id, String name, String logo, String order, String modifier);

    // 查询非当前游戏攻略分类的其他所有游戏
    public Map<String, Object> getAllGame(int page, String cid, String name, String gameCate);

    // 查询当前游戏攻略分类的游戏
    public Map<String, Object> getCurrStrategyCateGame(int page, String cid, String name);

    // 查询游戏是否已经存在于当前分类
    public int isExist(String cid, String sid);

    // 新增游戏到当前分类
    public void add(String cid, String sid, String order);

    // 更新当前分类中的游戏
    public void update(String cid, String sid, String order);

    // 将指定游戏移除出当前分类
    public void remove(String cid, String sid);

}
