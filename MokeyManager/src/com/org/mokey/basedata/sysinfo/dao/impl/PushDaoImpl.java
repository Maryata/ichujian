package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.PushDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class PushDaoImpl implements PushDao {

	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Map<String, Object> getPushInfoListMap(Map<String, Object> queryMap,
			int start, int limit) {
		Map<String,Object> ret = new HashMap<String,Object>();
		
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTION_PUSHINFO where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		/*if(StrUtils.isNotEmpty(c_category)){
			sql.append(" and c_category =? ");
			args.add(c_category);
		}*/
		
		int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		String sql1 = DaoUtil.addfy_oracle(sql, "C_ID", start, limit, args).toString().replace("count(*)", "*");
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql1, args));
		List<?> list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public String savePushInfo(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("C_ID"))){
			seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTION_PUSHINFO");
			saveMap.put("C_ID", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTION_PUSHINFO");
		}else{
			seqId = saveMap.get("C_ID")+"";
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("C_ID", saveMap.get("C_ID"));
			saveMap.remove("C_ID");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTION_PUSHINFO");
		}
		return seqId;
	}

}
