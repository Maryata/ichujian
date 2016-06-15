package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.McrAppCateDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 微用帮：应用分类
 */
public class McrAppCateDaoImpl implements McrAppCateDao {
	
	private static final Logger LOGGER = Logger.getLogger(McrAppCateDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 新增分类
	public void mcrAppCateAdd(String id, String name, String logo,
			String order, String modifier) {
		try {
			String sql = "INSERT INTO T_MCRAPP_CATEGORY" +
					"(C_ID,C_NAME,C_ORDER,C_MODIFIER,C_LOGO)" +
					" VALUES (?,?,?,?,?)";
			this.jdbcTemplate.update(sql,new Object[]{id,name,order,modifier,logo});
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.mcrAppCateAdd failed, e : " + e);
		}
	}

	@Override
	// 删除分类
	public void mcrAppCateDel(String cid) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String sql = "DELETE FROM T_MCRAPP_CATEGORY WHERE C_ID = ?";
				this.jdbcTemplate.update(sql,new Object[]{cid});
			}
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.mcrAppCateDel failed, e : " + e);
		}
	}

	@Override
	// 更新分类
	public void update(String cid, String cname, String modifier, String logo, String order) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String sql = "UPDATE T_MCRAPP_CATEGORY SET C_NAME = ?" +
						", C_MODIFIER = ?, C_LAST_UPDATE = ?, C_ORDER = ?," +
						" C_LOGO = ? WHERE C_ID = ?";
				this.jdbcTemplate.update(sql,new Object[]{cname, modifier,
						new Date(), order, logo, cid});
			}
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.update failed, e : " + e);
		}
	}

	@Override
	// 查询所有应用总数
	public Integer getAppTotal(String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1 AND NOT EXISTS" +
					" (SELECT C.C_AID FROM T_MCRAPP_CATEGORY_MCRAPP C WHERE" +
					" C.C_ISLIVE = 1 AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getAppTotal failed, e : " + e);
		}
		return null;
	}
	
	@Override
	// 查询所有应用
	public List<Map<String, Object>> getAppList(int page, String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT T.C_ID,T.C_NAME" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1 AND" +
					" NOT EXISTS (SELECT C.C_AID FROM T_MCRAPP_CATEGORY_MCRAPP C" +
					" WHERE C.C_ISLIVE = 1 AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			DaoUtil.addfy_oracle(sql, " T.C_NAME ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getAppList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询当前分类的应用总数 
	public Integer getCateTotal(String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_INFO T, T_MCRAPP_CATEGORY_MCRAPP C" +
					" WHERE T.C_ISLIVE = 1 AND C.C_ISLIVE = 1 AND" +
					" T.C_ID = C.C_AID AND C.C_CID = ?");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getCateTotal failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询当前分类的应用 
	public List<Map<String, Object>> getCateList(int page, String cid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME, C.C_ORDER " +
					" FROM T_MCRAPP_INFO T, T_MCRAPP_CATEGORY_MCRAPP C" +
					" WHERE T.C_ISLIVE = 1 AND C.C_ISLIVE = 1 AND" +
					" T.C_ID = C.C_AID AND C.C_CID = ?");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			DaoUtil.addfy_oracle(sql, " C.C_ORDER ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getCateList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询应用数量
	public Integer getTotalCondition(String cid, String name, String appCate) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1");
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			if(StrUtils.isNotEmpty(appCate)){
				sql.append(" AND T.C_CATEGORY = ? ");
				args.add(appCate);
			}
			sql.append(" AND NOT EXISTS (SELECT C.C_AID FROM" +
					" T_MCRAPP_CATEGORY_MCRAPP C WHERE C.C_ISLIVE = 1" +
					" AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			args.add(cid);
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getTotalCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询应用
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name, String appCate) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME" +
					" FROM T_MCRAPP_INFO T WHERE T.C_ISLIVE = 1");
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			if(StrUtils.isNotEmpty(appCate)){
				sql.append(" AND T.C_CATEGORY = ? ");
				args.add(appCate);
			}
			sql.append(" AND NOT EXISTS (SELECT C.C_AID FROM" +
					" T_MCRAPP_CATEGORY_MCRAPP C WHERE C.C_ISLIVE = 1" +
					" AND T.C_ID = C.C_AID AND C.C_CID = ?)");
			args.add(cid);
			DaoUtil.addfy_oracle(sql, " T.C_NAME ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getListCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询当前分类的应用数量
	public Integer getCateTotalCondition(String cid, String name) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM T_MCRAPP_INFO T," +
					" T_MCRAPP_CATEGORY_MCRAPP C WHERE T.C_ISLIVE = 1 AND" +
					" C.C_ISLIVE = 1 AND T.C_ID = C.C_AID AND C.C_CID = ? ");
			args.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getTotalCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询当前分类的应用
	public List<Map<String, Object>> getCateListCondition(int page, String cid,
			String name) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME, C.C_ORDER" +
					" FROM T_MCRAPP_INFO T, T_MCRAPP_CATEGORY_MCRAPP C" +
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
			LOGGER.error("McrAppCateDaoImpl.getListCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询应用是否已经存在于当前分类中
	public int isExist(String cid, String aid) {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_CATEGORY_MCRAPP T WHERE T.C_ISLIVE = 1" +
					" AND T.C_CID = ? AND T.C_AID = ?");
			Object[] obj = {cid,aid};
			return this.jdbcTemplate.queryForObject(sql.toString(),obj,Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.isExist failed, e : " + e);
		}
		return 0;
	}

	@Override
	// 新增应用到分类中
	public void handleCate(String cid, String aid, String order) {
		try {
			StringBuffer sql = new StringBuffer("INSERT INTO T_MCRAPP_CATEGORY_MCRAPP T" +
					" (T.C_ID,T.C_AID,T.C_CID,T.C_ISLIVE,T.C_ORDER) " +
					" VALUES (SEQ_MCRAPP_CATEGORY_MCRAPP.NEXTVAL,?,?,1,?)");
			Object[] obj = {aid,cid,order};
			this.jdbcTemplate.update(sql.toString(),obj);
		} catch (DataAccessException e) {
			LOGGER.error("McrAppCateDaoImpl.handleCate failed, e : " + e);
		}
	}

	@Override
	// 更新应用在分类中的顺序
	public void updateOrder(String cid, String aid, String order) {
		try {
			StringBuffer sql = new StringBuffer("UPDATE T_MCRAPP_CATEGORY_MCRAPP T" +
					" SET T.C_ORDER = ? WHERE T.C_ISLIVE = 1 AND T.C_CID = ? AND T.C_AID = ?");
			Object[] obj = {order,cid,aid};
			this.jdbcTemplate.update(sql.toString(), obj);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.updateOrder failed, e : " + e);
		}
	}

	@Override
	// 将应用移出分类
	public void removeApp(String cid, String aid) {
		try {
			StringBuffer sql = new StringBuffer("DELETE FROM T_MCRAPP_CATEGORY_MCRAPP T" +
					" WHERE T.C_ISLIVE = 1 AND T.C_CID = ? AND T.C_AID = ?");
			Object[] obj = {cid,aid};
			this.jdbcTemplate.update(sql.toString(), obj);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.removeGame failed, e : " + e);
		}
	}

	@Override
	// 查询所有分类总数
	public Integer getAllCateTotal() {
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(*)" +
					" FROM T_MCRAPP_CATEGORY WHERE C_ISLIVE = 1");
			return this.jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getAllCateTotal failed, e : " + e);
		}
		return null;
	}

	@Override
	// 分页查询所有分类
	public List<Map<String, Object>> getAllCateList(int page) {
		return this.getAllCateList(page,null);
	}

	@Override
	// 分页查询所有分类（可有查询条件）
	public List<Map<String, Object>> getAllCateList(int page, String name) {
		try {
			ArrayList<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT C_ID, C_NAME," +
					" C_MODIFIER, C_ORDER, C_LOGO FROM T_MCRAPP_CATEGORY WHERE C_ISLIVE = 1");
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND C_NAME LIKE ?");
				args.add(name);
			}
			DaoUtil.addfy_oracle(sql, " C_ORDER ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(),args.toArray());
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.getAllCateList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询所有分类名称和id
	public List<Map<String, Object>> cateList() {
		try {
			StringBuffer sql = new StringBuffer("SELECT C_ID, C_NAME FROM T_MCRAPP_CATEGORY WHERE C_ISLIVE = 1");
			return this.jdbcTemplate.queryForList(sql.toString());
		} catch (Exception e) {
			LOGGER.error("McrAppCateDaoImpl.cateList failed, e : " + e);
		}
		return null;
	}


}
