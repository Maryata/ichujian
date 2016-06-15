package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * 游戏帮：H5游戏推荐
 */
public interface H5GameHotSearchDao {

	// 查询未作为推荐的所有游戏
	public Map<String, Object> getAllGame(int page, String name, String gameCate);

	// 查询推荐的游戏
	public Map<String, Object> recommendGameList(int page, String name);

	// 查询指定游戏是否被推荐
	public int isExist(String gid);

	// 添加游戏到推荐中
	public void add(String gid, String order);

	// 更新推荐游戏的排序
	public void update(String gid, String order);

	// 将指定游戏移除出推荐
	public void remove(String gid);

}
