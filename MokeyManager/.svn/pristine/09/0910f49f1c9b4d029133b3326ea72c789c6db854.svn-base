package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexAppDao;
import com.org.mokey.basedata.sysinfo.service.EKIndexAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EKIndexAppServiceImpl implements EKIndexAppService {

	@Autowired
	private EKIndexAppDao ekIndexAppDao;

	@Override
	public void delete(String id) {
		if( id == null || id.isEmpty() ) return;

		ekIndexAppDao.delete(id);
	}

	@Override
	public Map<String, Object> list(String name, int start, int limit,String isLive) {
		return ekIndexAppDao.list( name, start, limit, isLive );
	}

	@Override
	public void save(Map<String, Object> map) {
		ekIndexAppDao.save(map);
	}
}
