package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

public interface TestingMachineManagementDao {
	Map<String, Object> getTestingMachineList(String phonename,
			String username, int start, int limit);
}
