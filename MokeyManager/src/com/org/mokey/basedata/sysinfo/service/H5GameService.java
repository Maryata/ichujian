package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;


public interface H5GameService {

	/**
	 * @Description: TODO H5游戏列表
	 * @param name 查询参数，游戏名称
	 * @param start 
	 * @param limit 
	 * @return
	 */
	Map<String, Object> list(String name, int start, int limit);

	/**
	 * @Description: TODO 保存或者更新H5游戏对象
	 * @param map H5对象
	 */
	void save(Map<String, Object> map);

	/**
	 * @Description: TODO 删除H5游戏
	 * @param id H5游戏ID
	 */
	void delete(String id);
	
	/**
	 * 
	 * @Description: TODO 检查jarname是否存在
	 * @param jarname 需要检查的jarname
	 * @return 是否存在: isExits 1 存在，0 不存在
	 */
	Map<String, Object> checkJarname(String jarname);
	
	/**
	 * 
	 * @Description: TODO 检查jarname是否存在，剔除当前ID所表示的对象
	 * @param jarname 需要检查的jarname
	 * @param id 当前ID
	 * @return 是否存在: isExits 1 存在，0 不存在
	 */
	Map<String, Object> checkJarname(String jarname, String id);
}

