package com.alipay.servlet;

import com.alipay.util.AlipayNotify;
import com.sys.ekey.shop.service.ShopService;
import com.unionpay.acp.sdk.LogUtil;
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

/**
 * Created by Maryn on 2016/4/15.
 */
public class ShopNotify extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LogUtil.writeLog("[e键商城-支付宝]接收后台通知开始");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = req.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号	String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号	String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        LogUtil.writeLog("------params--->>>>" + params);
        String trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");


        /**
         *      out_trade_no	商户网站唯一订单号	  String(64)	对应商户网站的订单系统中的唯一订单号，非支付宝交易号。
         *                                                      需保证在商户网站中的唯一性。是请求时对应的参数，原样返回。	  082215222612710
         trade_no	    支付宝交易号	      String(64)	该交易在支付宝系统中的交易流水号。最短16位，最长64位。		  2013082244524842
         notify_time	    通知时间	          Date	        通知的发送时间。格式为yyyy-MM-dd HH:mm:ss。	      	      2013-08-22 14:45:24
         notify_type	    通知类型	          String	    通知的类型。		                                      trade_status_sync
         notify_id	    通知校验ID	      String	    通知校验ID。		                                      64ce1b6ab92d00ede0ee56ade98fdf2f4c
         subject	        商品名称	          String(128)	商品的标题/交易标题/订单标题/订单关键字等。
         它在支付宝的交易明细中排在第一列，对于财务对账尤为重要。
         是请求时对应的参数，原样通知回来。		                  测试
         payment_type	支付类型	          String(4)	    支付类型。默认值为：1（商品购买）。		                  1
         trade_status	交易状态	          String	    交易状态，取值范围请参见“交易状态”。		                  TRADE_SUCCESS
         buyer_id	    买家支付宝用户号	  String(30)	买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字。 2088602315385429
         buyer_email	    买家支付宝账号	      String(100)	买家支付宝账号，可以是Email或手机号码。		              dlwdgl@gmail.com
         total_fee	    交易金额	          Number	    该笔订单的总金额。请求时对应的参数，原样通知回来。		      1.00
         gmt_create	    交易创建时间	      Date	        该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss。		      2013-08-22 14:45:23
         gmt_payment	    交易付款时间	      Date	        该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss。		  2013-08-22 14:45:24
         */


        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)){//验证成功
            LogUtil.writeLog("验签成功...");
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            ShopService shopService = WebApplicationContextUtils
                    .getRequiredWebApplicationContext( getServletContext() ).getBean( ShopService.class );
            LogUtil.writeLog("trade status -->" + trade_status);
            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的


                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                resp.getWriter().write("success");	//请不要修改或删除
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
//                String out_trade_no = params.get("out_trade_no");


                // 更新订单支付状态
                try {
                    shopService.updatePayStatus(params, "2","1");//(参数,支付类型,支付状态)支付
                } catch (Exception e) {
                    LogUtil.writeErrorLog("[e键商城]更新订单信息错误！", e);
                }
                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                resp.getWriter().write("success");	//请不要修改或删除

            }else if(trade_status.equals("TRADE_CLOSED")){
                // 更新订单支付状态
                try {
                    shopService.updatePayStatus(params, "2","3");//(参数,支付类型,支付状态)交易关闭
                } catch (Exception e) {
                    LogUtil.writeErrorLog("[e键商城]更新订单信息错误！", e);
                }

                resp.getWriter().write("fail");
            }



            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            LogUtil.writeLog("验签失败...");
            resp.getWriter().write("fail");
        }
        LogUtil.writeLog("[e键商城-支付宝]接收后台通知结束");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }


}
