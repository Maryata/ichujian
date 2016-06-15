package com.web;

import com.unionpay.acp.demo.DemoBase;

import javax.servlet.*;
import java.io.IOException;

public class CharsetEncodingFilter implements Filter {
	
	private String encodeString;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encodeString);
		response.setContentType("text/html; charset="+ DemoBase.encoding_UTF8);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encodeString=filterConfig.getInitParameter("encoding");
	}
}
