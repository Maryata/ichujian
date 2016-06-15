package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;


public interface GameTaskService {

	/**
	 * @Description:  任务列表
	 * @param name 查询参数，游戏名称
	 * @param start 
	 * @param limit 
	 * @return
	 */
	Map<String, Object> list (String name, int start, int limit);

	/**
	 * @Description:  保存或者更新任务对象
	 * @param map H5对象
	 */
	void save (Map<String, Object> map);

	/**
	 * @Description:  删除任务
	 * @param id 任务ID
	 */
	void delete (String id);

	/**
	 * 查询所有奖励
	 * @return
	 */
	public List<Map<String, Object>> getAllRewards();

	/**
	 * 新增签到奖励
	 * @param days 连续天数
	 * @param score 奖励
	 */
	public void addReward(String days, String score);

	/**
	 * 删除签到奖励
	 * @param id 对应id
	 */
	public void delReward(String id);

	/**
	 * 修改签到奖励
	 * @param id 对应id
	 * @param days 连续天数
	 * @param score 奖励
	 */
	public void handleReward(String id, String days, String score);

}

