package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.H5GameDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.task.ScheduledTasks;
import com.org.mokey.util.StrUtils;

public class H5GameDaoImpl implements H5GameDao {
	//private static final Logger log = Logger.getLogger( H5GameDaoImpl.class );
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> checkJarname(String jarname) { // save
		// check
		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "";
		List<Object> args = new ArrayList<Object>();
		int count = 0;
		
		if ( StrUtils.isNotEmpty( jarname ) ) {
			sql = "select count(1) from T_GAME_H5_INFO t where t.C_JARNAME=?";
			args.add( jarname );
		}
		
		if(!sql.isEmpty()) {
			count = jdbcTemplate.queryForObject( sql, args.toArray(), Integer.class );
		}
		if ( count >= 1 ) {
			retMap.put( "isExits", 1 );
		} else {
			retMap.put( "isExits", 0 );
		}
		
		return retMap;
	}

	@Override
	public Map<String, Object> checkJarname(String jarname, String id) { // save modify
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Object> args = new ArrayList<Object>();
		String sql = "";
		int count = 0;
		if ( StrUtils.isNotEmpty( jarname ) ) {
			sql = "select count(1) from T_GAME_H5_INFO where C_JARNAME=? and C_ID<>?";
			args.add( jarname );
			args.add( id );
		}
		
		if(!sql.isEmpty()) {
			count = jdbcTemplate.queryForObject( sql, args.toArray(), Integer.class );
		}
		
		if ( count >= 1 ) {
			retMap.put( "isExits", 1 );
		} else {
			retMap.put( "isExits", 0 );
		}
		return retMap;
	}

	@Override
	public void delete(String id) { // 物理删除
		String sql = "delete from  T_GAME_H5_INFO where C_ID=?";
		_deleteCollectionByGameId( id ); // 删除该游戏在合集中的记录
		jdbcTemplate.update( sql, new Object[] { id } );
	}
	
	private void _deleteCollectionByGameId(String id) {
		String sql = "delete from t_game_collection_h5_game where c_gid=?";
		jdbcTemplate.update( sql, new Object[] {id} );
		_scheduledTasks(); // 重新排序并生成文件
	}

	private void _scheduledTasks() {
		new Thread() {
			public void run() {
				ScheduledTasks st = new ScheduledTasks();
				st.sort();
				st.generator();
			}
		}.start();
	}

	@Override
	public Map<String, Object> list(String name,int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				"select count(1) from T_GAME_H5_INFO t where 1=1 " ); // t.C_ISLIVE = 1
		List<Object> argsList = new ArrayList<Object>();
		
		if ( StrUtils.isNotEmpty( name ) ) {
			sql.append( " and t.C_NAME like ?" );
			argsList.add( "%" + name + "%" );
		}
		int count = jdbcTemplate.queryForObject( sql.toString(), argsList
				.toArray(), Integer.class );
		
		sql.append( " order by t.C_PUBLISH_DATE desc  " );
		String sql1 = DaoUtil.addfy_oracle( sql, start, limit, argsList )
				.toString().replace( "count(1)", "*" );
		List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
		ret.put( "count", count );
		ret.put( "list", list );
		return ret;
	}

	@Override
	public void save(Map<String, Object> map) {
		Object id_old = map.get( "C_ID" );
		map.put( "C_PUBLISH_DATE", new Date() );
		if ( null != id_old && !id_old.toString().isEmpty() ) {
			String sql = "select count(1) from T_GAME_H5_INFO t where t.c_id=?";
			int count = jdbcTemplate.queryForObject( sql, new Object[]{id_old},Integer.class );
			
			if ( count >= 1 ) {
				Map<String, Object> wMap = new HashMap<String, Object>();
				wMap.put( "C_ID", id_old );
				JdbcTemplateUtils.updateDataByMap( jdbcTemplate, map, wMap,
						"T_GAME_H5_INFO" );
			} else {
				String sqlid = JdbcTemplateUtils.getSeqId( jdbcTemplate,
						"SEQ_GAME_H5_INFO" );
				map.put( "C_ID", sqlid );
				JdbcTemplateUtils.saveDataByMap( jdbcTemplate, map,
						"T_GAME_H5_INFO" );
			}
		} else {
			String sqlid = JdbcTemplateUtils.getSeqId( jdbcTemplate,
					"SEQ_GAME_H5_INFO" );
			map.put( "C_ID", sqlid );
			JdbcTemplateUtils.saveDataByMap( jdbcTemplate, map,
					"T_GAME_H5_INFO" );
		}
		_scheduledTasks();
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
