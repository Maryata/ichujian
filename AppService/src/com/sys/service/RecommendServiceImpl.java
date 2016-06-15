package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class RecommendServiceImpl extends SqlMapClientDaoSupport implements RecommendService {

	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(RecommendServiceImpl.class));
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List getRecommendFollow(String cityname) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityname", cityname);
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getRecommendFollow",map);
		return list;
	}

	@Override
	public List getRecommendMainIndustry(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getRecommendMainIndustry",map);
		return list;
	}

	@Override
	public List getRecommendMainAdvert(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getRecommendMainAdvert",map);
		return list;
	}
	
	@Override
	public List getRecommendMainAdvertMin(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getRecommendMainAdvertMin",map);
		return list;
	}

	@Override
	public List getRecommendMainHot(String cityid,String pageindex) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		int page=Integer.parseInt(pageindex);
		page=(page-1)*20;
		map.put("page", page);
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getRecommendMainHot",map);
		return list;
	}

	@Override
	public List getRecommendMainPage(String typeid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("typeid", typeid);
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getRecommendMainPage",map);
		return list;
	}

	@Override
	public List getCityList() {
		// TODO Auto-generated method stub
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getCityList");
		return list;
	}

	@Override
	public List getRecommendMainPageTitle(String cityid, String pageindex) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		int page=Integer.parseInt(pageindex);
		page=(page-1)*20;
		map.put("page", page);
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getRecommendMainPageTitle",map);
		return list;
	}

	@Override
	public List getHotCityList() {
		// TODO Auto-generated method stub
		List list=this.getSqlMapClientTemplate().queryForList("recommend.getHotCityList");
		return list;
	}

	@Override
	public int getMainAdvert(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("recommend.getMainAdvert",map);
		return i;
	}
	
	@Override
	public int getMainAdvertMin(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("recommend.getMainAdvertMin",map);
		return i;
	}

	@Override
	public int getMainIndustry(String cityid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("recommend.getMainIndustry",map);
		return i;
	}

	@Override
	public int getRecommend(String cityid, String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityid", cityid);
		map.put("type", type);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("recommend.getRecommend",map);
		return i;
	}

	@Override
	public int getRecommendCity(String cityname) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("cityname", cityname);
		List list=getSqlMapClientTemplate().queryForList("recommend.getRecommendCity",map);
		if(list.size()<1){
			return 0;
		}else{
			Map maps=(Map) list.get(0);
			map.put("cityid", maps.get("C_ID").toString());
			List city=getSqlMapClientTemplate().queryForList("recommend.getRecommendCityFollow",map);
			if(city.size()>0){
				return 1;
			}else {
				return 0;
			}
		}
	}

	@Override
	public List getRecommendFollowNull() {
		// TODO Auto-generated method stub
		List list=getSqlMapClientTemplate().queryForList("recommend.getRecommendFollowNull");
		return list;
	}


}
