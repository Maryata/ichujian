package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.ActivityBusinessZoneDao;
import com.org.mokey.basedata.sysinfo.service.ActivityBusinessZoneService;

public class ActivityBusinessZoneServiceImpl implements
		ActivityBusinessZoneService {
	
	ActivityBusinessZoneDao activityBusinessZoneDao;

	@Override
	public Map<String, Object> GetBusinessZoneList(String name, int start,
			int limit) {
		// TODO Auto-generated method stub
		Map map=activityBusinessZoneDao.GetBusinessZoneList(name, start, limit);
		return map;
	}
	
	
	@Override
	public Map<String, Object> findName() {
		// TODO Auto-generated method stub
		Map map=activityBusinessZoneDao.findName();
		return map;
	}
	

	public ActivityBusinessZoneDao getActivityBusinessZoneDao() {
		return activityBusinessZoneDao;
	}

	public void setActivityBusinessZoneDao(
			ActivityBusinessZoneDao activityBusinessZoneDao) {
		this.activityBusinessZoneDao = activityBusinessZoneDao;
	}


	@Override
	public Map CheckName(String nameString, String id) {
		// TODO Auto-generated method stub
		Map map=activityBusinessZoneDao.CheckName(nameString, id);
		return map;
	}


	@Override
	public void Delete(String id) {
		// TODO Auto-generated method stub
		activityBusinessZoneDao.Delete(id);
	}


	@Override
	public void Save(Map map) {
		// TODO Auto-generated method stub
		activityBusinessZoneDao.Save(map);
	}

}
