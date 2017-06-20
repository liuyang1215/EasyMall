package com.ly.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.ly.domain.User;
import com.ly.factory.BasicFactory;
import com.ly.service.UserService;

public class AutoLoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		//1.只有未登录的用户才做自动登陆---判断Session是否存在用户信息
		if(req.getSession(false) == null || req.getSession().getAttribute("user") == null){
			//2.只有客户端存在autoLogin Cookie，才做自动登陆
			Cookie[] cs = req.getCookies();
			Cookie findCookie = null;
			if(cs!=null){
				for(Cookie c : cs){
					if("autoLogin".equals(c.getName())){
						findCookie = c;
						break;
					}
				}
			}
			if(findCookie != null){//说明存在autoLogin的Cookie
				//3.判断cookie中用户信息是否正确
				//3.1 从cookie中获取用户名和密码
				String decUp = URLDecoder.decode(findCookie.getValue(), "UTF-8");
				String username = decUp.split(":")[0];
				String password = decUp.split(":")[1];
				//3.2 调用service方法，判断用户密码是否正确
				UserService service = BasicFactory.getFactory().getInstance(UserService.class);
				User user = service.login(username, password);
				if(user != null){//未登录、cookie存在、信息正确
					//4.实现登陆
					req.getSession().setAttribute("user", user);
				}
			}
		}
		//放行
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
		
	}

}
