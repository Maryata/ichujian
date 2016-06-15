package com.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sys.util.StrUtils;

public class ActivityDetailServiceImpl extends SqlMapClientDaoSupport implements ActivityDetailService {
	private Logger log = (Logger.getLogger(SetServiceImpl.class));
	
	private JdbcTemplate jdbcTemplate;	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	//	@Override
	//活动详情接口
	public List getActivityDetail(String activityid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityid", activityid);
		List list = getSqlMapClientTemplate().queryForList("detail.getActivityDetail",map);
		return list;
	}
	@Override
	
	//我要参加,收藏等
	public void joinActivityAndCollectionToShare(String uid, String activityid, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("activityid", activityid);
		map.put("type", type);
		map.put("date", new Date());
		getSqlMapClientTemplate().insert("detail.joinActivityAndCollectionToShare",map);
		
	}
	

	//品牌详情
	@Override
	public List brandDetail(String brandid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandid", brandid);
		List list = getSqlMapClientTemplate().queryForList("detail.brandDetail", map);
 		return list;
	}
	
	//品牌相关的活动及分页接口
//	@Override
//	public List brandAboutActivity(String cityid, String brandid,
//			int pageIndex) {
//		/*Map<String, Object> map = new HashMap<String, Object>();
//		map.put("cityid", cityid);
//		map.put("brandid", brandid);
//		
////		map.put("pageIndex", pageIndex);
//		
//		int page=(pageIndex-1)*20;
//		map.put("page", page);
//		log.info("-------"+cityid+"-----"+brandid+"----"+page);
//		List list = getSqlMapClientTemplate().queryForList("detail.brandAboutActivity", map);*/
//		int page=(pageIndex-1)*20;
//		String sql="select * from (select rownum rt, t.c_id,t.c_title,t.c_sdate,t.c_edate,b.c_cname,b.c_logourl as brandImage,t.c_imageurl as activityImage"+
//                 " from T_ACTIVITY_BASE_INFO t,T_ACTIVITY_BRAND_INFO b"+
//                 " where t.c_brandid="+brandid+" and t.c_cityid="+cityid+" and t.c_brandid =b.c_id  order by t.c_id desc"+ 
//                 " ) where rt>"+page+" and rt<="+page+"+20";
//		
//		//log.info("---------------------------"+sql);
//		List list=jdbcTemplate.queryForList(sql);
// 		return list;
//	}
	public List brandAboutActivity(String brandid,int pageIndex){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandid", brandid);
		map.put("pageIndex", pageIndex);
		int page=(pageIndex-1)*20;
		map.put("page", page);
		
		List list = getSqlMapClientTemplate().queryForList("detail.brandAboutActivity",map);
		return list;
	}
	
