<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>尚果科技运营平台</title>
<jsp:include page="common.jsp" />
<link href="http://lingxi.voicecloud.cn/favicon.ico" rel="shortcut icon"/>
<!-- 引用外部库 -->
</head>
<body>

	<%
		request.getRequestDispatcher("/index.do").forward(request, response);
	%>

</body>

</html>
