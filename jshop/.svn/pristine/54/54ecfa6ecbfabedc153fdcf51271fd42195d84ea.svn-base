package net.jeeshop.web.action.front.unionpay;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.sms.SMSUtil;
import net.jeeshop.core.util.ApDateTime;
import net.jeeshop.core.util.PathUtil;
import net.jeeshop.core.util.SignUtil;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.front.order.OrderService;
import net.jeeshop.services.front.unionpaylog.UnionPayLogService;
import net.jeeshop.services.front.unionpaylog.bean.UnionPayLog;
import net.jeeshop.services.manage.sms.bean.Sms;
import net.jeeshop.web.util.RequestHolder;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/unionPay")
public class UnionPayAction {

	private Logger logger = LoggerFactory.getLogger(UnionPayAction.class);
	
	private static final String METHOD_FRONTRCV = "frontRcvResponse";
	private static final String METHOD_BACKRCV = "backRcvResponse";
	private static final String SIGNATUREFIELD = "Signature";
	// 商户私有域
	private static final String MERRESV = "MerResvZD";
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UnionPayLogService unionPayLogService;
	
	/**
	 * 获取商户ID
	 * @param payFlag
	 * @return
	 */
	public static String getMerId(String payFlag){
		String merId = "";
		// merId="000001509184276";//test
		if("2".equals(payFlag)){
			merId = "531101510299984";// 无卡
		}else if("3".equals(payFlag)){
			merId = "531101510291487";// B2C
		}
		return merId;
	}
	
	/**
	 * 封装参数，生成签名，发送
	 * @param req
	 * @param resp
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/signNSend")
	public String signNSend(HttpServletRequest req, HttpServletResponse resp){
		logger.error("[银联-PC]frontRcvResponse >>> 前台接收报文返回开始>>>>>>>>>>>>>>>");
		
		/** 银联账户 */
		String payFlag = (String) req.getSession().getAttribute("payFlag");// 获取银联账号类型（无卡/B2C）
		String merId = getMerId(payFlag);
		/** 订单号(订单序列号) */
		String orderId = (String) req.getSession().getAttribute("orderId");
		/** 订单金额 */
		String orderAmt = "1";
		String amount = (String) req.getSession().getAttribute("amount");
		
		if(StrUtils.isEmpty(payFlag) ||StrUtils.isEmpty(orderId) || StrUtils.isEmpty(amount)){
			logger.error("signNSend amount is null, redirect to orders! ");
			return "redirect:/account/orders";
		}
		
		DecimalFormat df = new DecimalFormat("0");
		orderAmt = df.format(Double.valueOf(amount) * 100);
//		orderAmt = "1";
		
