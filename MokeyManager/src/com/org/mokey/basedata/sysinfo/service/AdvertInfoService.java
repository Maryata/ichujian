package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

public interface AdvertInfoService {

	void deleteAdvertIfo(String c_id);

	String saveAdvertInfo(Map<String, Object> saveMap);

	Map<String, Object> getAdvertIfoListMap(String c_type, String c_name,
			int start, int limit);

	Map<String, Object> checkAdvertIfo(String checkType, String value,
			String idVal);

	public List<Map<String, Object>> getListByType(String type);

}
