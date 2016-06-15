/**
 * 
 */
package com.sys.game.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author vpc
 *	游戏模块接口
 */
public interface GameService {
	/**
	 * @Description: TODO 对用户行为进行记录：某用户对某游戏进行（下载、卸载、启动、查看）操作
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param date 操作日期
	 * @param type 0：下载    1：卸载  2：启动   3：查看
	 * @param jarname 游戏jar包名称
	 * @param source 操作内容：0： app游戏  1：攻略  2：活动  3：资讯 4：h5游戏
	 * @return Integer 0表示对用户行为记录成功，1表示记录失败
	 */
	Integer persistentUserBehavior( Long uid, Long gid, Date date, Integer type, String jarname, String source );
	
	/**
	 * 
	 * @Description: TODO 游戏子项用户使用行为（攻略、活动等）
	 * @param uid 用户ID
	 * @param gid 游戏ID
	 * @param date 操作日期
	 * @param type 操作内容：0:查看  1：分享
	 * @param indexid 活动、攻略、资讯 ID
	 * @param source 操作内容：0：功率  1：活动  2：资讯
	 * @return 0表示对用户行为记录成功，1表示记录失败
	 */
	Integer persistentUserBehavior( Long uid, Long gid, Date date, Integer type, Long indexid, String source );
	
	/**
	 * @Description: TODO 用户评分操作
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param date 评分时间
	 * @param grade 评论分数
	 * @param isLive 是否有效 0表示无效，1表示有效
	 * @return Integer 评分操作成功返回1，否则返回0
	 */
	Integer userRating( Long uid, Long gid, Date date, Float grade, String isLive );
	
	/**
	 * @Description: TODO 用户评论操作
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param date 评论时间
	 * @param comment 评论内容
	 * @param isLive 是否有效
	 * @param grade 分值
	 * @return 评论操作成功返回1，否则返回0
	 */
	Integer userComments(Long uid, Long gid, Date date, String comment, String isLive, Float grade);
	
	/**
	 * @Description: TODO 通过游戏ID获取游戏详情
	 * @param gid 游戏id
	 * @return 游戏属性集合(包含名称，厂商， logo地址， app地址， jar包名称 , 简介
	 * 		,类型 1智力 2酷跑...，版本, 发布时间, 截图, 大小，来源, 星级, 下载数等等
	 */
	Map<String, Object> getGameDetailByGameId(Long gid);
	
	/**
	 * @Description: TODO 统计游戏星级评论
	 * @param gid 游戏id
	 * @return 游戏各星级的评论数
	 */
	List<Map<String,Object>> starGameStatistics(Long gid);
	
	/**
	 * @Description: TODO 通过游戏id获取游戏评论列表
	 * @param gid 游戏id
	 * @param pageNumber 当前页数
	 * @param pageSize 每页大小
	 * @return 该游戏评论列表信息
	 */
	List<Map<String, Object>> getThroughTheGameIdGameReviewList(Long gid, Integer pageNumber, Integer pageSize);
	
	/**
	 * @Description: TODO 通过游戏id获取游戏评论列表
	 * @param gid 游戏id
	 * @param uid 当前用户id
	 * @param pageNumber 当前页数
	 * @param pageSize 每页大小
	 * @return 该游戏评论列表信息，第一条信息为当前用户评论（如果存在）
	 */
	List <Map<String, Object>> getThroughTheGameIdGameReviewList(Long gid,Long uid, Integer pageNumber, Integer pageSize);
	
	/**
	 * @param uid 用户id
	 * @param idea 用户反馈
	 * @return 反馈意见是否持久化成功，1表示成功，0表示失败
	 */
	Integer userFeedback(Long uid, String idea);
	
	/**
	 * @Description: TODO 获取该用户对该游戏的评分
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @return
	 */
	List<Map<String, Object>> getUserRating(long uid, long gid);
	
	/**
	 * @Description: TODO 游戏资讯列表
	 * @param gid 游戏id
	 * @param page 当前页k
	 * @param rows 每页数量
	 * @return
	 */
	List<Map<String, Object>> listInformation(long gid, int page, int rows);
	
	/**
	 * 
	 * @Description: TODO 游戏活动列表
	 * @param gid 游戏id
	 * @param page 当前页
	 * @param rows 每页数量
	 * @return
	 */
	List<Map<String, Object>> listActivity(long gid, int page, int rows);
	
