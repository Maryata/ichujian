package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActiveCodeService {

	Map<String, Object> getActiveListMap(String isActive, String code,
			String supplier, String isSample,String isRemark, int start, int limit);

	String saveActiveRemark(Map<String, Object> saveMap);
	
	public int findActive(String c_code);
	
	List<String> list(List<String> codeList);
	
	void batch(List<Map<String,Object>> list);
}
