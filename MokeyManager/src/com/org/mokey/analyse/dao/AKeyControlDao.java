package com.org.mokey.analyse.dao;

import java.util.List;

public interface AKeyControlDao {

	List getUseKeyMonthList(String startdate, String enddate);

	List getKeyUsageStatMonthList(String startdate,String enddate);
	
	long getDeviceCount();

}
