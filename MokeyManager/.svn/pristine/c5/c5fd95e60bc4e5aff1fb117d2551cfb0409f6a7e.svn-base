package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionIntegralTypeService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActionIntegralTypeAction extends AbstractAction{
	private ActionIntegralTypeService actionIntegralTypeService;
	
	public ActionIntegralTypeService getActionIntegralTypeService() {
		return actionIntegralTypeService;
	}

	public void setActionIntegralTypeService(
			ActionIntegralTypeService actionIntegralTypeService) {
		this.actionIntegralTypeService = actionIntegralTypeService;
	}

	//获取数据
	public String getActionIntegralTypeList(){
		log.debug("init getActionPackageInfo");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_name=getParameter("c_name");
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.actionIntegralTypeService.getActionIntegralTypeListMap(c_name, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActionPackageInfoList",e);
		}
		log.debug("out getActionPackageInfoList");
		return NONE;
	}
	
	//添加
	public String saveActionIntegralTypeInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);


			String eareinfoId = this.actionIntegralTypeService.savaActionIntegralTypeInfo(saveMap);
			retMap.put("status", "Y");
			retMap.put("c_id", eareinfoId);
			retMap.put("success", true);			
		
		
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveActionEareInfo failed,",e);
		}
		return NONE;
	}
	//删除
	public String deleteActionIntegralTypeInfo(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.actionIntegralTypeService.deleteActionIntegralTypeInfo(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActionPackageInfo failed,",e);
		}
		return NONE;
	}
	
	public String checkActionIntegralTypeInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_id = getParameter("id");
		String value =getParameter("value");
		String name =getParameter("name");
		
		try {
			retMap=this.actionIntegralTypeService.checkActionIntegralTypeInfo(c_id, name,value);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("checkActionEareInfoInfo", e);
		}
		return NONE;	
	}
}
