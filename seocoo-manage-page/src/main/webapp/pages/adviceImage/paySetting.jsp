<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- app设置 -->
<div class="app sub" id="paySetting">
    <h1>
        <span class="fl">支付设置</span>
    </h1>
    <div class="sub_pay_setting">
        <div class="pay_left fl member_pay_left mt20">
            <div class="font14 other textc current">我的 SN 码</div>
            <div class="font14 other textc">我的支付二维码</div>
            <p style="height: 300px;"></p>
        </div>
        <div id="menuContent">

        </div>
    </div>
</div>
<input type="hidden" id="merchantCode" value="${merchantCode }">
<script type="text/javascript">
    $(function () {
        gotoSNSeting();
          //左侧菜单栏点击
        var hasPayChannel = '${hasPayChannel}';
        $("#paySetting .member_pay_left div").click(function () {
            var index = $(this).index();
            var merchantCode = $("#merchantCode").val();


            if (index == 1) {
                if (hasPayChannel == "has") {
                    var url = ctx + "/paySetting!staticQRCode.do?merchantCode=" + merchantCode;
                    $('#menuContent').load(url);
                } else {
                    alert("您未开通支付通道，不能生成付款二维码")
                    return false;
                }
            }
            if (index == 0) {
                gotoSNSeting();
            }
            $(this).addClass("current").siblings("div").removeClass("current");
        })

    })


    function gotoSNSeting() {
        var merchantCode = $("#merchantCode").val();
        var url = ctx + "/paySetting!snSeting.do?merchantCode=" + merchantCode;
        $('#menuContent').load(url);
    }
</script>