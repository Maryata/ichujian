package com.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UseActivityServiceImpl extends SqlMapClientDaoSupport implements UseActivityService {

	@Override
	public void ChooseInfo(String regid, String type, String indexid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("type", type);
		map.put("indexid", indexid);
		map.put("actiondate", new Date());
		getSqlMapClientTemplate().insert("useactivity.ChooseInfo",map);
	}

	@Override
	public void SelectInfo(String regid, String type, String action,
			String indexid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("type", type);
		map.put("action", action);
		map.put("indexid", indexid);
		map.put("actiondate", new Date());
		getSqlMapClientTemplate().insert("useactivity.SelectInfo",map);
	}

}
