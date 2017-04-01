<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>果果钱包</title>
<jsp:include page="../../common.jsp"></jsp:include>

<script type="text/javascript">
	require('js/jquery/jquery-1.10.1.min.js');
	
	require('js/wap/merchantRegister.js');
	requireCss('css/upst.css');
	requireCss('css/uploadApp.css');
</script>
</head>
<body style="background:#38c3ec;">
 <div class="header">
		<img src="${resourcePath }/images/bg.png">
		<div class="logo">
			<img src="${resourcePath }/images/paylogo.png">
			<p class="lit_title">果果钱包</p>
		</div>
		<div class="header_foot">
			<h4>已有<span>200万</span>用户加入</h4>
			<p>互联网支付+移动金融 的创业平台</p>
			<p>简单分享创造持续高收入</p>
		</div>
</div>
<div class="footer" style="height: 452px;">
  		<div class="ios" style="margin-top: -20.5px;">
   		<a id="IOSUrl" href="https://itunes.apple.com/cn/app/yun-fu-quan-min-chuang-fu/id1138176125?mt=8">
   		<img id="wg03" src="${resourcePath }/images/ios.png" style="width: 80%;">
   		</a>
  		</div>
	<div class="android" style="margin-top: -20.5px;">
		<a id="androidUrl" href="http://a.app.qq.com/o/simple.jsp?pkgname=com.br.yf">
		<img id="wg02" src="${resourcePath }/images/ans.png" style="width: 80%;">
		</a>
	</div>
</div>
<div class="mask"><img style="height: 638px;" src="${resourcePath }/images/67.png"></div>
<div class="mask2"><img style="width:100%;" src="${resourcePath }/images/68.png"></div>
<script type="text/javascript">
	common(2);
</script>

</body>
</html>