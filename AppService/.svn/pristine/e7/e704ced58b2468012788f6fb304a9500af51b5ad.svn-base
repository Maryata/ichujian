package com.sys.ekey.game.service.impl;

import com.sys.ekey.game.service.EKGameBaseService;
import com.sys.ekey.game.service.EKGameMallService;
import com.sys.game.service.GameBaseService;
import com.sys.game.service.GameMallService;
import com.sys.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service
@SuppressWarnings("deprecation")
public class EKGameMallServiceImpl extends EKGameBaseService implements
		EKGameMallService {
	private Logger LOGGER = Logger.getLogger(EKGameMallServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	// 查询商品详情
	public List<Map<String, Object>> mallProduct(String pid) {
		try {
			return this.getSqlMapClientTemplate().queryForList("ek_gameMall.mallProduct", pid);
		} catch (Exception e) {
			LOGGER.error("EKGameMallServiceImpl.mallProduct failed, e : ", e);
		}
		return null;
	}

	@Override
	// 申请商品兑换
	public void exchange(String pid, String uid, String contact, String type) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PID", pid);
			paramMap.put("UID", uid);
			paramMap.put("CONTACT", contact);
			paramMap.put("TYPE", type);
			paramMap.put("CDATE", new Date());
			this.getSqlMapClientTemplate().insert("ek_gameMall.exchange", paramMap);
		} catch (Exception e) {
			LOGGER.error("EKGameMallServiceImpl.mallProduct failed, e : ", e);
		}
	}

	@Override
	public List<Map<String, Object>> search(String gid) {
		return _queryForList("ek_gameMall.search", gid);
	}

	@Override
	// 商品兑换记录
	public List<Map<String, Object>> exchangeRecord(String uid, String page, String pSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UID", uid);
		paramMap.put("PAGE", page);
		paramMap.put("PSIZE", StrUtils.isEmpty(pSize)?"20":pSize);
		return _queryForList("ek_gameMall.exchangeRecord", paramMap);
	}
}
