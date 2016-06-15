package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

public interface EKAdvertInfoService {

	void deleteAdvertIfo(String c_id);

	String saveAdvertInfo(Map<String, Object> saveMap);

	Map<String, Object> getAdvertIfoListMap(String c_type, String c_name,
											int start, int limit);

	Map<String, Object> checkAdvertIfo(String checkType, String value,
									   String idVal);

	public List<Map<String, Object>> getListByType(String type);

	/**
	 * @Description:  广告列表
	 * @param name 查询参数，名称
	 * @param start
	 * @param limit
	 * @return
	 */
	Map<String, Object> list(String name, int start, int limit);

	/**
	 * @Description:  保存或者更新对象
	 * @param map 对象
	 */
	void save(Map<String, Object> map);

	/**
	 * @Description:  删除广告
	 * @param id 广告ID
	 */
	void delete(String id);

	/**
	 * 合集列表
	 * @return 包含合集ID，名称的集合
     */
	Map<String,Object> categoryList();

	/**
	 * 活动列表
	 * @return 包含活动ID，名称的集合
     */
	Map<String,Object> activityList();
}
