package com.org.mokey.basedata.baseinfo.service;

import java.util.Map;

public interface ActionCityInfoService {
	//查询（模糊查询）
	public Map<String, Object> getActionCityInfoListMap(String c_cname,
			int start, int limit);
	//添加
	public String savaActionCityInfo(Map<String, Object> saveMap);
	
	public void deleteActionCityInfo(String c_id);
	
	public Map<String, Object> checkActionCityInfo(String c_id,String c_name,String value);
	
	//查询所有
	public Map<String, Object> findAllName();
}
