package com.sys.easybuy.service;

import java.util.List;
import java.util.Map;

public interface EasyBuyService {

	/**
	 * 广告位信息
	 * @param user 激活码6-11位
	 * @return
	 */
	public List<Map<String, Object>> advertInfo(String user);

	/**
	 * 分类列表
	 * @param user 激活码6-11位
	 * @return
	 */
	public List<Map<String, Object>> category(String user);

	/**
	 * 可维护分类详情（首页专题详情）
	 * @param user 激活码6-11位
	 * @return
	 */
	public List<Map<String, Object>> customableCategory(String user);

	/**
	 * "附近门店"
	 * @param user 激活码6-11位
	 * @return
	 */
	public List<Map<String, Object>> storesNearby(String user);

	/**
	 * 分类详情
	 * @param user 激活码6-11位
	 * @param cid 分类id
	 * @param page 分页
	 * @param flag 是否可维护分类
	 * @return
	 */
	public List<Map<String, Object>> categoryDetail(String user, String cid, String page, String flag);

	/**
	 * 产品详情
	 * @param id 产品id
	 * @return
	 */
	public List<Map<String, Object>> productDetail(String id);

	/**
	 * 简明产品搜索
	 * @param user 激活码6-11位
	 * @param content 搜索内容
	 * @return 
	 */
	public List<Map<String, Object>> simpleSearch(String user, String content);

	/**
	 * 产品搜索
	 * @param user 激活码6-11位
	 * @param content 搜索内容
	 * @return
	 */
	public List<Map<String, Object>> search(String user, String content);

	/**
	 * "猜你喜欢"
	 * @param pid 产品id
	 * @return
	 */
	public List<Map<String, Object>> guessULike(String pid);

}
