package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.TestingMachineManagementDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

public class TestingMachineManagementDaoImpl implements TestingMachineManagementDao {
	
	private  JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Map<String, Object> getTestingMachineList(String phonename,
			String username, int start, int limit) {
		StringBuffer sql=new StringBuffer("SELECT count(1) FROM T_SYS_PHONE_RECORD T LEFT JOIN T_SYS_PHONE_IMEI T0 ON T.C_IMEI=T0.C_IMEI LEFT JOIN T_SYS_PHONE_USEREMAIL T1 ON T.C_UEMAIL = T1.C_EMAIL WHERE T.C_STATE=1 ");
		List<String> argsList = new ArrayList<String>();
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StrUtils.isNotEmpty(phonename)){
			sql.append(" and T0.C_PHONENAME like ?");
			argsList.add("%" + phonename + "%");
		}
		if(StrUtils.isNotEmpty(username)){
			sql.append(" and T1.C_UNAME like ?");
			argsList.add("%"+username+"%");
		}
		int count = jdbcTemplate.queryForObject( sql.toString(), argsList.toArray(), Integer.class );
		
		sql.append(" order by T.C_DATE DESC  ");
		String sql1=DaoUtil.addfy_oracle(sql, start, limit, argsList).toString().replace("count(1)", "T0.C_PHONENAME,T1.C_UNAME,T.C_DATE, T1.C_PHONENUM");
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql1, argsList.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

}
