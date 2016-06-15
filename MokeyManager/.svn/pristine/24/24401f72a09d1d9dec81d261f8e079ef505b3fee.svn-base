package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActivityBrandInfoService {
	//查询（模糊查询）
	public Map<String, Object> getActivityBrandInfoListMap(String c_cname,String c_industryid,
		int start, int limit);
	//添加
	public String savaActivityBrandInfo(Map<String, Object> saveMap);
	
	public void deleteActivityBrandInfo(String c_id);
	
	public Map<String, Object> checkActivityBrandInfo(String c_id,String c_name,String value);
	
	public Map<String, Object> findAllName();
	//根据行业的id查询
	public List findById(String c_Id);
	
	public String savaActivityBrandSearchInfo(Map<String, Object> searchMap);
	
	public Map getBrandSearchDetail(String id);
	
}
