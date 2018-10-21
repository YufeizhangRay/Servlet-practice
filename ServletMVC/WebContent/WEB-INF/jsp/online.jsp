<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1" cellpadding="0" cellspacing="0"
		style="width: 80%; margin: 0 auto;">
		<tr>
			<td>在线用户的ssid</td>
			<td>在线用户的用户名</td>
			<td>在线用户的ip地址</td>
			<td>在线用户的放问页面</td>
			<td>在线用户的进来的时间</td>
		</tr>
		<c:forEach var="online" items="${online}">
			<tr>
				<td>${online.ssid}</td>
				<td>${online.username}</td>
				<td>${online.ip}</td>
				<td>${online.page}</td>
				<td>${online.time}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>