package com.org.mokey.basedata.baseinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionActThemeService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActionActThemeAction  extends AbstractAction{ 
	private static final long serialVersionUID = 1L;
	private   ActionActThemeService actionActThemeService;
	
	public ActionActThemeService getActionActThemeService() {
		return actionActThemeService;
	}

	public void setActionActThemeService(ActionActThemeService actionActThemeService) {
		this.actionActThemeService = actionActThemeService;
	}

	public  String  getActThemeList(){
	log.debug("getActThemeList init");
	Map<String, Object> retMap = new HashMap<String, Object>();
	String c_name = getParameter("c_name");
	
	int start = getParameter2Int("start",0);
	int limit = getParameter2Int("limit",10);
	
	retMap=this.actionActThemeService.getActionTypeListMap(c_name, start, limit);
	try {
		writeToResponse(JSONObject.fromObject(retMap).toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		log.error("getActThemeList",e);
	}
	log.debug("getActThemeList out");
	return NONE;
	}
	
	public  String  deleteById(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		try {
			this.actionActThemeService.deleteActionTypeInfo(c_id);
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
	@SuppressWarnings("unchecked")
	public  String  saveOrUpdate(){
		log.debug("saveActionInfo init");
		Map<String, Object> retMap = new HashMap<String, Object>();
//		{"c_id":242,"c_name":"1454","c_istitle":"1","c_order":"8","c_islive":"1"}
		String saveParam =getParameter("saveParam");
		//Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		Map<String, Object> saveMap =(Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		
		
		String actioninfoId =this.actionActThemeService.savaActionTypeInfo(saveMap);
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
}
