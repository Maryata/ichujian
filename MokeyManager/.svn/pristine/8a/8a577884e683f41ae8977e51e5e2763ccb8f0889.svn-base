package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.H5GameCategoryDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：h5游戏分类
 */
public class H5GameCategoryDaoImpl implements H5GameCategoryDao {

	private static final Logger LOGGER = Logger
			.getLogger(H5GameCategoryDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 获取游戏分类（下拉菜单）
	public List<Map<String, Object>> getGameCategoryList() {
		try {
			String sql = "SELECT C_ID, C_NAME FROM T_GAME_CATEGORY" +
					" WHERE C_ISLIVE = 1 AND C_TYPE = '04' ORDER BY C_ORDER";
			return jdbcTemplate.queryForList(sql);
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCategoryDaoImpl.getGameCategoryList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 分页显示游戏分类
	public Map<String, Object> gameGiftCateList(int page) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_CATEGORY WHERE C_ISLIVE = 1 AND C_TYPE = '04'"); 
			List<Object> argsList = new ArrayList<Object>();
			int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
			
			sql.append(" ORDER BY C_ORDER ");
			String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
					.toString().replace( "COUNT(1)", "C_ID,C_NAME,C_LOGO,C_ORDER,C_MODIFIER" );
			List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
			ret.put( "count", count );
			ret.put( "list", list );
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.gameGiftCateList failed, e : " + e);
		}
		return ret;
	}

	@Override
	// 新增游戏游戏分类
	public void addGameGiftCate(String id, String name, String logo,
			String order, String modifier) {
		try {
			String sql = "INSERT INTO T_GAME_CATEGORY" +
					" (C_ID,C_NAME,C_ISLIVE,C_LOGO,C_ORDER,C_MODIFIER,C_TYPE)" +
					" VALUES(?, ?, '1', ?, ?, ?, '04')";
			this.jdbcTemplate.update(sql,new Object[]{id,name,logo,order,modifier});
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.addGameGiftCate failed, e : " + e);
		}
	}

	@Override
	// 删除分类
	public void delGameGiftCate(String id) {
		try {
			String sql = "DELETE FROM T_GAME_CATEGORY WHERE C_ID = ?";
			this.jdbcTemplate.update(sql,new Object[]{id});
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.delGameGiftCate failed, e : " + e);
		}
	}

	@Override
	// 更新游戏游戏分类
	public void updateGameGiftCate(String id, String name, String logo,
			String order, String modifier) {
		try {
			String sql = "UPDATE T_GAME_CATEGORY SET C_NAME = ?, C_LOGO = ?," +
					" C_ORDER = ?, C_MODIFIER = ? WHERE C_ID = ?";
			this.jdbcTemplate.update(sql,new Object[]{name,logo,order,modifier,id});
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.delGameGiftCate failed, e : " + e);
		}
	}

	@Override
	// 查询非当前游戏分类的其他所有游戏
	public Map<String, Object> getAllGame(int page, String cid, String name,
			String gameCate) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_H5_INFO T WHERE T.C_ISLIVE = 1" +
					" AND NOT EXISTS" +
					" (SELECT C.C_GID FROM T_GAME_H5_APP_CATEGORY C WHERE" +
					" T.C_ID = C.C_GID AND C.C_CID = ?)"); 
			List<Object> argsList = new ArrayList<Object>();
			argsList.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			if(StrUtils.isNotEmpty(gameCate)){
				sql.append(" AND T.C_TYPE = ?");
				argsList.add(gameCate);
			}
			// 查询总数
			int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
			
			sql.append(" ORDER BY T.C_NAME ");
			String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
					.toString().replace( "COUNT(1)", "T.C_ID, T.C_NAME" );
			// 查询数据
			List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
			ret.put( "count", count );
			ret.put( "list", list );
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.getAllGame failed, e : " + e);
		}
		return ret;
	}

	@Override
	// 查询当前游戏分类的游戏
	public Map<String, Object> getCurrCateGame(int page, String cid, String name) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_H5_APP_CATEGORY T,T_GAME_H5_INFO G WHERE " +
					" G.C_ISLIVE = 1 AND T.C_GID = G.C_ID AND T.C_CID = ?"); 
			List<Object> argsList = new ArrayList<Object>();
			argsList.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND G.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			// 查询总数
			int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
			
			sql.append(" ORDER BY T.C_ORDER");
			String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
					.toString().replace( "COUNT(1)", "G.C_ID, G.C_NAME, T.C_ORDER " );
			// 查询数据
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
			ret.put( "count", count );
			ret.put( "list", list );
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.getCurrCateGift failed, e : " + e);
		}
		return ret;
	}

	@Override
	// 查询游戏是否已经存在于当前分类
	public int isExist(String cid, String gid) {
		try {
			String sql = "SELECT COUNT(1) FROM T_GAME_H5_APP_CATEGORY " +
					" WHERE C_CID = ? AND C_GID = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{cid, gid}, Integer.class);
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.isExist failed, e : " + e);
		}
		return 0;
	}

	@Override
	// 新增游戏到当前分类
	public void add(String cid, String gid, String order) {
		try {
			String sql = "INSERT INTO T_GAME_H5_APP_CATEGORY" +
					" (C_ID, C_GID, C_CID, C_ORDER) VALUES" +
					" (SEQ_GAME_H5_APP_CATEGORY.NEXTVAL,?,?,?)";
			jdbcTemplate.update(sql, new Object[]{gid,cid,order});
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.add failed, e : " + e);
		}
	}

	@Override
	// 更新当前分类中的游戏
	public void update(String cid, String gid, String order) {
		try {
			String sql = "UPDATE T_GAME_H5_APP_CATEGORY SET" +
					" C_ORDER = ? WHERE C_CID = ? AND C_GID = ?";
			jdbcTemplate.update(sql, new Object[]{order,cid,gid});
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.update failed, e : " + e);
		}
	}

	@Override
	// 将指定游戏移除出当前分类
	public void remove(String cid, String gid) {
		try {
			String sql = "DELETE FROM T_GAME_H5_APP_CATEGORY" +
					" WHERE C_CID = ? AND C_GID = ?";
			jdbcTemplate.update(sql, new Object[]{cid,gid});
		} catch (Exception e) {
			LOGGER.error("H5GameCategoryDaoImpl.remove failed, e : " + e);
		}
	}

}
