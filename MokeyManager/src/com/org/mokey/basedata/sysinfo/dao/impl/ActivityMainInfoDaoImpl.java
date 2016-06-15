package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.analyse.service.NewsAppUseInfoService;
import com.org.mokey.basedata.sysinfo.dao.ActivityMainInfoDao;
import com.org.mokey.basedata.sysinfo.dao.SupplierDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActivityMainInfoDaoImpl implements ActivityMainInfoDao {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private  JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> findIndustry(String cityid,int start,int limit) {   
		// TODO Auto-generated method stub
		Map<String, Object> ret=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_CITY_INDUSTRY t where t.c_islive=1  ");
		List argsList=new ArrayList();
		if(StrUtils.isNotEmpty(cityid)){
			sql.append(" and t.c_cityid=?");
			argsList.add(cityid);
		}
		int count=jdbcTemplate.queryForInt(sql.toString(), argsList.toArray());
		//sql.append(" order by t.c_order asc  ");
		String sql1=DaoUtil.addfy_oracle(sql, start, limit, argsList).toString().replace("count(*)", " * ");
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql1, argsList.toArray());
		
		List<Map<String, Object>> industry=jdbcTemplate.queryForList("select t.c_id,t.c_name from  T_ACTIVITY_INDUSTRY_TYPE t where t.c_islive=1");
		
		if(StrUtils.isNotEmpty(cityid)){
			
			for (Map cityMap:list) {
				for(Map industrymap:industry){
				if(industrymap.get("C_ID").toString().equals(cityMap.get("C_INDUSTRYID").toString())){
					industrymap.put("C_MATCH", "1");
					industrymap.put("C_ORDER", cityMap.get("C_ORDER").toString());
					industrymap.put("C_TYPE", cityMap.get("C_TYPE").toString());
				}
		    }
		}
			
		}
			
		ret.put("count", industry.size());
		ret.put("list", industry);
		
		return ret;
	}
	
	@Override
	public Map<String, Object> findcity(){  //save modify
		// TODO Auto-generated method stub
		Map<String, Object> retMap=new HashMap<String, Object>();
	    List<Object> args=new ArrayList<Object>();
		String sql="select t.c_id,t.c_cname from T_ACTIVITY_CITY_INFO t where t.c_islive=1 and t.c_level=2";
        List list=jdbcTemplate.queryForList(sql);
        retMap.put("list", list);
		return retMap;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		String sql = "delete from  T_BASE_SUPPLIER where C_ID=?";
		jdbcTemplate.update(sql, new Object[]{id});
	}

	@Override
	public Map<String, Object> getList(String city,
			int start, int limit) {	
		// TODO Auto-generated method stub
		Map<String, Object> ret=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_CITY_INDUSTRY t,t_activity_city_info i where t.c_cityid=i.c_id ");
		List argsList=new ArrayList();
		if(StrUtils.isNotEmpty(city)){
			sql.append(" and i.c_cname like  ?");
			argsList.add("%"+city+"%");
		}
		int count=jdbcTemplate.queryForInt(sql.toString(), argsList.toArray());
		//sql.append(" order by t.c_order asc  ");
		String sql1=DaoUtil.addfy_oracle(sql, start, limit, argsList).toString().replace("count(*)", " distinct(t.c_cityid),i.c_cname ");
		List list=jdbcTemplate.queryForList(sql1, argsList.toArray());
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}

	@Override
	public void save(Map<String, Object>[] map,String cityid) {
		String sql2="select t.c_industryid from T_ACTIVITY_CITY_INDUSTRY t where t.c_islive=1 and t.c_cityid="+cityid;
		List list=jdbcTemplate.queryForList(sql2);
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Map> lists=new ArrayList<Map>();    //增加行业的列表
		
		Map<String,Object> dmaps=new HashMap<String,Object>();
		List<Map> dlist=new ArrayList<Map>();    //删除行业的列表
		
		Map<String,Object> umaps=new HashMap<String,Object>();
		List<Map> ulist=new ArrayList<Map>();    //更新行业的列表

		for (Map map2:map) {     //增加的行业
			boolean ishas=false;
			for (int i = 0; i < list.size(); i++) {
				Map map3=(Map) list.get(i);
				if(map2.get("id").toString().equals(map3.get("C_INDUSTRYID").toString())){
					ishas=true;
					break;
				}
			}
			if(!ishas){
				maps.put("industryid", map2.get("id").toString());
				maps.put("order", "".equals(map2.get("order"))?"":map2.get("order").toString());
				maps.put("type", "".equals(map2.get("type"))?"":map2.get("type").toString());
				lists.add(maps);
			}else{
				umaps.put("industryid", map2.get("id").toString());
				umaps.put("order", "".equals(map2.get("order"))?"":map2.get("order").toString());
				umaps.put("type", "".equals(map2.get("type"))?"":map2.get("type").toString());
				ulist.add(umaps);
			}
		}
		
			
		for (int i = 0; i < list.size(); i++) {   //删除的行业
			Map map3=(Map) list.get(i);
				boolean ishas=false;
				for (Map map2:map) {
				if(map3.get("C_INDUSTRYID").toString().equals(map2.get("id").toString())){
					ishas=true;
					break;
				}
				}
			if(!ishas){
				dmaps.put("industryid", map3.get("C_INDUSTRYID").toString());
				dlist.add(dmaps);
			}
		}
		
		if(lists.size()>0){
			for(Map<String,Object> map2 : lists){
				String sqlid=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_T_CITY_INDUSTRY1");
				String sql="insert into T_ACTIVITY_CITY_INDUSTRY (c_id,c_cityid,c_industryid,c_islive,c_order,c_type)" +
						"values("+sqlid+","+cityid+","+map2.get("industryid").toString()+",1,"+map2.get("order").toString()+","+map2.get("type").toString()+")";
				jdbcTemplate.update(sql);
			}
		}
		if(dlist.size()>0){
			for(Map<String,Object> map2 : dlist){
				String sql="delete from T_ACTIVITY_CITY_INDUSTRY t where t.c_cityid="+cityid+"  and  t.c_industryid="+map2.get("industryid").toString();
				jdbcTemplate.update(sql);
			}
		}
		if(ulist.size()>0){
			for(Map<String,Object> map2 : ulist){
				String sql="update T_ACTIVITY_CITY_INDUSTRY t set t.c_order="+map2.get("order").toString()+",t.c_type="+map2.get("type").toString()+" where t.c_cityid="+cityid+" and  t.c_industryid="+map2.get("industryid").toString();
				jdbcTemplate.update(sql);
			}
		}		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List getMain() {
		String sql = "select C_ID,C_SUPPLIER_CODE,C_COMPANY as C_SUPPLIER_NAME from T_BASE_SUPPLIER ";
		return jdbcTemplate.queryForList(sql);
	}

}
