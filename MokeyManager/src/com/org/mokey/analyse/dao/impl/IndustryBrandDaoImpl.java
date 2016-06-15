package com.org.mokey.analyse.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.analyse.dao.IndustryBrandDao;

public class IndustryBrandDaoImpl implements IndustryBrandDao {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	// 查询所有行业中的品牌总数
	public Integer getTotalBrands() {
		try {
			String sql = "SELECT COUNT(*) FROM T_ACTIVITY_BRAND_INFO B WHERE B.C_INDUSTRYID IN " +
					"(SELECT TO_CHAR(I.C_ID) FROM T_ACTIVITY_INDUSTRY_TYPE I " +
					"WHERE I.C_ISLIVE = 1 AND I.C_ORDER <> 0) AND B.C_ISLIVE = 1";
			return this.jdbcTemplate.queryForObject(sql,Integer.class);
		} catch (DataAccessException e) {
			log.error("IndustryBrandDaoImpl.getTotalBrands failed, e : " + e);
		}
		return 0;
	}
	@Override
	// 查询每个行业的品牌数
	public List<Map<String, Object>> brandOfIndustry() {
		try {
			String sql = "SELECT I.C_ID,I.C_NAME,COUNT(*) CNT FROM T_ACTIVITY_BRAND_INFO B,T_ACTIVITY_INDUSTRY_TYPE I " +
					"WHERE B.C_ISLIVE = 1 AND I.C_ISLIVE = 1 AND B.C_INDUSTRYID = TO_CHAR(I.C_ID) AND I.C_ID <> 0 " +
					"GROUP BY B.C_INDUSTRYID,I.C_ID,I.C_NAME";
			return this.jdbcTemplate.queryForList(sql);
		} catch (DataAccessException e) {
			log.error("IndustryBrandDaoImpl.brandOfIndustry failed, e : " + e);
		}
		return null;
	}
}
