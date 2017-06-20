package com.ly.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ly.factory.BasicFactory;
import com.ly.service.OrderService;
import com.ly.utils.PaymentUtil;

public class PayServlet extends HttpServlet {
	private static Properties prop = null;
	static{
		prop = new Properties();
		try {
			prop.load(new FileReader(PayServlet.class.getClassLoader().getResource("merchantInfo.properties").getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.准备第三方支付平台需要的数据
		String p0_Cmd ="Buy";
		String p1_MerId=prop.getProperty("p1_MerId");
		String  p2_Order= request.getParameter("orderid");
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		//String p3_Amt = service.getMoneyByOrderId(p2_Order)+"";
		String p3_Amt = "0.01";//切记别忘记改为0.01，将来程序部署到正式服务器之前，在改回去
		String p4_Cur ="CNY";//支持的币种（人民币）
		String p5_Pid = "";
		String p6_Pcat="";
		String p7_Pdesc ="";
		String p8_Url = prop.getProperty("responseURL");//
		String p9_SAF = "";
		String pa_MP ="";
		String pd_FrpId = request.getParameter("pd_FrpId");
		String  pr_NeedResponse = "1";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, 
					p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, prop.getProperty("keyValue"));
		//2.将以上参数传到confirm.jsp
		request.setAttribute("pd_FrpId", pd_FrpId);
		request.setAttribute("p0_Cmd", p0_Cmd);
		request.setAttribute("p1_MerId", p1_MerId);
		request.setAttribute("p2_Order", p2_Order);
		request.setAttribute("p3_Amt", p3_Amt);
		request.setAttribute("p4_Cur", p4_Cur);
		request.setAttribute("p5_Pid", p5_Pid);
		request.setAttribute("p6_Pcat", p6_Pcat);
		request.setAttribute("p7_Pdesc", p7_Pdesc);
		request.setAttribute("p8_Url", p8_Url);
		request.setAttribute("p9_SAF", p9_SAF);
		request.setAttribute("pa_MP", pa_MP);
		request.setAttribute("pr_NeedResponse", pr_NeedResponse);
		request.setAttribute("hmac", hmac);
		//3\转发
		request.getRequestDispatcher("/confirm.jsp").forward(request, response);
	}

}
