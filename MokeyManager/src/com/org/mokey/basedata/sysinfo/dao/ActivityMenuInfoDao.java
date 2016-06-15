package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

public interface ActivityMenuInfoDao {

	Map<String, Object> GetList(String name,int start, int limit);
	
	Map<String, Object> findName();
	
	void Delete(String id);
	
	Map CheckName(String nameString,String id);
	
	void Save(Map map);
}
