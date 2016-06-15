package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.sysinfo.service.ActivityMainInfoService;
import com.org.mokey.basedata.sysinfo.service.SupplierService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.JSONUtil;
/**
 * 首页行业广告管理
 * @author lenovo
 *
 */
public class ActivityMainInfoAction extends AbstractAction{
	private ActivityMainInfoService activityMainInfoservice;
	private String out;
	
	public String getList(){
		log.info("into getSupplierList");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String city=getParameter("c_city");
		int start=getParameter2Int("start",0);
		int limit=getParameter2Int("limit",10);
		try {
			retmap=activityMainInfoservice.getList(city,  start, limit);
			retmap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retmap.put("status", "N");
			log.error("getSupplierList failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	public String delete(){
		log.info("into deleteSupplier");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String id=getParameter("c_id");
		try {
			activityMainInfoservice.delete(id);
			retmap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retmap.put("status", "N");
			log.error("deleteSupplier failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();		
		return SUCCESS;
	}
	
	public String save(){
		log.info("into saveSupplier");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String saveParam = getParameter("saveParam");
		Map<String,Object>[] saveMap = (Map<String, Object>[]) JSONUtil.JSONString2ObjectArray(saveParam, Map.class);
		String cityid=getParameter("cityid");
		try {
			activityMainInfoservice.save(saveMap,cityid);
			retmap.put("status", "Y");
			retmap.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
			retmap.put("status", "N");
			log.error("saveSupplier failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();	
		return SUCCESS;
	}
	
	public String findIndustry(){
		log.info("into findIndustry");
		Map<String, Object> retmap=new HashMap<String, Object>();
		int start=getParameter2Int("start",0);
		int limit=getParameter2Int("limit",30);
		String cityid=getParameter("cityid");
		try {
			retmap=activityMainInfoservice.findIndustry(cityid,start,limit);
			retmap.put("status", "Y");
		} catch (Exception e) {
			// TODO: handle exception
			retmap.put("status", "N");
			log.error("findIndustry failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();	
		return SUCCESS;
	}
	
	public String findcity(){
		log.info("into findcity");
		Map<String, Object> retmap=new HashMap<String, Object>();
		try {
			retmap=activityMainInfoservice.findcity();
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("findcity failed");
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	public String getMain(){
		Map<String, Object> retmap=new HashMap<String, Object>();
		try {
			List list = activityMainInfoservice.getMain();
			retmap.put("list", list);
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("getSupplierList failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public ActivityMainInfoService getActivityMainInfoservice() {
		return activityMainInfoservice;
	}

	public void setActivityMainInfoservice(
			ActivityMainInfoService activityMainInfoservice) {
		this.activityMainInfoservice = activityMainInfoservice;
	}

 

}
