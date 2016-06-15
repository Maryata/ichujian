package com.sys.game.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sys.game.service.GameBaseService;
import com.sys.game.service.GameMallService;
import com.sys.util.StrUtils;

@Service
@SuppressWarnings("deprecation")
public class GameMallServiceImpl extends GameBaseService implements
		GameMallService {
	private Logger LOGGER = Logger.getLogger(GameMallServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	// 查询商品详情
	public List<Map<String, Object>> mallProduct(String pid) {
		try {
			return this.getSqlMapClientTemplate().queryForList("gameMall.mallProduct", pid);
		} catch (Exception e) {
			LOGGER.error("GameMallServiceImpl.mallProduct failed, e : ", e);
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
			this.getSqlMapClientTemplate().insert("gameMall.exchange", paramMap);
		} catch (Exception e) {
			LOGGER.error("GameMallServiceImpl.mallProduct failed, e : ", e);
		}
	}

	@Override
	public List<Map<String, Object>> search(String gid) {
		return _queryForList("gameMall.search", gid);
	}

	@Override
	// 商品兑换记录
	public List<Map<String, Object>> exchangeRecord(String uid, String page, String pSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UID", uid);
		paramMap.put("PAGE", page);
		paramMap.put("PSIZE", StrUtils.isEmpty(pSize)?"20":pSize);
		return _queryForList("gameMall.exchangeRecord", paramMap);
	}
}
