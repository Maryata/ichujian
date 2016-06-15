package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionRecommendSchemeService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

public class ActivityRecommendSchemeAction extends AbstractAction {
	
	private ActionRecommendSchemeService actionRecommendSchemeService;
	
	
	public ActionRecommendSchemeService getActionRecommendSchemeService() {
		return actionRecommendSchemeService;
	}


	public void setActionRecommendSchemeService(
			ActionRecommendSchemeService actionRecommendSchemeService) {
		this.actionRecommendSchemeService = actionRecommendSchemeService;
	}


	//获取数据
	public String getActivityRecommendScheme(){
		log.debug("init getActionRecommendScheme");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_name=getParameter("c_name");
		String c_cityid=getParameter("c_cityid");
		String c_type=getParameter("c_type");
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.actionRecommendSchemeService.getActionRecommendScheme(c_name,c_cityid,c_type, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActionRecommendScheme",e);
		}
		log.debug("out getActionRecommendScheme");
		return NONE;
	}
	//删除
	public String deleteActivityRecommendScheme(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.actionRecommendSchemeService.deleteActionRecommendScheme(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActionEareInfo failed,",e);
		}
		return NONE;
	}
	//增加
	public String saveActivityRecommendScheme(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String cityid=getParameter("cityid");
			String type=getParameter("type");
			String schemename=getParameter("schemename");
			String data =getParameter("checkMsg");
			String advId =getParameter("meunid");
			//String meunid =getParameter("meunid");
			
			String c_id =getParameter("schemeId");
			
			Map<String,Object>[] datas = (Map[]) JSONUtil.JSONString2ObjectArray(data, Map.class);
			
			Map<String, Object> saveParam =new HashMap<String, Object>();
			saveParam.put("c_id", c_id);
			saveParam.put("c_cityid", cityid);
			saveParam.put("c_type", type);
			saveParam.put("c_name", schemename);
			String schemeId = "";
			//List schemeList = actionRecommendSchemeService.queryRecommendScheme(cityid, type);
			schemeId = actionRecommendSchemeService.savaActionRecommendScheme(saveParam);
			if(c_id!=null&&!c_id.equals("")){//edit
				//actionRecommendSchemeService.deleteRecommendScheme(cityid, type);//删除主表
				actionRecommendSchemeService.deleteSchemeDetail(c_id);//删除详细表
			}
			
//			System.out.println(StrUtils.isNotEmpty(advId));
			if(datas!=null&&datas.length>0){//保存详细
				for(int i = 0 ; i <datas.length ; i++){
					String contentid = datas[i].get("cid").toString();
					log.info("-------------------------------"+contentid);
					
					String order=datas[i].get("order").toString();
			    	if(StrUtils.isEmpty(order)||order==null){
			    		order="1";
			    	}
						//int schemeIds = actionRecommendSchemeService.querySchenmeList(cityid, contentid);
						//contentid =  String.valueOf(schemeIds) ;
						Map<String,Object> saveItem = new HashMap<String,Object>();
				    	saveItem.clear();

				    	saveItem.put("c_schemeid", schemeId);
				    	saveItem.put("c_order", order);
				    	saveItem.put("c_typeid", contentid);//
				    	saveItem.put("c_advertid", advId==null?0:advId);
				    	actionRecommendSchemeService.savaRecommendSchemeDetail(saveItem);
				}
			}
		/*	//方案ID
		    String c_schemeid =  actionRecommendSchemeService.savaActionRecommendScheme(saveParam);
		    
		    //方案详细的修改
		    *//**
		     * 1：将后来获取到的数据与之前的数据进行比较，
		     * 2：取两者交集，前者多的话删除掉，后者多的话就增加
		     *//*
		    List<Map<String,Object>> details = actionRecommendSchemeService.findContentTypeById(c_schemeid);
		    //删除;新增
		    List<String> deletes = new ArrayList<String>();
		    List<Map<String,Object>> adds = new ArrayList<Map<String,Object>>();
		    if(StrUtils.isNotEmpty(details)){
		    	//新数据位空;全部删除
		    	if(StrUtils.isEmpty(datas)){
		    		for(Map<String,Object> item : details ){
		    			deletes.add(String.valueOf(item.get("C_ID")));
			    	}
		    	}else{
		    		for(int i=0;i<datas.length;i++){
		    			boolean isHave = false;
		    			for(Map<String,Object> item : details ){
			    			if(item.get("C_TYPEID").toString().equals(datas[i].get("tid").toString())){
			    				if(StrUtils.isNotEmpty(datas[i].get("cid"))  && String.valueOf(item.get("c_advertid")).equals(String.valueOf(datas[i].get("c_advertid")))){
			    					isHave = true;
				    			}
			    			}
			    		}
		    			if(!isHave){//未找到;添加的
		    				adds.add(datas[i]);
		    			}
			    	}
		    		
		    		for(Map<String,Object> item : details ){
		    			boolean isHave = false;
			    		for(int i=0;i<datas.length;i++){
			    			if(item.get("C_TYPEID").toString().equals(datas[i].get("tid").toString())){
			    				isHave = true;
			    			}
			    		}
		    			if(!isHave){//未找到;删除的
		    				deletes.add(String.valueOf(item.get("C_ID")));
		    			}
			    	}
		    	}
		    }else{//全部add
		    	adds =  Arrays.asList(datas);
		    }
		    
		    for(String cid : deletes ){ //删除数据;
		    	actionRecommendSchemeService.deleteById(cid);
		    }
		    Map<String,Object> saveItem = new HashMap<String,Object>();
		    for(Map<String,Object> item : adds){//保存新增的数据;
		    	saveItem.clear();
		    	saveItem.put("c_schemeid", c_schemeid);
		    	
		    	saveItem.put("c_typeid", item.get("tid"));//
		    	saveItem.put("c_advertid", item.get("c_advertid"));
		    	saveItem.put("c_id", item.get("cid"));
		    	actionRecommendSchemeService.savaRecommendSchemeDetail(saveItem);
		    }*/
			retMap.put("status", "Y");
			retMap.put("schemeId", schemeId);
			retMap.put("success", true);
		} catch (Exception e) {
			log.error("saveActionEareInfo failed,",e);
			retMap.put("success", false);
			retMap.put("msg", e.getMessage());
		}finally{
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	//查询所有的广告
	public String findAllAdvertise(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			retMap=actionRecommendSchemeService.findAllAdvertise();
			writeToResponse(JSONObject.fromObject(retMap).toString());
		}catch (Exception e) {
			log.error("findAllAdvertise failed",e);
		}
		
		return NONE;
	}
	
