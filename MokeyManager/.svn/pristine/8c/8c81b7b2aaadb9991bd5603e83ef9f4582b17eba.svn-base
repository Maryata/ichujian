package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.PhoneBrandDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class PhoneBrandDaoImpl implements PhoneBrandDao {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void deleteBrandInfo(String c_id) {
		String sql = "update T_BASE_PHONE_MODEL set C_ISLIVE=? where C_BRAND_ID=?";
		jdbcTemplate.update(sql, 0,c_id);
		sql = "update T_BASE_PHONE_BRAND set C_ISLIVE=? where C_ID=?";
		jdbcTemplate.update(sql, 0,c_id);
	}
	@Override
	public String saveBrandInfo(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("C_ID"))){
			seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_PHONE_BRAND");
			saveMap.put("C_ID", seqId);
			//saveMap.put("#SEQ#c_id", "SEQ_APP_INFO.nextval");
			saveMap.put("C_ISLIVE", "1");
			
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_PHONE_BRAND");
		}else{
			seqId = saveMap.get("C_ID")+"";
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("C_ID", saveMap.get("C_ID"));
			saveMap.remove("C_ID");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_BASE_PHONE_BRAND");
		}
		return seqId;
	}
	
	@Override
	public List<String> getModelByBrandId(String brandId){
		return jdbcTemplate.queryForList("select C_CODE from T_BASE_PHONE_MODEL where C_BRAND_ID=? order by C_CODE", new Object[]{brandId},String.class);
	}
	
	@Override
	public void deleteModel(String brandId,String modelCode){
		StringBuffer sql = new StringBuffer("delete from  T_BASE_PHONE_MODEL where C_BRAND_ID=?");
		List<Object> args = new ArrayList<Object>();
		args.add(brandId);
		if(StrUtils.isNotEmpty(modelCode)){
			args.add(modelCode);
			sql.append(" and C_CODE=? ");
		}
		jdbcTemplate.update(sql.toString(), args.toArray());
	}
	
	@Override
	public void saveModel(String brandId,String code){
		String sql = "INSERT INTO T_BASE_PHONE_MODEL(C_ID, C_BRAND_ID, C_CODE, C_ISLIVE) values(SEQ_PHONE_MODEL.nextval,?,?,?) ";
		jdbcTemplate.update(sql, brandId,code,1);
	}
	
	
	@Override
	public Map<String, Object> getBrandListMap(String c_name, int start,
			int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select count(*) FROM T_BASE_PHONE_BRAND b where b.C_ISLIVE=1 ");
		List<Object> args = new ArrayList<Object>();
		
		if(StrUtils.isNotEmpty(c_name)){
			sql.append(" and b.c_name like ? ");
			args.add("%"+c_name+"%");
		}
		
		int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		String sql1 = DaoUtil.addfy_oracle(sql, " b.C_ORDER ", start, limit, args).toString().replace("count(*)", "b.C_ID,b.C_CODE,b.C_NAME,b.C_SERIES,b.C_ISLIVE,b.C_ORDER , (select wmsys.wm_concat(m.C_CODE) from T_BASE_PHONE_MODEL m where m.C_BRAND_ID=b.C_ID) as C_MODEL_CODE");
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql1, args));
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

}
