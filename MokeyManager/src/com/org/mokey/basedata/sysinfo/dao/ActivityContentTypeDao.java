package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

public interface ActivityContentTypeDao {

	Map<String, Object> GetList(String name,int start, int limit);
	
	Map<String, Object> findName();
	
	void Delete(String id);
	
	Map CheckName(String nameString,String id);
	
	void Save(Map map);
}
