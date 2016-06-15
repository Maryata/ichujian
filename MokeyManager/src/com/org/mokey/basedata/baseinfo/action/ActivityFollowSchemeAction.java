package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActivityFollowSchemeService;
import com.org.mokey.basedata.baseinfo.service.ActivitySchemeDetailService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

public class ActivityFollowSchemeAction  extends AbstractAction{
	private ActivityFollowSchemeService activityFollowSchemeService;
	private ActivitySchemeDetailService activitySchemeDetailService;
	
	
	public ActivitySchemeDetailService getActivitySchemeDetailService() {
		return activitySchemeDetailService;
	}

	public void setActivitySchemeDetailService(
			ActivitySchemeDetailService activitySchemeDetailService) {
		this.activitySchemeDetailService = activitySchemeDetailService;
	}

	public ActivityFollowSchemeService getActivityFollowSchemeService() {
		return activityFollowSchemeService;
	}

	public void setActivityFollowSchemeService(
			ActivityFollowSchemeService activityFollowSchemeService) {
		this.activityFollowSchemeService = activityFollowSchemeService;
	}
	

	//查询
	public String getActivityFollowSchemeList(){
		log.debug("init getActivityBrandInfoList");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_name=getParameter("c_name");
		String c_cityid =getParameter("c_cityid");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.activityFollowSchemeService.getActivityFollowSchemeListMap(c_name,c_cityid, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActivityFollowSchemeList",e);
		}
		log.debug("out getActivityFollowSchemeList");
		return NONE;
	}
	
	//删除
	public String deleteActivityFollowScheme(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.activityFollowSchemeService.deleteActivityFollowScheme(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActivityFollowScheme failed,",e);
		}
		return NONE;
	}
	//增加
	@SuppressWarnings("unchecked")
	public String saveActivityFollowScheme(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> saveMap = new HashMap<String, Object>();
		
		String schemeName =getParameter("schemeName");
		String cityid =getParameter("cityid");
		String data =getParameter("data");
		String ProvinceID=getParameter("ProvinceID");
		String MainID=getParameter("MainId");
//		String data1 =getRequest().getParameter("data");
//		char[] datas=data1.toCharArray();
		//如果省的ID 是空的话 则 代表是的添加，如果不是空的话则进行保存
		if(StrUtils.isNotEmpty(ProvinceID)){
			//在这里进行 根据省的ID  找到方案表里面 所有在这个省下面的都进行删除
			//先把记录都删了  然后随意你加
			activityFollowSchemeService.deleteTwoTablesByProvinceID(ProvinceID);
		}
		if(MainID!=null&&!MainID.equals("")){
		if(cityid.equals("0")){//
			activityFollowSchemeService.deleteTwoTablesByMoRenCityID(MainID);
			System.out.println("adf");
		}
		}
		if("0".equals(cityid)){
			saveMap.put("c_name", schemeName);
			saveMap.put("c_cityid", 0);
			//activityFollowSchemeService.savaActivityFollowScheme(saveMap);
			int c_schemeid=	activityFollowSchemeService.addCourse(saveMap);
			JSONArray array=JSONArray.fromObject(data);
			//Map [] array = (Map[]) JSONUtil.JSONString2ObjectArray(data, Map.class);
			for(int j =0;j<array.size();j++){
				Map<String, Object> saveMap2 = new HashMap<String, Object>();//Map<String, Object> saveMap2 = new HashMap<String, Object>();
		    	//Map json = array[j];
//		    	String c_order= String.valueOf(json.get("number")) ;
//		    	if(StrUtils.isEmpty(c_order)){
//		    		c_order = "1";
//		    	}
				JSONObject json=(JSONObject) array.get(j);
		    	//String c_brandid=json.getString("id");
//		    	String c_order=json.getString("number");
		    	saveMap2.put("c_brandid", json.getString("id"));
		    	saveMap2.put("c_order", "2");
		    	saveMap2.put("c_schemeid", c_schemeid);
		    	saveMap2.put("c_id", "");
		    	activitySchemeDetailService.savaActivitySchemeDetail(saveMap2);
			}
		}else{
			String strs[] =cityid.split(",");
			System.out.println(strs.length);
			for (int i = 0; i < strs.length; i++) {
				saveMap.put("c_name", schemeName);
				saveMap.put("c_cityid", strs[i]);
				//System.out.println(strs[i]);
				saveMap.put("c_id","");
				
				//System.out.println(saveMap);
				//activityFollowSchemeService.savaActivityFollowScheme(saveMap);
				int c_schemeid=	activityFollowSchemeService.addCourse(saveMap);
				//System.out.println(i);
				Map [] array = (Map[]) JSONUtil.JSONString2ObjectArray(data, Map.class);
				for(int j =0;j<array.length;j++){
					Map<String, Object> saveMap2 = new HashMap<String, Object>();
			    	Map json = array[j];
			    	String c_order= String.valueOf(json.get("number")) ;
			    	if(StrUtils.isEmpty(c_order)){
			    		c_order = "1";
			    	}
			    	saveMap2.put("c_brandid", json.get("id"));
			    	saveMap2.put("c_order", "1");
			    	saveMap2.put("c_schemeid", c_schemeid);
			    	saveMap2.put("c_id", "");
			    	activitySchemeDetailService.savaActivitySchemeDetail(saveMap2);
				}
			}
			retMap.put("status", "Y");
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return NONE;
		
		//Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);
//		String eareinfoId = this.activityFollowSchemeService.savaActivityFollowScheme(saveMap);
		
//		String aa =getParameter("canshu");
//		System.out.println(aa);
//		
//		retMap.put("status", "Y");
//		retMap.put("c_id", eareinfoId);
//		retMap.put("success", true);
//		try {
//			writeToResponse(JSONObject.fromObject(retMap).toString());
//		} catch (IOException e) {
//			log.error("saveActivityFollowScheme failed,",e);
//		}
//		return NONE;
	}
	//验证类型
	public String checkActivityFollowScheme(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_id = getParameter("id");
		String value =getParameter("value");
		String name =getParameter("name");
		
		try {
			retMap=this.activityFollowSchemeService.checkActivityFollowScheme(c_id, name,value);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("checkActivityFollowScheme", e);
		}
		return NONE;	
	}
	//查询所有的行业
	public String findAllIndustryName(){
		log.debug("init findAllIndustryName:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			retMap=this.activityFollowSchemeService.findAllIndustryName();
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "findAllIndustryName failed");
			log.error("findAllIndustryName", e);
		}
		log.debug("out findAllIndustryName:"+retMap);
		return NONE;
	}
	
