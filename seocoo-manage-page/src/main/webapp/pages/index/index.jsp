<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="keywords" content="">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<% response.setHeader("Access-Control-Allow-Origin", "*"); %>
<title>支付通道</title>
<jsp:include page="../../common.jsp"></jsp:include>
<style>

@font-face {
  font-family: 'FontAwesome';
  src: url('${ctx }/pages/fonts/fontawesome-webfont.eot?v=3.2.1');
  src: url('${ctx }/pages/fonts/fontawesome-webfont.eot?#iefix&v=3.2.1') format('embedded-opentype'), url('${ctx }/pages/fonts/fontawesome-webfont.woff?v=3.2.1') format('woff'), url('${ctx }/pages/fonts/fontawesome-webfont.ttf?v=3.2.1') format('truetype'), url('${ctx }/pages/fonts/fontawesome-webfont.svg#fontawesomeregular?v=3.2.1') format('svg');
  font-weight: normal;
  font-style: normal;
}
#ui-datepicker-div{
   display:none;
}
</style>
<script type="text/javascript">
	require('js/jquery/swiper.min.js');			
    require('js/jquery/jquery.min.js');
	require('js/index/index.js');
	require('js/index/inputCheck.js');
	require('js/index/common.js');
	require('js/jquery/jquery-1.10.1.min.js');
	require('js/upload/jquery.form.js');
	require('js/upload/upload.js');
	require('js/payRate/payRate.js');
	require('js/payRate/md5.js');
	require('js/jquery/jquery-ui.min.js');
	require('js/jquery/jquery.qrcode.min.js');
	require("js/jquery/jquery.pagination.js");
	
	require("js/merchant/merchant.js");
	require("js/audit/audit.js");
	require("js/withdrawCash/withdrawCash.js");
	require("js/userUpgrade/userUpgrade.js");
	require("js/merchant/groupMer.js");

	require('js/group/group.js');
	require('js/transaDetail/transaDetail.js');
	requireCss('css/reset.css');
	requireCss('css/pc.css');
	requireCss('css/main.css');
	requireCss('css/index.css');
	requireCss('css/font-awesome.css');
	requireCss('css/pagination.css');
	requireCss('css/common.css');

</script>

</head>
<body style="background:#E8EDF1;">
      <!-- 头部 -->
    <div class="navbar">
         <div class="fr">
             <div class="navbar-title fl">
                 <a href="javascrpit:void(0);">
                     <img src="${resourcePath}/images/title.png" alt=""><span>支付通道</span>
                 </a>
             </div>
             <div class="navbar-exit fl">
                 <a href="${ctx }/index!logout.do">
                     <img src="${resourcePath}/images/exit.png" alt="">
                     <span>退出</span>
                 </a>
             </div>
         </div>
     </div>
     
        
     <!-- 侧导航栏 -->
    <div class="slidebar">
        <div class="slidebar-top">
            <img src="${resourcePath}/images/dianha.png" alt="">
            <a href="javascript:void(0);"><span>${su.name }</span>&nbsp;&nbsp;&nbsp;<i class="icon-caret-right"></i></a>
        </div>
        <div class="slidebar-list">
            <ul>
               
            </ul>
        </div>
    </div>
    
	
	<!-- 右侧内容 -->
    <div class="container" id="shop_container">
	      <!-- 首页 -->
	</div>
	
		
<script type="text/javascript">
$(function(){
	$(".slidebar-top a").click(function(){
		var url = ctx + '/index!accountSet.do';
		$('#shop_container').load(url);	
	})
})
</script>

</body>
</html>