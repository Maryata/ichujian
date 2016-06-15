package com.sys.game.service;

import java.util.List;
import java.util.Map;

public interface GameMallService {

	/**
	 * 查询商品详情
	 * @param pid 商品id
	 * @return
	 */
	public List<Map<String, Object>> mallProduct(String pid);

	/**
	 * 申请商品兑换
	 * @param pid 商品id
	 * @param uid 用户id
	 * @param contact 联系方式
	 * @param type 联系方式类型
	 */
	public void exchange(String pid, String uid, String contact, String type);

	/**
	 * 某个游戏下的商品搜索
	 * @param gid 游戏ID
	 * @return
	 */
	List<Map<String,Object>> search(String gid);

	/**
	 * 商品兑换记录
	 * @param uid 用户id
	 * @param page 页码
	 * @param pSize 每页数量
	 * @return
	 */
	public List<Map<String, Object>> exchangeRecord(String uid, String page, String pSize);
}
