package com.org.mokey.system.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.system.dao.FunctionDao;
import com.org.mokey.util.StrUtils;

public class FunctionDaoImpl extends AbstractAction implements FunctionDao {

	private  JdbcTemplate jdbcTemplate;
	@Override
	public void DeleteFunction(String id) {
		// TODO Auto-generated method stub
		String sql="delete from T_SYS_FUNCTION where c_id=?";
		jdbcTemplate.update(sql,new Object[]{id});
	}

	@Override
	public Map<String, Object> GetFunctionList(String name, int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> ret=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_SYS_FUNCTION t where 1=1");
		List argsList=new ArrayList();
		if(StrUtils.isNotEmpty(name)){
			sql.append(" and t.C_NAME like ?");
			argsList.add("%"+name+"%");
		}
		int count=jdbcTemplate.queryForInt(sql.toString(), argsList.toArray());
		String sql1=DaoUtil.addfy_oracle(sql," c_order ", start, limit, argsList).toString().replace("count(*)", " t.*,(select c_name from T_SYS_FUNCTION t2 where t2.c_id=t.c_pid ) as P_NAME ");
		List list=jdbcTemplate.queryForList(sql1, argsList.toArray());
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}

	@Override
	public void SaveFunction(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Object id_old=map.get("c_id_old");
		String sql="select * from T_SYS_FUNCTION t where t.c_id="+id_old;
		List cn  =jdbcTemplate.queryForList(sql);
		map.remove("c_id_old");
		map.put("c_modity_time", new Date());
		if(cn.size()>0){
			Map wMap=new HashMap();
			wMap.put("c_id", id_old);
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap, "T_SYS_FUNCTION");
		}else {
			String sqlid=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_SYS_FUNCTION");
			map.put("c_id", sqlid);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map, "T_SYS_FUNCTION");
		}
	}

	@Override
	public Map<String, Object> CheckName(String name, String id) {
		// TODO Auto-generated method stub
		Map<String, Object> retMap=new HashMap<String, Object>();
		String sql="";int count=0;
		List<Object> argsList=new ArrayList<Object>();
		if(StrUtils.isNotEmpty(name)){
			if(StrUtils.isNotEmpty(id)){
				sql="select count(*) from T_SYS_FUNCTION where c_name=? and c_id<>?";
				argsList.add(name);
				argsList.add(id);
			}else{
				sql="select count(*) from T_SYS_FUNCTION where c_name=?";
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
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> findName() {
		// TODO Auto-generated method stub
		Map<String, Object>  retMap=new HashMap<String, Object>();
		String sqlString="select c_id,c_name from T_SYS_FUNCTION where c_is_menu=0 order by c_order";
		List list=jdbcTemplate.queryForList(sqlString);
		retMap.put("list", list);
		return retMap;
	}
}
