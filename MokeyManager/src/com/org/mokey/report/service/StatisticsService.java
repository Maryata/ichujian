package com.org.mokey.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

	// 按键设置
	public Map<String, Object> keySet(String sDate, String eDate);

	public List<Map<String, Object>> updatedIds();
	
	/**
	 * 品牌用户
	 * @Description: TODO
	 * @return 品牌（品牌名称，库存、下载数、激活数) 对应键值：[C_SUPPLIER_NAME,C1,C2,C3]
	 */
	List<Map<String, Object>> brandUser();
	
	/**
	 * 一段时间内用户增长变化
	 * @Description: TODO
	 * @param sDate 开始时间
	 * @param eDate 结束时间
	 * @return 日期，下载数、激活数 对应键值 [D,C1,C2]
	 */
	List<Map<String, Object>> userGrowth(Date sDate, Date eDate);
	
	/**
	 * 一段时间内品牌激活情况
	 * @Description: TODO
	 * @param sDate 开始时间
	 * @param eDate 结束时间
	 * @return 品牌、日期、激活数 对应键值[C_SUPPLIER_NAME,D,C]
	 */
	List<Map<String, Object>> brandUserGrowth(Date sDate, Date eDate);
}
