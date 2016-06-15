package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.GameGiftRecommendDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏礼包推荐
 */
public class GameGiftRecommendDaoImpl implements GameGiftRecommendDao {

	private static final Logger LOGGER = Logger
			.getLogger(GameGiftRecommendDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 查询未作为推荐的礼包
	public Map<String, Object> getAllGameGift(int page, String name,
			String giftCate) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_GIFTS_INFO T" +
					" WHERE T.C_ISLIVE = 1 AND SYSDATE < T.C_EDATE AND" +
					" NOT EXISTS (SELECT R.C_GID FROM T_GAME_GIFTS_RECOMMEND R" +
					" WHERE T.C_ID = R.C_GID)");
			List<Object> argsList = new ArrayList<Object>();
			if (StrUtils.isNotEmpty(name)) {
				sql.append(" AND T.C_NAME LIKE ?");
				argsList.add("%" + name + "%");
			}
			if (StrUtils.isNotEmpty(giftCate)) {
				sql.append(" AND T.C_TYPE = ?");
				argsList.add(giftCate);
			}
			// 查询总数
			int count = jdbcTemplate.queryForObject(sql.toString(),
					argsList.toArray(), Integer.class);

			sql.append(" ORDER BY T.C_ORDER ");
			String sql1 = DaoUtil
					.addfy_oracle(sql, (page - 1) * 5, 5, argsList).toString()
					.replace("COUNT(1)", "T.C_ID, T.C_NAME");
			// 查询数据
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,
					argsList.toArray());
			ret.put("count", count);
			ret.put("list", list);
		} catch (Exception e) {
			LOGGER.error("GameGiftRecommendDaoImpl.getAllGameGift failed, e : "
					+ e);
		}
		return ret;
	}

	@Override
	// 查询推荐的礼包
	public Map<String, Object> recommendGiftList(int page, String name) {
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
					" FROM T_GAME_GIFTS_INFO T, T_GAME_GIFTS_RECOMMEND R" +
					" WHERE T.C_ISLIVE = 1 AND T.C_ID = R.C_GID"); 
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
			LOGGER.error("GameGiftRecommendDaoImpl.recommendGiftList failed, e : " + e);
		}
		return ret;
	}

	@Override
	// 查询礼包是否已经存在于当前分类
	public int isExist(String gid) {
		try {
			String sql = "SELECT COUNT(1) FROM T_GAME_GIFTS_RECOMMEND WHERE C_GID = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{gid}, Integer.class);
		} catch (Exception e) {
			LOGGER.error("GameGiftRecommendDaoImpl.isExist failed, e : " + e);
		}
		return 0;
	}

	@Override
	// 新增礼包到当前分类
	public void add(String gid, String order) {
		try {
			String sql = "INSERT INTO T_GAME_GIFTS_RECOMMEND" +
					" (C_ID, C_GID, C_ORDER) VALUES" +
					" (SEQ_GAME_GIFTS_RECOMMEND.NEXTVAL,?,?)";
			jdbcTemplate.update(sql, new Object[]{gid,order});
		} catch (Exception e) {
			LOGGER.error("GameGiftRecommendDaoImpl.add failed, e : " + e);
		}
	}

	@Override
	// 更新当前分类中的礼包
	public void update(String gid, String order) {
		try {
			String sql = "UPDATE T_GAME_GIFTS_RECOMMEND SET" +
					" C_ORDER = ? WHERE C_GID = ?";
			jdbcTemplate.update(sql, new Object[]{order,gid});
		} catch (Exception e) {
			LOGGER.error("GameGiftRecommendDaoImpl.update failed, e : " + e);
		}
	}

	@Override
	// 将指定礼包移除出当前分类
	public void remove(String gid) {
		try {
			String sql = "DELETE FROM T_GAME_GIFTS_RECOMMEND WHERE C_GID = ?";
			jdbcTemplate.update(sql, new Object[]{gid});
		} catch (Exception e) {
			LOGGER.error("GameGiftRecommendDaoImpl.remove failed, e : " + e);
		}
	}

}
