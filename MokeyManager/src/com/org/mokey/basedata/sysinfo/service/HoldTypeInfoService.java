package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

public interface HoldTypeInfoService {

	void deleteHoldtypeInfo(String c_id);

	void saveHoldtypeInfo(Map<String, Object> saveMap);

	Map<String, Object> getHoldtypeInfoListMap(String c_name,
			int start, int limit);

	Map<String, Object> checkHoldtypeInfo(String name,String id);
	
	Map<String, Object> checkHoldTypeID(String id);

}
