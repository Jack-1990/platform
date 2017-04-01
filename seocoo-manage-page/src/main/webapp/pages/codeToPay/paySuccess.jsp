<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>支付</title>
    <jsp:include page="../../common.jsp"></jsp:include>
</head>
<script type="text/javascript">
    require('js/jquery/jquery-1.10.1.min.js');
    require('js/jquery/jquery.qrcode.min.js');
    requireCss('css/style.css');
</script>
<body style="background:#F3F6F5;">
<!-- header -->
<header>
    <div class="navbar">
        <%--<a href="javascript:void(0);" class="finish">完成</a>--%>
        <p>付款成功</p>
    </div>
</header>
<!-- 注册成功 -->
<div class="expense-sucess">
    <h1>${resultMsg.amount/100}元</h1>
    <div>交易成功</div>
    <div class="type">
        <span class="fl">付款方式</span>
        <c:if test="${ resultMsg.selectTradeType ==  'API_WXQRCODE'}">
            <span class="fr">微信</span>
        </c:if>
        <c:if test="${ resultMsg.selectTradeType ==  'API_ZFBQRCODE'}">
            <span class="fr">支付宝</span>
        </c:if>

    </div>
    <div>
        <span class="fl">创建时间</span>

        <span class="fr"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate> </span>
    </div>
</div>
</body>

</html>