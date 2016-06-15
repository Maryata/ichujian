package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏资讯
 */
public interface GameInfoService {

	/**
	 *  查询资讯列表
	 * @param gid 游戏id
	 * @param name 资讯标题名
	 * @param page 分页页码
	 * @param gName 游戏名
	 * @return
	 */
	public List<Map<String, Object>> gameInfoList(String gid, String name, int page, String gName);

	/**
	 * 获取资讯总数
	 * @param gid 游戏id
	 * @param name 资讯标题名
	 * @param gName 游戏名
	 * @return
	 */
	public Integer getTotal(String gid, String name, String gName);

	/**
	 * 根据id删除游戏资讯
	 * @param id 资讯id
	 */
	public void deleteGameInfo(String id);

	/**
	 * 保存资讯
	 * @param id 资讯id
	 * @param gid 游戏id
	 * @param gid 排序
	 * @param name 资讯标题
	 * @param depict 资讯详情
	 * @param logourl 资讯LOGO
	 */
	public void saveGameInfo(String id, String gid, String order, String name, String depict, String logourl);

	/**
	 * 根据id查询资讯
	 * @param editId 资讯id
	 * @return
	 */
	public List<Map<String, Object>> queryInfoById(String editId);

}
