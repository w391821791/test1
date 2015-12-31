package com.zty.edu_equip.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
	
	private String encoding=null;

	public void init(FilterConfig filterConfig) throws ServletException {
		String  encoding=filterConfig.getInitParameter("encoding");
		if(encoding==null){
			this.encoding="UTF-8";
		}else{
			this.encoding=encoding;
		}
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding(encoding);
		resp.setCharacterEncoding(encoding);
		chain.doFilter(req, resp);

	}

	public void destroy() {
		System.out.println("EncodingFilter destroy .....");
	}
}
