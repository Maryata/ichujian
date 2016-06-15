package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

public interface ActivityMainInfoDao {
	Map<String, Object> getList(String city,int start,int limit);
	
    void delete(String id);
    
    void save(Map<String, Object>[] map,String cityid);
    
    Map<String, Object> findIndustry(String cityid,int start,int limit);
    
    Map<String, Object> findcity();

	List getMain();
}
