package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.H5GameRecommendDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：H5游戏推荐
 */
public class H5GameRecommendDaoImpl implements H5GameRecommendDao {

	private static final Logger LOGGER = Logger
			.getLogger(H5GameRecommendDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 查询未作为推荐的H5游戏
	public Map<String, Object> getAllGame(int page, String name, String gameCate) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_H5_INFO T WHERE T.C_ISLIVE = 1 AND " +
					" NOT EXISTS (SELECT R.C_GID FROM T_GAME_APP_RECOMMEND R" +
					" WHERE T.C_ID = R.C_GID AND R.C_TYPE = 2)");
			List<Object> argsList = new ArrayList<Object>();
			if (StrUtils.isNotEmpty(name)) {
				sql.append(" AND T.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			if (StrUtils.isNotEmpty(gameCate)) {
				sql.append(" AND T.C_TYPE = ?");
				argsList.add(gameCate);
			}
			// 查询总数
			int count = jdbcTemplate.queryForObject(sql.toString(),
					argsList.toArray(), Integer.class);

			sql.append(" ORDER BY T.C_NAME ");
			String sql1 = DaoUtil
					.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString()
					.replace("COUNT(1)", "T.C_ID, T.C_NAME");
			// 查询数据
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,
					argsList.toArray());
			ret.put("count", count);
			ret.put("list", list);
		} catch (Exception e) {
			LOGGER.error("H5GameRecommendDaoImpl.getAllGame failed, e : "
					+ e);
		}
		return ret;
	}

	@Override
	// 查询推荐的H5游戏
	public Map<String, Object> recommendGameList(int page, String name) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_H5_INFO T, T_GAME_APP_RECOMMEND R WHERE" +
					" T.C_ISLIVE = 1 AND R.C_TYPE = 2 AND T.C_ID = R.C_GID"); 
			List<Object> argsList = new ArrayList<Object>();
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			// 查询总数
			int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);
			
			sql.append(" ORDER BY R.C_ORDER");
			String sql1 = DaoUtil.addfy_oracle( sql, (page-1)*5, 5, argsList )
					.toString().replace( "COUNT(1)", "T.C_ID, T.C_NAME, R.C_ORDER " );
			// 查询数据
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
			ret.put( "count", count );
			ret.put( "list", list );
		} catch (Exception e) {
			LOGGER.error("H5GameRecommendDaoImpl.recommendList failed, e : " + e);
		}
		return ret;
	}

	@Override
	// 查询H5游戏是否已经存在于当前分类
	public int isExist(String gid) {
		try {
			String sql = "SELECT COUNT(1) FROM T_GAME_APP_RECOMMEND WHERE C_GID = ? AND C_TYPE = 2";
			return jdbcTemplate.queryForObject(sql, new Object[]{gid}, Integer.class);
		} catch (Exception e) {
			LOGGER.error("H5GameRecommendDaoImpl.isExist failed, e : " + e);
		}
		return 0;
	}

	@Override
	// 新增H5游戏到当前分类
	public void add(String gid, String order) {
		try {
			String sql = "INSERT INTO T_GAME_APP_RECOMMEND" +
					" (C_ID, C_GID, C_TYPE, C_ORDER) VALUES" +
					" (SEQ_GAME_APP_RECOMMEND.NEXTVAL,?,2,?)";
			jdbcTemplate.update(sql, new Object[]{gid,order});
		} catch (Exception e) {
			LOGGER.error("H5GameRecommendDaoImpl.add failed, e : " + e);
		}
	}

	@Override
	// 更新当前分类中的H5游戏
	public void update(String gid, String order) {
		try {
			String sql = "UPDATE T_GAME_APP_RECOMMEND SET" +
					" C_ORDER = ? WHERE C_GID = ? AND C_TYPE = 2";
			jdbcTemplate.update(sql, new Object[]{order,gid});
		} catch (Exception e) {
			LOGGER.error("H5GameRecommendDaoImpl.update failed, e : " + e);
		}
	}

	@Override
	// 将指定H5游戏移除出当前分类
	public void remove(String gid) {
		try {
			String sql = "DELETE FROM T_GAME_APP_RECOMMEND WHERE C_GID = ? AND C_TYPE = 2";
			jdbcTemplate.update(sql, new Object[]{gid});
		} catch (Exception e) {
			LOGGER.error("H5GameRecommendDaoImpl.remove failed, e : " + e);
		}
	}

}
