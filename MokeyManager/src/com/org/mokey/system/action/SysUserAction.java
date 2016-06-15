package com.org.mokey.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.system.entiy.TSysUser;
import com.org.mokey.system.service.SysUserService;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;

/**
 * 用户管理
 * @author W
 *
 */
public class SysUserAction extends AbstractAction {

	private SysUserService sysUserService;
	/**输出内容*/
	private String out;

	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String UpdatePass() {
		String oldPassword = getParameter("oldPassword");
		String newPassword = getParameter("newPassword");
		try {
			if(StrUtils.isEmpty(oldPassword) || StrUtils.isEmpty(newPassword)){
				return "updatePassPage";
			}
			//
			TSysUser sysUser = getSessionLoginUser();
			sysUser = sysUserService.userLogin(sysUser.getUserCode(), oldPassword);
			if (StrUtils.isEmpty(sysUser)) {
				getRequest().setAttribute("result", "原始密码输入错误！");
				return "updatePassPage";
			}
			boolean falg = sysUserService.updatePass(sysUser.getUserId(),newPassword);
			if(falg){
				getRequest().setAttribute("result", "修改成功！");
			}else{
				getRequest().setAttribute("result", "修改失败！");
			}
		} catch (Exception e) {
			log.error("UpdatePass failed,",e);
			getRequest().setAttribute("result", "修改失败！");
		}
		log.debug("UpdatePass success");
		return "updatePassPage";
	}
	
	/**
	 * 用户信息列表查询
	 * @return
	 */
	public String getUserIfoList() {
		Map<String,Object> retMap = new HashMap<String,Object>();

		String c_usercode = getParameter("c_usercode");
		String c_username = getParameter("c_username");
		
		int start = getParameter2Int("start",0);
		int limit = getParameter2Int("limit",10);
		
		try{
			retMap = sysUserService.getUserIfoListMap(c_usercode, c_username, start,limit);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getUserIfoList failed");
			log.error("getUserIfoList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String checkUserInfo() {
		log.debug("init checkUserInfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String checkType = getParameter("checkType");
		String value = getParameter("value");
		String id = getParameter("id");
		try{
			retMap = sysUserService.checkUserInfo(checkType, value, id);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "checkUserInfo failed");
			log.error("checkUserInfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out checkUserInfo:"+retMap);
		return SUCCESS;
	}
	
	public String getSuppliers() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			List list = sysUserService.getSuppliers();
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getSuppliers failed");
			log.error("getSuppliers failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String getUserRoles() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			List list = sysUserService.getUserRoles();
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getUserRoles failed");
			log.error("getUserRoles failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String saveUserIfo() {
		log.debug("init saveUserIfo");
		Map<String,Object> retMap = new HashMap<String,Object>();

		String saveParam = getParameter("saveParam");
		Map<String,Object> saveMap = (Map) JSONUtil.JSONString2Bean(saveParam, Map.class);
		
		try{
			String cId = sysUserService.saveUserIfo(saveMap);
			
			retMap.put("cId", cId);
			retMap.put("status", "Y");
			retMap.put("success", true);
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "saveUserIfo failed");
			log.error("saveUserIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("out saveUserIfo");
		return SUCCESS;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String deleteUserIfo() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String c_id = getParameter("c_id");
		try{
			
			sysUserService.deleteUserIfo(c_id);
			
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "删除失败,"+e.getMessage());
			log.error("deleteUserIfo failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
	public String getUserIfoById() {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String userId = getParameter("userId");
		try{
			Map<String,Object> userMap = sysUserService.getUserIfoById(userId);
			String userRoles ="";
			List<Map> userRoleList = sysUserService.getUserRoleById(userId);
			for(Map um : userRoleList){
				userRoles +=","+um.get("C_ROLE_ID");
			}
			if(userRoles.length()>1){
				userRoles = userRoles.substring(1, userRoles.length());
			}
			userMap.put("USER_ROLE", userRoles);
			
			retMap.put("user", userMap);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getUserIfoById failed");
			log.error("getUserIfoById failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		return SUCCESS;
	}
	
}
