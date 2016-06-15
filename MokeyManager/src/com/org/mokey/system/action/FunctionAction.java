package com.org.mokey.system.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.system.service.FunctionService;
import com.org.mokey.util.JSONUtil;
/**
 * 功能管理
 * @author lenovo
 *
 */
public class FunctionAction extends AbstractAction{
	private FunctionService functionService;
	private String out="";

	public String GetFunctionList() {
		log.info("into GetFunctionList");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String name=getParameter("c_name");
		int start=getParameter2Int("start",0);
		int limit=getParameter2Int("limit",10);
		try {
			retmap=functionService.GetFunctionList(name, start, limit);
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("GetFunctionList failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	public String SaveFunction() {
		log.info("into SaveFunction");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String saveParam = getParameter("saveParam");
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		try {
			functionService.SaveFunction(saveMap);
			retmap.put("status", "Y");
			retmap.put("success", true);
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("SaveFunction failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();	
		return SUCCESS;
	}
	
	public String deleteFunction() {
		log.info("into deleteFunction");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String id=getParameter("c_id");
		try {
			functionService.DeleteFunction(id);
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("deleteFunction failed",e);
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
			retmap=functionService.CheckName(name, id);	
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("checkName failed",e);
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	
	public String findName(){
		log.info("into findName");
		Map<String, Object> retmap=new HashMap<String, Object>();
		try {
			retmap=functionService.findName();
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("findNmae failed");
		}
		out=JSONObject.fromObject(retmap).toString();
		return SUCCESS;
	}
	

	public FunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
		
}
