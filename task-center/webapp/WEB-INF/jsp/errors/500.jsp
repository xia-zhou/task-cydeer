<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="imagetoolbar" content="no">
<link rel="icon" type="image/x-icon" href="<c:url value="/resources/icons/favicon.ico" />">
<link rel="shortcut icon" type="image/x-icon" href="${ctx}/resources/icons/favicon.ico">
  <meta name="robots" content="noindex,nofollow">
  <title>系统内部错误</title>

		<style>
		body {background: #f9fee8;margin: 0; padding: 20px; text-align:center; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#666666;}
		.error_page {width: 600px; padding: 50px; margin: auto;}
		.error_page h1 {margin: 20px 0 0;}
		.error_page p {margin: 10px 0; padding: 0;}		
		a {color: #9caa6d; text-decoration:none;}
		a:hover {color: #9caa6d; text-decoration:underline;}
		</style>

</head>

<body>
  <div class="error_page">
    <img alt="Kidmondo_face_sad" src="${ctx}/resources/images/error_page.gif">
    <h1>很抱歉！</h1>
    <p>系统出现内部错误！</p>
    <p><a href="${ctx}/home">返回到首页</a></p>
 </div>
</body>
</html>