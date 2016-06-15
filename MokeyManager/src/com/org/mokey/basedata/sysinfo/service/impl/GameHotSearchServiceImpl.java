package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.GameHotSearchDao;
import com.org.mokey.basedata.sysinfo.service.GameHotSearchService;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏礼包推荐
 */
public class GameHotSearchServiceImpl implements GameHotSearchService{
	
	private GameHotSearchDao gameHotSearchDao;

	public GameHotSearchDao getGameHotSearchDao() {
		return gameHotSearchDao;
	}

	public void setGameHotSearchDao(GameHotSearchDao gameHotSearchDao) {
		this.gameHotSearchDao = gameHotSearchDao;
	}

	@Override
	// 查询未作为推荐的游戏
	public Map<String, Object> getAllGame(int page, String name, String gameCate) {
		return gameHotSearchDao.getAllGame(page, name, gameCate);
	}

	@Override
	// 查询推荐的游戏
	public Map<String, Object> recommendGameList(int page, String name) {
		return gameHotSearchDao.hotSearchGameList(page, name);
	}

	@Override
	// 游戏维护
	public void handle(String gid, String removeGid, String order) {
		String[] gids = gid.split(",");
		String[] removeGids = removeGid.split(",");
		String[] orders = order.split(",");
		// 替换所有的“n”为空
		if(StrUtils.isNotEmpty(orders)){
			for (int i = 0; i < orders.length; i++) {
				if("n".equals(orders[i])){
					orders[i] = "";
				}
			}
		}
		// 添加游戏到当前推荐
		if(null!=gids && gids.length>0){
			for (int i = 0; i < gids.length; i++) {
				// 查询游戏是否已经存在
				int cateCount = gameHotSearchDao.isExist(gids[i]);
				if(cateCount==0){
					// 如果不存在，新增
					gameHotSearchDao.add(gids[i], orders[i]);
				}else{
					// 如果存在，更新排序
					gameHotSearchDao.update(gids[i], orders[i]);
				}
			}
		}
		// 移除出推荐
		if(null!=removeGids && removeGids.length>0){
			for (String id : removeGids) {
				if(StrUtils.isEmpty(id)){
					continue;
				}
				gameHotSearchDao.remove(id);
			}
		}
	}
}
