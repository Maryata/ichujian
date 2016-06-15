package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * 游戏帮：精品游戏首页排版
 */
public interface GameBoutiqueIndexDao {

	// 查询首页显示的分类
	public Map<String, Object> indexCateList(String name, int start, int limit);

	// 查询分类是否存在
	public Integer isExist(String cid);

	// 更新分类
	public void update(String cid, String number, String order);

	// 新增分类
	public void add(String cid, String number, String order);

	// 移除分类
	public void remove(String cid);

	// 分页查询首页中未显示的分类
	public Map<String, Object> cateList(int page, String name);

}
