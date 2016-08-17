package com.alipay.servlet;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;
import com.sys.action.ErpInfo;
import com.sys.service.ExchangeService;
import com.sys.util.StrUtils;
import com.unionpay.acp.sdk.LogUtil;
import com.weixinpay.util.WeixinPayUtil;
import net.sf.json.JSONObject;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝签名请求
 */
public class Sign extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String,String> resultMap = new HashMap<String,String>();

		String content = req.getParameter("content");

        content = WeixinPayUtil.decode(content);

		LogUtil.writeLog("content -->" + content);

        double total_fee = 9;

		content = content.substring(0,content.indexOf("notify_url") - 1) + "&total_fee=" + "\"" + total_fee + "\"" + content.substring(content.indexOf("notify_url") -1);
//		content = content + "&total_fee=" + "\"" + 0.01 + "\"";
//        StringBuilder stringBuilder = new StringBuilder(content);
//        stringBuilder.append("&total_fee=\"0.01\"");
//
//		content = stringBuilder.toString();
		LogUtil.writeLog("content -->" + content);

		String signed = RSA.sign(content, AlipayConfig.private_key, AlipayConfig.input_charset);

        resultMap.put("total_fee", String.valueOf(total_fee));
        resultMap.put("sign",signed);
	//	resultMap.put("content",content);

        String phone = req.getParameter("phone");
        phone = WeixinPayUtil.decode(phone);
        String express = req.getParameter("express");
        express = StrUtils.isEmpty(WeixinPayUtil.decode(express)) ? "中通" : WeixinPayUtil.decode(express);// 如果为空，默认物流中通
        String address = req.getParameter("address");
        address = WeixinPayUtil.decode(address);
        String consignee = req.getParameter("consignee");
        consignee = WeixinPayUtil.decode(consignee);
        String telNum = req.getParameter("telNum");
        telNum = WeixinPayUtil.decode(telNum);
        String imei = req.getParameter("imei");
        imei = WeixinPayUtil.decode(imei);
        // 产品代码
        String pCode = req.getParameter("pCode");
        pCode = WeixinPayUtil.decode(pCode);
        // 物流公司代码
        String expressCode = req.getParameter("expressCode");
        expressCode = WeixinPayUtil.decode(expressCode);
        String out_trade_no = req.getParameter("out_trade_no");
        out_trade_no = WeixinPayUtil.decode(out_trade_no);
        String uid = WeixinPayUtil.decode(req.getParameter("uid"));
        String c_type=WeixinPayUtil.decode(req.getParameter("c_type"));
        if("1".equals(c_type)){
            total_fee=129;
        }else if("2".equals(c_type)){
            total_fee=299;
        }
        ExchangeService exchangeService = WebApplicationContextUtils
                .getRequiredWebApplicationContext( getServletContext() ).getBean( ExchangeService.class );

        try {
            exchangeService.save(phone, express, address, consignee, telNum, imei, pCode, ErpInfo.EXPRESS_CODE_ZTO, out_trade_no, uid, "2",StrUtils.emptyOrString(total_fee),c_type);
        } catch (Exception e) {
            LogUtil.writeErrorLog("记录订单信息错误！",e);
        }

        resp.getWriter().write(JSONObject.fromObject(resultMap).toString());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}


	public static void main(String[] args) {
		String content = "partner=\"2088301260920203\"&seller_id=\"vip@longfore.net\"&out_trade_no=\"030711051011060\"&subject=\"趣键智能膜\"&body=\"趣键智能膜\"&notify_url=\"http://192.168.8.221/AppService/servlet/alipayNotify\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&return_url=\"m.alipay.com\"";
		content = content.substring(0,content.indexOf("notify_url") - 1) + "&total_fee=" + "\"" + 0.01 + "\"" + content.substring(content.indexOf("notify_url") -1);
		System.out.println(content);
	}
}
