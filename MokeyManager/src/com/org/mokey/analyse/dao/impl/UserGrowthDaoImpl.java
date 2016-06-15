package com.org.mokey.analyse.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.analyse.dao.UserGrowthDao;
import com.org.mokey.util.StrUtils;

public class UserGrowthDaoImpl implements UserGrowthDao {
	protected Logger log = (Logger.getLogger( getClass() ));
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<?> getActiveDayList(String time_s, String time_e,
			String supplierCode) {
		StringBuffer sql = new StringBuffer(
				"select * from ( select to_char(C_SYSDATE,'yyyy-mm-dd') as DAY,count(*) COUNT from T_ACTION_ACTIVE where C_SYSDATE>= to_date(?,'yyyy-mm-dd') and C_SYSDATE< to_date(?,'yyyy-mm-dd')+1 " );
		List<Object> args = new ArrayList<Object>();
		args.add( time_s );
		args.add( time_e );
		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
			sql.append( " and instr(C_ACTIVECODE,?)=6  " );
			args.add( supplierCode );
		}
		sql.append( " group by to_char(C_SYSDATE,'yyyy-mm-dd') ) temp order by DAY " );
		return jdbcTemplate.queryForList( sql.toString(), args.toArray() );

	}

	@Override
	public List<?> getActiveMonthList(String time_s, String time_e,
			String supplierCode) {
		StringBuffer sql = new StringBuffer(
				"select * from ( select to_char(C_SYSDATE,'yyyy-mm') as DAY,count(*) COUNT from T_ACTION_ACTIVE where C_SYSDATE>= to_date(?,'yyyy-mm') and C_SYSDATE< add_months(to_date(?,'yyyy-mm'),1) " );
		List<Object> args = new ArrayList<Object>();
		args.add( time_s );
		args.add( time_e );
		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
			sql.append( " and instr(C_ACTIVECODE,?)=6  " );
			args.add( supplierCode );
		}
		sql.append( " group by to_char(C_SYSDATE,'yyyy-mm') ) temp order by DAY " );
		return jdbcTemplate.queryForList( sql.toString(), args.toArray() );
	}

	@Override
	public List<?> getStartDayList(String time_s, String time_e,
			String supplierCode) {
		// StringBuffer sql = new
		// StringBuffer("select * from ( select to_char(s.C_SYSDATE,'yyyy-mm-dd') as DAY,count(*) COUNT from T_ACTION_START s where s.C_SYSDATE>= to_date(?,'yyyy-mm-dd') and s.C_SYSDATE< to_date(?,'yyyy-mm-dd')+1 ");
		// List<Object> args = new ArrayList<Object>();
		// args.add(time_s);args.add(time_e);
		// if(StrUtils.isNotEmpty(supplierCode) && !"0".equals(supplierCode)){
		// //SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
		// sql.append(" and exists ( select 0 from T_ACTION_ACTIVE a where s.C_IMEI=a.C_IMEI and SUBSTR(a.C_ACTIVECODE,6,2)=? )");
		// args.add(supplierCode);
		// }
		// sql.append(" group by to_char(C_SYSDATE,'yyyy-mm-dd') ) temp order by DAY ");
		StringBuffer sql = new StringBuffer(
				" SELECT count(1) COUNT, to_char(date1,'yyyy-MM-dd') as DAY FROM (SELECT c_imei, min(c_sysdate) date1 from T_ACTION_START t where 1=1 " );
		List<Object> args = new ArrayList<Object>();
		args.add( time_e );
		args.add( time_s );
		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
			sql.append( " and exists ( select 0 from T_ACTION_ACTIVE a where t.C_IMEI=a.C_IMEI and SUBSTR(a.C_ACTIVECODE,6,2)=? )" );
			args.add( supplierCode );
		}
		sql.append( "  GROUP BY c_imei)  where date1 < to_date(?, 'yyyy-MM-dd') + 1 and date1 >= to_date(?, 'yyyy-MM-dd') group by to_char(date1,'yyyy-MM-dd') order by DAY desc " );
		return jdbcTemplate.queryForList( sql.toString(), args.toArray() );
	}

	@Override
	public List<?> getStartMonthList(String time_s, String time_e,
			String supplierCode) {
//		StringBuffer sql = new StringBuffer(
//				"select * from ( select to_char(s.C_SYSDATE,'yyyy-mm') as DAY,count(*) COUNT from T_ACTION_START s where s.C_SYSDATE>= to_date(?,'yyyy-mm') and s.C_SYSDATE< add_months(to_date(?,'yyyy-mm'),1) " );
//		List<Object> args = new ArrayList<Object>();
//		args.add( time_s );
//		args.add( time_e );
//		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
//			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
//			sql.append( " and exists ( select 0 from T_ACTION_ACTIVE a where s.C_IMEI=a.C_IMEI and SUBSTR(a.C_ACTIVECODE,6,2)=? )" );
//			args.add( supplierCode );
//		}
//		sql.append( " group by to_char(C_SYSDATE,'yyyy-mm') ) temp order by DAY " );
//		return jdbcTemplate.queryForList( sql.toString(), args.toArray() );
		StringBuffer sql = new StringBuffer(
				" SELECT count(1) COUNT, to_char(date1,'yyyy-MM') as DAY FROM (SELECT c_imei, min(c_sysdate) date1 from T_ACTION_START t  " );
		List<Object> args = new ArrayList<Object>();
		args.add( time_e );
		args.add( time_s );
		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
			sql.append( " and exists ( select 0 from T_ACTION_ACTIVE a where t.C_IMEI=a.C_IMEI and SUBSTR(a.C_ACTIVECODE,6,2)=? )" );
			args.add( supplierCode );
		}
		sql.append( "  GROUP BY c_imei)  where date1 < add_months(to_date(?,'yyyy-mm'),1) and date1 >= to_date(?, 'yyyy-MM') group by to_char(date1,'yyyy-MM') order by DAY desc " );
		return jdbcTemplate.queryForList( sql.toString(), args.toArray() );
	}

	@Override
	public int getActiveCodeCount(String time_e, String supplierCode) {
		StringBuffer sql = new StringBuffer(
				"select count(*) from T_BASE_ACTIVE_CODE where C_SYSDATE<to_date(?,'yyyy-mm-dd')+1 " );
		List<Object> args = new ArrayList<Object>();
		args.add( time_e );
		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
			sql.append( " and instr(C_CODE,?)=6  " );
			args.add( supplierCode );
		}
		return jdbcTemplate.queryForObject( sql.toString(), args.toArray(),
				Integer.class );
	}

	@Override
	public int getActiveCodeMonthCount(String time_e, String supplierCode) {
		StringBuffer sql = new StringBuffer(
				"select count(*) from T_BASE_ACTIVE_CODE where C_SYSDATE<add_months(to_date(?,'yyyy-mm'),1) " );
		List<Object> args = new ArrayList<Object>();
		args.add( time_e );
		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
			sql.append( " and instr(C_CODE,?)=6  " );
			args.add( supplierCode );
		}
		return jdbcTemplate.queryForObject( sql.toString(), args.toArray(),
				Integer.class );
	}

	@Override
	public int getActiveCountAtBeforeTime(String time_e, String fomart,
			String supplierCode) {
		StringBuffer sql = new StringBuffer(
				"select count(*) from T_ACTION_ACTIVE where C_SYSDATE<to_date(?,'"
						+ fomart + "') " );
		List<Object> args = new ArrayList<Object>();
		args.add( time_e );
		if ( StrUtils.isNotEmpty( supplierCode ) && !"0".equals( supplierCode ) ) {
			// SUBSTR(C_CODE,6,2)='01' ; instr(C_CODE,'01')=6
			sql.append( " and instr(C_ACTIVECODE,?)=6  " );
			args.add( supplierCode );
		}
		return jdbcTemplate.queryForObject( sql.toString(), args.toArray(),
				Integer.class );
	}
}
