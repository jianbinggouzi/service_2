<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String context = request.getContextPath();
	request.setAttribute("context",context);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transactional//EN">
<html>
	<head>
		<meta content="Content-Type" content="text/html; charset=UTF-8">
		<title>用户登录</title>
	</head>
	<body>
		<c:if test="${!empty errorMsg}">
			<div style="color:red">${errorMsg}</div>
		</c:if>
		<form action="${context}/login/doLogin.action" method="post">
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
					<td>
						<input type="submit" value="登录"/>
					</td>
				</tr>	
			</table>
		</form>
	</body>
</html>