	//查询所有的省份
	public String findAllProvince(){
		log.debug("init findAllProvince:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			retMap=this.activityFollowSchemeService.findAllProvince();
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "findAllProvince failed");
			log.error("findAllProvince", e);
		}
		log.debug("out findAllProvince:"+retMap);
		return NONE;
	}
	//查询对应省份下面的所有的城市
	public String findAllCityById(){
		log.debug("init findAllCityById:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		String provinceid =getParameter("provinceid");
		try {
			retMap=this.activityFollowSchemeService.findAllCityById(provinceid);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "findAllCityById failed");
			log.error("findAllCityById", e);
		}
		log.debug("out findAllCityById:"+retMap);
		return NONE;
	}	
	//查询行业对应的品牌
	public String findIndustryBrand(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String c_cname=getParameter("c_cname");
		String c_industryid=getParameter2("c_industryid");
		System.out.println(c_industryid);
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.activityFollowSchemeService.findIndustryBrand(c_industryid, c_cname, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActivityBrandInfoList",e);
		}
		log.debug("out getActivityBrandInfoList");
		return NONE;

	}
	//查询所有的城市
	public String findAllCity(){
		log.debug("init findAllCity:");
		Map<String, Object> retMap = new HashMap<String, Object>();		
		try {
			retMap=this.activityFollowSchemeService.findAllCity();
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "findAllCity failed");
			log.error("findAllCity", e);
		}
		log.debug("out findAllCity:"+retMap);
		return NONE;
	}
	
	@SuppressWarnings("unchecked")
	public  String  returnProvince(){
		log.debug("init returnProvince:");
		Map<String, Object> retMap = new HashMap<String, Object>();		
		try {
//			C_ID,C_CNAME
			String str=getParameter("C_ID");
			Map map=this.activityFollowSchemeService.returnProvince(getParameter("C_ID"));
			if(str.equals("0")){
				retMap.put("Province", "");//省的名字
				retMap.put("ProvinceID", "");//省的ID 
				
			}else{
			retMap.put("Province", map.get("C_CNAME"));//省的名字
			retMap.put("ProvinceID", map.get("C_ID"));//省的ID 
			}
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("info", "findAllCity failed");
			log.error("returnProvince", e);
		}
		log.debug("out returnProvince:"+retMap);
		return NONE;
	}
	
	public  String  getSelectCity(){
		log.debug("init returnProvince:");
		Map<String, Object> retMap = new HashMap<String, Object>();		
		try {
//			C_ID,C_CNAME
			retMap=this.activityFollowSchemeService.getSelectCity(getParameter("provinceid"));
			
			List  list=(List) retMap.get("list");
			String str="";
			Iterator  it=list.iterator();
			while(it.hasNext()){
			Map  map=(Map)it.next();
			str=str+map.get("C_CITYID")+",";
			}
			retMap.put("ids", str);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("info", "findAllCity failed");
			log.error("returnProvince", e);
		}
		log.debug("out returnProvince:"+retMap);
		return NONE;
	}
	
	public  String  getNotSelectCity(){
		
		log.debug("init returnProvince:");
		Map<String, Object> retMap = new HashMap<String, Object>();	
		
		try {
			retMap=this.activityFollowSchemeService.getNotSelectCity(getParameter("provinceid"),getParameter("str"));
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("info", "findAllCity failed");
			log.error("returnProvince", e);
		}
		log.debug("out returnProvince:"+retMap);
		return NONE;
	}
	
	public  String  getSaveItemsForUpdate(){ 
		log.debug("init getSaveItemsForUpdate:");
		String  cityID=	getParameter("id");//省的ID 
		String  MainId=getParameter("MainId");
		
		List  list=null;
		Map<String, Object> retMap = new HashMap<String, Object>();	
		if(cityID==null||"".equals(cityID)){
			list=activityFollowSchemeService.getSaveItemsForUpdateMoRen(MainId);
		}else{
			list =activityFollowSchemeService.getSaveItemsForUpdate(cityID);
		}
		
		retMap.put("list", list);
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("getSaveItemsForUpdate", e);
		}
		log.debug("out getSaveItemsForUpdate:"+retMap);
		return NONE;
	}
	
}
