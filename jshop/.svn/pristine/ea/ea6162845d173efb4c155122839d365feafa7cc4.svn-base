package net.jeeshop.web.action.front.unionpay;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.util.SignUtil;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class PgReturnServlet
 */
public class PgReturnServlet extends HttpServlet {
	Logger log = Logger.getLogger(PgReturnServlet.class);
	private static final long serialVersionUID = 1L;
	
	private static final String VERIFY_KEY = "VERIFY_KEY";
	private static final Map<String, String> dispatchMap = new HashMap<String, String>();
	static{
		//配置个人网银交易转发地址
		dispatchMap.put("0001", "/pay/b2cPayResult.jsp");
		//配置退款交易转发地址
		dispatchMap.put("0401", "/refund/b2cRefundResult.jsp");
		//配置查询交易转发地址
		dispatchMap.put("0502", "/query/b2cQueryResult.jsp");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PgReturnServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("PgReturnServlet.init-- ");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//解析 返回报文
		@SuppressWarnings("unchecked")
		Enumeration<String> requestNames = request.getParameterNames();
		Map<String, String> resultMap = new HashMap<String, String>();
		while(requestNames.hasMoreElements()){
			String name = requestNames.nextElement();
			String value = request.getParameter(name);
//			value = URLDecoder.decode(value, "UTF-8");
			resultMap.put(name, value);
		}
		
		//验证签名
		if(SignUtil.verify(resultMap)){
			resultMap.put(VERIFY_KEY, "success");
		}else{
			resultMap.put(VERIFY_KEY, "fail");
		}
		for(Map.Entry<String, String> entry:resultMap.entrySet()){
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		//转发请求到页面
		String requestDispatcherPath = dispatchMap.get(resultMap.get("TranType"));
		request.getRequestDispatcher(requestDispatcherPath).forward(request,response);
		log.info("PgReturnServlet.end--- ");
	}

}
