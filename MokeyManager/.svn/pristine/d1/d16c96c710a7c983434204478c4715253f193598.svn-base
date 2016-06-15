package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

/**
 * 游戏帮：H5游戏推荐
 */
public interface H5GameRecommendService {

	/**
	 * 查询非推荐的其他所有游戏
	 * @param page 分页
	 * @param name 游戏名
	 * @param gameCate 游戏初始分类
	 * @return
	 */
	public Map<String, Object> getAllGame(int page, String name, String gameCate);

	/**
	 * 推荐的游戏 
	 * @param page 分页
	 * @param name 游戏名
	 * @return
	 */
	public Map<String, Object> recommendGameList(int page, String name);

	/**
	 * 推荐维护
	 * @param gid 游戏id
	 * @param removeGid 移除出推荐的游戏
	 * @param order 排序
	 */
	public void handle(String gid, String removeGid, String order);

}
