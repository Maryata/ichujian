package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;


public interface AppInfoService {

	Map getAppIfoListMap(String c_category, String c_type, String c_name, int start, int limit);

	void deleteAppIfo(String c_id);

	String saveAppInfo(Map saveMap);

	List getAppIfoListByType(String c_category);
	
	// 获取游戏APP列表
	List getGameAppInfoList();

	Map<String, Object> checkAppIfo(String checkType, String value, String idVal);

	

}
