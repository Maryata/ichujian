package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.l;

import org.apache.log4j.Logger;
import org.apache.struts2.views.xslt.ArrayAdapter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionLogInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

public class ActionLogInfoServiceImpl implements ActionLogInfoService {

	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;
	@Override
	public Map<String, Object> getDeviceInfoListMap(String logs,String imei,String timeS, String timeE,
			int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> ret=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer(" select count(*) from T_ACTION_APPLOG t where 1=1 ");
		List<String> agrsList=new ArrayList<String>();
		if(StrUtils.isNotEmpty(timeS)){
			sql.append(" and t.c_actiondate>=to_date(?,'yyyy-mm-dd') ");
			agrsList.add(timeS);
		}
		if(StrUtils.isNotEmpty(timeE)){
			sql.append(" and t.c_actiondate<to_date(?,'yyyy-mm-dd')+1 ");
			agrsList.add(timeE);
		}
		if(StrUtils.isNotEmpty(logs)){
			sql.append(" and t.c_log like ? " );
			agrsList.add("%"+logs+"%");
		}
		if(StrUtils.isNotEmpty(imei)){
			sql.append(" and t.c_imei like ? " );
			agrsList.add("%"+imei+"%");
		}
		int count=jdbcTemplate.queryForInt(sql.toString(),agrsList.toArray());
		String sql1=DaoUtil.addfy_oracle(sql,"  t.c_actiondate desc ", start, limit, agrsList).toString().replace("count(*)", " * ");
		List list=jdbcTemplate.queryForList(sql1,agrsList.toArray());
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

	@Override
	public void Updatedispose(String id, String remark) {
		// TODO Auto-generated method stub
		String sql = "update T_ACTION_APPLOG t set t.c_remark=? where t.c_id=?";
		jdbcTemplate.update(sql, new Object[]{remark,id});
	}
	
}
