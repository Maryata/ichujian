package com.sys.ekey.freecall.action;

import com.sys.commons.AbstractAction;
import com.sys.ekey.freecall.common.FeiyuCloudErrorConst;
import com.sys.ekey.freecall.common.FeiyuCloudParamConst;
import com.sys.ekey.freecall.service.FeiyuCloudService;
import com.sys.game.util.IGameConst;
import com.sys.util.HttpUtil;
import com.sys.util.MD5;
import com.sys.util.StrUtils;
import com.unionpay.acp.sdk.LogUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by Maryn on 2016/4/12.
 */
@Controller("feiyuCloudAction")
public class FeiyuCloudAction extends AbstractAction {

    @Autowired
    private FeiyuCloudService feiyuCloudService;

    // 呼叫授权
    public String callAuth() {
        JSONObject jsonObject = new JSONObject();

        //请求类型：固定值：callauth
        String action = getRequest().getParameter("action");
        //呼叫类型：0）互联网语音；1）网络直拨；2）双向回拨
        String callType = getRequest().getParameter("callType");
        //外呼显号标示：1）显号；2）不显号
        String showNumberType = getRequest().getParameter("showNumberType");
        // 主叫号码type取值为0或1时，主叫号码为飞语帐号；type取值为2时，主叫号码为电话号码
        String caller = getRequest().getParameter("caller");
        // 被叫号码type取值为0时，被叫号码为飞语帐号；type取值为1、2时，被叫号码为电话号码
        String callee = getRequest().getParameter("callee");
        //第三方私有数据，APP客户端产生的，提交呼叫请求的时候传到我方服务器的
        String appExtraData = getRequest().getParameter("appExtraData");
        //飞语产生的呼叫的唯一标识
        String fyCallId = getRequest().getParameter("fyCallId");
        //提交呼叫请求的飞语账户号码
        String fyAccountId = getRequest().getParameter("fyAccountId");
        //在用户服务器端用户的唯一名称
        String appAccountId = getRequest().getParameter("appAccountId");
        //应用ID
        String appId = getRequest().getParameter("appId");
        //渠道ID，用来统计用
        String channelId = getRequest().getParameter("channelId");
        //是否需要录音：1）需要；2）不需要
        String ifRecord = getRequest().getParameter("ifRecord");
        //自1970年1月1日0时起的毫秒数（UTC/GMT+08:00）
        String ti = getRequest().getParameter("ti");
        // MD5（appId + appToken + ti）
        String au = getRequest().getParameter("au");

        LogUtil.writeLog("===============================\n\n\n\n\n");
        LogUtil.writeLog("showNumberType =======" + showNumberType);
        LogUtil.writeLog("\n\n\n\n\n==========================================");

        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> keys = getRequest().getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, getRequest().getParameter(key));
        }

        String _au = MD5.md5Code(appId + FeiyuCloudParamConst.APPTOKEN + ti);

        final String errMsg = "授权数据回写出错";

        if (StringUtils.isEmpty(fyAccountId)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_NULL_ACCID, "", "", "0", appExtraData, showNumberType, ifRecord);
        } else if (StringUtils.isEmpty(appId)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_NULL_APPID, "", "", "0", appExtraData, showNumberType, ifRecord);
        } else if (StringUtils.isEmpty(au)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_NULL_AU, "", "", "0", appExtraData, showNumberType, ifRecord);
        } else if (StringUtils.isEmpty(appAccountId)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_NULL_APP_ACCID, "", "", "0", appExtraData, showNumberType, ifRecord);
        } else if (!FeiyuCloudParamConst.APPID.equalsIgnoreCase(appId)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_UNEXISTSAPP, "", "", "0", appExtraData, showNumberType, ifRecord);
        } else if (!_au.equalsIgnoreCase(au)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_ENCRYPT, "", "", "0", appExtraData, showNumberType, ifRecord);
        } else if (!feiyuCloudService.isEnableAccount(fyAccountId)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.ACCOUNT_BAN, "", "", "0", appExtraData, showNumberType, ifRecord);
        } else {
            // 其它错误条件暂不验证，例如：用户不存在
            int maxCallMinute = feiyuCloudService.getMaxCallMinuteByFyAccountId(fyAccountId, map);

            // 授权被锁定, 可能由于话单推送失败或者处理未完成造成
            if (maxCallMinute == -1) {
                fillJson(jsonObject, FeiyuCloudErrorConst.Server.CALL_OVER_MAXONLINE, "", "", String.valueOf(maxCallMinute), appExtraData, showNumberType, ifRecord);
            } else {
                fillJson(jsonObject, "0", "审核成功", "", String.valueOf(maxCallMinute), appExtraData, showNumberType, ifRecord);
            }
        }

        writeJson(jsonObject, errMsg);

        return NONE;
    }

    // 话单推送
    public String pushCallInfo() {
        JSONObject jsonObject = new JSONObject();
        //String	是	请求类型固定值：callhangup
        String action = getRequest().getParameter("action");
        // String	是	应用ID
        String appId = getRequest().getParameter("appId");
        //String	否	第三方呼叫的唯一标识
        String appCallId = getRequest().getParameter("appCallId");
        //String	是	飞语产生的呼叫ID
        String fyCallId = getRequest().getParameter("fyCallId");
        //String	否	APP附加的数据，通话鉴权的时候产生的
        String appServerExtraData = getRequest().getParameter("appServerExtraData");
        //long	否	回拨第一路开始通话时间，如果是直拨模式，这一路时间是0，回拨才有这一路时间格式为离1970年的毫秒数
        String callbackFirstStartTime = getRequest().getParameter("callbackFirstStartTime");
        // 	long	否	回拨第一路结束通话时间
        String callbackFirstEndTime = getRequest().getParameter("callbackFirstEndTime");
        //	long	是	通话的开始时间、回拨第二路的开始时间或者直拨的开始时间
        String callStartTime = getRequest().getParameter("callStartTime");
        // 	long	是	通话的结束时间、回拨第二路结束通话时间或者直拨的结束时间
        String callEndTime = getRequest().getParameter("callEndTime");
        //int	是	中断原因
        String stopReason = getRequest().getParameter("stopReason");
        //	int	是	真实的显号类型：1为显示号码，2为不显示号码
        String trueShowNumberType = getRequest().getParameter("trueShowNumberType");
        //int	是	真实的是否录音：1为录音，0或者2为不录音
        String trueIfRecord = getRequest().getParameter("trueIfRecord");
        // 	long	是	自1970年1月1日0时起的毫秒数（UTC/GMT+08:00）
        String ti = getRequest().getParameter("ti");
        //	String	是	MD5（appId+ appToken+ti）
        String au = getRequest().getParameter("au");

        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> keys = getRequest().getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, getRequest().getParameter(key));
        }
        String _au = MD5.md5Code(appId + FeiyuCloudParamConst.APPTOKEN + ti);

        final String errMsg = "消息记录结果回写出错";

        if (StringUtils.isEmpty(appId)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_NULL_APPID, "");
        } else if (StringUtils.isEmpty(au)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_NULL_AU, "");
        } else if (!FeiyuCloudParamConst.APPID.equalsIgnoreCase(appId)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_UNEXISTSAPP, "");
        } else if (!_au.equalsIgnoreCase(au)) {
            fillJson(jsonObject, FeiyuCloudErrorConst.Server.AUTH_FAIL_ENCRYPT, "");
        } else {
            feiyuCloudService.insertCallHis(map);
            fillJson(jsonObject, "0", "接收成功");
        }

        writeJson(jsonObject, errMsg);

        return NONE;
    }

    public String callHistory() {
        log.info( "into callHistory ..." );
        String fyAccountId = getParameter( "fyAccountId" ), sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" );
        int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
        List<Map<String, Object>> list = null; // 存放通话列表信息
        Map<String, Object> retMap = new HashMap<String, Object>();

        if ( StringUtils.isEmpty(fyAccountId) ) {
            retMap.put( IGameConst.STATUS, IGameConst.NO );
            retMap.put( IGameConst.INFO, IGameConst._1001 );
        } else {
            try {
                if ( !StringUtils.isEmpty( sPageNumber ) ) {
                    pageNumber = Integer.parseInt( sPageNumber );
                }
                if ( !StringUtils.isEmpty( sPageSize ) ) {
                    pageSize = Integer.parseInt( sPageSize );
                }

                list = feiyuCloudService.callHistory( fyAccountId, pageNumber, pageSize );

                if ( null == list ) {
                    list = new ArrayList<Map<String, Object>>();
                }

                retMap.put( "callHistory", list );
                retMap.put( IGameConst.STATUS, IGameConst.YES );
                retMap.put( IGameConst.INFO, IGameConst._1002 );
            } catch ( Exception e ) {
                log.error( "处理调用通话历史出错！", e );
                retMap.put( IGameConst.STATUS, IGameConst.NO );
                retMap.put( IGameConst.INFO, IGameConst._1003 );
            }
        }

        try {
            writeToResponse( JSONObject.fromObject( retMap ).toString() );
        } catch ( IOException e ) {
            log.error( "回写通话历史数据错误！",e );
        } catch ( Exception e ) {
            log.error( "回写通话历史数据错误！",e );
        }

        return NONE;
    }

    // 语音验证码
    public String voiceValidateCode() {
        return "";
    }

    // 录音通知回调
    public String voiceRecCallback() {
        return "";
    }

    private void fillJson(JSONObject json, String resultCode, String resultMsg, String appCallId, String maxCallMinute,
                          String appServerExtraData, String showNumberType, String ifRecord) {

        fillJson(json, resultCode, resultMsg);
        JSONObject jsonObject = new JSONObject();
        // APP端自己产生的呼叫ID
        jsonObject.put("appCallId", appCallId);
        // 最大通话分钟数
        jsonObject.put("maxCallMinute", maxCallMinute);
        // APP附加的数据，通话鉴权的时候产生的，话单推送的时候会同时推送给APP服务器端
        jsonObject.put("appServerExtraData", appServerExtraData);
        // 显号类型：1为显号，2为不显号
        jsonObject.put("showNumberType", showNumberType);
        // 是否录音：1为录音，2为不录音
        jsonObject.put("ifRecord", ifRecord);

        json.put("result", jsonObject);
    }

    private void fillJson(JSONObject json, String resultCode, String resultMsg) {
        //认证授权结果：0）：成功，其他代表错误码（如果自定义的错误码请使用9开头的6位数字，此错误码会透传给客户端）
        json.put("resultCode", resultCode);
        // 认证结果描述,会透传给客户端认证授权成功的话，result是下面的json数组
        json.put("resultMsg", resultMsg);
    }

    private void writeJson(JSONObject json, String errMsg) {
        try {
            writeToResponse(json.toString());
        } catch (IOException e) {
            log.error(errMsg, e);
        }
    }

    // 新增飞语云账号
    public List<Map<String, Object>> addCloudAccount(String phonenum, String regid, String supcode) throws Exception {
        /** 初始化飞语账号信息 */
        Object fyAccId_add = "";
        Object fyAccPwd_add = "";
        List<Map<String, Object>> fyAccInfo = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("C_FYACCOUNTID", fyAccId_add);
        map.put("C_FYACCOUNTPWD", fyAccPwd_add);
        fyAccInfo.add(map);

        /** 注册飞语账号 */
        Map<String, String> paramMap = new HashMap<>();
        long ti = System.currentTimeMillis();
        // 飞语云通讯给每个APP的分配的唯一APPID
        paramMap.put("appId", FeiyuCloudParamConst.APPID);
        // 在应用服务器端用户的唯一名称，同一个应用必须要保证唯一 （使用e键用户的regId）
        paramMap.put("appAccountId", regid);
        // 待绑定手机号码，拨打出去可以显示用户的本机号码，要带国别码；例如：86+13888888888；如果账户要调用双向回拨接口，必须绑定手机号;
        paramMap.put("globalMobilePhone", "86+" + phonenum);
        // 号码的国际区号（中国就是86）
        paramMap.put("district", "86");
        // 当前时间毫秒值
        paramMap.put("ti", String.valueOf(ti));
        // 当前接口的版本号：2.1.0
        paramMap.put("ver", FeiyuCloudParamConst.VER);
        // MD5（appId + appToken + ti）
        paramMap.put("au", MD5.md5Code(FeiyuCloudParamConst.APPID + FeiyuCloudParamConst.APPTOKEN + ti));

        String addResult = "";
        String getResult = "";

        try {
            addResult = HttpUtil.post(paramMap, FeiyuCloudParamConst.URL_ADDACCOUNT, "utf-8");
            if (!"".equals(addResult)) {
                JSONObject addJsonObject = JSONObject.fromObject(addResult);
                if ("0".equals(StrUtils.emptyOrString(addJsonObject.get("resultCode")))) {
                    fyAccId_add = ((Map)addJsonObject.get("result")).get("fyAccountId");
                    fyAccPwd_add = ((Map)addJsonObject.get("result")).get("fyAccountPwd");
                    map.put("C_FYACCOUNTID", fyAccId_add);
                    map.put("C_FYACCOUNTPWD", fyAccPwd_add);
                    /** 2016-04-29 e键v2.1 创建完飞语用户之后，再调用“查询飞语用户”接口，查询是否创建成功。如果未成功，再次创建 begin */
                    if (!"".equals(fyAccId_add)) {
                        Object status_add = ((Map)addJsonObject.get("result")).get("status");
                        paramMap = new HashMap<>();
                        ti = System.currentTimeMillis();
                        paramMap.put("appId", FeiyuCloudParamConst.APPID);
                        paramMap.put("infoType", "1");// 查询信息类型：1）飞语云账户号码；2）APP账户号码；3）手机号码
                        paramMap.put("info", fyAccId_add.toString());
                        paramMap.put("ti", String.valueOf(ti));
                        paramMap.put("ver", FeiyuCloudParamConst.VER);
                        paramMap.put("au", MD5.md5Code(FeiyuCloudParamConst.APPID + FeiyuCloudParamConst.APPTOKEN + ti));
                        getResult = HttpUtil.post(paramMap, FeiyuCloudParamConst.URL_GETACCOUNT, "utf-8");
                        if (!"".equals(getResult)) {
                            JSONObject getJsonObject = JSONObject.fromObject(getResult);
                            if ("0".equals(StrUtils.emptyOrString(getJsonObject.get("resultCode")))) {
                                Object fyAccId_get = ((Map)addJsonObject.get("result")).get("fyAccountId");
                                Object fyAccPwd_get = ((Map)addJsonObject.get("result")).get("fyAccountPwd");
                                Object status_get = ((Map)addJsonObject.get("result")).get("status");

                                // 如果返回的飞语账号存在，则添加本地飞语账号信息
                                if (fyAccId_add.equals(fyAccId_get) && fyAccPwd_add.equals(fyAccPwd_get) && status_add.equals(status_get)) {
                                    // 将生成的飞语云用户存入数据库
                                    int i = feiyuCloudService.addAccount(regid, phonenum, addJsonObject);
                                    if (0 == i) {
                                        // 给用户增加通话时间
                                        feiyuCloudService.grantUserCallTime(fyAccId_add, supcode);
                                    }
                                } else {
                                    // 如果不存在，递归调用创建飞语账号方法，直到创建成功
                                    addCloudAccount(phonenum, regid, supcode);
                                }
                            }
                        }

                    }
                    /** 2016-04-29 e键v2.1 创建完飞语用户之后，再调用“查询飞语用户”接口，查询是否创建成功。如果未成功，再次创建 end */
                } else {
                    // 返回码不为0时表示添加失败，记录日志
                    feiyuCloudService.addAccountLogError(regid, addJsonObject, "1");
                }
            } else {
                log.error("Add account in FeiyuCloud failed, reason : [no result], phone : [" + phonenum + "]");
            }
        } catch (Exception e) {
            log.error("Add account in FeiyuCloud failed, phone : [" + phonenum + "], e : ", e);
        }

        return fyAccInfo;
    }

    // 修改绑定手机
    public String modifyPhone() {
        Map<String, Object> reqMap = new HashMap<>();
        String regId = getParameter("regId");
        String fyAccountId = getParameter("fyAccountId");
        String phonenum = getParameter("phonenum");
//        String regId = "ICJ100000000001";
//        String fyAccountId = "FY3A55F2OZVKX";
//        String phonenum = "15002124204";
        try {
            Map<String, String> paramMap = new HashMap<>();
            long ti = System.currentTimeMillis();
            // 飞语云通讯给每个APP的分配的唯一APPID
            paramMap.put("appId", FeiyuCloudParamConst.APPID);
            // 在应用服务器端用户的唯一名称，同一个应用必须要保证唯一 （使用e键用户的regId）
            paramMap.put("fyAccountId", fyAccountId);
            // 待绑定手机号码，拨打出去可以显示用户的本机号码，要带国别码；例如：86+13888888888；如果账户要调用双向回拨接口，必须绑定手机号;
            paramMap.put("globalMobilePhone", "86+" + phonenum);
            // 号码的国际区号（中国就是86）
            paramMap.put("district", "86");
            // 当前时间毫秒值
            paramMap.put("ti", String.valueOf(ti));
            // 当前接口的版本号：2.1.0
            paramMap.put("ver", FeiyuCloudParamConst.VER);
            // MD5（appId + appToken + ti）
            paramMap.put("au", MD5.md5Code(FeiyuCloudParamConst.APPID + FeiyuCloudParamConst.APPTOKEN + ti));

            String result = "";
            try {
                result = HttpUtil.post(paramMap, FeiyuCloudParamConst.URL_MODIFY_PHONE, "utf-8");
                if (!"".equals(result)) {
                    JSONObject jsonObject = JSONObject.fromObject(result);
                    if (!"0".equals(StrUtils.emptyOrString(jsonObject.get("resultCode")))) {
                        // 返回码不为0时表示添加失败，记录日志
                        feiyuCloudService.addAccountLogError(regId, jsonObject, "2");
                    } else {
                        // 成功则修改数据库中的绑定手机
                        feiyuCloudService.modifyPhone(regId, fyAccountId, phonenum);
                    }
                    reqMap.put("result", "Y");
                    reqMap.put("resultCode", StrUtils.emptyOrString(jsonObject.get("resultCode")));
                } else {
                    reqMap.put("result", "N");
                    reqMap.put("info", "modify phone in FeiyuCloud failed, reason : [no result]");
                    log.error("modify phone in FeiyuCloud failed, reason : [no result], phone : [" + phonenum + "]");
                }
            } catch (Exception e) {
                reqMap.put("result", "N");
                reqMap.put("info", "1003, " + e.getMessage());
                log.error("modify phone in FeiyuCloud failed, phone : [" + phonenum + "], e : ", e);
            }

        } catch (Exception e) {
            reqMap.put("result", "N");
            log.error("FeiyuCloudAction.modifyPhone failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    // 套餐列表
    public String combo() {
        Map<String, Object> reqMap = new HashMap<>();
        log.info("into FeiyuCloudAction.combo...");
        try {
            List<Map<String, Object>> combo = feiyuCloudService.combo();
            reqMap.put("status", "Y");
            reqMap.put("combo", combo);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, e :" + e.getMessage());
            log.error("FeiyuCloudAction.modifyPhone failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    // 用户信息
    public String userInfo() {
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        log.info("into FeiyuCloudAction.userInfo...");
        log.info("uid = " + uid);
        try {
            List<Map<String, Object>> userInfo = feiyuCloudService.userInfo(uid);
            reqMap.put("status", "Y");
            reqMap.put("userInfo", userInfo);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, e :" + e.getMessage());
            log.error("FeiyuCloudAction.userInfo failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    // 获取手机号归属地
    public String phoneArea() {
        Map<String, Object> reqMap = new HashMap<>();
        String phone = getParameter("phone");// 手机号
        log.info("into FeiyuCloudAction.phoneArea...");
        log.info("phone = " + phone);
        try {
            String phoneArea = "";
            if (StrUtils.isNotEmpty(phone) && phone.length() == 11) {
                phoneArea = feiyuCloudService.phoneArea(phone);
            }
            reqMap.put("phoneArea", phoneArea);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, e :" + e.getMessage());
            log.error("FeiyuCloudAction.phoneArea failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    // 记录用户点击“免费电话”按钮的行为
    public String callingRec() {
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        String imei = getParameter("imei");// 用户imei
        String fyAccId = getParameter("fyAccId");// 飞语账号id
        String flag = getParameter("flag");// 本次行为的标记（时间戳）
        String sup = getParameter("sup");// 供应商6位代码
        String callee = getParameter("callee");// 被叫
        log.info("into FeiyuCloudAction.callingRec...");
        log.info("uid = " + uid + ", imei = " + imei + ", fyAccId = " + fyAccId + ", flag = "
                + flag + ", sup = " + sup + ", callee = " + callee);
        try {

            feiyuCloudService.callingRec(uid, imei, fyAccId, flag, sup, callee);

            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, e :" + e.getMessage());
            log.error("FeiyuCloudAction.callingRec failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    // 更新通话记录
    public String updateCallingRec() {
        Map<String, Object> reqMap = new HashMap<>();
        String etime = getParameter("etime");// 结束时间
        String elapse = getParameter("elapse");// 通话时长
        String flag = getParameter("flag");// 本次行为的标记（时间戳）
        log.info("into FeiyuCloudAction.updateCallingRec...");
        log.info("etime = " + etime + ", elapse = " + elapse + "flag = " + flag);
        try {

            feiyuCloudService.updateCallingRec(etime, elapse, flag);

            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, e :" + e.getMessage());
            log.error("FeiyuCloudAction.updateCallingRec failed, e : ", e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    // 查询飞语账号是否存在
    public String findExistsFyAccOnFyCloud() {
        String fyId = "FY88CE1ZO5WAF";
        String phone = "86+13914365476";
        Map<String, String> paramMap = new HashMap<>();
        long ti = System.currentTimeMillis();
        paramMap.put("appId", FeiyuCloudParamConst.APPID);
        paramMap.put("infoType", "3");// 查询信息类型：1）飞语云账户号码；2）APP账户号码；3）手机号码
        paramMap.put("info", phone);
        paramMap.put("ti", String.valueOf(ti));
        paramMap.put("ver", FeiyuCloudParamConst.VER);
        paramMap.put("au", MD5.md5Code(FeiyuCloudParamConst.APPID + FeiyuCloudParamConst.APPTOKEN + ti));
        String getResult = HttpUtil.post(paramMap, FeiyuCloudParamConst.URL_GETACCOUNT, "utf-8");
        if (!"".equals(getResult)) {
            JSONObject getJsonObject = JSONObject.fromObject(getResult);
            try {
                getJsonObject.put("fyId", fyId);
                writeToResponse(getJsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return NONE;
    }

    // 老用户一次性注册飞语云账号
    public String addFeiyuAcc4ExistsUser() {

        // 查询没有飞语云账号的老用户
//        List<Map<String, Object>> users = feiyuCloudService.getExistsUserIsNotFyAcc();

//        if (StrUtils.isNotEmpty(users)) {
//            for (int i = 0; i < users.size(); i++) {
//                Map<String, Object> user = users.get(i);
//                String regid = StrUtils.emptyOrString(user.get("C_REGID"));
//                String phonenum = StrUtils.emptyOrString(user.get("C_PHONENUM"));
//                String regid = "ICJ1639";
//                String phonenum = "15102194569";
//                Map<String, String> paramMap = new HashMap<>();
//                long ti = System.currentTimeMillis();
//                // 飞语云通讯给每个APP的分配的唯一APPID
//                paramMap.put("appId", FeiyuCloudParamConst.APPID);
//                // 在应用服务器端用户的唯一名称，同一个应用必须要保证唯一 （使用e键用户的regId）
//                paramMap.put("appAccountId", regid);
//                // 待绑定手机号码，拨打出去可以显示用户的本机号码，要带国别码；例如：86+13888888888；如果账户要调用双向回拨接口，必须绑定手机号;
//                paramMap.put("globalMobilePhone", "86+" + phonenum);
//                // 号码的国际区号（中国就是86）
//                paramMap.put("district", "86");
//                // 当前时间毫秒值
//                paramMap.put("ti", String.valueOf(ti));
//                // 当前接口的版本号：2.1.0
//                paramMap.put("ver", FeiyuCloudParamConst.VER);
//                // MD5（appId + appToken + ti）
//                paramMap.put("au", MD5.md5Code(FeiyuCloudParamConst.APPID + FeiyuCloudParamConst.APPTOKEN + ti));
//
//                String result = "";
//
//                try {
//                    result = HttpUtil.post(paramMap, FeiyuCloudParamConst.URL_ADDACCOUNT, "utf-8");
//                    log.info("飞语账号添加完成! 返回结果  >>>>>>>>  " + result);
//                    if (!"".equals(result)) {
//                        JSONObject jsonObject = JSONObject.fromObject(result);
//                        if ("0".equals(StrUtils.emptyOrString(jsonObject.get("resultCode")))) {
//                            // 将生成的飞语云用户存入数据库
//                            int ii = feiyuCloudService.addAccount(regid, phonenum, jsonObject);
//                            if (0==ii) {
//                                // 给用户增加通话时间
//                                feiyuCloudService.grantUserCallTime(((Map)jsonObject.get("result")).get("fyAccountId"));
//                            }
//                        } else {
//                            // 返回码为0时表示添加失败，记录日志
//                            feiyuCloudService.addAccountLogError(regid, jsonObject, "1");
//                        }
//                    } else {
//                        log.error("Add account in FeiyuCloud failed, reason : [no result], phone : [" + phonenum + "]");
//                    }
//                } catch (Exception e) {
//                    log.error("Add account in FeiyuCloud failed, phone : [" + phonenum + "], e : ", e);
//                }
//            }
//        }

        // 查询所有没有通话时间的飞语用户
//        List<Map<String, Object>> users = feiyuCloudService.feiyuAccHasntTime();
//        if (users.size()>0) {
//            for (int i = 0; i < users.size(); i++) {
//                String fyaccountid = users.get(i).get("C_FYACCOUNTID").toString();
//                feiyuCloudService.grantUserCallTime(fyaccountid);
//            }
//        }

        return NONE;
    }

//    public static void main(String[] args) {
//        String regid = "ICJ3760";
//        String phonenum = "15002124206";
//        Map<String, String> paramMap = new HashMap<>();
//        long ti = System.currentTimeMillis();
//        paramMap.put("appId", FeiyuCloudParamConst.APPID);
//        paramMap.put("infoType", "2");// 查询信息类型：1）飞语云账户号码；2）APP账户号码；3）手机号码
//        paramMap.put("info", regid);
//        paramMap.put("ti", String.valueOf(ti));
//        paramMap.put("ver", FeiyuCloudParamConst.VER);
//        paramMap.put("au", MD5.md5Code(FeiyuCloudParamConst.APPID + FeiyuCloudParamConst.APPTOKEN + ti));
//        String getResult = HttpUtil.post(paramMap, FeiyuCloudParamConst.URL_GETACCOUNT, "utf-8");
//        JSONObject jsonObject = JSONObject.fromObject(getResult);
//        Map<String, Object> result = (Map) (jsonObject.get("result"));
//        System.out.println("fyAccountId  : " + result.get("fyAccountId").toString());
//        System.out.println("fyAccountPwd : " + result.get("fyAccountPwd").toString());
//        System.out.println("addDate      : " + result.get("addDate").toString());
//        System.out.println("status       : " + result.get("status").toString());
//
//        // 飞语账号接口
//        String acc = "INSERT INTO T_FY_USER (C_ID,C_APPACCOUNTID,C_FYACCOUNTID,C_FYACCOUNTPWD,C_CREATEDATE,C_STATUS,C_GLOBALMOBILEPHONE) VALUES (SEQ_FY_USER.NEXTVAL,'ICJ3862','FY88CE1VUTFNA','3HRZLC','2016-05-30 14:41:09','1','15002124206')";
//        // 通话时间SQL1
//        String time1 = "INSERT INTO T_FY_USER_TIME (C_ID,C_FYACCOUNTID,C_TIME_TEMP,C_TIME_PERPETUAL,C_VALIDITY) VALUES (SEQ_FY_USER_TIME.NEXTVAL,'FY88CE1VUTFNA',200,0,TO_DATE('2016-05-31 23:59:59', 'yyyy-MM-dd HH24:mi:ss'))";
//        // 通话时间SQL2
//        String time2 = "INSERT INTO T_FY_USER_TIME (C_ID,C_FYACCOUNTID,C_TIME_TEMP,C_TIME_PERPETUAL,C_VALIDITY) VALUES (SEQ_FY_USER_TIME.NEXTVAL,'FY88CE1VUTFNA',200,0,TO_DATE('9999-12-31 23:59:59', 'yyyy-MM-dd HH24:mi:ss'))";
//    }
}