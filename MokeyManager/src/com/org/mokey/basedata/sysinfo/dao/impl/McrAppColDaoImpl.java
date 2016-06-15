package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.McrAppColDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 微用帮：应用合集
 */
public class McrAppColDaoImpl implements McrAppColDao {
	
	private static final Logger LOGGER = Logger.getLogger(McrAppColDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 获取应用合集列表
	public List<Map<String, Object>> mcrAppColList() {
		try {
			String sql = "SELECT * FROM T_MCRAPP_COLLECTION_INFO ORDER BY C_ID";
			return this.jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.mcrAppColList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 新增合集
	public void mcrAppColAdd(String addName, String modifier) {
		try {
			if(StrUtils.isNotEmpty(addName)){
				String sql = "INSERT INTO T_MCRAPP_COLLECTION_INFO" +
						"(C_ID,C_NAME,C_MODIFIER,C_LAST_UPDATE)" +
						" VALUES (SEQ_MCRAPP_COLLECTION_INFO.NEXTVAL,?,?,?)";
				this.jdbcTemplate.update(sql,new Object[]{addName,modifier,new Date()});
			}
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.mcrAppColAdd failed, e : " + e);
		}
	}

	@Override
	// 删除合集
	public void mcrAppColDel(String cid) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String sql = "DELETE FROM T_MCRAPP_COLLECTION_INFO WHERE C_ID = ?";
				this.jdbcTemplate.update(sql,new Object[]{cid});
			}
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.mcrAppColDel failed, e : " + e);
		}
	}

	@Override
	// 更新合集
	public void update(String cid, String cname, String modifier) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String sql = "UPDATE T_MCRAPP_COLLECTION_INFO SET C_NAME = ?" +
						", C_MODIFIER = ?, C_LAST_UPDATE = ? WHERE C_ID = ?";
				this.jdbcTemplate.update(sql,new Object[]{cname, modifier, new Date(), cid});
			}
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.update failed, e : " + e);
		}
	}

	@Override
	// 查询所有应用总数
	public Integer getAppTotal(String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1 AND NOT EXISTS" +
					" (SELECT C.C_AID FROM T_MCRAPP_COLLECTION_MCRAPP C WHERE" +
					" C.C_ISLIVE = 1 AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getAppTotal failed, e : " + e);
		}
		return null;
	}
	
	@Override
	// 查询所有应用
	public List<Map<String, Object>> getAppList(int page, String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT T.C_ID,T.C_NAME" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1 AND" +
					" NOT EXISTS (SELECT C.C_AID FROM T_MCRAPP_COLLECTION_MCRAPP C" +
					" WHERE C.C_ISLIVE = 1 AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			DaoUtil.addfy_oracle(sql, " T.C_NAME ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getAppList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询当前合集的应用总数 
	public Integer getColTotal(String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_INFO T, T_MCRAPP_COLLECTION_MCRAPP C" +
					" WHERE T.C_ISLIVE = 1 AND C.C_ISLIVE = 1 AND" +
					" T.C_ID = C.C_AID AND C.C_CID = ?");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getColTotal failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询当前合集的应用 
	public List<Map<String, Object>> getColList(int page, String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME, C.C_ORDER " +
					" FROM T_MCRAPP_INFO T, T_MCRAPP_COLLECTION_MCRAPP C" +
					" WHERE T.C_ISLIVE = 1 AND C.C_ISLIVE = 1 AND" +
					" T.C_ID = C.C_AID AND C.C_CID = ?");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			DaoUtil.addfy_oracle(sql, " C.C_ORDER ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getColList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询应用数量
	public Integer getTotalCondition(String cid, String name) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1");
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			sql.append(" AND NOT EXISTS (SELECT C.C_AID FROM" +
					" T_MCRAPP_COLLECTION_MCRAPP C WHERE C.C_ISLIVE = 1" +
					" AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			args.add(cid);
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getTotalCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询应用
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1");
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			sql.append(" AND NOT EXISTS (SELECT C.C_AID FROM" +
					" T_MCRAPP_COLLECTION_MCRAPP C WHERE C.C_ISLIVE = 1" +
					" AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			args.add(cid);
			DaoUtil.addfy_oracle(sql, " T.C_NAME ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getListCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询当前合集的应用数量
	public Integer getColTotalCondition(String cid, String name) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM T_MCRAPP_INFO T," +
					" T_MCRAPP_COLLECTION_MCRAPP C WHERE T.C_ISLIVE = 1 AND" +
					" C.C_ISLIVE = 1 AND T.C_ID = C.C_AID AND C.C_CID = ? ");
			args.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getTotalCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询当前合集的应用
	public List<Map<String, Object>> getColListCondition(int page, String cid,
			String name) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME, C.C_ORDER" +
					" FROM T_MCRAPP_INFO T, T_MCRAPP_COLLECTION_MCRAPP C" +
					" WHERE T.C_ISLIVE = 1 AND C.C_ISLIVE = 1 AND T.C_ID = C.C_AID" +
					" AND C.C_CID = ? ");
			args.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			DaoUtil.addfy_oracle(sql, " C.C_ORDER ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.getListCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询应用是否已经存在于当前合集中
	public int isExist(String cid, String aid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_COLLECTION_MCRAPP T WHERE T.C_ISLIVE = 1" +
					" AND T.C_CID = ? AND T.C_AID = ?");
			Object[] obj = {cid,aid};
			return this.jdbcTemplate.queryForObject(sql.toString(),obj,Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.isExist failed, e : " + e);
		}
		return 0;
	}

	@Override
	// 新增应用到合集中
	public void handleCol(String cid, String aid, String order) {
		try {
			StringBuffer sql = new StringBuffer("INSERT INTO T_MCRAPP_COLLECTION_MCRAPP T" +
					" (T.C_ID,T.C_AID,T.C_CID,T.C_ISLIVE,T.C_ORDER) " +
					" VALUES (SEQ_MCRAPP_COLLECTION_MCRAPP.NEXTVAL,?,?,1,?)");
			Object[] obj = {aid,cid,order};
			this.jdbcTemplate.update(sql.toString(),obj);
		} catch (DataAccessException e) {
			LOGGER.error("McrAppColDaoImpl.handleCol failed, e : " + e);
		}
	}

	@Override
	// 更新应用在合集中的顺序
	public void updateOrder(String cid, String aid, String order) {
		try {
			StringBuffer sql = new StringBuffer("UPDATE T_MCRAPP_COLLECTION_MCRAPP T" +
					" SET T.C_ORDER = ? WHERE T.C_ISLIVE = 1 AND T.C_CID = ? AND T.C_AID = ?");
			Object[] obj = {order,cid,aid};
			this.jdbcTemplate.update(sql.toString(), obj);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.updateOrder failed, e : " + e);
		}
	}

	@Override
	// 将应用移出合集
	public void removeApp(String cid, String aid) {
		try {
			StringBuffer sql = new StringBuffer("DELETE FROM T_MCRAPP_COLLECTION_MCRAPP T" +
					" WHERE T.C_ISLIVE = 1 AND T.C_CID = ? AND T.C_AID = ?");
			Object[] obj = {cid,aid};
			this.jdbcTemplate.update(sql.toString(), obj);
		} catch (Exception e) {
			LOGGER.error("McrAppColDaoImpl.removeGame failed, e : " + e);
		}
	}


}
