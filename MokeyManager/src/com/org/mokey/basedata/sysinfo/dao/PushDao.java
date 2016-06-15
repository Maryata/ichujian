package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

public interface PushDao {

	Map<String, Object> getPushInfoListMap(Map<String, Object> queryMap,
			int start, int limit);

	String savePushInfo(Map<String, Object> saveMap);

}
