package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.McrAppDao;
import com.org.mokey.basedata.sysinfo.service.McrAppService;

/**
 * 微用帮
 */
public class McrAppServiceImpl implements McrAppService {

	private McrAppDao mcrAppDao;
	
	public McrAppDao getMcrAppDao() {
		return mcrAppDao;
	}


	public void setMcrAppDao(McrAppDao mcrAppDao) {
		this.mcrAppDao = mcrAppDao;
	}


	@Override
	// 分页查询所有应用
	public Map<String, Object> list(String name, int start, int limit) {
		return mcrAppDao.list(name, start, limit);
	}


	@Override
	public void save(Map<String, Object> map) {
		mcrAppDao.save( map );
	}


	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.sysinfo.service.McrAppService#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		if( id == null || id.isEmpty() ) return;
		
		mcrAppDao.delete( id );
	}

}
