package com.qujian;

import com.qujian.service.DownloadRecService;
import com.qujian.service.impl.DownloadRecServiceImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@SuppressWarnings("serial")
public class openDownloadPage extends HttpServlet {

	protected  Logger log = (Logger.getLogger(getClass()));

	/**
	 * Constructor of the object.
	 */
	public openDownloadPage() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cilentIp = request.getHeader("x-forwarded-for");// 获取真实的IP地址
		if (cilentIp == null || "".equals(cilentIp)) {
			cilentIp = request.getRemoteAddr();
		}
		MDC.put("clientIp", cilentIp);

		String licenseCn = request.getParameter("lc");
		log.debug("licenseCn: "+licenseCn);

		/** 2016-01-04 新增：记录下载信息 begin */
		try {
			// 下载记录Service
			DownloadRecService downloadRecService = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext())
					.getBean( DownloadRecServiceImpl.class );
			downloadRecService.openRec(cilentIp, licenseCn);
		} catch (Exception e) {
			log.error("记录下载信息出错！", e);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
