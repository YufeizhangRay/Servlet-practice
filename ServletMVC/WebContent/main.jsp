<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
tr {
	height: 40px;
}

td {
	padding: 10px;
}
</style>
</head>
<body>

<!-- 不推荐使用这种方法，页面过多则容易遗漏 -->
<%-- 	<c:if test="${empty sessionScope.user }">
		<c:redirect url="/login.jsp"></c:redirect>
	</c:if> --%>

	<form action="${pageContext.request.contextPath}/query.udo"
		method="post">
		<table
			style="margin-left: 100px; padding: 50px; border: 1px #ccc solid; width: 400px;">
			<tr>
				<td style="text-align: right;">用户名:</td>
				<td style="text-align: left"><input type="text" name="username" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">用户地址:</td>
				<td style="text-align: left"><input type="text" name="address" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">用户电话:</td>
				<td style="text-align: left"><input type="text" name="phoneNo" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input
					type="submit" value="查询用户" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><a
					href="${pageContext.request.contextPath}/add.jsp">注册新用户</a></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><a
					href="${pageContext.request.contextPath}/logout.udo">注销用户</a></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><a href="${pageContext.request.contextPath}/online.udo">在线用户</a></td>
			</tr>
		</table>
	</form>

	<table style="margin-left: 100px; padding: 50px;" border="1"
		cellpadding="0" cellspacing="0">
		<tr>
			<td>用户id</td>
			<td>用户名称</td>
			<td>用户密码</td>
			<td>用户电话</td>
			<td>用户地址</td>
			<td>注册日期</td>
			<td>操作记录</td>
		</tr>
		<c:if test="${not empty userList}">
			<c:forEach items="${userList}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.username }</td>
					<td>${user.pasword }</td>
					<td>${user.phoneNo }</td>
					<td>${user.address }</td>
					<td>${user.regDate }</td>
					<td><a href="${pageContext.request.contextPath}/update.udo?id=${user.id}">编辑</a>
					    <a href="${pageContext.request.contextPath}/delete.udo?id=${user.id}">删除</a></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<br>
	<br>
</body>
</html>