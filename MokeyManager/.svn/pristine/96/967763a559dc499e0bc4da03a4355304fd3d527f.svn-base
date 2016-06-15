package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.H5GameBoutiqueIndexDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：H5精品游戏首页排版
 */
public class H5GameBoutiqueIndexDaoImpl implements H5GameBoutiqueIndexDao {
	
	private static final Logger LOGGER = Logger.getLogger(H5GameBoutiqueIndexDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 查询首页显示的分类
	public Map<String, Object> indexCateList(String name, int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM" +
					" T_GAME_CUSTOM T, T_GAME_CATEGORY C WHERE C.C_ISLIVE = 1" +
					" AND T.C_TYPE = 2 AND C.C_TYPE = '05' AND T.C_CID = C.C_ID"); 
			List<Object> argsList = new ArrayList<Object>();
			
			if (StrUtils.isNotEmpty(name)) {
				sql.append(" AND C.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
			
			sql.append(" ORDER BY T.C_ORDER ");
			String sql1 = DaoUtil.addfy_oracle( sql, start, limit, argsList )
					.toString().replace( "COUNT(1)", "T.C_CID C_ID,C.C_NAME,T.C_NUMBER,T.C_ORDER" );
			List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
			ret.put( "count", count );
			ret.put( "list", list );
		} catch (Exception e) {
			LOGGER.error("H5GameBoutiqueIndexDaoImpl.indexCateList failed, e : " + e);
		}
		return ret;
	}

	@Override
	// 查询分类是否存在
	public Integer isExist(String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM" +
					" T_GAME_CUSTOM T WHERE T.C_TYPE = 2 AND T.C_CID = ?");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("H5GameBoutiqueIndexDaoImpl.isExist failed, e : " + e);
		}
		return null;
	}

	@Override
	// 更新分类
	public void update(String cid, String number, String order) {
		try {
			StringBuffer sql = new StringBuffer("UPDATE T_GAME_CUSTOM T" +
					" SET T.C_NUMBER = ?, T.C_ORDER = ? WHERE" +
					" T.C_TYPE = 2 AND T.C_CID = ?");
			List<Object> args = new ArrayList<Object>();
			args.add(number);
			args.add(order);
			args.add(cid);
			this.jdbcTemplate.update(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("H5GameBoutiqueIndexDaoImpl.update failed, e : " + e);
		}
	}

	@Override
	// 新增分类
	public void add(String cid, String number, String order) {
		try {
			StringBuffer sql = new StringBuffer("INSERT INTO" +
					" T_GAME_CUSTOM (C_ID, C_CID, C_TYPE, C_NUMBER, C_ORDER)" +
					" VALUES (SEQ_GAME_CUSTOM.NEXTVAL, ?, 2, ?, ?)");
			this.jdbcTemplate.update(sql.toString(), new Object[]{cid,number,order});
		} catch (Exception e) {
			LOGGER.error("H5GameBoutiqueIndexDaoImpl.add failed, e : " + e);
		}
	}

	@Override
	// 移除分类
	public void remove(String cid) {
		try {
			StringBuffer sql = new StringBuffer("DELETE FROM" +
					" T_GAME_CUSTOM T WHERE T.C_TYPE = 2 AND T.C_CID = ?");
			this.jdbcTemplate.update(sql.toString(), new Object[]{cid});
		} catch (Exception e) {
			LOGGER.error("H5GameBoutiqueIndexDaoImpl.remove failed, e : " + e);
		}
	}

	@Override
	// 分页查询首页中未显示的分类
	public Map<String, Object> cateList(int page, String name) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_CATEGORY C WHERE C.C_ISLIVE = 1 AND C.C_TYPE = '05'"); 
			List<Object> argsList = new ArrayList<Object>();
			if (StrUtils.isNotEmpty(name)) {
				sql.append(" AND C.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			sql.append(" AND NOT EXISTS(SELECT T.C_CID FROM" +
					" T_GAME_CUSTOM T WHERE T.C_TYPE = 2 AND T.C_CID = C.C_ID)");
			int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
			
			sql.append(" ORDER BY C.C_ORDER ");
			String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
					.toString().replace( "COUNT(1)", "C.C_ID, C.C_NAME" );
			List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
			ret.put( "count", count );
			ret.put( "list", list );
		} catch (Exception e) {
			LOGGER.error("H5GameBoutiqueIndexDaoImpl.cateList failed, e : " + e);
		}
		return ret;
	}

}
