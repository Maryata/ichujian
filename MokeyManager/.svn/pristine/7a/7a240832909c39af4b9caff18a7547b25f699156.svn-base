package com.org.mokey.analyse.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import com.org.mokey.analyse.dao.BrandfUserGrowthDao;

public class BrandUserGrowthDaoImpl implements BrandfUserGrowthDao{
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List getActiveDayList(String time_s, String time_e,String brand) {
		String sql = "select * from (select to_char(TS.C_SYSDATE,'yyyy-mm-dd') as DAY,count(*) COUNT from T_ACTION_ACTIVE ts,T_BASE_DEVICE ds" +
		" where TS.C_SYSDATE>= to_date(?,'yyyy-mm-dd') and TS.C_SYSDATE< to_date(?,'yyyy-mm-dd')+1 and TS.C_IMEI=DS.C_IMEI " +
		" and ds.C_MODEL in (select c_code from T_BASE_PHONE_MODEL where c_brand_id=?) group by to_char(TS.C_SYSDATE,'yyyy-mm-dd') ) temp order by DAY  ";
        Object [] args = new Object[]{time_s,time_e,brand};
		return jdbcTemplate.queryForList(sql, args);
	}
	
	@Override
	public List getActiveMonthList(String time_s, String time_e,String brand) {
		String sql = "select * from ( select to_char(ts.C_SYSDATE,'yyyy-mm') as DAY,count(*) COUNT from T_ACTION_ACTIVE ts,T_BASE_DEVICE ds" +
				" where ts.C_SYSDATE>= to_date(?,'yyyy-mm') and ts.C_SYSDATE< add_months(to_date(?,'yyyy-mm'),1) and ts.c_imei=ds.c_imei " +
				" and ds.c_model in (select c_code from T_BASE_PHONE_MODEL where c_brand_id=?) group by to_char(ts.C_SYSDATE,'yyyy-mm') ) temp order by DAY ";
		Object [] args = new Object[]{time_s,time_e,brand};
		return jdbcTemplate.queryForList(sql, args);
	}
	
	public List getStartDayList(String time_s, String time_e,String brand) {
		String sql = "select * from (select to_char(TS.C_SYSDATE,'yyyy-mm-dd') as DAY,count(*) COUNT from T_ACTION_START ts,T_BASE_DEVICE ds" +
				" where TS.C_SYSDATE>= to_date(?,'yyyy-mm-dd') and TS.C_SYSDATE< to_date(?,'yyyy-mm-dd')+1 and TS.C_IMEI=DS.C_IMEI " +
				" and ds.C_MODEL in (select c_code from T_BASE_PHONE_MODEL where c_brand_id=?) group by to_char(TS.C_SYSDATE,'yyyy-mm-dd') ) temp order by DAY  ";
		Object [] args = new Object[]{time_s,time_e,brand};
		return jdbcTemplate.queryForList(sql, args);
	}
	
	public List getStartMonthList(String time_s, String time_e,String brand) {
		String sql = "select * from (select to_char(ts.C_SYSDATE,'yyyy-mm') as DAY,count(*) COUNT from T_ACTION_START ts,T_BASE_DEVICE ds" +
				" where ts.C_SYSDATE>= to_date(?,'yyyy-mm') and ts.C_SYSDATE< add_months(to_date(?,'yyyy-mm'),1) and ts.c_imei=ds.c_imei " +
				" and ds.c_model in (select c_code from T_BASE_PHONE_MODEL where c_brand_id=?) group by to_char(ts.C_SYSDATE,'yyyy-mm') ) temp order by DAY  ";
		Object [] args = new Object[]{time_s,time_e,brand};
		return jdbcTemplate.queryForList(sql, args);
	}
	
	@Override
	public int getActiveCodeCount(String time_s, String time_e){
		return jdbcTemplate.queryForObject("select count(*) from T_BASE_ACTIVE_CODE where C_SYSDATE<to_date(?,'yyyy-mm-dd')+1 ",new Object[]{time_e}, Integer.class);
	}
	
	@Override
	public int getActiveCodeMonthCount(String time_s, String time_e){
		return jdbcTemplate.queryForObject("select count(*) from T_BASE_ACTIVE_CODE where C_SYSDATE<add_months(to_date(?,'yyyy-mm'),1) ",new Object[]{time_e}, Integer.class);
	}
	
	@Override
	public int getActiveCountAtBeforeTime(String time_e,String fomart) {
		return jdbcTemplate.queryForObject("select count(*) from T_ACTION_ACTIVE where C_SYSDATE<to_date(?,'"+fomart+"') ",new Object[]{time_e}, Integer.class);
	}
	@Override
	public List getBrand() {
		// TODO Auto-generated method stub
		String sql="select c_id,c_name from t_base_phone_brand order by C_ORDER";
		return jdbcTemplate.queryForList(sql);
	}
}
