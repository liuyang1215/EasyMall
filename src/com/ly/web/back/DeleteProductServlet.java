package com.ly.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.exception.MsgException;
import com.ly.factory.BasicFactory;
import com.ly.service.ProductService;

public class DeleteProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接收参数_商品ID
		String id = request.getParameter("id");
		ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
		try{
			service.deleteProductById(id,getServletContext());
			response.getWriter().write("删除成功！2秒之后，自动跳转，如果没有跳转，请点击<a href='"+request.getContextPath()+"/BackProductListServlet'>跳转</a>");
		}catch(MsgException e){
			response.getWriter().write(e.getMessage()+"！2秒之后，自动跳转，如果没有跳转，请点击<a href='"
					+request.getContextPath()+"/BackProductListServlet'>跳转</a>");
		}
		response.setHeader("Refresh", "2;url="+request.getContextPath()+"/BackProductListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
