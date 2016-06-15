package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionFourMainTainAdService;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionFourMainTainAdServiceImpl implements
		ActionFourMainTainAdService {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() { 
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public List getCityList() {
		String sql = "select  C_ID,c_cname  from T_ACTIVITY_CITY_INFO  where c_level=2 and c_islive=1 order by c_type,c_short_name ";

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List getMenuList() {
		String sql = "select t.c_id,t.c_name  from T_ACTIVITY_MENU_INFO t where t.c_islive=1 order by t.c_order";

		return jdbcTemplate.queryForList(sql);
	}

	// 城市添加 为2级,是否有效为1

	@SuppressWarnings( { "unchecked" })
	@Override
	public Map getSummaryMessage(String cityID, String menuID,int start,int limit) {

		StringBuffer sql = new StringBuffer(
				"select t.c_id,t.c_menuid,t.c_cityid,t.c_advertid from  T_ACTIVITY_CITY_ADVERT t where c_islive=1  "); // 根据外面两个条件
		// ,查询出主键，外面显示的用
		// MAP丢出去
		String sql1 = "select c_cname from T_ACTIVITY_CITY_INFO where  C_ID="; // 查询出city的名字
		String sql2 = "select c_name  from T_ACTIVITY_MENU_INFO  where  c_id="; // 查询出menu的名字
		ArrayList list = new ArrayList();
		if (StrUtils.isNotEmpty(cityID)) {
			sql.append("and t.c_cityid=?");
			list.add(cityID);
		}
		if (StrUtils.isNotEmpty(menuID)) {

			sql.append("and t.c_menuid=?");
			list.add(menuID);
		}
		sql.append("order by t.c_cityid ");
		
		//		int  count =jdbcTemplate.queryForInt(sql.toString().replace("t.c_id,t.c_menuid,t.c_cityid","count(*)"));
		int  count =jdbcTemplate.queryForInt("select count(*) from  T_ACTIVITY_ADVERT_INFO t where c_islive=1");
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
			jsonMap.put("CID", map.get("c_cityid"));
			jsonMap.put("MID", map.get("c_menuid"));
			jsonMap.put("C_ADVERTID", map.get("c_ADVERTID"));
			
			if (map.get("c_cityid") == null || "".equals(map.get("c_cityid"))) {
				jsonMap.put("C_CITY", "");
			} else {
				jsonMap.put("C_CITY", JdbcTemplateUtils.getMap(sql1 + map.get("c_cityid"), jdbcTemplate).get("c_cname"));
			}
			if (map.get("C_MENUID") == null || "".equals(map.get("C_MENUID"))) {
				jsonMap.put("C_MENU", "");
			} else {
				List  list5=jdbcTemplate.queryForList(sql2 + map.get("c_menuid"));
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

	@Override
	public void deleteById(String cId,String MID) { 
		String sql = "update   T_ACTIVITY_CITY_ADVERT set  c_islive=0  where c_id=? and C_MENUID=? "; 
		jdbcTemplate.update(sql, cId,MID);
	}
 
	@Override
	public List getIndustryCat() {
		String sql = "select t.c_name ,t.c_id from T_ACTIVITY_INDUSTRY_TYPE t where t.c_islive=1 order by c_order ";

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List getActivityCat() {

		String sql = "select t.c_name ,t.c_id from T_ACTIVITY_PROPERTY_TYPE t where t.c_islive=1 order by c_order";
                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		return jdbcTemplate.queryForList(sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map getInnerSummaryMessage(String cityid, String industyid,
			String activityid, String title,int start,int limit) {
		StringBuffer sb = new StringBuffer(
				"select t.c_id, t.c_cityid,t.c_propertyid,t.c_title,(select b.c_industryid from T_ACTIVITY_BRAND_INFO b WHERE b.c_id=t.c_brandid ) AS c_industryid,t.c_sourcesurl   from T_ACTIVITY_BASE_INFO t where t.c_edate >= SYSDATE and t.c_islive=1 and (SELECT d.c_islive FROM T_ACTIVITY_BRAND_INFO d WHERE d.c_id= t.c_brandid)=1 AND SYSDATE>t.c_actiondate ");
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
			sb.append("and t.c_title like ?");
			list.add("%"+title+"%");
		}
		
		int count =jdbcTemplate.queryForInt(sb.toString().replace("t.c_id, t.c_cityid,t.c_propertyid,t.c_title,(select b.c_industryid from T_ACTIVITY_BRAND_INFO b WHERE b.c_id=t.c_brandid ) AS c_industryid,t.c_sourcesurl","count(*)"),list.toArray());
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

			Map map1 = JdbcTemplateUtils.getMap(sql1 + city, jdbcTemplate);// jdbcTemplate.queryForMap(sql1+city);
			Map map2 = JdbcTemplateUtils.getMap(sql3 + activity, jdbcTemplate);
			Map map3 = JdbcTemplateUtils.getMap(sql2 + industy, jdbcTemplate);
			
			
			if (map1 == null || "".equals(map1)) {
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
				jsonMap.put("industy", map3.get("c_name"));
			}
			jsonMap.put("title", map.get("c_title"));
			jsonMap.put("Url", map.get("c_sourcesurl")); 
			jsonMap.put("id", map.get("C_ID"));
			jsonList.add(jsonMap);
		}
		Map  map=new  HashMap();
		map.put("list", jsonList);
		map.put("count",count);
		return map;
	}

	@Override
	public void saveAll(String menuID, String cityID, String pic, Date date,
			String id) {
	
		String sql="insert  into  t_activity_advert_info(c_id,c_picurl,c_islive,c_date,c_activityid) values(SEQ_T_ACTIVITY_ADVERT_INFO.nextval,"+"'"+pic+"'"+","+"'"+1+"'"+","+"sysdate"+","+"'"+id+"'"+")";
		jdbcTemplate.execute(sql);
	}

	@Override
	public List getUpdateCityList(String id) {
		String sql = "select  C_ID,c_cname  from T_ACTIVITY_CITY_INFO  where c_level=2 and c_islive=1 and c_id="+id;
		return jdbcTemplate.queryForList(sql);
		
	}

	@Override
	public List getUpdateMenuList(String updateId) {
		String sql = "select t.c_id,t.c_name  from T_ACTIVITY_MENU_INFO t where t.c_islive=1 and t.c_id="+updateId;
		return jdbcTemplate.queryForList(sql);
	}

	@Override 
	public void saveUpdateAll(String menuID, String cityID, String pic,
			Date date, String id, String saveID) {
		String sql="update   t_activity_advert_info set c_menuid="+"'"+menuID+"'"+","+"c_cityid='"+cityID+"'"+","+"c_picurl='"+pic+"'"+","+"c_activityid='"+id+"'"+"where c_id="+saveID;
		jdbcTemplate.execute(sql);
	} 

	@Override
	public Map getPicsAndIds(String id) {
		String  sql="select  t.c_picurl ,t.c_activityid from   T_ACTIVITY_ADVERT_INFO t where t.c_islive =1 and t.c_id=?";
		return  JdbcTemplateUtils.getMap(sql, new Object[]{id}, jdbcTemplate);
	}

	@Override
	public Map getTitle(String id) { 
		return JdbcTemplateUtils.getMap("select c_title from T_ACTIVITY_BASE_INFO t where t.c_islive=1 and c_id=?", new Object[]{id}, jdbcTemplate);
	}

	@Override
	public void update(String iD, String pics, String ids) {
		String  sql="update  t_activity_advert_info set c_picURL=?,c_activityid=? where c_id=?";
		this.jdbcTemplate.update(sql, new Object[]{pics,ids,iD});
	}

	@Override
	public int isExist(String cityID, String menuID) {
		return this.jdbcTemplate.queryForInt("select count(t.c_id) from T_ACTIVITY_CITY_ADVERT t where t.c_menuid="+menuID+" and  t.c_cityid="+cityID);
	}

	@Override
	public String getNextID() {
		return Integer.valueOf(JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_T_ACTIVITY_ADVERT_INFO"))-1+"";
	}
 
	@Override
	public void saveNewTable(String cityID, String menuID, String nextID) {	
		String sql="insert  into T_ACTIVITY_CITY_ADVERT t(t.c_id,t.c_cityid,t.c_menuid,t.c_advertid,t.c_islive,t.c_date) values(SEQ_ACTIVITY_ADVERT_INFO.nextval,?,?,?,1,sysdate)";
		jdbcTemplate.update(sql, new Object[]{cityID,menuID,nextID});
	}

}
