package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

public interface PushService {

	Map<String, Object> getPushInfoListMap(Map<String, Object> queryMap,
			int start, int limit);

	String savePushInfo(Map<String, Object> saveMap);

	String sendMessages(Map<String, Object> messageMap) throws Exception ;

}
