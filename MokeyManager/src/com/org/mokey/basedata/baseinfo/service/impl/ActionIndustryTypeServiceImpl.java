package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.org.mokey.basedata.baseinfo.service.ActionIndustryTypeService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionIndustryTypeServiceImpl implements ActionIndustryTypeService {
	protected JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> getActionTypeListMap(String cName, int start,
			int limit) {
		Map<String, Object> ret= new HashMap<String, Object>();
		//1=1 and
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTIVITY_INDUSTRY_TYPE t where  t.c_id !=0");
		List args = new ArrayList();//存储条件
		
		if(StrUtils.isNotEmpty(cName)){
			sql.append(" and t.c_name like ?");
			args.add("%"+cName+"%");
		}
		//获得总条数
		int count =jdbcTemplate.queryForObject(sql.toString(), args.toArray(),Integer.class);
		//最总的查询语句
		String sql1 =DaoUtil.addfy_oracle(sql,"t.c_order", start, limit, args).toString().replace("count(*)", " t.*,(select c_name from T_ACTIVITY_INDUSTRY_TYPE t2 where t2.c_id=t.c_parid ) as P_NAME ");
		
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("list", list);
		ret.put("count", count);
 		// TODO Auto-generated method stub
		return ret;
	}

	@SuppressWarnings({  "unchecked" })//*********************
	public String savaActionTypeInfo(Map<String, Object> saveMap) {
		String seqId=null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_BASE_ACTIONTYPE");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_INDUSTRY_TYPE");
		}else{
			seqId= String.valueOf(saveMap.get("c_id"));
			Map wmap = new HashMap();
			wmap.put("c_id", saveMap.get("c_id"));
			saveMap.remove("c_id");
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, wmap, "T_ACTIVITY_INDUSTRY_TYPE");
		}
		return seqId;
	}
	
	//删除
	public void deleteActionTypeInfo(String c_id){
		String sql="delete from T_ACTIVITY_INDUSTRY_TYPE t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[] {c_id});	
	}


	//根据姓名和id查询
	public Map<String, Object> checkActionTypeInfo(String c_id ,String c_name){
		Map<String, Object> ret =new HashMap<String, Object>();
		StringBuffer sql =new StringBuffer("select count(*) from T_ACTIVITY_INDUSTRY_TYPE t where t.c_name=?");
		List<Object> args = new ArrayList<Object>();
		args.add(c_name);
		
		if(StrUtils.isNotEmpty(c_id)){
			sql.append(" and t.c_id <>? ");
			args.add(c_id);
		}
		//int count=jdbcTemplate.queryForInt(sql, args.toArray());
		int count =jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		
		System.out.println(count);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}		
		
		return ret;
	}

	@Override
	public Map<String, Object> findAllName() {
		// TODO Auto-generated method stub
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_name from T_ACTIVITY_INDUSTRY_TYPE t ";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}
}
