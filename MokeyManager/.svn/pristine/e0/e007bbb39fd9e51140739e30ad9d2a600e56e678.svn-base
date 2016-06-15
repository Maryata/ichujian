package com.org.mokey.report.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.report.dao.StatisticsDao;
import com.org.mokey.util.StrUtils;

/**
 * 后台数据统计指标分析
 */
public class StatisticsDaoImpl implements StatisticsDao {
	protected Logger log = (Logger.getLogger( getClass() ));
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 按键设置
	public Map<String, Object> statisticsDao(String sDate, String eDate) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 查询1、2号键设置数量排名前十的app
		String sql = "SELECT C_KEY, C_APP_NAME, CNT FROM (SELECT C_KEY, C_APP_NAME, CNT,"
				+ " ROW_NUMBER() OVER(PARTITION BY A.C_KEY ORDER BY A.CNT DESC) R FROM"
				+ " (SELECT C_KEY, C_APP_NAME, COUNT(C_APP_NAME) CNT FROM T_SET_CLICK_HIS T"
				+ " WHERE T.C_KEY IN (1, 2) AND T.C_ACTIONDATE >= TO_DATE(?, 'yyyy-MM-dd')"
				+ " AND T.C_ACTIONDATE < TO_DATE(?, 'yyyy-MM-dd') + 1 GROUP BY"
				+ " T.C_KEY, C_APP_NAME) A) WHERE R < 11";
		List<Object> args = new ArrayList<Object>();
		args.add( sDate );
		args.add( eDate );
		List<Map<String, Object>> list = jdbcTemplate.queryForList( sql,
				args.toArray() );
		// 一号键
		List<Map<String, Object>> first = new ArrayList<Map<String, Object>>(
				10 );
		// 二号键
		List<Map<String, Object>> second = new ArrayList<Map<String, Object>>(
				10 );
		if ( StrUtils.isNotEmpty( list ) ) {
			for ( int i = 0; i < list.size(); i++ ) {
				Map<String, Object> map = list.get( i );
				String key = map.get( "C_KEY" ).toString();
				if ( "1".equals( key ) ) {
					first.add( map );
				} else if ( "2".equals( key ) ) {
					second.add( map );
				}

			}
		}
		// 查询3、4号键（活动帮、游戏帮、微用帮）的设置排行
		sql = "SELECT C_KEY, C_APP_NAME, COUNT(T.C_APP_NAME) CNT FROM"
				+ " T_SET_CLICK_HIS T WHERE T.C_KEY IN (3,4) GROUP BY"
				+ " T.C_KEY,T.C_APP_NAME ORDER BY T.C_KEY,COUNT(T.C_APP_NAME) DESC";
		list = jdbcTemplate.queryForList( sql );
		// 三号键
		List<Map<String, Object>> third = new ArrayList<Map<String, Object>>(
				10 );
		// 四号键
		List<Map<String, Object>> fourth = new ArrayList<Map<String, Object>>(10);
		if(StrUtils.isNotEmpty(list)){
			for(int i=0; i<list.size(); i++){
				Map<String, Object> map = list.get(i);
				String key = map.get("C_KEY").toString();
				String cnt = map.get("CNT").toString();
				if("3".equals(key) && !"0".equals(cnt)){
					third.add(map);
				}else if("4".equals(key) && !"0".equals(cnt)){
					fourth.add(map);
				}

			}
		}
		reqMap.put( "first", first );
		reqMap.put( "second", second );
		reqMap.put( "third", third );
		reqMap.put( "fourth", fourth );
		return reqMap;
	}

	@Override
	public List<Map<String, Object>> updatedIds() {
		String sql = "SELECT T.C_ID FROM T_SET_CLICK_HIS T WHERE T.C_KEY = 3";
		return jdbcTemplate.queryForList( sql );
	}

	/*
	 * (non-Javadoc)
	 * @see com.org.mokey.report.dao.StatisticsDao#brandUser()
	 */
	@Override
	public List<Map<String, Object>> brandUser() {
		StringBuffer sql = new StringBuffer(
				"select tt.c,tt.c_supplier_name,tt.c1,tt1.c3,tt2.c2 " );
		sql.append( " from (select t.*, t1.c_supplier_name, t1.c_company " )
				.append(
						" from (select substr(c_code, 6, 2) as c, count(1) as c1 " )
				.append( " from T_BASE_ACTIVE_CODE " )
				.append( " where C_SYSDATE < sysdate + 1 " )
				.append( " group by substr(c_code, 6, 2)) t " )
				.append( " left join t_base_supplier t1 " )
				.append(
						" on t.c = t1.c_supplier_code where t1.c_is_potential_demand='0') tt " )
				.append(
						" left join (select substr(c_activecode, 6, 2) as c, count(1) as c3 " )
				.append( " from T_ACTION_ACTIVE " )
				.append( " where C_SYSDATE < sysdate + 1 " )
				.append( " group by substr(c_activecode, 6, 2)) tt1 " )
				.append( " on tt.c = tt1.c " )
				.append( " left join ( " )
				.append(
						" select substr(t1.c_activecode,6,2) as c, count(1) as c2 from (SELECT c_imei, min(c_sysdate) date1 " )
				.append( " from T_ACTION_START " )
				.append(
						" GROUP BY c_imei) t left join t_action_active t1 on t.c_imei=t1.c_imei " )
				.append( " where date1 < sysdate + 1 " )
				.append( " group by substr(t1.c_activecode,6,2) " )
				.append( " ) tt2 on " ).append( " tt.c = tt2.c " );

		return jdbcTemplate.queryForList( sql.toString() );
	}

	/*
	 * (non-Javadoc)
	 * @see com.org.mokey.report.dao.StatisticsDao#userGrowth(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> userGrowth(Date sDate, Date eDate) {
		StringBuffer sql = new StringBuffer(
				"select t.d,t.c2,tt.c1 from (select to_char(ta.c_sysdate,'yyyy-mm-dd') as d, count(1) as C2" );
		sql.append( " from T_ACTION_ACTIVE ta right join t_base_supplier ts on substr(ta.c_activecode, 6, 2) = ts.c_supplier_code" )
				.append( " where ta.C_SYSDATE < ? + 1 " )
				.append( " and  ta.C_SYSDATE >= ?  and ts.c_is_potential_demand='0'" )
				.append(
						" group by to_char(ta.c_sysdate,'yyyy-mm-dd') ) t left join ( " )
				.append(
						" select to_char(date1,'yyyy-MM-dd') as d,count(1) as C1 from (SELECT c_imei, min(c_sysdate) date1 " )
				.append( " from T_ACTION_START " )
				.append( " GROUP BY c_imei) " )
				.append( " where date1 < ? + 1 " )
				.append( " and date1 >= ? " )
				.append( " group by to_char(date1,'yyyy-MM-dd') " )
				.append( " ) tt on t.d=tt.d " ).append( " order by t.d " );

		return jdbcTemplate.queryForList( sql.toString(), new Object[]{eDate,sDate,eDate,sDate} );
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.org.mokey.report.dao.StatisticsDao#brandUserGrowth(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> brandUserGrowth(Date sDate, Date eDate) {
		StringBuffer sql = new StringBuffer(" select tt.c, tt.d, tt1.c_supplier_name,tt1.c_supplier_code ");
		sql.append( " from (select substr(t1.c_activecode, 6, 2) as s_code, " )
		.append( " count(1) as c, " )
		.append( " to_char(d, 'yyyy-MM-dd') as d " )
		.append( " from (SELECT c_imei, min(c_sysdate) d " )
		.append( " from T_ACTION_START " )
		.append( " GROUP BY c_imei) t " )
		.append( " left join t_action_active t1 " )
		.append( " on t.c_imei = t1.c_imei " )
		.append( " where d < ? + 1 " )
		.append( " and d >= ? " )
		.append( " group by substr(t1.c_activecode, 6, 2), to_char(d, 'yyyy-MM-dd')) tt " )
		.append( " right join t_base_supplier tt1 " )
		.append( " on tt.s_code = tt1.c_supplier_code where tt1.c_is_potential_demand='0' order by tt1.c_supplier_name" ); 
		
		return jdbcTemplate.queryForList( sql.toString(), new Object[]{eDate,sDate} );
	}

}
