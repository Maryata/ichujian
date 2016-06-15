package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKFreeCallDao;
import com.org.mokey.basedata.sysinfo.entiy.FreeCall;
import com.org.mokey.basedata.sysinfo.service.EKFreeCallService;
import com.org.mokey.basedata.sysinfo.util.HttpUtil;
import com.org.mokey.util.StrUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/11.
 * e 键
 * 免费通话 管理
 */
@Service
public class EKFreeCallServiceImpl implements EKFreeCallService {

    private static final Log LOGGER = LogFactory.getLog(EKFreeCallServiceImpl.class);

    @Autowired
    private EKFreeCallDao eKFreeCallDao;

    /**
     * 免费通话  列表
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> freeCallList(int page, String fyAccountId, String phone, String status, String sdate, String edate) {
        return eKFreeCallDao.freeCallList(page, fyAccountId, phone, status, sdate, edate);
    }

    /**
     * 查询  飞语账户信息  By   id
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectFyUserById(String id) {
        return eKFreeCallDao.selectFyUserById(id);
    }

    /**
     * 修改本地账号的状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateFreeCall(String id, String status) {
        List<Object> args = new ArrayList<Object>();
        if ("1".equals(status)) {
            status = "2";
            args.add(status);
        } else {
            status = "1";
            args.add(status);
        }
        args.add(id);
        eKFreeCallDao.updateFreeCall(args);


    }

    /**
     * 授权列表
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> authList(int page, String hjType, String whShow, String fyId) {
        return eKFreeCallDao.authList(page, hjType, whShow, fyId);
    }

    /**
     * 话单列表
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> callHisList(int page, String hjId, String zdReason) {
        return eKFreeCallDao.callHisList(page, hjId, zdReason);
    }

    /**
     * 查询飞语账号信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOne(String id) {
        return eKFreeCallDao.selectOne(id);
    }

    @Override
    public int addAccount(String fyAccountId, String fyAccountPwd, String addDate, String status, String id) {
        return eKFreeCallDao.addAccount(fyAccountId, fyAccountPwd, addDate, status, id);
    }

    /**
     * 删除时间
     *
     * @param fYiD
     * @return
     */
    @Override
    public int deleteTime(String fYiD) {
        return eKFreeCallDao.deleteTime(fYiD);
    }

    @Override
    public void addTime(String fyAccountId) {
        eKFreeCallDao.addTime(fyAccountId);
    }

    @Override
    public int selectAll() {
        return eKFreeCallDao.selectAll();
    }

    /**
     * 未注册列表查询
     *
     * @return
     */
    @Override
    public Map<String, Object> registerList() {
        return eKFreeCallDao.registerList();
    }

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
    @Override
    public int insertAccount(String regid, String fyAccountId, String fyAccountPwd, String addDate, String status, String phone) {
        return eKFreeCallDao.insertAccount(regid, fyAccountId, fyAccountPwd, addDate, status, phone);
    }

    /**
     * 删除飞语用户
     *
     * @param fyId
     * @return
     */
    @Override
    public int deleteUser(String fyId) {
        return eKFreeCallDao.deleteUser(fyId);
    }

    @Override
    public void checkExceptionalAccount() {
        // 获取所有飞语账号
        List<Map<String, Object>> allAccount = eKFreeCallDao.getAllFyAccout();
        // 遍历集合，依次查询每个飞语账号是否存在。
        if (StrUtils.isNotEmpty(allAccount)) {
            LOGGER.info(">>>>>>>>>>>> 飞语账号数量 : [" + allAccount.size() + "] <<<<<<<<<<<<<<<<<<<");
            Map<String, String> paramMap = new HashMap<>();
            for (Map<String, Object> accout : allAccount) {
                // 获取异常账号的id、密码、手机号
                String fyAccId = StrUtils.emptyOrString(accout.get("C_FYACCOUNTID"));
                String appAccId = StrUtils.emptyOrString(accout.get("C_APPACCOUNTID"));
                String phone = StrUtils.emptyOrString(accout.get("C_GLOBALMOBILEPHONE"));
//                String fyAccId = "FY88CE1ZJPC0D";
//                String appAccId = "ICJ5465";
//                String phone = "15712672918";
                try {
                    String result, resultCode;
                    long ti = System.currentTimeMillis();
                    FreeCall freeCall = new FreeCall();
                    paramMap.clear();
                    // 调用飞语“查询飞语账号”接口查询是否存在
                    result = queryFyAccout(paramMap, fyAccId, ti, freeCall);
                    if (!"".equals(result)) {
                        JSONObject queryJson = JSONObject.fromObject(result);
                        resultCode = queryJson.getString("resultCode");
                        if (!"0".equals(resultCode)) {
                            /** 如果不存在，注册飞语账号 */
                            paramMap.clear();
                            ti = System.currentTimeMillis();
                            result = addFyAccount(paramMap, appAccId, phone, ti, freeCall);
                            JSONObject addJson = JSONObject.fromObject(result);
                            resultCode = addJson.getString("resultCode");
                            if ("0".equals(resultCode)) {
                                Map<String, Object> addResult = (Map) (addJson.get("result"));
                                Object fyAccountId = addResult.get("fyAccountId");
                                Object fyAccountPwd = addResult.get("fyAccountPwd");
                                Object addDate = addResult.get("addDate");
                                // 如果成功，修改已有的飞语账号信息为新增的飞语账号
                                int i = eKFreeCallDao.changeFyAccount(fyAccId, fyAccountId, fyAccountPwd, addDate);
                                if (i > 0) {
                                    eKFreeCallDao.changeFyAccountInCallingTime(fyAccId, fyAccountId);
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 查询飞语账号是否存在
    private String queryFyAccout(Map<String, String> paramMap, String fyAccId, long ti, FreeCall freeCall) {
        paramMap.put("appId", freeCall.GetAppId());//appId
        paramMap.put("infoType", "1");//类型
        paramMap.put("info", fyAccId);//账号id
        paramMap.put("ti", String.valueOf(ti));//时间
        paramMap.put("ver", freeCall.GetVer());//版本
        paramMap.put("au", freeCall.getAuValue(ti));//AU
        return HttpUtil.sendPost(freeCall.freecall_select, paramMap, "utf-8");
    }

    // 添加飞语云账号
    private String addFyAccount(Map<String, String> paramMap, String appAccId, String phone, long ti, FreeCall freeCall) {
        // 飞语云通讯给每个APP的分配的唯一APPID
        paramMap.put("appId", freeCall.GetAppId());
        // 在应用服务器端用户的唯一名称，同一个应用必须要保证唯一 （使用e键用户的regId）
        paramMap.put("appAccountId", appAccId);
        // 待绑定手机号码，拨打出去可以显示用户的本机号码，要带国别码；例如：86+13888888888；如果账户要调用双向回拨接口，必须绑定手机号;
        paramMap.put("globalMobilePhone", "86+" + phone);
        // 号码的国际区号（中国就是86）
        paramMap.put("district", "86");
        // 当前时间毫秒值
        paramMap.put("ti", String.valueOf(ti));
        // 当前接口的版本号：2.1.0
        paramMap.put("ver", freeCall.GetVer());
        // MD5（appId + appToken + ti）
        paramMap.put("au", freeCall.getAuValue(ti));
        return HttpUtil.sendPost(freeCall.freecall_addAccount, paramMap, "utf-8");
    }

}
