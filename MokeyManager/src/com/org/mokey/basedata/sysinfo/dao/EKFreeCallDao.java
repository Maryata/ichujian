package com.org.mokey.basedata.sysinfo.dao;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/11.
 * e 键
 * 免费通话 管理
 */
public interface EKFreeCallDao {
    /**
     * 免费通话 列表
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
     * 修改 本地飞语账号的状态
     *
     * @param args
     */
    void updateFreeCall(List<Object> args);

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
     * 添加飞语账号
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
     * 删除飞语账号
     *
     * @param fyId
     * @return
     */
    int deleteUser(String fyId);

    /**
     * 获取所有飞语账号
     *
     * @return
     */
    List<Map<String, Object>> getAllFyAccout();

    /**
     * 更新已存在的异常账号信息
     *
     * @param fyAccId      异常账号的id
     * @param fyAccountId  新增的飞语账号id
     * @param fyAccountPwd 新增的飞语账号密码
     * @param addDate      新增飞语账号的时间
     * @return
     */
    int changeFyAccount(String fyAccId, Object fyAccountId, Object fyAccountPwd, Object addDate);

    /**
     * 修改通话时间表中的飞语账号
     *
     * @param fyAccId     异常账号的id
     * @param fyAccountId 新增的飞语账号id
     */
    int changeFyAccountInCallingTime(String fyAccId, Object fyAccountId);
}
