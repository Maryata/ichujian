package com.org.mokey.system.dao;

import java.util.List;
import java.util.Map;

public interface SysRoleDao {
	
	Map<String, Object> getRoleIfoListMap(String rolecode, String rolename,
			int start, int limit);

	Map<String, Object> checkRoleInfo(String checkType, String value, String id);

	String saveRoleIfo(Map<String, Object> saveMap);

	void deleteRoleIfo(String c_id);

	List getRoleFunctionById(String roleId);
	
	List getFunctionByPid(String fid);

	void saveRoleFunct(String funId, String roleId);

	void deleteRoleFunct(String funId, String roleId);

}
