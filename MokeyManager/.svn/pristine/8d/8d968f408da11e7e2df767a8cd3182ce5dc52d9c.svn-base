package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 微用帮：首页排版
 */
public interface McrAppIndexService {

	/**
	 * 查询当前首页显示的合集
	 * @return
	 */
	public List<Map<String,Object>> currCol();

	/**
	 * 查询首页显示的分类
	 * @param name 查询分类的名称
	 * @param start 起始页
	 * @param limit 每页总条数
	 * @return
	 */
	public Map<String, Object> indexColList(String name, int start, int limit);

	/**
	 * 首页排版
	 * @param col 合集id
	 * @param colCnt 合集中应用的数量
	 * @param id 分类id
	 * @param removeId 移除出的分类id
	 * @param order 分类的排序
	 * @param number 分类中的应用的数量
	 */
	public void handle(String col, String colCnt, String id, String removeId, String order,
			String number);

	/**
	 * 分页查询首页中未显示的分类
	 * @param page 分页
	 * @param name 分类名称
	 * @return
	 */
	public Map<String, Object> cateList(int page, String name);

}
