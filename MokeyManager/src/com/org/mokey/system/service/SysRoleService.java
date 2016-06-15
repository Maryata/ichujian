package com.org.mokey.system.service;

import java.util.Map;

import com.org.mokey.system.entiy.common.MenuTree;

public interface SysRoleService {
	
	Map<String, Object> getRoleIfoListMap(String rolecode, String rolename,
			int start, int limit);

	Map<String, Object> checkRoleInfo(String checkType, String value, String id);

	String saveRoleIfo(Map<String, Object> saveMap,String [] roleFuncts);

	void deleteRoleIfo(String c_id);

	MenuTree getPermTree(String roleId);

}
