package com.org.mokey.basedata.baseinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.dao.ActiveCodeDao;
import com.org.mokey.basedata.baseinfo.dao.ActiveCreateCodeDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActiveCreateCodeDaoImpl implements ActiveCreateCodeDao {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> getActiveListMap(int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select count(*) from T_BASE_CREATE_CODE c where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		String sql1 = DaoUtil.addfy_oracle(sql, start, limit, args).toString().replace("count(*)", " * ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}
	@Override
	public String saveActive(Map<String, Object> map) {
		
		String id = (String) map.get("C_ID");
		if(id==null){
			id=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_T_CREATE_CODE");
			map.put("c_id", id);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map, "T_BASE_CREATE_CODE");
		}else{
			Map<String,Object> wMap=new HashMap<String,Object>();
			wMap.put("C_ID", id);
			map.remove("C_ID");
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap, "T_BASE_CREATE_CODE");
		}
		return id;
	}
	@Override
	public int findActive(String cCode) {
		// TODO Auto-generated method stub
		
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql="select count(*) from T_BASE_CREATE_CODE t where t.C_CODE = ?";
		List args = new ArrayList();
		args.add(cCode);
		
		int count=jdbcTemplate.queryForObject(sql, args.toArray(), Integer.class);
		
		//ret.put("list", list);
		return count;
	}

}
