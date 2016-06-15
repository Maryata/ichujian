package com.sys.ekey.game.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EKGameH5Service {

	/**
	 * H5游戏分类列表
	 * @return
	 */
	public List<Map<String, Object>> h5CategoryList();

	/**
	 * 保存用户发弹幕的行为
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param type 操作类型  0：下载    1：卸载  2：启动   3：查看 4：退出 5：发弹幕
	 * @param source 操作内容：0： app游戏  1：h5游戏
	 */
	public void saveH5Barrage(String uid, String gid, String type, String source);

	/**
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> recentlyPlaying(String uid, String type);

	/**
	 * 根据分类id获取具体某一分类中的游戏(H5)
	 * @param cid 分类id
	 * @param pageindex 分页
	 * @return
	 */
	public List<Map<String, Object>> h5GamesInCategory(String cid,
													   String pageindex);

	/**
	 * @Description: 小游戏搜索
	 * @param uid 用户ID
	 * @param imei IMEI
	 * @param content 搜索内容
	 * @param date 搜索时间
	 * @return
	 */
	List<Map<String,Object>> searchH5(int uid, String imei, String content, Date date, String type);

	/**
	 * 查询h5游戏
	 * @param gid 游戏id
	 * @return
	 */
	Map<String,Object> getH5GameById(String gid);
}
