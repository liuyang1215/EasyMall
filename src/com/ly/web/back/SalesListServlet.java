package com.ly.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.SaleInfo;
import com.ly.factory.BasicFactory;
import com.ly.service.OrderService;

public class SalesListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderService service = BasicFactory.getFactory().
				getInstance(OrderService.class);
		List<SaleInfo> list = service.findAllSales();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/back/saleList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
