package com.ly.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.service.ProductService;

public class AjaxChangePnumServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收参数
		String id = request.getParameter("id");
		String num = request.getParameter("pnum").trim();
		int pnum = Integer.valueOf(num);
		//2.调用业务层方法修改商品库存对象
		ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
		try{
			service.changePnum(id,pnum);
			response.getWriter().write("true");
		}catch(MsgException e){
			response.getWriter().write("false");
		}
	}

}
