package com.sys.commons;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sys.util.StrUtils;
import com.sys.util.encrypt.ParameterEncryptor;

public class LoginFilter extends OncePerRequestFilter {
	
	//例外地址;
	private static String [] NO_SIGN_URLS = new String[]{"webInterface_*","h5Gamecollection*","userNotify","campusActivities","callAuth*","pushCallInfo*","voiceValidateCode*","voiceRecCallback*"};

	private static Logger log = Logger.getLogger(LoginFilter.class);

	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// 记录客户端IP
			String cilentIp = request.getHeader("x-forwarded-for");// 获取真实的IP地址
			if (cilentIp == null || "".equals(cilentIp)) {
				cilentIp = request.getRemoteAddr();
			}
			MDC.put("clientIp", cilentIp);
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Expires", "Thu,01 Jan 1970 00:00:01 GMT");
			String reqUrl = request.getRequestURI();
			log.info("reqURL =" + reqUrl);
			//例外认证
			boolean isSpecilPage = isSpecil(reqUrl);
     		log.info("isSpecilPage =" + isSpecilPage);
			//验证;拦截
			if(!isSpecilPage && !this.authRequest(request, response)){
				return;
			}else{
				filterChain.doFilter(request, response);
				log.debug(" loginFilter pass real!");
			}
		} catch (Exception e) {
			log.error(" login filter error happend", e);
		}
	}
	
	/**
	 * 例外认证
	 * @param reqUrl
	 * @return
	 */
	private boolean isSpecil(String reqUrl){
		for(int i=0;i<NO_SIGN_URLS.length;i++){
			String[] spils = NO_SIGN_URLS[i].split("[*]");
			if (spils.length == 1) {
				if(reqUrl.indexOf(spils[0])>-1){
					return true;
				}
			}else{
				if(checkFlag(reqUrl,spils)){
					return true;
				}
			}
		}
		
		if(reqUrl.indexOf( "weixin" ) != -1) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 输入" http://localhost:8080/applatform/login!XXXXX.action" 输入" /login!" 和
	 * ".action" 确认第一个输入否包含后两个的。 同时 后一个（.action）在前一个（" /login!"）的后面
	 * 
	 * @param reqUrl
	 * @param spils
	 * @return
	 */
	private static boolean checkFlag(String reqUrl, String[] spils) {
		int befortInt = -1;
		for (int j = 0; j < spils.length; j++) {
			int teInt = reqUrl.indexOf(spils[j]);
			if (teInt <= befortInt) {
				return false;
			} else {
				befortInt = teInt;
				// log.debug(" next location:"+befortInt);
			}
		}
		return true;
	}
	
	/**
	 * 请求验证;
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean authRequest(HttpServletRequest request,
			HttpServletResponse response){
		boolean isAuth = false;
		//
		try {
//			log.debug("mm:"+request.getParameterMap());
			String msg = "";
			String sign = request.getParameter("sign");

			if(StrUtils.isNotEmpty(sign)){
				String key = ParameterEncryptor.encrypt(request);
				log.info("sign: "+key);
				if(sign.equals(key)){
					isAuth = true;
				}else{
					log.debug("key:"+key);
					msg = "sign faild";
				}
			}else{
				msg = "sign param is null";
			}
			//验证不通过;
			if(!isAuth){
				JSONObject ret = new JSONObject();
				ret.put("status", "N");
				ret.put("info", "9999");
				AbstractAction.writeToResponse(response,ret.toString());
				log.info(msg);
			}
		} catch (Exception e) {
			log.error("authRequest failed,",e);
			isAuth = false;
		}
		return isAuth;
	}

}
