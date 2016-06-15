package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.service.AdvertInfoService;
import com.org.mokey.basedata.sysinfo.service.EKAdvertInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.PathUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class EKAdvertInfoServiceImpl implements EKAdvertInfoService {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAdvertIfoListMap(String c_type, String c_name,
			int start, int limit) {
		Map ret = new HashMap();
		
//		StringBuffer sql = new StringBuffer("select count(adv.c_id)  from T_BASE_ADVERT_INFO adv left join T_BASE_APP_INFO app on adv.C_APPID=app.C_ID where 1=1 ");
		/** 2015-5-6 修改：表修改为游戏帮相关表 */
		//StringBuffer sql = new StringBuffer("select count(adv.c_id)  from T_EK_GAME_ADVERT_INFO adv left join T_EK_GAME_APP_INFO app on adv.C_APPID=app.C_ID where 1=1 ");
		List args = new ArrayList();
		
		/** 2015-5-6 修改：取消"app类型" */
//		if(StrUtils.isNotEmpty(c_type)){
//			sql.append(" and adv.c_type =? ");
//			args.add(c_type);
//		}
        StringBuffer sql = new StringBuffer("select count(1) from T_EK_GAME_advert_info t where 1=1");
        if(StrUtils.isNotEmpty(c_name)){
            sql.append(" and t.c_name like ? ");
            args.add("%"+c_name+"%");
        }
        int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		String sql1 = DaoUtil.addfy_oracle(sql, "t.c_order", start, limit, args).toString().replace("count(1)", "t.*");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,args.toArray());
		if (StrUtils.isNotEmpty(list)) {
			for (Map<String, Object> map : list) {
				String appid = map.get("C_APPID").toString();// 广告对应的app的ID
				String type = map.get("C_TYPE").toString();// 广告类型
				// 通过广告类型获取对应的表名
				String tbName = PathUtil.getConfig("tableName", "ek.tbName.".concat(type));
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
			seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_GAME_ADVERT_INFO");
			//log.debug("seqId:"+seqId);
			saveMap.put("c_id", seqId);
			//saveMap.put("#SEQ#c_id", "SEQ_ADVERT_INFO.nextval");
			/** 2015-5-6 修改：表修改为游戏帮相关表 */
//			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_ADVERT_INFO");
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_EK_GAME_ADVERT_INFO");
		}else{
			seqId = (String) saveMap.get("c_id");
			Map whereMap = new HashMap();
			whereMap.put("c_id", saveMap.get("c_id"));
			saveMap.remove("c_id");
//			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_BASE_ADVERT_INFO");
			/** 2015-5-6 修改：表修改为游戏帮相关表 */
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_EK_GAME_ADVERT_INFO");
			
		}
		return seqId;
	}
	
	@Override
	public Map<String, Object> checkAdvertIfo(String checkType, String value,
			String idVal) {
		Map<String, Object> ret = new HashMap<String, Object>();
//		String sql = "select count(*) from T_BASE_ADVERT_INFO where "+checkType+"=? ";
		/** 2015-5-27 修改：数据库表修改为“游戏帮”相关 */
		String sql = "SELECT COUNT(*) FROM T_EK_GAME_ADVERT_INFO WHERE "+checkType+"=? ";
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
		String sql = "delete from  T_EK_GAME_ADVERT_INFO where C_ID=?";
		jdbcTemplate.update(sql, new Object[]{c_id});
	}

	@Override
	public List<Map<String, Object>> getListByType(String type) {
		// 根据分类id读取表名
		String tbName = PathUtil.getConfig("tableName", "ek.tbName.".concat(type));
		String sql = "SELECT C_ID, C_NAME FROM " + tbName + " WHERE C_ISLIVE = 1";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public void delete(String id) {
		if( id == null || id.isEmpty() ) return;

		String sql = "delete from  T_EK_ACTIVITY_HEADLINE where C_ID=?";

		jdbcTemplate.update(sql, new Object[]{id});
	}

	@Override
	public Map<String, Object> list(String name, int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();

		StringBuilder sql = new StringBuilder(
				"select count(1) from T_EK_ACTIVITY_HEADLINE");
		StringBuilder sql0 = new StringBuilder("select c_id, c_eid, c_type, c_img, c_name, c_order from t_ek_activity_headline");

		List<Object> argsList = new ArrayList<Object>();

		if (StrUtils.isNotEmpty(name)) {
			sql.append(" where C_NAME like ?");
			sql0.append(" where c_name LIKE ?");
			argsList.add("%" + name + "%");
		}
		sql0.append(" order by c_order");

		int count = jdbcTemplate.queryForObject(sql.toString(), argsList
				.toArray(), Integer.class);

		String sql1 = DaoUtil.addfy_oracle(sql0, start, limit, argsList)
				.toString();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}

	@Override
	public void save(Map<String, Object> map) {
		final String tableName = "T_EK_ACTIVITY_HEADLINE";
		final String seqName = "seq_ek_activity_headline";
		final String idName = "C_ID";
		Object id_old = map.get(idName);

		if (!StringUtils.isEmpty(id_old)) {
			String sql = "select count(1) from T_EK_ACTIVITY_HEADLINE t where t.c_id=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[]{id_old}, Integer.class);

			if (count >= 1) {
				Map<String, Object> wMap = new HashMap<String, Object>();
				wMap.put(idName, id_old);
				JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap,
						tableName);
			} else {
				String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
						seqName);
				map.put(idName, sqlid);
				JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
						tableName);
			}
		} else {
			String sqlId = JdbcTemplateUtils.getSeqId(jdbcTemplate,
					seqName);
			map.put(idName, sqlId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
					tableName);
		}
	}

	@Override
	public Map<String, Object> categoryList() {
		Map<String, Object> ret = new HashMap<String, Object>();
		final String sql = "SELECT C_ID,C_NAME FROM T_EK_ACTIVITY_CATEGORY_INFO WHERE C_ISLIVE=1 AND C_TYPE=1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		ret.put("categories",list);

		return ret;
	}

	@Override
	public Map<String, Object> activityList() {
		Map<String, Object> ret = new HashMap<String, Object>();
		// 这里没加时间判断，所以有可能活动已过期
		final String sql = "SELECT t.C_ID,t.C_TITLE FROM T_EK_ACTIVITY_INFO t where t.c_islive=1 and t.c_audit_status=1 ORDER  BY t.c_edate desc";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		ret.put("activity",list);

		return ret;
	}


}
