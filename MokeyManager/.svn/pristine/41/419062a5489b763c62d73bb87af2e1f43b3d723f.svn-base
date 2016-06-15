package com.org.mokey.analyse.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.analyse.dao.ActOnlineStatusDao;
import com.org.mokey.analyse.service.ActOnlineStatusService;

public class ActOnlineStatusServiceImpl implements ActOnlineStatusService {
	
	private ActOnlineStatusDao actOnlineStatusDao;

	public ActOnlineStatusDao getActOnlineStatusDao() {
		return actOnlineStatusDao;
	}

	public void setActOnlineStatusDao(ActOnlineStatusDao actOnlineStatusDao) {
		this.actOnlineStatusDao = actOnlineStatusDao;
	}

	@Override
	// 获取指定时间的活动上线情况
	public List<Map<String,Object>> actOnlineStatus(String onOrOff, String sDate, String eDate) {
		// 获取上线活动数量
		return this.actOnlineStatusDao.onlineOrOfflineAct(onOrOff, sDate, eDate);
	}
	
}
