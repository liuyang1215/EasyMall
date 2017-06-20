<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>全部商品</title>
<link rel="stylesheet" type="text/css" href="${app }/css/prodList.css">
<link rel="stylesheet" type="text/css" href="${app }/css/page.css">
<script type="text/javascript">
	function changeSubmit(){
		document.getElementById("thispage").value= 1;
		//Js提交表单
		document.getElementById("searchForm").submit();
	}
	function changePage(thispage){
		document.getElementById("thispage").value= thispage;
		//Js提交表单
		document.getElementById("searchForm").submit();
	}
	function jumpTo(obj){
		//获取输入框中的值
		var val = obj.value;
		var reg = /^[1-9][0-9]*$/;
		if(!reg.test(val)){
			alert("请输入正整数");
			obj.value = "${page.thispage}";
			return;
		}
		//改变隐藏域的值
		document.getElementById("thispage").value = val;
		document.getElementById("searchForm").submit();
	}
</script>
</head>

<body>
	<%@include file="/_head.jsp" %>
	<div id="content">
		<div id="search_div">
			<form method="post"  id="searchForm" action="${app }/ProdPageServlet">
				<input type="hidden"  id="thispage" name="thispage" value="${page.thispage }"/>
				<input type="hidden"  id="rowperpage" name="rowperpage" value="${page.rowperpage }"/>
				<span class="input_span">商品名：<input type="text" name="name"  value="${name }"/></span>
				<span class="input_span">商品种类：<input type="text" name="category" value="${category }"/></span> 
				<span class="input_span">商品价格区间：<input type="text" name="minprice" value="${minprice }"/> - <input type="text" name="maxprice" value="${maxprice}"/></span>
				<input type="submit" value="查询">
			</form>
		</div>
		<div id="prod_content">
		<c:forEach items="${page.list }"  var="prod">
			<div id="prod_div">
				<a href="${app}/ProductInfoServlet?pid=${prod.id}">
					<img src="${app}/ProductImgServlet?imgurl=${prod.imgurl}"></img>			
				</a>
		
				<div id="prod_name_div"><a href="${app}/ProductInfoServlet?pid=${prod.id}">${prod.name}</a></div>
				<div id="prod_price_div">￥${prod.price }元</div>
				<div>
					<div id="gotocart_div">
						<a href="${app}/AddCartServlet?pid=${prod.id}">加入购物车</a>
					</div>
					<div id="say_div">133人评价</div>
				</div>
			</div>
		</c:forEach>
		</div>
		<div style="clear: both"></div>
	</div>


	<!-- 分页插件 -->
	<div id="fy_div">
		共${page.countrow }条记录 共${page.countpage }页 
		<a href="javascript:void(0)" onclick="changePage(1)">首页</a> 
		<a href="javascript:void(0)" onclick="changePage(${page.prepage})">上一页</a>

		<%-- 分页逻辑开始 --%>
		<c:set var="begin" scope="page" value="0"/>
		<c:set var="end" scope="page" value="0"/>
		<c:if test="${page.countpage<=5 }">
			<c:set var="begin" scope="page" value="1"/>
		    <c:set var="end" scope="page" value="${page.countpage}"/>
		</c:if>
		<c:if test="${page.countpage>5}">
		   <c:choose>
		   	<c:when test="${page.thispage<=3}">
		   	  <c:set var="begin" scope="page" value="1"/>
		      <c:set var="end" scope="page" value="5"/>
		   	</c:when>
		    <c:when test="${page.thispage>=page.countpage-2}">
		     <c:set var="begin" scope="page" value="${page.countpage-4}"/>
		     <c:set var="end" scope="page" value="${page.countpage}"/>
		    </c:when>
		    <c:otherwise>
		      <c:set var="begin" scope="page" value="${page.thispage-2}"/>
		      <c:set var="end" scope="page" value="${page.thispage+2}"/>
		    </c:otherwise>
		   </c:choose>
		</c:if>
		<c:forEach begin="${begin }" end="${end }" step="1" var="i">
		<c:if test="${i != page.thispage }" var="isnotCp" scope="page">
			<a href="javascript:void(0)" onclick="changePage(${i})">${i }</a>
		</c:if>
		<c:if test="${!isnotCp }">
			${i }
		</c:if>	
		</c:forEach>
		<%-- 分页逻辑结束 --%>
		<a href="javascript:void(0)" onclick="changePage(${page.nextpage})">下一页</a> <a href="javascript:void(0)" onclick="changePage(${page.countpage})"">尾页</a> 跳转到<input type="text" value="${page.thispage }"
			onblur="jumpTo(this)" />页
	</div>
	
	<%@include file="/_foot.jsp" %>	
</body>
</html>
