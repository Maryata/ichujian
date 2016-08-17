package com.sys.ekey.rebate.action;

import com.sys.commons.AbstractAction;
import com.sys.ekey.freecall.common.FeiyuCloudParamConst;
import com.sys.ekey.rebate.service.RebateService;
import com.sys.util.*;
import com.weixinpay.config.WeixinPayConfig;
import com.weixinpay.util.MD5Util;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Maryn on 2016/7/25.
 */
@Controller
public class RebateAction extends AbstractAction {

    @Autowired
    private RebateService rebateService;

    // 我的财富
    public String myWealth(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        log.info("into RebateAction.myWealth...");
        log.info("uid = " + uid);
        try {
            List<Map<String, Object>> wealth = rebateService.myWealth(uid);
            reqMap.put("status", "Y");
            reqMap.put("wealth", wealth);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.myWealth failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 财富明细
    public String wealthDetail(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        log.info("into RebateAction.wealthDetail...");
        log.info("uid = " + uid);
        try {
            List<Map<String, Object>> detail = rebateService.wealthDetail(uid);
            reqMap.put("status", "Y");
            reqMap.put("detail", detail);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.wealthDetail failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 财富明细（按月统计）
    public String wealthDetailByMonth(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        String type = getParameter("type");// 查询的时间类型：1.当前月 2.所有月
        log.info("into RebateAction.wealthDetailByMonth...");
        log.info("uid = " + uid + ", type = " + type);
        try {
            List<Map<String, Object>> detail = rebateService.wealthDetailByMonth(uid, type);
            reqMap.put("status", "Y");
            reqMap.put("detail", detail);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.wealthDetailByMonth failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 我的人脉
    public String myReference(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        log.info("into RebateAction.myReference...");
        log.info("uid = " + uid);
        try {
            List<Map<String, Object>> reference = rebateService.myReference(uid);
            reqMap.put("status", "Y");
            reqMap.put("reference", reference);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.myReference failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 我的人脉
    public String rankList(){
        Map<String, Object> reqMap = new HashMap<>();
        String type = getParameter("type");// 统计时间类型：1.当前月，2.所有月
        log.info("into RebateAction.rankList...");
        log.info("type = " + type);
        try {
            List<Map<String, Object>> rankList = rebateService.rankList(type);
            reqMap.put("status", "Y");
            reqMap.put("rankList", rankList);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.rankList failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 提现明细
    public String withdrawDetail(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        log.info("into RebateAction.withdrawDetail...");
        log.info("uid = " + uid);
        try {
            List<Map<String, Object>> detail = rebateService.withdrawDetail(uid);
            reqMap.put("status", "Y");
            reqMap.put("detail", detail);
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.withdrawDetail failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 申请提现
    public String withdraw(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        String amount = getParameter("amount");// 提现金额
        String pay_type = getParameter("pay_type");// 充值类型 1.微信 2.支付宝
        String pay_account = getParameter("pay_account");// 充值账号
        String withdraw_pwd = getParameter("withdraw_pwd");// 提现密码
        log.info("into RebateAction.withdraw...");
        log.info("uid = " + uid + ", amount = " + amount + ", pay_type = " + pay_type
                + ", pay_account = " + pay_account + ", withdraw_pwd = " + withdraw_pwd);
        try {
            String result = rebateService.withdraw(uid, amount, pay_type, pay_account, withdraw_pwd);

            reqMap.put("result", result);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.withdraw failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 校验身份证
    public String authId(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        String id_num = getParameter("id_num");// 身份证号码
        log.info("into RebateAction.authId...");
        log.info("uid = " + uid + ", id_num = " + id_num);
        try {
            String result = rebateService.authId(uid, id_num);

            reqMap.put("result", result);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.authId failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 保存商户信息
    public String saveBusinessInfo(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        String company = getParameter("company");// 公司名
        String license = getParameter("license");// 营业执照
        String law_name = getParameter("law_name");// 法人名称
        String law_id = getParameter("law_id");// 法人身份证
        String shop_addr = getParameter("shop_addr");// 店铺地址
        String shop_tel = getParameter("shop_tel");// 店铺电话
        String[] license_img = getRequest().getParameterValues("license_img");// 营业执照照片
        String[] shop_img = getRequest().getParameterValues("shop_img");// 店铺照片
        log.info("into RebateAction.saveBusinessInfo...");
        log.info("uid = " + uid + ", company = " + company + ", license = " + license + ", law_name = " + law_name
                + ", law_id = " + law_id + ", shop_addr = " + shop_addr + ", shop_tel = " + shop_tel);
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("C_ID", uid);
            param.put("C_COMPANY", company);
            param.put("C_LICENSE", license);
            param.put("C_LAW_NAME", law_name);
            param.put("C_LAW_ID", law_id);
            param.put("C_SHOP_ADDR", shop_addr);
            param.put("C_SHOP_TEL", shop_tel);
            String result = rebateService.saveBusinessInfo(param, license_img, shop_img);

            reqMap.put("result", result);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.saveBusinessInfo failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 获取商户信息
    public String businessInfo(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");
        log.info("into RebateAction.businessInfo...");
        log.info("uid = " + uid);
        try {
            List<Map<String, Object>> info = rebateService.businessInfo(uid);
            reqMap.put("info", info);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.businessInfo failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 申请售后屏保
    public String activeInsurance(){
        Map<String, Object> reqMap = new HashMap<>();
        String type = getParameter("type");// 是否需要扫码后激活 0.否 1.是
        String uid = getParameter("uid");// 用户id
        String phone_num = getParameter("phone_num");// 手机号
        String imei = getParameter("imei");// 手机IMEI
        String code = getParameter("code");// 激活码
        String phone_model = getParameter("phone_model");// 手机型号
        String[] img = getRequest().getParameterValues("img");// 图片
        log.info("into RebateAction.activeInsurance...");
        log.info("type = " + type + ", uid = " + uid + ", phone_num = " + phone_num
                + ", imei = " + imei + ", code = " + code + ", phone_model = " + phone_model);
        try {

            Map<String, Object> param = new HashMap<>();
            param.put("type", type);
            param.put("C_UID", uid);
            param.put("C_PHONE_NUM", phone_num);
            param.put("C_IMEI", imei);
            param.put("C_CODE", code);

            // 二次申请时用到的查询参数
            param.put("activecode", code);
            param.put("imei", imei);

            param.put("C_PHONE_MODEL", phone_model);

            String result = rebateService.activeInsurance(param, img);

            reqMap.put("result", result);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.activeInsurance failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 查询是否已激活售后屏保
    public String isActed(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        String code = getParameter("code");// 用户激活码
        log.info("into RebateAction.isActed...");
        log.info("uid = " + uid + ", code = " + code);
        try {

            reqMap = rebateService.isActed(reqMap, uid, code);

            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.isActed failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 申请维修
    public String applyForRepair(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        String code = getParameter("code");// 激活码
        String[] img = getRequest().getParameterValues("img");// 图片
        log.info("into RebateAction.applyForRepair...");
        log.info("uid = " + uid + ", code = " + code);
        try {

            String result = rebateService.applyForRepair(uid, code, img);

            reqMap.put("result", result);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.applyForRepair failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    // 申请明细
    public String applyDetail(){
        Map<String, Object> reqMap = new HashMap<>();
        String uid = getParameter("uid");// 用户id
        String code = getParameter("code");// 激活码
        log.info("into RebateAction.applyDetail...");
        log.info("uid = " + uid + ", code = " + code);
        try {

            Map<String, Object> detail = rebateService.applyDetail(uid, code);

            reqMap.put("detail", detail);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("RebateAction.applyDetail failed, e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error(e);
        }

        return NONE;
    }

    /**
     * 将这个code传送给后端服务器，然后在服务器里发起对微信的GET请求：

       https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code;

       这个请求返回的Json里的openid就是我们要的。

        1.获取access_token
        2.通过access_token获取ticket
        3.通过ticket生成signature
     * @return
     */
    public String getOpenId(){
        String callback = getRequest().getParameter("callback");
        String code = getRequest().getParameter("code");
        log.info("code = " + code);

        /** 1.获取access_token
         *
         *  {
             "access_token": "YVptktHnKkRHs---pBtB-pr2DoLfaSP6qD3Q6Cvo6m_tCtMAYNEh47EooSEWskCnKDXzoL8eDJp2UOuj2HzwkmloEyAAKzS5uKavAYpB-D8",
             "expires_in": 7200,
             "refresh_token": "FVKl3XedK-A27YiteU5WEfDFPhbWIupCLyHWaF27dHYI8ccCcgrZPF3pLNwyaPlE585t5an8bjTqFD4ha6SDnGayW-93CH_qv_x8QNBkpi4",
             "openid": "oz-PxvoxMPerRcZLuUQUtLBuU5-0",
             "scope": "snsapi_userinfo"
            }
         */
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", WeixinPayConfig.APP_ID_H5);
        paramMap.put("secret", WeixinPayConfig.APP_SECRET_H5);
        paramMap.put("code", code);
        paramMap.put("grant_type", "authorization_code");
        String result_openId = HttpUtil.post(paramMap, WeixinPayConfig.URL_GET_OPEN_ID, "utf-8");
        log.info("result_openId ---> " + result_openId);

        try {
            writeToResponse(callback + "(" + result_openId + ")");
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

    /**
     *
     *  http://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi

    */
    public String getSignature(){
        Map<String, Object> reqMap = new HashMap<>();
        String callback = getRequest().getParameter("callback");
        String _url = getRequest().getParameter("_url");
        log.info(", _url = " + _url);
        // 获取公众号的access_token（不是用户的access_token）
        /**
         *  {
             "access_token": "5xxyKfu3ZtEE40_8RM6BKULAtIn-V_8zpIRaeN7RuAdmlw-S7kaK5KNr8W1TxRQ0UDorBePCmad5Rk6XAHAyB_nMFYAkuIecBEfRq9cqGw-7kD46uocaJYEoByvf2RFCFCBaAAAAXW",
             "expires_in": 7200
            }
         *
         */
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credential");
        paramMap.put("appid", WeixinPayConfig.APP_ID_H5);
        paramMap.put("secret", WeixinPayConfig.APP_SECRET_H5);
        String result_token = HttpUtil.post(paramMap, WeixinPayConfig.URL_GET_TOKEN, "utf-8");
        log.info("result_token ---> " + result_token);

        /**
         * 获取ticket
         * {
             "errcode":0,
             "errmsg":"ok",
             "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
             "expires_in":7200
           }
         */
        String access_token = JSONObject.fromObject(result_token).getString("access_token");
        StringBuffer url_ticket = new StringBuffer(WeixinPayConfig.URL_GET_TICKET);
        url_ticket.append("?access_token=" + access_token);
        url_ticket.append("&type=jsapi");
        String result_ticket = GetUtil.doGet(url_ticket.toString(), "utf-8");
        log.info("result_ticket ---> " + result_ticket);

        String sign = "";
        if ("ok".equals(JSONObject.fromObject(result_ticket).getString("errmsg"))) {
            // 签名
            /**
             *   noncestr=Wm3WZYTPz0wzccnW
                 jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
                 timestamp=1414587457
                 url=http://mp.weixin.qq.com
             */
            String ticket = JSONObject.fromObject(result_ticket).getString("ticket");
            String noncestr = MD5Util.MD5Encode(RandNumber.getRandNumber(4), "utf-8");
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

            log.info("ticket ---> " + ticket);
            log.info("noncestr ---> " + noncestr);
            log.info("timestamp ---> " + timestamp);
            log.info("requestURL ---> " + _url);

            /**
             *
             *      jsapi_ticket=kgt8ON7yVITDhtdwci0qeaRqIE47hpxYXidmWIeMEnA5D6yIVTr_NNMaYQCDLWflSG1xISlHqKtDcUVq78h66Q
             *      &noncestr=f6876a9f998f6472cc26708e27444456
             *      &timestamp=1471327570
             *      &url=http://www.51ekey.com/webView/rebate/wxPay.html?appId=wx51d347df2a345dd3&timeStamp=1471327569&nonceStr=01847f00f49b070bba5c0331bf16fe58&pg=prepay_id=wx201608161406098df0da18c40048873022&paySign=166F1D55DE978E819B2B4325302FA254&signType=MD5
             *
             *
             */
            StringBuffer sb = new StringBuffer();
            sb.append("jsapi_ticket=" + ticket);
            sb.append("&noncestr=" + noncestr);
            sb.append("&timestamp=" + timestamp);
            sb.append("&url=" + _url);
            sign = MD5Util.SHA1(sb.toString());
            reqMap.put("appId", WeixinPayConfig.APP_ID_H5);
            reqMap.put("timestamp", timestamp);
            reqMap.put("nonceStr", noncestr);
            reqMap.put("signature", sign);
        }

        try {
            writeToResponse(callback + "(" + JSONObject.fromObject(reqMap).toString() + ")");
        } catch (Exception e) {
            log.error(e);
        }
        return NONE;
    }

}
