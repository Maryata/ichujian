package com.sys.ekey.game.service;

import java.util.List;
import java.util.Map;

public interface EKGameActivityService {

	/**
	 * 游戏活动列表
	 * @param gid 游戏ID
	 * @param pageNumber 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	public List<Map<String, Object>> listActivity(long gid, int pageNumber,
												  int pageSize);

	/**
	 * 获取活动
	 * @param id 活动ID
	 * @param uid 用户ID
	 * @param source 用户行为中的来源，这边应该是是否点赞
	 * @return
	 */
	public Map<String, Object> getActivityById(long id, String uid,
											   String source);

	/**
	 * 所有正在进行的活动列表
	 * @param pageNumber 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	List<Map<String,Object>> listActivity(int pageNumber, int pageSize);

	/**
	 * 往期活动列表
	 * @param pageNumber 当前页
	 * @param pageSize 每页大小
     * @return
     */
	List<Map<String,Object>> listOldActivity(int pageNumber, int pageSize);

	/**
	 * 用户对活动的回复
	 * @param id 活动ID
	 * @param uid 用户ID
	 * @return
	 */
	Map<String,Object> getReply(long id, String uid);

	/**
	 * 我的活动
	 * @param uid 用户ID
	 * @param pSize 每页显示数
	 * @param page 页码
	 * @return
	 */
	public List<Map<String, Object>> attendedAct(String uid, String page, String pSize);

	/**
	 * 回复活动信息
	 * @param id 该记录id（如果是修改，这个id会有）
	 * @param uid 用户ID
	 * @param aid 活动ID
	 * @param comment 回复内容
	 * @param imgArr 回复图片数组
	 */
	void campusActivities(String id, String uid, String aid, String comment, String[] imgArr);

}
