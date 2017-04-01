<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<jsp:include page="../../common.jsp"></jsp:include>

<script type="text/javascript">
	requireCss('css/style.css');
</script>

</head>
<body style="background:#F3F6F5;">
 <div class="instruction">
    <div class="clearfix">
       <div class="question">Q：如何支付</div>
       <div class="answer">
          <div>A：</div>
          <div class="right">
          	<div>第一：收款，点击收款，输入金额，选择微信或支付宝，扫描消费者付款码，即可完成收款</div>
          	<div>第二：收款码，点击收款码，输入金额，选择微信或支付宝，消费者扫描收款码，即可完成收款</div>
          </div>
       </div>
    </div>
</div>
</body>
</html>