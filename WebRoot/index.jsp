<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" buffer="0kb"%>
<!DOCTYPE html>
<html>
<head>
<title>易买网</title>
<link rel="icon" href="${pageContext.request.contextPath}/img/index/icon.ico" mce_href="${pageContext.request.contextPath}/img/index/icon.ico" type="image/x-icon">
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/banner.js"></script>
</head>

<body>
    <!-- head start-->
	<!-- 将头部包含进来 -->
	<%@include file="/_head.jsp" %>
	<!-- head end -->
	<!-- index start -->
	<div id="index">
	<!-- 
	<div class="line1">
		<img alt="banner" src="./img/index/banner_big.jpg">
	</div>
	 -->
	 <div id="wrap">
  		<div id="slider"> 
   		<a target="_blank" href="#"><img src="img/index/1.jpg" /></a> 
   		<a target="_blank" href="#"><img id="second" /></a> 
   		<a target="_blank" href="#"><img id="third"/></a> 
   		<a target="_blank" href="#"><img id="four" /></a> 
  		</div> 
  			<ul> 
   				<li>1</li> 
   				<li>2</li> 
   				<li>3</li> 
   				<li>4</li> 
  			</ul> 
	</div>
	<div class="plus">
		<div>
			<h3>品牌馆</h3>
		</div>
		<div>
			<img alt="iPhone" src="${pageContext.request.contextPath}/img/index/iPhone.jpg">
			<div class="g">
				<img alt="vivo" src="${pageContext.request.contextPath}/img/index/vivo.png"> 
				<img alt="oppo" src="${pageContext.request.contextPath}/img/index/oppo.jpg">
			</div>
			<div class="g">
				<img alt="360" src="${pageContext.request.contextPath}/img/index/360.jpg"> 
				<img alt="LG" src="${pageContext.request.contextPath}/img/index/LG.jpg">
			</div>
			<div class="g">
				<img alt="ZTE" src="${pageContext.request.contextPath}/img/index/ZTE.jpg"> 
				<img alt="gionee" src="${pageContext.request.contextPath}/img/index/gionee.png">
			</div>
			<div class="g">
				<img alt="htc" src="${pageContext.request.contextPath}/img/index/htc.jpg"> 
				<img alt="oneplus" src="${pageContext.request.contextPath}/img/index/oneplus.jpg">
			</div>
		</div>
	</div>
	<div class="line2">
		<img src="${pageContext.request.contextPath}/img/index/adv1.jpg" alt="" /> <img
			src="${pageContext.request.contextPath}/img/index/adv2.jpg" alt="" /> <img class="last"
			src="${pageContext.request.contextPath}/img/index/adv_l1.jpg" alt="" />
	</div>
	<div class="line3">
		<img src="${pageContext.request.contextPath}/img/index/adv3.jpg" alt="" /> 
		<img src="${pageContext.request.contextPath}/img/index/adv4.jpg" alt="" />
		<div class="line3_right">
			<img src="${pageContext.request.contextPath}/img/index/adv_l2.jpg" alt="" /> 
			<img src="${pageContext.request.contextPath}/img/index/adv_l3.jpg" alt="" />
		</div>
	</div>
	<div class="line4">
				<img src="${pageContext.request.contextPath}/img/index/bottom_banner.png" alt="" />
			</div>
	<div class="line5">
		<div class="security">
			<img src="${pageContext.request.contextPath}/img/index/icon_g1.png" width="" height="" alt="" /> 
			<span>500强企业 品质保证 </span>
		</div>
		<div class="security">
			<img src="${pageContext.request.contextPath}/img/index/icon_g2.png" width="" height="" alt="" /> 
			<span>7天退货 15天换货 </span>
		</div>
		<div class="security">
			<img src="${pageContext.request.contextPath}/img/index/icon_g3.png" width="" height="" alt="" /> 
			<span>100元起免运费 </span>
		</div>
		<div class="security">
			<img src="${pageContext.request.contextPath}/img/index/icon_g4.png" width="" height="" alt="" /> 
			<span>448家维修网点 全国联保 </span>
		</div>
	</div>
	</div>
	<!-- index end -->
	<!-- footer start-->
		<!-- 将尾部包含进来 -->
	<%@include file="/_foot.jsp" %>	
	<!-- footer end -->
</body>
</html>
