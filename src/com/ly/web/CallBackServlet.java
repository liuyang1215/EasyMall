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

public class CallBackServlet extends HttpServlet {
	private static Properties prop = null;
	static {
		prop = new Properties();
		try {
			prop.load(new FileReader(PayServlet.class.getClassLoader()
					.getResource("merchantInfo.properties").getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取回调时服务器回传回来的信息
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		// 订单id
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		// 为“1”: 浏览器重定向;
		// 为“2”: 服务器点对点通讯.
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = prop.getProperty("keyValue");
		// 对以上参数结果进行加密，和支付公司发回来的hmac进行比较，没有被修改返回true
		boolean isTrue = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isTrue) {// 数据没有被篡改
			// 如果r9_BType为1，给予提示信息
			if ("1".equals(r9_BType)) {// 客户端浏览器重定向
				response.getWriter().println("<h1>付款操作完成，等待银行回复确认信息。。。</h1>");
				//
				/*
				 * OrderService service = BasicFactory.getFactory().
				 * getInstance(OrderService.class);
				 * service.updatePayState(r6_Order,1); //回复支付公司
				 * response.getWriter().print("success");
				 */
			} else if ("2".equals(r9_BType)) {
				// “2”: 服务器点对点通讯,修改支付状态
				System.out.println("付款成功！");
				// 修改订单状态:未付款--》已付款
				OrderService service = BasicFactory.getFactory().getInstance(
						OrderService.class);
				service.updatePayState(r6_Order, 1);
				// 回复支付公司
				response.getWriter().print("success");
			}
		} else {// 数据无效
			System.out.println("数据被篡改");
		}
	}

}


