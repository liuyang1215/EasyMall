package com.ly.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.User;
import com.ly.factory.BasicFactory;
import com.ly.factory.UserServiceFactory;
import com.ly.service.UserService;
import com.ly.service.impl.UserServiceImpl;
import com.ly.utils.DBUtils;
import com.ly.utils.MD5Utils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    //请求参数乱码
//		request.setCharacterEncoding("UTF-8");
		//获取用户登陆参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");
		
		//记住用户名功能
		if("true".equals(remname)){//记住用户名
			Cookie cookie = new Cookie("remname",URLEncoder.encode(username,"UTF-8"));
			System.out.println(cookie.getValue());
			cookie.setPath(request.getContextPath()+"/");
			cookie.setMaxAge(60*60*24*30);//保存30天
			response.addCookie(cookie);
		}else{//取消记住用户名
			Cookie cookie = new Cookie("remname",URLEncoder.encode(username, "UTF-8"));
			cookie.setPath(request.getContextPath()+"/");
			cookie.setMaxAge(0);//删除Cookie
			response.addCookie(cookie);
		}
		
		UserService service = BasicFactory.getFactory().getInstance(UserService.class);
		User user = service.login(username, MD5Utils.md5(password));
		if(user != null){
			request.getSession().setAttribute("user", user);
			// 登陆成功跳转到首页
			//处理30天免登陆
			//1.选择了30天自动登录复选框，才保存用户信息到cookie
			if("true".equals(request.getParameter("autologin"))){
				Cookie autoLogin = new Cookie("autoLogin",URLEncoder.encode(user.getUsername()+":"+user.getPassword(),"UTF-8"));
				autoLogin.setPath(request.getContextPath()+"/");
				autoLogin.setMaxAge(3600*24*30);
				response.addCookie(autoLogin);
			}
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}else{
			request.setAttribute("msg", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		
//		if( "".equals(username)|| username == null){
//			request.setAttribute("msg", "用户名不能为空");
//			request.getRequestDispatcher("/login.jsp").forward(request, response);
//			return;
//		}
//		if(password == null || "".equals(password) ){
//			request.setAttribute("msg", "密码不能为空");
//			request.getRequestDispatcher("/login.jsp").forward(request, response);
//			return;
//		}
//		//记住用户名功能
//		if("true".equals(remname)){//记住用户名
//			Cookie cookie = new Cookie("remname",URLEncoder.encode(username,"UTF-8"));
//			System.out.println(cookie.getValue());
//			cookie.setPath(request.getContextPath()+"/");
//			cookie.setMaxAge(60*60*24*30);//保存30天
//			response.addCookie(cookie);
//		}else{//取消记住用户名
//			Cookie cookie = new Cookie("remname",URLEncoder.encode(username, "UTF-8"));
//			cookie.setPath(request.getContextPath()+"/");
//			cookie.setMaxAge(0);//删除Cookie
//			response.addCookie(cookie);
//		}
//		
//		//根据用户名密码查数据库
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		ComboPooledDataSource pool = new ComboPooledDataSource();
//		try {
//			conn = DBUtils.getConnection();
//			ps = conn.prepareStatement("select * from user where username=? and password=?");
//			ps.setString(1, username);
//			ps.setString(2, password);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				//登陆
//				request.getSession().setAttribute("user", username);
//				
//				//登陆成功跳转到首页
//				response.sendRedirect(request.getContextPath()+"/index.jsp");
//			}else{
//				request.setAttribute("msg", "用户名或密码错误");
//				request.getRequestDispatcher("/login.jsp").forward(request, response);
//				return;
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("数据库连接失败！");
//		}finally{
//			if(rs != null){
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}finally{
//					rs = null;
//				}
//			}
//			if(conn != null){
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}finally{
//					conn = null;
//				}
//			}
//		}
//		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
