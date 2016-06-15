package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionCityInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionCityInfoServiceImpl implements ActionCityInfoService {
	private JdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> checkActionCityInfo(String c_id, String c_name,
			String value) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTIVITY_CITY_INFO t where ").append(c_name).append("=?");//t.c_name=?
		List<String> args = new ArrayList<String>();
		args.add(value);
		//过滤自己
		if(StrUtils.isNotEmpty(c_id)){
			sql.append(" and t.c_id <>? ");
			args.add(c_id);
		}
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}

	@Override
	public void deleteActionCityInfo(String c_id) {
		String sql ="delete from T_ACTIVITY_CITY_INFO t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[]{c_id});

	}

	@Override
	public Map<String, Object> getActionCityInfoListMap(String c_cname,
			int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_CITY_INFO t where 1=1 and t.c_level=2");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_cname)){
			sql.append(" and t.c_cname like ?");
			args.add("%"+c_cname+"%");
		}
		
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_order,t.c_parent_id ",start, limit, args).toString().replace("count(*)", "t.*,(select c_cname from T_ACTIVITY_CITY_INFO t2 where t2.c_id =t.c_parent_id) as P_NAME");
		List list = jdbcTemplate.queryForList(sql1, args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}

	@Override
	public String savaActionCityInfo(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_CITY_INFO");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_CITY_INFO");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_CITY_INFO");
		}
		return seqId;
	}
	//查询所有的城市名字
	@Override
	public Map<String, Object> findAllName() {
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql ="select c_cname ,c_id from T_ACTIVITY_CITY_INFO where c_level =1 ";
		List list = jdbcTemplate.queryForList(sql);
		//System.out.println(list.size());
		ret.put("list", list);
		return ret;
	}

}
