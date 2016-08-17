package com.weixinpay;

import com.sys.ekey.shop.common.OrderUtil;
import com.sys.ekey.shop.service.ShopService;
import com.sys.util.FileUtil;
import com.sys.util.RandNumber;
import com.sys.util.StrUtils;
import com.weixinpay.config.WeixinPayConfig;
import com.weixinpay.util.MD5Util;
import com.weixinpay.util.WeixinPayUtil;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 商品 物流费支付
 * Created by vpc on 2016/4/14.
 */
public class shopServlet extends HttpServlet {

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
        Map<String, String> resultMap = new HashMap<String, String>();

        // 要加密的参数
        StringBuilder stringBuilder = new StringBuilder();
        // 商品描述
        String body = request.getParameter("body");
        body = WeixinPayUtil.decode(body);
        body = body == null ? "" : body;
        // 商品id
        String pid = request.getParameter("pid");
        pid = WeixinPayUtil.decode(pid);
        String num = WeixinPayUtil.decode(request.getParameter("num"));//数量
        String score = WeixinPayUtil.decode(request.getParameter("score"));//商品积分
        String _account = WeixinPayUtil.decode(request.getParameter("account"));//收货人
        String _address = WeixinPayUtil.decode(request.getParameter("address"));//收货地址
        String _phone = WeixinPayUtil.decode(request.getParameter("phone"));//手机号
        //String _expressCost = WeixinPayUtil.decode(request.getParameter("price"));//
        // uid
        String pCode = WeixinPayUtil.decode(request.getParameter("pCode"));//手机型号
        String pName = WeixinPayUtil.decode(request.getParameter("pName"));//型号名称
        String uid = request.getParameter("uid");
        uid = WeixinPayUtil.decode(uid);
        // 默认总金额
        String _expressCost="";
        String total_fee = "";
        float price = 0.0f;
        // 加密
        String sign = "";
        // 订单号
        String out_trade_no = OrderUtil.outTradeNo();
        String c_score_cost = "";
        String c_ctime = "";
        String c_pay_status = "";
        String c_pay_type = "";
        String c_pid = "";
        String c_type = "";


