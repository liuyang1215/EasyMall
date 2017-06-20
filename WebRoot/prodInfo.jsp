<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>${prod.name}—你想要的，都在易购</title>
	<link href="${app }/css/prodInfo.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
		function addCart(){
			window.location.href="${app}/AddCartServlet?pid=${prod.id}";
		}
	</script>
</head>
<body>
	<%@include file="/_head.jsp" %>
	<div id="warp">
		<div id="left">
			<div id="left_top">
				<img src="${app}/ProductImgServlet?imgurl=${prod.imgurl}"/>
			</div>
			<div id="left_bottom">
				<img id="lf_img" src="${app}/img/prodInfo/lf.jpg"/>
				<img id="mid_img" src="${app}/img/prodInfo/prod.jpg" width="60px" height="60px"/>
				<img id="rt_img" src="${app}/img/prodInfo/rt.jpg"/>
			</div>
		</div>
		<div id="right">
			<div id="right_top">
				<span id="prod_name">${prod.name}<br/></span>
				<br>
				<span id="prod_desc">${prod.description}<br/></span>
			</div>
			<div id="right_middle">
				<span id="right_middle_span">
						EasyMall 价：<span class="price_red">￥${prod.price }<br/>
			            运     费：满 100 免运费<br />
			            服     务：由EasyMall负责发货，并提供售后服务<br />
			            库     存：${prod.pnum }<br />
			            购买数量：
	            <a href="#" id="delNum" onclick="">-</a>
	            <input id="buyNumInp" name="" type="text" value="1" onblur="">
		        <a href="#" id="addNum" onclick="">+</a>
			</div>
			<div id="right_bottom">
				<input class="add_cart_but" type="button" onclick="addCart()"/>	
			</div>
		</div>
	</div>
	<%@include file="/_foot.jsp" %>	
</body>
</html>