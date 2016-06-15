package com.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.sys.service.MemberService;

@SuppressWarnings("deprecation")
@Service
public class MemberServiceImpl extends SqlMapClientDaoSupport implements MemberService {
	private Logger LOGGER = (Logger.getLogger(MemberServiceImpl.class));

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> isUsableCode(String phonenum,
			String valicode, String s_date, String type) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("DATE", s_date);
			map.put("VALICODE", valicode);
			map.put("TYPE", type);
			map.put("PHONENUM", phonenum);
			return this.getSqlMapClientTemplate().queryForList("member.isUsableCode", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.isUsableCode failed! e : ", e);
		}
		return null;
	}

	@Override
	public String register(String phonenum, String pwd, String supcode) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PHONENUM", phonenum);
			map.put("PWD", pwd);
			map.put("DATE", new Date());
			map.put("SUPCODE", supcode);
			return (String) this.getSqlMapClientTemplate().insert("member.register", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.register failed! e : ", e);
		}
		return null;
	}

	@Override
	public String getUid(String regid) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("REGID", regid);
			return (String) this.getSqlMapClientTemplate().queryForObject("member.getUid", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.getUid failed! e : ", e);
		}
		return null;
	}

	@Override
	public void defaultNickname(String nickname, String regid) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("NICKNAME", nickname);
			map.put("REGID", regid);
			this.getSqlMapClientTemplate().update("member.defaultNickname", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.defaultNickname failed! e : ", e);
		}
	}

	@Override
	public void insertImei(String regid, String imei, String valicode, String uid) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("REGID", regid);
			map.put("IMEI", imei);
			map.put("VALICODE", valicode);
			map.put("DATE", new Date());
			map.put("UID", uid);
			this.getSqlMapClientTemplate().update("member.insertImei", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.insertImei failed! e : ", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> login(String phonenum, String pwd) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PHONENUM", phonenum);
			map.put("PWD", pwd);
			return this.getSqlMapClientTemplate().queryForList("member.login", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.login failed! e : ", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMemberImei(String regid, String imei) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("REGID", regid);
			map.put("IMEI", imei);
			return this.getSqlMapClientTemplate().queryForList("member.getMemberImei", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.getMemberImei failed! e : ", e);
		}
		return null;
	}

	@Override
	public void updateIsLogin(String regid, String imei) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("REGID", regid);
			map.put("IMEI", imei);
			this.getSqlMapClientTemplate().update("member.updateIsLogin", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.updateIsLogin failed! e : ", e);
		}
	}

	@Override
	public void updateIsNotLogin(String regid, String imei) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("REGID", regid);
			map.put("IMEI", imei);
			this.getSqlMapClientTemplate().update("member.updateIsNotLogin", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.invalidatedOther failed! e : ", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findUserByUidNPwd(String uid, String pwd) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UID", uid);
			map.put("PWD", pwd);
			return this.getSqlMapClientTemplate().queryForList("member.findUserByUidNPwd", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.findUserByUidNPwd failed! e : ", e);
		}
		return null;
	}

	@Override
	public void modifyPwd(String uid, String oldpwd, String newpwd) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UID", uid);
			map.put("OPWD", oldpwd);
			map.put("NPWD", newpwd);
			map.put("ACTIONDATE", new Date());
			this.getSqlMapClientTemplate().update("member.modifyPwd", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.modifyPwd failed! e : ", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findByPhone(String phonenum) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PHONENUM", phonenum);
			return this.getSqlMapClientTemplate().queryForList("member.findByPhone", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.findByPhone failed! e : ", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getValiCode(String phonenum, String type,
			String date) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PHONENUM", phonenum);
			map.put("TYPE", type);
			map.put("DATE", date);
			return this.getSqlMapClientTemplate().queryForList("member.getValiCode", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.getValiCode failed! e : ", e);
		}
		return null;
	}

	@Override
	public void invalidatedOther(String phonenum, String type) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PHONENUM", phonenum);
			map.put("TYPE", type);
			this.getSqlMapClientTemplate().update("member.invalidatedOther", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.invalidatedOther failed! e : ", e);
		}
	}

	@Override
	public void insertValicode(String imei, String phonenum, String type,
			Date date, String code) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("IMEI", imei);
			map.put("DATE", date);
			map.put("CODE", code);
			map.put("TYPE", type);
			map.put("PHONENUM", phonenum);
			this.getSqlMapClientTemplate().insert("member.insertValicode", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.insertValicode failed! e : ", e);
		}
	}

	@Override
	public void setNewPwd(String phonenum, String pwd) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PHONENUM", phonenum);
			map.put("PWD", pwd);
			map.put("ACTIONDATE", new Date());
			this.getSqlMapClientTemplate().update("member.setNewPwd", map);
		} catch (DataAccessException e) {
			LOGGER.error("MemberServiceImpl.setNewPwd failed! e : ", e);
		}
	}
}
