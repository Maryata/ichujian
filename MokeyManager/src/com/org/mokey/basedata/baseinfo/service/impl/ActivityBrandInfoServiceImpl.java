package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.org.mokey.basedata.baseinfo.service.ActivityBrandInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.Cn2Spell;
import com.org.mokey.util.StrUtils;

public class ActivityBrandInfoServiceImpl implements ActivityBrandInfoService {
	protected JdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void init() {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
	}

	@Override
	public Map<String, Object> checkActivityBrandInfo(String c_id,String c_name,String value) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select count(*) from T_ACTIVITY_BRAND_INFO t where ").append(c_name).append("=? ");//t.c_name=?
		//.append("and").append(c_name1).append("=?")
		List<String> args = new ArrayList<String>();
		args.add(value);
		//


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
	public void deleteActivityBrandInfo(String c_id) {
		//删除品牌对应的查询信息;
		String sql = "delete from T_ACTIVITY_SEARCH_KEY where c_id in(select C_KEYID from T_ACTIVITY_BRAND_INFO where c_id=?) ";
		jdbcTemplate.update(sql, c_id);
		//删除品牌
		sql ="delete from T_ACTIVITY_BRAND_INFO t where t.c_id =?";
		jdbcTemplate.update(sql, c_id);
	}

	@Override
	public Map<String, Object> getActivityBrandInfoListMap(String c_cname,String c_industryid,
			int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer("select count(*) from T_ACTIVITY_BRAND_INFO t where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_cname)){
			sql.append(" and t.c_cname like ?");
			args.add("%"+c_cname+"%");
		}

		if(StrUtils.isNotEmpty(c_industryid)){
			sql.append(" and regexp_like (t.c_industryid,?)");
			//args.add("%"+c_industryid+"%");
			args.add("^(\\d+,)*"+c_industryid+"(,\\d+)*$"); // ((\d+,)+21(,\d+)+)|(^21(,\d+)+)|((\d+,)+21$) 
		}
		
		int count=jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
		//String sql1=DaoUtil.addfy_oracle(sql, "t.c_order ",start, limit, args).toString().replace("count(*)", "t.*,(select c_name from T_ACTIVITY_INDUSTRY_TYPE t2 where t2.c_id=t.c_industryid ) as P_NAME");
		String sql1=DaoUtil.addfy_oracle(sql, "t.c_order ",start, limit, args).toString().replace("count(*)", "*");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, args.toArray());
		
		_processIndustryType(list);
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
		
	}
	
	private void _processIndustryType(List<Map<String, Object>> list) {
		if(null == list || list.size() <= 0) return;
		
		// 拼接list数据包含的所有类别ID（含重复数据)
		StringBuffer sb = new StringBuffer();
		for(Iterator<Map<String, Object>> i = list.iterator(); i.hasNext();) {
			Map<String, Object> map = i.next();
			Object v = map.get( "C_INDUSTRYID" );
			if(v != null) {
				if(sb.length() == 0) {
					sb.append( v );
				} else {
					sb.append( "," ).append( v );
				}
			}
		}
		
		if(sb.length() >= 1) {
			String sql = "SELECT C_ID, C_NAME FROM T_ACTIVITY_INDUSTRY_TYPE WHERE C_ID IN(:ids)"; // C_ISLIVE = 1 AND 
			List<String> idsList =Arrays.asList( sb.toString().split( "," ) );
			// 去重
			Set<String> idsSet = new HashSet<String>(idsList);
			// 查询类型列表
			if(this.namedParameterJdbcTemplate == null) init( );
			SqlParameterSource paramSource = new MapSqlParameterSource("ids", idsSet);
			List<Map<String, Object >> l = namedParameterJdbcTemplate.queryForList( sql, paramSource );
			
			if(null != l) {
				for(Iterator<Map<String, Object>> i = list.iterator(); i.hasNext();) {
					Map<String, Object> map = i.next();
					String v = String.valueOf( map.get( "C_INDUSTRYID" ) ); //当前数据所属类别
					if(v != null) {
						String[] types = v.split( "," );
						StringBuffer _sb = new StringBuffer(); // 类别中文显示
						for(int j = 0; j < types.length; ++j) { // 循环该数据所属类别
							for(int k = 0; k < l.size(); ++k) { // 循环所有类别
								Map<String, Object> _m = l.get( k ); // 类别对象

								if(types[j].trim().equals( String.valueOf( _m.get( "C_ID" ) ) )) {
									if(_sb.length() == 0) {
										_sb.append( _m.get( "C_NAME" ) );
									} else {
										_sb.append(",").append( _m.get( "C_NAME" ) );
									}
									
									break; // 找到类别后跳出本次循环
								}
							}
						}
						map.put( "P_NAME", _sb.toString() );
					}
				}
			}
			
		}
		
		
		//SELECT C_ID,C_NAME FROM T_ACTIVITY_INDUSTRY_TYPE WHERE C_ISLIVE = 1 AND C_ID IN();
	}

	@Override
	public String savaActivityBrandInfo(Map<String, Object> saveMap) {
		/*Object object =  saveMap.get("c_id");
		String sql ="select count(*) from T_BASE_EARE_INFO t where t.c_id =?";
		List args = new ArrayList();
		args.add(object);
		int count=jdbcTemplate.queryForObject(sql, args.toArray(),Integer.class);
		if(count>0){
			Map wmap = new HashMap();
			wmap.put("c_id", object);
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, wmap, "T_BASE_EARE_INFO");
		}else{
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_EARE_INFO");
		}*/

		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_BRAND_INFO");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_BRAND_INFO");
		}else{
			seqId = saveMap.get("c_id")+"";
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_BRAND_INFO");
		}
		return seqId;
		
	}

	@Override
	public Map<String, Object> findAllName() {
		Map<String, Object> ret  =new HashMap<String, Object>();
		String sql ="select t.c_id,t.c_name from T_ACTIVITY_INDUSTRY_TYPE t where t.c_id!=0 and t.C_ISLIVE=1 order by t.C_ORDER ";
		List list = jdbcTemplate.queryForList(sql);
		ret.put("list", list);
		return ret;
	}

	@Override
	public List findById(String cId) {
		String sql ="select t.c_id,t.c_cname,t.c_industryid ,c.c_name as p_name from T_ACTIVITY_BRAND_INFO t inner join T_ACTIVITY_INDUSTRY_TYPE c on t.c_industryid =c.c_id where t.c_id=? ";
		List object =jdbcTemplate.queryForList(sql,cId);
		return object;
	}

	@Override
	public String savaActivityBrandSearchInfo(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_pinyin"))){
			saveMap.put("c_pinyin", Cn2Spell.converterToSpell(String.valueOf(saveMap.get("c_name"))));
		}
		//设置第一位字母
		saveMap.put("C_AB", String.valueOf(saveMap.get("c_pinyin")).substring(0, 1).toUpperCase());
		
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			//获得系统自动生成的id
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_SEARCH_KEY");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_SEARCH_KEY");
		}else{
			seqId = saveMap.get("c_id")+"";
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", saveMap.get("c_id"));
			
			saveMap.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_SEARCH_KEY");
		}
		return seqId;
	}
	
	@Override
	public Map getBrandSearchDetail(String id){
		String sql = "select * from T_ACTIVITY_SEARCH_KEY where c_id=?";
		return JdbcTemplateUtils.getMap(jdbcTemplate, sql, id);
	}

}