	/**
	 * 
	 * @Description: TODO 游戏攻略列表
	 * @return
	 */
	List<Map<String, Object>> raiders(long gid,int page, int rows);
	
	/**
	 * 
	 * @Description: TODO 获取活动详情
	 * @param id  活动详情id
	 * @param uid 
	 * @return 活动对象
	 */
	Map<String, Object> getActivityById(long id, String uid, String source);
	/**
	 * 
	 * @Description: TODO 获取游戏资讯详情 
	 * @param id 资讯ID
	 * @param uid 
	 * @return 游戏资讯对象
	 */
	Map<String, Object> getInformationById(long id, String uid, String source);
	/**
	 * 
	 * @Description: TODO 获取游戏攻略详情
	 * @param id 游戏攻略ID
	 * @param uid 
	 * @return 游戏攻略对象
	 */
	Map<String, Object> getRaidersById(long id, String uid, String source);
	
	/**
	 * 是否点赞
	 * @Description: TODO
	 * @param id 活动、攻略、资讯ID
	 * @param uid 用户ID
	 * @return
	 */
	Map<String, Object> isLike(Long id, String uid, String source);
	
	/**
	 * 热门搜索APP
	 * @Description: TODO
	 * @return
	 */
	List<Map<String,Object>> getPopularSearchesApp();
	/**
	 * 热门搜索h5小游戏
	 * @Description: TODO
	 * @return
	 */
	List<Map<String,Object>> getPopularSearchesH5();
	
	/**
	 * @Description:  app搜索
	 * @param uid 用户ID
	 * @param imei imei
	 * @param content 搜索内容
	 * @param date 搜索时间
	 * @return
	 */
	List<Map<String,Object>> searchApp(int uid, String imei,String content,Date date, String type);

	/**
	 * @Description: 小游戏搜索
	 * @param uid 用户ID
	 * @param imei IMEI
	 * @param content 搜索内容 
	 * @param date 搜索时间
	 * @return
	 */
	List<Map<String,Object>> searchH5(int uid, String imei,String content,Date date,String type);
	
	/**
	 * 手游-精品内容页
	 * @Description: TODO
	 * @return
	 */
	Map<String,Object> boutiqueAppIndex();
	
	/**
	 * 小游戏-精品内容页
	 * @Description: TODO
	 * @return
	 */
	Map<String,Object> boutiqueH5Index();
	
	/**
	 * 手游-礼包内容页
	 * @Description: TODO
	 * @param uid 用户ID
	 * @return
	 */
	Map<String,Object> giftsIndex(Long uid);
	
	/**
	 * 手游-精品分类
	 * @Description: TODO
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @return
	 */
	List<Map<String,Object>> boutiqueAppCategory(int page, int rows);
	
	/**
	 * 手游-精品分类下的具体分类里面的app
	 * @Description: TODO
	 * @param categoryId 具体分类ID
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @return
	 */
	List<Map<String,Object>> boutiqueAppCategoryDetail(int categoryId,int page, int rows);
	
	/**
	 * 小游戏-精品分类
	 * @Description: TODO
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @return
	 */
	List<Map<String,Object>> boutiqueH5Category(int page, int rows);
	
	/**
	 * 小游戏-精品分类下的具体分类里面的app
	 * @Description: TODO
	 * @param categoryId 具体分类ID
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @return
	 */
	List<Map<String,Object>> boutiqueH5CategoryDetail(int categoryId,int page, int rows);
	
	/**
	 * 手游-礼包分类
	 * @Description: TODO
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @return
	 */
	List<Map<String,Object>> giftsCategory(int page, int rows);
	
	/**
	 * 手游-礼包分类下的具体分类里面的礼包
	 * @Description: TODO
	 * @param uid 用户ID
	 * @param categoryId 具体分类ID
	 * @param page 当前页
	 * @param rows 每页显示行数
	 * @return
	 */
	List<Map<String,Object>> giftsCategoryDetail(long uid, int categoryId,int page, int rows);
	
	/**
	 * 微信礼包
	 * @Description: TODO
	 * @param uid
	 * @param pageIndex
	 * @return
	 *//*
	List<Map<String, Object>> wechatGiftList(String uid, String pageIndex);*/
	
