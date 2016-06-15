package com.org.mokey.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.mokey.common.SysConstant;
import com.org.mokey.system.dao.SysRoleDao;
import com.org.mokey.system.entiy.common.MenuTree;
import com.org.mokey.system.service.SysRoleService;

public class SysRoleServiceImpl implements SysRoleService {
	protected Logger log = (Logger.getLogger(getClass()));
	private SysRoleDao sysRoleDao;
	
	
	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	@Override
	public Map<String, Object> getRoleIfoListMap(String rolecode,
			String rolename, int start, int limit) {
		return sysRoleDao.getRoleIfoListMap(rolecode, rolename, start, limit);
	}

	@Override
	public Map<String, Object> checkRoleInfo(String checkType, String value,
			String id) {
		return sysRoleDao.checkRoleInfo(checkType, value, id);
	}

	@Override
	public String saveRoleIfo(Map<String, Object> saveMap,String [] roleFuncts) {
		String roleId = sysRoleDao.saveRoleIfo(saveMap);
		//------------
		for(int i =0; i<roleFuncts.length; i++){
			String[] tempArray = roleFuncts[i].split(",");
			if(tempArray.length<2){
				continue;
			}
			if ("true".equals(tempArray[1])) {
				try {
					sysRoleDao.saveRoleFunct(tempArray[0],roleId);
				} catch (RuntimeException e) {
					log.error("saveRoleIfo failed", e);
				}
			} else {
				//log.debug("delete function" + tempArray[0] + "/"+ tempArray[1]);
				sysRoleDao.deleteRoleFunct(tempArray[0],roleId);
			}
		}
		return roleId;
		
	}

	@Override
	public void deleteRoleIfo(String c_id) {
		sysRoleDao.deleteRoleIfo(c_id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MenuTree getPermTree(String roleId) {
		//获取角色权限
		List<Map> roleFunctionList = sysRoleDao.getRoleFunctionById(roleId);
		List roleFuns = new ArrayList();
		for(Map fm : roleFunctionList ){
			roleFuns.add(fm.get("C_FUNCTION_ID"));
		}
		
		MenuTree menuTreeRoot = new MenuTree();
		menuTreeRoot.setId(SysConstant.FUNCTION_ROOT);
		List<Map<String,Object>> list = sysRoleDao.getFunctionByPid(SysConstant.FUNCTION_ROOT);
		// 设置子菜单
		if(list !=null && list.size()>0){
			List chard = new ArrayList();
			for(Map<String,Object> function :  list){
				MenuTree menuTree = new MenuTree();
				this.buildMenuTree(menuTree, function,roleFuns);
				chard.add(menuTree);
			}
			menuTreeRoot.setChildren(chard);
		}
		return menuTreeRoot;
	}
	
	/**
	 * 把从db中你想得到的map组装成MenuTree
	 */
	private void buildChildrenMenuTree(MenuTree menuTreeRoot, List<Map<String,Object>> list,List roleFuns) {
		if(list !=null && list.size()>0){
			List chard = new ArrayList();
			for(Map<String,Object> function :  list){
				MenuTree menuTree = new MenuTree();
				this.buildMenuTree(menuTree, function,roleFuns);
				chard.add(menuTree);
			}
			menuTreeRoot.setChildren(chard);
		}
	}
	
	private void buildMenuTree(MenuTree menuTree,Map<String,?> root,List roleFuns) {
		// ROOT 指向他自己
		menuTree.setId(root.get("C_ID").toString());
		menuTree.setPid(root.get("C_PID").toString());
		menuTree.setText(root.get("C_NAME").toString());
		menuTree.setQtip(menuTree.getText());
		menuTree.setLocation(root.get("C_URL").toString());
		menuTree.setOrder(Integer.valueOf(root.get("C_ORDER")+""));
		menuTree.setLeaf(false);
		//menuTree.setExpandable(true);
		menuTree.setNeedCheck(true);
		//menuTree.setExpanded(true);
		if(roleFuns.contains(menuTree.getId())){//设置选择
			menuTree.setChecked(true);
		}
		
		List<Map<String,Object>> list = sysRoleDao.getFunctionByPid(menuTree.getId());
		if(list !=null && list.size()>0){
			this.buildChildrenMenuTree(menuTree, list,roleFuns);
		}else{
			menuTree.setLeaf(true);
			//menuTree.setExpanded(false);
		}
		if("1".equals(root.get("C_IS_MENU") )){
			//menuTree.setCls(".x-tree-node-expanded");
		}
		
	}

}
