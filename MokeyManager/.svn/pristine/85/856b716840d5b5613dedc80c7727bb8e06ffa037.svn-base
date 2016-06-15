package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import com.org.mokey.basedata.sysinfo.service.HoldTypeInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class HoldTypeInfoServiceImpl implements HoldTypeInfoService {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })//*********************
	public Map getHoldtypeInfoListMap(String c_name,
			int start, int limit) {
		Map ret = new HashMap();
		
		StringBuffer sql = new StringBuffer("select count(*)  from T_BASE_HOLDTYPE_INFO ht where 1=1 ");
		List args = new ArrayList();
	
		if(StrUtils.isNotEmpty(c_name)){
			sql.append(" and ht.c_name like ? ");
			args.add("%"+c_name+"%");
		}
		
		int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		String sql1 = DaoUtil.addfy_oracle(sql, start, limit, args).toString().replace("count(*)", " * ");
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveHoldtypeInfo(Map saveMap) {
		        Object cid_old=saveMap.get("c_old_id");  
		    	String sql="select count(*) from T_BASE_HOLDTYPE_INFO where c_id="+cid_old;
			    int cn  =jdbcTemplate.queryForObject(sql, Integer.class);
			    saveMap.remove("c_old_id");
			    if(cn>0){
			    	Map wMap=new HashMap();
			    	wMap.put("c_id", cid_old);
			    	JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, wMap, "T_BASE_HOLDTYPE_INFO");
			    }else{
					JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_HOLDTYPE_INFO");
			    }
	}
	
	@Override
	public Map<String, Object> checkHoldtypeInfo(String name,String id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql = "select count(*) from T_BASE_HOLDTYPE_INFO where c_name=? and c_id<>?";
		List<Object> args = new ArrayList<Object>();
		args.add(name);
		args.add(id);
		int count = jdbcTemplate.queryForInt(sql, args.toArray());
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}
	
	@Override
	public Map<String, Object> checkHoldTypeID(String id) {
		// TODO Auto-generated method stub
		Map<String,Object> ret=new HashMap<String, Object>();
		String sql="select count(*) from T_BASE_HOLDTYPE_INFO where c_id=?";
		List<Object> args=new ArrayList<Object>();
		args.add(id);
		int count=jdbcTemplate.queryForInt(sql,args.toArray());
		if(count>0){
			ret.put("isExits", 1);
		}else {
			ret.put("isExits", 0);
		}
		return ret;
	}
	
	public void deleteHoldtypeInfo(String c_id) {
		String sql = "delete from  T_BASE_HOLDTYPE_INFO where C_ID=?";
		jdbcTemplate.update(sql, new Object[]{c_id});
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
