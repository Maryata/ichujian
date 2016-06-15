package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.GameBoutiqueCateDao;
import com.org.mokey.basedata.sysinfo.service.GameBoutiqueCategoryService;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏礼包分类
 */
public class GameBoutiqueCateServiceImpl implements GameBoutiqueCategoryService{
	
	private GameBoutiqueCateDao gameBoutiqueCateDao;

	public GameBoutiqueCateDao getGameBoutiqueCateDao() {
		return gameBoutiqueCateDao;
	}

	public void setGameBoutiqueCateDao(GameBoutiqueCateDao gameBoutiqueCateDao) {
		this.gameBoutiqueCateDao = gameBoutiqueCateDao;
	}

	@Override
	// 分页显示礼包分类
	public Map<String, Object> gameBoutiqueCategoryList(int page) {
		return gameBoutiqueCateDao.gameBoutiqueCateList(page);
	}

	@Override
	// 新增游戏礼包分类
	public void addGameBoutiqueCate(String id, String name, String logo,
			String order, String modifier) {
		gameBoutiqueCateDao.addGameBoutiqueCate(id, name, logo, order, modifier);
	}

	@Override
	// 删除分类
	public void delGameBoutiqueCate(String id) {
		gameBoutiqueCateDao.delGameBoutiqueCate(id);
	}

	@Override
	// 更新游戏礼包分类
	public void updateGameBoutiqueCate(String id, String name, String logo,
			String order, String modifier) {
		gameBoutiqueCateDao.updateGameBoutiqueCate(id, name, logo, order, modifier);
	}

	@Override
	// 查询非当前礼包分类的其他所有礼包
	public Map<String, Object> getAllGame(int page, String cid, String name, String giftCate) {
		return gameBoutiqueCateDao.getAllGame(page, cid, name, giftCate);
	}

	@Override
	// 查询当前礼包分类的礼包
	public Map<String, Object> getCurrCateGame(int page, String cid, String name) {
		return gameBoutiqueCateDao.getCurrCateGame(page, cid, name);
	}

	@Override
	// 所有礼包分类
	public List<Map<String,Object>> getGameBoutiqueCategoryList() {
		return gameBoutiqueCateDao.getGameBoutiqueCateList();
	}

	@Override
	// 分类维护
	public void handleCate(String cid, String gid, String removeGid,
			String order) {
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
		// 添加礼包到当前分类
		if(null!=gids && gids.length>0){
			for (int i = 0; i < gids.length; i++) {
				// 查询礼包是否已经存在
				int cateCount = gameBoutiqueCateDao.isExist(cid, gids[i]);
				if(cateCount==0){
					// 如果不存在，新增
					gameBoutiqueCateDao.add(cid, gids[i], orders[i]);
				}else{
					// 如果存在，更新排序
					gameBoutiqueCateDao.update(cid, gids[i], orders[i]);
				}
			}
		}
		// 移除出分类
		if(null!=removeGids && removeGids.length>0){
			for (String id : removeGids) {
				if(StrUtils.isEmpty(id)){
					continue;
				}
				gameBoutiqueCateDao.remove(cid, id);
			}
		}
	}


}
