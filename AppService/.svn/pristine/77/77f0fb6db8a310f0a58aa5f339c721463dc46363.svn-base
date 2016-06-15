package com.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class WebActionServiceImpl extends SqlMapClientDaoSupport  implements WebActionService {

	private Logger log = (Logger.getLogger(WebActionServiceImpl.class));
	@Override
	public int getActivityInfo(String id) {
		Map map =new HashMap();
		map.put("id", id);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("web.getActivity", map);
		return i;
	}
	@Override
	public int getBrandInfo(String id) {
		Map map =new HashMap();
		map.put("id", id);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("web.getBrand", map);
		return i;
	}

}
