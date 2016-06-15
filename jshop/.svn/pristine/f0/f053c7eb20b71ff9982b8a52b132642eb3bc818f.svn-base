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
import net.jeeshop.core.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class SignServlet
 */
public class SignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String transResvered = "trans_";
	private static final String cardResvered = "card_"; 
	private static final String transResveredKey = "TranReserved";
	private static final String cardResveredKey = "CardTranData"; 
	private static final String signatureField = "Signature";
	
	private static final Map<String, String> dispatchMap = new HashMap<String, String>();
	static{
		//配置个人网银交易转发地址
		dispatchMap.put("0001", "/unionpay/b2cPaySend.jsp");
		dispatchMap.put("0004", "/unionpay/b2cPaySend.jsp");
		//配置退款交易转发地址
		dispatchMap.put("0401", "/unionpay/b2cRefundSend.jsp");
		//配置查询交易转发地址
		dispatchMap.put("0502", "/unionpay/b2cQuerySend.jsp");
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		@SuppressWarnings("unchecked")
		Enumeration<String> requestNames = request.getParameterNames(); 
		//交易扩展域信息
		JSONObject transResvedJson = new JSONObject();
		//有卡信息域信息-需要签名加密
		JSONObject cardInfoJson = new JSONObject();
		
		Map<String, Object> sendMap = new HashMap<String,Object>();
		
		try {
			while(requestNames.hasMoreElements()){
				String key = requestNames.nextElement();
				String value = request.getParameter(key);
				
				//过滤空值
				if(StringUtil.isEmpty(value)){
					continue;
				}
				
				if(key.startsWith(transResvered)){
					//组装交易扩展域
					key = key.substring(transResvered.length());
					transResvedJson.put(key, value);
				}else if(key.startsWith(cardResvered)){
					//组装有卡交易信息域
					key = key.substring(cardResvered.length());
					cardInfoJson.put(key, value);
				}else{
					sendMap.put(key, value);
				}
			}
			String transResvedStr = null;
			String cardResvedStr = null;
			if(cardInfoJson.length()!=0)
				cardResvedStr=cardInfoJson.toString();
			if(transResvedJson.length()!=0)
				transResvedStr = transResvedJson.toString();
			//敏感信息加密处理
			if(StringUtil.isNotEmpty(cardResvedStr)){
				String MerId = request.getParameter("MerId");
				cardResvedStr = SignUtil.decryptData(cardResvedStr,MerId);
				sendMap.put(cardResveredKey, cardResvedStr);
			}
			if(StringUtil.isNotEmpty(transResvedStr)){
				sendMap.put(transResveredKey, transResvedStr);
			}
			//商户签名
			String signature = SignUtil.sign(sendMap);
			sendMap.put(signatureField, signature);
			
			String tranType = (String)sendMap.get("TranType");
			String requestDispatcherPath = dispatchMap.get(tranType);

			//默认支付页面
			requestDispatcherPath = StringUtil.isEmpty(requestDispatcherPath)?dispatchMap.get("0001"):requestDispatcherPath;
			
			System.out.println(sendMap);
			for(Map.Entry<String, Object> entry:sendMap.entrySet()){
				request.setAttribute(entry.getKey(), entry.getValue());
			}
			
			request.getRequestDispatcher(requestDispatcherPath).forward(request, response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
