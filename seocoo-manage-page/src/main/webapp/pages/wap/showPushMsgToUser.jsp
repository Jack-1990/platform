<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="keywords" content="">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<% response.setHeader("Access-Control-Allow-Origin", "*"); %>
<title>显示消息</title>
<jsp:include page="../../common.jsp"></jsp:include>

<script type="text/javascript">
	require('js/jquery/jquery-1.10.1.min.js');
	
	requireCss('css/style.css');
</script>

</head>
<body style="background:#F3F6F5;">
    <div class="message_box">
        <h1>${pushMsg.msgTitle }</h1>
        <p><fmt:formatDate pattern="yy-MM-dd HH:mm:ss" value="${pushMsg.createTime }"/></p>
        <p>${pushMsg.msgCotent }
        </p>
    </div>
</body>
</html>