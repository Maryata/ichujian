package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 微用帮：首页排版
 */
public interface McrAppIndexDao {

	// 查询当前首页显示的合集
	public List<Map<String,Object>> currCol();

	// 查询首页显示的分类
	public Map<String, Object> indexColList(String name, int start, int limit);

	// 查询合集/分类是否存在
	public Integer isExist(String type, String cid);

	// 更新合集/分类
	public void update(String type, String cid, String number, String order);

	// 新增合集/分类
	public void add(String type, String cid, String number, String order);

	// 移除合集/分类
	public void remove(String type, String cid);

	// 分页查询首页中未显示的分类
	public Map<String, Object> cateList(int page, String name);

}
