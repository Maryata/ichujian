package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.GameTaskDao;
import com.org.mokey.basedata.sysinfo.service.GameTaskService;

public class GameTaskServiceImpl implements GameTaskService {

	private GameTaskDao gameTaskDao;

	@Override
	public void delete(String id) {
		if( id == null || id.isEmpty() ) return;
		
		gameTaskDao.delete(id);
	}

	@Override
	public Map<String, Object> list(String name, int start, int limit) {
		return gameTaskDao.list( name, start, limit );
	}

	@Override
	public void save(Map<String, Object> map) {
		gameTaskDao.save(map);
	}

	public GameTaskDao getGameTaskDao () {
		return gameTaskDao;
	}

	public void setGameTaskDao (GameTaskDao gameTaskDao) {
		this.gameTaskDao = gameTaskDao;
	}

	@Override
	public List<Map<String, Object>> getAllRewards() {
		return gameTaskDao.getAllRewards();
	}

	@Override
	public void addReward(String days, String score) {
		gameTaskDao.addReward(days, score);
	}

	@Override
	public void delReward(String id) {
		gameTaskDao.delReward(id);
	}

	@Override
	public void handleReward(String id, String days, String score) {
		gameTaskDao.handleReward(id, days, score);
	}
}
