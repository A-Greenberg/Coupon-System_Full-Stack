package com.services.coupons;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter
public class MySessionFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*System.out.println("Inside filter");*/
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		
		
		String path = req.getRequestURL().toString();
		// check the URL - if it contains Login it means session is ok to be missing
		// if NOT Login - then session must be available
		if(!path.contains("login"))
		{
			HttpSession session = req.getSession(false);
			if(session == null)
			{
				/*System.out.println("inside null");*/
				// when in async response we cannot redirect - instead we will issue
				// an error code that will be translated in the client response to redirect
				//res.sendRedirect("Error.html");
				res.sendError(400);
				return;
			}
		}
		/*System.out.println("Before chain");*/
		// continue the chain of filters (if exist) and will continue to the servlet
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
