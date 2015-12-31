package com.zty.edu_equip.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.po.serverbase.BaseDao;
import com.po.serverbase.PermissionSuit;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.entity.UserInfo;

public class PowerFilter implements Filter
{

	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
	        FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) arg0;
		String url = request.getRequestURI().split("\\/")[request
		        .getRequestURI().split("\\/").length - 1];
		System.out.println("zzxurl="+url);
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (url.equals("Util_login.action"))
		{
			chain.doFilter(arg0, arg1);
		}
		else if (url.equals("Redirect_redirect.action"))
		{
			chain.doFilter(arg0, arg1);
		}
		else if (null != user)
		{
			String power=(String) session.getAttribute("userPower");
			String [] arr=power.split(",");
			List powerlist=new ArrayList();
			for (String str : arr) {
				powerlist.add(str);
			}
			try
			{
				if (!PermissionSuit.getInstance().checkPermissionSuit(url,
				        powerlist, false))
					request.getRequestDispatcher("nopower.jsp").forward(
					        request, response);
				else
					chain.doFilter(arg0, arg1);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			if(url.indexOf("Moblie")!=-1){
				chain.doFilter(arg0, arg1);
			}else{
				request.getRequestDispatcher("error.jsp")
				        .forward(request, response);
			}
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		// TODO Auto-generated method stub

	}

}