	/**
	 * 获取游戏合集
	 * @author Maryn
	 * @param cid 合集id
	 * @param pageindex 页码
	 * @return 返回单个合集中的游戏信息（游戏id、名称、LOGO、大小、标题、评级、下载量）
	 */
	public List<Map<String, Object>> gameCollection(String cid, String pageindex);
	
	/**
	 * 获取游戏APP信息
	 * @author Maryn
	 * @return 所有游戏APP的包名
	 */
	public List<Map<String, Object>> gameInfo();
	
	/**
	 * 查询用户信息
	 * @author Maryn
	 * @param uid 用户id
	 * @return 用户信息（用户id、注册id、昵称、登陆类型、手机号、性别、头像）
	 */
	public List<Map<String,Object>> getUserInfo(String uid);
	
	/**
	 * 查询用户账号绑定信息
	 * @author Maryn
	 * @param uid 用户id
	 * @return 第三方绑定信息（0表示未绑定、1表示绑定）
	 */
	public List<Map<String,Object>> getBindingInfo(String uid);
	
	/**
	 * 查询广告位数据
	 * @author Maryn
	 * @return 广告位数据（广告图、广告名称、对应APP的id）
	 */
	public List<Map<String, Object>> advertInfo();
	
	/**
	 * 根据logintype和regid查询第三方信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param logintype 第三方登录状态（1表示QQ，2表示微信，3表示微博）  
	 * @return 用户id
	 */
	public List<Map<String, Object>> findExternal(String regid, String logintype);
	
	/**
	 * 根据regid和imei查询登录与手机对应关系信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @return 登录与手机对应关系信息
	 */
	public Integer findInfoByRegidNImei(String regid, String imei);

	/**
	 * 更新当前第三方登录为最新，修改"首次登录"为0（如果需要）
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void updateIsLogin(String regid, String imei);

	/**
	 * 新增当前第三方登录记录（IMEI）
	 * @author Maryn
	 * @param uid 用户id
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void insertExternalNImei(String uid, String regid, String imei);
	
	/**
	 * 新增触键注册用户登录记录（IMEI）
	 * @author Maryn
	 * @param uid 用户id
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void insertImei(String uid, String regid, String imei);
	
	/**
	 * 更新其他第三方登录非最新
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void updateIsNotLogin(String regid, String imei);

	/**
	 * 新增第三方登录信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param nickname 昵称
	 * @param logintype 第三方登录状态（1表示QQ，2表示微信，3表示微博）  
	 * @param headimage 头像
	 * @param sex 性别
	 * @param age 年龄
	 * @return 新增用户的主键id
	 */
	public String insertExternal(String regid, String nickname,
			String logintype, String headimage, String sex, String age);

	/**
	 * 根据用户id查询是否已注册
	 * @author Maryn
	 * @param uid 用户id
	 * @param loginType 用户手机号
	 * @return 注册的数据
	 */
	public List<Map<String, Object>> isRegistered(String uid, String loginType);

	/**
	 * 保存第三方绑定信息
	 * @author Maryn
	 * @param uid 用户id
	 * @param regid 注册id
	 * @param nickname 昵称
	 * @param logintype 第三方登录状态（1表示QQ，2表示微信，3表示微博） 
	 * @param phonenum 手机号
	 * @param sex 性别
	 * @param age 年龄
	 * @param headimage 头像
	 */
	public void bindExternal(String uid, String regid, String nickname,
			String logintype, String phonenum, String sex, String age,
			String headimage);
	/**
	 * 根据注册id和第三方登录状态查询是否已经绑定过第三方账号
	 * @author Maryn
	 * @param regid 注册ID
	 * @param loginType 第三方登录状态（1表示QQ，2表示微信，3表示微博） 
	 * @return 第三方信息
	 */
	public List<Map<String, Object>> isBinding(String regid, String loginType);

	/**
	 * 校验验证码是否正确
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param valicode 验证码
	 * @param s_date 验证码生效时间
	 * @return 验证码信息 type
	 */
	public List<Map<String, Object>> isUsableCode(String phonenum, String valicode,
			String s_date, String type);

	/**
	 * 保存用户注册信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 密码
	 * @return 注册成功后生成的注册id（没有前缀ICJ）
	 */
	public String register(String phonenum, String pwd);

