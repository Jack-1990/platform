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
       <div class="question">Q：如何快速规范的通过实名验证</div>
       <div class="answer">
          <div>A：</div>
          <div class="right">
          	<div>第一：在我的-实名认证里去填写您的个人资料，没有通过实名认证的则不能使用产品功能，完善资料的第一步先填写基本信息，姓名，身份证，银行卡信息等，因为如果您不填写这些信息，您支付的金额是无法到账的，因为我们不知道要到您的哪张卡里</div>
          	<div>第二：就是要上传您的<span class="blue">身份证照片正面</span>和<span class="blue">银行卡照片正面</span>，<span class="blue">身份证照片反面</span>+<span class="blue">银行卡照片反面</span>，我们的系统会根据银行大数据来自动判断您的信息是否属实。系统会通过公安身份证识别接口，身份证人脸识别接口，银行卡实名6要素认证接口来自动识别实名</div>
            <div>第三：当您完善了这些资料之后，自己再确认无误后，点击上传，我们后台客服人员会进行审核，审核时间一般15分钟到8小时之间（具体视当天注册用户的排队数量而定），审核通过后，退出APP再登录，您的状态会显示为已认证，即可使用全部支付功能， 如遇资料填写不规范的，会有驳回原因并系统会有消息通知到您哪些照片没有正确提交</div>
          </div>
       </div>
    </div>
</div>
</body>
</html>