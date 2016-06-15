package com.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberService {

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
	 * @param pwd 密码
	 * @param phonenum 手机号
	 * @param supcode 激活码
	 * @return 注册成功后生成的注册id（没有前缀ICJ）
	 */
	public String register(String phonenum, String pwd, String supcode);

	/**
	 * 查询用户id
	 * @author Maryn
	 * @param regid 注册id
	 * @return
	 */
	public String getUid(String regid);

	/**
	 * 生成默认昵称
	 * @author Maryn
	 * @param nickname 默认昵称
	 * @param regid 注册id
	 */
	public void defaultNickname(String nickname, String regid);

	/**
	 * 新增注册用户IMEI信息
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 * @param valicode 验证码
	 * @param uid 用户id
	 */
	public void insertImei(String regid, String imei, String valicode, String uid);

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
	 * 更新当前第三方登录为最新，修改"首次登录"为0（如果需要）
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void updateIsLogin(String regid, String imei);

	/**
	 * 更新其他第三方登录非最新
	 * @author Maryn
	 * @param regid 注册id
	 * @param imei 手机唯一标识
	 */
	public void updateIsNotLogin(String regid, String imei);

	/**
	 * 根据用户id和密码查询用户信息
	 * @author Maryn
	 * @param uid 用户id
	 * @param opwd 密码
	 * @return 用户信息
	 */
	public List<Map<String, Object>> findUserByUidNPwd(String uid, String opwd);

	/**
	 * 修改密码
	 * @author Maryn
	 * @param uid 用户id
	 * @param opwd 原密码
	 * @param npwd 新密码
	 */
	public void modifyPwd(String uid, String opwd, String npwd);

	/**
	 * 根据手机号查询用户信息
	 * @author Maryn
	 * @param phonenum 手机号
	 * @return 用户信息
	 */
	public List<Map<String, Object>> findByPhone(String phonenum);

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
	public void insertValicode(String imei, String phonenum, String type,
			Date date, String code);

	/**
	 * 设置新密码
	 * @author Maryn
	 * @param phonenum 手机号
	 * @param pwd 新密码
	 */
	public void setNewPwd(String phonenum, String pwd);

}
