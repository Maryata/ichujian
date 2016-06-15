package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 微用帮：应用合集
 */
public interface McrAppColService {

	/**
	 * 获取应用合集列表
	 * @return
	 */
	public List<Map<String, Object>> mcrAppColList();

	/**
	 * 新增合集
	 * @param addName 新增合集名称
	 * @param userName 操作人
	 */
	public void mcrAppColAdd(String addName, String userName);

	/**
	 * 删除合集
	 * @param cid 合集id
	 */
	public void mcrAppColDel(String cid);

	/**
	 * 更新合集
	 * @param cid 合集id
	 * @param cname 合集名称
	 * @param modifier 操作人
	 */
	public void update(String cid, String cname, String modifier);

	/**
	 * 查询所有应用总数
	 * @param cid 当前合集id
	 * @return
	 */
	public Integer getAppTotal(String cid);

	/**
	 * 查询所有应用
	 * @param page 分页
	 * @param cid 合集id
	 * @return
	 */
	public List<Map<String,Object>> getAppList(int page, String cid);

	/**
	 * 查询当前合集的应用总数 
	 * @param cid 当前合集id
	 * @return
	 */
	public Integer getColTotal(String cid);

	/**
	 * 查询当前合集的应用 
	 * @param page 分页
	 * @param cid 合集id
	 * @return
	 */
	public List<Map<String, Object>> getColList(int page, String cid);

	/**
	 * 条件查询应用数量
	 * @param cid 合集id
	 * @param name 应用名称
	 * @return
	 */
	public Integer getTotalCondition(String cid, String name);

	/**
	 * 条件查询应用
	 * @param page 分页
	 * @param cid 合集id
	 * @param name 应用名称
	 * @return
	 */
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name);

	/**
	 * 条件查询当前合集的应用数量
	 * @param cid 合集id
	 * @param name 应用名称
	 * @return
	 */
	public Integer getColTotalCondition(String cid, String name);

	/**
	 * 条件查询当前合集的应用
	 * @param page 分页
	 * @param cid 合集id
	 * @param name 应用名称
	 * @return
	 */
	public List<Map<String, Object>> getColListCondition(int page, String cid,
			String name);

	/**
	 * 对应用进行合集分类
	 * @param cid 合集id
	 * @param aids 应用id的集合
	 * @param removeAids 移出的应用id
	 * @param orders 更新/新增应用的排序
	 */
	public void handleCol(String cid, String[] aids, String[] removeAids,
			String[] orders);


}
