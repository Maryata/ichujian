package com.sys.game.service;

import java.util.List;
import java.util.Map;

public interface GameGiftService {

	/**
	 * 手游-礼包内容页
	 * @Description: TODO
	 * @param uid 用户ID
	 * @return
	 */
	public Map<String, Object> giftsIndex(long uid);

	/**
	 * 手游-礼包分类下的具体分类里面的礼包
	 * @Description: TODO
	 * @param uid 用户ID
	 * @param categoryId 具体分类ID
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @return
	 */
	public List<Map<String, Object>> giftsCategoryDetail(long uid,
			int categoryId, int pageNumber, int pageSize);

	/**
	 * 获取所有游戏礼包
	 * @param uid 用户id
	 * @param pageIndex 分页页码
	 * @return
	 */
	public List<Map<String, Object>> gameGiftList(String uid, String pageIndex);

	/**
	 * 获取单个游戏礼包
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @return
	 */
	public List<Map<String, Object>> gameGift(String uid, String gid);

	/**
	 * 获取当前用户的礼包码
	 * @param uid 用户id
	 * @param gid 礼包id
	 * @return
	 */
	public List<Map<String, Object>> usersGiftCode(String uid, String gid);

	/**
	 * 获取一条礼包码
	 * @param gid 礼包id
	 * @return
	 */
	public List<Map<String, Object>> getGiftCode(String gid);

	/**
	 * 更新礼包码
	 * @param cid 礼包码id
	 */
	public void updateGiftCode(String cid);

	/**
	 * 添加用户对礼包的操作行为
	 * @param uid 用户id
	 * @param gid 礼包id
	 * @param type 操作类型  0：查看    1：领取  2：淘号  3：复杂并打开
	 * @param code 礼包码
	 */
	public void addUserActionOfGift(String uid, String gid, String string,
			String code);

	/**
	 * 查询礼包详情
	 * @param uid 用户id
	 * @param gid 礼包id
	 * @return
	 */
	public List<Map<String, Object>> giftDetail(String uid, String gid);

	/**
	 * 礼包淘号
	 * @param gid 礼包id
	 * @return
	 */
	public List<Map<String, Object>> drawGiftCode(String gid);

	/**
	 * 获取当前用户的礼包
	 * @param uid 用户id
	 * @return
	 */
	public List<Map<String, Object>> usersGifts(String uid);

	/**
	 * 礼包推荐
	 * @param uid 用户id
	 * @return
	 */
	public List<Map<String, Object>> recommendGift(String uid);

}
