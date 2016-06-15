package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 微用帮：应用合集
 */
public interface McrAppColDao {

	// 获取应用合集列表
	public List<Map<String, Object>> mcrAppColList();

	// 新增合集
	public void mcrAppColAdd(String addName, String modifier);

	// 删除合集
	public void mcrAppColDel(String cid);

	// 更新合集
	public void update(String cid, String cname, String modifier);

	// 查询所有应用总数
	public Integer getAppTotal(String cid);

	// 查询所有应用
	public List<Map<String, Object>> getAppList(int page, String cid);

	// 查询当前合集的应用总数 
	public Integer getColTotal(String cid);

	// 查询当前合集的应用 
	public List<Map<String, Object>> getColList(int page, String cid);

	// 条件查询应用数量
	public Integer getTotalCondition(String cid, String name);

	// 条件查询应用
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name);

	// 条件查询当前合集的应用数量
	public Integer getColTotalCondition(String cid, String name);

	// 条件查询当前合集的应用
	public List<Map<String, Object>> getColListCondition(int page, String cid,
			String name);

	// 查询应用是否已经存在于当前合集中
	public int isExist(String cid, String aid);

	// 新增应用到合集中
	public void handleCol(String cid, String aid, String order);

	// 更新应用在合集中的顺序
	public void updateOrder(String cid, String aid, String order);

	// 将应用移出合集
	public void removeApp(String cid, String aid);


}
