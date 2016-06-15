package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.TestingMachineManagementDao;
import com.org.mokey.basedata.sysinfo.service.TestingMachineManagementService;

public class TestingMachineManagementServiceImpl implements TestingMachineManagementService {
	private TestingMachineManagementDao testingMachineManagementDao;
	
	@Override
	public Map<String, Object> getTestingMachineList(String phonename,
			String username, int start, int limit) {
		return testingMachineManagementDao.getTestingMachineList( phonename, username, start, limit );
	}

	public TestingMachineManagementDao getTestingMachineManagementDao() {
		return testingMachineManagementDao;
	}

	public void setTestingMachineManagementDao(
			TestingMachineManagementDao testingMachineManagementDao) {
		this.testingMachineManagementDao = testingMachineManagementDao;
	}
	
}
