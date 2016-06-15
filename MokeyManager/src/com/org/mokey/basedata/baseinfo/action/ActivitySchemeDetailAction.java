package com.org.mokey.basedata.baseinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActivitySchemeDetailService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.common.util.file.FileUtil;
import com.org.mokey.util.JSONUtil;

public class ActivitySchemeDetailAction extends AbstractAction {
	private ActivitySchemeDetailService activitySchemeDetailService;
	
	
	public ActivitySchemeDetailService getActivitySchemeDetailService() {
		return activitySchemeDetailService;
	}

	public void setActivitySchemeDetailService(
			ActivitySchemeDetailService activitySchemeDetailService) {
		this.activitySchemeDetailService = activitySchemeDetailService;
	}

	//*********************
	public String getActivitySchemeDetailList(){
		log.debug("init getActivityBrandInfoList");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_schemeid=getParameter("c_schemeid");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.activitySchemeDetailService.getActivitySchemeDetailListMap(c_schemeid, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActivitySchemeDetailList",e);
		}
		log.debug("out getActivitySchemeDetailList");
		return NONE;
	}
	
	//删除
	public String deleteActivitySchemeDetail(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.activitySchemeDetailService.deleteActivitySchemeDetail(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActivitySchemeDetail failed,",e);
		}
		return NONE;
	}
	//增加
	public String saveActivitySchemeDetail(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);
		
		String eareinfoId = this.activitySchemeDetailService.savaActivitySchemeDetail(saveMap);

		retMap.put("status", "Y");
		retMap.put("c_id", eareinfoId);
		retMap.put("success", true);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveActivitySchemeDetail failed,",e);
		}
		return NONE;
	}

	
	public String findBrandAllName(){
		log.debug("init findBrandAllName:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			retMap=this.activitySchemeDetailService.findBrandAllName();
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "findBrandAllName failed");
			log.error("findBrandAllName", e);
		}
		log.debug("out findBrandAllName:"+retMap);
		return NONE;
	}

	public String findSchemeAllName(){
		log.debug("init findSchemeAllName:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			retMap=this.activitySchemeDetailService.findSchemeAllName();
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			retMap.put("status", "N");
			retMap.put("info", "findSchemeAllName failed");
			log.error("findSchemeAllName", e);
		}
		log.debug("out findSchemeName:"+retMap);
		return NONE;
	}

}
