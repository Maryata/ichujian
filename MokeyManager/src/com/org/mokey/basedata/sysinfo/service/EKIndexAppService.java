package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;


public interface EKIndexAppService {

	/**
	 * @Description:  app列表
	 * @param name 查询参数，名称
	 *             @param isLive 是否有效
	 * @param start 
	 * @param limit 
	 * @return
	 */
	Map<String, Object> list(String name, int start, int limit, String isLive);

	/**
	 * @Description:  保存或者更新对象
	 * @param map 对象
	 */
	void save(Map<String, Object> map);

	/**
	 * @Description:  删除应用
	 * @param id 应用ID
	 */
	void delete(String id);
}