	/**
	 * 查询用户id
	 * @author Maryn
	 * @param regid 注册id
	 * @param type 状态（0表示触键注册，1表示QQ，2表示微信，3表示微博，4表示游客）  
	 * @return
	 */
	public String getUid(String regid, String type);

	/**
	 * 根据手机号和密码查询用户信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 密码
	 * @return 用户信息
	 */
	public List<Map<String, Object>> login(String phonenum, String pwd);

	/**
	 * 根据注册id和imei查询IMEI是否有记录
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @return imei记录信息
	 */
	public List<Map<String, Object>> getMemberImei(String regid, String imei);

	/**
	 * 根据用户id和密码查询用户信息
	 * @author Maryn
	 * @param uid 用户id
	 * @param pwd 密码
	 * @return 用户信息
	 */
	public List<Map<String, Object>> findUserByUidNPwd(String uid, String pwd);

	/**
	 * 修改密码
	 * @author Maryn
	 * @param uid 用户id
	 * @param oldpwd 原密码
	 * @param newpwd 新密码
	 */
	public void modifyPwd(String uid, String oldpwd, String newpwd);

	/**
	 * 编辑用户信息（只修改昵称和性别）
	 * @author Maryn
	 * @param uid 用户id
	 * @param nickname 昵称
	 * @param sex 性别
	 */
	public void editUserInfo(String uid, String nickname, String sex);

	/**
	 * 根据手机号查询用户信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @return 用户信息
	 */
	public List<Map<String, Object>> findByPhone(String phonenum);

	/**
	 * 绑定手机
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登录类型（0表示触键注册，1表示QQ，2表示微信，3表示微博）  
	 * @param phonenum 手机号
	 * @param pwd 密码
	 */
	public void bindPhone(String uid, String logintype, String phonenum, String pwd);

	/**
	 * 获取当天发送的验证码
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param type 验证码类型 （1 注册 2 忘记密码 3 绑定手机）
	 * @param date 当前时间（格式：年-月-日）
	 * @return 验证码
	 */
	public List<Map<String, Object>> getValiCode(String phonenum, String type,
			String date);

	/**
	 * 将指定手机号和类型对应的其他验证码置为无效
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param type 验证码类型 （1 注册 2 忘记密码 3 绑定手机）
	 */
	public void invalidatedOther(String phonenum, String type);

	/**
	 * 保存验证码
	 * @author Maryn
	 * @param imei 手机IMEI
	 * @param phonenum 手机号
	 * @param type 验证码类型 （1 注册 2 忘记密码 3 绑定手机）
	 * @param date 验证码生成时间
	 * @param code 验证码
	 */
	public void insertValicode(String imei, String phonenum, String type, Date date,
			String code);

	/**
	 * 设置新密码
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 新密码
	 */
	public void setNewPwd(String phonenum, String pwd);

	/**
	 * 修改用户头像
	 * @author Maryn
	 * @param uid 用户id
	 * @param fileurl 头像地址
	 */
	public void updateHead(String uid, String fileurl);

	/**
	 * 获取游戏提示语
	 * @author Maryn
	 * @return 游戏提示语
	 */
	public List<Map<String, Object>> gameCue();

	/**
	 * 是否使用过“一键登录”
	 * @author Maryn
	 * @param imei 手机唯一标识
	 * @return 游客登录生成的用户id
	 */
	public String isLogined(String imei);

	/**
	 * 获取注册ID
	 * @author Maryn
	 * @return 游客注册id
	 */
	public List<Map<String, Object>> getRegid();

	/**
	 * 生成新的游客信息，返回用户id
	 * @author Maryn
	 * @param regid 注册id
	 * @param logintype 登录类型 4 游客
	 * @return
	 */
	public String quickLogin(String regid, String logintype);

	/**
	 * 新增游客登录记录
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @param uid 用户id
	 */
	public void quickLoginImei(String regid, String imei, String uid);

	/**
	 * 获取用户头像
	 * @author Maryn
	 * @param uid 用户id
	 * @param loginType 登陆状态
	 * @return 用户头像
	 */
	public List<Map<String, Object>> userHeadImg(String uid, String loginType);

	/**
	 * 解绑第三方账号
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登陆状态
	 */
	public void unBindExternal(String uid, String logintype);

