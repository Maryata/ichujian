package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.GameRecommendDao;
import com.org.mokey.basedata.sysinfo.service.GameRecommendService;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏游戏推荐
 */
public class GameRecommendServiceImpl implements GameRecommendService{
	
	private GameRecommendDao gameRecommendDao;

	public GameRecommendDao getGameRecommendDao() {
		return gameRecommendDao;
	}

	public void setGameRecommendDao(GameRecommendDao gameRecommendDao) {
		this.gameRecommendDao = gameRecommendDao;
	}

	@Override
	// 查询未作为推荐的游戏
	public Map<String, Object> getAllGame(int page, String name, String gameCate) {
		return gameRecommendDao.getAllGame(page, name, gameCate);
	}

	@Override
	// 查询推荐的游戏
	public Map<String, Object> recommendGameList(int page, String name) {
		return gameRecommendDao.recommendGameList(page, name);
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
				int cateCount = gameRecommendDao.isExist(gids[i]);
				if(cateCount==0){
					// 如果不存在，新增
					gameRecommendDao.add(gids[i], orders[i]);
				}else{
					// 如果存在，更新排序
					gameRecommendDao.update(gids[i], orders[i]);
				}
			}
		}
		// 移除出推荐
		if(null!=removeGids && removeGids.length>0){
			for (String id : removeGids) {
				if(StrUtils.isEmpty(id)){
					continue;
				}
				gameRecommendDao.remove(id);
			}
		}
	}


}
