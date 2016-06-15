package com.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings("deprecation")
public class PhoneRecordServiceImpl extends SqlMapClientDaoSupport implements PhoneRecordService{
	/** Logger available to subclasses */
	private Logger LOG = (Logger.getLogger(PhoneRecordServiceImpl.class));

	@Override
	// 记录手机号
	public void recordPhone(String imei, String uEmail) {
		try {
			// 1、修改已存在的imei的状态为0
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("IMEI", imei);
			this.getSqlMapClientTemplate().update("phoneRecord.updatePhone", map);
			// 2、保存数据
			map.put("UEMAIL", uEmail);
			this.getSqlMapClientTemplate().update("phoneRecord.recordPhone", map);
			
		} catch (Exception e) {
			LOG.error("PhoneRecordServiceImpl.recordPhone failed, e : " + e);
		}
	}

	@Override
	// 记录手机imei
	public void recordImei(String imei) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("IMEI", imei);
			this.getSqlMapClientTemplate().update("phoneRecord.recordImei", map);
		} catch (Exception e) {
			LOG.error("PhoneRecordServiceImpl.recordImei failed, e : " + e);
		}
	}
}
