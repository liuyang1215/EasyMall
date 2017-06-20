package com.ly.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.OrderInfo;
import com.ly.domain.User;
import com.ly.factory.BasicFactory;
import com.ly.service.OrderService;

public class OrderListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				//1、判断用户是否登录
				Object userObj = request.getSession().getAttribute("user");
				if(userObj==null){//未登录
					response.sendRedirect(request.getContextPath()+"/login.jsp");
					return;
				}
				//2、获取用户
				User user = (User)userObj;
				//3、调用service方法
				OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
				List<OrderInfo> list = service.getOrderInfosByUserId(user.getId());
				//4、传参
				request.setAttribute("list", list);
				//5、转发跳转
				request.getRequestDispatcher("/orderList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
