package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.McrAppIndexDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 微用帮：首页排版
 */
public class McrAppIndexDaoImpl implements McrAppIndexDao {
	
	private static final Logger LOGGER = Logger.getLogger(McrAppIndexDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 查询当前首页显示的合集
	public List<Map<String,Object>> currCol() {
		try {
			StringBuffer sql = new StringBuffer("SELECT T.C_CID CID," +
					" T.C_NUMBER CNT FROM T_MCRAPP_INDEX T WHERE T.C_TYPE = 0");
			return this.jdbcTemplate.queryForList(sql.toString());
		} catch (Exception e) {
			LOGGER.error("McrAppIndexDaoImpl.currCol failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询首页显示的分类
	public Map<String, Object> indexColList(String name, int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM" +
					" T_MCRAPP_INDEX T, T_MCRAPP_CATEGORY C WHERE C.C_ISLIVE = 1" +
					" AND T.C_TYPE = 1 AND T.C_CID = C.C_ID"); 
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
			LOGGER.error("McrAppIndexDaoImpl.indexColList failed, e : " + e);
		}
		return ret;
	}

	@Override
	// 查询合集/分类是否存在
	public Integer isExist(String type, String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM" +
					" T_MCRAPP_INDEX T WHERE T.C_TYPE = ? ");
			List<Object> args = new ArrayList<Object>();
			args.add(type);
			if("1".equals(type)){// 如果是分类
				sql.append(" AND T.C_CID = ?");
				args.add(cid);
			}
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppIndexDaoImpl.isExist failed, e : " + e);
		}
		return null;
	}

	@Override
	// 更新合集/分类
	public void update(String type, String cid, String number, String order) {
		try {
			StringBuffer sql = new StringBuffer("UPDATE T_MCRAPP_INDEX T" +
					" SET T.C_CID = ?, T.C_NUMBER = ?, T.C_ORDER = ? WHERE T.C_TYPE = ?");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			args.add(number);
			args.add(order);
			args.add(type);
			if("1".equals(type)){// 如果是分类
				sql.append(" AND T.C_CID = ?");
				args.add(cid);
			}
			this.jdbcTemplate.update(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppIndexDaoImpl.update failed, e : " + e);
		}
	}

	@Override
	// 新增合集/分类
	public void add(String type, String cid, String number, String order) {
		try {
			StringBuffer sql = new StringBuffer("INSERT INTO" +
					" T_MCRAPP_INDEX (C_ID, C_CID, C_TYPE, C_NUMBER, C_ORDER)" +
					" VALUES (SEQ_MCRAPP_INDEX.NEXTVAL, ?, ?, ?, ?)");
			this.jdbcTemplate.update(sql.toString(), new Object[]{cid,type,number,order});
		} catch (Exception e) {
			LOGGER.error("McrAppIndexDaoImpl.add failed, e : " + e);
		}
	}

	@Override
	// 移除合集/分类
	public void remove(String type, String cid) {
		try {
			StringBuffer sql = new StringBuffer("DELETE FROM" +
					" T_MCRAPP_INDEX T WHERE T.C_TYPE = ? AND T.C_CID = ?");
			this.jdbcTemplate.update(sql.toString(), new Object[]{type,cid});
		} catch (Exception e) {
			LOGGER.error("McrAppIndexDaoImpl.remove failed, e : " + e);
		}
	}

	@Override
	// 分页查询首页中未显示的分类
	public Map<String, Object> cateList(int page, String name) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_MCRAPP_CATEGORY C WHERE C.C_ISLIVE = 1"); 
			List<Object> argsList = new ArrayList<Object>();
			if (StrUtils.isNotEmpty(name)) {
				sql.append(" AND C.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			sql.append(" AND NOT EXISTS(SELECT T.C_CID FROM" +
					" T_MCRAPP_INDEX T WHERE T.C_TYPE = 1 AND T.C_CID = C.C_ID)");
			int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
			
			sql.append(" ORDER BY C.C_ORDER ");
			String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
					.toString().replace( "COUNT(1)", "C.C_ID,C.C_NAME" );
			List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
			ret.put( "count", count );
			ret.put( "list", list );
		} catch (Exception e) {
			LOGGER.error("McrAppIndexDaoImpl.cateList failed, e : " + e);
		}
		return ret;
	}

}
