package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.service.AppInfoService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.stereotype.Service;

@Service
public class AppInfoServiceImpl implements AppInfoService {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAppIfoListMap(String c_category, String c_type, String c_name,
			int start, int limit) {
		Map ret = new HashMap();
		
		/** 2015-5-20 修改：查询游戏列表，数据库表改为：T_GAME_APP_INFO */
//		StringBuffer sql = new StringBuffer("select count(*) from T_BASE_APP_INFO where C_ISLIVE=1 ");
		StringBuffer sql = new StringBuffer("select count(*) from T_GAME_APP_INFO where C_ISLIVE=1 ");
		List args = new ArrayList();
		
		if(StrUtils.isNotEmpty(c_category)){
			sql.append(" and c_category =? ");
			args.add(c_category);
		}
		if(StrUtils.isNotEmpty(c_type)){
			sql.append(" and c_type =? ");
			args.add(c_type);
		}
		if(StrUtils.isNotEmpty(c_name)){
			sql.append(" and c_name like ? ");
			args.add("%"+c_name+"%");
		}
		
		
		int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		String sql1 = DaoUtil.addfy_oracle(sql, " C_ORDER asc,C_ID ", start, limit, args).toString().replace("count(*)", "*");
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql1, args));
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}
	
	// 获取游戏APP列表
	@Override
	public List getGameAppInfoList() {
		String sql = "SELECT C_ID,C_NAME FROM T_GAME_APP_INFO where C_ISLIVE=1 order by C_NAME";
		List list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String saveAppInfo(Map saveMap) {
		String seqId = null;
		if(StrUtils.isEmpty(saveMap.get("c_id"))){
			seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_APP_INFO");
			//log.debug("seqId:"+seqId);
			saveMap.put("c_id", seqId);
			
			/*************************************/
			HtmlUtil htmlUtil = new HtmlUtil();
			String sharePath = htmlUtil.createGameHtml( saveMap );
			//log.info( sharePath );
			saveMap.put( "C_SHARE_PATH", sharePath );
			/*************************************/
			
			//saveMap.put("#SEQ#c_id", "SEQ_APP_INFO.nextval");
			
			saveMap.put("C_PUBLISH_DATE", new java.util.Date());
			saveMap.put("C_ISLIVE", "1");
			
//			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_BASE_APP_INFO");
			/** 2015-5-4 修改 ： 将App数据存入"游戏帮"相关表 */
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_GAME_APP_INFO");
		}else{
			seqId = (String) saveMap.get("c_id");
			
			/*************************************/
			HtmlUtil htmlUtil = new HtmlUtil();
			String sharePath = htmlUtil.createGameHtml( saveMap );
			//log.info( sharePath );
			saveMap.put( "C_SHARE_PATH", sharePath );
			/*************************************/
			
			Map whereMap = new HashMap();
			whereMap.put("c_id", saveMap.get("c_id"));
			saveMap.remove("c_id");
			
			/** 2015-5-4 修改 ： "游戏帮"相关表 */
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_GAME_APP_INFO");
		}
		
		/** 2015-9-8 修改：保存游戏的同时保存游戏到“游戏分类”中 */
		this.joinCategory(seqId, (Integer)saveMap.get("c_type"));
		
		return seqId;
	}
	
	/**
	 * 存游戏的同时保存游戏到“游戏分类”中
	 * @param gid 游戏id
	 * @param type 分类id 
	 */
	private void joinCategory(String gid, Integer type) {
		try {
			// 查询该游戏是否存在于当前分类
			String sql = "SELECT COUNT(1) FROM T_GAME_APP_CATEGORY " +
					     " WHERE C_CID = ? AND C_GID = ?";
			int count = jdbcTemplate.queryForObject(sql, new Object[]{type, gid}, Integer.class);
			if(count > 0){
				// 如果存在，删除原分类中的该游戏
				sql = "DELETE FROM T_GAME_APP_CATEGORY WHERE C_CID = ? AND C_GID = ?";
				jdbcTemplate.update(sql, new Object[]{type, gid});
			}
			// 新增游戏到指定分类
			sql = "INSERT INTO T_GAME_APP_CATEGORY (C_ID, C_GID, C_CID)" +
				  " VALUES (SEQ_GAME_APP_CATEGORY.NEXTVAL,?,?)";
			jdbcTemplate.update(sql, new Object[]{gid, type});
		} catch (Exception e) {
			log.error("AppInfoServiceImpl.joinCategory failed, e : " + e);
		}
	}

	public void deleteAppIfo(String c_id) {
//		String sql = "update T_BASE_APP_INFO set  C_ISLIVE=0 where C_ID=?";
		/** 2015-5-22 修改：数据库表改为游戏帮相关 */
		String sql = "UPDATE T_GAME_APP_INFO SET C_ISLIVE=0 WHERE C_ID=?";
		jdbcTemplate.update(sql, new Object[]{c_id});
		
		/** 2015-5-27 新增：删除游戏的同时，还需要删除“合集与游戏中间表的数据” */
		/** 2016-03-15 删除游戏的同时不删除中间表数据（因为从中间表获取数据的时候会关联主表，而主表没有的数据就不会被查出来） */
//		String sql2 = "DELETE FROM T_GAME_COLLECTION_GAME T WHERE T.C_GID = ?";
//		jdbcTemplate.update(sql2, new Object[]{c_id});
	}
	

	@Override
	public List<Object> getAppIfoListByType(String c_category) {
		String sql = "SELECT C_ID,C_NAME FROM T_BASE_APP_INFO where C_ISLIVE=1 and C_CATEGORY=? order by C_ORDER,C_NAME";
		List list = jdbcTemplate.queryForList(sql, new Object[]{c_category});
		return list;
	}
	
	@Override
	public Map<String, Object> checkAppIfo(String checkType, String value,
			String idVal) {
		List<Object> args = new ArrayList<Object>();
		Map<String, Object> ret = new HashMap<String, Object>();
//		String sql = "select count(*) from T_BASE_APP_INFO where C_ISLIVE=1 ";
		/** 2015-5-27 修改：数据库表修改为“游戏帮”相关 */
		String sql = "select count(*) from T_GAME_APP_INFO where C_ISLIVE=1 ";
		
//		if(checkType.equalsIgnoreCase("c_picurl")){
//			sql +="and "+checkType+" like ? ";
//			args.add("%"+value+"%");
//		}else{
//			sql +="and "+checkType+" = ? ";
//			args.add(value);
//		}
		sql +="and "+checkType+" = ? ";
		args.add(value);
		
		if(StrUtils.isNotEmpty(idVal)){
			sql +=" and C_ID<>? ";
			args.add(idVal);
		}
		log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql, args));
		int count = jdbcTemplate.queryForInt(sql, args.toArray());
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}



	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
