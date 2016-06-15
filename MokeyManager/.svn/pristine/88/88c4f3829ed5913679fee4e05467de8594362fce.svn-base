package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionSearchRecardService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActivitySearchRecardAction  extends AbstractAction{
	private ActionSearchRecardService actionSearchRecardService;
	
	public ActionSearchRecardService getActionSearchRecardService() {
		return actionSearchRecardService;
	}

	public void setActionSearchRecardService(
			ActionSearchRecardService actionSearchRecardService) {
		this.actionSearchRecardService = actionSearchRecardService;
	}

	//获取数据
	public String getActionSearchRecardList(){
		log.debug("init getActionSearchRecardList");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		//String c_imei=getParameter("c_imei");
		String time_s=getParameter("time_s");
		String time_e=getParameter("time_e");
	//	int start = getParameter2Int("start",0);
	//	int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.actionSearchRecardService.getActionSearchRecardListMap(time_s, time_e);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActionSearchRecardList",e);
		}
		log.debug("out getActionSearchRecardList");
		return NONE;
	}
	
	//删除
	public String deleteActionSearchRecard(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.actionSearchRecardService.deleteActionSearchRecard(c_id);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteActionSearchRecard failed,",e);
		}
		return NONE;
	}
	//增加
	public String saveActionSearchRecard(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);
		String eareinfoId = this.actionSearchRecardService.savaActionSearchRecard(saveMap);

		retMap.put("status", "Y");
		retMap.put("c_id", eareinfoId);
		retMap.put("success", true);
		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error("saveActionSearchRecard failed,",e);
		}
		return NONE;
	}
	//验证类型
	public String checkActionSearchRecard(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_id = getParameter("id");
		String value =getParameter("value");
		String name =getParameter("name");
		
		try {
			retMap=this.actionSearchRecardService.checkActionSearchRecard(c_id, name,value);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("checkActionSearchRecard", e);
		}
		return NONE;	
	}
}
