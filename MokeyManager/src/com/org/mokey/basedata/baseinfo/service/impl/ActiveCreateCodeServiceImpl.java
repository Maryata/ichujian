package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.baseinfo.dao.ActiveCodeDao;
import com.org.mokey.basedata.baseinfo.dao.ActiveCreateCodeDao;
import com.org.mokey.basedata.baseinfo.service.ActiveCodeService;
import com.org.mokey.basedata.baseinfo.service.ActiveCreateCodeService;
import com.org.mokey.common.util.JdbcTemplateUtils;

public class ActiveCreateCodeServiceImpl implements ActiveCreateCodeService {
	
	private ActiveCreateCodeDao activeCreateCodeDao;
	
 
	public ActiveCreateCodeDao getActiveCreateCodeDao() {
		return activeCreateCodeDao;
	}
	public void setActiveCreateCodeDao(ActiveCreateCodeDao activeCreateCodeDao) {
		this.activeCreateCodeDao = activeCreateCodeDao;
	}
	
	@Override
	public Map<String, Object> getActiveListMap(int start, int limit) {
		return activeCreateCodeDao.getActiveListMap(start,limit);
	}
	@Override
	public String saveActive(Map<String, Object> saveMap) {
		return activeCreateCodeDao.saveActive(saveMap);
	}
	@Override
	public int findActive(String cCode) {
		// TODO Auto-generated method stub
		return activeCreateCodeDao.findActive(cCode);
	}

}
