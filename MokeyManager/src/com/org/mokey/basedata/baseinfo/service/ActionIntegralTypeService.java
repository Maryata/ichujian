package com.org.mokey.basedata.baseinfo.service;

import java.util.Map;

public interface ActionIntegralTypeService {
	//查询（模糊查询）
	public Map<String, Object> getActionIntegralTypeListMap(String c_name,
			int start, int limit);
	//添加
	public String savaActionIntegralTypeInfo(Map<String, Object> saveMap);
	
	public void deleteActionIntegralTypeInfo(String c_id);
	
	public Map<String, Object> checkActionIntegralTypeInfo(String c_id,String c_name,String value);
}
