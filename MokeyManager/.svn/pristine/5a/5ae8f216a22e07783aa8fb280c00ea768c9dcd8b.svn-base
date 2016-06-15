package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * H5游戏合集
 * @author Maryn
 *
 */
public interface H5GameCollectionDao {

	// 获取H5游戏合集列表
	public List<Map<String, Object>> h5GameCol();

	// 添加游戏合集
	public void h5AddCol(String name);

	// 删除合集
	public void h5DeleteCol(String cid);

	// 使合集不可用/可用
	public void h5IsValid(String cid, String islive);

	// 修改合集名称
	public void h5ModifyCol(String cid, String cname, String islive);

	// 查询不属于当前合集的游戏总条数
	public Integer getTotal(String cid);

	// 查询不属于当前合集的游戏
	public List<Map<String, Object>> getGameList(int page, String cid);

	// 根据合集id查询合集对应的游戏页数
	public Integer getTotalCol(String cid);

	// 根据合集id查询游戏
	public List<Map<String, Object>> getGamePageByColId(String cid, int page);

	// 条件查询游戏总数
	public Integer getTotalCondition(String cid, String name);

	// 条件查询游戏APP
	public List<Map<String, Object>> queryGameCondition(String cid, String name, int page);

	// 查询游戏是否已经存在于当前合集中
	public Integer isExist(String cid, String string);

	// 对指定id的游戏进行合集分类
	public void handleCol(String cid, String string, String string2);

	// 更新当前合集中的游戏排序
	public void updateOrder(String cid, String string, String string2);

	// 从指定合集中移除指定游戏
	public void removeGame(String cid, String gid);

	// 删除合集游戏中间表数据
	public void h5DeleteColGame(String cid);

	// 根据合集id查询合集游戏（用于生成html）
	public List<Map<String, Object>> getGamePageByColIdHtml(String cid, int page);

	// 查询指定H5游戏被启动的次数
	public Integer startingCountH5(String gid);

	// 条件查询合集中游戏总数
	public Integer getColTotalCondition(String cid, String name);

	// 条件查询合集中游戏
	public List<Map<String, Object>> queryColGameCondition(String cid,
			String name, int page);
}
