package com.org.mokey.basedata.baseinfo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionSearchRecardService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionSearchRecardServiceImpl implements ActionSearchRecardService {
	 private JdbcTemplate jdbcTemplate;
	 
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> checkActionSearchRecard(String c_id,
			String c_name, String value) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTIVITY_SEARCH_RECARD t where ").append(c_name).append("=?");//t.c_name=?
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
	public void deleteActionSearchRecard(String c_id) {
		String sql ="delete from T_ACTIVITY_SEARCH_RECARD t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[]{c_id});
	}

	@Override
	public Map<String, Object> getActionSearchRecardListMap(String time_s,
			String time_e) {
		Map<String, Object> ret = new HashMap<String, Object>(); 
		

		
//			String sql ="select  c_key,count(*) as c_count from (select c_key"+
//					" from T_ACTIVITY_SEARCH_RECARD "+
//					" where C_DATE >= to_date(?,'yyyy-mm-dd') and C_DATE < to_date(?,'yyyy-mm-dd')"+
//					") group by c_key order by count(*)desc";
		
        String sql ="select * "+
			        " from (select rownum,c_key,c_count"+
			        " from (select  c_key,count(*) as c_count"+
			        " from (select c_key "+
			        " from T_ACTIVITY_SEARCH_RECARD "+
		         	" where C_DATE >= to_date(?,'yyyy-mm-dd') and C_DATE < to_date(?,'yyyy-mm-dd')"+
			        ") group by c_key order by count(*) desc "+
			        ")"+
			        ") where rownum<=20";
		List list = jdbcTemplate.queryForList(sql, time_s,time_e);
		ret.put("list", list);
		return ret;
	}

	@Override
	public String savaActionSearchRecard(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_SEARCH_RECARD");
			saveMap.put("c_id", seqId);
			/*
			 * SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
String dateString = "20071128175545";
try {
Date date = df.parse(dateString);
System.out.println(df.format(date));
			 * */
			try {
				Date date =new SimpleDateFormat("yyyy-MM-dd").parse(saveMap.get("c_date").toString());
				saveMap.put("c_date", date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_SEARCH_RECARD");
		}else{
			seqId = (String) saveMap.get("c_id");
			try {
				Date date =new SimpleDateFormat("yyyy-MM-dd").parse(saveMap.get("c_date").toString());
				saveMap.put("c_date", date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_SEARCH_RECARD");
		}
		return seqId;
	}

}
