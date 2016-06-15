package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏分类
 */
public interface GameCategoryService {

	// 获取游戏分类（下拉菜单）
	public List<Map<String, Object>> getGameCategoryList();

	/**
	 * 分页显示游戏分类
	 * @param page 分页
	 * @return
	 */
	public Map<String, Object> gameCategoryList(int page);

	/**
	 * 新增游戏游戏分类
	 * @param id 主键
	 * @param name 分类名称 
	 * @param logo logo
	 * @param order 排序
	 * @param modifier 修改人
	 */
	public void addGameCate(String id, String name, String logo, String order,
			String modifier);

	/**
	 * 删除分类
	 * @param id 分类id
	 */
	public void delGameCate(String id);

	/**
	 * 更新游戏游戏分类
	 * @param id 主键
	 * @param name 分类名称 
	 * @param logo logo
	 * @param order 排序
	 * @param modifier 修改人
	 */
	public void updateGameCate(String id, String name, String logo,
			String order, String modifier);

	/**
	 * 查询非当前游戏分类的其他所有游戏
	 * @param page 分页
	 * @param cid 分类id
	 * @param name 游戏名称
	 * @param gameCate 游戏初始分类
	 * @return
	 */
	public Map<String, Object> getAllGame(int page, String cid, String name,
			String gameCate);

	/**
	 * 查询当前游戏分类的游戏
	 * @param page 分页
	 * @param cid 分类id
	 * @param name 游戏名称
	 * @return
	 */
	public Map<String, Object> getCurrCateGame(int page, String cid, String name);

	/**
	 * 游戏分类维护 
	 * @param cid 分类id
	 * @param gid 游戏id
	 * @param removeGid 移除出当前分类的游戏id
	 * @param order 排序
	 */
	public void handleCate(String cid, String gid, String removeGid,
			String order);

}
