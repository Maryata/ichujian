package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionEareInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionEareInfoServiceImpl implements ActionEareInfoService {
	protected JdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> checkActionEareInfo(String c_id,String c_name,String value) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTIVITY_EARE_INFO t where ").append(c_name).append("=?");//t.c_name=?
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

/*	@Override*/
/*	public Map<String, Object> checkActionEareInfoId(String c_id) {
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String sql ="select count(*) from T_BASE_EARE_INFO t where t.c_id =?";
		List args = new ArrayList();
		args.add(c_id);
		
		int count= jdbcTemplate.queryForObject(sql, args.toArray(), Integer.class);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}*/

	@Override
	public void deleteActionEareInfo(String c_id) {
		// TODO Auto-generated method stub
		String sql ="delete from T_ACTIVITY_EARE_INFO t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[]{c_id});
	}

	@Override
	public Map<String, Object> getActionEareInfoListMap(String c_name,
			int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_EARE_INFO t where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_name)){
			sql.append(" and t.c_name like ?");
			args.add("%"+c_name+"%");
		}
		
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_order ",start, limit, args).toString().replace("count(*)", "*");	
		List list = jdbcTemplate.queryForList(sql1, args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public String savaActionEareInfo(Map<String, Object> saveMap) {
		/*Object object =  saveMap.get("c_id");
		String sql ="select count(*) from T_BASE_EARE_INFO t where t.c_id =?";
		List args = new ArrayList();
		args.add(object);
		int count=jdbcTemplate.queryForObject(sql, args.toArray(),Integer.class);
		if(count>0){
			Map wmap = new HashMap();
			wmap.put("c_id", object);
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, wmap, "T_BASE_EARE_INFO");
		}else{
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_EARE_INFO");
		}*/

		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EARE_INFO");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_EARE_INFO");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_EARE_INFO");
		}
		return seqId;
		
	}


}
