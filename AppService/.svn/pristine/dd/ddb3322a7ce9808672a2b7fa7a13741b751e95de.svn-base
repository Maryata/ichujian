package com.weixinpay;

import com.sys.ekey.shop.common.OrderUtil;
import com.sys.service.ExchangeService;
import com.sys.util.FileUtil;
import com.sys.util.RandNumber;
import com.weixinpay.config.WeixinPayConfig;
import com.weixinpay.util.MD5Util;
import com.weixinpay.util.WeixinService;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vpc on 2016/4/14.
 */
public class H5WXSign extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        应用ID	appid	是	String(32)	wxd678efh567hg6787	微信开放平台审核通过的应用APPID
//        商户号	mch_id	是	String(32)	1230000109	微信支付分配的商户号
//        设备号	device_info	否	String(32)	013467007045764	终端设备号(门店号或收银设备ID)，默认请传"WEB"
//        随机字符串	nonce_str	是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
//        签名	sign	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
//        商品描述	body	是	String(128)	Ipad mini  16G  白色	商品或支付单简要描述
//        商品详情	detail	否	String(8192)	Ipad mini  16G  白色	商品名称明细列表
//        附加数据	attach	否	String(127)	深圳分店	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
//        商户订单号	out_trade_no	是	String(32)	20150806125346	商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
//        货币类型	fee_type	否	String(16)	CNY	符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
//        总金额	total_fee	是	Int	888	订单总金额，单位为分，详见支付金额
//        终端IP	spbill_create_ip	是	String(16)	123.12.12.123	用户端实际ip
//        交易起始时间	time_start	否	String(14)	20091225091010	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//        交易结束时间	time_expire	否	String(14)	20091227091010
//        订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
//        注意：最短失效时间间隔必须大于5分钟
//        商品标记	goods_tag	否	String(32)	WXG	商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
//        通知地址	notify_url	是	String(256)	http://www.weixin.qq.com/wxpay/pay.php	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
//        交易类型	trade_type	是	String(16)	APP	支付类型
//        指定支付方式	limit_pay	否	String(32)	no_credit	no_credit--指定不能使用信用卡支付
        request.setCharacterEncoding("utf-8");
        Map<String, String> resultMap = new HashMap<>();

        // 要加密的参数
        StringBuilder stringBuilder = new StringBuilder();

        String callback = request.getParameter("callback");//跨域请求回调方法名称
        String openId = request.getParameter("openId");//跨域请求回调方法名称
        String uid = request.getParameter("uid");// 用户id
        String payType = request.getParameter("payType");// 支付类型 1.微信 2.支付宝 0.老版本免费换膜
        String type = "1";// 套餐类型 1.129套餐 2.299套餐 （目前只有129套餐）
        String body = request.getParameter("phone");// 手机品牌型号（相当于body参数）
        String pCode = request.getParameter("pCode");// 手机型号（eg. C200A1M0140）
        String telNum = request.getParameter("telNum");// 手机型号（eg. C200A1M0140）
        String consignee = request.getParameter("consignee");// 手机型号（eg. C200A1M0140）
        String address = request.getParameter("address");// 手机型号（eg. C200A1M0140）
        String total_fee = "1";
        String express = "中通";//物流
        String title = "H5微信支付";
        String expressCode = "ZTO";//物流码


        String appId = WeixinPayConfig.APP_ID_H5;
        String mch_id = WeixinPayConfig.MCH_ID_H5;
        String notify_url = FileUtil.getConfig("acp_sdk", "baseUrl") + WeixinPayConfig.NOTIFY_URL_H5;
        String trade_type = WeixinPayConfig.TRADE_TYPE_H5;
        String key = WeixinPayConfig.KEY_H5;
        String charSet = WeixinPayConfig.CHARSET;
        String spbill_create_ip = FileUtil.getConfig("acp_sdk", "wxpay.spbill.create.ip");


        // 订单号
        String out_trade_no = OrderUtil.outTradeNo();//订单号

        ExchangeService exchangeService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext()).getBean(ExchangeService.class);

        String nonce_str = MD5Util.MD5Encode(uid + RandNumber.getRandNumber(4), charSet);

        Map<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("appid", appId);
        parameterMap.put("mch_id", mch_id);
        parameterMap.put("nonce_str", nonce_str);
        parameterMap.put("body", body);
        parameterMap.put("out_trade_no", out_trade_no);
        parameterMap.put("total_fee", total_fee);
        parameterMap.put("spbill_create_ip", spbill_create_ip);
        parameterMap.put("notify_url", notify_url);
        parameterMap.put("trade_type", trade_type);
        parameterMap.put("openid", openId);

        // 组装加密参数
        for (Iterator<Map.Entry<String, String>> iterator = parameterMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();

            if (stringBuilder.length() == 0) {
                stringBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue());
            } else {
                stringBuilder.append("&").append(entry.getKey()).append("=")
                        .append(entry.getValue());
            }
        }
        stringBuilder.append("&").append("key=").append(key);

        // 加密
        String sign = MD5Util.MD5Encode(stringBuilder.toString(), charSet).toUpperCase();

        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(charSet);
        Element root = document.addElement("xml");
        root.addElement("appid").setText(appId);
        root.addElement("body").setText(body);
        root.addElement("mch_id").setText(mch_id);
        root.addElement("nonce_str").setText(nonce_str);
        root.addElement("notify_url").setText(notify_url);
        root.addElement("out_trade_no").setText(out_trade_no);
        root.addElement("spbill_create_ip").setText(spbill_create_ip);
        root.addElement("total_fee").setText(total_fee);
        root.addElement("trade_type").setText(trade_type);
        root.addElement("openid").setText(openId);
        root.addElement("sign").setText(sign);

        // 发送订单数据
        Document result = WeixinService.post(document, WeixinPayConfig.UNIFIED_ORDER_URL, charSet);

        String return_code = "FAIL";
        String return_msg = "签名失败";
        Map<String, String> signMap = new TreeMap<>();
        if (result != null) {
            Element resultRootElement = result.getRootElement();

            return_code = resultRootElement.element("return_code").getText();
            return_msg = resultRootElement.element("return_msg").getText();

            if ("SUCCESS".equals(return_code)) {
//                String r_appId = resultRootElement.element("appid").getText();
//                String r_mch_id = resultRootElement.element("mch_id").getText();
//                String r_nonce_str = resultRootElement.element("nonce_str").getText();
//                String r_sign = resultRootElement.element("sign").getText();
                String result_code = resultRootElement.elementText("result_code");
                if ("SUCCESS".equals(result_code)) {
                    String r_prepay_id = resultRootElement.elementText("prepay_id");
                    String r_trade_type = resultRootElement.elementText("trade_type");

//                    signMap.put("appid", WeixinPayConfig.APP_ID);
//                    signMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
//                    signMap.put("noncestr", nonce_str);
//                    signMap.put("partnerid", WeixinPayConfig.MCH_ID);
//                    signMap.put("prepayid", r_prepay_id);
//                    signMap.put("package", WeixinPayConfig.PACKAGE);

                    signMap.put("appId", appId);
                    signMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                    signMap.put("nonceStr", nonce_str);
                    signMap.put("package", "prepay_id=" + r_prepay_id);
                    signMap.put("signType", "MD5");

                    StringBuilder sb = new StringBuilder();
                    for (Iterator<Map.Entry<String, String>> iterator = signMap.entrySet().iterator(); iterator.hasNext(); ) {
                        Map.Entry<String, String> entry = iterator.next();
                        if (sb.length() == 0) {
                            sb.append(entry.getKey()).append("=")
                                    .append(entry.getValue());
                        } else {
                            sb.append("&").append(entry.getKey()).append("=")
                                    .append(entry.getValue());
                        }
                    }
                    sb.append("&").append("key=").append(key);

                    String rSign = MD5Util.MD5Encode(sb.toString(), charSet).toUpperCase();

                    resultMap.putAll(signMap);

                    resultMap.put("rSign", rSign);
//                    resultMap.put("trade_type", r_trade_type);

                    // 写入订单信息
                    exchangeService.save(body, express, address, consignee, telNum, title, pCode, expressCode, out_trade_no, uid, payType, total_fee, type);
                } else {
                    String r_err_code = resultRootElement.elementText("err_code");
                    String r_err_code_des = resultRootElement.elementText("err_code_des");

                    resultMap.put("err_code", r_err_code);
                    resultMap.put("err_code_des", r_err_code_des);
                }
                resultMap.put("return_code", return_code);
                resultMap.put("return_msg", return_msg);
            } else {
                resultMap.put("return_code", return_code);
                resultMap.put("return_msg", return_msg);
            }
        } else {
            resultMap.put("return_code", return_code);
            resultMap.put("return_msg", return_msg);
        }

        response.setContentType("text/html;charset=UTF-8");
//        response.getWriter().write(JSONObject.fromObject(resultMap).toString());

        response.getWriter().write(callback + "(" + JSONObject.fromObject(resultMap).toString() + ")");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public static void main(String[] args) {
        System.out.println(Long.valueOf(System.currentTimeMillis() / 1000));
    }


}
