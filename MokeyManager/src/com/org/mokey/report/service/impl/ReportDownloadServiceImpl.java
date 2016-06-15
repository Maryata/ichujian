package com.org.mokey.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.report.service.ReportDownloadService;
import com.org.mokey.util.StrUtils;

public class ReportDownloadServiceImpl implements ReportDownloadService {

	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Map<String,Object> getReportDownload(String time_s,String time_e,int start,int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_REPORT_DOWNLOAD rd where 1=1 and rd.c_islive=1 ");
		List<Object> args=new ArrayList<Object>();
		if(StrUtils.isNotEmpty(time_s)){
			sql.append(" and rd.c_actiondate>=to_date(?,'yyyy-mm-dd')");
			args.add(time_s);
		}
		if(StrUtils.isNotEmpty(time_e)){
			sql.append(" and rd.c_actiondate<to_date(?,'yyyy-mm-dd')");
			args.add(time_e);
		}
		int count=jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		String sql1=DaoUtil.addfy_oracle(sql, "rd.c_actiondate desc ", start, limit, args).toString().replace("count(*)", " * ");
        List list=jdbcTemplate.queryForList(sql1,args.toArray());
        map.put("count", count);
        map.put("list", list);
		return map;
	}

	@Override
	public void deleteReportDownload(String id) {
		// TODO Auto-generated method stub
		//log.info("--------"+id);
		String sql="delete from T_REPORT_DOWNLOAD t where t.c_id=?";
		jdbcTemplate.update(sql,new Object[]{id});
	}
	
	@Override
	public void saveReport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql_id= JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_REPOET_DOWNLOAD");
		map.put("c_id", sql_id);
		JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map, "T_REPORT_DOWNLOAD");
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
