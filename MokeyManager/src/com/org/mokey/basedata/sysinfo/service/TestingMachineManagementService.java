package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

public interface TestingMachineManagementService {
	Map<String, Object> getTestingMachineList(String phonename,String username,int start,int limit);
}

