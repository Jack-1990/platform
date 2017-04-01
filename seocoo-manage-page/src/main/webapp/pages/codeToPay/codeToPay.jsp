<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="initial-scale1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>支付</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <jsp:include page="../../common.jsp"></jsp:include>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        html {
            font-size: 100px;
        }

        body {
            font-family: "微软雅黑";
        }

        .pay {
            padding: 0.2rem;
        }

        .top {
            overflow: hidden;
            padding: 0.3rem 0;
            border-bottom: 1px solid #ccc;
        }

        .top img {
            width: 0.45rem;
            height: 0.45rem;
        }

        .top .fl span {
            font-size: 0.18rem;
            vertical-align: top;
            line-height: 0.45rem;
        }

        .top .fr span {
            font-size: 0.15rem;
            display: block;
            line-height: 0.225rem;
        }

        .fl {
            float: left;
        }

        .fr {
            float: right;
        }

        .textc {
            text-align: center;
        }

        .textr {
            text-align: right;
        }

        .mt20 {
            margin-top: 0.2rem;
        }

        .middle {
            margin-top: 0.3rem;
            background: #fff;
            border-radius: 0.1rem;
            padding: 0.3rem 0.1rem;
            color: #5e5e5e;
            font-size: 0.16rem;
        }

        input {
            border: none;
            width: 1.5rem;
            border-bottom: 1px solid #ccc;
            padding: 0.05rem;
            outline: none;
            font-size: 0.2rem;
            font-weight: 600;
        }

        .middle a {
            display: block;
            background: rgba(22, 155, 213, 1);
            margin: 0 auto;
            color: #fff;
            border-radius: 0.05rem;
            padding: 0.1rem 0;
            text-decoration: none;
            margin-top: 0.2rem;
        }

        .tips {
            margin-top: 0.3rem;
        }

        .bottom {
            font-size: 0.18rem;
            color: red;
            margin-top: 0.3rem;
        }

        @media screen and (max-width: 320px) {
            html {
                font-size: 90px;
            }

            input {
                width: 1.2rem;
            }
        }
    </style>
    <script type="text/javascript">
        require('js/jquery/jquery-1.10.1.min.js');

    </script>
</head>

<body style="background:#F3F6F5;">
<div class="pay">
    <div class="top">
        <div class="fl">
            <img src="${resourcePath}/images/paylogo.png" class="fl">
            <span class="fl">付款给商家</span>
        </div>
        <div class="fr">
                <span> 果果钱包官网： </span>
            <span><a href="www.guoguof.com">www.guoguof.com</a></span>
        </div>
    </div>
    <div class="middle">
        <div>
            <span>请输入支付金额</span>
            <input type="text" id="amountNumber" maxlength="10" onkeyup="checkedNumber(this)" name="" value="" class="textr">
            <span>元</span>
        </div>
        <div>
            <a href="javascript:void(0);" onclick="submitAmount()" class="textc">确认付款</a>
        </div>
        <div class="textc tips">银行通道 资金安全</div>
        <div class="textc">到账时间：T1 上午</div>
    </div>
    <div class="bottom">
        <h3>温馨提示：</h3>
        <div>如支付宝无法交易，请选择使用微信支付</div>
        <h3 class="mt20">咨询电话：18956009769</h3>
        <h3 class="mt20">咨询QQ：2459967035</h3>
    </div>
</div>
<input type="hidden" id="merchantCode" value="${merchantCode}"/>
</div>
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

