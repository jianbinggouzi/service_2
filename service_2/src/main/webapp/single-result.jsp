<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String context = request.getContextPath();
	request.setAttribute("context",context);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transactional//EN">
<html>
	<head>
		<meta content="Content-Type" content="text/html; charset=UTF-8">
		<title>单个结果</title>
	</head>
	<body>
		${result }
		<!-- 
		<c:if test="${!empty errorMsg}">
			<div style="color:red">${errorMsg}</div>
		</c:if>
		 -->
	</body>
</html>