package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * 游戏帮：游戏礼包推荐
 */
public interface GameGiftRecommendDao {

	// 查询未作为推荐的礼包
	public Map<String, Object> getAllGameGift(int page, String name,
			String giftCate);

	// 查询推荐的礼包
	public Map<String, Object> recommendGiftList(int page, String name);

	// 查询礼包是否已经存在于当前分类
	public int isExist(String gid);

	// 新增礼包到当前分类
	public void add(String gid, String order);

	// 更新当前分类中的礼包
	public void update(String gid, String order);

	// 将指定礼包移除出当前分类
	public void remove(String gid);

}
