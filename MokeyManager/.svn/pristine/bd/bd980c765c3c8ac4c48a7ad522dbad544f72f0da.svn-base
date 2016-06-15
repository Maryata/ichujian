package com.org.mokey.basedata.baseinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.org.mokey.basedata.baseinfo.service.ActionRecommendSchemeService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionRecommendSchemeImpl implements ActionRecommendSchemeService {
	private JdbcTemplate jdbcTemplate;
	private  SimpleJdbcTemplate simpleJdbcTemplate;
	
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void deleteActionRecommendScheme(String c_id) {
		// TODO Auto-generated method stub
		String sql ="delete from T_ACTIVITY_RECOMMEND_SCHEME t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[]{c_id});
	}

	@Override
	public Map<String, Object> getActionRecommendScheme(String c_name,
			String c_cityid, String c_type, int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_RECOMMEND_SCHEME t where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_name)){
			sql.append(" and t.c_name like ?");
			args.add("%"+c_name+"%");
		}
		if(StrUtils.isNotEmpty(c_cityid)){
			sql.append(" and t.c_cityid like ?");
			args.add("%"+c_cityid+"%");
		}
		if(StrUtils.isNotEmpty(c_type)){
			sql.append(" and t.c_type like ?");
			args.add("%"+c_type+"%");
		}
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_order ",start, limit, args).toString().replace("count(*)", "t.*,(select c_cname from T_ACTIVITY_CITY_INFO where t.c_cityid =c_id) as P_NAME");
		
		List list = jdbcTemplate.queryForList(sql1, args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public String savaActionRecommendScheme(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_RECOMMEND_SCHEME");
			saveMap.put("c_id", seqId);
			saveMap.put("c_date", new Date());
			saveMap.put("c_order", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_RECOMMEND_SCHEME");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			saveMap.put("c_date", new Date());
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_RECOMMEND_SCHEME") ;
		}
		return seqId;
	}
	//查询所有的广告
	@Override
	public Map<String, Object> findAllAdvertise() {
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_name from T_ACTIVITY_MENU_INFO t where t.c_islive=1 ";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}

	@Override
	public Map<String, Object> findAllRecommend() {
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_name from T_ACTIVITY_CONTENT_TYPE t";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}
	//查询广告位对应的图片
	@Override
	public Map<String, Object> findAdvertiseImages(String cityid, String meunid) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql="  select C_PICURL FROM    T_ACTIVITY_ADVERT_INFO WHERE C_ID=( select  C_advertid from  T_ACTIVITY_CITY_ADVERT  where   C_menuid = ? and c_cityid = ?)";
		List args = new ArrayList();
		args.add(meunid);
		args.add(cityid);
		//int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		List list =jdbcTemplate.queryForList(sql, args.toArray());
		
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public String savaRecommendSchemeDetail(Map<String, Object> Map) {
		String id = null;
		if(StrUtils.isEmpty(Map.get("c_id"))){
			//获得系统自动生成的id
			id=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_RECOMMEND_SCHEME_D");
			Map.put("c_id", id);		
			
//			JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_RECOMMEND_SCHEME_D");
			
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, Map, "T_ACTIVITY_RECOMMEND_SCHEME_D");
		}else{
			id = String.valueOf(Map.get("c_id"));
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", Map.get("c_id"));
			
			Map.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, Map, whereMap, "T_ACTIVITY_RECOMMEND_SCHEME_D");
		}
		return id;
		
	}
	
//	public int addCourse(Map<String, Object> saveParam) {
//	    //设置参数集合,匹配sql语句中的参数
//	    MapSqlParameterSource params=new MapSqlParameterSource();
////	    params.addValue("c_cityid", c_cityid);
////	    params.addValue("c_contentid", c_contentid);
//	    
//	    params.addValue("c_date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//	    params.addValue("c_name",saveParam.get("c_name"));
//	    params.addValue("c_cityid",saveParam.get("c_cityid"));
//	    params.addValue("c_type",saveParam.get("c_type"));
////	    insert into t_activity_city_content (c_id,c_cityid,c_contentid) values (SEQ_t_activity_city_content.Nextval,'8','8')
//	    KeyHolder keyHolder = new GeneratedKeyHolder();
//	    String sql = "insert into T_ACTIVITY_RECOMMEND_SCHEME (c_id,c_name,c_cityid,c_type,c_date,c_order) " +
//	    "values(SEQ_ACTIVITY_RECOMMEND_SCHEME.Nextval,:c_name,:c_cityid,:c_type,:c_date,10)";
//	    //需要最后一个String集合列表参数，id表示表主键，否则也会出问题
//	    getSimpleJdbcTemplate().getNamedParameterJdbcOperations().update(sql, params, keyHolder, new String[]{"c_id"});
//	    Map<String, Object> km = keyHolder.getKeys();
//	    Integer generatedId = Integer.valueOf(km.get("c_id").toString()); //keyHolder.getKeys() .getKey().intValue();
//	    return generatedId;
//	}

	@Override
	public int getNextID() {
		return Integer.valueOf(JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_RECOMMEND_SCHEME"))+1;
	}

	@Override
	public Map<String, Object> findById(String cId) {
		Map<String, Object> ret = new HashMap<String, Object>();
 		//String sql ="select t.*,b.c_name as T_NAME,b.c_id as ID,c.c_name as C_NAME,c.c_id as T_ID from T_ACTIVITY_RECOMMEND_SCHEME_D t,T_ACTIVITY_MENU_INFO b,T_ACTIVITY_CONTENT_TYPE c where t.c_advertid=b.c_id and t.c_typeid=c.c_id and c_schemeid = ?";
		String sql="select t.*,c.c_id as t_id,c.c_name,d.c_name as t_name  from T_ACTIVITY_RECOMMEND_SCHEME_D t,T_ACTIVITY_CONTENT_TYPE c ,T_ACTIVITY_MENU_INFO d WHERE  t.c_advertid=d.c_id and t.c_schemeid=? AND t.c_typeid=c.c_id";
 		List list =jdbcTemplate.queryForList(sql, cId);
 		if(list.size()!=0){
	 		Map  map=(Map)list.get(0);
	 		ret.put("list",list);
//	 		ret.put("advertid", map.get("c_advertid"));
//	 		ret.put("c_name", map.get("c_name"));
//	 		ret.put("t_name", map.get("t_name"));
//	 		ret.put("c_id", map.get("t_id"));
//	 		ret.put("id", map.get("ID"));
	 		ret.put("c_id", map.get("c_id"));
	 		ret.put("t_id", map.get("t_id"));
	 		ret.put("c_name", map.get("c_name"));
	 		ret.put("t_name", map.get("t_name"));
	 		ret.put("advertid", map.get("c_advertid"));
 		}else{
// 			ret.put("list",Collections.EMPTY_LIST);
//	 		ret.put("advertid", "");
//	 		ret.put("c_name", "");
//	 		ret.put("t_name", "");
//	 		ret.put("c_id", "");
//	 		ret.put("id", "");
 			ret.put("t_name", "");
	 		ret.put("c_id", "");
	 		ret.put("t_id", "");
	 		ret.put("c_name", "");
	 		ret.put("advertid", "");
	 		ret.put("list",Collections.EMPTY_LIST);
 		}
 		return ret;
	}
	
	@Override
	public List findContentTypeById(String cId) {
 		String sql ="select * from T_ACTIVITY_RECOMMEND_SCHEME_D where c_schemeid = ?";
 		List list =jdbcTemplate.queryForList(sql, cId);
 		return list;
	}
	
	//根据cid删除
	@Override
	public void deleteById(String cid) {
		String sql ="delete from T_ACTIVITY_RECOMMEND_SCHEME_D t where t.c_id =?";
		jdbcTemplate.update(sql, cid);
	}

	@Override
	public List queryRecommendScheme(String cityid, String typeid) {
		// TODO Auto-generated method stub
		String sql ="select * from T_ACTIVITY_RECOMMEND_SCHEME where c_cityid = ? and c_type= ?";
 		List list =jdbcTemplate.queryForList(sql, cityid,typeid);
 		return list;
	}

	@Override
	public void deleteRecommendScheme(String cityid, String typeid) {
		// TODO Auto-generated method stub
		String sql ="delete from T_ACTIVITY_RECOMMEND_SCHEME  where c_cityid = ? and c_type= ?";
		jdbcTemplate.update(sql,cityid, typeid);
		
	}

	@Override
	public void deleteSchemeDetail(String schemeId) {
		// TODO Auto-generated method stub
		String sql ="delete from T_ACTIVITY_RECOMMEND_SCHEME_D t where t.c_schemeid =?";
		jdbcTemplate.update(sql, schemeId);
	}

	@Override
	public int querySchenmeList(String cityid, String contentid) {
		String sql ="select c_id from T_ACTIVITY_CITY_CONTENT t where c_cityid = "+cityid+" and c_contentid= "+contentid+" and c_islive=1";
		Integer list =(Integer)jdbcTemplate.queryForInt(sql);
 		return list;
	}
//根据城市id查询到应的contentid
	@Override
	public List findContentId(String cityid) {
		String sql ="select b.c_name as C_NAME,t.c_contentid as C_ID from T_ACTIVITY_CITY_CONTENT t ,T_ACTIVITY_CONTENT_TYPE b where t.c_contentid=b.c_id and t.c_cityid = ? and t.c_islive=1";
 		List list =jdbcTemplate.queryForList(sql, cityid);
 		return list;
	}


}
