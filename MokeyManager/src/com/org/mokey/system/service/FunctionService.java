package com.org.mokey.system.service;

import java.util.Map;

public interface FunctionService {

	Map<String, Object> GetFunctionList(String name,int start,int limit);
	
	void SaveFunction(Map<String, Object> map);
	
	void DeleteFunction(String id);
	
	Map<String, Object> CheckName(String name,String id);
	
	Map<String, Object> findName();
}
