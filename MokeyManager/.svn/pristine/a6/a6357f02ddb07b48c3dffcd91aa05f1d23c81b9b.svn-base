package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

public interface PhoneBrandDao {

	void deleteBrandInfo(String c_id);

	String saveBrandInfo(Map<String, Object> saveMap);

	Map<String, Object> getBrandListMap(String c_name, int start, int limit);
	
	List<String> getModelByBrandId(String brandId);
	
	void deleteModel(String brandId,String modelCode);
	
	void saveModel(String brandId,String code);

}