	/**
	 * 生成默认昵称
	 * @author Maryn
	 * @param nickname 默认昵称
	 * @param regid 注册id
	 */
	public void defaultNickname(String nickname, String regid);

	/**
	 * 第三方注册账号绑定手机后，生成logintype为0的数据
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登录状态，为0
	 * @param phonenum 手机号
	 * @param pwd 密码
	 * @param nickname 默认昵称
	 */
	public void chujianUser(String uid, String logintype, String phonenum, String pwd, String nickname);

	/**
	 * 查询是否存在logintype为0的信息
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登录状态，为0
	 * @return 是否存在
	 */
	public int existChujianUser(String uid, String logintype);

	/**
	 * 查询当前第三方账号是否是第一次登录的账号
	 * @author Maryn
	 * @param uid 用户id 
	 * @param logintype 登录类型
	 * @return 是否是第一次登录	1/是，0/否
	 */
	public String isFirstInfo(String uid, String logintype);

	/**
	 * 修改当前用户的手机号
	 * @author Maryn
	 * @param uid 用户id
	 * @param phonenum 手机号
	 */
	public void updatePhonenum(String uid, String phonenum);

	/**
	 * 新增注册用户IMEI信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @param valicode 验证码
	 * @param date 日期
	 * @param uid 用户id
	 */
	public void RegImei(String regid, String imei, String valicode, Date date,
			String uid);

	/**
	 * 根据合集id获取相应的H5游戏合集
	 * @param cid 合集id
	 * @param pageindex 分页
	 * @return
	 */
	public List<Map<String, Object>> h5GameCollection(String cid, String pageindex, String pageSize);

	/**
	 * 查询H5游戏广告信息
	 * @return
	 */
	public List<Map<String, Object>> h5AdvertInfo();

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
	public void addUserActionOfGift(String uid, String gid, String type, String code);

	/**
	 * 查询礼包详情
	 * @param uid 用户id
	 * @param gid 礼包id
	 * @return
	 */
	public List<Map<String, Object>> giftDetail(String uid, String gid);

	/**
	 * 获取当前用户的礼包码
	 * @param uid 用户id
	 * @param gid 礼包id
	 * @return
	 */
	public String usersGiftCode(String uid, String gid);

	/**
	 * 礼包淘号
	 * @param gid 礼包id
	 * @return
	 */
	public List<Map<String, Object>> drawGiftCode(String gid);

	/**
	 * 保存用户发弹幕的行为
	 * @param uid 用户id
	 * @param gid 游戏id
	 * @param type 操作类型  0：下载    1：卸载  2：启动   3：查看 4：退出 5：发弹幕
	 * @param source 操作内容：0： app游戏  1：h5游戏
	 */
	public void saveH5Barrage(String uid, String gid, String type, String source);

	/**
	 * 获取当前用户的礼包
	 * @param uid 用户id
	 * @return
	 */
	public List<Map<String, Object>> usersGifts(String uid);

	/**
	 * 根据JAR包名获取游戏LOGO
	 * @param jarName JAR包名
	 * @return
	 */
	public String getLogo(String jarName);

	/**
	 * 礼包推荐
	 * @param uid 用户id
	 * @return
	 */
	public List<Map<String, Object>> recommendGift(String uid);

	/**
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> recentlyPlaying(String uid, String type);

	/**
	 * 游戏分类列表
	 * @return
	 */
	public List<Map<String, Object>> categoryList();

	/**
	 * H5游戏分类列表
	 * @return
	 */
	public List<Map<String, Object>> h5CategoryList();

	/**
	 * 根据分类id获取具体某一分类中的游戏
	 * @param cid 分类id
	 * @param pageindex 分页
	 * @return
	 */
	public List<Map<String, Object>> gamesInCategory(String cid, String pageindex);
	
	/**
	 * 根据分类id获取具体某一分类中的游戏(H5)
	 * @param cid 分类id
	 * @param pageindex 分页
	 * @return
	 */
	public List<Map<String, Object>> h5GamesInCategory(String cid, String pageindex);

	/**
	 * 根据游戏id查询所在分类中推荐的游戏
	 * @return
	 */
	public List<Map<String, Object>> gameRecommend();

	/**
	 * 根据游戏id查询所在分类中推荐的游戏(H5)
	 * @return
	 */
	public List<Map<String, Object>> h5GameRecommend();

}
