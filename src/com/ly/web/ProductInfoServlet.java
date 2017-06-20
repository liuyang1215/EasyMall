package com.ly.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.Product;
import com.ly.factory.BasicFactory;
import com.ly.service.ProductService;

public class ProductInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		//2.创建业务层对象
		ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
		//3.调用查询方法
		Product prod = service.findProductById(pid);
		//4.保存到作用域中
		request.setAttribute("prod", prod);
		//5.转发
		request.getRequestDispatcher("/prodInfo.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
