package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

public interface ActivityBusinessZoneService {

	Map<String, Object> GetBusinessZoneList(String name,int start, int limit);
	
	Map<String, Object> findName();
	
	void Delete(String id);
	
	Map CheckName(String nameString,String id);
	
	void Save(Map map);
}
