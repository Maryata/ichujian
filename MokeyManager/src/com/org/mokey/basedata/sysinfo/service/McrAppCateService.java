package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 微用帮：应用分类
 */
public interface McrAppCateService {
	
	/**
	 * 新增分类
	 * @param id 主键
	 * @param name 分类名称 
	 * @param logo logo
	 * @param order 排序
	 * @param modifier 修改人
	 */
	public void mcrAppCateAdd(String id, String name, String logo,
			String order, String modifier);

	/**
	 * 删除分类
	 * @param cid 分类id
	 */
	public void mcrAppCateDel(String cid);

	/**
	 * 更新分类
	 * @param cid 分类id
	 * @param cname 分类名称
	 * @param islive 是否有效
	 * @param order 排序
	 * @param logo logo
	 */
	public void update(String cid, String cname, String islive, String logo, String order);

	/**
	 * 查询所有应用总数
	 * @param cid 当前分类id
	 * @return
	 */
	public Integer getAppTotal(String cid);

	/**
	 * 查询所有应用
	 * @param page 分页
	 * @param cid 分类id
	 * @return
	 */
	public List<Map<String,Object>> getAppList(int page, String cid);

	/**
	 * 查询当前分类的应用总数 
	 * @param cid 当前分类id
	 * @return
	 */
	public Integer getCateTotal(String cid);

	/**
	 * 查询当前分类的应用 
	 * @param page 分页
	 * @param cid 分类id
	 * @return
	 */
	public List<Map<String, Object>> getCateList(int page, String cid);

	/**
	 * 条件查询应用数量
	 * @param cid 分类id
	 * @param name 应用名称
	 * @param appCate 应用初始分类
	 * @return
	 */
	public Integer getTotalCondition(String cid, String name, String appCate);

	/**
	 * 条件查询应用
	 * @param page 分页
	 * @param cid 分类id
	 * @param name 应用名称
	 * @param appCate 应用初始分类
	 * @return
	 */
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name, String appCate);

	/**
	 * 条件查询当前分类的应用数量
	 * @param cid 分类id
	 * @param name 应用名称
	 * @return
	 */
	public Integer getCateTotalCondition(String cid, String name);

	/**
	 * 条件查询当前分类的应用
	 * @param page 分页
	 * @param cid 分类id
	 * @param name 应用名称
	 * @return
	 */
	public List<Map<String, Object>> getCateListCondition(int page, String cid,
			String name);

	/**
	 * 对应用进行分类
	 * @param cid 分类id
	 * @param aids 应用id的分类
	 * @param removeAids 移出的应用id
	 * @param orders 更新/新增应用的排序
	 */
	public void handleCate(String cid, String[] aids, String[] removeAids,
			String[] orders);

	/**
	 * 查询所有分类总数
	 * @return
	 */
	public Integer getAllCateTotal();

	/**
	 * 分页查询所有分类
	 * @param page 分页
	 * @return
	 */
	public List<Map<String, Object>> getAllCateList(int page);

	/**
	 * 分页查询所有分类（可有查询条件）
	 * @param page 分页
	 * @param name 分类名称
	 * @return
	 */
	public List<Map<String, Object>> getAllCateList(int page, String name);

	/**
	 * 查询所有分类名称和id
	 * @return
	 */
	public List<Map<String, Object>> cateList();
}
