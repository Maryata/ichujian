package com.sys.commons;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by vpc on 2016/2/24.
 */
public class ServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String target = request.getRequestURI();
        int index = target.indexOf("/servlet/");
        target = target.lastIndexOf("?") > 0 ? target.substring(
                target.lastIndexOf("/") + 1,
                target.lastIndexOf("?") - target.lastIndexOf("/")) : target
                .substring(target.lastIndexOf("/") + 1);

        if (index != -1) {
            // request.getRequestURI()格式应该形如：/st/servlet/jqueryAjax，
            // 其中st是项目名，servlet是所有servlet都增加的前缀，用于能够判断出是servlet。
            // if只判断请求uri是否包含/servlet/，如果包含则处理；
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(target);
            requestDispatcher.forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
