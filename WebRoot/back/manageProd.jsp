<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
body {
	background: #F5F5F5;
	text-align: center;
}
#customers
  {
  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
  width:100%;
  border-collapse:collapse;
  align="center";
  vertical-align:middle;
  
  }

#customers td, #customers th 
  {
  font-size:1em;
  border:1px solid #98bf21;
  padding:3px 7px 2px 7px;
  }

#customers th 
  {
  font-size:1.1em;
  padding-top:5px;
  padding-bottom:4px;
  background-color:#A7C942;
  color:#ffffff;
  }

#customers tr.alt td 
  {
  color:#000000;
  background-color:#EAF2D3;
  }
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax.js"></script>
<script type="text/javascript">
    function changePnum(pid,obj){
       //解决修改库存后再次修改数据错误时还原第一次库存量的问题 
    	var oldPnum = document.getElementById("old_"+pid);
    	var newNum = obj.value;
    	//判断输入内容不合法
    	if(isNaN(newNum)){
    		alert("您输入的数据不合法，请输入数字！");
    		//将对应的输入框的内容返回
    		obj.value = oldPnum.value;
    	}else if(newNum < 0){
    		alert("请输入大于等于0的数字！");
    		obj.value = oldPnum.value;
    	}else if(newNum.value != oldPnum.value ){
    		//1.第一步, 创建XMLHTTPRequest对象
			var xmlHttp = ajaxFunction();
			//2.第二步, 打开与服务器的连接
			xmlHttp.open("POST", "${app}/AjaxChangePnumServlet", true);
			//3.第三步, 发送请求
			xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");//指定向服务器发送的是请求参数
			xmlHttp.send("id="+pid+"&pnum="+obj.value );
			//4.第四步, 注册监听
			xmlHttp.onreadystatechange = function(){
				if(xmlHttp.readyState == 4){
					if(xmlHttp.status == 200 || xmlHttp.status == 304){
						var data = xmlHttp.responseText;
						if(data == "true"){
							alert("修改成功！");
							oldPnum.value=newNum;
						}else{
							alert("修改失败！");
						}
					}
				}
			};
    	}
    }
    function deleteProd(pid){
		if(window.confirm("您确认删除吗？")){
			window.location.href = "${app }/DeleteProductServlet?pid="+pid;
		}
	}
</script>
</head>
<body>
	<h1>商品管理</h1>
	<a href="${pageContext.request.contextPath}/back/manageAddProd.jsp">添加商品</a>
	<hr>
	<table id="customers" >
		<tr>
			<th>商品图片</th>
			<th>商品id</th>
			<th>商品名称</th>
			<th>商品种类</th>
			<th>商品单价</th>
			<th>库存数量</th>
			<th>描述信息</th>
			<th>删除</th>
		</tr>
		<c:forEach items="${list }" var="prod" varStatus="status">
			<tr <c:if test="${status.index % 2 != 0}">style="background-color:#ECF6EE;"</c:if>>
				<td>
					<img width="120px" height="120px" src="${pageContext.request.contextPath}/ProductImgServlet?imgurl=${prod.imgurl}"/>
				</td>
				<td>${prod.id }</td>
				<td>${prod.name}</td>
				<td><p>${prod.category}</p></td>
				<td>${prod.price }</td>
				<td>
					<input type="text" value=" ${prod.pnum }" style="width: 50px" onblur="changePnum('${prod.id}',this)"/>
					<!-- 解决修改库存后再次修改数据错误时还原第一次库存量的问题 -->
					<input type="hidden" id="old_${prod.id }" value="${prod.pnum }"/>
				</td>
				<td><p style="text-align: justify;">${prod.description }</p></td>
				<td> <a href="javascript:void(0)"  onclick="deleteProd('${prod.id}')">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
