package com.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class SearchServiceImpl extends SqlMapClientDaoSupport implements SearchService {
	private Logger log = (Logger.getLogger(SetServiceImpl.class));
	private JdbcTemplate jdbcTemplate;
	
	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List getSearch() {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("search.getSearch");
		return list;
	}

	@Override
	public List getSearchActivity(String textMsg,String cityid,String imei,String pageIndex) {
		int page=(Integer.parseInt(pageIndex)-1)*20;
		String sql="select * from (select t.c_id,rownum rt,t.c_title,t.c_sdate,t.c_edate,b.c_cname,b.c_logourl as brandLogo,t.c_imageurl as activityImage"+
		" from T_ACTIVITY_BASE_INFO t,T_ACTIVITY_BRAND_INFO b"+
		" where upper(t.c_title) like ? and (t.c_cityid = ? or t.c_cityid is null or t.c_cityid=0) and t.c_brandid =b.c_id and c_edate >=sysdate and t.c_type=1"+
		") where rt>? and rt<=? ";
		List list=jdbcTemplate.queryForList(sql ,"%"+textMsg.toUpperCase()+"%" ,cityid ,page , page+20);
 		return list;
	}

	@Override
	public List getSearchBrand(String textMsg,String imei,String pageIndex) {
		// TODO Auto-generated method stub
		int page=(Integer.parseInt(pageIndex)-1)*20;
		textMsg = "%"+textMsg.toUpperCase()+"%";
		//alter session set NLS_SORT=BINARY_CI;  
		String sql="select * from (select rownum rt,c_id,c_cname,c_ename,c_logourl"+
			" from T_ACTIVITY_BRAND_INFO "+
			" where upper(c_cname) like ? or upper(c_ename) like ? "+
			" ) where rt>? and rt<=? ";
		log.info("sql:"+sql);
		List list=jdbcTemplate.queryForList(sql,textMsg,textMsg,page,page+20);
 		return list;
		
	}
	//添加搜索记录表
	@Override
	public void setSearchRecord(String textMsg, String imei) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("textMsg", textMsg);
		map.put("imei", imei);
		map.put("date", new Date());
		getSqlMapClientTemplate().insert("search.setSearchRecord", map);
	}



	@Override
	public int isAttention(String brandid, String uid,String brandType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("brandid", brandid);
		map.put("brandType", brandType);
//		List<Map> attentions = getSqlMapClientTemplate().queryForList("search.isAttention", map);
//		log.debug("attentions:"+attentions);
//		boolean isAtten =false;
//		if(StrUtils.isNotEmpty(attentions)){
//			for(Map item : attentions){
//				if("1".equals(item.get("C_STATE"))){
//					isAtten = true;
//					break;
//				}
//			}
//		}
		
		int isAtten =(Integer)getSqlMapClientTemplate().queryForObject("search.isAttention", map);
		return isAtten;
	}

	@Override
	public int brandAttentionNum(String brandid, String brandType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandid", brandid);
		map.put("brandType", brandType);
		
		int attentiomNum = (Integer)getSqlMapClientTemplate().queryForObject("search.attentionNum", map);

		return attentiomNum;
	}
}
