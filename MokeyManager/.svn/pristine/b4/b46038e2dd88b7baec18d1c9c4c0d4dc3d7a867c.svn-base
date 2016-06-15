package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.GameCueDao;

public class GameCueDaoImpl implements GameCueDao {
	private static final Logger LOGGER = Logger.getLogger(GameCueDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 查询游戏提示语
	public List<Map<String, Object>> gameCue() {
		try {
			String sql = "SELECT * FROM T_GAME_TITLE_INFO T ORDER BY T.C_ISLIVE DESC,T.C_DATE DESC";
			return this.jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("GameCueDaoImpl.gameCue failed, e = " + e);
		}
		return null;
	}

	@Override
	// 添加提示语
	public void addCue(String addTitle) {
		try {
			String sql = "INSERT INTO T_GAME_TITLE_INFO (C_ID,C_TITLE,C_DATE,C_ISLIVE) " +
					"VALUES(SEQ_GAME_TITLE_INFO.NEXTVAL,?,?,0)";
			this.jdbcTemplate.update(sql,new Object[]{addTitle,new Date()});
		} catch (Exception e) {
			LOGGER.error("GameCueDaoImpl.gameCue failed, e = " + e);
		}
	}

	@Override
	// 修改提示语
	public void modifyCue(String cid, String title, String islive) {
		try {
			String sql = "UPDATE T_GAME_TITLE_INFO T SET T.C_TITLE = ?, T.C_ISLIVE = ?, " +
					"T.C_DATE = ? WHERE T.C_ID = ?";
			Object[] obj = {title,islive,new Date(),cid};
			this.jdbcTemplate.update(sql,obj);
		} catch (Exception e) {
			LOGGER.error("GameCueDaoImpl.modifyCue failed, e = " + e);
		}
	}

	@Override
	// 使当前提示内容生效/无效
	public void isvalid(String cid, String islive) {
		try {
			String sql = "UPDATE T_GAME_TITLE_INFO T SET T.C_ISLIVE = ?, T.C_DATE = ? WHERE T.C_ID = ?";
			Object[] obj = {islive,new Date(),cid};
			this.jdbcTemplate.update(sql,obj);
		} catch (Exception e) {
			LOGGER.error("GameCueDaoImpl.isvalid failed, e = " + e);
		}
	}

	@Override
	// 删除提示内容
	public void deleteCol(String cid) {
		try {
			String sql = "DELETE FROM T_GAME_TITLE_INFO T WHERE T.C_ID = ?";
			Object[] obj = {cid};
			this.jdbcTemplate.update(sql,obj);
		} catch (Exception e) {
			LOGGER.error("GameCueDaoImpl.isvalid failed, e = " + e);
		}
	}
}
