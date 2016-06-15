package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏活动
 */
public interface EKGameActDao {

	// 查询活动列表
	public List<Map<String, Object>> gameActList(String gid, String name, int page, String gName);

	// 获取活动总数
	public Integer getTotal(String gid, String name, String gName);

	// 根据id删除游戏活动
	public void deleteGameAct(String id);

	// 保存游戏活动
	public void saveGameAct(List<Object> args);

	// 更新游戏活动
	public void updateGameAct(List<Object> args);

	// 根据id查询活动
	public List<Map<String, Object>> queryActById(String editId);

	// 浏览游戏活动的人数--newId：活动id，gid：游戏id
	public Integer scanedNum(String newId, String gid);

	// 查询id是否存在
	public boolean existId(String id);

	Map<String,Object> listUser(int start, int limit, int aid, int flag);

	void updateUser(Map<String, Object> map) throws Exception;
}
