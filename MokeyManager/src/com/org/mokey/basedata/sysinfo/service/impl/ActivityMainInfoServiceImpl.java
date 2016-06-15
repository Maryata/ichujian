package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.ActivityMainInfoDao;
import com.org.mokey.basedata.sysinfo.dao.SupplierDao;
import com.org.mokey.basedata.sysinfo.service.ActivityMainInfoService;
import com.org.mokey.basedata.sysinfo.service.SupplierService;

public class ActivityMainInfoServiceImpl implements ActivityMainInfoService {
	
	 private ActivityMainInfoDao activityMainInfoDao;

	public ActivityMainInfoDao getActivityMainInfoDao() {
		return activityMainInfoDao;
	}

	public void setActivityMainInfoDao(ActivityMainInfoDao activityMainInfoDao) {
		this.activityMainInfoDao = activityMainInfoDao;
	}

	@Override
	public Map<String, Object> findIndustry(String cityid,int start,int limit) {
		// TODO Auto-generated method stub
		Map<String,Object> map=activityMainInfoDao.findIndustry(cityid,start,limit);
		return map;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		activityMainInfoDao.delete(id);
	}

	@Override
	public Map<String, Object> getList(String city,
			int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map=activityMainInfoDao.getList(city, start, limit);
		return map;
	}

	@Override
	public void save(Map<String, Object>[] map,String cityid) {
		// TODO Auto-generated method stub
		activityMainInfoDao.save(map,cityid);
	}

 

	@Override
	public Map<String, Object> findcity() {
		// TODO Auto-generated method stub
		Map<String, Object> retMap=activityMainInfoDao.findcity();
		return retMap;
	}

	@Override
	public List getMain() {
		return activityMainInfoDao.getMain();
	}
	
}
