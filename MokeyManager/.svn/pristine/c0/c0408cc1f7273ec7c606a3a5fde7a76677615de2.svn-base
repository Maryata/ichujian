package com.org.mokey.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.system.dao.SysUserDao;
import com.org.mokey.system.entiy.TSysUser;
import com.org.mokey.system.service.SysUserService;
import com.org.mokey.util.StrUtils;

public class SysUserServiceImpl implements SysUserService {

	protected Logger log = (Logger.getLogger(getClass()));
	
	private SysUserDao sysUserDao;
	public SysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Override
	public TSysUser userLogin(String username, String password) {
		return sysUserDao.userLogin(username,password);
	}

	@Override
	public boolean updatePass(String userId, String newPassword) {
		return sysUserDao.updatePass(userId,newPassword);
	}

	@Override
	public Map<String, Object> getUserIfoListMap(String c_usercode,
			String c_username, int start, int limit) {
		return sysUserDao.getUserIfoListMap(c_usercode, c_username, start, limit);
	}

	@Override
	public Map<String, Object> checkUserInfo(String checkType, String value,
			String id) {
		return sysUserDao.checkUserInfo(checkType, value, id);
	}

	@Override
	public List getSuppliers() {
		return sysUserDao.getSuppliers();
	}

	@Override
	public List getUserRoles() {
		return sysUserDao.getUserRoles();
	}

	@Override
	public String saveUserIfo(Map<String, Object> saveMap) {
		return sysUserDao.saveUserIfo(saveMap);
	}

	@Override
	public void deleteUserIfo(String c_id) {
		sysUserDao.deleteUserIfo(c_id);
	}

	@Override
	public Map<String, Object> getUserIfoById(String userId) {
		return sysUserDao.getUserIfoById(userId);
	}

	@Override
	public List getUserRoleById(String userId) {
		return sysUserDao.getUserRoleById(userId);
	}

	@Override
	public List getUserFunctions(String userId) {
		return sysUserDao.getUserFunctions(userId);
	}

}
