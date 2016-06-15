package net.jeeshop.web.action.front.unionpay;

import java.io.IOException;
import java.net.URLDecoder;
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
 * Servlet implementation class BgReceiveServlet
 */
public class BgReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(BgReturnServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BgReturnServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		log.info("BgReturnServlet.init-- ");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//解析 返回报文
		@SuppressWarnings("unchecked")
		Enumeration<String> requestNames = request.getParameterNames();
		Map<String, String> resultMap = new HashMap<String, String>();
		while(requestNames.hasMoreElements()){
			String name = requestNames.nextElement();
			String value = request.getParameter(name);
			value = URLDecoder.decode(value, "UTF-8");
			resultMap.put(name, value);
		}
		
		//验证签名
		if(SignUtil.verify(resultMap)){
			response.getWriter().write("success");
		}else{
			response.getWriter().write("fail");
		}
		log.info("BgReturnServlet.end-- ");
	}
}
