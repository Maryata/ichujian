package com.org.mokey.basedata.baseinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionEareInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.common.util.file.FileUtil;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

public class ActionEareInfoAction  extends AbstractAction{
	//
	protected ActionEareInfoService actionEareInfoService;
	
	
	public ActionEareInfoService getActionEareInfoService() {
		return actionEareInfoService;
	}

	public void setActionEareInfoService(ActionEareInfoService actionEareInfoService) {
		this.actionEareInfoService = actionEareInfoService;
	}

	//获取数据
	public String getActionEareInfoList(){
		log.debug("init getActionEareInfoList");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_name=getParameter("c_name");
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try {
			retMap=this.actionEareInfoService.getActionEareInfoListMap(c_name, start, limit);
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getActionEareInfoList",e);
		}
		log.debug("out getActionEareInfoList");
		return NONE;
	}
	
	//删除
	public String deleteActionEareInfo(){
		Map<String, Object> retMap= new HashMap<String, Object>();
		String c_id = getParameter("c_id");

		try {
			this.actionEareInfoService.deleteActionEareInfo(c_id);
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
	public String saveActionEareInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String saveParam =getParameter("saveParam");
		Map<String, Object> saveMap =(Map<String, Object>) JSONUtil.JSONString2Bean(saveParam, Map.class);
		String eareinfoId = this.actionEareInfoService.savaActionEareInfo(saveMap);

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
	//验证类型
	public String checkActionEareInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String c_id = getParameter("id");
		String value =getParameter("value");
		String name =getParameter("name");
		
		try {
			retMap=this.actionEareInfoService.checkActionEareInfo(c_id, name,value);
			retMap.put("status", "Y");
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (Exception e) {
			retMap.put("status", "N");
			log.error("checkActionEareInfoInfo", e);
		}
		return NONE;	
	}

}
