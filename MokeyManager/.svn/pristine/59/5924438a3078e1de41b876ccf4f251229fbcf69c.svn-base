package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.GameCueDao;
import com.org.mokey.basedata.sysinfo.service.GameCueService;

public class GameCueServiceImpl implements GameCueService {
	
	private GameCueDao gameCueDao;

	public GameCueDao getGameCueDao() {
		return gameCueDao;
	}

	public void setGameCueDao(GameCueDao gameCueDao) {
		this.gameCueDao = gameCueDao;
	}

	@Override
	// 询游戏提示语
	public List<Map<String, Object>> gameCue() {
		return gameCueDao.gameCue();
	}

	@Override
	// 添加提示语
	public void addCue(String addTitle) {
		gameCueDao.addCue(addTitle);
	}

	@Override
	// 修改提示语
	public void modifyCue(String cid, String title, String islive) {
		gameCueDao.modifyCue(cid,title,islive);
	}

	@Override
	// 使当前提示内容生效/无效
	public void isvalid(String cid, String islive) {
		gameCueDao.isvalid(cid,islive);
	}

	@Override
	// 删除提示内容
	public void deleteCol(String cid) {
		gameCueDao.deleteCol(cid);
	}
	
}
