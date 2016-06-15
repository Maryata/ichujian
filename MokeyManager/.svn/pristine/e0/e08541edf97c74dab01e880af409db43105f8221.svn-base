package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.ActivityBusinessZoneDao;
import com.org.mokey.basedata.sysinfo.dao.ActivityContentTypeDao;
import com.org.mokey.basedata.sysinfo.dao.ActivityMenuInfoDao;
import com.org.mokey.basedata.sysinfo.service.ActivityBusinessZoneService;
import com.org.mokey.basedata.sysinfo.service.ActivityContentTypeService;
import com.org.mokey.basedata.sysinfo.service.ActivityMenuInfoService;

public class ActivityContentTypeServiceImpl implements
ActivityContentTypeService {
	
	ActivityContentTypeDao activityContentTypeDao;

	public ActivityContentTypeDao getActivityContentTypeDao() {
		return activityContentTypeDao;
	}


	public void setActivityContentTypeDao(
			ActivityContentTypeDao activityContentTypeDao) {
		this.activityContentTypeDao = activityContentTypeDao;
	}


	@Override
	public Map<String, Object> GetList(String name, int start,
			int limit) {
		// TODO Auto-generated method stub
		Map map=activityContentTypeDao.GetList(name, start, limit);
		return map;
	}
	
	
	@Override
	public Map<String, Object> findName() {
		// TODO Auto-generated method stub
		Map map=activityContentTypeDao.findName();
		return map;
	}


	@Override
	public Map CheckName(String nameString, String id) {
		// TODO Auto-generated method stub
		Map map=activityContentTypeDao.CheckName(nameString, id);
		return map;
	}


	@Override
	public void Delete(String id) {
		// TODO Auto-generated method stub
		activityContentTypeDao.Delete(id);
	}


	@Override
	public void Save(Map map) {
		// TODO Auto-generated method stub
		activityContentTypeDao.Save(map);
	}

}
