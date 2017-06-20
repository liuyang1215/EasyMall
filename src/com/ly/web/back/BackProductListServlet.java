package com.ly.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.Product;
import com.ly.factory.BasicFactory;
import com.ly.service.ProductService;

public class BackProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		        //1、创建业务层对象
				ProductService ps = BasicFactory.getFactory().
						getInstance(ProductService.class);
				//2、调用查询全部商品的方法
				List<Product> list = ps.findAll();
				//3、将查询的结果保存到request作用域中
				request.setAttribute("list", list);
				//4、转发
				request.getRequestDispatcher("/back/manageProd.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
