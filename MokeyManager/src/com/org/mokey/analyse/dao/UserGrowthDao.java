package com.org.mokey.analyse.dao;

import java.util.List;

public interface UserGrowthDao {

	/**
	 * 激活列表
	 * @param time_s
	 * @param time_e
	 * @return
	 */
	List<?> getActiveDayList(String time_s, String time_e,String supplierCode);
	
	List<?> getActiveMonthList(String time_s, String time_e,String supplierCode);
	
	/**
	 * 启动列表
	 * @param time_s
	 * @param time_e
	 * @return
	 */
	List<?> getStartDayList(String time_s, String time_e,String supplierCode);
	
	List<?> getStartMonthList(String time_s, String time_e,String supplierCode);

	int getActiveCodeCount(String time_e,String supplierCode);
	
	int getActiveCodeMonthCount(String time_e, String supplierCode);

	int getActiveCountAtBeforeTime(String time_e,String fomart,String supplierCode);

}
