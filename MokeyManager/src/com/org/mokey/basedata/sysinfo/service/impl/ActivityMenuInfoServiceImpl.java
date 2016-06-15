package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.ActivityBusinessZoneDao;
import com.org.mokey.basedata.sysinfo.dao.ActivityMenuInfoDao;
import com.org.mokey.basedata.sysinfo.service.ActivityBusinessZoneService;
import com.org.mokey.basedata.sysinfo.service.ActivityMenuInfoService;

public class ActivityMenuInfoServiceImpl implements
ActivityMenuInfoService {
	
	ActivityMenuInfoDao activityMenuInfoDao;

	public ActivityMenuInfoDao getActivityMenuInfoDao() {
		return activityMenuInfoDao;
	}


	public void setActivityMenuInfoDao(ActivityMenuInfoDao activityMenuInfoDao) {
		this.activityMenuInfoDao = activityMenuInfoDao;
	}


	@Override
	public Map<String, Object> GetList(String name, int start,
			int limit) {
		// TODO Auto-generated method stub
		Map map=activityMenuInfoDao.GetList(name, start, limit);
		return map;
	}
	
	
	@Override
	public Map<String, Object> findName() {
		// TODO Auto-generated method stub
		Map map=activityMenuInfoDao.findName();
		return map;
	}


	@Override
	public Map CheckName(String nameString, String id) {
		// TODO Auto-generated method stub
		Map map=activityMenuInfoDao.CheckName(nameString, id);
		return map;
	}


	@Override
	public void Delete(String id) {
		// TODO Auto-generated method stub
		activityMenuInfoDao.Delete(id);
	}


	@Override
	public void Save(Map map) {
		// TODO Auto-generated method stub
		activityMenuInfoDao.Save(map);
	}

}
