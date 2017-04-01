<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>果果钱包</title>
<jsp:include page="../../common.jsp"></jsp:include>

<script type="text/javascript">
	require('js/jquery/jquery-1.10.1.min.js');
	
	require('js/wap/merchantRegister.js');
	requireCss('css/style.css');
</script>

</head>
<body style="background:#F3F6F5;">
    <!-- header -->
    <header>
        <div class="navbar">
            <p>果果钱包注册</p>
        </div>
    </header>
    <!-- 注册成功 -->
		<div class="message_form">
		<div class="register-success-icon">
			<img src="${resourcePath }/images/register-success.png" alt="">
		</div>
		<div class="register-success-text">
			<h1>恭喜您,您已注册成功！</h1>
			<h1>请马上下载果果钱包软件</h1>
		</div>
         <div class="btn-wrapper">
            <a href="javascript:void(0);" onclick="downApp();">下载果果钱包</a>
        </div>
		</div>

</body>
</html>