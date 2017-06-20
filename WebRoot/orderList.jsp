<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>我的订单-订单列表</title>
    <link href="${app}/css/orderList.css" rel="stylesheet" type="text/css">
  </head>
  <body style="text-align:center;">
  <%@include file="/_head.jsp" %>
  <!-- 未购买提示信息 -->

  <c:forEach items="${list }" var="oi">
  <dl class="Order_information">
		<dt>
			<h3>订单信息</h3>
		</dt>
		<dd>
			订单编号：${oi.order.id }<br />
			 下单时间：${oi.order.ordertime }<br /> 
			 订单金额：${oi.order.money }<br /> 
			 支付状态：
			 <c:if test="${oi.order.paystate==0 }" var="ps" scope="page">
					<font color="red">未支付</font>&nbsp;&nbsp;&nbsp;
					<a href="${app }/OrderDeleteServlet?oid=${oi.order.id }"><img src="${app}/img/orderList/sc.jpg" width="69" height="19"></a> 
			 		<a href="${app }/pay.jsp?oid=${oi.order.id }&money=${oi.order.money }"><img src="${app}/img/orderList/zx.jpg" width="69" height="19"></a><br /> 
			 </c:if>
			 <c:if test="${!ps }">
					<font color="blue">已支付</font><br/> 
			 </c:if>
			 收货地址：${oi.order.receiverinfo }<br/> 
			支付方式：在线支付
		</dd>
	</dl>
	<table width="1200" border="0" cellpadding="0"
		cellspacing="1" style="background:#d8d8d8;color:#333333">
		<tr>
			<th width="276" height="30" align="center" valign="middle" bgcolor="#f3f3f3">商品图片</th>
			<th width="247" align="center" valign="middle" bgcolor="#f3f3f3">商品名称</th>
			<th width="231" align="center" valign="middle" bgcolor="#f3f3f3">商品单价</th>
			<th width="214" align="center" valign="middle" bgcolor="#f3f3f3">购买数量</th>
			<th width="232" align="center" valign="middle" bgcolor="#f3f3f3">小计</th>
		</tr>
	<c:forEach items="${oi.orderItem}" var="item"> 
		<tr>
			<td align="center" valign="middle" bgcolor="#FFFFFF">
				<img src="${app}/ProductImgServlet?imgurl=${item.imgurl}" width="90" height="105">
			</td>
			<td align="center" valign="middle" bgcolor="#FFFFFF">${item.name }</td>
			<td align="center" valign="middle" bgcolor="#FFFFFF">${item.price}元</td>
			<td align="center" valign="middle" bgcolor="#FFFFFF">${item.buynum }件</td>
			<td align="center" valign="middle" bgcolor="#FFFFFF">${item.price*item.buynum}元</td>
		</tr>
	</c:forEach>
	</table>
	<div class="Order_price">${oi.order.money }</div>
	</c:forEach>
  <%@include file="/_foot.jsp" %>
  </body>
</html>
