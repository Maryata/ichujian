/**
 * 
 */
package com.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * @author vpc
 */
@SuppressWarnings("deprecation")
public class UsernotifyService extends SqlMapClientDaoSupport implements
		IUsernotifyService {
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger( UsernotifyService.class );

	/*
	 * (non-Javadoc)
	 * @see com.sys.service.IUsernotifyService#usernotify(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, String> usernotify(String regid, String nickname,
			String logintype, String phonenum, String sex, String age,
			String headimage, String password, String imei) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		String[] keys = new String[] {"regid","nickname","logintype","phonenum","sex","age","headimage","password","imei"};
		Integer r = 0;
		String[] values = new String[]{regid,nickname,logintype,phonenum,sex,age,headimage,password,imei};
		for ( int i = 0; i < values.length; ++i ) {
			parameterObject.put( keys[i], values[i] == null ? "" : values[i] );
		}
		parameterObject.put( "r", r );
		
		getSqlMapClientTemplate().queryForObject( "usernotify.procUsernotify", parameterObject );
		Object result = parameterObject.get( "r" );
		
		if ( log.isDebugEnabled() ) {
			log.debug( "result : " + result );
		}
		
		if( log.isInfoEnabled() ) {
			log.info( "result : " + result );
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put( "code", result.toString() );

		return m;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
