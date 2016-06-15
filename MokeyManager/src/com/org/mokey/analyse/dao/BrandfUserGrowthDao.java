package com.org.mokey.analyse.dao;

import java.util.List;

public interface BrandfUserGrowthDao {

	/**
	 * 激活列表
	 * @param time_s
	 * @param time_e
	 * @return
	 */
	List getActiveDayList(String time_s, String time_e,String brand);
	
	List getActiveMonthList(String time_s, String time_e,String brand);
	
	/**
	 * 启动列表
	 * @param time_s
	 * @param time_e
	 * @return
	 */
	List getStartDayList(String time_s, String time_e,String brand);
	
	List getStartMonthList(String time_s, String time_e,String brand);

	int getActiveCodeCount(String time_s, String time_e);
	
	int getActiveCodeMonthCount(String time_s, String time_e);

	int getActiveCountAtBeforeTime(String time_e,String fomart);

	List getBrand();
}
