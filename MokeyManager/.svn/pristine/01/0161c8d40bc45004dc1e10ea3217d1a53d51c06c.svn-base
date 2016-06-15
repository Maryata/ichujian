package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionIndustryTypeService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActionIndustryTypeAction extends AbstractAction {
	protected ActionIndustryTypeService actionIndustryTypeService;
	
	

	public ActionIndustryTypeService getActionIndustryTypeService() {
		return actionIndustryTypeService;
	}

	public void setActionIndustryTypeService(
			ActionIndustryTypeService actionIndustryTypeService) {
		this.actionIndustryTypeService = actionIndustryTypeService;
	}

	//查询
	public String getActionTypeList(){
		log.debug("getActionTypeList init");
		Map<String, Object> retMap = new HashMap<String, Object>();
		String c_name = getParameter("c_name");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		retMap=this.actionIndustryTypeService.getActionTypeListMap(c_name, start, limit);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("getActionTypeList",e);
		}
		log.debug("getActionTypeList out");
		return NONE;
	}
	
	//增加
	public String saveActionInfo(){
		log.debug("saveActionInfo init");
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		//Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		Map<String, Object> saveMap =(Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		String actioninfoId =this.actionIndustryTypeService.savaActionTypeInfo(saveMap);
		retMap.put("status", "Y");
		retMap.put("success", true);
		retMap.put("c_id", actioninfoId);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveActionInfo",e);
		}
		log.debug("saveActionInfo out");
		return NONE; 
	}
	
	/**
	 * 删除
	 */
	public String deleteActionTypeInfo() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		
		try {
			this.actionIndustryTypeService.deleteActionTypeInfo(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActionTypeInfo failed,",e);
		}
		return NONE;
	}
	
	//验证类型
	public String checkActionTypeInfo() {
		log.debug("init checkActionTypeInfo");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_name = getParameter("name");
		String c_id = getParameter("id");

		try {
			retMap = this.actionIndustryTypeService.checkActionTypeInfo(c_id, c_name);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "checkActionTypeInfo failed");
			log.error("checkActionTypeInfo failed,",e);
		}
		log.debug("out checkActionTypeInfo:"+retMap);
		return NONE;
	}
	
	//验证id
/*	public String checkActionTypeID(){
		log.debug("into checkActionTypeID");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id=getParameter("c_id");
		try {
			retMap=this.actionIndustryTypeService.checkActionTypeId(c_id);
			retMap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			log.error("checkActionTypeID failed",e);
		}
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("checkActionTypeID", e);
		}
		return NONE;
	}*/
	public String findAllName(){
		log.debug("init checkActionTypeInfo:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			retMap=this.actionIndustryTypeService.findAllName();
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "checkActionTypeInfo failed");
			log.error("checkActionTypeInfo", e);
		}
		log.debug("out checkActionTypeInfo:"+retMap);
		return NONE;
	}
	
}
