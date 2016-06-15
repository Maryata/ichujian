package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.sysinfo.service.ActivityBusinessZoneService;
import com.org.mokey.basedata.sysinfo.service.ActivityMenuInfoService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;

public class ActivityMenuInfoAction extends AbstractAction {
	
	/**输出内容*/
	private String out;
	
	ActivityMenuInfoService activityMenuInfoService;
	
	public String GetList() {
		log.info("into GetList");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String name=getParameter("c_name");
		int start=getParameter2Int("start",0);
		int limit=getParameter2Int("limit",10);
		try {
			retmap=activityMenuInfoService.GetList(name, start, limit);
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("GetList failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	public String findName(){
		log.info("into findName");
		Map<String, Object> retmap=new HashMap<String, Object>();
		try {
			retmap=activityMenuInfoService.findName();
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("findNmae failed");
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	public String delete() {
		log.info("into delete");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String id=getParameter("c_id");
		try {
			activityMenuInfoService.Delete(id);
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("delete failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();		
		return SUCCESS;
	}
	
	public String checkName() {
		log.info("into chenkName");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String name=getParameter("c_name");
		String id=getParameter("c_id");
		try {
			retmap=activityMenuInfoService.CheckName(name, id);	
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("checkName failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	
	public String Save() {
		log.info("into Save");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String saveParam = getParameter("saveParam");
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try {
			activityMenuInfoService.Save(saveMap);
			retmap.put("status", "Y");
			retmap.put("success", true);
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("Save failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();	
		return SUCCESS;
	}
	

	public ActivityMenuInfoService getActivityMenuInfoService() {
		return activityMenuInfoService;
	}

	public void setActivityMenuInfoService(
			ActivityMenuInfoService activityMenuInfoService) {
		this.activityMenuInfoService = activityMenuInfoService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

}