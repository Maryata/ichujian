package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.org.mokey.basedata.baseinfo.service.ActionActThemeService;
import com.org.mokey.basedata.baseinfo.service.ActionIndustryTypeService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionActThemeServiceImpl implements ActionActThemeService {
	protected JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> getActionTypeListMap(String cName, int start,
			int limit) {
		Map<String, Object> ret= new HashMap<String, Object>();
		//1=1 and
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTIVITY_CONTENT_TYPE t where  t.c_id !=0");
		List args = new ArrayList();//存储条件
		
		if(StrUtils.isNotEmpty(cName)){
			sql.append(" and t.c_name like ?");
			args.add("%"+cName+"%");
		}
		//获得总条数
		int count =jdbcTemplate.queryForObject(sql.toString(), args.toArray(),Integer.class);
		//最总的查询语句
		String sql1 =DaoUtil.addfy_oracle(sql,"t.c_order", start, limit, args).toString().replace("count(*)", "t.c_id,t.c_name,t.c_islive,t.c_order,t.c_istitle");
		System.out.println(sql1+".......");
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("list", list);
		ret.put("count", count);
 		// TODO Auto-generated method stub
		return ret;
	}

	@SuppressWarnings({  "unchecked" })//*********************
	public String savaActionTypeInfo(Map<String, Object> saveMap) {
/*		Object object= saveMap.get("c_id");
		String sql ="select count(*) from T_BASE_INDUSTRY_TYPE t where t.c_id=?";
		List args= new ArrayList();
		args.add(object);
		int cn =jdbcTemplate.queryForObject(sql, args.toArray(), Integer.class);
		//int cn =jdbcTemplate.queryForObject(sql,Integer.class);//**************后面的参数的作用
		//saveMap.remove("c_id_old");
		if(cn>0){
			Map wmap = new HashMap();
			wmap.put("c_id", object);
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, wmap, "T_BASE_INDUSTRY_TYPE");
		}else{
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_INDUSTRY_TYPE");	
		}*/
		
		String seqId=null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){//没有ID 则执行添加方法,根据c_name查询,如果有这个条目,则使用哪个ID 
			String sql ="select c_id from T_ACTIVITY_CONTENT_TYPE where c_name ='"+saveMap.get("c_name")+"'"; 
			List list=jdbcTemplate.queryForList(sql);
//			Object id=map.get("C_ID");
			if(list.size()==0){
				seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_BASE_ACTIONTYPE");
				saveMap.put("c_id", seqId);
				JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_CONTENT_TYPE");
			}else{
				Map map=(Map) list.get(0);
				seqId=map.get("C_ID").toString();
			}
		}else{
			seqId=String.valueOf(saveMap.get("c_id")) ;
			Map wmap = new HashMap();
			wmap.put("c_id", saveMap.get("c_id"));
			saveMap.remove("c_id");
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, wmap, "T_ACTIVITY_CONTENT_TYPE");
		}
		return seqId;
	}
	
	//删除
	public void deleteActionTypeInfo(String c_id){
		String sql="delete from T_ACTIVITY_CONTENT_TYPE t where t.c_id =?";
		jdbcTemplate.update(sql, new Object[] {c_id});	
	}


	//根据姓名和id查询
	public Map<String, Object> checkActionTypeInfo(String c_id ,String c_name){
		Map<String, Object> ret =new HashMap<String, Object>();
		StringBuffer sql =new StringBuffer("select count(*) from T_ACTIVITY_INDUSTRY_TYPE t where t.c_name=?");
		List<Object> args = new ArrayList<Object>();
		args.add(c_name);
		
		if(StrUtils.isNotEmpty(c_id)){
			sql.append(" and t.c_id <>? ");
			args.add(c_id);
		}
		//int count=jdbcTemplate.queryForInt(sql, args.toArray());
		int count =jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		
		System.out.println(count);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}		
		
		return ret;
	}

	@Override
	public Map<String, Object> findAllName() {
		// TODO Auto-generated method stub
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_name from T_ACTIVITY_INDUSTRY_TYPE t ";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}
}
