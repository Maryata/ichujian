package com.org.mokey.analyse.dao;

import java.util.List;
import java.util.Map;

public interface UserUseInfoDao {

	List getUserUseByDayList(String startdate, String enddate);

	List getUserUseByMonthList(String startdate, String enddate);

}
