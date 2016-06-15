package com.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sys.util.ApDateTime;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UseServiceImpl extends SqlMapClientDaoSupport implements UseService {
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(UseServiceImpl.class));

	@Override
	public void UseApp(String imei,String type,String key,String clicktype,String package_name,
			String app_name,Date actiondate,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("key", key);
		map.put("clicktype", clicktype);
		map.put("package_name", package_name);
		map.put("app_name", app_name);
		map.put("actiondate", actiondate);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().insert("soft_sql1.UseApp",map);
	}

	@Override
	public void Usekey(String imei, String type, String key,Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("key", key);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql1.Usekey",map);
	}
	
	@Override
	public void Usekey(String imei,String type,String key,Date actiondate,String clicktype,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("key", key);
		map.put("actiondate", actiondate);
		map.put("clicktype", clicktype);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().insert("soft_sql1.Usekey1",map);
	}

	@Override
	public void UseCommon(String imei, String type, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql1.UseCommon",map);
	}

	@Override
	public List getSupplierUrl(String code) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("code", code);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.getSupplierUrl", map);
		return list;
	}

	@Override
	public String getHelpInfo(String num, String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("num", num);
		map.put("type", type);
		String list=(String) getSqlMapClientTemplate().queryForObject("soft_sql1.getHelpInfo", map);
		return list;
	}

	@Override
	public String ekHelpInfo(String sup, String type) {
		Map<String, Object> map = new HashMap();
		map.put("sup", sup);
		map.put("type", type);
		return (String) getSqlMapClientTemplate().queryForObject("soft_sql1.ekHelpInfo", map);
	}

	@Override
	// 查询用户使用1/2/3/4号键的次数
	public Integer countOfUsingKey(String uid, String key) {
		String date = "1970-01-01";
		try {
			date = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD, System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap();
		map.put("UID", uid);
		map.put("KEY", key);
		map.put("DATE", date);
		return (Integer) getSqlMapClientTemplate().queryForObject("soft_sql1.countOfUsingKey", map);
	}

}