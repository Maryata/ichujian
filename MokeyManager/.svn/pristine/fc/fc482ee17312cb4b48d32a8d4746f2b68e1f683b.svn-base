package com.org.mokey.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.org.mokey.system.entiy.TSysUser;

public class LoginFilter extends OncePerRequestFilter {

	private static Logger log = Logger.getLogger(LoginFilter.class);
	private String[] loginPages;

	private String[] forbiddenPages;
	private String rootPath;

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
			// 是否登录 或session过期
			TSysUser tempUser = null;
			if (request.getSession() != null) {
				tempUser = (TSysUser) request.getSession().getAttribute(
						AbstractAction.AP_SYS_SESSION_LOGON_USER);
				if (tempUser != null) {
					// MDC是LOG4J的一种定制LOG参数的数据模型. MDC中的数据可以配置显示在LOG文件里.
					MDC.put("usc", tempUser.getUserCode());
					MDC.put("name", tempUser.getUserName());
				}
			}
			log.info("reqURL =" + reqUrl);
			if (tempUser != null) {
				filterChain.doFilter(request, response);
				log.debug(" loginFilter pass real!");
				return;
			}
			/*
			 * if(true){ filterChain.doFilter(request, response); return; }
			 */
			// 是否为默认可连接的(登录页面)
			boolean isLoginPage = isSpecil(reqUrl);
			boolean isRootPath = reqUrl.equalsIgnoreCase(rootPath);
			if (isLoginPage || isRootPath) {
				filterChain.doFilter(request, response);
				log.debug(" loginFilter pass.");
			} else {// 返回登录页面
					// log.error("loginFilter refuse,isLoginPage="+isLoginPage+",isRootPath="+isRootPath+",uri:"+request.getRequestURI());
				responseLoginPage(request, response);
				log.debug(" loginFilter false");
			}
		} catch (Exception e) {
			log.error(" login filter error happend", e);
		}

	}
	
	/**
	 * 是否为默认允许的页面或action
	 * 
	 * @param reqUrl
	 */
	private boolean isSpecil(String reqUrl) {
		// 禁止的url
		for (int i = 0; i < forbiddenPages.length; i++) {
			if (reqUrl.indexOf(forbiddenPages[i].trim()) > -1) {
				// log.debug(" fobidden specil is"+forbiddenPages[i]);
				return false;
			}
		}
		for (int i = 0; i < loginPages.length; i++) {
			// 解析*号
			String[] spils = loginPages[i].split("[*]");
			if (spils.length == 1) {// 如果不包括* 就直接比较
				if (reqUrl.indexOf(loginPages[i].trim()) > -1) {
					// log.debug(" specil is"+loginPages[i]);
					return true;
				}// 如果不匹配 继续找
			} else {
				if (checkFlag(reqUrl, spils)) { // 已经匹配
					return true;
				}// 如果不匹配 继续找
			}
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
	 * 返回到登录页面
	 * 
	 * @param response
	 * @throws IOException
	 */
	private static void responseLoginPage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UFT-8\">");
		out.println("</head>");
		out.println("<script language=\"JavaScript\">");
		// out.println("alert(\"您还没有登录，或登录已超时，请重新操作！\");");
		out.println("window.parent.location=\""
				+ AbstractAction.getAbsolutePath(request, "/login.jsp") // 回到登录界面
				+ "\";");
		out.println("</script>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		// log.debug(" loginFilter false");
	}

	public void setLoginPages(String loginPages) {
		if (loginPages != null && loginPages.length() == 0) {
			this.loginPages = new String[] { "" };
		} else {
			this.loginPages = loginPages.split(",");
		}
	}

	public void setForbiddenPages(String forbiddenPages) {
		if (forbiddenPages != null && forbiddenPages.length() == 0) {
			this.forbiddenPages = new String[] { "" };
		} else {
			this.forbiddenPages = forbiddenPages.split(",");
		}
	}

	public void setRootPath(String rootPath) {
		this.rootPath = "/" + rootPath + "/";
	}
	
	public static void main(String[] args) {
		String aa = "http://localhost:8080/applatform//pages/login.jsp";
		String url = "rm/login!.actionh";
		String[] spils = aa.split("[*]");
		log.debug("spils=" + spils.length);
		// log.debug("spils="+spils[1]);
		log.debug(" return ==" + checkFlag(url, spils));

		LoginFilter filter = new LoginFilter();

		String initStr = "/pages/login.jsp,/login!*.action,/login!listLogon.action,/login!listIOTypes.action,/login!getId.action,/login!change.action,/login!listPatterns.action";
		filter.setLoginPages(initStr);
		log.debug(" filter?=" + filter.isSpecil(aa));

	}

}
