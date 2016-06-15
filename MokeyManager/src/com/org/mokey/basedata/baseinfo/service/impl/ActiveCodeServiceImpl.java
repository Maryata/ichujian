package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.baseinfo.dao.ActiveCodeDao;
import com.org.mokey.basedata.baseinfo.service.ActiveCodeService;
import com.org.mokey.common.util.JdbcTemplateUtils;

public class ActiveCodeServiceImpl implements ActiveCodeService {
	
	private ActiveCodeDao activeCodeDao;
	
	public ActiveCodeDao getActiveCodeDao() {
		return activeCodeDao;
	}
	public void setActiveCodeDao(ActiveCodeDao activeCodeDao) {
		this.activeCodeDao = activeCodeDao;
	}
	@Override
	public Map<String, Object> getActiveListMap(String isActive, String code,
			String supplier,String isSample, String isRemark, int start, int limit) {
		return activeCodeDao.getActiveListMap(isActive, code,supplier,isSample,isRemark,start,limit);
	}
	@Override
	public String saveActiveRemark(Map<String, Object> saveMap) {
		return activeCodeDao.saveActiveRemark(saveMap);
	}
	@Override
	public int findActive(String cCode) {
		// TODO Auto-generated method stub
		return activeCodeDao.findActive(cCode);
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.baseinfo.service.ActiveCodeService#batch(java.util.List)
	 */
	@Override
	public void batch(List<Map<String, Object>> list) {
		activeCodeDao.batch(list);
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.baseinfo.service.ActiveCodeService#list(java.util.List)
	 */
	@Override
	public List<String> list(List<String> codeList) {
		return activeCodeDao.list(codeList);
	}

}
