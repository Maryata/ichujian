package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;


/**
 * 微用帮
 */
public interface McrAppService {

	/**
	 * 分页查询所有应用
	 * @param name 查询条件
	 * @param start 起始数
	 * @param limit 每页条数
	 * @return
	 */
	public Map<String, Object> list(String name, int start, int limit);

	/**
	 * 持久化应用信息
	 * @Description: TODO
	 * @param map 存储应用的map,key对应字段，value对应值
	 */
	void save(Map<String, Object> map);

	/**
	 * 根据ID删除应用信息
	 * @Description: TODO
	 * @param id 应用ID
	 */
	void delete(String id);
}
