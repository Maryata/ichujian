package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏礼包分类
 */
public interface EKGameGiftCateDao {

	// 分页显示礼包分类
	public Map<String, Object> gameGiftCateList(int page);

	// 新增游戏礼包分类
	public void addGameGiftCate(String id, String name, String logo,
								String order, String modifier);

	// 删除分类
	public void delGameGiftCate(String id);

	// 更新游戏礼包分类
	public void updateGameGiftCate(String id, String name, String logo,
								   String order, String modifier);

	// 查询非当前礼包分类的其他所有礼包
	public Map<String, Object> getAllGameGift(int page, String cid, String name, String giftCate);

	// 查询当前礼包分类的礼包
	public Map<String, Object> getCurrCateGift(int page, String cid, String name);

	// 所有礼包分类
	public List<Map<String,Object>> getGiftCateList();

	// 查询礼包是否已经存在于当前分类
	public int isExist(String cid, String gid);

	// 新增礼包到当前分类
	public void add(String cid, String gid, String order);

	// 更新当前分类中的礼包
	public void update(String cid, String gid, String order);

	// 将指定礼包移除出当前分类
	public void remove(String cid, String gid);


}
