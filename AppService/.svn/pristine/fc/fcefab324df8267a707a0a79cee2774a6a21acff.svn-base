package com.sys.game.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GameAppService {

	/**
	 *  通过游戏ID获取游戏详情
	 * @param gid 游戏id
	 * @return 游戏属性集合(包含名称，厂商， logo地址， app地址， jar包名称 , 简介
	 * 		,类型 1智力 2酷跑...，版本, 发布时间, 截图, 大小，来源, 星级, 下载数等等
	 */
	public Map<String, Object> getGameDetailByGameId(Long gid);

	/**
	 *  用户评分操作
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param date 评分时间
	 * @param grade 评论分数
	 * @param isLive 是否有效 0表示无效，1表示有效
	 * @return Integer 评分操作成功返回1，否则返回0
	 */
	public Integer userRating(Long uid, Long gid, Date date, Float grade,
			String isLive);

	/**
	 *  对用户行为进行记录：某用户对某游戏进行（下载、卸载、启动、查看）操作
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param date 操作日期
	 * @param type 0：下载    1：卸载  2：启动   3：查看
	 * @param jarname 游戏jar包名称
	 * @param source 操作内容：0： app游戏  1：攻略  2：活动  3：资讯 4：h5游戏
	 * @return Integer 0表示对用户行为记录成功，1表示记录失败
	 */
	public Integer persistentUserBehavior(Long uid, Long gid, Date date,
			Integer type, String jarname,String source,String imei);

	/**
	 * 
	 *  游戏子项用户使用行为（攻略、活动等）
	 * @param uid 用户ID
	 * @param gid 游戏ID
	 * @param date 操作日期
	 * @param type 操作内容：0:查看  1：分享
	 * @param indexid 活动、攻略、资讯 ID
	 * @param source 操作内容：0：功率  1：活动  2：资讯
	 * @return 0表示对用户行为记录成功，1表示记录失败
	 */
	public Integer persistentUserBehavior(Long uid, Long gid, Date date, Integer type,
			Long indexid, String source);

	/**
	 *  用户评论操作
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param date 评论时间
	 * @param comment 评论内容
	 * @param isLive 是否有效
	 * @param grade 分值
	 * @return 评论操作成功返回1，否则返回0
	 */
	public Integer userComments(Long uid, Long gid, Date date, String comment,
			String isLive, float grade);

	/**
	 *  统计游戏星级评论
	 * @param gid 游戏id
	 * @return 游戏各星级的评论数
	 */
	public List<Map<String, Object>> starGameStatistics(Long gid);

	/**
	 *  通过游戏id获取游戏评论列表
	 * @param gid 游戏id
	 * @param pageNumber 当前页数
	 * @param pageSize 每页大小
	 * @return 该游戏评论列表信息
	 */
	public List<Map<String, Object>> getThroughTheGameIdGameReviewList(
			Long gid, Integer pageNumber, Integer pageSize);

	/**
	 *  通过游戏id获取游戏评论列表
	 * @param gid 游戏id
	 * @param uid 当前用户id
	 * @param pageNumber 当前页数
	 * @param pageSize 每页大小
	 * @return 该游戏评论列表信息，第一条信息为当前用户评论（如果存在）
	 */
	public List<Map<String, Object>> getThroughTheGameIdGameReviewList(
			Long gid, Long uid, Integer pageNumber, Integer pageSize);

	/**
	 *  获取该用户对该游戏的评分
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @return
	 */
	public List<Map<String, Object>> getUserRating(long uid, long gid);

	/**
	 * 是否点赞
	 * 
	 * @param id 活动、攻略、资讯ID
	 * @param uid 用户ID
	 * @return
	 */
	public Map<String, Object> isLike(Long id, String uid, String source);

	/**
	 * 获取游戏APP信息
	 * @author Maryn
	 * @return 所有游戏APP的包名
	 */
	public List<Map<String, Object>> gameInfo();

	/**
	 * 根据JAR包名获取游戏LOGO
	 * @param jarName JAR包名
	 * @return
	 */
	public String getLogo(String jarName);

	/**
	 * 游戏分类列表
	 * @return
	 */
	public List<Map<String, Object>> categoryList();

	/**
	 * 根据分类id获取具体某一分类中的游戏
	 * @param cid 分类id
	 * @param pageindex 分页
	 * @return
	 */
	public List<Map<String, Object>> gamesInCategory(String cid,
			String pageindex);

	/**
	 * 根据游戏id查询所在分类中推荐的游戏
	 * @return
	 */
	public List<Map<String, Object>> gameRecommend();

	/**
	 * 获取首页广告
	 * @return 
	 */
	public List<Map<String, Object>> advertInfo();

	/**
	 * 合集详情
	 * @param cid 合集id
	 * @param flag 分类数量
	 * @param pageindex 页码
	 * @param pSize 每页显示数
	 * @return
	 */
	public List<Map<String, Object>> gameCollection(String cid, String flag, String pageindex, String pSize);

	/**
	 * 全局搜索
	 * @param gid 游戏IE
	 * @return
	 */
	List<Map<String,Object>> search(String gid);

	/**
	 * @Description:  app搜索
	 * @param uid 用户ID
	 * @param imei imei
	 * @param content 搜索内容
	 * @param date 搜索时间
	 * @return
	 */
	List<Map<String,Object>> searchApp(int uid, String imei,String content,Date date, String type);
}