		//日期时间
		String tranDate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YYYYMMDD);
		String tranTime = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_HHMMSS);
		
		//支付版本号
		String version ="20140728";
		
		// 交易类型
		String tranType ="0001";
		
		// 业务类型
		String busiType ="0001";
		
		String project = SystemManager.getInstance().getProperty("projectUrl");
		// 前台接收地址
		String pageReturnUrl = project + "/unionPay/pgReturn";
		// 后台接收地址
		String bgReturnUrl = project + "/unionPay/bgReturn";
		
		// 收单机构号
		String acqCode = "000000000000014";
		
		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		data.put("MerId", merId);// 商户号
		data.put("MerOrderNo", orderId);// 订单号
		data.put("OrderAmt", orderAmt);// 订单金额
		data.put("TranDate", tranDate);// 交易日期
		data.put("TranTime", tranTime);// 交易时间
		data.put("TranType", tranType);// 交易类型
		data.put("BusiType", busiType);// 业务类型
		data.put("Version", version);// 版本号
		data.put("MerPageUrl", pageReturnUrl);// 页面应答接收URL
		data.put("MerBgUrl", bgReturnUrl);// 后台应答接收URL
		data.put("MerResv", MERRESV);// 商户私有域(英文或数字，不超过60字节，ChinaPay将原样返回给商户系统该字段填入的数据)
		data.put("AcqCode", acqCode);// 收单机构号
		data.put("CurryNo", "CNY");// 交易币种(3位，默认为CNY 人民币)
		data.put("AccessType", "0");// 接入类型(1位数字，默认:0,表示接入类型[0:以商户身份接入；1:以机构身份接入])
		
		/*
		data.put("SplitType", "");// 分账类型(4位数字,不分账不填写此域；填写规则[0001:实时分账;0002:延时分账])
		data.put("SplitMethod", "");// 分账方式(1位数字,不分账不填写此域；填写规则[0:按金额分账;1:按比例分账])
		data.put("MerSplitMsg", "");// 分账公式(不分账不填写此域；填写规则[商户号或者费用类型^金额或者占用比例;商户号或者费用^金额或者占用比例])
		data.put("BankInstNo", "");// 支付机构号(15位，可以为空)
		data.put("PayTimeOut", "");// 支付超时时间(5位，可以为空 ，单位：分钟)
		data.put("TimeStamp", "");// 商户系统时间戳(13位，可以为空 ，单位：毫秒)
		data.put("RemoteAddr", "");// 商户客户端IP(可以为空 ，商户开通防钓鱼时必填，单位：分钟)
		data.put("CommodityMsg", "");// 商品信息描述
		data.put("trans_BusiId", "");// 业务ID(可以为空,需要在chinapay开通业务Id编号)
		data.put("trans_P1", "");// 参数1(可以为空,商户传输此业务下的参数值)
		data.put("trans_P2", "");// 参数2
		*/
		
		//商户签名
		String signature = SignUtil.sign(data);
		data.put(SIGNATUREFIELD, signature);
		
		for(Map.Entry<String, String> entry:data.entrySet()){
			req.getSession().setAttribute(entry.getKey(), entry.getValue());
		}
		
		// 拼接form表单
		String html = SignUtil.createHtml(PathUtil.getValue("pay_url"), data);
		resp.setCharacterEncoding("UTF-8");
		try {
			resp.getWriter().write(html);
		} catch (IOException e) {
			logger.error("[银联-PC] send data failed! e : " + e);
		}
		logger.info("signNSend >>> 银联参数签名结束");
		return null;
	}
	
	/**
	 * 前台回调
	 * @param req
	 * @param resp
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/pgReturn")
	public String pgReturn(HttpServletRequest req, HttpServletResponse resp){
		logger.info("[银联-PC]pgReturn >>> 前台回调开始");
		try {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			String result = "";
			String flag = "";
			//解析 返回报文
			@SuppressWarnings("unchecked")
			Enumeration<String> requestNames = req.getParameterNames();
			Map<String, String> resultMap = new HashMap<String, String>();
			if(!requestNames.hasMoreElements()){
				result = "回调参数为空";
				flag = "1";
				return "redirect:/order/paySuccess.html";
			}
			boolean wrongMerResv = false;
			while(requestNames.hasMoreElements()){
				
				String name = requestNames.nextElement();
				String value = req.getParameter(name);
				// 前台无需解码
//				value = URLDecoder.decode(value, "UTF-8");
				if("MerResv".equals(name) && !MERRESV.equals(value)){
					// 如果返回的“商户私有域”与发送的不同，通知管理员
					wrongMerResv = true;
				}
				resultMap.put(name, value);
			}
			String serialId = resultMap.get("MerOrderNo");// 订单序列号
			if(wrongMerResv){
				Sms sms = new Sms();
				sms.setPhone(SystemManager.getInstance().getProperty("unionpay.callbak.notice"));
				sms.setContent("[银联-PC]异常:前台回调中的#商户私有域#有误，请尽快查看。订单序列号：" + resultMap.get("MerOrderNo"));
				SMSUtil.sendSMS(sms);
			}
			logger.error("[银联-PC]前台回调结果:{}",resultMap);
			
			//验证签名
			if(!SignUtil.verify(resultMap)){
				logger.error("[银联-PC]订单[{}]支付异常！！！",serialId);
				result = "订单支付异常，请联系客服！";
				flag = "3";
				Sms sms = new Sms();
				sms.setPhone(SystemManager.getInstance().getProperty("unionpay.callbak.notice"));
				sms.setContent("异常:前台回调时#验证签名#失败，请尽快查看。订单序列号：" + resultMap.get("MerOrderNo"));
				SMSUtil.sendSMS(sms);
				logger.error("[银联-PC]修改订单[{}]支付状态失败！！！",serialId);
			}else{
				if("0000".equals(resultMap.get("OrderStatus"))){// 支付成功
					// 修改订单状态
					boolean boo = orderService.unionPayReq(resultMap);
					if(boo){
						logger.info("[银联-PC]订单[{}]验证签名结果[成功].",serialId);
					}else{
						Sms sms = new Sms();
						sms.setPhone(SystemManager.getInstance().getProperty("unionpay.callbak.notice"));
						sms.setContent("异常:前台回调时修改订单状态失败，请尽快查看。订单序列号：" + resultMap.get("MerOrderNo"));
						SMSUtil.sendSMS(sms);
						logger.error("[银联-PC]修改订单[{}]支付状态失败！！！",serialId);
					}
					flag = "1";
				}else{
					logger.error("[银联-PC]订单[{}]支付异常！！！",serialId);
					result = "订单支付异常，请联系客服！";
					flag = "0";
				}
				
			}
			
			try {
				// 记录回调信息
				logRespose(resultMap, METHOD_FRONTRCV);
			} catch (Exception e) {
				logger.error("[银联-PC] back >>> logRespose failed! e : " + e);
			}
			
			req.getSession().setAttribute("pay_result", result);
			req.getSession().setAttribute("flag", flag);
			
		} catch (Exception e) {
			logger.error("[银联-PC] front callback failed! e : " + e);
		}
		logger.info("[银联-PC]pgReturn >>> 前台回调结束");
		//转到支付成功页面
		return "redirect:/order/paySuccess.html";
	}
	
	/**
	 * 后台回调
	 * @param req
	 * @param resp
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/bgReturn")
	@ResponseBody
	public void bgReturn(HttpServletRequest req, HttpServletResponse resp){
		logger.info("[银联-PC]pgReturn >>> 后台回调开始");
		
		try {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			//解析 返回报文
			@SuppressWarnings("unchecked")
			Enumeration<String> requestNames = req.getParameterNames();
			Map<String, String> resultMap = new HashMap<String, String>();
			boolean wrongMerResv = false;
			while(requestNames.hasMoreElements()){
				String name = requestNames.nextElement();
				String value = req.getParameter(name);
				// 后台需解码
				value = URLDecoder.decode(value, "UTF-8");
				if("MerResv".equals(name) && !MERRESV.equals(value)){
					// 如果返回的“商户私有域”与发送的不同，通知管理员
					wrongMerResv = true;
				}
				resultMap.put(name, value);
			}
			logger.error("[银联-PC]后台回调结果:{}",resultMap);
			String serialId = resultMap.get("MerOrderNo");
			
			if(wrongMerResv){
				Sms sms = new Sms();
				sms.setPhone(SystemManager.getInstance().getProperty("unionpay.callbak.notice"));
				sms.setContent("[银联-PC]异常:后台回调中的#商户私有域#有误，请尽快查看。订单序列号：" + resultMap.get("MerOrderNo"));
				SMSUtil.sendSMS(sms);
			}
			
			//验证签名
			if(!SignUtil.verify(resultMap)){
				Sms sms = new Sms();
				sms.setPhone(SystemManager.getInstance().getProperty("unionpay.callbak.notice"));
				sms.setContent("异常:前台回调时#验证签名#失败，请尽快查看。订单序列号：" + resultMap.get("MerOrderNo"));
				SMSUtil.sendSMS(sms);
				logger.error("[银联-PC]修改订单[{}]支付状态失败！！！",serialId);
			}else{
				if("0000".equals(resultMap.get("OrderStatus"))){// 支付成功
					// 修改订单状态
					boolean boo = orderService.unionPayReq(resultMap);
					if(boo){
						logger.info("[银联-PC]订单[{}]验证签名结果[成功].",serialId);
					}else{
						Sms sms = new Sms();
						sms.setPhone(SystemManager.getInstance().getProperty("unionpay.callbak.notice"));
						sms.setContent("异常:前台回调时修改订单状态失败，请尽快查看。订单序列号：" + resultMap.get("MerOrderNo"));
						SMSUtil.sendSMS(sms);
						logger.error("[银联-PC]修改订单[{}]支付状态失败！！！",serialId);
					}
				}else{
					logger.error("[银联-PC]订单[{}]支付异常！！！",serialId);
				}
				
			}
			
			try {
				// 记录回调信息
				logRespose(resultMap, METHOD_BACKRCV);
			} catch (Exception e) {
				logger.error("[银联-PC] back >>> logRespose failed! e : " + e);
			}
			RequestHolder.getResponse().getWriter().write("success");
		} catch (Exception e) {
			logger.error("[银联-PC] back callback failed! e : " + e);
			try {
				RequestHolder.getResponse().getWriter().write("fail");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		logger.info("[银联-PC]pgReturn >>> 银联后台回调结束");
	}
	
	private void logRespose(Map<String, String> resultMap, String callback){
		try {
			UnionPayLog unionPayLog = new UnionPayLog();
			String merOrderNo = resultMap.get("MerOrderNo");
			unionPayLog.setCallback(callback);
			unionPayLog.setOrderId(merOrderNo);
			unionPayLog.setQueryId(resultMap.get("AcqSeqId"));
			unionPayLog.setRespCode(resultMap.get("OrderStatus"));
			/** 根据回 <调方法名、订单序列号、银联查询号、回调状态> 查询日志是否已经记录*/
			int existLog = unionPayLogService.isExists(unionPayLog);
			if(existLog < 1){
				unionPayLog.setValideData(JSONObject.fromObject(resultMap).toString());
				unionPayLogService.logResponse(unionPayLog);
				logger.error("[银联-PC]logRespose >>> 记录日志成功 by {}, 订单号：{}", callback, merOrderNo);
			}else{
				logger.error("[银联-PC]logRespose >>> 日志已经记录，无需重复！");
			}
		} catch (Exception e) {
			logger.error("[银联-PC]logRespose >>> 记录日志失败！", e);
		}
	}
}
