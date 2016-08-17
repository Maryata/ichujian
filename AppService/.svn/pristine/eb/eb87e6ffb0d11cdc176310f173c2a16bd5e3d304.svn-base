package com.alipay.servlet;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;
import com.sys.ekey.shop.common.OrderUtil;
import com.sys.ekey.shop.service.ShopService;
import com.sys.util.FileUtil;
import com.sys.util.StrUtils;
import com.sys.util.encrypt.Base64;
import com.unionpay.acp.sdk.LogUtil;
import net.sf.json.JSONObject;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/4/15.
 */
public class ShopSign extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, Object> result = new HashMap<>();
        /** 1.组装参数 */
        /**
         *     service             接口名称，固定值                 mobile.securitypay.pay
         *     partner             签约的支付宝唯一用户号            2088101568358171
         *     seller_id           卖家支付宝账号
         *     _input_charset      参数编码字符集                   utf-8
         *     sign_type           签名方式                        RSA
         *     notify_url          服务器异步通知页面路径
         *     payment_type        支付类型,默认值为：1（商品购买）
         *     subject             商品名称
         *     body                商品详情描述信息
         *     out_trade_no        商户网站唯一订单号
         *     total_fee           总金额,精确到小数点后两位[0.01，100000000.00]
         *     sign                签名
         *
         */

        String service = FileUtil.getConfig("acp_sdk", "aliPay.method.mobile");
        String notify_url = FileUtil.getConfig("acp_sdk", "baseUrl") + "servlet/shopNotifyAli";
        LogUtil.writeLog("------notify_url---->>>>>>" + notify_url);
        String payment_type = FileUtil.getConfig("acp_sdk", "aliPay.payment.type.buyproduct");

        /** 1.获取参数 */
        String pid = _decode(req.getParameter("pid"));// 商品id
        String uid = _decode(req.getParameter("uid"));// 用户id
        String num = _decode(req.getParameter("num"));//数量
        String score = _decode(req.getParameter("score"));//商品积分
        String out_trade_no = "";// 订单号（为空则是新订单，不为空则是之前生成而未支付的订单）
        String _account = _decode(req.getParameter("account"));//收货人
        String _address = _decode(req.getParameter("address"));//收货地址
        String _phone = _decode(req.getParameter("phone"));//手机号
        String pCode = _decode(req.getParameter("pCode"));//手机型号
        String pName = _decode(req.getParameter("pName"));//型号名称
        //String _expressCost = _decode(req.getParameter("price"));
        String subject = "";// 商品名称
        String body = "";// 商品描述

        LogUtil.writeLog("pid = " + pid + ", uid = " + uid + ", num = " + num + ", score = " + score
                + ", _account = " + _account + ", _address = " + _address + ", _phone = " + _phone
                + ", pCode = " + pCode + ", pName = " + pName + ", _phone = " + _phone);

        ShopService shopService = WebApplicationContextUtils.
                getRequiredWebApplicationContext(getServletContext()).getBean(ShopService.class);

        /** 2.生成订单 */
        String total_fee = "";


        /**判断商品积分是否够兑换  1：查询用户总积分*/
        String _score = shopService.selectUserScore(uid);
        if (Integer.parseInt(_score) >= Integer.parseInt(score)) {
            //用户积分 >=  商品积分
            /**查询实物商品是否存在*/
            int row = shopService.updateProductNum(pid);//修改商品数量
            if (row > 0) {
                //修改用户积分
                int scoreRow = shopService.updateUserScore(uid, score);
                if (scoreRow > 0) {
                    try {
                        // 查询套餐详情
                        List<Map<String, Object>> combos = shopService.getComboById(uid,pid);
                        if (StrUtils.isNotEmpty(combos)) {
                            Map<String, Object> combo = combos.get(0);

                            out_trade_no = OrderUtil.outTradeNo();//订单交易号
                            String c_score_cost = StrUtils.emptyOrString(score);//积分
                            subject = StrUtils.emptyOrString(combo.get("C_TITLE"));// 商品名称
                            body = StrUtils.emptyOrString(combo.get("C_SUBTITLE"));// 商品描述

                            String _expressCost = StrUtils.emptyOrString(combo.get("C_CARRIAGE"));//物流费
//                            DecimalFormat df = new DecimalFormat("0.00");// 订单金额
//                            total_fee = df.format(Double.valueOf(_expressCost));
//                            total_fee = "10";

                            DecimalFormat df = new DecimalFormat("0.00");// 订单金额
                            total_fee = df.format(Double.valueOf(_expressCost));


                            Long currentTimeMillis = System.currentTimeMillis();
                            String c_ctime = String.valueOf(currentTimeMillis);//添加时间
                            String c_pay_status = "0";//未支付
                            String c_pay_type = "2";//支付类型   2：支付宝支付
                            String phone = _phone;//手机号
                            String account = _account;//姓名
                            String address = _address;//地址
                            String c_pid = StrUtils.emptyOrString(combo.get("C_ID"));//商品id
                            String c_type = StrUtils.emptyOrString(combo.get("C_TYPE"));//类型
                            shopService.insertOrder(out_trade_no, _expressCost, c_score_cost, c_ctime, c_pay_status, c_pay_type, phone, account, address, c_pid, c_type, uid, num,pCode,pName);
                        }
                    } catch (Exception e) {
                        LogUtil.writeErrorLog("记录订单信息错误！", e);
                    }

                    /** 3.拼接参数 */
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("partner=\"" + AlipayConfig.partner + "\"");
                    buffer.append("&seller_id=\"" + AlipayConfig.seller_id + "\"");
                    buffer.append("&out_trade_no=\"" + out_trade_no + "\"");
                    buffer.append("&subject=\"" + subject + "\"");
                    buffer.append("&body=\"" + body + "\"");
                    buffer.append("&total_fee=\"" + total_fee + "\"");
                    buffer.append("&notify_url=\"" + notify_url + "\"");
                    buffer.append("&service=\"" + service + "\"");
                    buffer.append("&payment_type=\"" + payment_type + "\"");
                    buffer.append("&_input_charset=\"" + AlipayConfig.input_charset + "\"");
                    buffer.append("&it_b_pay=\"" + "30m" + "\"");
                    //        buffer.append("&return_url=\"" + "m.alipay.com" + "\"");

                    /** 4.加密 */
                    String sign = RSA.sign(buffer.toString(), AlipayConfig.private_key, AlipayConfig.input_charset);

                    result.put("content", buffer.toString());
                    result.put("sign", sign);

                    result.put("status", "SUCCESS");
                    result.put("msg", "签名成功");
                } else {
                    result.put("status", "FAIL");
                    result.put("msg", "你的积分不足");
                }

            } else {//失败
                result.put("status", "FAIL");
                result.put("msg", "商品已售完");
            }
        } else {//失败
            result.put("status", "FAIL");
            result.put("msg", "你的积分不足");
        }
        /** 5.返回 */
        LogUtil.writeLog("返回签到的数据---->>> " + JSONObject.fromObject(result).toString());
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(JSONObject.fromObject(result).toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    private String _decode(String value) {
        if (value != null) {
            value = value.trim();
            try {
                value = Base64.decode(value);
            } catch (Exception e) {
                LogUtil.writeErrorLog("转码错误！", e);
            }
        }

        return value;
    }

}
