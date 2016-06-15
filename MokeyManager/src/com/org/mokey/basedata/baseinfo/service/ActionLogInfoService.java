package com.org.mokey.basedata.baseinfo.service;

import java.util.Map;

public interface ActionLogInfoService {

	Map<String, Object> getDeviceInfoListMap(String log,String imei,String time_s,String time_e,int start,int limit);
	
	void Updatedispose(String id,String remark);
}
