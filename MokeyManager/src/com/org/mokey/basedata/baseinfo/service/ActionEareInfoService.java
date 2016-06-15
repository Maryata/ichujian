package com.org.mokey.basedata.baseinfo.service;

import java.util.Map;

public interface ActionEareInfoService {
	//查询（模糊查询）
	public Map<String, Object> getActionEareInfoListMap(String c_name,
			int start, int limit);
	//添加
	public String savaActionEareInfo(Map<String, Object> saveMap);
	
	public void deleteActionEareInfo(String c_id);
	
	public Map<String, Object> checkActionEareInfo(String c_id,String c_name,String value);
	
	//验证编号
//	public Map<String, Object> checkActionEareInfoNumber(String number);
	
	//根据id查询
//	public Map<String, Object> checkActionEareInfoId(String c_id);
}
