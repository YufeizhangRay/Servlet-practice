<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<script type="text/javascript">
	/* 不推荐，这种逻辑应该交给拦截器完成 */
	/* function getCookie(c_name){
		if(document.cookie.length > 0){
			var c_start = document.cookie.indexOf(c_name+"=");
			c_start = c_start + c_name.length+1;
			var c_end = document.cookie.indexOf(";",c_start);
			if(c_end==-1) c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start,c_end));
		}
	}
	window.onload=function(){ 
		var form = document.getElementById("loginform");
		var useranme = document.getElementById("username");
		if(getCookie("userKey")!="" && getCookie("userKey")!=null && getCookie("ssid")!="" && getCookie("ssid")!=null){
			useranme.value = getCookie("userKey");
			form.submit();
		}
	} */ 
</script>
</head>
<body>
	<br><br>
	<c:if test="${not empty requestScope.note}">
		<span style="color: red; font-weight: bolder;">${note}</span>
	</c:if>
	<br>
	<form id="loginform" action="<%=request.getContextPath()%>/login.udo" method="get">
		用户名:<input id="username" type="text" name="username" value="" /> <br><br> 
		密　码:<input type="text" name="pasword" /> <br> <br>
		记住我一周<input type="radio" name="expiredays" value="7"> 
		记住我一月<input name="expiredays" type="radio" value="30"> 
		记住我到永远<input name="expiredays" type="radio" value="100"> <br> <br>
		<input type="submit" value="登录" />
	</form>
</body>
</html>