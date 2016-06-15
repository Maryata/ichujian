package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionFeedbackService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

public class ActionFeedbackServiceImpl implements ActionFeedbackService {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getFeedbackListMap(String time_s, String time_e,
			int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select count(feed.C_ID)  from T_ACTION_FEEDBACK feed left join T_BASE_DEVICE dev on feed.C_IMEI=dev.C_IMEI where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		if(StrUtils.isNotEmpty(time_s)){
			sql.append(" and feed.C_SYSDATE >= to_date(?,'yyyy-mm-dd') ");
			args.add(time_s);
		}
		if(StrUtils.isNotEmpty(time_e)){
			sql.append(" and feed.C_SYSDATE < to_date(?,'yyyy-mm-dd')+1 ");
			args.add(time_e);
		}
		int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		//,sta.C_SYSDATE as START_DATE 启动时间;  left join T_ACTION_START sta on act.C_IMEI=sta.C_IMEI 
		String sql1 = DaoUtil.addfy_oracle(sql, " feed.C_SYSDATE desc ", start, limit, args).toString().replace("count(feed.C_ID)", "  feed.C_ID,feed.C_IMEI,feed.C_CONTENT ,to_char(feed.C_SYSDATE,'yyyy-mm-dd') FEED_DAY,to_char(feed.C_SYSDATE,'hh24:mi') FEED_TIME,dev.C_BRAND  ");
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
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
