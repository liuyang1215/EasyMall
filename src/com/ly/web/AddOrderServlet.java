package com.ly.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.Order;
import com.ly.domain.OrderItem;
import com.ly.domain.Product;
import com.ly.domain.User;
import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.service.OrderService;

public class AddOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				//1、判断用户是否登录
				Object userObj = request.getSession().getAttribute("user");
				if(userObj==null){//用户未登录
					response.sendRedirect(request.getContextPath()+"/login.jsp");
					return;
				}
				//用户已经登录
				User user = (User)userObj;
				//2、将购物车中信息封装一个Order和N个OrderItem对象
				//2.1封装Order对象
				Order order= new Order();
				order.setId(UUID.randomUUID().toString());
				order.setUser_id(user.getId());
				order.setReceiverinfo(request.getParameter("receiverinfo"));
				order.setPaystate(0);
				order.setOrdertime(new Timestamp(new Date().getTime()));
				//2.2从session中获取购物车
				Map<Product,Integer> cart = (Map<Product,Integer>)request.getSession().getAttribute("cart");
				//2.3遍历购物车，计算订单总金额，创建OrderItem对象并保存集合中
				double money =0;
				List<OrderItem> items = new ArrayList<OrderItem>();
				for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
					//2.3.1计算总金额
					money +=entry.getKey().getPrice()*entry.getValue();
					//2.3.2获取购物项转换成OrderItem对象（订单项）
					OrderItem item= new OrderItem();
					item.setOrder_id(order.getId());
					item.setProduct_id(entry.getKey().getId());
					item.setBuynum(entry.getValue());
					//2.3.3勿忘我
					items.add(item);
				}
				//3、将订单总金额封装order
				order.setMoney(money);
				//4、调用service方法
				OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
				try{
					service.addOrder(order,items);
					//5、清空购物车
					cart.clear();
					//6、提示添加成功
					response.getWriter().write("订单添加成功！");
					response.setHeader("Refresh", "2;url="+request.getContextPath()+"/OrderListServlet");
				}catch (MsgException e) {
					request.setAttribute("msg", e.getMessage());
					request.getRequestDispatcher("/cart.jsp").forward(request, response);
				}
				
	}

}
