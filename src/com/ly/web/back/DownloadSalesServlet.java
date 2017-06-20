package com.ly.web.back;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.domain.SaleInfo;
import com.ly.factory.BasicFactory;
import com.ly.service.OrderService;

public class DownloadSalesServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1、调用service方法查询所有的销售榜单结果集
				OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
				List<SaleInfo> list = service.findAllSales();
				//2、组织csv文件的内容
				//2.1初始表头
				String data = "商品编号,商品名称,销售总量\r\n";
				//2.2遍历集合
				if(list!=null){
					for (SaleInfo saleInfo : list) {
						//data += saleInfo.getProd_id()+","+saleInfo.getProd_name()+","+saleInfo.getSale_num()+"\r\n";
						data += saleInfo.toString()+"\r\n";
					}
				}
				//3、提供下载
				//3.1定义文件名称
				String fileName = "EasyMall销售榜单"+new Date().toLocaleString()+".csv";
				//3.2设置响应头，让浏览器以附件的方式打开
				response.setHeader("Content-Disposition", "attachment;filename="+
						URLEncoder.encode(fileName,"utf-8"));
				//3.3处理文件内容乱码的问题，csv只支持gbk
				response.setContentType("text/html;charset=gbk");
				//3.4将销售榜单的内容输出到浏览器
				response.getWriter().write(data);
				
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
