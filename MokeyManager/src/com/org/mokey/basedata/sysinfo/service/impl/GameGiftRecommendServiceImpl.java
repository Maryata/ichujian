package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.GameGiftRecommendDao;
import com.org.mokey.basedata.sysinfo.service.GameGiftRecommendService;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏礼包推荐
 */
public class GameGiftRecommendServiceImpl implements GameGiftRecommendService{
	
	private GameGiftRecommendDao gameGiftRecommendDao;

	public GameGiftRecommendDao getGameGiftRecommendDao() {
		return gameGiftRecommendDao;
	}

	public void setGameGiftRecommendDao(GameGiftRecommendDao gameGiftRecommendDao) {
		this.gameGiftRecommendDao = gameGiftRecommendDao;
	}

	@Override
	// 查询未作为推荐的礼包
	public Map<String, Object> getAllGameGift(int page, String name,
			String giftCate) {
		return gameGiftRecommendDao.getAllGameGift(page, name, giftCate);
	}

	@Override
	// 查询推荐的礼包
	public Map<String, Object> recommendGiftList(int page, String name) {
		return gameGiftRecommendDao.recommendGiftList(page, name);
	}

	@Override
	// 礼包维护
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
		// 添加礼包到当前推荐
		if(null!=gids && gids.length>0){
			for (int i = 0; i < gids.length; i++) {
				// 查询礼包是否已经存在
				int cateCount = gameGiftRecommendDao.isExist(gids[i]);
				if(cateCount==0){
					// 如果不存在，新增
					gameGiftRecommendDao.add(gids[i], orders[i]);
				}else{
					// 如果存在，更新排序
					gameGiftRecommendDao.update(gids[i], orders[i]);
				}
			}
		}
		// 移除出推荐
		if(null!=removeGids && removeGids.length>0){
			for (String id : removeGids) {
				if(StrUtils.isEmpty(id)){
					continue;
				}
				gameGiftRecommendDao.remove(id);
			}
		}
	}


}
