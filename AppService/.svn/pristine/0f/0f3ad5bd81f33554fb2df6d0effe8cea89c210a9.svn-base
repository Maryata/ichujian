package com.alipayH5.servlet;

import com.alipayH5.util.AlipayNotify;
import com.sys.service.ExchangeService;
import com.sys.util.StrUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/8/8.
 */
public class FreeMembraneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//获取支付宝GET过来反馈信息
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
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
            System.out.println("name="+name+",valueStr="+valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);
        String result = null;
        String uid="";
        //Logger.getLogger(getClass()).info(verify_result+"验签");
        WebApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        ExchangeService exchangeService = app.getBean(ExchangeService.class);
        System.out.println(verify_result+"验证===================================================================");
        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //修改订单状态---------
                Map<String,String> infoMap = exchangeService.update(out_trade_no);
                List<Map<String,Object>> uidMap= exchangeService.selectOrder(out_trade_no);
                uid = StrUtils.emptyOrString(uidMap.get(0).get("C_UID"));
                if("SUCCESS".equals(infoMap.get("return_code"))){//success ok
                    System.out.println("支付成功===================================================================");
                    /*out.println("【已付款】成功" + "<br />");*/
                    result = "支付成功！";
                    response.sendRedirect("http://www.ichujian.com/webView/rebate/ok.html?id="+uid);
                }else{//fail no
                    System.out.println("支付失败===================================================================");
                    result = "订单支付异常，请联系客服！";
                   /* out.println("【已付款】失败！，请检查" + "<br />");*/
                    response.sendRedirect("http://www.ichujian.com/webView/rebate/fail.html?id="+uid);
                }
            }

            //该页面可做页面美工编辑
            //out.println("验证成功<br />");
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            //java.net.URI uri = new java.net.URI("https://www.ichujian.com/webView/rebate/ok.html?id="+uid);
            //java.awt.Desktop.getDesktop().browse(uri);
           // response.sendRedirect("http://www.ichujian.com/webView/rebate/ok.html?id="+uid);
            //////////////////////////////////////////////////////////////////////////////////////////
        }else{
            System.out.println("验证失败===================================================================");
            //该页面可做页面美工编辑
            //out.println("验证失败");
            result = "订单支付异常，请联系客服！";
            response.sendRedirect("http://www.ichujian.com/webView/rebate/fail.html?id="+uid);
        }
    }
}
