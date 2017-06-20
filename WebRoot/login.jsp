<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" buffer="0kb"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/login.css" />
<title>EasyMall欢迎您登陆</title>
<script type="text/javascript">
		window.onload = function(){
			document.getElementsByName("username")[0].value = decodeURI(document.getElementsByName("username")[0].value, "utf-8");
		}
</script>
</head>
<body>
	<h1>欢迎登陆EasyMall</h1>
	<form action="${ pageContext.request.contextPath }/LoginServlet" method="POST">
		<table>
		<tr>
				<td class="tds" colspan="2" style="color:red;font-size:15px;text-align: center">
					${ msg }
					</td>
			</tr>
			<tr>
				<td class="tdx">用户名:</td>
				<td><input type="text" name="username"  value="${ cookie.remname.value }"/></td>
			</tr>
			<tr>
				<td class="tdx">密码:</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="checkbox" name="remname" value="true" ${ cookie.remname.value == "" ? "": 'checked=checked' }/>记住用户名 
				<input type="checkbox" name="autologin" value="true" />30天内自动登陆</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="登陆" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
