package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionCanalDicInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionCanalDicInfoServiceImpl implements ActionCanalDicInfoService {
	protected JdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int checkActionCanalDicInfo(String cCanalid, String cName,String cId) {
		// TODO Auto-generated method stub
		//Map<String, Object> ret = new HashMap<String, Object>();
/*		StringBuffer sql = new StringBuffer("select count(*) from T_BASE_CANALDIC_INFO t where t.c_name =? ");//.append(cName).append("=?");//t.c_name=?
		List<String> args = new ArrayList<String>();
		args.add(cName);
		//过滤自己
		if(StrUtils.isNotEmpty(cId)){
			sql.append(" and t.c_id <>? ");
			args.add(cId);
		}
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}*/
		StringBuffer sql = new StringBuffer("select count(*) from T_BASE_CANALDIC_INFO t where t.c_canalid= ? and t.c_name =? ");//.append(cName).append("=?");//t.c_name=?
		List<String> args = new ArrayList<String>();
		args.add(cCanalid);
		args.add(cName);
		if(StrUtils.isNotEmpty(cId)){
			sql.append(" and t.c_id <>? ");
			args.add(cId);
		}
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		
		return count;
	}

	@Override
	public void deleteActionCanalDicInfo(String cId) {
		// TODO Auto-generated method stub
		String sql ="delete from T_BASE_CANALDIC_INFO t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[]{cId});
	}

	@Override
	public Map<String, Object> getActionCanalDicInfoListMap(String cName,
			int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_BASE_CANALDIC_INFO t where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(cName)){
			sql.append(" and t.c_name like ?");
			args.add("%"+cName+"%");
		}
		
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_order ",start, limit, args).toString().replace("count(*)", "*");
		
		List list = jdbcTemplate.queryForList(sql1, args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public String savaActionCanalDicInfo(Map<String, Object> saveMap) {
		// TODO Auto-generated method stub
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_BASE_CANALDIC_INFO");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_CANALDIC_INFO");
			
		}else{
			seqId = (String) saveMap.get("c_id");
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_BASE_CANALDIC_INFO");
		}
		return seqId;
	}

}
