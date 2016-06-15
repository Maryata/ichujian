package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

public interface MallProductDao {
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
	 * @param map 任务对象
	 */
	void save (Map<String, Object> map);

	/**
	 * @Description:  删除任务
	 * @param id 任务ID
	 */
	void delete (String id);

	/**
	 * 游戏名称列表
	 * @param start
	 * @param limit
	 * @return
	 */
	Map<String,Object> listGame (int start, int limit);
}