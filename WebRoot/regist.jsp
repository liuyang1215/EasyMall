<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>欢迎注册EasyMall</title>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/regist.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajax.js"></script>
	<script type="text/javascript">
		//注册表单js校验
		function checkForm(){
			var flag = true;
			flag = checkNull("username","用户名不能为空") && flag;
			flag = checkNull("password","密码不能为空") && flag;
			flag = checkNull("password2","确认密码不能为空") && flag;
			flag = checkNull("nickname","昵称不能为空") && flag;
			flag = checkNull("email","邮箱不能为空") && flag;
			flag = checkNull("valistr","验证码不能为空") && flag;
			flag = checkPassword("password","两次密码不一样")&& flag;
			flag = checkEmail("email", "邮箱格式不正确") && flag;
			return flag;
		}
		//检查邮箱格式
		function checkEmail(name, msg){
			var email = document.getElementsByName(name)[0].value;
			var reg = /^\w+@\w+(\.\w+)+$/;
			setMsg(name, "");
			checkNull(name, "邮箱不能为空");
			if(email != "" && !reg.test(email)){
				setMsg(name, msg);
				return false;
			}
			return true;
		}
		//检查密码是否一致
		function checkPassword(name,msg){
			var psw1 = document.getElementsByName(name)[0].value;
			var psw2 = document.getElementsByName(name+"2")[0].value;
			setMsg(name+"2","");
			checkNull(name+"2","确认密码不能为空");
			if(psw1 != "" && psw2 != "" && psw1 != psw2){
				setMsg(name,msg);
				return false;
			}
			return true;
		}
		//检查表单项是否为空
		function checkNull(name,msg){
			var value = document.getElementsByName(name)[0].value;
			setMsg(name,"");
			if(value == ""){
				setMsg(name, msg);
				return false;
			}
			return true;
		}
		//设置提示消息
		function setMsg(name,msg){
			document.getElementById(name+"_msg").innerHTML=msg;
		}
		
		//利用ajax校验用户名是否存在
		function ajaxCheckUserName(thisobj){
			var username = thisobj.value;
			if(username == ""){
				setMsg("username","用户名不能为空");
				return;
			}
			
			//ajax校验用户名
			//1.创建XMLHttpRequest对象
			var xmlHttp = ajaxFunction();
			//2.打开与服务器的连接
			xmlHttp.open("POST","<%= request.getContextPath()%>/AjaxCheckUserNameServlet",true);
			//3.发送请求
			xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); //用来通知服务器发送的是请求参数
			xmlHttp.send("username="+username);
			//4.注册监听
			xmlHttp.onreadystatechange = function(){
				if(xmlHttp.readyState == 4){
					if(xmlHttp.status == 200 || xmlHttp.status == 304){
						var data = xmlHttp.responseText;
						if(data == "true"){
							setMsg("username","用户名已经存在");
						}else{
							setMsg("username","恭喜！用户名可以使用！");
						}
					}
				}
			}
		}
		
	</script>
</head>
<body>
	<h1>欢迎注册EasyMall</h1>
	<form action="${pageContext.request.contextPath}/RegistServlet" method="POST" onsubmit="return checkForm()">
		<table>
			<tr>
				<td class="tds" colspan="2" style="color:red;font-size:15px;text-align: center">
					<!-- 判断非空操作 -->
					${ msg }
					</td>
			</tr>
			<tr>
				<td class="tds">用户名：</td>
				<td>
					<input type="text" name="username" value="${ param.username }" onblur="ajaxCheckUserName(this)">
					<span id="username_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">密码：</td>
				<td>
					<input type="password" name="password"  onblur="checkNull('password','密码不能为空')">
					<span id="password_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">确认密码：</td>
				<td>
					<input type="password" name="password2" onblur="checkPassword('password','两次密码不正确')">
					<span id="password2_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">昵称：</td>
				<td>
					<input type="text" name="nickname"  value="${ param.nickname }" onblur="checkNull('nickname','昵称不能为空')">
					<span id="nickname_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">邮箱：</td>
				<td>
					<input type="text" name="email" value="${ param.email }"  onblur="checkEmail('email','邮箱格式不正确')">
					<span id="email_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">验证码：</td>
				<td>
					<input type="text" name="valistr">
					<img id="yzm_img" onclick="changeImage(this)" src="${pageContext.request.contextPath}/ValiImageServlet" style="cursor: pointer; border:1px solid grey" onblur="checkNull('valistr','验证码不能为空')"/>
					<span id="valistr_msg"></span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="注册用户"/>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		//鼠标点击更换验证码图片
		function changeImage(thisobj){
			thisobj.src = "${pageContext.request.contextPath}/ValiImageServlet?time="+new Date().getTime()
		}
	</script>
</body>
</html>

