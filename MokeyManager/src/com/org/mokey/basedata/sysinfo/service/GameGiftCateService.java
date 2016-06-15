package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏礼包分类
 */
public interface GameGiftCateService {

	/**
	 * 分页显示礼包分类
	 * @param page 分页
	 * @return
	 */
	public Map<String, Object> gameGiftCateList(int page);

	/**
	 * 新增游戏礼包分类
	 * @param id 主键
	 * @param name 分类名称 
	 * @param logo logo
	 * @param order 排序
	 * @param modifier 修改人
	 */
	public void addGameGiftCate(String id, String name, String logo,
			String order, String modifier);

	/**
	 * 删除分类
	 * @param id 分类id
	 */
	public void delGameGiftCate(String id);

	/**
	 * 更新游戏礼包分类
	 * @param id 主键
	 * @param name 分类名称 
	 * @param logo logo
	 * @param order 排序
	 * @param modifier 修改人
	 */
	public void updateGameGiftCate(String id, String name, String logo,
			String order, String modifier);

	/**
	 * 查询非当前礼包分类的其他所有礼包
	 * @param page 分页
	 * @param cid 分类id
	 * @param name 礼包名称
	 * @param giftCate 礼包初始分类
	 * @return
	 */
	public Map<String, Object> getAllGameGift(int page, String cid, String name, String giftCate);

	/**
	 * 查询当前礼包分类的礼包
	 * @param page 分页
	 * @param cid 分类id
	 * @param name 礼包名称
	 * @return
	 */
	public Map<String, Object> getCurrCateGift(int page, String cid, String name);

	/**
	 * 所有礼包分类
	 * @return
	 */
	public List<Map<String,Object>> getGiftCateList();

	/**
	 * 礼包分类维护 
	 * @param cid 分类id
	 * @param gid 礼包id
	 * @param removeGid 移除出当前分类的礼包id
	 * @param order 排序
	 */
	public void handleCate(String cid, String gid, String removeGid,
			String order);

}
