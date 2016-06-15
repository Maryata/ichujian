package com.org.mokey.analyse.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.org.mokey.analyse.dao.AKeyControlDao;
import com.org.mokey.analyse.entiy.AKeyControlBean;
import com.org.mokey.analyse.entiy.KeyUsageStatBean;
import com.org.mokey.common.util.DaoUtil;

public class AKeyControlDaoImpl implements AKeyControlDao {
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List getUseKeyMonthList(String startdate, String enddate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (")//
		.append(" select C_CLICKTYPE as CLICKTYPE,to_char(C_SYSDATE,'yyyy-mm') as DAY,count(*) COUNT ")
		.append(" from T_ACTION_USEKEY")
		.append(" where C_CLICKTYPE in(0,1,2) and C_SYSDATE>= to_date(?,'yyyy-mm') and C_SYSDATE< add_months(to_date(?,'yyyy-mm'),1)")
		.append(" group by C_CLICKTYPE,to_char(C_SYSDATE,'yyyy-mm')")
		.append(" ) temp order by DAY,CLICKTYPE");//
		
		Object [] args = new Object[]{startdate,enddate};
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql.toString(), args));
		return jdbcTemplate.query(sql.toString(),args ,new UseKeyRowMapper());//
	}
	
	private class UseKeyRowMapper implements RowMapper<AKeyControlBean> {  
	    public AKeyControlBean mapRow(ResultSet rs, int index) throws SQLException {  
	    	AKeyControlBean bean = new AKeyControlBean();  	  
	    	bean.setDay(rs.getString("DAY"));
	    	bean.setType(rs.getString("CLICKTYPE"));
	    	bean.setStartCn(rs.getInt("COUNT"));
	        return bean;  
	    }  
	}

	@Override
	public List getKeyUsageStatMonthList(String startdate, String enddate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (")//
		.append("select C_KEY as KEY, C_TYPE TYPE ,to_char(C_SYSDATE,'yyyy-mm') as DAY,count(*) COUNT ")
		.append(" from T_ACTION_USEKEY")
		.append(" where C_CLICKTYPE in(0,1,2) and C_SYSDATE>= to_date(?,'yyyy-mm') and C_SYSDATE< add_months(to_date(?,'yyyy-mm'),1)")
		.append(" group by C_KEY, C_TYPE,to_char(C_SYSDATE,'yyyy-mm')")
		.append(" ) temp order by DAY,KEY,TYPE");//
		
		Object [] args = new Object[]{startdate,enddate};
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql.toString(), args));
		return jdbcTemplate.query(sql.toString(),args ,new KeyUsageRowMapper());//
	}
	
	private class KeyUsageRowMapper implements RowMapper<KeyUsageStatBean> {  
	    public KeyUsageStatBean mapRow(ResultSet rs, int index) throws SQLException {  
	    	KeyUsageStatBean bean = new KeyUsageStatBean();  	  
	    	bean.setDay(rs.getString("DAY"));
	    	bean.setType(rs.getString("TYPE"));
	    	bean.setKey(rs.getString("KEY"));
	    	bean.setStartCn(rs.getInt("COUNT"));
	        return bean;  
	    }  
	}

	@Override
	public long getDeviceCount() {
		String sql = "SELECT count(*) FROM T_BASE_DEVICE";
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

}
