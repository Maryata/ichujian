package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActivitySchemeDetailService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActivitySchemeDetailServiceImpl implements
		ActivitySchemeDetailService {
	protected JdbcTemplate jdbcTemplate;
	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void deleteActivitySchemeDetail(String cId) {
		// TODO Auto-generated method stub
		String sql ="delete from T_ACTIVITY_FOLL_SCH_DETAIL t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[]{cId});
	}

	@Override
	public Map<String, Object> findBrandAllName() {
		// TODO Auto-generated method stub
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_cname from T_ACTIVITY_BRAND_INFO t ";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}

	@Override
	public Map<String, Object> findSchemeAllName() {
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_name from T_ACTIVITY_FOLLOW_SCHEME t ";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}

	@Override
	public Map<String, Object> getActivitySchemeDetailListMap(String c_schemeid,
			int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_FOLL_SCH_DETAIL t where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_schemeid)){
			sql.append(" and t.c_schemeid like ?");
			args.add("%"+c_schemeid+"%");
		}

		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_order ",start, limit, args).toString().replace("count(*)", "t.*,(select c_cname from T_ACTIVITY_BRAND_INFO t2 where t2.c_id=t.c_brandid ) as P_NAME,(select c_name from T_ACTIVITY_FOLLOW_SCHEME t2 where t2.c_id=t.c_schemeid ) as P_NAME1");
		
		List list = jdbcTemplate.queryForList(sql1, args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public String savaActivitySchemeDetail(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_SCHEME_DETAIL");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_FOLL_SCH_DETAIL");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_FOLL_SCH_DETAIL");
		}
		return seqId;
	}
	
	
	
}
