package com.org.mokey.basedata.baseinfo.dao;

import java.util.List;
import java.util.Map;

public interface ActiveCodeDao {

	Map<String, Object> getActiveListMap(String isActive, String code,
			String supplier,String isSample, String isRemark, int start, int limit);

	String saveActiveRemark(Map<String, Object> saveMap);
	//验证重复的激活码
	public int findActive(String c_code);

	/**
	 * @Description: TODO
	 * @param list
	 */
	void batch(List<Map<String, Object>> list);

	/**
	 * @Description: TODO
	 * @param codeList
	 */
	List<String> list(List<String> codeList);
}
