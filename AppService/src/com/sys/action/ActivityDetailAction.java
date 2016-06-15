package com.sys.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.a;

import org.apache.commons.collections.map.HashedMap;

import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.service.ActivityDetailService;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;

public class ActivityDetailAction extends AbstractAction{
	
	private ActivityDetailService activityDetailService;
	
	public ActivityDetailService getActivityDetailService() {
		return activityDetailService;
	}

	public void setActivityDetailService(ActivityDetailService activityDetailService) {
		this.activityDetailService = activityDetailService;
	}

	//活动详情
	public String GetActivityDetail(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid =getParameter("uid");
		String activityid =getParameter("activityid");
		log.info("into getActivityDetail");
		log.info("--activityid--"+activityid+" ,uid:"+uid);
		
		try{
			if(StrUtils.isEmpty(activityid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			int isExitCollection=0;
			if("".equals(uid)||null==uid){
				uid="999999999999999";   //游客ID
			}else{
				//isExitCollection =activityDetailService.isExitCollection(uid, activityid, "2");  //判断是否收藏
				isExitCollection = activityDetailService.isFavoriteAct(uid, activityid, "2");
			}
			
			activityDetailService.record(uid, activityid);  //浏览记录
			List activitylist = activityDetailService.getActivityDetail(activityid); //活动详细信息

			activitylist=JSONUtil.jsonListToList(activitylist);
			Map map = (Map) activitylist.get(0);
			String brandid =(String) map.get("C_BRANDID");
			int activityNum=activityDetailService.jionNum(activityid);  //参加活动人数
			int brandNum=activityDetailService.attentionNum(brandid);     //关注该品牌人数
			
			retMap.put("jionNum", activityNum);
			retMap.put("attenNum", brandNum);

			

			retMap.put("status", "Y");
			retMap.put("activityDetail", activitylist);
			retMap.put("isExitCollection", isExitCollection);
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.getActivityDetail failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}
	
	//活动相关人数
	public String ActivityNum(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String activityid =getParameter("activityid");
		String type=getParameter("type");
		log.info("into BrandNum");
		log.info("--activityid--"+activityid+"--type--"+type);
		try{
			if(StrUtils.isEmpty(activityid)||StrUtils.isEmpty(type)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
		//int  brandNum =activityDetailService.attentionNum(type, activityid);
		//retMap.put("brandNum", brandNum);
		retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.jionActivity failed,",e);
		}
		return NONE;
	}
	
	//品牌相关人数
	public String BrandNum(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String brandid =getParameter("brandid");
		log.info("into BrandNum");
		log.info("--brandid--"+brandid);
		try{
			if(StrUtils.isEmpty(brandid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
		int  brandNum =activityDetailService.attentionNum(brandid);
		retMap.put("brandNum", brandNum);
		retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.BrandNum failed,",e);
		}
		return NONE;
	}
	
	//我要参加,收藏等
	public String JionActivityAndCollectionToShare(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid =getParameter("uid");
		String activityid =getParameter("activityid");
		String type=getParameter("type");
		log.info("into JionActivityAndCollectionToShare");
		log.info("--uid--"+uid+"--activityid--"+activityid+"--type--"+type);
		try{
			if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(activityid)||StrUtils.isEmpty(type)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			int isExitCollection =activityDetailService.isExitCollection(uid, activityid, type);
			if(isExitCollection>0){
				String state ="1";
				activityDetailService.activityCancle(uid, activityid, type, state);
			}else{
				activityDetailService.joinActivityAndCollectionToShare(uid, activityid, type);
			}
			
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.JionActivityAndCollectionToShare failed,",e);
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error(e);
		}	
		return NONE;
	}
	//取消收藏
	public String ActivityCancle(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid=getParameter("uid");
		String activityid=getParameter("activityid");
		String activityType=getParameter("activityType");
		String state="0";
		log.info("into BrandCancelAttention");
		log.info("--activityid--"+activityid+"--uid--"+uid);
		try{
			if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(activityid)||StrUtils.isEmpty(activityType)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			activityDetailService.activityCancle(uid, activityid,activityType,state);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.IsAttention failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	
	//品牌详情
	public String BrandDetail(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String brandid =getParameter("brandid");
		String uid =getParameter("uid");
		log.info("into BrandDetail");
		log.info("--brandid--"+brandid);
		try{
			if(StrUtils.isEmpty(brandid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			if("".equals(uid)||null==uid)
				uid="999999999999999";
			activityDetailService.record2(uid, brandid);
			List returnList = activityDetailService.brandDetail(brandid);
//			returnList=JSONUtil.jsonListToList(returnList);
			int brandNum=activityDetailService.attentionNum(brandid);  //品牌关注数
			retMap.put("brandNum", brandNum);
			int isAtten =activityDetailService.isAttention(uid, brandid);
			if(isAtten>0){
				retMap.put("isAtten", "Y");
			}else{
				retMap.put("isAtten", "N");
			}
			retMap.put("status", "Y");
			retMap.put("list", returnList);
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.brandDetail failed,",e);
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	

	
	//品牌关注
	public String BrandAttention(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid=getParameter("uid");
		String brandid=getParameter("brandid");
		String type=getParameter("type");
		log.info("into BrandAttention");
		log.info("--brandid--"+brandid+"--uid--"+uid);
		try{
			if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(brandid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			int isExit =activityDetailService.isExit(uid, brandid, type);
			if(isExit>0){
				String state="1";
				activityDetailService.brandCancelAttention(uid, brandid, type, state);
			}else{
				activityDetailService.brandAttention(uid, brandid, type);
			}
			
			retMap.put("status", "Y");		
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.IsAttention failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	
	//品牌取消关注
	public String BrandCancelAttention(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid=getParameter("uid");
		String brandid=getParameter("brandid");
		String type=getParameter("type");
		String state="0";
		log.info("into BrandCancelAttention");
		log.info("--brandid--"+brandid+"--uid--"+uid);
		try{
			if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(brandid)||StrUtils.isEmpty(type)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			activityDetailService.brandCancelAttention(uid, brandid,type,state);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.IsAttention failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	
	//品牌相关活动及分页
	public String BrandAboutActivity(){
		Map<String, Object> retMap = new HashMap<String, Object>();
	//	String cityid =getParameter("cityid");
		String brandid =getParameter("brandid");
		int pageIndex =Integer.parseInt(getParameter("pageIndex").toString());
		log.info("into BrandAboutActivity");
		log.info("--brandid--"+brandid+"--pageIndex--"+pageIndex);
		try{
//			if(StrUtils.isEmpty(cityid)||StrUtils.isEmpty(brandid)||StrUtils.isEmpty(pageIndex)){
//    			retMap.put("status", "N");
//    			retMap.put("info", "1001");
//    			writeToResponse(JSONObject.fromObject(retMap).toString());
//    			return NONE;
//			}
//			List returnList = activityDetailService.brandAboutActivity(cityid, brandid, pageIndex);
			
			if(StrUtils.isEmpty(brandid)||StrUtils.isEmpty(pageIndex)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			List returnList = activityDetailService.brandAboutActivity(brandid, pageIndex);
			//returnList=JSONUtil.jsonListToList(returnList);
			retMap.put("list", returnList);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.BrandAboutActivity failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return NONE;
	}
	
	//活动分类接口
	public String ActivitySort(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		log.info("into ActivitySort");
		List list =activityDetailService.activitySort();
		if(list.size()>0){
			retMap.put("status", "Y");
			retMap.put("list", list);
		}else{
			retMap.put("status", "N");
			retMap.put("info", "数据为空");
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
/*	//查看用户行为统计数量
	public String UserActionNum(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		log.info("into UserActionNum");
		String indexid=getParameter("indexid");
		String type=getParameter("type");
		String action=getParameter("action");
		try{
			if(StrUtils.isEmpty(indexid)||StrUtils.isEmpty(type)||StrUtils.isEmpty(action)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
				writeToResponse(JSONObject.fromObject(retMap).toString());
				return NONE;
			}
			int number=activityDetailService.userActionNum(indexid, type, action);
			retMap.put("userActionNum", number);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ActivityDetailAction.UserActionNum failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}*/

}
