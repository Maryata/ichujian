package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionCityInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActivityCityInfoAction extends AbstractAction{
	private ActionCityInfoService actionCityInfoService;
	
	public ActionCityInfoService getActionCityInfoService() {
		return actionCityInfoService;
	}

	public void setActionCityInfoService(ActionCityInfoService actionCityInfoService) {
		this.actionCityInfoService = actionCityInfoService;
	}

	//获取数据
	public String getActionCityInfoList(){
		log.debug("init getActionCityInfoList");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_cname=getParameter("c_cname");
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.actionCityInfoService.getActionCityInfoListMap(c_cname, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActionCityInfoList",e);
		}
		log.debug("out getActionCityInfoList");
		return NONE;
	}
	
	//删除
	public String deleteActionCityInfo(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.actionCityInfoService.deleteActionCityInfo(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActionCityInfo failed,",e);
		}
		return NONE;
	}
	//增加
	public String saveActionCityInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);
		String eareinfoId = this.actionCityInfoService.savaActionCityInfo(saveMap);

		retMap.put("status", "Y");
		retMap.put("c_id", eareinfoId);
		retMap.put("success", true);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveActionCityInfo failed,",e);
		}
		return NONE;
	}
	//验证类型
	public String checkActionCityInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_id = getParameter("id");
		String value =getParameter("value");
		String name =getParameter("name");
		
		try {
			retMap=this.actionCityInfoService.checkActionCityInfo(c_id, name,value);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("checkActionCityInfoInfo", e);
		}
		return NONE;	
	}
	
	public String findAllName(){
		log.debug("init checkActionTypeInfo:");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			retMap=this.actionCityInfoService.findAllName();
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "findAllName failed");
			log.error("findAllName", e);
		}
		log.debug("out findAllName:"+retMap);
		return NONE;
	}
}
