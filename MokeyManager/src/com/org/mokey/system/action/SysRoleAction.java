package com.org.mokey.system.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.system.entiy.common.MenuModel;
import com.org.mokey.system.entiy.common.MenuTree;
import com.org.mokey.system.service.SysRoleService;
import com.org.mokey.util.JSONUtil;

public class SysRoleAction extends AbstractAction {
	
	private SysRoleService sysRoleService;
	/**输出内容*/
	private String out;

	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
	
	/**
	 * 用户信息列表查询
	 * @return
	 */
	public String getRoleList() {
		Map<String,Object> retMap = new HashMap<String,Object>();

		String rolecode = getParameter("rolecode");
		String rolename = getParameter("rolename");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = sysRoleService.getRoleIfoListMap(rolecode, rolename, start, limit);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getRoleList failed");
			log.error("getRoleList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String checkRoleInfo() {
		log.debug("init checkRoleIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String checkType = getParameter("checkType");
		String value = getParameter("value");
		String id = getParameter("id");
		try{
			retMap = sysRoleService.checkRoleInfo(checkType, value, id);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "checkRoleInfo failed");
			log.error("checkRoleInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out checkRoleInfo:"+retMap);
		return SUCCESS;
	}
	
	public String saveRoleIfo() {
		log.debug("init saveRoleIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String saveParam = getParameter("saveParam");
		@SuppressWarnings("unchecked")
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		String [] roleFuncts = getRequest().getParameterValues("roleFuncts");
		
		try{
			String cId = sysRoleService.saveRoleIfo(saveMap,roleFuncts);
			
			retMap.put("cId", cId);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "saveRoleIfo failed");
			log.error("saveRoleIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out saveRoleIfo");
		return SUCCESS;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String deleteRoleIfo() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		try{
			
			sysRoleService.deleteRoleIfo(c_id);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteUserIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	
	public String getPermTree() {
		String roleId = getParameter("roleId");
		try{
			MenuTree treeData = sysRoleService.getPermTree(roleId);
			
			String str = MenuModel.getMenuJSONString(treeData);
			str = str.replaceAll("\"false\"", "false");
			str = str.replaceAll(",\"children\":\\[]", "");

			writeToResponse(str);
		}catch(Exception e){
			Map<String,Object> retMap = new HashMap<String,Object>();
			retMap.put("status", "N");
			retMap.put("info", "getPermTree failed");
			log.error("getPermTree failed,",e);
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return NONE;
	}
	

}
