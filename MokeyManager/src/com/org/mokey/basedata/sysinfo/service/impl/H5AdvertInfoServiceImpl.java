package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.H5AdvertInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.task.ScheduledTasks;
import com.org.mokey.util.StrUtils;

public class H5AdvertInfoServiceImpl implements H5AdvertInfoService {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAdvertIfoListMap(String c_type, String c_name,
			int start, int limit) {
		Map ret = new HashMap();
		
		StringBuffer sql = new StringBuffer("select count(adv.c_id)  from T_GAME_H5_ADVERT_INFO adv left join T_GAME_H5_INFO app on adv.C_APPID=app.C_ID where 1=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_name)){
			sql.append(" and adv.c_name like ? ");
			args.add("%"+c_name+"%");
		}
		
		int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		String sql1 = DaoUtil.addfy_oracle(sql, " adv.C_ORDER asc,adv.C_ID ", start, limit, args).toString().replace("count(adv.c_id)", "adv.*,app.C_NAME as C_APPNAME");
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}
	
	public List<Map<String, Object>> h5AdvertList(){
		try {
			String sql = "SELECT T.C_ID ID, T.C_APPID APPID, T.C_PICURL PICURL, T.C_NAME NAME," +
					" G.C_APPURL APPURL FROM T_GAME_H5_ADVERT_INFO T, T_GAME_H5_INFO G " +
					"WHERE T.C_APPID = G.C_ID AND G.C_ISLIVE = 1 ORDER BY T.C_ORDER";
			return jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			log.error("H5AdvertInfoServiceImpl.h5AdvertList failed, e : " + e);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String saveAdvertInfo(Map saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_H5_ADVERT_INFO");
			saveMap.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_GAME_H5_ADVERT_INFO");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map whereMap = new HashMap();
			whereMap.put("c_id", saveMap.get("c_id"));
			saveMap.remove("c_id");
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_GAME_H5_ADVERT_INFO");
			
		}
		// 生成html
		ScheduledTasks st = new ScheduledTasks();
		st.generator();
		return seqId;
	}
	
	@Override
	public Map<String, Object> checkAdvertIfo(String checkType, String value,
			String idVal) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql = "SELECT COUNT(*) FROM T_GAME_H5_ADVERT_INFO WHERE "+checkType+"=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(value);
		if(StrUtils.isNotEmpty(idVal)){
			sql +=" and C_ID<>? ";
			args.add(idVal);
		}
		int count = jdbcTemplate.queryForObject(sql, args.toArray(), Integer.class);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}
	
	public void deleteAdvertIfo(String c_id) {
		String sql = "delete from  T_GAME_H5_ADVERT_INFO where C_ID=?";
		jdbcTemplate.update(sql, new Object[]{c_id});
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	

}
