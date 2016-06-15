package com.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;

public class NewsServiceImpl extends SqlMapClientDaoSupport implements NewsService {
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(NewsServiceImpl.class));

	@Override
	public List GoodNews() {
		// TODO Auto-generated method stub
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.GoodNews");
		return list;
	}

	@Override
	public void Installed(String imei,String type,String package_name,String app_name,Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("package_name", package_name);
		map.put("app_name", app_name);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql1.Installed",map);
	}
	
	@Override
	public List NewsDetails(String id) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("id", id);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.NewsDetails",map);
		return list;
	}

	@Override
	public List GoodNews(String type, String category) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		List list=new ArrayList();
		map.put("type", type);
		map.put("category", category);
		if("0".equals(type)||"0"==type){
			list=getSqlMapClientTemplate().queryForList("soft_sql1.GoodNews1",map);	
		}else if("1".equals(type)||"1"==type){
			list=getSqlMapClientTemplate().queryForList("soft_sql1.GoodNews2",map);
		}else if("3".equals(type)||"3"==type){
			list=getSqlMapClientTemplate().queryForList("soft_sql1.GoodNews3",map);			
		}
		return list;
	}

	@Override
	public void DownApp(String imei, String type, String key,
			String packageName, String appName, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("key", key);
		map.put("package_name", packageName);
		map.put("app_name", appName);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql1.DownApp",map);
	}

	@Override
	public void UnloadApp(String imei, String type, String key,
			String packageName, String appName, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("key", key);
		map.put("package_name", packageName);
		map.put("app_name", appName);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql1.UnloadApp",map);
	}

	@Override
	public List Advert(String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("type", type);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.Advert",map);
		return list;
	}

	@Override
	public List findList(String imei, String type, String packageName,
			String appName) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("package_name", packageName);
		map.put("app_name", appName);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.findList",map);
		return list;
	}

	@Override
	public void updateList(String imei, String type, String packageName,
			String appName,Date actiondate,String id) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("type", type);
		map.put("package_name", packageName);
		map.put("app_name", appName);
		map.put("actiondate", actiondate);
		map.put("id", id);
		getSqlMapClientTemplate().update("soft_sql1.updateList",map);
	}

	@Override
	public void deleteAppInfo(String id) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("id", id);
		getSqlMapClientTemplate().delete("soft_sql1.deleteAppInfo",map);
	}

	@Override
	public List MainAdvert(String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("type", type);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.MainAdvert",map);
		return list;
	}
}
		
