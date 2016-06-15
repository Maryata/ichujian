package com.org.mokey.analyse.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.analyse.dao.UserUseInfoDao;

public class UserUseInfoDaoImpl implements UserUseInfoDao {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List getUserUseByDayList(String startdate, String enddate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (")//
		.append(" select to_char(C_SYSDATE,'yyyy-mm-dd') as DAY,count(*) COUNT ")
		.append(" from T_ACTION_USEKEY")
		.append(" where C_SYSDATE>= to_date(?,'yyyy-mm-dd') and C_SYSDATE< to_date(?,'yyyy-mm-dd')+1 ")
		.append(" group by to_char(C_SYSDATE,'yyyy-mm-dd')")
		.append(" ) temp order by DAY");//
		
		Object [] args = new Object[]{startdate,enddate};
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql.toString(), args));
		return jdbcTemplate.queryForList(sql.toString(),args);//
	}
	
	@Override
	public List getUserUseByMonthList(String startdate, String enddate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (")//
		.append(" select to_char(C_SYSDATE,'yyyy-mm') as DAY,count(*) COUNT ")
		.append(" from T_ACTION_USEKEY")
		.append(" where C_SYSDATE>= to_date(?,'yyyy-mm') and C_SYSDATE< add_months(to_date(?,'yyyy-mm'),1) ")
		.append(" group by to_char(C_SYSDATE,'yyyy-mm')")
		.append(" ) temp order by DAY");//
		
		Object [] args = new Object[]{startdate,enddate};
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql.toString(), args));
		return jdbcTemplate.queryForList(sql.toString(),args);//
	}

}
