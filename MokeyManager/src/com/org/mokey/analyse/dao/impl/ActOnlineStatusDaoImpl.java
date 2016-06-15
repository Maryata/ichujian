package com.org.mokey.analyse.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.analyse.dao.ActOnlineStatusDao;

public class ActOnlineStatusDaoImpl implements ActOnlineStatusDao {

	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	// 获取上/下线活动数量
	public List<Map<String,Object>> onlineOrOfflineAct(String onOrOff, String sDate, String eDate) {
		try {
			String sql = "SELECT C_EDITPERSON,COUNT(*) CNT FROM T_ACTIVITY_BASE_INFO WHERE " +
						"TO_CHAR("+onOrOff+", 'yyyy-MM-dd') >= ? " +
						"AND TO_CHAR("+onOrOff+", 'yyyy-MM-dd') < ? " +
						"AND C_ISLIVE = 1 GROUP BY C_EDITPERSON";
			
			return this.jdbcTemplate.queryForList(sql, new Object[]{sDate,eDate});
		} catch (Exception e) {
			log.error("ActOnlineStatusDaoImpl.onlineOrOfflineAct failed, e : " + e);
		}
		return null;
	}
}
