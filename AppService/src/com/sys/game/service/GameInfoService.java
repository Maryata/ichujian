package com.sys.game.service;

import java.util.List;
import java.util.Map;


public interface GameInfoService {

	/**
	 * 
	 * @Description: TODO 获取游戏资讯详情 
	 * @param id 资讯ID
	 * @param uid 
	 * @return 游戏资讯对象
	 */
	public Map<String, Object> getInformationById(long id, String uid, String source);

	/**
	 * @Description: TODO 游戏资讯列表
	 * @param gid 游戏id
	 * @param page 当前页
	 * @param rows 每页数量
	 * @return
	 */
	public List<Map<String, Object>> listInformation(long gid, int pageNumber,
			int pageSize);

}
