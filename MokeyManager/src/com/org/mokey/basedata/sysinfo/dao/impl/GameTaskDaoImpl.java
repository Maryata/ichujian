package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.org.mokey.basedata.sysinfo.dao.GameTaskDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class GameTaskDaoImpl implements GameTaskDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameTaskDaoImpl.class);
	
    private JdbcTemplate jdbcTemplate;

    @Override
    public void delete (String id) { // 物理删除
        String sql = "delete from  T_GAME_TASK where C_ID=?";

        jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public Map<String, Object> list (String name, int start, int limit) {
        Map<String, Object> ret = new HashMap<String, Object>();

        // 用 StringBuilder 好点儿，不过之前都是用的StringBuffer，要改太多，先这样了
        StringBuffer sql = new StringBuffer(
                "select count(1) from T_GAME_TASK t where 1=1 and t.c_isLive='1' "); // t.C_ISLIVE = 1
        List<Object> argsList = new ArrayList<Object>();

        if (StrUtils.isNotEmpty(name)) {
            sql.append(" and t.C_SUBNAME like ?");
            argsList.add("%" + name + "%");
        }
        int count = jdbcTemplate.queryForObject(sql.toString(), argsList
                .toArray(), Integer.class);

        sql.append(" order by t.c_type desc  ");
        String sql1 = DaoUtil.addfy_oracle(sql, start, limit, argsList)
                .toString().replace("count(1)", "*");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
        ret.put("count", count);
        ret.put("list", list);
        return ret;
    }

    @Override
    public void save (Map<String, Object> map) {
        Object id_old = map.get("C_ID");

        if (!StringUtils.isEmpty(id_old)) {
            String sql = "select count(1) from T_GAME_TASK t where t.c_id=?";
            int count = jdbcTemplate.queryForObject(sql, new Object[]{id_old}, Integer.class);

            if (count >= 1) {
                Map<String, Object> wMap = new HashMap<String, Object>();
                wMap.put("C_ID", id_old);
                JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap,
                        "T_GAME_TASK");
            } else {
                String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                        "SEQ_GAME_H5_INFO");
                map.put("C_ID", sqlid);
                JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                        "T_GAME_TASK");
            }
        } else {
            String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                    "SEQ_GAME_H5_INFO");
            map.put("C_ID", sqlid);
            JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                    "T_GAME_TASK");
        }
    }

    public JdbcTemplate getJdbcTemplate () {
        return jdbcTemplate;
    }

    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public List<Map<String, Object>> getAllRewards() {
		try {
			String sql = "SELECT * FROM T_GAME_REWARD ORDER BY C_DAYS";
			return jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("getAllRewards failed!", e);
		}
		return null;
	}

	@Override
	public void addReward(String days, String score) {
		try {
			String sql = "INSERT INTO T_GAME_REWARD " +
					" (C_ID, C_DAYS, C_SCORE)" +
					" VALUES (SEQ_GAME_REWARD.NEXTVAL, ?, ?)";
			jdbcTemplate.update(sql, new Object[]{days, score});
		} catch (Exception e) {
			LOGGER.error("addReward failed!", e);
		}
	}

	@Override
	public void delReward(String id) {
		try {
			String sql = "DELETE FROM T_GAME_REWARD WHERE C_ID = ?";
			jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			LOGGER.error("delReward failed!", e);
		}
	}

	@Override
	public void handleReward(String id, String days, String score) {
		try {
			String sql = "UPDATE T_GAME_REWARD SET C_SCORE = ? WHERE C_ID = ? AND C_DAYS = ?";
			jdbcTemplate.update(sql, new Object[]{score, id, days});
		} catch (Exception e) {
			LOGGER.error("handleReward failed!", e);
		}
	}
}
