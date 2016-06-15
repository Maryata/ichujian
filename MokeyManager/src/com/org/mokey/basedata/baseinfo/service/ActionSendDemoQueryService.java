package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActionSendDemoQueryService {

	
     /**
      * 根据时间，提供商，是否是送样 查询 供应商的相关信息
      * @param timeS  开始时间
      * @param timeE  结束时间
      * @param supp 供应商
      * @param isSendDemo 是否是送样
      * @param start
      * @param limit
      * @return
      */
	public Map<String, Object> queryForSupplier(String timeS, String timeE,
			String supp, String isSendDemo, int start, int limit);
	@SuppressWarnings("unchecked")
	
	public List queryForOneMessage(String imei, String clickType);
	
	public Map<String, Object> getActiveListMap(String time_s, String time_e,
			int start, int limit,String imei,String code); 
} 