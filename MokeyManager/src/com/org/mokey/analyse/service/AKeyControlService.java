package com.org.mokey.analyse.service;

import java.util.Map;

public interface AKeyControlService {

	Map<String, Object> getUseKeyList(Map<String,String> keyMaps,String startdate, String enddate);

	Map<String, Object> getKeyUsageStatList(String startdate, String enddate);

}
