package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：h5游戏分类
 */
public interface EKH5GameCategoryDao {

    // 获取游戏分类（下拉菜单）
    List<Map<String, Object>> getGameCategoryList();

    // 分页显示游戏分类
    Map<String, Object> gameGiftCateList(int page);

    // 新增游戏游戏分类
    void addGameGiftCate(String id, String name, String logo,
                         String order, String modifier);

    // 删除分类
    void delGameGiftCate(String id);

    // 更新游戏游戏分类
    void updateGameGiftCate(String id, String name, String logo,
                            String order, String modifier);

    // 查询非当前游戏分类的其他所有游戏
    Map<String, Object> getAllGame(int page, String cid, String name,
                                   String gameCate);

    // 查询当前游戏分类的游戏
    Map<String, Object> getCurrCateGame(int page, String cid, String name);

    // 查询游戏是否已经存在于当前分类
    int isExist(String cid, String gid);

    // 新增游戏到当前分类
    void add(String cid, String gid, String order);

    // 更新当前分类中的游戏
    void update(String cid, String gid, String order);

    // 将指定游戏移除出当前分类
    void remove(String cid, String gid);

}