	//品牌关注
	@Override
	public void brandAttention(String uid, String brandid, String type) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("brandid", brandid);
		map.put("type", type);
		map.put("date", new Date());
		getSqlMapClientTemplate().insert("detail.brandAttention", map);
	}
	//取消品牌关注
	@Override
	public void brandCancelAttention(String uid, String brandid,String type,String state) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("brandid", brandid);
		map.put("type", type);
		map.put("state", state);
		getSqlMapClientTemplate().update("detail.brandCancelAttention", map);
		
	}
	
	//关注该品牌的人数
	@Override
	public int attentionNum(String brandid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandid", brandid);
		int brandNum = (Integer) getSqlMapClientTemplate().queryForObject("detail.attentionNum", map);
		return brandNum;
	}
	
	//是否关注
	@Override
	public int isAttention(String uid, String brandid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("brandid", brandid);
		
		int isAtten =(Integer)getSqlMapClientTemplate().queryForObject("detail.isAttention", map);
		return isAtten;
//		List list  = getSqlMapClientTemplate().queryForList("detail.isAttention", map);
		
//		List<Map> attentions = getSqlMapClientTemplate().queryForList("detail.isAttention", map);
//		log.debug("attentions:"+attentions);
//		boolean isAtten =false;
//		if(StrUtils.isNotEmpty(attentions)){
//			for(Map item : attentions){
//				if("1".equals(item.get("C_STATE"))){
//					isAtten = true;
//					break;
//				}
//			}
//		}
//		return isAtten;
//		return jionNum;
//		Map map2 = (Map) list.get(0);
//		int isAtten =Integer.parseInt(String.valueOf(map2.get("c_type")));
		
	}
	@Override
	//是否参加、收藏
	public List  isJionOrCollect(String activityid, String uid) {
		
		//ret.put("isJion", "N");
		//ret.put("isCollect", "N");
		//
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("activityid", activityid);
		
		List list = getSqlMapClientTemplate().queryForList("detail.isJionOrCollect", params);
		
		if(StrUtils.isNotEmpty(list)){
//			for (Map map : list) {
//				for(int i=0;i<3;i++){
//					String type=(String) map.get("c_type");
//					if("1".equals(type)){
//						ret.put("isJion", "Y");
//					}else if("2".equals(type)){
//						ret.put("isCollection","Y");
//					}else if("3".equals(type)){
//						ret.put("isComment","Y");
//					}
//				}
//			}
			for (int j = 0; j < 3; j++) {
				boolean ishas=false;
				for (int i = 0; i < list.size(); i++) {
					Map map2=(Map) list.get(i);
					String value=map2.get("C_TYPE").toString();
					if(value.equals(j+1+"")){
						ishas=true;
						break;
					}
				}
				if(!ishas){
					Map ret= new HashMap();
		            ret.put("C_TYPE", j+1+"");
		            ret.put("C_STATE", "0");
		            list.add(ret);
				}
			}
		}else{
			for(int i=0;i<3;i++){
				Map ret= new HashMap();
	            ret.put("C_TYPE", i+1+"");
	            ret.put("C_STATE", "0");
	            list.add(ret);
			}
		}
		return list;
//		int isJionOrCollect =(Integer)getSqlMapClientTemplate().queryForObject("detail.isJionOrCollect",params);
	//	return isJionOrCollect;
//		List<HashMap> list =getSqlMapClientTemplate().queryForList("detail.isJionOrCollect", params);
		
//		if(list.size()==2){
//			ret.put("isJion", "Y");
//			ret.put("isCollect", "Y");
//		}else if(list.size()==1){
//			Map map2 = (Map) list.get(0);
//			System.out.println(map2.get("C_TYPE"));
//			if("1".equals(map2.get("C_TYPE").toString())){
//				System.out.println(map2.get("C_TYPE"));
//				ret.put("isJion", "Y");
//				ret.put("isCollect", "N");
//			}else if("2".equals(map2.get("C_TYPE").toString())){
//				System.out.println(map2.get("C_TYPE"));
//				ret.put("isJion", "N");
//				ret.put("isCollect", "Y");				
//			}	
//		}else{
//			ret.put("isJion", "N");
//			ret.put("isCollect", "N");
//		}
//		return ret;
	}
	//查询是否有关注记录
	@Override
	public int isExit(String uid, String brandid, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("brandid", brandid);
		map.put("type", 1);
//		List list  = getSqlMapClientTemplate().queryForList("detail.isAttention", map);
		
		int isExit = (Integer)getSqlMapClientTemplate().queryForObject("detail.isExit", map);

		return isExit;
	}

	//参加该活动的人数
	@Override
	public int jionNum(String activityid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityid", activityid);
		int jionNum = (Integer) getSqlMapClientTemplate().queryForObject("detail.jionNum",map);
		return jionNum;
	}
	@Override
	public void activityCancle(String uid, String activityid,
			String activityType, String state) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("activityid", activityid);
		map.put("activityType", activityType);
		map.put("state", state);
		getSqlMapClientTemplate().update("detail.activityCancel", map);
	}
	@Override
	public int isExitCollection(String uid, String activityid,
			String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("activityid", activityid);
		map.put("type", type);
		int isExit = (Integer)getSqlMapClientTemplate().queryForObject("detail.isExitCollection", map);
		return isExit;
	}

	//活动分类接口
	@Override
	public List activitySort() {
		List list =getSqlMapClientTemplate().queryForList("detail.activitySort");
		return list;
	}

	@Override
	public void record(String uid, String activityid) {
		// TODO Auto-generated method stub
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("activityid", activityid);
		map.put("date", new Date());
		getSqlMapClientTemplate().insert("detail.record", map);
	}
	@Override
	public void record2(String uid, String brandid) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("imei", uid);
		map.put("activityid", brandid);
		map.put("date", new Date());
		getSqlMapClientTemplate().insert("detail.record2", map);
		
	}
	@Override
	public int isFavoriteAct(String uid, String activityid, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("activityid", activityid);
		map.put("type", type);
		int isExit = (Integer)getSqlMapClientTemplate().queryForObject("detail.isFavoriteAct", map);
		return isExit;
	}

}