	//查询所有的推荐内容
	public String findAllRecommend(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			retMap=actionRecommendSchemeService.findAllRecommend();
			writeToResponse(JSONObject.fromObject(retMap).toString());
		}catch (Exception e) {
			// TODO: handle exception
			log.error("findAllRecommend failed",e);
		}
		return NONE;
	}
	
	//查询对应广告位的图片
	public String findAdvertiseimages(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String cityid=getParameter("c_cityid");
		String meunid=getParameter("c_meunid");
		try{
			retMap=actionRecommendSchemeService.findAdvertiseImages(cityid, meunid);
		 List list=	(List)retMap.get("list");
		 Iterator  it = list.iterator();
		 if(it.hasNext()){
			 retMap.put("result", true);
		 }else{
			 retMap.put("result", false);
		 }
			
			writeToResponse(JSONObject.fromObject(retMap).toString());
		}catch (Exception e) {
			// TODO: handle exception
			log.error("findAdvertiseimages failed",e);
		}
		return NONE;
	}
	
	//保存首页推荐方案表详情
	public String savaRecommendSchemeDetail(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> saveParam =new HashMap<String, Object>();
		String schemeid=getParameter("schemeid");
		String advertid=getParameter("advertid");
		String typeid=getParameter("typeid");
		saveParam.put("c_cityid", schemeid);
		saveParam.put("c_type", typeid);
		saveParam.put("c_name", advertid);
		
		String eareinfoId = this.actionRecommendSchemeService.savaRecommendSchemeDetail(saveParam);

		retMap.put("status", "Y");
		retMap.put("c_id", eareinfoId);
		retMap.put("success", true);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("savaRecommendSchemeDetail failed,",e);
		}
		return NONE;
	}
	//查询是否有广告位
	public String findById(){
		Map<String, Object> retMap =new HashMap<String, Object>();
		String cid = getParameter("C_ID");
		retMap=actionRecommendSchemeService.findById(cid);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	//根据城市id查询对应的contentid
	public String findContentId(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String cityid=getParameter("cityid");
//		String meunid=getParameter("c_meunid");
		try{
			List list =actionRecommendSchemeService.findContentId(cityid);
			retMap.put("list", list);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		}catch (Exception e) {
			// TODO: handle exception
			log.error("findAdvertiseimages failed",e);
		}
		return NONE;
	}

}
