package com.org.mokey.basedata.baseinfo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ActionBaseActivityInfoService {
	
	public  String  saveData(InputStream  is) throws IOException;
	
	/** 2015-6-11 修改：增加参数：islive（审核状态） */
	public Map getBaseActivityInfo(int start, int limit, String sTimeS, String sTimeE, String eTimeS, String eTimeE, String city, String industy, String property, String brand, String title, String islive);

	public Map getIndusties();

	public Map getBrands();
	 
	public  void deleteByID(String id);
	
	public Map getActDetail(String id);
	
	public String getNextActID();
	
	//public void save(Map<String, Object> map);

	public String saveActBaseIfo(Map<String, Object> saveMap);

	public Map<String, Object> getActBrandInfo(String brandId);


}
