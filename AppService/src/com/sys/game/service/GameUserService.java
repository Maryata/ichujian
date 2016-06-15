package com.sys.game.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GameUserService {

	/**
	 * 查询用户账号绑定信息
	 * @author Maryn
	 * @param uid 用户id
	 * @return 第三方绑定信息（0表示未绑定、1表示绑定）
	 */
	public List<Map<String, Object>> getBindingInfo(String uid);

	/**
	 * 查询用户信息
	 * @author Maryn
	 * @param uid 用户id
	 * @return 用户信息（用户id、注册id、昵称、登陆类型、手机号、性别、头像）
	 */
	public List<Map<String, Object>> getUserInfo(String uid);

	/**
	 * 修改用户头像
	 * @author Maryn
	 * @param uid 用户id
	 * @param fileurl 头像地址
	 */
	public void updateHead(String uid, String fileurl);

	/**
	 * 设置新密码
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 新密码
	 */
	public void setNewPwd(String phonenum, String pwd);

	/**
	 * 根据手机号查询用户信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @return 用户信息
	 */
	public List<Map<String, Object>> findByPhone(String phonenum);

	/**
	 * 将指定手机号和类型对应的其他验证码置为无效
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param type 验证码类型 （1 注册 2 忘记密码 3 绑定手机）
	 */
	public void invalidatedOther(String phonenum, String type);

	/**
	 * 校验验证码是否正确
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param valicode 验证码
	 * @param s_date 验证码生效时间
	 * @return 验证码信息 type
	 */
	public List<Map<String, Object>> isUsableCode(String phonenum,
			String valicode, String s_date, String type);

	/**
	 * 保存验证码
	 * @author Maryn
	 * @param imei 手机IMEI
	 * @param phonenum 手机号
	 * @param type 验证码类型 （1 注册 2 忘记密码 3 绑定手机）
	 * @param date 验证码生成时间
	 * @param code 验证码
	 */
	public void insertValicode(String imei, String phonenum, String type,
			Date date, String code);

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
	 * 修改当前用户的手机号
	 * @author Maryn
	 * @param uid 用户id
	 * @param phonenum 手机号
	 */
	public void updatePhonenum(String uid, String phonenum);

	/**
	 * 第三方注册账号绑定手机后，生成logintype为0的数据
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登录状态，为0
	 * @param phonenum 手机号
	 * @param pwd 密码
	 * @param nickname 默认昵称
	 */
	public void chujianUser(String uid, String logintype, String phonenum,
			String pwd, String nickname);

	/**
	 * 绑定手机
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登录类型（0表示触键注册，1表示QQ，2表示微信，3表示微博）  
	 * @param phonenum 手机号
	 * @param pwd 密码
	 */
	public void bindPhone(String uid, String logintype, String phonenum,
			String pwd);

	/**
	 * 编辑用户信息（只修改昵称和性别）
	 * @author Maryn
	 * @param uid 用户id
	 * @param nickname 昵称
	 * @param sex 性别
	 */
	public void editUserInfo(String uid, String nickname, String sex);

	/**
	 * 修改密码
	 * @author Maryn
	 * @param uid 用户id
	 * @param oldpwd 原密码
	 * @param newpwd 新密码
	 */
	public void modifyPwd(String uid, String oldpwd, String newpwd);

	/**
	 * 根据用户id和密码查询用户信息
	 * @author Maryn
	 * @param uid 用户id
	 * @param pwd 密码
	 * @return 用户信息
	 */
	public List<Map<String, Object>> findUserByUidNPwd(String uid, String pwd);

	/**
	 * 更新其他第三方登录非最新
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void updateIsNotLogin(String regid, String imei);

	/**
	 * 新增触键注册用户登录记录（IMEI）
	 * @author Maryn
	 * @param uid 用户id
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void insertImei(String string, String string2, String imei);

	/**
	 * 更新当前第三方登录为最新，修改"首次登录"为0（如果需要）
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void updateIsLogin(String regid, String imei);

	/**
	 * 根据注册id和imei查询IMEI是否有记录
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @return imei记录信息
	 */
	public List<Map<String, Object>> getMemberImei(String regid, String imei);

	/**
	 * 根据手机号和密码查询用户信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 密码
	 * @return 用户信息
	 */
	public List<Map<String, Object>> login(String phonenum, String pwd);

	/**
	 * 新增注册用户IMEI信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @param valicode 验证码
	 * @param date 日期
	 * @param uid 用户id
	 */
	public void regImei(String regid, String imei, String valicode, Date date,
			String uid);

	/**
	 * 生成默认昵称
	 * @author Maryn
	 * @param nickname 默认昵称
	 * @param regid 注册id
	 */
	public void defaultNickname(String nickname, String regid);

	/**
	 * 查询用户id
	 * @author Maryn
	 * @param regid 注册id
	 * @param type 状态（0表示触键注册，1表示QQ，2表示微信，3表示微博，4表示游客）  
	 * @return
	 */
	public String getUid(String regid, String string);

	/**
	 * 保存用户注册信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 密码
	 * @return 注册成功后生成的注册id（没有前缀ICJ）
	 */
	public String register(String phonenum, String pwd);

	/**
	 * 解绑第三方账号
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登陆状态
	 */
	public void unBindExternal(String uid, String logintype);

	/**
	 * 查询当前第三方账号是否是第一次登录的账号
	 * @author Maryn
	 * @param uid 用户id 
	 * @param logintype 登录类型
	 * @return 是否是第一次登录	1/是，0/否
	 */
	public String isFirstInfo(String uid, String logintype);

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
	 * 根据用户id查询是否已注册
	 * @author Maryn
	 * @param uid 用户id
	 * @param loginType 用户手机号
	 * @return 注册的数据
	 */
	public List<Map<String, Object>> isRegistered(String uid, String loginType);

	/**
	 * 根据注册id和第三方登录状态查询是否已经绑定过第三方账号
	 * @author Maryn
	 * @param regid 注册ID
	 * @param loginType 第三方登录状态（1表示QQ，2表示微信，3表示微博） 
	 * @return 第三方信息
	 */
	public List<Map<String, Object>> isBinding(String regid, String loginType);

	/**
	 * 新增当前第三方登录记录（IMEI）
	 * @author Maryn
	 * @param uid 用户id
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void insertExternalNImei(String uid, String regid, String imei);

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
	 * 根据regid和imei查询登录与手机对应关系信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @return 登录与手机对应关系信息
	 */
	public Integer findInfoByRegidNImei(String regid, String imei);

	/**
	 * 根据logintype和regid查询第三方信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param logintype 第三方登录状态（1表示QQ，2表示微信，3表示微博）  
	 * @return 用户id
	 */
	public List<Map<String, Object>> findExternal(String regid, String logintype);

	/**
	 * 获取游戏提示语
	 * @author Maryn
	 * @return 游戏提示语
	 */
	public List<Map<String, Object>> gameCue();

	/**
	 * 获取用户头像
	 * @author Maryn
	 * @param uid 用户id
	 * @param loginType 登陆状态
	 * @return 用户头像
	 */
	public List<Map<String, Object>> userHeadImg(String uid, String loginType);

	/**
	 * 触键用户-保存用户注册信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 密码
	 * @return 注册成功后生成的注册id（没有前缀ICJ）
	 */
	public String registerCj(String phonenum, String pwd);

	/**
	 * 触键用户-查询用户id
	 * @author Maryn
	 * @param regid 注册id
	 * @param type 状态（0表示触键注册，1表示QQ，2表示微信，3表示微博，4表示游客）  
	 * @return
	 */
	public String getUidCj(String regid, String type);

	/**
	 * 触键用户-生成默认昵称
	 * @author Maryn
	 * @param nickname 默认昵称
	 * @param regid 注册id
	 */
	public void defaultNicknameCj(String nickname, String regid);

	/**
	 * 触键用户-新增注册用户IMEI信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @param valicode 验证码
	 * @param uid 用户id
	 */
	public void regImeiCj(String regid, String imei, String valicode, String uid);

	/**
	 * 我的趣币
	 * @param uid 用户ID
	 * @param pageNumber 页数
	 * @param pageSize 每页数量
	 * @return 结果集 包含总数，当前页行记录
	 */
	Map<String,Object> score(int uid, int pageNumber, int pageSize);

	/**
	 * 获取用户总积分
	 * @param uid 用户ID
	 * @return
	 */
	Object getTotalScore( int uid );

	/**
	 * 礼包总数
	 * @param uid 用户ID
	 * @return
	 */
	public Integer getTotalGift(String uid);

	/**
	 * 消息总数
	 * @param uid 用户ID
	 * @return
	 */
	public Integer getTotalMess(String uid);

	/**
	 * 获取用户和客服交互的消息
	 * @param i 用户ID
	 * @param pageNumber 页数
	 * @param pageSize 每页大小
	 * @return
	 */
	List<Map<String,Object>> getMessage(int i, int pageNumber, int pageSize);

	/**
	 * 发送消息给客服
	 * @param sUid 用户ID
	 * @param message 发送的消息
	 * @param imgArr 发送的图片数组
	 */
	void contactServiceRoute (String sUid, String message, String[] imgArr) throws IOException;

	/**
	 * 查询state=1的用户信息
	 * @param uid 用户ID
	 * @return
	 */
	public List<Map<String, Object>> getRegisterInfo(String uid);

	/**
	 * 查询用户是否做过一次性任务（上传头像、修改昵称、修改性别、修改手机号、绑定第三方）
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>> haveCompleted(String uid);

	/**
	 * 查询是否做过一次性任务的完成情况
	 * @param uid 用户ID
	 * @return
	 */
	public Map<String, Object> showGetScore(String uid);

}
