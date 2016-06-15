package com.org.mokey.analyse.service;

import java.util.List;
import java.util.Map;

/**
 * 活动上下线情况
 * @author Maryn
 *
 */
public interface ActOnlineStatusService {
	
	/**
	 * 获取指定时间的活动上下线情况
	 * @param onOrOff 上/下线
	 * @param sDate 起始时间
	 * @param eDate 截止时间
	 * @return
	 */
	public List<Map<String,Object>> actOnlineStatus(String onOrOff, String sDate, String eDate);
	
}