//
//    $(".consume-grid>div ").click(function () {
//            var type = $(this).data("type");
//            var merchantCode = $("#merchantCode").val();
//            var amount = $("#amount").val();
//
//
//            switch (type) {
//                case "buttn_1":
//                    passButton(amount, "1");
//                    break;
//                case "buttn_2":
//                    passButton(amount, "2");
//                    break;
//                case "buttn_3":
//                    passButton(amount, "3");
//                    break;
//                case "buttn_4":
//                    passButton(amount, "4");
//                    break;
//                case "buttn_5":
//                    passButton(amount, "5");
//                    break;
//                case "buttn_6":
//                    passButton(amount, "6");
//                    break;
//                case "buttn_7":
//                    passButton(amount, "7");
//                    break;
//                case "buttn_8":
//                    passButton(amount, "8");
//                    break;
//                case "buttn_9":
//                    passButton(amount, "9");
//                    break;
//                case  "buttn_0" :
//                    if (amount.indexOf(".") > 0) {
//                        var count = amount.substring(amount.indexOf(".") + 1, amount.length).length
//                        if (count < 2) {
//                            $("#amount").val(amount + "0");
//                        } else {
//                            alert("您输入的金额不能小于分")
//                        }
//                    } else if (amount.indexOf("0") != 0) {
//                        $("#amount").val(amount + "0");
//                    }
//                    break;
//                case "buttn_empty" :
//                    amount = ""
//                    $("#amount").val("");
//                    break;
//                case "buttn_delete" :
//                    amount = amount.substring(0, amount.length - 1)
//                    $("#amount").val(amount);
//                    break;
//                case "buttn_zfb"  :
//                    if (amount == "") {
//                        alert("请输入金额");
//                        break
//                    }
//                    if (amount > 50000) {
//                        alert("已超过支付宝每日限额。");
//                        break
//                    }
//                    var url = ctx + "/codeToPay!payCode.do?merchantCode=" + merchantCode + "&amount=" + amount + "&type=API_ZFBQRCODE";
//                    window.location = url;
//                    break;
//                case "buttn_wx"  :
//                    if (amount == "") {
//                        alert("请输入金额");
//                        break
//                    }
//                    var url = ctx + "/codeToPay!payCode.do?merchantCode=" + merchantCode + "&amount=" + amount + "&type=API_WXQRCODE";
//                    window.location = url;
//                    break;
//                case  "buttn_point" :
//                    if (amount == "") {
//                        amount = "0.";
//                        $("#amount").val("0.");
//                    }
//                    if (amount.indexOf(".") < 0) {
//                        $("#amount").val(amount + ".");
//                    }
//                    break;
//            }
//
//        }
//    )
//
//    function passButton(amount, number) {
//        if (amount.indexOf(".") > 0) {
//            var count = amount.substring(amount.indexOf(".") + 1, amount.length).length
//            if (count < 2) {
//                $("#amount").val(amount + number);
//            } else {
//                alert("您输入的金额不能小于分")
//            }
//        } else {
//            $("#amount").val(amount + number);
//            if (amount.indexOf("0") == 0) {
//                amount = $("#amount").val();
//                amount = amount.substring(1, amount.length);
//                $("#amount").val(amount);
//            }
//        }
//    }

    function is_weixin() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    
    function checkedNumber(obj) {
        // 限制输入的是数字
            obj.value = obj.value.replace(/[^\d.]/g, ""); // 先把非数字的都替换掉，除了数字和.
            obj.value = obj.value.replace(/^\./g, ""); // 必须保证第一个为数字而不是.
            obj.value = obj.value.replace(/^0{2,}/g, "")
            obj.value = obj.value.replace(/\.{2,}/g, "."); // 保证只有出现一个.而没有多个.
//            obj.value = obj.value.replace(/^\.\d{2}/g, ""); // 保证只有出现一个.而没有多个.
            obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", "."); // 保证.只出现一次，而不能出现两次以上
            if (obj.value.indexOf(".")>0){
                obj.value = obj.value.substring(0,obj.value.indexOf(".")+3);
            }
            if (obj.value != "" && obj.value.indexOf(".") < 0){
                obj.value = Number(obj.value);
            }

    }
    function submitAmount() {
        var amountNumber = $("#amountNumber").val();
        var merchantCode = $("#merchantCode").val();
        if (amountNumber == "") {
            alert("请输入金额");
           return false ;
        }
        if (amountNumber > 50000) {
            alert("已超出每日5万支付额。");
            return false ;
        }
        if (is_weixin()) {
            var url = ctx + "/codeToPay!payCode.do?merchantCode=" + merchantCode + "&amount=" + amountNumber + "&type=API_WXQRCODE";
            window.location = url;
        }else {

            var url = ctx + "/codeToPay!payCode.do?merchantCode=" + merchantCode + "&amount=" + amountNumber + "&type=API_ZFBQRCODE";
            window.location = url;
        }

    }
</script>
</html>