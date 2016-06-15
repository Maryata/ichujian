package net.jeeshop.web.action.front.alipay;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.pay.alipay.alipayescow.util.AlipayNotify;
import net.jeeshop.services.front.alipay.AliPayService;
import net.jeeshop.services.front.alipay.bean.AliPay;
import net.jeeshop.services.front.order.OrderService;
import net.jeeshop.web.util.RequestHolder;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("alipayAction")
@RequestMapping("/alipay")
public class AlipayAction {
	
	private Logger logger = LoggerFactory.getLogger(AlipayAction.class);
	
	private static final String METHOD_NOTIFY = "alipayNotify";
	private static final String METHOD_RETURN = "alipayReturn";
	
	@Autowired
	private AliPayService aliPayService;
	
	@Autowired
	private OrderService orderService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/alipayNotify")
	@ResponseBody
	// 后台回调
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response){
		logger.error("[支付宝-PC]init alipayNotify--------");
		/**
		 * 需要再支付宝回调方法中 如果支付成功的话，则清空购物车。
		 */
//		request.getSession().setAttribute(FrontContainer.myCart, null);//清空购物车
		
		//获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = RequestHolder.getRequest().getParameterMap();
		logger.error("[支付宝-PC]alipayNotify >>> 同步通知request.getParameterMap()=" + requestParams);
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		logger.error("[支付宝-PC]alipayNotify >>> 同步通知params=" + params);

		try {
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			//退款状态
			//String refund_status = new String(request.getParameter("refund_status").getBytes("ISO-8859-1"),"UTF-8");
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			logger.error("[支付宝-PC]alipayNotify >>> [商户订单号 : {}][支付宝交易号 : {}][交易状态 : {}]", out_trade_no, trade_no, trade_status);
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			logger.error("[支付宝-PC]alipayNotify >>> 同步通知verify_result=" + verify_result);
//			String result = null;
			if (verify_result) {//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
//				response.getWriter().println("trade_status=" + trade_status + "<br />");
				if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
//					response.getWriter().println("买家已付款，等待卖家发货" + "<br />");

					//本系统的业务逻辑处理，把订单更新为已成功付款状态
					if (orderService.alipayNotify(trade_status, null,
							out_trade_no, trade_no)) {
						logger.error("[支付宝-PC]alipayNotify >>> 修改订单[{out_trade_no}]状态为【已付款】成功", out_trade_no);
//						response.getWriter().println("修改订单状态为【已付款】成功" + "<br />");
//						result = "恭喜，您的订单已支付成功！";
					} else {
						logger.error("[支付宝-PC]alipayNotify >>> 修改订单[{out_trade_no}]状态为【已付款】失败", out_trade_no);
//						result = "订单支付异常，请联系客服！";
//						response.getWriter().println("修改订单状态为【已付款】失败！，请检查" + "<br />");
					}
				}

				//该页面可做页面美工编辑
//				response.getWriter().println("验证成功<br />");

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

				//////////////////////////////////////////////////////////////////////////////////////////
			} else {
				logger.error("[支付宝-PC]alipayNotify >>> 后台验证失败,订单[{out_trade_no}]", out_trade_no);
				//该页面可做页面美工编辑
//				response.getWriter().println("验证失败");
//				result = "订单支付异常，请联系客服！";
			}
			
			/** 记录支付完成之后的响应信息 begin */
			
			logRespose(AlipayAction.METHOD_NOTIFY,out_trade_no,trade_no,trade_status,params);
			
			/** 记录支付完成之后的响应信息 end */
			response.getWriter().write("success");
//			RequestHolder.getRequest().getSession().setAttribute("pay_result", result);
			//转到首页
//			response.sendRedirect("/order/paySuccess.html");
		} catch (Exception e) {
			logger.error("[支付宝-PC]alipayNotify >>> 系统异常, e : ", e);
			try {
				response.getWriter().write("fail");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/alipayReturn")
	//@ResponseBody
	public String alipayReturn(HttpServletRequest request, HttpServletResponse response){
		logger.error("[支付宝-PC]init alipayReturn--------");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		logger.error("[支付宝-PC]alipayReturn >>> params="+params);
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		try {
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			//退款状态
			String refund_status = null;
			if(StringUtils.isNotBlank(request.getParameter("refund_status"))){
				refund_status = new String(request.getParameter("refund_status").getBytes("ISO-8859-1"),"UTF-8");
			}
			logger.error("[支付宝-PC]alipayReturn >>> [商户订单号 : {}][支付宝交易号 : {}][交易状态 : {}]", out_trade_no, trade_no, trade_status);
			
			/** 记录支付完成之后的响应信息 begin */
			logRespose(AlipayAction.METHOD_RETURN,out_trade_no,trade_no,trade_status,params);
			/** 记录支付完成之后的响应信息 end */
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			String result = "";
			String flag = "";
			if(AlipayNotify.verify(params)){//验证成功
				//本系统的业务逻辑处理，把订单更新为已成功付款状态
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				logger.error("[支付宝-PC]alipayReturn >>> 异步验证成功，订单[{}]!", out_trade_no);
				if(StringUtils.isNotBlank(trade_status)){
					if(orderService.alipayNotify(trade_status,refund_status,out_trade_no,trade_no)){
//						response.getWriter().println("success");	//请不要修改或删除
						result = "支付成功";
						flag = "1";
					}else{
//						response.getWriter().println("success_fail");	//请不要修改或删除
						result = "支付失败";
						flag = "0";
					}
//					if(trade_status.equals("WAIT_BUYER_PAY")){
					//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
					
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//orderService.alipayNotify(trade_status,refund_status,out_trade_no,trade_no);
						//out.println("success");	//请不要修改或删除
//						result = "支付成功";
//						flag = "1";
//					} else if(trade_status.equals("WAIT_SELLER_SEND_GOODS")){
					//该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
					
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//orderService.alipayNotify(trade_status,refund_status,out_trade_no,trade_no);
						//out.println("success");	//请不要修改或删除
//						result = "支付成功";
//						flag = "1";
//					} else if(trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")){
					//该判断表示卖家已经发了货，但买家还没有做确认收货的操作
					
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//orderService.alipayNotify(trade_status,refund_status,out_trade_no,trade_no);
//						response.getWriter().println("success");	//请不要修改或删除
//					} else if(trade_status.equals("TRADE_FINISHED")){
					//该判断表示买家已经确认收货，这笔交易完成
					
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//orderService.alipayNotify(trade_status,refund_status,out_trade_no,trade_no);
						//out.println("success");	//请不要修改或删除
//					}
//					else {
						//out.println("success");	//请不要修改或删除
//					}
				}
				
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				logger.error("[支付宝-PC]alipayReturn >>> 异步验证失败，订单[{}]", out_trade_no);
//				response.getWriter().println("fail");
				flag = "0";
			}
			RequestHolder.getRequest().setAttribute("pay_result", result);
			RequestHolder.getRequest().setAttribute("flag", flag);
			//转到首页
			//response.sendRedirect("/order/paySuccess.html");
		} catch (Exception e) {
			logger.error("[支付宝-PC]alipayReturn >>> 系统异常");
		}
		return "paySuccess";
	}
	
	private void logRespose(String method, String out_trade_no, String trade_no, String trade_status, Map<String, String> params){
		try {
			AliPay aliPay = new AliPay();
			aliPay.setCallback(method);
			aliPay.setOutTradeNo(out_trade_no);
			aliPay.setTradeNo(trade_no);
			aliPay.setTradeStatus(trade_status);
			int existLog = aliPayService.selectExistsLog(aliPay);
			if(existLog < 1){
				aliPay.setParams(JSONObject.fromObject(params).toString());
				aliPay.setDate(new Date());
				aliPayService.logResponse(aliPay);
				logger.error("[支付宝-PC]logRespose >>> 记录日志成功 by {}", method);
			}else{
				logger.error("[支付宝-PC]logRespose >>> 日志已经记录，不需要再次记录！by {}", method);
			}
			
		} catch (Exception e) {
			logger.error("[支付宝-PC]logRespose >>> 记录日志异常！", e);
		}
	}
}
