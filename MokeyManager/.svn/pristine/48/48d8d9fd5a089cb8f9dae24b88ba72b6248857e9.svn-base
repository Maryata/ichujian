package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏攻略
 */
public interface EKGameStrategyDao {

	// 查询攻略列表
	public List<Map<String, Object>> gameStrategyList(String gid, String name, int page, String gName);

	// 获取攻略总数
	public Integer getTotal(String gid, String name, String gName);

	// 根据id删除游戏攻略
	public void deleteGameStrategy(String id);

	// 保存游戏攻略
	public void saveGameStrategy(List<Object> args);

	// 更新游戏攻略
	public void updateGameStrategy(List<Object> args);

	// 根据id查询攻略
	public List<Map<String, Object>> queryStrategyById(String editId);

	// 浏览游戏攻略的人数--newId：攻略id，gid：游戏id
	public Integer scanedNum(String newId, String gid);

	// 查询id是否存在
	public boolean existId(String id);

	
	
	// 查询所有攻略id和其对应的攻略内容（仅用于给所有图片设置尺寸）
	public List<Map<String, Object>> selectDepict();
	// 给所有图片设置尺寸
	public int setImgSize(String id, String depict, String flag);

}
