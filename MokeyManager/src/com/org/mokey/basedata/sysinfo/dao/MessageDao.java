package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

public interface MessageDao {
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
	 * @param map 留言对象
	 */
	void save (Map<String, Object> map);

	/**
	 * @Description:  删除留言
	 * @param id 留言ID
	 */
	void delete (String id);

	/**
	 * 用户留言列表
	 * @param uid 用户id
	 * @param start
	 * @param limit
	 * @return
	 */
	Map<String,Object> listByUid (long uid, int start, int limit);
}
