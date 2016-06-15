package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/11.
 * e 键
 * 免费通话 管理
 */
public interface EKFreeCallService {

    /**
     * 查询免费通话列表
     *
     * @param page
     * @return
     */
    Map<String, Object> freeCallList(int page, String fyAccountId, String phone, String status, String sdate, String edate);

    /**
     * 查询  飞语账户信息  By   id
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> selectFyUserById(String id);

    /**
     * 修改 本地账户的  状态
     *
     * @param id
     * @param status
     */
    void updateFreeCall(String id, String status);

    /**
     * 授权列表
     *
     * @param page
     * @return
     */
    Map<String, Object> authList(int page, String hjType, String whShow, String fyId);

    /**
     * 话单列表
     *
     * @param page
     * @return
     */
    Map<String, Object> callHisList(int page, String hjId, String zdReason);

    /**
     * 查询飞语账号信息
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> selectOne(String id);

    /**
     * 添加
     *
     * @param fyAccountId
     * @param fyAccountPwd
     * @param addDate
     * @param status
     * @param id
     * @return
     */
    int addAccount(String fyAccountId, String fyAccountPwd, String addDate, String status, String id);

    /**
     * 删除时间
     *
     * @param fYiD
     * @return
     */
    int deleteTime(String fYiD);

    void addTime(String fyAccountId);

    int selectAll();

    /**
     * 未注册列表查询
     *
     * @return
     */
    Map<String, Object> registerList();

    /**
     * 添加 飞语账户
     *
     * @param regid
     * @param fyAccountId
     * @param fyAccountPwd
     * @param addDate
     * @param status
     * @param phone
     * @return
     */
    int insertAccount(String regid, String fyAccountId, String fyAccountPwd, String addDate, String status, String phone);

    /**
     *
     * @return
     */
    int deleteUser(String fyId);

    /**
     * 查询异常的飞语账号，重新添加
     */
    void checkExceptionalAccount();
}
