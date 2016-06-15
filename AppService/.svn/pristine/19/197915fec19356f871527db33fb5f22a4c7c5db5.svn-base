package com.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;

public class SetServiceImpl extends SqlMapClientDaoSupport implements SetService {
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(SetServiceImpl.class));

	@Override
	public void Click(String imei, String clicktype, String name_package, Date actiondate ,String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("clicktype", clicktype);
		map.put("name_package", name_package);;
		map.put("actiondate", actiondate);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().insert("soft_sql1.Click",map);
	}

	@Override
	public void Hold(String imei,String holdtype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		getSqlMapClientTemplate().insert("soft_sql1.Hold",map);
	}
	
	@Override
	public void Hold(String imei,String holdtype,Date actiondate,String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		map.put("actiondate", actiondate);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().insert("soft_sql1.Hold1",map);
	}

	@Override
	public void Sos(String userid,String imei, String sostype, String behavior,
			String soscontent, String objective, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("userid", userid);
		map.put("imei", imei);
		map.put("sostype", sostype);
		map.put("behavior", behavior);
		map.put("soscontent", soscontent);
		map.put("objective", objective);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql.Sos",map);
	}

	@Override
	public List FindClick(String imei ,String clicktype,String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("clicktype", clicktype);
		map.put("key", key);
		map.put("choosetype", choosetype);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.FindClick",map);
		return list;
	}

	@Override
	public List FindHold(String imei,String holdtype,String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		map.put("key", key);
		map.put("choosetype", choosetype);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.FindHold",map);
		return list;
	}
	
	@Override
	public List FindHold(String imei,String holdtype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		List list=getSqlMapClientTemplate().queryForList("soft_sql1.FindHold1",map);
		return list;
	}

	@Override
	public List FindSos(String userid, String imei) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("userid", userid);
		map.put("imei", imei);
		List list=getSqlMapClientTemplate().queryForList("soft_sql.FindSos",map);
		return list;
	}

	@Override
	public void UpdateClick(String imei, String clicktype, String name_package, Date actiondate ,String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("clicktype", clicktype);
		map.put("name_package", name_package);
		map.put("actiondate", actiondate);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateClick",map);
	}

	@Override
	public void UpdateHold(String imei, String holdtype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateHold",map);
	}
	
	
	@Override
	public void UpdateHold(String imei, String holdtype,String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateHold1",map);
	}
	

	@Override
	public void UpdateSos(String userid,String imei, String sostype, String behavior,
			String soscontent, String objective, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("userid", userid);
		map.put("imei", imei);
		map.put("sostype", sostype);
		map.put("behavior", behavior);
		map.put("soscontent", soscontent);
		map.put("objective", objective);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().update("soft_sql.UpdateSos",map);
	}

	@Override
	public void UpdateOtherClick(String imei, String clicktype ,String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("clicktype", clicktype);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateOtherClick",map);
	}

	@Override
	public void UpdateOtherHold(String imei, String holdtype, String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateOtherHold",map);
	}

	@Override
	public void UpdateClickHis(String imei, String clicktype, String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("clicktype", clicktype);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateClickHis",map);
	}

	@Override
	public void ClickHis(String imei, String clicktype, String key,
			String packageName, String appName, Date actiondate,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("clicktype", clicktype);
		map.put("key", key);
		map.put("packageName", packageName);
		map.put("appName", appName);
		map.put("actiondate", actiondate);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().insert("soft_sql1.ClickHis",map);
	}

	@Override
	public void HoldHis(String imei, String holdtype, String key,Date actiondate,String choosetype,String appname,String apppackage) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		map.put("key", key);
		map.put("actiondate", actiondate);
		map.put("choosetype", choosetype);
		map.put("appname", appname);
		map.put("apppackage", apppackage);
		getSqlMapClientTemplate().insert("soft_sql1.HoldHis",map);
	}

	@Override
	public void UpdateHoldHis(String imei, String holdtype, String key,String choosetype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		map.put("key", key);
		map.put("choosetype", choosetype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateHoldHis",map);
	}

	@Override
	public void UpdateOtherHold(String imei, String holdtype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("holdtype", holdtype);
		getSqlMapClientTemplate().update("soft_sql1.UpdateOtherHold1",map);
	}
		
}
