package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.AdvertInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.PathUtil;
import com.org.mokey.util.StrUtils;

public class AdvertInfoServiceImpl implements AdvertInfoService {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAdvertIfoListMap(String c_type, String c_name,
			int start, int limit) {
		Map ret = new HashMap();
		
//		StringBuffer sql = new StringBuffer("select count(adv.c_id)  from T_BASE_ADVERT_INFO adv left join T_BASE_APP_INFO app on adv.C_APPID=app.C_ID where 1=1 ");
		/** 2015-5-6 修改：表修改为游戏帮相关表 */
		//StringBuffer sql = new StringBuffer("select count(adv.c_id)  from T_GAME_ADVERT_INFO adv left join T_GAME_APP_INFO app on adv.C_APPID=app.C_ID where 1=1 ");
		List args = new ArrayList();
		
		/** 2015-5-6 修改：取消"app类型" */
//		if(StrUtils.isNotEmpty(c_type)){
//			sql.append(" and adv.c_type =? ");
//			args.add(c_type);
//		}
        StringBuffer sql = new StringBuffer("select count(1) from t_game_advert_info t where 1=1");
        if(StrUtils.isNotEmpty(c_name)){
            sql.append(" and t.c_name like ? ");
            args.add("%"+c_name+"%");
        }
        int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		String sql1 = DaoUtil.addfy_oracle(sql, null, start, limit, args).toString().replace("count(1)", "t.*");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,args.toArray());
		if (StrUtils.isNotEmpty(list)) {
			for (Map<String, Object> map : list) {
				String appid = map.get("C_APPID").toString();// 广告对应的app的ID
				String type = map.get("C_TYPE").toString();// 广告类型
				// 通过广告类型获取对应的表名
				String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
				sql.delete(0, sql.length());
				args.clear();
				sql.append("select t.c_name from ");
				sql.append(tbName);
				sql.append(" t where t.c_id = ? and t.c_islive = 1");
				args.add(appid);
				List<Map<String,Object>> appName = jdbcTemplate.queryForList(sql.toString(), args.toArray());
				if (StrUtils.isNotEmpty(appName)) {
					map.put("C_APPNAME", StrUtils.emptyOrString(appName.get(0).get("C_NAME")));
				} else {
					map.put("C_APPNAME", "");
				}
			}
		}
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String saveAdvertInfo(Map saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
//			seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ADVERT_INFO");
			/** 2015-5-6 修改：序列修改为游戏帮相关序列 */
			seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_ADVERT_INFO");
			//log.debug("seqId:"+seqId);
			saveMap.put("c_id", seqId);
			//saveMap.put("#SEQ#c_id", "SEQ_ADVERT_INFO.nextval");
			/** 2015-5-6 修改：表修改为游戏帮相关表 */
//			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_ADVERT_INFO");
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_GAME_ADVERT_INFO");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map whereMap = new HashMap();
			whereMap.put("c_id", saveMap.get("c_id"));
			saveMap.remove("c_id");
//			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_BASE_ADVERT_INFO");
			/** 2015-5-6 修改：表修改为游戏帮相关表 */
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_GAME_ADVERT_INFO");
			
		}
		return seqId;
	}
	
	@Override
	public Map<String, Object> checkAdvertIfo(String checkType, String value,
			String idVal) {
		Map<String, Object> ret = new HashMap<String, Object>();
//		String sql = "select count(*) from T_BASE_ADVERT_INFO where "+checkType+"=? ";
		/** 2015-5-27 修改：数据库表修改为“游戏帮”相关 */
		String sql = "SELECT COUNT(*) FROM T_GAME_ADVERT_INFO WHERE "+checkType+"=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(value);
		if(StrUtils.isNotEmpty(idVal)){
			sql +=" and C_ID<>? ";
			args.add(idVal);
		}
		int count = jdbcTemplate.queryForInt(sql, args.toArray());
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}
	
	public void deleteAdvertIfo(String c_id) {
//		String sql = "delete from  T_BASE_ADVERT_INFO where C_ID=?";
		String sql = "delete from  T_GAME_ADVERT_INFO where C_ID=?";
		jdbcTemplate.update(sql, new Object[]{c_id});
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String, Object>> getListByType(String type) {
		// 根据分类id读取表名
		String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
		String sql = "SELECT C_ID, C_NAME FROM " + tbName + " WHERE C_ISLIVE = 1";
		return jdbcTemplate.queryForList(sql);
	}

	

}
