<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<body style="background:#4180cd;">
<div  style="align:center;margin-left:2%;margin-top:2%; display: none " id="wechatQrcode" class="allQrcode" content="${resultMsg.qrCodeUrl}"></div>
<!-- header -->
<header>
    <div class="navbar">
        <p>付款</p>
    </div>
</header>
<!-- 收款 -->
<div class="expense">
    <div class="two-code">
        <div id="convertedImg"></div>
    </div>
    <div class="cash">￥${amount}元</div>
    <div class="tips">请长按上方二维码完成付款</div>
</div>

</body>
<input type="hidden" id="payCodeUrl" value="${resultMsg.qrCodeUrl}"/>
<input type="hidden" id="merchantCode" value="${merchantCode}"/>
<input type="hidden" id="orderNumber" value="${resultMsg.orderNumber}"/>
<input type="hidden" id="amount" value="${amount}"/>
<input type="hidden" id="type" value="${type}"/>
<script type="application/javascript">
    $(function () {
        var content = $("#payCodeUrl").val();
        var type = "${type}";
        var img = ""
        if (type == "API_WXQRCODE"){
            img = "/images/weChat.png";
        }else{
            img = "/images/aliPay.png";
            // 轮训 order
            var merchantCode = $("#merchantCode").val();
            var orderNumber = $("#orderNumber").val();
            var ur = ctx + "/codeToPay!getZFBOrderStuts.do";
            $(".expense").html("<div class='cash' style='margin-top: 50%;' >正在付款中...</div>");
            $.post(ur,{"merchantCode":merchantCode,"orderNumber":orderNumber},function () {
              window.location = $("#payCodeUrl").val();
            });
            return false ;
        }
        jQuery('#wechatQrcode').qrcode({
            render: "canvas", //table
            correctLevel: 1,
            width: 700, //宽度
            height: 700, //高度
            text: content, //内容
            src: resourcePath + img,         //二维码中间的图片
            //src: ctx + $("#homePageUrl").val()//中文会乱码
        });
        var myCanvas = document.getElementsByTagName("canvas")[0];
        var img = convertCanvasToImage(myCanvas);
        $("#convertedImg").append(img);

    })
    var count = 0 ;
    var sh = setInterval(function(){
        getOrderMsg();
        count++;
    },3000);

    // canvas-->image
    function convertCanvasToImage(canvas){
        //新Image对象,可以理解为DOM;
        var image = new Image();
        //canvas.toDataURL返回的是一串Base64编码的URL,当然,浏览器自己肯定支持
        //指定格式PNG
        image.src = canvas.toDataURL("image/png");
        return image;
    }

    /**
     * 查询订单结果
     */
    function getOrderMsg() {
        console.log(count)
        if(count>100){
            clearInterval(sh);
            alert("二维码已经失效，请刷新页面")
            return false ;
        }

        var url =  ctx + "/codeToPay!getOrderMsg.do?"
        var merchantCode = $("#merchantCode").val();
        var orderNumber = $("#orderNumber").val();
        var amount = $("#amount").val();
        var type = $("#type").val();
//        var url = ctx + "/codeToPay!getOrderMsg.do?merchantCode="+merchantCode+"&orderNumber="+orderNumber+"&amount="+amount+"&type="+type+"&random="+Math.random();
//        window.location = url;
        $.post(url,{"merchantCode":merchantCode,"orderNumber":orderNumber,"amount":amount,"type":type},function (data) {
            var resultCode =  data.resultCode;
            if(resultCode == "SUCCESS"){
                var url = ctx + "/codeToPay!resultSuccess.do?merchantCode="+merchantCode+"&orderNumber="+orderNumber+"&amount="+amount+"&type="+type;
                window.location = url;
            }
        })
    }



</script>
</body>
</html>