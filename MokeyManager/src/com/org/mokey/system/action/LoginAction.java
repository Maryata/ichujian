package com.org.mokey.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.SysConstant;
import com.org.mokey.system.entiy.TSysUser;
import com.org.mokey.system.service.SysUserService;
import com.org.mokey.util.StrUtils;

/**
 * 用户登录管理
 * 
 * @author W
 * 
 */
public class LoginAction extends AbstractAction {

	private SysUserService sysUserService;

	public SysUserService getSysUserService() {
		return sysUserService;
	}
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	/**
	 * 登陆
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String login() {
		String username = getParameter("username");
		String password = getParameter("password");
		try {
			if(StrUtils.isEmpty(username) || StrUtils.isEmpty(password)){
				return "loginpage";
			}
			log.debug("login into:username:"+username+",password:"+password);
			// String md5p=MD5.GetMD5Code(password); //密码加密
			TSysUser sysUser = sysUserService.userLogin(username, password);
			String resultMsg = null;
			List userRoleList = null;
			if (StrUtils.isEmpty(sysUser)) {
				resultMsg = "账号或密码不正确!";
			}else if(!"1".equals(sysUser.getUserStatus())){//验证用户有效性;
				resultMsg = "账号失效,请联系管理员!";
			}else {
				//查看用户权限信息;
				userRoleList = sysUserService.getUserRoleById(sysUser.getUserId());
				if(StrUtils.isEmpty(userRoleList)){
					resultMsg = "账号未设置角色,请联系管理员!";
				}
			}
			if( null!=resultMsg ){
				log.debug("user： "+username+" msg :"+resultMsg);
				getRequest().setAttribute("result", resultMsg);
				return "loginpage";
			}
			//---------------------------
			List userFunctionList = sysUserService.getUserFunctions(sysUser.getUserId());
			List<Map> rootMenus = new ArrayList<Map>();
			Map<String,List> modelMenu = new HashMap<String,List>();
			for(int i=0;i<userFunctionList.size();i++ ){
				Map funMap = (Map) userFunctionList.get(i);
				String pid = (String) funMap.get("C_PID");
				if(SysConstant.FUNCTION_ROOT.equals(pid) ){
					rootMenus.add(funMap);
				}else{
					List modelList = null;
					if(modelMenu.containsKey(pid)){
						modelList = modelMenu.get(pid);
					}else{
						modelList = new ArrayList();
					}
					modelList.add(funMap);
					modelMenu.put(pid, modelList);
				}
			}
			setSessionKey(AP_SYS_SESSION_MENU_ROOT, rootMenus);
			setSessionKey(AP_SYS_SESSION_MENU_MODEL, modelMenu);
			
			//-----
			boolean homeFlag = false;
			List allMenu = new ArrayList();
	        for(Map bean : rootMenus){
	        	Map root = new HashMap();
	        	
	        	List items = new ArrayList();
	        	List menus = new ArrayList();
	        	
	         	
	        	root.put("id", bean.get("C_ID"));
	        	root.put("menu", menus);
	        	if(!homeFlag){//set homePage;
	        		root.put("homePage", "welcome");
	        		Map home = new HashMap();
		        	home.put("id", "welcome");
		        	home.put("text", "welcome");
		        	home.put("href", "menu/blank.jsp");
		        	home.put("closeable", false);
	        		items.add(home);
	        		homeFlag = true;
	        	}
	        	
	        	List<Map> menuList = modelMenu.get(root.get("id"));
	        	
	        	if(menuList != null && menuList.size() >= 1)
	        	for(Map menuMap : menuList){
	        		String url = (String) menuMap.get("C_URL");
	        		if(url.equals("#")){
	        			List items2 = new ArrayList();
	        			if(modelMenu.containsKey(menuMap.get("C_ID"))){
	        				List<Map> menuList3 = modelMenu.get(menuMap.get("C_ID"));
	        				for(Map menu3 : menuList3 ){
	        					url = (String) menu3.get("C_URL");
	        					if(url.startsWith("/")){
	        	        			url = url.substring(1, url.length());
	        	        		}
	        					Map item3 = new HashMap();
	        					item3.put("id", menu3.get("C_ID"));
	        					item3.put("text", menu3.get("C_NAME"));
	        					item3.put("href", url);
	        	        		items2.add(item3);
	        				}
	        			}
	        			Map menu2 = new HashMap();
	        			menu2.put("text", menuMap.get("C_NAME"));
	        			menu2.put("items", items2);
	        			menu2.put("collapsed", true);
	    	         	
	    	         	menus.add(menu2);
	        			continue;
	        		}
	        		if(url.startsWith("/")){
	        			url = url.substring(1, url.length());
	        		}
	        		Map item = new HashMap();
	        		item.put("id", menuMap.get("C_ID"));
	        		item.put("text", menuMap.get("C_NAME"));
	        		item.put("href", url);
	        		items.add(item);
	        	}
	        	
	        	if(StrUtils.isNotEmpty(items)){
	        		Map menu = new HashMap();
		         	menu.put("text", bean.get("C_NAME"));
		         	menu.put("items", items);
		         	
		         	menus.add(0,menu);
	        	}
	        	
	        	allMenu.add(root);
	        }
        	
	        setSessionKey(AP_SYS_SESSION_MENU, allMenu);
			setSessionLoginUser(sysUser);
			log.debug("login success");
			return "success";
		} catch (Exception e) {
			log.error("login failed,",e);
			getRequest().setAttribute("result", "login failed,"+e.getMessage());
			return "loginpage";
		}
	}
	
	public String getRoleMenu() {
		log.debug("getRoleMenu init");
		try {
			@SuppressWarnings("unchecked")
			List<Object> allMenu = (List<Object>) getSessionKey(AP_SYS_SESSION_MENU);
			
			Map<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("menuConfig", allMenu);
			writeToResponse(JSONObject.fromObject(roleMap).toString());
		} catch (Exception e) {
			log.error("getRoleMenu failed,",e);
			try {
				writeToResponse("[]");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		log.debug("getRoleMenu success");
		return NONE;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logOut() {
		log.debug("logOut init");
		try {
			invalidateLoginUser();
		} catch (Exception e) {
			log.error("logOut failed,",e);
		}
		log.debug("logOut success");
		return "logOutpage";
	}

}
