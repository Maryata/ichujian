package com.org.mokey.basedata.baseinfo.service;

import java.util.Map;

public interface ActionPackageInfoService {
	//查询（模糊查询）
	public Map<String, Object> getActionPackageInfoListMap(String c_name,
			int start, int limit);
	//添加
	public String savaActionPackageInfo(Map<String, Object> saveMap);
	
	public void deleteActionPackageInfo(String c_id);
	
	public Map<String, Object> checkActionPackageInfo(String c_id,String c_name);
}
