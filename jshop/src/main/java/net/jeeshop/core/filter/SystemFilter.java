package net.jeeshop.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.AddressUtils;
import net.jeeshop.core.util.Encodes;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * User: Ambitor springMVC拦截器 判断session中用户是否过期
 * Date: 13-6-27
 * Time: 下午7:31
 * To change this template use File | Settings | File Templates.
 *
 * @author giles
 */

public class SystemFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(SystemFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String url = httpRequest.getRequestURL().toString();
        //System.out.println("req:"+url);
        if(url.endsWith(".ftl")||url.endsWith(".js")||url.endsWith(".css") || url.indexOf("/manage/browser")>-1){
        	filterChain.doFilter(servletRequest, servletResponse);
        	return;
        }
        MDC.put("clientIp", AddressUtils.getIp(httpRequest));
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(true);
        if(url.indexOf("/manage/")==-1 || url.indexOf("/manage/user/login")>-1 ){
        	filterChain.doFilter(servletRequest, servletResponse);
        	return;
        }
//        String[] strs = ProsReader.getString("INDICATION_APP_NAME").split("\\|");
//        if (strs != null && strs.length > 0) {
//            for (String str : strs) {
//                if (url.indexOf(str) >= 0) {
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    return;
//                }
//            }
//        }
        User user = (User) session.getAttribute("manage_session_user_info");
        if (user == null) {
            boolean isAjaxRequest = isAjaxRequest(httpRequest);
            if (isAjaxRequest) {
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),"您已经太长时间没有操作,请刷新页面");
            }
            String urlLogin = httpRequest.getContextPath()+"/manage/user/login?returl="+Encodes.urlEncode(url);
            //System.out.println("urlLogin:"+urlLogin);
            httpResponse.sendRedirect(urlLogin);
            return;
        }else{        	
        	MDC.put("name", user.getNickname());
        }
        filterChain.doFilter(servletRequest, servletResponse);
        //MDC.clear();
        return;
    }

    /**
     * 判断是否为Ajax请求
     *
     * @param request HttpServletRequest
     * @return 是true, 否false
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        //return request.getRequestURI().startsWith("/api");
        String requestType = request.getHeader("X-Requested-With");
        return requestType != null && requestType.equals("XMLHttpRequest");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }


}
