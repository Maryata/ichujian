package com.sys.game.service;

import java.util.List;
import java.util.Map;

public interface GameStrategyService {

	/**
	 * 
	 * @Description: TODO 游戏攻略列表
	 * @return
	 */
	public List<Map<String, Object>> raiders(long gid,int page, int rows);

	/**
	 * 
	 * @Description: TODO 获取游戏攻略详情
	 * @param id 游戏攻略ID
	 * @param uid 
	 * @return 游戏攻略对象
	 */
	public Map<String, Object> getRaidersById(long id, String uid, String source);

	/**
	 * 攻略搜索
	 * @param uid 用户ID
	 * @param imei 设备IMEI
	 * @param content 搜索内容
	 * @param type 搜索类型
     * @return
     */
	List<Map<String,Object>> search(int uid, String imei, String content, String type);
}
