package net.jeeshop.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class EncodeFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//设置不缓存
		if(response instanceof HttpServletResponse){
			//System.out.println("set no-cache-----------------");
			HttpServletResponse res = (HttpServletResponse)response;
			res.setHeader("Cache-Control","no-store"); 
			res.setHeader("Pragrma","no-cache"); 
			res.setDateHeader("Expires",0); 
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
