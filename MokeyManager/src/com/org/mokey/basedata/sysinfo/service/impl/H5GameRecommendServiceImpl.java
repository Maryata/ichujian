package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.H5GameRecommendDao;
import com.org.mokey.basedata.sysinfo.service.H5GameRecommendService;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：H5游戏推荐
 */
public class H5GameRecommendServiceImpl implements H5GameRecommendService{
	
	private H5GameRecommendDao h5GameRecommendDao;

	public H5GameRecommendDao getH5GameRecommendDao() {
		return h5GameRecommendDao;
	}

	public void setH5GameRecommendDao(H5GameRecommendDao h5GameRecommendDao) {
		this.h5GameRecommendDao = h5GameRecommendDao;
	}

	@Override
	// 查询未作为推荐的H5游戏
	public Map<String, Object> getAllGame(int page, String name, String gameCate) {
		return h5GameRecommendDao.getAllGame(page, name, gameCate);
	}

	@Override
	// 查询推荐的H5游戏
	public Map<String, Object> recommendGameList(int page, String name) {
		return h5GameRecommendDao.recommendGameList(page, name);
	}

	@Override
	// H5游戏维护
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
		// 添加H5游戏到当前推荐
		if(null!=gids && gids.length>0){
			for (int i = 0; i < gids.length; i++) {
				// 查询H5游戏是否已经存在
				int cateCount = h5GameRecommendDao.isExist(gids[i]);
				if(cateCount==0){
					// 如果不存在，新增
					h5GameRecommendDao.add(gids[i], orders[i]);
				}else{
					// 如果存在，更新排序
					h5GameRecommendDao.update(gids[i], orders[i]);
				}
			}
		}
		// 移除出推荐
		if(null!=removeGids && removeGids.length>0){
			for (String id : removeGids) {
				if(StrUtils.isEmpty(id)){
					continue;
				}
				h5GameRecommendDao.remove(id);
			}
		}
	}


}
