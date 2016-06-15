package com.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {

	//普通登录
	public List Login(String phonenum,String password);
	//查询IMEI表是否有记录
	public List GetMemberList(String regid,String imei);
	//更新其他记录不是最新
	public void UpdateOtherImei(String regid,String imei);
	//设置当前记录为最新
	public void UpdateNewImei(String regid,String imei);
	//插入最新记录
	public void InsertImei(String regid,String imei,String uid);
	
	
	//查询ID是否登录过
	public List GetUserList(String uid,String logintype);
	//查询用户信息--注册过的
	public List GetUserList(String uid);
	//查询ID绑定信息
	public List GetUserBindList(String uid);
	
	//查询第三方账号是否登录过
	public List GetExternalListbystate(String regid,String logintype);
	
	//查询第三方账号是否登录或绑定过
	public List GetExternalList(String regid,String logintype);
	//第三方登录
	public String External(String regid,String nickname,String logintype,String sex,String age,String headimage);
	//更新第三方登录
	public void UpdateExternal(String regid,String nickname,String logintype,String sex,String age,String headimage);
	//查询IMEI表是否有记录
	public List FindExternalImei(String regid,String imei);
	//插入第三方登录信息 IMEI
	public void InsertExternalImei(String regid,String imei,String uid);
	//更新第三方登录信息当前最新imei
	public void UpdateExternalNewImei(String regid,String imei);
	//更新第三方登录信息其他不是最新
	public void UpdateExternalOtherImei(String regid,String imei);
	
	//修改密码
	public void Modifypd(String regid,String oldpassword,String newpassword,Date actiondate);
	
	//绑定手机号
	public void BindPhone(String regid,String logintype,String phonenum,Date actiondate);
	
	/**
	 * 第三方注册账号绑定手机后，生成logintype为0的数据
	 * @author Maryn
	 * @param uid 用户id
	 * @param phonenum 手机号
	 * @param logintype 登录状态，为0
	 * @param pwd 密码
	 * @param nickname 默认昵称
	 */
	public void chujianUser(String uid, String phonenum, String logintype, String pwd, String nickname);
	
	//绑定第三方
	public void BindExternal(String uid,String regid,String nickname,String logintype,String sex,String age,String headimage);    	
	//绑定第三方---增加已经注册的手机号
	public void BindExternal(String uid,String regid,String nickname,String logintype,String sex,String age,String headimage,String phonenum);
	
	/**
	 * 解绑第三方账号
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登陆状态
	 */
	public void unBindExternal(String uid, String logintype);
	
	//注册
	public String Reg(String phonenum,String password,Date actiondate);
	public void RegImei(String regid,String imei,String validatecode,Date actiondate,String uid);
	
	
	//意见反馈
	public void Feedback(String content,Date actiondate);
	public void Feedback(String content,Date actiondate,String imei,String type);
	
	//忘记密码 ---修改
	public void ForGetpd(String phonenum,String newpassword,Date actiondate);
	
	//产品用户表
	public void Product_User(String userid,String activecode,Date actiondate);
	
	//设备用户表
	public void Device_User(String userid,String imei,Date actiondate);
	
	//查询手机是否已经注册
	public List FindPhone(String phonenum);
	
	//查询原密码是否正确
	public List Findpd(String regid,String oldpassword);
	
	//编辑资料
	public void EditUserInfo(String regid,String nickname,String sex);
	
	//用户积分
	public String GetIntegral(String regid);
	
	//用户优惠
	public String GetPrivilege(String regid);
	
	//用户活动
	public List GetActivity(String regid);
	
	//用户关注
    public String GetFollow(String regid);
    
    //用户信息
    public List GetUserInfo(String regid,String logintype);
    
    //用户关注品牌(我的活动帮)
    public List UserFollowBrand(String regid,String pageindex);
    //用户关注品牌(首页)
    public List UserMainFollowBrand(String regid,String pageindex);
    
    //用户关注了多少品牌
    public int UserFollowBrandNum(String regid);
    
    //用户参加或收藏了多少活动数
    public int UserCollectingActivityNum(String type,String regid);
    
    //用户关注品牌对应的活动
    public List UserFollowBrandActivity(String regid,String pageindex);
    public List UserCollectingActivity(String regid,String pageindex,String type);
    
    //验证码发送次数
    public List GetValidateList(String phonenum,String type,String date);
    
    //查询验证码
    public List GetValidate(String phonenum,String type);
    //记录验证码
    public void InsertValidate(String imei,String phonenum,String type,Date date,String code);
    
    //更新状态
    public void UpdateValidate(String phonenum,String type);
    
    //更新头像
    public void UpdateHead(String regid,String headurl);
    
    /**
	 * 查询当前第三方账号是否是第一次登录的账号
	 * @author Maryn
	 * @param uid
	 * @param logintype
	 * @return 是否是第一次登录	1/是，0/否
	 */
	public String isFirstInfo(String uid, String logintype);
	
	/**
	 * 查询是否存在logintype为0的信息
	 * @author Maryn
	 * @param uid 用户id
	 * @param logintype 登录状态，为0
	 * @return 是否存在
	 */
	public int existChujianUser(String uid, String string);
	
	/**
	 * 生成默认昵称
	 * @author Maryn
	 * @param nickname 默认昵称
	 * @param regid 注册id
	 */
	public void defaultNickname(String nickname, String regid);
	
	/**
	 * 校验验证码是否正确
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param valicode 验证码
	 * @param s_date 验证码生效时间
	 * @return 验证码信息 type
	 */
	public List<Map<String, Object>> isUsableCode(String phonenum,
			String validatecode, String s_date, String type);
	
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
	 * 新增注册用户IMEI信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @param valicode 验证码
	 * @param date 日期
	 * @param uid 用户id
	 */
	public void updatePhonenum(String uid, String phonenum);
    
}
