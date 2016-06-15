package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.GameTaskDao;
import com.org.mokey.basedata.sysinfo.dao.MallProductDao;
import com.org.mokey.basedata.sysinfo.service.GameTaskService;
import com.org.mokey.basedata.sysinfo.service.MallProductService;

import java.util.Map;

public class MallProductServiceImpl implements MallProductService {

	private MallProductDao mallProductDao;

	@Override
	public void delete(String id) {
		if( id == null || id.isEmpty() ) return;
		
		mallProductDao.delete(id);
	}

	@Override
	public Map<String, Object> listGame (int start, int limit) {
		return mallProductDao.listGame( start, limit );
	}

	@Override
	public Map<String, Object> list(String name, int start, int limit) {
		return mallProductDao.list( name, start, limit );
	}

	@Override
	public void save(Map<String, Object> map) {
		mallProductDao.save(map);
	}

	public MallProductDao getMallProductDao () {
		return mallProductDao;
	}

	public void setMallProductDao (MallProductDao mallProductDao) {
		this.mallProductDao = mallProductDao;
	}
}
