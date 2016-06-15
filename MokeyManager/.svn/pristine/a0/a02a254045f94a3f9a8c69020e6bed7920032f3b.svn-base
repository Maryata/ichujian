package com.org.mokey.basedata.baseinfo.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.service.ActionLogInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActionLogInfoAction extends AbstractAction {

	private String out;
	private ActionLogInfoService actionLogInfoService;

	public String GetLogInfoList() {
		log.info("into GetLogInfoList");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String time_s = getParameter("time_s");
		String time_e = getParameter("time_e");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		String logs = getParameter("c_log");
		String imei = getParameter("c_imei");
		
		try {
			retMap=actionLogInfoService.getDeviceInfoListMap(logs,imei,time_s, time_e, start, limit);
			retMap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			log.error("GetLogInfoList failed"+e.getMessage());
		}
		out=JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String Updatedispose() {
		log.debug("init Updatedispose");
		Map<String,Object> retMap = new HashMap<String,Object>();
		String saveParam = getParameter("saveParam");
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try {
			log.info(saveMap.get("c_remark").toString()+"-----------------==");
			log.info(saveMap.get("c_id_old").toString()+"-------------------");
			
			actionLogInfoService.Updatedispose(saveMap.get("c_id_old").toString(), saveMap.get("c_remark").toString());
			retMap.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("Updatedispose failed",e);
		}
		out=JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public ActionLogInfoService getActionLogInfoService() {
		return actionLogInfoService;
	}

	public void setActionLogInfoService(ActionLogInfoService actionLogInfoService) {
		this.actionLogInfoService = actionLogInfoService;
	}
	
}
