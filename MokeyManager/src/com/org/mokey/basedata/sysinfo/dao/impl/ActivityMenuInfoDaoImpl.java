package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.ActivityBusinessZoneDao;
import com.org.mokey.basedata.sysinfo.dao.ActivityMenuInfoDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActivityMenuInfoDaoImpl implements
ActivityMenuInfoDao {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Map<String, Object> GetList(String name, int start,
			int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> ret=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_MENU_INFO t where t.c_islive=1 ");
		List argsList=new ArrayList();
		if(StrUtils.isNotEmpty(name)){
			sql.append(" and t.C_NAME like ?");
			argsList.add("%"+name+"%");
		}
		int count=jdbcTemplate.queryForInt(sql.toString(), argsList.toArray());
		String sql1=DaoUtil.addfy_oracle(sql," c_order ", start, limit, argsList).toString().replace("count(*)", " * ");
		List list=jdbcTemplate.queryForList(sql1, argsList.toArray());
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}
	
	@Override
	public Map<String, Object> findName() {
		// TODO Auto-generated method stub
		Map<String, Object>  retMap=new HashMap<String, Object>();
		String sqlString="select c_id,c_name from T_ACTIVITY_MENU_INFO where c_islive=1 order by c_order";
		List list=jdbcTemplate.queryForList(sqlString);
		retMap.put("list", list);
		return retMap;
	}

	@Override
	public Map CheckName(String name, String id) {
		// TODO Auto-generated method stub
		Map<String, Object> retMap=new HashMap<String, Object>();
		String sql="";int count=0;
		List<Object> argsList=new ArrayList<Object>();
		if(StrUtils.isNotEmpty(name)){
			if(StrUtils.isNotEmpty(id)){
				sql="select count(*) from T_ACTIVITY_MENU_INFO where c_name=? and c_id<>?";
				argsList.add(name);
				argsList.add(id);
			}else{
				sql="select count(*) from T_ACTIVITY_MENU_INFO where c_name=?";
				argsList.add(name);
			}
			count=jdbcTemplate.queryForInt(sql, argsList.toArray());	
		}
		
		if(count>0){
			retMap.put("isExits", 1);
		}else {
			retMap.put("isExits", 0);
		}
		return retMap;
	}

	@Override
	public void Delete(String id) {
		// TODO Auto-generated method stub
		String sql="update T_ACTIVITY_MENU_INFO set c_islive=0 where c_id=?";
		jdbcTemplate.update(sql,new Object[]{id});
	}

	@Override
	public void Save(Map map) {
		// TODO Auto-generated method stub
		Object id_old=map.get("c_id_old");
		String sql="select * from T_ACTIVITY_MENU_INFO t where t.c_id="+id_old;
		List cn  =jdbcTemplate.queryForList(sql);
		map.remove("c_id_old");
		if(cn.size()>0){
			Map wMap=new HashMap();
			wMap.put("c_id", id_old);
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap, "T_ACTIVITY_MENU_INFO");
		}else {
			String sqlid=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_T_ACTIVITY_MENU_INFO");
			map.put("c_id", sqlid);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map, "T_ACTIVITY_MENU_INFO");
		}
	}
	
	

}
