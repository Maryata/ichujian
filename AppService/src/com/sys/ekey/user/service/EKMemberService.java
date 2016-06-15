package com.sys.ekey.user.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EKMemberService {

    /**
     * 校验验证码是否正确
     *
     * @param phonenum 手机号
     * @param valicode 验证码
     * @param s_date   验证码生效时间
     * @return 验证码信息 type
     * @author Maryn
     */
    public List<Map<String, Object>> isUsableCode(String phonenum, String valicode,
                                                  String s_date, String type);

    /**
     * 保存用户注册信息
     *
     * @param pwd      密码
     * @param phonenum 手机号
     * @param supcode  激活码
     * @return 注册成功后生成的注册id（没有前缀ICJ）
     * @author Maryn
     */
    public String register(String phonenum, String pwd, String supcode);

    /**
     * 查询用户id
     *
     * @param regid 注册id
     * @return
     * @author Maryn
     */
    public String getUid(String regid);

    /**
     * 生成默认昵称
     *
     * @param nickname 默认昵称
     * @param regid    注册id
     * @author Maryn
     */
    public void defaultNickname(String nickname, String regid);

    /**
     * 新增注册用户IMEI信息
     *
     * @param regid    注册id
     * @param imei     手机唯一标识
     * @param valicode 验证码
     * @param uid      用户id
     * @author Maryn
     */
    public void insertImei(String regid, String imei, String valicode, String uid);

    /**
     * 根据手机号和密码查询用户信息
     *
     * @param phonenum 手机号
     * @param pwd      密码
     * @return 用户信息
     * @author Maryn
     */
    public List<Map<String, Object>> login(String phonenum, String pwd);

    /**
     * 根据注册id和imei查询IMEI是否有记录
     *
     * @param regid 注册id
     * @param imei  手机唯一标识
     * @return imei记录信息
     * @author Maryn
     */
    public List<Map<String, Object>> getMemberImei(String regid, String imei);

    /**
     * 更新当前第三方登录为最新，修改"首次登录"为0（如果需要）
     *
     * @param regid 注册id
     * @param imei  手机唯一标识
     * @author Maryn
     */
    public void updateIsLogin(String regid, String imei);

    /**
     * 更新其他第三方登录非最新
     *
     * @param regid 注册id
     * @param imei  手机唯一标识
     * @author Maryn
     */
    public void updateIsNotLogin(String regid, String imei);

    /**
     * 根据用户id和密码查询用户信息
     *
     * @param uid  用户id
     * @param opwd 密码
     * @return 用户信息
     * @author Maryn
     */
    public List<Map<String, Object>> findUserByUidNPwd(String uid, String opwd);

    /**
     * 修改密码
     *
     * @param uid  用户id
     * @param opwd 原密码
     * @param npwd 新密码
     * @author Maryn
     */
    public void modifyPwd(String uid, String opwd, String npwd);

    /**
     * 根据手机号查询用户信息
     *
     * @param phonenum 手机号
     * @return 用户信息
     * @author Maryn
     */
    public List<Map<String, Object>> findByPhone(String phonenum);

    /**
     * 获取当天发送的验证码
     *
     * @param phonenum 手机号
     * @param type     验证码类型 （1 注册 2 忘记密码 3 绑定手机）
     * @param date     当前时间（格式：年-月-日）
     * @return 验证码
     * @author Maryn
     */
    public List<Map<String, Object>> getValiCode(String phonenum, String type,
                                                 String date);

    /**
     * 将指定手机号和类型对应的其他验证码置为无效
     *
     * @param phonenum 手机号
     * @param type     验证码类型 （1 注册 2 忘记密码 3 绑定手机）
     * @author Maryn
     */
    public void invalidatedOther(String phonenum, String type);

    /**
     * 保存验证码
     *
     * @param imei     手机IMEI
     * @param phonenum 手机号
     * @param type     验证码类型 （1 注册 2 忘记密码 3 绑定手机）
     * @param date     验证码生成时间
     * @param code     验证码
     * @author Maryn
     */
    public void insertValicode(String imei, String phonenum, String type,
                               Date date, String code);

    /**
     * 设置新密码
     *
     * @param phonenum 手机号
     * @param pwd      新密码
     * @author Maryn
     */
    public void setNewPwd(String phonenum, String pwd);

    /**
     * 活动用户头像、昵称
     *
     * @param uid       用户id
     * @param loginType 登录类型
     * @return
     */
    List<Map<String, Object>> userHeadImg(String uid, String loginType);

    /**
     * 查询用户信息
     *
     * @param uid 用户id
     * @return 用户信息（用户id、注册id、昵称、登陆类型、手机号、性别、头像）
     * @author Maryn
     */
    List<Map<String, Object>> getUserInfo(String uid);

    /**
     * 修改用户头像
     *
     * @param uid     用户id
     * @param fileurl 头像地址
     * @author Maryn
     */
    void updateHead(String uid, String fileurl);

    /**
     * 修改用户信息
     *
     * @param uid  用户id
     * @param type 修改的项：1.昵称，2.性别，3.地区
     * @param info 修改的内容
     */
    void modify(String uid, String type, String info);

    /**
     * 新增/修改用户地址
     *
     * @param uid      用户id
     * @param name     用户姓名
     * @param phone    用户手机号
     * @param addr     详细地址
     * @param postCode 邮编
     */
    void usersAddress(String uid, String name, String phone, String addr, String postCode);

    /**
     * 给新注册用户添加首页APP
     *
     * @param uid 用户id
     */
    void setIndexApp4NewUser(String uid);

    /**
     * 获取用户和客服交互的消息
     *
     * @param i          用户ID
     * @param pageNumber 页数
     * @param pageSize   每页大小
     * @return
     */
    List<Map<String, Object>> getMessage(int i, int pageNumber, int pageSize);

    /**
     * 发送消息给客服
     *
     * @param sUid    用户ID
     * @param message 发送的消息
     * @param imgArr  发送的图片数组
     */
    void contactServiceRoute(String sUid, String message, String[] imgArr) throws IOException;

    /**
     * 根据邀请码查询邀请人已邀请的次数
     *
     * @param inviteCode 邀请码
     * @return
     */
    Integer countOfInvite(String inviteCode);

    /**
     * 根据供应商代码获取供应商名称
     * @param sup 供应商代码
     * @return
     */
    String getNameBySupCode(String sup);
}
