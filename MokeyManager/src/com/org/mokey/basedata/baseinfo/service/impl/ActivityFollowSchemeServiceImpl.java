package com.org.mokey.basedata.baseinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.org.mokey.basedata.baseinfo.service.ActivityFollowSchemeService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActivityFollowSchemeServiceImpl implements
		ActivityFollowSchemeService {
	protected JdbcTemplate jdbcTemplate;
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
	public Map<String, Object> checkActivityFollowScheme(String c_id,
			String c_name, String value) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTIVITY_FOLLOW_SCHEME t where ").append(c_name).append("=?");//t.c_name=?
		List<String> args = new ArrayList<String>();
		args.add(value);
		//过滤自己
		if(StrUtils.isNotEmpty(c_id)){
			sql.append(" and t.c_id <>? ");
			args.add(c_id);
		}
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}

	@Override
	public void deleteActivityFollowScheme(String cId) {
		// TODO Auto-generated method stub
		String sql ="delete from T_ACTIVITY_FOLLOW_SCHEME t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[]{cId});
	}
	//查询所有行业
	@Override
	public Map<String, Object> findAllIndustryName() {
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_name from T_ACTIVITY_INDUSTRY_TYPE t ";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}

	@Override
	public Map<String, Object> getActivityFollowSchemeListMap(String cName,String cityid,
			int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_FOLLOW_SCHEME t where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(cName)){
			sql.append(" and t.c_name like ? ");
			args.add("%"+cName+"%");
		}
		if(StrUtils.isNotEmpty(cityid)){
			sql.append(" and t.c_cityid like ? ");
			args.add("%"+cityid+"%");
		}

		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_order ",start, limit, args).toString().replace("count(*)", "t.*,(select c_cname from T_ACTIVITY_CITY_INFO t2 where t2.c_id=t.c_cityid ) as P_NAME");
		
		List list = jdbcTemplate.queryForList(sql1, args.toArray());
		List addlist=new ArrayList();
		if(!StrUtils.isEmpty(list)){
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map)list.get(i);
				if(map.get("P_NAME")==null){
					map.put("P_NAME", 0);
				}
				
				addlist.add(map);
			}
		}
		ret.put("count", count);
		ret.put("list", addlist);
		
		return ret;
		
	}

	@Override
	public String savaActivityFollowScheme(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_FOLLOW_SCHEME");
			saveMap.put("c_id", seqId);
			saveMap.put("c_islive", 1);
			saveMap.put("c_data", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			saveMap.put("c_order", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_FOLLOW_SCHEME");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_FOLLOW_SCHEME");
		}
		return seqId;
	}

	@Override
	public Map<String, Object> findByName(String name) {
		Map<String, Object> ret  =new HashMap<String, Object>();
	
		StringBuffer sql =new StringBuffer("select t.c_id,t.c_cname from T_ACTIVITY_CITY_INFO t where c_level =2 and t.c_cname like ?");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(name)){
			sql.append(" and t.c_cname like ?");
			args.add("%"+name+"%");
		}
		List list = jdbcTemplate.queryForList(sql.toString(), args.toArray());
		ret.put("list", list);
		return ret;
	}

	@Override
	public Map<String, Object> findAllCityById(String provinceId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql ="select c_id,c_cname from T_ACTIVITY_CITY_INFO where c_parent_id =?";
		List args = new ArrayList();
		args.add(provinceId);
		List list = jdbcTemplate.queryForList(sql,args.toArray());
		ret.put("list", list);
		return ret;
	}

	@Override
	public Map<String, Object> findAllProvince() {
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql ="select c_id,c_cname from T_ACTIVITY_CITY_INFO where c_level =1";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}
	//查询行业对应的品牌信息
	@Override
	public Map<String, Object> findIndustryBrand(String c_industryid,String c_cname,int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_BRAND_INFO t where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_cname)){
			sql.append(" and t.c_cname like ? ");
			args.add("%"+c_cname+"%");
		}

		if(StrUtils.isNotEmpty(c_industryid)){
			sql.append(" and t.c_industryid = ? ");
			args.add(c_industryid);
		}
		
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		//(select c_name from T_ACTIVITY_INDUSTRY_TYPE t2 where t2.c_id=t.c_industryid ) as P_NAME
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_industryid,t.c_cname",start, limit, args).toString().replace("count(*)", "t.*,(select c_name from T_ACTIVITY_INDUSTRY_TYPE t2 where t2.c_id=t.c_industryid ) as P_NAME");
		
		List list = jdbcTemplate.queryForList(sql1, args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
		
	}

	@Override
	public int addCourse(Map<String, Object> saveParam) {
	    //设置参数集合,匹配sql语句中的参数
	    MapSqlParameterSource params=new MapSqlParameterSource();
//	    params.addValue("c_cityid", c_cityid);
//	    params.addValue("c_contentid", c_contentid);
	    
	    params.addValue("c_data",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	    params.addValue("c_name",saveParam.get("c_name"));
	    params.addValue("c_cityid",saveParam.get("c_cityid"));
	    params.addValue("c_name",saveParam.get("c_name"));
//	    insert into t_activity_city_content (c_id,c_cityid,c_contentid) values (SEQ_t_activity_city_content.Nextval,'8','8')
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    String sql = "insert into T_ACTIVITY_FOLLOW_SCHEME (c_id,c_name,c_cityid,c_islive,c_data,c_order) " +
	    "values(SEQ_ACTIVITY_FOLLOW_SCHEME.Nextval,:c_name,:c_cityid,1,:c_data,SEQ_ACTIVITY_FOLLOW_SCHEME.Nextval)";
	    //需要最后一个String集合列表参数，id表示表主键，否则也会出问题
	    getSimpleJdbcTemplate().getNamedParameterJdbcOperations().update(sql, params, keyHolder, new String[]{"c_id"});
	    Map<String, Object> km = keyHolder.getKeys();
	    Integer generatedId = Integer.valueOf(km.get("c_id").toString()); //keyHolder.getKeys() .getKey().intValue();
	    return generatedId;
	}

	@Override
	public Map<String, Object> findAllCity() {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql ="select c_id,c_cname from T_ACTIVITY_CITY_INFO where c_level =2";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
		
	}

	@Override
	public Map returnProvince(String  ID) {
		return JdbcTemplateUtils.getMap("select C_ID,C_CNAME from t_activity_city_info   where  c_id=(select t.c_parent_id from t_activity_city_info  t where  c_id=?)", new Object[]{ID}, jdbcTemplate);
	}

	@Override
	public Map getSelectCity(String pid) {
		Map  map=new HashMap();
		map.put("list", this.jdbcTemplate.queryForList("select  * from  T_ACTIVITY_FOLLOW_SCHEME t ,T_ACTIVITY_CITY_INFO v where t.c_cityid=v.c_id and v.c_parent_id="+pid));
		return map;
	}

	@Override
	public Map getNotSelectCity(String pid, String ids) {
		Map  map=new HashMap();
		map.put("list", this.jdbcTemplate.queryForList("select * from T_ACTIVITY_CITY_INFO where c_id  not in ("+ids.substring(0,ids.length()-1)+") and c_parent_id="+pid));
		return map;
	}

	@Override
	public List getSaveItemsForUpdate(String cityID) {
		String  sql  ="Select t.*,(select c_name from T_ACTIVITY_INDUSTRY_TYPE t2 where t2.c_id=t.c_industryid ) as P_NAME from T_ACTIVITY_BRAND_INFO  t where  t.c_id  in (select  c_brandid from T_ACTIVITY_FOLL_SCH_DETAIL where c_schemeid=(select C_ID  FROM T_ACTIVITY_FOLLOW_SCHEME where c_cityid="+cityID+"))";
		
		
		
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public void deleteTwoTablesByProvinceID(String provinceID) {
		//根据省的ID 删除 改省下 在 T_ACTIVITY_FOLLOW_SCHEME 这张表里面的数据
		String sql1="delete from T_ACTIVITY_FOLLOW_SCHEME where c_cityid in(select c_id from t_activity_city_info t where  t.c_parent_id="+provinceID+")";
		jdbcTemplate.execute(sql1);
		String  sql2="delete  from T_ACTIVITY_FOLL_SCH_DETAIL where  c_schemeid in (select  c_id from T_ACTIVITY_FOLLOW_SCHEME where c_cityid in(select c_id from t_activity_city_info t where  t.c_parent_id="+provinceID+"))";
		jdbcTemplate.execute(sql2);
	}

	@Override
	public List getSaveItemsForUpdateZero() {
		String sql ="";
		return jdbcTemplate.queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getSaveItemsForUpdateMoRen(String mainId) {
		
		String  sql="Select t.*,(select c_name from T_ACTIVITY_INDUSTRY_TYPE t2 where t2.c_id=t.c_industryid ) as P_NAME from T_ACTIVITY_BRAND_INFO  t where  t.c_id  in (select  c_brandid from T_ACTIVITY_FOLL_SCH_DETAIL where c_schemeid="+mainId+")";
		return  this.jdbcTemplate.queryForList(sql)  ;
	}

	@Override
	public void deleteTwoTablesByMoRenCityID(String mainID) {
	String  sql="delete  from T_ACTIVITY_FOLL_SCH_DETAIL t where  t.c_schemeid="+mainID;	
	String  sql2="delete  from T_ACTIVITY_FOLLOW_SCHEME t where  t.c_id="+mainID;
	this.jdbcTemplate.execute(sql2);
	this.jdbcTemplate.execute(sql);
	}
}
