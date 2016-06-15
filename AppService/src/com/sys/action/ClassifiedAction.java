package com.sys.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.service.ClassifiedService;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;

public class ClassifiedAction extends AbstractAction{
	private ClassifiedService classifiedService;
	/**输出内容*/
	private String out;
	
	public ClassifiedService getClassifiedService() {
		return classifiedService;
	}

	public void setClassifiedService(ClassifiedService classifiedService) {
		this.classifiedService = classifiedService;
	}


	//分类热门品牌更多
	public String GetHotBrand(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		String industryid =getParameter("industryid");
		String cityid =getParameter("cityid");
		String type =getParameter("type");
		log.info("into getHotBrand");
    	log.info("--industryid--"+industryid+"--cityid--:"+cityid+"--type--"+type);
    	try{
			if(StrUtils.isEmpty(industryid)||StrUtils.isEmpty(cityid)||StrUtils.isEmpty(type)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
    		List list =classifiedService.getHotBrand(industryid, cityid, type);
    		retMap.put("list", list);
    		retMap.put("status", "Y");
    		
    	}catch (Exception e ){
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ClassifiedAction.getHotBrand failed,",e);
    	}
    	
    	try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return NONE; 
	}
	
	//分类活动性质的类别
	public String GetPropertyType(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		log.info("into getPropertyType");
    	
		try{
			List list =classifiedService.getPropertyType();
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ClassifiedAction.getPropertyType failed,",e);
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	
	//按行业内容类别查询对应的活动信息
	public String GetBaseInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String industryid = getParameter("industryid");
		String cityid = getParameter("cityid");
		String propertyid = getParameter("propertyid");
		log.info("into getBaseInfo");
		log.info("--industryid--"+industryid+"--cityid--:"+cityid+"--propertyid--"+propertyid);
		try{
			if(StrUtils.isEmpty(industryid)||StrUtils.isEmpty(cityid)||StrUtils.isEmpty(propertyid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			
			List list =classifiedService.getBaseInfo(industryid, cityid, propertyid);
			retMap.put("status", "Y");
			retMap.put("list",list);
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ClassifiedAction.getBaseInfo failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return NONE;
	}
	
	
	//分类列表
	public String ClassiFiedDetail(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String industyid=getParameter("industyid");
		String pageindex=getParameter("pageindex");  //分页
		log.info("into ClassiFiedDetail");
		log.info("--industyid--"+industyid+"--pageindex--"+pageindex);
		try {
			if(StrUtils.isEmpty(industyid)||StrUtils.isEmpty(pageindex)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			out=JSONObject.fromObject(retMap).toString();
    			return "success";
			}
			List list=classifiedService.ClassiFiedDetail(industyid,pageindex);
			retMap.put("status", "Y");
			retMap.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ClassiFiedDetail failed",e);
		}
		out=JSONObject.fromObject(retMap).toString();
		return "success";
	}
	
	public String saveSearchRecord(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid = getParameter("uid");
		String type = getParameter("type");
		String indexid = getParameter("indexid"); 
		log.info("into saveSearchRecord");
		
		try{
			if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(type)||StrUtils.isEmpty(indexid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			
			classifiedService.saveSearchRecord(uid, type, indexid);
			retMap.put("status", "Y");
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ClassifiedAction.saveSerarchRecord failed,",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}
	
	//分类区域查询
	public String GetBusinessArea(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String cityid=getParameter("cityid");
		log.info("into saveSearchRecord");
		log.info("--cityid--"+cityid);
		
		try{
			if(StrUtils.isEmpty(cityid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			List list = classifiedService.getBusinessArea(cityid);
			retMap.put("status", "Y");
			retMap.put("list", list);
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ClassifiedAction.getBusinessArea failed,",e);
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}
	//分类商圈查询
	public String GetBusinessZone(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String areaid=getParameter("areaid");
		log.info("into saveSearchRecord");
		log.info("--areaid--"+areaid);
		
		try{
			if(StrUtils.isEmpty(areaid)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    			writeToResponse(JSONObject.fromObject(retMap).toString());
    			return NONE;
			}
			List list = classifiedService.getBusinessZone(areaid);
			retMap.put("status", "Y");
			retMap.put("list", list);
		}catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("ClassifiedAction.getBusinessArea failed,",e);
		}
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
