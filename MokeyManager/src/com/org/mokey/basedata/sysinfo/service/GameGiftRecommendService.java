package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

/**
 * 游戏帮：游戏礼包推荐
 */
public interface GameGiftRecommendService {

	/**
	 * 查询未作为推荐的礼包
	 * @param page 分页
	 * @param name 礼包名称
	 * @param giftCate 礼包分类
	 * @return
	 */
	public Map<String, Object> getAllGameGift(int page, String name,
			String giftCate);

	/**
	 * 查询推荐的礼包
	 * @param page 分页
	 * @param name 礼包名称
	 * @return
	 */
	public Map<String, Object> recommendGiftList(int page, String name);

	/**
	 * 推荐维护 
	 * @param gid 礼包id
	 * @param removeGid 移除出推荐的礼包
	 * @param order 推荐礼包的排序
	 */
	public void handle(String gid, String removeGid, String order);

}
