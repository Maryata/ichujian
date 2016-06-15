package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 *	游戏帮：游戏合集
 */
public interface EKGameCollectionDao {
	
	// 获取游戏合集
	public List<Map<String, Object>> gameCol(String cid);
	
	// 添加游戏合集
	public void addCol(String name, String type);

	// 删除游戏合集
	public void deleteCol(String cid);

	// 修改合集名称
	public void modifyCol(String cid, String cname, String islive);

	// 使合集不可用/可用  
	public void isvalid(String cid, String islive);

	// 查询不属于当前合集的游戏
	public List<Map<String, Object>> getGameList(int page, String cid, String type);

	// 对指定id的游戏进行合集分类
	public void handleCol(String cid, String gids, String order);

	// 查询不属于当前合集的游戏总条数
	public Integer getTotal(String cid, String type);

	// 查询游戏是否已经存在于当前合集中
	public Integer isExist(String cid, String gid);

	// 条件查询游戏APP
	public List<Map<String, Object>> queryGameCondition(String cid, String name, String type, String size, String maxSize, int page);

	// 条件查询游戏总数
	public Integer getTotalCondition(String cid, String name, String type, String minSize, String maxSize);

	// 根据合集id查询游戏
	public List<Map<String, Object>> getGamePageByColId(String cid, int page, String type);

	// 根据合集id查询合集对应的游戏页数
	public Integer getTotalCol(String cid, String type);
	
	// 从指定合集中移除游戏
	public void removeGame(String cid, String gid);

	// 更新当前合集中的游戏排序
	public void updateOrder(String cid, String gid, String order);

	// 删除合集游戏中间表数据
	public void deleteColGame(String cid);

	// 条件查询合集中游戏总数
	public Integer getColTotalCondition(String cid, String name, String type);

	// 条件查询合集中游戏
	public List<Map<String, Object>> queryColGameCondition(String cid,
														   String name, int page, String type);

}
