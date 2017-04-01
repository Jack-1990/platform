<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="keywords" content="">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<% response.setHeader("Access-Control-Allow-Origin", "*"); %>
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
    <!-- 短信登录 -->
    <form action="" autocomplete="off" method="post" class="message_form">
        <dl class="message_list">
            <dd class="dd-padding">
                <div class="input-weak">
                    <input type="text" placeholder="请输入您的手机号码" maxlength="11"  id="phone" onkeyup="checkIsNum(this,event);">
                </div>
            </dd>
             <dd class="dd-padding verification">
                <div class="input-weak1">
                    <input type="text" placeholder="请输入校验码" id="verImgCode" maxlength="4" onkeyup="checkAccountNum(this,event);">
                </div>
                <div class="fr ehong">
                     <span class="code"></span>
                </div>
            </dd>
            <dd class="dd-padding">
                <div class="input-weak1">
                    <input type="text" placeholder="请输入验证码" name="password" id="verifyCode"  maxlength="4" onkeyup="checkIsNum(this,event);">
                </div>
                <div class="pic">
                   <a href="javascript:void(0);" title="" class="current" onclick="getVerifiCode();">获取验证码</a>
                </div>
            </dd>
            <dd class="dd-padding">
                <div class="input-weak">
                    <input type="password" placeholder="请设置6~16位密码" name="password" maxlength="16"  id="password" onkeyup="checkAccountNum(this,event);">
                </div>
            </dd>
        </dl>
         <div class="btn-wrapper">
           <p>请先注册账号，然后下载APP即可直接登录</p>
        </div>
        <div class="btn-wrapper">
            <a href="javascript:void(0);" onclick="register();">注册</a>
        </div>
         <div class="btn-wrapper">
            <a href="javascript:void(0);" onclick="downApp();">下载APP</a>
        </div>
    </form>
    <input id="parentUser" type="hidden" value="${parentUser}">
    
<script type="text/javascript"> 
    createCode();
</script>
</body>
</html>