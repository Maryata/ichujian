package com.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IndustryServiceImpl extends SqlMapClientDaoSupport implements IndustryService {

	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(IndustryServiceImpl.class));
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List getIndustrType(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		List list=this.getSqlMapClientTemplate().queryForList("industry.getIndustrType",map);
		return list;
	}

	@Override
	public int getIndustry(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("industry.getIndustry",map);
		return i;
	}

	@Override
	public List getBrand() {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		List list=this.getSqlMapClientTemplate().queryForList("industry.getBrand",map);
		return list;
	}

}
