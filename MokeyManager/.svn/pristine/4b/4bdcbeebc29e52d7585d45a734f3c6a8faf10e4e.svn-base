package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏攻略
 */
public interface GameStrategyService {

	/**
	 *  查询攻略列表
	 * @param gid 游戏id
	 * @param name 攻略标题名
	 * @param page 分页页码
	 * @param gName 游戏名
	 * @return
	 */
	public List<Map<String, Object>> gameStrategyList(String gid, String name, int page, String gName);

	/**
	 * 获取攻略总数
	 * @param gid 游戏id
	 * @param name 攻略标题名
	 * @param gName 游戏名 
	 * @return
	 */
	public Integer getTotal(String gid, String name, String gName);

	/**
	 * 根据id删除游戏攻略
	 * @param id 攻略id
	 */
	public void deleteGameStrategy(String id);

	/**
	 * 保存攻略
	 * @param id 攻略id
	 * @param gid 游戏id
	 * @param gid 排序
	 * @param name 攻略标题
	 * @param depict 攻略详情
	 * @param logourl 攻略LOGO
	 */
	public void saveGameStrategy(String id, String gid, String order, String name, String depict, String logourl);

	/**
	 * 根据id查询攻略
	 * @param editId 攻略id
	 * @return
	 */
	public List<Map<String, Object>> queryStrategyById(String editId);

	// 查询所有攻略id和其对应的攻略内容（仅用于给所有图片设置尺寸）
	public List<Map<String, Object>> selectDepict();

	// 给所有图片设置尺寸
	public int setImgSize(String id, String depict, String flag);

}
