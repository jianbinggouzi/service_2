<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta content="Content-Type" content="text/html; charset=UTF-8">
		<title>用户注册</title>
		<script>
			function mycheck(){
				if(document.all("user.password").value != document.all("again").value){
					alert("两次输入密码不同");
					return false;
				}else {
					return true;	
				}
				
			}
		</script>
	</head>
	<body>
		<form action="<c:url value="/register.action" />" method="post" onsubmit="return mycheck()">
			<c:if test="${!empty errorMsg}">
				<div style="color:red">${errorMsg}</div> 
			</c:if>
			<table>
			<tr>
				<td width="20%">用户名</td>
				<td width="80%"><input type="text" name="userName"/></td>
			</tr>
			<tr>
				<td width="20%">密码</td>
				<td width="80%"><input type="password" name="password"/></td>
			</tr>
			<tr>
				<td width="20%">确认密码</td>
				<td width="80%"><input type="password" name="again"/></td>
			</tr>
			<tr>
				<td> <input type="submit" value="确认"/> </td>
			</tr>
			</table>
		</form>
	</body>
</html>