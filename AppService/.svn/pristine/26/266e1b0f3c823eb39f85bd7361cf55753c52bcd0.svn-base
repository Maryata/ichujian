package com.sys.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;

import com.sys.commons.AbstractAction;
import com.sys.service.ActivityDetailService;
import com.sys.service.SearchService;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;

public class SearchAction extends AbstractAction{
	private SearchService searchService;
	private ActivityDetailService activityDetailService;
	
	
	//查询关键字接口
	public String GetSearch(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		log.info("into getSearch");
		try{
			List list =searchService.getSearch();
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("SearchAction.getSearch failed,",e);
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE; 
	}
	
	//搜索品牌
	public String GetSearchBrand(){
		Map<String, Object> retMap = new HashMap<String,Object> ();
		String textMsg=getParameter("textMsg");
		String imei=getParameter("imei");
		String pageIndex=getParameter("pageIndex");
//		String brandType="1";
		log.info("into getSearchBrand");
		log.info("--textMsg--"+textMsg+"--imei--"+imei);
		try{
			if(StrUtils.isEmpty(textMsg)||StrUtils.isEmpty(imei)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
				writeToResponse(JSONObject.fromObject(retMap).toString());
				return NONE;
			}
			List<HashMap> list = searchService.getSearchBrand(textMsg,imei,pageIndex);
//			if(StrUtils.isNotEmpty(list)){    
//				log.info("list.size:"+list.size());
//				for(Map m : list){
//					Object ob =m.get("C_ID");
//					String brandid =ob.toString();
//					int attentionNum =searchService.brandAttentionNum(brandid,brandType);  //品牌关注人数
//					int isAttention =searchService.isAttention(brandid, uid,brandType);    //是否关注
//					if(isAttention>0){
//						m.put("isAttention", "Y");
//					}else{
//						m.put("isAttention", "N");
//					}
//					m.put("attentionNum", attentionNum);
//				}
//			}
			searchService.setSearchRecord(textMsg,imei);
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("SearchAction.getSearchBrand failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	//搜索活动
	public String GetSearchActivity(){
		Map<String, Object> retMap = new HashMap<String,Object> ();
		String textMsg=getParameter("textMsg");
		String cityid=getParameter("cityid");
		String uid=getParameter("uid");
		String pageIndex=getParameter("pageIndex");
		log.info("into getSearchActivity");
		log.info("--textMsg--"+textMsg+"--cityid--:"+cityid+"--uid--"+uid);
		try{
			if(StrUtils.isEmpty(textMsg)||StrUtils.isEmpty(cityid)||StrUtils.isEmpty(uid)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
				writeToResponse(JSONObject.fromObject(retMap).toString());
				return NONE;
			}
			List returnList = searchService.getSearchActivity(textMsg, cityid, uid,pageIndex);
			//returnList=JSONUtil.jsonListToList(returnList);
			searchService.setSearchRecord(textMsg, uid);
			retMap.put("list", returnList);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("SearchAction.getSearchActivity failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	
	public ActivityDetailService getActivityDetailService() {
		return activityDetailService;
	}

	public void setActivityDetailService(ActivityDetailService activityDetailService) {
		this.activityDetailService = activityDetailService;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	
}
