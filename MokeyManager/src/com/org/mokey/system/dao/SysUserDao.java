package com.org.mokey.system.dao;

import java.util.List;
import java.util.Map;

import com.org.mokey.system.entiy.TSysUser;

public interface SysUserDao {

	TSysUser userLogin(String username, String password);

	boolean updatePass(String userId, String newPassword);
	
	Map<String, Object> getUserIfoListMap(String c_usercode, String c_username,
			int start, int limit);

	Map<String, Object> checkUserInfo(String checkType, String value, String id);

	List getSuppliers();

	List getUserRoles();

	String saveUserIfo(Map<String, Object> saveMap);

	void deleteUserIfo(String c_id);

	Map<String, Object> getUserIfoById(String userId);

	List getUserRoleById(String userId);

	List getUserFunctions(String userId);

}
