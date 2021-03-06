<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="container-marketing">
	<input type="hidden" id="merchantCode" value="${merchantCode }" />
	<input type="hidden" id="cardNumber" value="${merchantInfo.cardNumber }" />
	<input type="hidden" id="bankNumber" value="${merchantInfo.bankNumber }" />
	<input type="hidden" id="realName" value="${merchantInfo.realName }" />
	<input type="hidden" id="groupCode" value="${groupCode}" />
	<div class="order-tab">
		<ul class="fl">
			<li class="fl current">支付绑定</li>
		</ul>

	</div>
	<div class="pay-bind-box">
		<div class="breadcrumbs">
			<ul class="fl book-sum">
				<li class="current" data-type="wx">
					<a href="javascript:void(0);">微信</a>
				</li>
				<li class=""  data-type="zfb">
					<a href="javascript:void(0);">支付宝</a>
				</li>
			</ul>
		</div>
		<div class="sub-pay-bind-box" id="auditMsgBlance">
			
			
		</div>
	</div>
</div>
<!--加载中 -->
<div class="import_loading" style="display: none;margin-left:0">
	<div>
		<img alt="" src="${resourcePath}/images/loading.gif">
		<p>正在进件中...</p>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	bindingWXBlance(merchantId,"wx")
})
	//数据报表类型选择
	$(".pay-bind-box .breadcrumbs li").click(function()
	{
		var index = $(this).index();
		$(this).addClass("current").siblings("li").removeClass("current");
		$(".sub-pay-bind-box>div").eq(index).show().siblings("div").hide();
		var type =   $(this).data("type");
		if(type == "wx"){
			bindingWXBlanceAudit()
		}else if(type == "zfb")  {
			bindingZFBBlanceAudit()
		}
	})
</script>