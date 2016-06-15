package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 微用帮：应用分类
 */
public interface McrAppCateDao {

	// 新增分类
	public void mcrAppCateAdd(String id, String name, String logo,
			String order, String modifier);

	// 删除分类
	public void mcrAppCateDel(String cid);

	// 更新分类
	public void update(String cid, String cname, String modifier, String logo, String order);

	// 查询所有应用总数
	public Integer getAppTotal(String cid);

	// 查询所有应用
	public List<Map<String, Object>> getAppList(int page, String cid);

	// 查询当前分类的应用总数 
	public Integer getCateTotal(String cid);

	// 查询当前分类的应用 
	public List<Map<String, Object>> getCateList(int page, String cid);

	// 条件查询应用数量
	public Integer getTotalCondition(String cid, String name, String appCate);

	// 条件查询应用
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name, String appCate);

	// 条件查询当前分类的应用数量
	public Integer getCateTotalCondition(String cid, String name);

	// 条件查询当前分类的应用
	public List<Map<String, Object>> getCateListCondition(int page, String cid,
			String name);

	// 查询应用是否已经存在于当前分类中
	public int isExist(String cid, String aid);

	// 新增应用到分类中
	public void handleCate(String cid, String aid, String order);

	// 更新应用在分类中的顺序
	public void updateOrder(String cid, String aid, String order);

	// 将应用移出分类
	public void removeApp(String cid, String aid);

	// 查询所有分类总数
	public Integer getAllCateTotal();

	// 分页查询所有分类
	public List<Map<String, Object>> getAllCateList(int page);

	// 分页查询所有分类（可有查询条件）
	public List<Map<String, Object>> getAllCateList(int page, String name);

	// 查询所有分类名称和id
	public List<Map<String, Object>> cateList();
}
