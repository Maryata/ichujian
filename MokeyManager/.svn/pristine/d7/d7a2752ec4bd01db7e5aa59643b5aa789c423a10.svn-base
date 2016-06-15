package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏活动
 */
public interface GameActService {

	/**
	 *  查询活动列表
	 * @param gid 游戏id
	 * @param name 活动标题名
	 * @param page 分页页码
	 * @param gName 游戏名
	 * @return
	 */
	public List<Map<String, Object>> gameActList(String gid, String name, int page, String gName);

	/**
	 * 获取活动总数
	 * @param gid 游戏id
	 * @param name 活动标题名
	 * @param gName 游戏名
	 * @return
	 */
	public Integer getTotal(String gid, String name, String gName);

	/**
	 * 根据id删除游戏活动
	 * @param id 活动id
	 */
	public void deleteGameAct(String id);

	/**
	 * 保存活动
	 * @param id 活动id
	 * @param gid 游戏id
	 * @param gid 排序
	 * @param name 活动标题
	 * @param depict 活动详情
	 * @param logourl 活动LOGO
	 * @param eDate 活动开始时间
	 * @param sDate 活动结束时间
	 * @param reward 活动奖励
	 */
	public void saveGameAct(String id, String gid, String order,
			String name, String depict, String logourl,
			String sDate, String eDate, String reward);

	/**
	 * 根据id查询活动
	 * @param editId 活动id
	 * @return
	 */
	public List<Map<String, Object>> queryActById(String editId);

	/**
	 * 参与某活动的用户列表
	 * @param start
	 * @param limit
	 * @param aid 活动id
	 * @param flag 是否发放 0 ：未发放 1：已发放
     * @return
     */
	Map<String,Object> listUser(int start, int limit,int aid, int flag);

	void updateUser(Map<String, Object> map);
}
