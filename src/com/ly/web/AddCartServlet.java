package com.ly.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.Product;
import com.ly.factory.BasicFactory;
import com.ly.service.ProductService;

public class AddCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取商品id
		String pid = request.getParameter("pid");
		//2. 调用业务层方法
		ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
		Product prod = service.findProductById(pid);
		//3.将商品加入购物车
		//3.1 获取购物车中的map
		Map<Product,Integer> cart = (Map<Product,Integer>)request.getSession().getAttribute("cart");
		//3.2 加入购物车
		int newCount = cart.containsKey(prod)?cart.get(prod)+1:1;
		cart.put(prod, newCount);
		//4.回到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
