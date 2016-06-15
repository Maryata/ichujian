package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import com.org.mokey.basedata.baseinfo.service.ActionDeviceInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionDeviceInfoServiceImpl implements ActionDeviceInfoService {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getDeviceInfoListMap(String time_s, String time_e,
			int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select count(*) from T_BASE_DEVICE dev where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		if(StrUtils.isNotEmpty(time_s)){
			sql.append(" and dev.C_SYSDATE >= to_date(?,'yyyy-mm-dd') ");
			args.add(time_s);
		}
		if(StrUtils.isNotEmpty(time_e)){
			sql.append(" and dev.C_SYSDATE < to_date(?,'yyyy-mm-dd')+1 ");
			args.add(time_e);
		}
		int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		String sql1 = DaoUtil.addfy_oracle(sql, " dev.C_SYSDATE desc ", start, limit, args).toString().replace("count(*)", "  *  ");
		List list = jdbcTemplate.queryForList(sql1,args.toArray());///*********************
		
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
