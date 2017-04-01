<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta name="viewport" content="user-scalable=0" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>支付</title>
    <jsp:include page="../../common.jsp"></jsp:include>
</head>

<script type="text/javascript">
    require('js/wap/payCode.js');
    require('js/jquery/jquery-1.10.1.min.js');

    requireCss('css/style.css');
</script>

<body style="background:#F3F6F5;">
<!-- 注册成功 -->
<div class="consume" style="margin-top: 0;">
    <div class="top">消费总金额</div>
    <div class="middle">
        <input type="text" name="" id="amount" readonly="readonly" value="" placeholder="请输入金额">
    </div>
    <div class="bottom">
    </div>
    <div class="consume-grid">
        <div data-type="buttn_1"><span>1</span></div>
        <div data-type="buttn_2"><span>2</span></div>
        <div data-type="buttn_3"><span>3</span></div>
        <div data-type="buttn_delete"><img src="${resourcePath }/images/detele.png" alt=""></div>
        <div data-type="buttn_4"><span>4</span></div>
        <div data-type="buttn_5"><span>5</span></div>
        <div data-type="buttn_6"><span>6</span></div>
        <div data-type="buttn_empty"><span>清空</span></div>
        <div data-type="buttn_7"><span>7</span></div>
        <div data-type="buttn_8"><span>8</span></div>
        <div data-type="buttn_9"><span>9</span></div>
        <div data-type="buttn_zfb"><img src="${resourcePath }/images/zhifubao.png" alt=""></div>
        <div data-type="buttn_0" class="w50"><span>0</span></div>
        <div data-type="buttn_point"><span>.</span></div>
        <div data-type="buttn_wx"><img src="${resourcePath }/images/weixin.png" alt=""></div>
    </div>
</div>
<%--<input type="text" id="amount" value="1"/>--%>
<%--<input type="button" onclick="gotoWXCode()" value="支付" />--%>
<input type="hidden" id="merchantCode" value="${merchantCode}"/>
</body>
<script type="text/javascript">
    $(function () {
        // 判断 是否是微信浏览器
//        if (is_weixin()) {
//            alert("微信浏览器")
//        }else{
//            alert("不是微信浏览器")
//        }
    })
    
    
    $(".consume-grid>div ").click(function () {
            var type = $(this).data("type");
            var merchantCode = $("#merchantCode").val();
            var amount = $("#amount").val();


        switch (type) {
            case "buttn_1":
                passButton(amount,"1");
                    break;
                case "buttn_2":
                    passButton(amount,"2");
                    break;
                case "buttn_3":
                    passButton(amount,"3");
                    break;
                case "buttn_4":
                    passButton(amount,"4");
                    break;
                case "buttn_5":
                    passButton(amount,"5");
                    break;
                case "buttn_6":
                    passButton(amount,"6");
                    break;
                case "buttn_7":
                    passButton(amount,"7");
                    break;
                case "buttn_8":
                    passButton(amount,"8");
                    break;
                case "buttn_9":
                    passButton(amount,"9");
                    break;
                case  "buttn_0" :
                    if (amount.indexOf(".") > 0) {
                        var count = amount.substring(amount.indexOf(".") + 1, amount.length).length
                        if (count < 2) {
                            $("#amount").val(amount + "0");
                        } else {
                            alert("您输入的金额不能小于分")
                        }
                    }else if(amount.indexOf("0") != 0){
                        $("#amount").val(amount + "0");
                    }
                    break;
                case "buttn_empty" :
                    amount = ""
                    $("#amount").val("");
                    break;
                case "buttn_delete" :
                    amount = amount.substring(0, amount.length - 1)
                    $("#amount").val(amount);
                    break;
                case "buttn_zfb"  :
                    if(amount == ""){
                        alert("请输入金额");
                        break
                    }
                    if (amount > 50000) {
                        alert("已超过支付宝每日限额。");
                        break
                    }
                    var url = ctx + "/codeToPay!payCode.do?merchantCode=" + merchantCode + "&amount=" + amount + "&type=API_ZFBQRCODE";
                    window.location = url;
                    break;
                case "buttn_wx"  :
                    if(amount == ""){
                        alert("请输入金额");
                        break
                    }
                    var url = ctx + "/codeToPay!payCode.do?merchantCode=" + merchantCode + "&amount=" + amount + "&type=API_WXQRCODE";
                    window.location = url;
                    break;
                case  "buttn_point" :
                    if (amount == "") {
                        amount = "0.";
                        $("#amount").val("0.");
                    }
                    if (amount.indexOf(".") < 0) {
                        $("#amount").val(amount + ".");
                    }
                    break;
            }

        }
    )

    function passButton(amount,number) {
        if (amount.indexOf(".") > 0) {
            var count = amount.substring(amount.indexOf(".") + 1, amount.length).length
            if (count < 2) {
                $("#amount").val(amount + number);
            } else {
                alert("您输入的金额不能小于分")
            }
        } else {
            $("#amount").val(amount + number);
            if (amount.indexOf("0") == 0) {
                amount = $("#amount").val();
                amount = amount.substring(1, amount.length);
                $("#amount").val(amount);
            }
        }
    }

    function is_weixin(){
        var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i)=="micromessenger") {
            return true;
        } else {
            return false;
        }
    }
</script>
</html>