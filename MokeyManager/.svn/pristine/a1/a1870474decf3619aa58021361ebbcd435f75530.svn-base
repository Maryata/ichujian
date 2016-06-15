package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏资讯
 */
public interface EKGameInfoDao {

	// 查询资讯列表
	public List<Map<String, Object>> gameInfoList(String gid, String name, int page, String gName);

	// 获取资讯总数
	public Integer getTotal(String gid, String name, String gName);

	// 根据id删除游戏资讯
	public void deleteGameInfo(String id);

	// 保存游戏资讯
	public void saveGameInfo(List<Object> args);

	// 更新游戏资讯
	public void updateGameInfo(List<Object> args);

	// 根据id查询资讯
	public List<Map<String, Object>> queryInfoById(String editId);

	// 浏览游戏资讯的人数--newId：资讯id，gid：游戏id
	public Integer scanedNum(String newId, String gid);

	// 查询id是否存在
	public boolean existId(String id);

}
