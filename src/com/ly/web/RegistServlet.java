package com.ly.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.ly.domain.User;
import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.factory.UserServiceFactory;
import com.ly.service.UserService;
import com.ly.service.impl.UserServiceImpl;
import com.ly.utils.MD5Utils;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
		//处理注册请求
		//0.解决乱码
//		response.setContentType("text/html;charset=utf-8");
//		request.setCharacterEncoding("UTF-8");//POST提交解决乱码
		
		//校验验证码
		String valistr = request.getParameter("valistr");
		String valistr2 = (String) request.getSession().getAttribute("valistr");
		if(valistr == null || valistr2 == null || !valistr.equalsIgnoreCase(valistr2) ){
			request.setAttribute("msg", "验证码不正确");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		//校验两次密码是否一致
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		if(password == null || password2 == null || !password.equals(password2) ){
			request.setAttribute("msg", "两次密码不一致");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		
		//1.获取请求参数
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		
		//2.将数据封装到JavaBean
		User user = new User();
		BeanUtils.populate(user, request.getParameterMap());
		
		//3.校验数据
		user.checkData();
		
		//4.调用Service层方法注册用户
		UserService service = BasicFactory.getFactory().getInstance(UserService.class);
		user.setPassword(MD5Utils.md5(password));
		service.registUser(user);
		
		//5.给出提示(注册成功, 跳转回首页)
		request.getSession().setAttribute("user", user);
		response.getWriter().write("恭喜您注册成功, 3秒之后跳转回首页...");
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
	
	} catch (MsgException e) {
		request.setAttribute("msg", e.getMessage());
		request.getRequestDispatcher("/regist.jsp").forward(request, response);
		return;
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}

		
//		//1.获取请求参数
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		String password2 = request.getParameter("password2");
//		String nickname = request.getParameter("nickname");
//		String email = request.getParameter("email");
//		String valistr = request.getParameter("valistr");
//		
//		//校验验证码
//		String valistr2 = (String)request.getSession().getAttribute("valistr");
//		if(valistr==null && valistr2==null && !valistr.equalsIgnoreCase(valistr2)){
//			request.setAttribute("msg", "验证码不正确");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		
//		//2.校验数据
//		  //>非空校验 |数据回写
//		if(username == null || "".equals(username) ){
//			request.setAttribute("msg", "用户名不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		if(password == null || "".equals(password) ){
//			request.setAttribute("msg", "密码不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		if(password2 == null || "".equals(password2) ){
//			request.setAttribute("msg", "确认密码不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		 //>两次密码是否一致校验
//		if(!password .equals(password2)){
//			request.setAttribute("msg", "两次密码不一致");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		if(nickname == null || "".equals(nickname) ){
//			request.setAttribute("msg", "昵称不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		if(email == null || "".equals(email) ){
//			request.setAttribute("msg", "邮箱不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		 //>邮箱格式校验
//		String reg = "^\\w+@\\w+(\\.\\w+)+$";
//		if(!email .matches(reg)){
//			request.setAttribute("msg", "邮箱格式不正确");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//		if(valistr == null || "".equals(valistr) ){
//			request.setAttribute("msg", "验证码不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
//			return;
//		}
//
//		
//		
//		 //>验证码是否正确
//		
//		//3.注册（把数据保存到数据库）
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		ComboPooledDataSource pool = new ComboPooledDataSource();
//		try {
//			conn = pool.getConnection();
//			 //>用户名是否存在校验
//			ps = conn.prepareStatement("select * from user where username=?");
//			ps.setString(1, username);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				request.setAttribute("msg", "用户名已存在");
//				request.getRequestDispatcher("/regist.jsp").forward(request, response);
//				return;
//			}
//			 //>注册
//			ps = conn.prepareStatement("insert into user values(null,?,?,?,?)");
//			ps.setString(1, username);
//			ps.setString(2, password);
//			ps.setString(3, nickname);
//			ps.setString(4, email);
//			int row = ps.executeUpdate();
//			if(row > 0){
//				//4.给出提示（注册成功，跳转回首页）
//				response.getWriter().write("恭喜您注册成功！3秒后跳转回首页...");
//				response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
//			}else{
//				throw new RuntimeException("Sorry!连接数据库失败!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException();
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
