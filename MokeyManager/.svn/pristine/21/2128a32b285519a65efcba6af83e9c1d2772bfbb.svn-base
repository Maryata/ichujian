package com.org.mokey.basedata.baseinfo.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.org.mokey.basedata.baseinfo.service.ActionCityandActTypeService;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionCityandActTypeServiceImpl  implements ActionCityandActTypeService{
protected JdbcTemplate jdbcTemplate;
private  SimpleJdbcTemplate simpleJdbcTemplate;
	
	
	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("unchecked")
	public List getActTypeList() {  

		
		return jdbcTemplate.queryForList("select c_id,c_name from t_activity_content_type where c_islive=1 and c_istitle=1 ");
	}

	@SuppressWarnings("unchecked")
	public Map getSummaryMessage(String cityID, String menuID,int start,int limit) {
		
		StringBuffer  sb=new StringBuffer("select count(*) from T_ACTIVITY_CITY_CONTENT t where  c_islive=1");

		StringBuffer sql = new StringBuffer(
				"select t.c_id,t.c_cityid,t.c_contentid from T_ACTIVITY_CITY_CONTENT t where  c_islive=1  "); // 根据外面两个条件
		// ,查询出主键，外面显示的用
		// MAP丢出去
		String sql1 = "select c_cname from T_ACTIVITY_CITY_INFO where  C_ID="; // 查询出city的名字
		String sql2 = "select c_name  from T_ACTIVITY_CONTENT_TYPE  where  c_id="; // 查询出menu的名字
		ArrayList list = new ArrayList();
		if (StrUtils.isNotEmpty(cityID)) {
			sql.append("and t.c_cityid=?");   //.c_cityid,t.c_contentid
			list.add(cityID);
			sb.append("and t.c_cityid=");
			sb.append(cityID);
		}
		if (StrUtils.isNotEmpty(menuID)) {
			sql.append("and t.c_contentid=?");
			list.add(menuID);
			sb.append("and t.c_contentid=");
			sb.append(menuID);
		}
		
		int  count =jdbcTemplate.queryForInt(sb.toString());
		if (start > 0 || limit > 0) {
			sql.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM NUM  FROM (");

			sql.append(") temp ");
			if (limit > 0) {
				if (start < 0) {
					start = 0;
				}
				sql.append(" WHERE ROWNUM <= ");
				sql.append(start + limit);
				sql.append(")");

			}
			if (start > 0) {
				sql.append(" where num >  ");
				sql.append(start);
			}
		}
		
		
		
		List messageList = jdbcTemplate.queryForList(sql.toString(), list
				.toArray());
		List jsonList = new ArrayList();
		Iterator it = messageList.iterator();
		Map jsonMap = null;
		while (it.hasNext()) {
			jsonMap = new HashMap(); 
			Map map = (Map) it.next();
			jsonMap.put("C_ID", map.get("c_id"));
//			jsonMap.put("CID", map.get("c_cityid"));
//			jsonMap.put("MID", map.get("c_menuid"));
			if (map.get("c_cityid") == null || "".equals(map.get("c_cityid"))) {
				jsonMap.put("C_CITY", "");
			} else {
				jsonMap.put("C_CITY", jdbcTemplate.queryForMap(
						sql1 + map.get("c_cityid")).get("c_cname"));
			}
			if (map.get("c_contentid") == null || "".equals(map.get("C_MENUID"))) {
				jsonMap.put("C_MENU", "");
				
			} else {
				jsonMap.put("C_CONTENTID", map.get("c_contentid"));
				List  list5=jdbcTemplate.queryForList(sql2 + map.get("c_contentid"));
				if (list5.size()>0) {
					Map  map5=(Map)list5.get(0);	
					jsonMap.put("C_MENU",map5.get("c_name"));
				}else{
					jsonMap.put("C_MENU", "");
					
				}
			}

			jsonList.add(jsonMap);
		}
		Map map=new HashMap();
		map.put("list", jsonList);
		map.put("count", count);

		return map;
	}
//城市ID  活动内容类别 ID title
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Map getInnerSummaryMessage(String cityid, String industyid,
			String activityid, String title,int start,int limit) {
		
		
		StringBuffer sb = new StringBuffer(
				"select t.c_id, t.c_cityid,t.c_propertyid,t.c_title,t.c_industryid,t.C_IMAGEURL,t.c_address,t.c_edate   from T_ACTIVITY_BASE_INFO t where t.c_islive=1");
		ArrayList<Object> list = new ArrayList();
		if (StrUtils.isNotEmpty(cityid)) {
			sb.append("and t.c_cityid=?");
			list.add(cityid);
		}
		if (StrUtils.isNotEmpty(industyid)) {
			sb.append("and t.c_industryid=?");
			list.add(industyid);
		}
		if (StrUtils.isNotEmpty(activityid)) {
			sb.append("and t.c_propertyid=?");
			list.add(activityid);
		} 
		if (StrUtils.isNotEmpty(title)) {
			sb.append("and t.c_title like '%");
			sb.append(title);
			sb.append("%'");
		}
		sb.append("ORDER  BY t.c_actiondate DESC");
		int count =jdbcTemplate.queryForInt(sb.toString().replace("t.c_id, t.c_cityid,t.c_propertyid,t.c_title,t.c_industryid,t.C_IMAGEURL,t.c_address,t.c_edate","count(*)"),list.toArray());
		if (start > 0 || limit > 0) {
			sb.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM NUM  FROM (");

			sb.append(") temp ");
			if (limit > 0) {
				if (start < 0) {
					start = 0;
				}
				sb.append(" WHERE ROWNUM <= ");
				sb.append(start + limit);
				sb.append(")");

			}
			if (start > 0) {
				sb.append(" where num >  ");
				sb.append(start);
			}
		}
		Iterator it = jdbcTemplate.queryForList(sb.toString(), list.toArray())
				.iterator();
		// 根据城市ID 查询城市的语句
		String sql1 = "select c_cname  from T_ACTIVITY_CITY_INFO  where c_level=2 and c_islive=1 and c_id=";
		// 根据行业ID 的查询行业语句
		String sql2 = "select t.c_name  from T_ACTIVITY_INDUSTRY_TYPE t where t.c_islive=1 and t.c_id=";
		// 根据活动ID 查询活动的语句
		String sql3 = "select t.c_name from T_ACTIVITY_PROPERTY_TYPE t where t.c_islive=1 and t.c_id="; 

		List<Map> jsonList = new ArrayList<Map>();
		Map jsonMap =null;
		while (it.hasNext()) {
			Map map = (Map) it.next();
			jsonMap=new HashMap();
			String city = String.valueOf(map.get("c_cityid")); // 获得结果集里面的 城市ID
			String industy = String.valueOf(map.get("c_industryid")); // 获得结果集里面的
			// 行业ID
			String activity = String.valueOf(map.get("c_propertyid")); // 获得结果集里面的活动ID
			String address=String.valueOf(map.get("c_address"));
			String date=String.valueOf(map.get("c_edate"));
			Map map1 = JdbcTemplateUtils.getMap(sql1 + city, jdbcTemplate);// jdbcTemplate.queryForMap(sql1+city);
			Map map2 = JdbcTemplateUtils.getMap(sql3 + activity, jdbcTemplate);
			Map map3 = JdbcTemplateUtils.getMap(sql2 + industy, jdbcTemplate);
			
			
			if (map1 == null || "".equals(map1)) {  //,行业类别有了，活动标题有了，，没有 活动时间没有
				jsonMap.put("city", "");
			} else {
				jsonMap.put("city", map1.get("c_cname"));
			}
			if (map2 == null || "".equals(map2)) {
				jsonMap.put("activity", "");
			} else {
				jsonMap.put("activity", map2.get("c_name"));
			}
			if (map3 == null || "".equals(map3)) {
				jsonMap.put("industy", "");
			} else {
				jsonMap.put("industy", map3.get("c_name"));//行业类型
			}
			jsonMap.put("title", map.get("c_title"));//标题
//			jsonMap.put("Url", map.get("C_IMAGEURL"));
			jsonMap.put("id", map.get("C_ID"));
			jsonMap.put("address", address);//地址
			jsonMap.put("date", date);//时间
			jsonList.add(jsonMap);
			
		}

		Map  map=new  HashMap();
		map.put("list", jsonList);
		
		
		map.put("count",count );
		return map;
	}



	@SuppressWarnings("deprecation")
	@Override
	public int addCourse(String c_cityid,String c_contentid){
	    //设置参数集合,匹配sql语句中的参数
	    MapSqlParameterSource params=new MapSqlParameterSource();
	    params.addValue("c_cityid", c_cityid);
	    params.addValue("c_contentid", c_contentid);
	    params.addValue("c_islive",1);
//	    insert into t_activity_city_content (c_id,c_cityid,c_contentid) values (SEQ_t_activity_city_content.Nextval,'8','8')
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    String sql = "insert into t_activity_city_content (c_id,c_cityid,c_contentid,c_islive) " +
	    "values(SEQ_t_activity_city_content.Nextval,:c_cityid,:c_contentid,:c_islive)";
	    //需要最后一个String集合列表参数，id表示表主键，否则也会出问题
	    getSimpleJdbcTemplate().getNamedParameterJdbcOperations().update(sql, params, keyHolder, new String[]{"c_id"});
	    Map<String, Object> km = keyHolder.getKeys();
	    Integer generatedId = Integer.valueOf(km.get("c_id").toString()); //keyHolder.getKeys() .getKey().intValue();
	    return generatedId;
	  }

	public String addLast(String valueOf, String string, Object object) {
		int id=Integer.parseInt(JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_activity_content_activity"))+1;
		jdbcTemplate.update(  
                "insert  into    t_activity_content_activity (c_id,c_schemeid,c_activtyid,c_order,c_islive) values(SEQ_activity_content_activity.NextVal,?,?,?,'1')",  
                new Object[] { valueOf, string,  
                       object});  
		
		return id+"";
	}

	public void deleteById(String id) {
	this.jdbcTemplate.execute("update  T_ACTIVITY_CITY_CONTENT  set  c_islive =0 where  c_id="+id);
	}

	public Map getUpdateInnerSummaryMessage(String id, int start, int limit) {
	

		
		
		StringBuffer sb = new StringBuffer(
				"select t.c_id, t.c_cityid,t.c_propertyid,t.c_title,t.c_industryid,t.C_IMAGEURL,t.c_address,t.c_edate   from T_ACTIVITY_BASE_INFO t where t.c_islive=1");
		ArrayList<Object> list = new ArrayList();
		if (StrUtils.isNotEmpty(id)) {
			sb.append("and t.c_id=?");
			list.add(id);
		}
	
		int count =jdbcTemplate.queryForInt(sb.toString().replace("t.c_id, t.c_cityid,t.c_propertyid,t.c_title,t.c_industryid,t.C_IMAGEURL,t.c_address,t.c_edate","count(*)"),list.toArray());
		if (start > 0 || limit > 0) {
			sb.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM NUM  FROM (");

			sb.append(") temp ");
			if (limit > 0) {
				if (start < 0) {
					start = 0;
				}
				sb.append(" WHERE ROWNUM <= ");
				sb.append(start + limit);
				sb.append(")");

			}
			if (start > 0) {
				sb.append(" where num >  ");
				sb.append(start);
			}
		}
		Iterator it = jdbcTemplate.queryForList(sb.toString(), list.toArray())
				.iterator();
		// 根据城市ID 查询城市的语句
		String sql1 = "select c_cname  from T_ACTIVITY_CITY_INFO  where c_level=2 and c_islive=1 and c_id=";
		// 根据行业ID 的查询行业语句
		String sql2 = "select t.c_name  from T_ACTIVITY_INDUSTRY_TYPE t where t.c_islive=1 and t.c_id=";
		// 根据活动ID 查询活动的语句
		String sql3 = "select t.c_name from T_ACTIVITY_PROPERTY_TYPE t where t.c_islive=1 and t.c_id="; 

		List<Map> jsonList = new ArrayList<Map>();
		Map jsonMap =null;
		while (it.hasNext()) {
			Map map = (Map) it.next();
			jsonMap=new HashMap();
			String city = String.valueOf(map.get("c_cityid")); // 获得结果集里面的 城市ID
			String industy = String.valueOf(map.get("c_industryid")); // 获得结果集里面的
			// 行业ID
			String activity = String.valueOf(map.get("c_propertyid")); // 获得结果集里面的活动ID
			String address=String.valueOf(map.get("c_address"));
			String date=String.valueOf(map.get("c_edate"));
			Map map1 = JdbcTemplateUtils.getMap(sql1 + city, jdbcTemplate);// jdbcTemplate.queryForMap(sql1+city);
			Map map2 = JdbcTemplateUtils.getMap(sql3 + activity, jdbcTemplate);
			Map map3 = JdbcTemplateUtils.getMap(sql2 + industy, jdbcTemplate);
			
			
			if (map1 == null || "".equals(map1)) {  //,行业类别有了，活动标题有了，，没有 活动时间没有
				jsonMap.put("city", "");
			} else {
				jsonMap.put("city", map1.get("c_cname"));
			}
			if (map2 == null || "".equals(map2)) {
				jsonMap.put("activity", "");
			} else {
				jsonMap.put("activity", map2.get("c_name"));
			}
			if (map3 == null || "".equals(map3)) {
				jsonMap.put("industy", "");
			} else {
				jsonMap.put("industy", map3.get("c_name"));//行业类型
			}
			jsonMap.put("title", map.get("c_title"));//标题
//			jsonMap.put("Url", map.get("C_IMAGEURL"));
			jsonMap.put("id", map.get("C_ID"));
			jsonMap.put("address", address);//地址
			jsonMap.put("date", date);//时间
			jsonList.add(jsonMap);
			
		}
		Map  map=new  HashMap();
if(jsonList.size()>=1){
	
	
	map.put("item",jsonList.get(0));
}else{
	map.put("item","");
}
		
		return map;
	
		
		
		
		
		
	}
	
	public  List  getIDs(String id){
		
		//t_activity_city_content 根据他的ID 到t_activity_content_activity   这里查询出满足条件的数据
		String sql="select  c_activtyid  from t_activity_content_activity   where c_islive=1 and  c_schemeid="+id;
		
	List  list=	this.jdbcTemplate.queryForList(sql);
	Iterator  it=list.iterator();
	List<String>  innerList=new ArrayList<String>();
	String sql1="select t.c_id, t.c_cityid,t.c_propertyid,t.c_title,t.c_industryid,t.C_IMAGEURL,t.c_address,t.c_edate   from T_ACTIVITY_BASE_INFO t where t.c_islive=1";
	  while(it.hasNext()){
		  Map  map=(Map)it.next();
		  String  activityID =String.valueOf(map.get("c_activtyid"));
		 innerList.add(activityID);
	  }
		
		
		
		
		return innerList;
	}

	public void deleteLastById(String savecityid) {
		this.jdbcTemplate.execute("delete from t_activity_content_activity  where c_schemeid="+savecityid);
	}

	public int isExist(String savecityid, String contentid) {
		StringBuffer  sb=new StringBuffer("select count(c_id)  from  T_ACTIVITY_CITY_CONTENT where  c_cityid=? and c_contentid=? and  c_islive=1");
		return  this.jdbcTemplate.queryForInt(sb.toString(),new Object[]{savecityid,contentid});
	}

	public String getID(String name) {
		String id="";
		String sql ="select c_id from T_ACTIVITY_CONTENT_TYPE where c_name ='"+name+"'"; 
		List list=jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			Map map=(Map) list.get(0);
			id=map.get("C_ID").toString();
		}
		
		
		return id;
	}
}
