package com.ly.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.User;

/**
 * 权限管理
 * 
 * @author 李国鑫
 * @date 创建时间：2016年11月22日 上午11:18:57
 */
public class PrivilegeFilter implements Filter {
	private List<String> adminList = new ArrayList<String>();
	private List<String> userList = new ArrayList<String>();

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 1、获取用户访问的url地址
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getRequestURI();
		// 2、判断当前访问的资源是否需要权限控制
		if (userList.contains(url) || adminList.contains(url)) {// 需要权限控制
			// 3、判断当前用户是否登录
			if (req.getSession(false) == null
					|| req.getSession().getAttribute("user") == null) {
				// 没有登录,给予提示，并跳转到登录页面
				resp.getWriter().write("当前资源需要权限，请先登录！");
				resp.setHeader("Refresh", "3;url=" + req.getContextPath()
						+ "/login.jsp");
			} else {// 已登录
					// 获取session中的用户的角色
				String role = ((User) req.getSession().getAttribute("user")).getRole();
				// 4、判断权限是否匹配
				if ("user".equals(role) && userList.contains(url)) {
					chain.doFilter(request, response);
				} else if ("admin".equals(role) && adminList.contains(url)) {
					chain.doFilter(request, response);
				} else {
					resp.getWriter().write("对不起，您权限不能访问该资源，权限不足！");
				}
			}
		} else {// 不需要权限控制
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			String line = "";
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					filterConfig.getServletContext().getRealPath(
							"/WEB-INF/user.txt"))));
			while ((line = reader.readLine()) != null) {
				userList.add(line);
			}
			reader = new BufferedReader(new FileReader(new File(filterConfig
					.getServletContext().getRealPath("/WEB-INF/admin.txt"))));
			while ((line = reader.readLine()) != null) {
				adminList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
