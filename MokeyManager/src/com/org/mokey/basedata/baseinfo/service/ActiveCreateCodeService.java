package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActiveCreateCodeService {

	Map<String, Object> getActiveListMap(int start, int limit);

	String saveActive(Map<String, Object> saveMap);
	
	public int findActive(String c_code);
	
}
