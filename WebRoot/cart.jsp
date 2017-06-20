<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>我的购物车</title>
    <link href="${app }/css/cart.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
    function changeNum2(pid,oldNum,obj){
    	var newNumIpt = obj;
    	var reg = /^[1-9][0-9]*$/;
    	if(!reg.test(newNumIpt.value)){
    		alert("购买数量必须是正整数!");
    		newNumIpt.value=oldNum;
    		return;
    	}
    	if(newNumIpt.value!=oldNum){
    		window.location.href="${app}/UpdateCartServlet?pid="+pid+"&newBuyNum="+newNumIpt.value;
    	}
    }
    function addNum2(pid,obj){
    	//var num = document.getElementById(pid).value;
    	var num = obj.parentNode.getElementsByTagName("input")[0].value;
    	var newNum = parseInt(num)+1;
    	window.location.href="${app}/UpdateCartServlet?pid="+pid+"&newBuyNum="+newNum;
    }
    function changeNum(pid,oldNum){
    	var newNumIpt = document.getElementById(pid);
    	var reg = /^[1-9][0-9]*$/;
    	if(!reg.test(newNumIpt.value)){
    		alert("购买数量必须是正整数!");
    		newNumIpt.value=oldNum;
    		return;
    	}
    	if(newNumIpt.value!=oldNum){
    		window.location.href="${app}/UpdateCartServlet?pid="+pid+"&newBuyNum="+newNumIpt.value;
    	}
    }
    function addNum(pid){
    	var num = document.getElementById(pid).value;
    	var newNum = parseInt(num)+1;
    	window.location.href="${app}/UpdateCartServlet?pid="+pid+"&newBuyNum="+newNum;
    }
    function delNum(pid){
    	var num = document.getElementById(pid).value;
    	var newNum = num-1;
    	if(newNum>0){
	    	window.location.href="${app}/UpdateCartServlet?pid="+pid+"&newBuyNum="+newNum;
    	}else{
    		if(window.confirm("您确认删除吗？")){
    			window.location.href="${app}/DeleteCartServlet?pid="+pid;
    		}
    	}
    }
    </script>
  </head>
  <body>
  <%@include file="/_head.jsp" %>
   	<div class="warp">
   	   <font color="red">${requestScope.msg}</font>
		<!-- 标题信息 -->
		<div id="title">
			<input name="allC" type="checkbox" value="" onclick=""/>
			<span id="title_checkall_text">全选</span>
			<span id="title_name">商品</span>
			<span id="title_price">单价（元）</span>
			<span id="title_buynum">数量</span>
			<span id="title_money">小计（元）</span>
			<span id="title_del">操作</span>
		</div>
		<!-- 购物信息 -->
	<c:set  var="money" value="0" scope="page"/>
	<c:forEach items="${sessionScope.cart}" var="entry">
		<div id="prods">
			<input name="prodC" type="checkbox" value="" onclick=""/>
			<img src="${app }/ProductImgServlet?imgurl=${entry.key.imgurl}" width="90" height="90" />
			<span id="prods_name" style="width:10px; text-align:center;">${entry.key.name }</span>
			<span id="prods_price">${entry.key.price }</span>
			<span id="prods_buynum"> 
				<a href="javascript:void(0)" id="delNum" onclick="delNum('${entry.key.id}')" >-</a>
				<input id="${entry.key.id}" type="text" value="${entry.value}" size="4" onblur="changeNum('${entry.key.id}',${entry.value})">
				<a href="javascript:void(0)" id="addNum" onclick="addNum2('${entry.key.id}',this)">+</a>
			</span>
			<span id="prods_money">${entry.key.price*entry.value}</span>
			<c:set  var="money" value="${money+entry.key.price*entry.value}" scope="page"/>
			<span id="prods_del"><a href="${app }/DeleteCartServlet?pid=${entry.key.id}">删除</a></span>
		</div>
	</c:forEach>
		<!-- 总计条 -->
		<div id="total">
			<div id="total_1">
				<input name="allC" type="checkbox" value=""/> 
				<span>全选</span>
				<a id="del_a" href="#">删除选中的商品</a>
				<span id="span_1">总价：</span>
				<span id="span_2">${money}</span>
			</div>
			<div id="total_2">	
				<a id="goto_order" href="${app }/addOrder.jsp">去结算</a>
			</div>
		</div>
	</div>
     <%@include file="/_foot.jsp" %>
  </body>
</html>
