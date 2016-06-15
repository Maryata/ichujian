package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * 游戏帮：游戏热门推荐
 */
public interface GameHotSearchDao {

	// 查询未作为推荐的热门
	public Map<String, Object> getAllGame(int page, String name,
			String giftCate);

	// 查询推荐的热门
	public Map<String, Object> hotSearchGameList(int page, String name);

	// 查询热门是否已经存在于当前分类
	public int isExist(String gid);

	// 新增热门到当前分类
	public void add(String gid, String order);

	// 更新当前分类中的热门
	public void update(String gid, String order);

	// 将指定热门移除出当前分类
	public void remove(String gid);

}
