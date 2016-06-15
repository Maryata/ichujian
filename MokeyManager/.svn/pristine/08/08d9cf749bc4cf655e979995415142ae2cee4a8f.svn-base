package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;


public interface MessageService {

	/**
	 * @Description:  留言列表
	 * @param name 查询参数，游戏名称
	 * @param start 
	 * @param limit 
	 * @return
	 */
	Map<String, Object> list (String name, int start, int limit);

	/**
	 * @Description:  保存或者更新留言对象
	 * @param map H5对象
	 */
	void save (Map<String, Object> map);

	/**
	 * @Description:  删除留言
	 * @param id 留言ID
	 */
	void delete (String id);

	/**
	 * 用户的留言列表
	 * @param sUid 用户id
	 * @param start
	 * @param limit
	 * @return
	 */
	Map<String, Object> listByUid (String sUid, int start, int limit);
}