        ShopService shopService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext()).getBean(ShopService.class);
        /**查询实物商品是否存在*/
        int row = shopService.updateProductNum(pid);//修改商品数量
        /**判断商品积分是否够兑换  1：查询用户总积分*/
        String _score = shopService.selectUserScore(uid);
        if (Integer.parseInt(_score) >= Integer.parseInt(score)) {
            //用户积分 >=  商品积分
            if (row > 0) {//商品数量不为0
                int scoreRow = shopService.updateUserScore(uid, score);
                if (scoreRow > 0) {//积分修改
                    // 到数据库查询 商品信息
                    List<Map<String, Object>> comboList = shopService.getComboById(uid,pid);
                    if (comboList != null) {
                        Map<String, Object> map = comboList.get(0);
                        out_trade_no = OrderUtil.outTradeNo();//订单交易号
                        c_score_cost = StrUtils.emptyOrString(score);//积分
                        body = StrUtils.emptyOrString(map.get("C_TITLE"));// 商品描述
                        _expressCost = StrUtils.emptyOrString(map.get("C_CARRIAGE"));//物流费
                        DecimalFormat df = new DecimalFormat("0.00");// 订单金额
                        total_fee = df.format(Double.valueOf(_expressCost));
                        price = ((BigDecimal) (map.get("C_CARRIAGE"))).floatValue();
                        total_fee = String.valueOf((int) (price * 100));

                        Long currentTimeMillis = System.currentTimeMillis();
                        c_ctime = String.valueOf(currentTimeMillis);//添加时间
                        c_pay_status = "0";//未支付
                        c_pay_type = "1";//支付类型   2：微信支付
                        c_pid = StrUtils.emptyOrString(map.get("C_ID"));//商品id
                        c_type = StrUtils.emptyOrString(map.get("C_TYPE"));//类型
                    }

                    String nonce_str = MD5Util.MD5Encode(uid + RandNumber.getRandNumber(4), WeixinPayConfig.CHARSET);

                    Map<String, String> parameterMap = new TreeMap<String, String>();
                    parameterMap.put("appid", WeixinPayConfig.APP_ID);
                    parameterMap.put("mch_id", WeixinPayConfig.MCH_ID);
                    // parameterMap.put("device_info","WEB");
                    parameterMap.put("nonce_str", nonce_str);
                    parameterMap.put("body", body);
                    parameterMap.put("out_trade_no", out_trade_no);
                    parameterMap.put("total_fee", total_fee);
                    parameterMap.put("spbill_create_ip", WeixinPayConfig.SPBILL_CREATE_IP);
                    //parameterMap.put("spbill_create_ip", "120.26.50.114");
                    parameterMap.put("notify_url", FileUtil.getConfig("acp_sdk", "baseUrl") + "servlet/shopNotifyWeChat");//servlet/shopNotifyWeChat
                    parameterMap.put("trade_type", WeixinPayConfig.TRADE_TYPE);

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
                    stringBuilder.append("&").append("key=").append(WeixinPayConfig.KEY);

                    sign = MD5Util.MD5Encode(stringBuilder.toString(), WeixinPayConfig.CHARSET).toUpperCase();

                    Document document = DocumentHelper.createDocument();
                    document.setXMLEncoding(WeixinPayConfig.CHARSET);
                    Element root = document.addElement("xml");
                    root.addElement("appid").setText(WeixinPayConfig.APP_ID);
                    root.addElement("body").setText(body);
                    root.addElement("mch_id").setText(WeixinPayConfig.MCH_ID);
                    root.addElement("nonce_str").setText(nonce_str);
                    root.addElement("notify_url").setText(WeixinPayConfig.NOTIFY_URL_SHOP);
                    root.addElement("out_trade_no").setText(out_trade_no);
                    root.addElement("spbill_create_ip").setText(WeixinPayConfig.SPBILL_CREATE_IP);
                    //root.addElement("spbill_create_ip").setText("120.26.50.114");
                    root.addElement("total_fee").setText(total_fee);
                    root.addElement("trade_type").setText(WeixinPayConfig.TRADE_TYPE);
                    root.addElement("sign").setText(sign);



                    // 发送订单数据
                    Document result = WeixinService.post(document, WeixinPayConfig.UNIFIED_ORDER_URL, WeixinPayConfig.CHARSET);

                    String return_code = "FAIL";
                    String return_msg = "签名失败";
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

                                Map<String, String> signMap = new TreeMap<String, String>();
                                signMap.put("appid", WeixinPayConfig.APP_ID);
                                signMap.put("partnerid", WeixinPayConfig.MCH_ID);
                                signMap.put("prepayid", r_prepay_id);
                                signMap.put("package", WeixinPayConfig.PACKAGE);
                                signMap.put("noncestr", nonce_str);
                                signMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));

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
                                sb.append("&").append("key=").append(WeixinPayConfig.KEY);

                                String rSign = MD5Util.MD5Encode(sb.toString(), WeixinPayConfig.CHARSET).toUpperCase();

                                resultMap.putAll(signMap);

                                resultMap.put("sign", rSign);
                                resultMap.put("trade_type", r_trade_type);

                                // 写入订单信息
                                // 支付类型1：微信 2:支付宝
                                shopService.insertOrder(out_trade_no, _expressCost, c_score_cost, c_ctime, c_pay_status, c_pay_type, _phone, _account, _address, c_pid, c_type, uid, num, pCode, pName);
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

                } else {
                    resultMap.put("return_code", "FAIL");
                    resultMap.put("return_msg", "你的积分不足");
                }

            } else {//商品库存为0
                resultMap.put("return_code", "FAIL");
                resultMap.put("return_msg", "商品已售完");
            }
        } else {//积分不够
            resultMap.put("return_code", "FAIL");
            resultMap.put("return_msg", "你的积分不足");
        }


        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSONObject.fromObject(resultMap).toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public static void main(String[] args) {
        System.out.println(Long.valueOf(System.currentTimeMillis() / 1000));
    }


}
