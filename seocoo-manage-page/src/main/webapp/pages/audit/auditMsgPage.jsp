<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.sales-top>div label{
 width: 100px;
}
.sales-top>div span{
    font-size: 15px;
}
</style>
<div class="container-marketing">
	<div class="order-tab">
		<ul class="fl">
			<li class="fl current">资料审核</li>
		</ul>
		<div class="fr">
			<a href="javascript:void(0);" onclick="loadContent('/audit.do')">
				<span><i class="icon-reply"></i></span>返回
			</a>
		</div>
	</div>
	<div>
		<div class="muti-marketing">
			<input type="hidden" name="merchant.merchantCode" value="${merchantInfo.merchantCode }" />
			<div class="sales-top">
				<div class="fl">
					<label class="fl">外部商户号:</label>
					<span class="fl">${merchantInfo.merchantCode }</span>
				</div>
				<div class="fl">
					<label class="fl">商户简称:</label>
					<span class="fl">${merchantInfo.realName }</span>
				</div>
				<div class="fl">
					<label class="fl">手机号:</label>
					<span class="fl">${merchantInfo.createUser }</span>
				</div>
				<div class="fl">
					<label class="fl">证件号:</label>
					<span class="fl">${merchantInfo.idNumber }</span>
				</div>
				<div class="fl">
					<label class="fl">商户等级:</label>
					<span class="fl">${merchantInfo.attrName }</span>
				</div>
				<div class="fl">
					<label class="fl">父商户:</label>
					<span class="fl">${merchantInfo.parentMerchantCode }</span>
				</div>
				<div class="fl">
					<label class="fl">地址:</label>
					<span class="fl">${merchantInfo.address }</span>
				</div>
				<div class="fl">
					<label class="fl">提交时间:</label>
					<span class="fl"><fmt:formatDate value="${merchantInfo.submitAuditTime }" type="both"/></span>
				</div>
			</div>
			<div class="sales-top">
				<div class="fl">
					<label class="fl">银行卡号:</label>
					<span class="fl">${merchantInfo.cardNumber }</span>
				</div>
				<div class="fl">
					<label class="fl">所属银行:</label>
					<span class="fl">${merchantInfo.affiliatedBank }</span>
				</div>
				<div class="fl">
					<label class="fl">开户地区:</label>
					<span class="fl">${merchantInfo.bankArea }</span>
				</div>
				<div class="fl">
					<label class="fl">开户银行:</label>
					<span class="fl">${merchantInfo.bankNumber }</span>
				</div>

			</div>
			<div class="sales-top">
				<div class="fl">
					<label class="fl">身份证正面:</label>
					<div class="fl position">
						<img src='${merchantInfo.ID_before_pic}'>
					</div>
				</div>
				<div class="fl">
					<label class="fl">身份证反面:</label>
					<div class="fl position">
						<img src='${merchantInfo.ID_after_pic}'>
					</div>
				</div>
			</div>
			<div class="sales-top">
				<div class="fl">
					<label class="fl">银行卡正面:</label>
					<div class="fl position">
						<img src='${merchantInfo.bank_before_pic}'>
					</div>
				</div>

				<div class="fl">
					<label class="fl">银行卡反面:</label>

					<div class="fl position">
						<img src='${merchantInfo.bank_after_pic}'>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="bind-box textc">
	<a href="javascript:void(0);" onclick="auditSuccess(this)" class="bind-btn">通过</a>
	<a href="javascript:void(0);" onclick="auditFail(this)" class="bind-btn">不通过</a>
	<input type="hidden" id="merchantCode" value="${merchantInfo.merchantCode }" />
	<input type="hidden" id="level" value="${merchantInfo.level }" />
</div>
<!--加载中 -->
<div class="import_loading" style="display: none; margin-left:0">
	<div>
		<img alt="" src="${resourcePath}/images/loading.gif">
		<p>正在进件中...</p>
	</div>
</div>
<!-- 审核不通过 -->
<div class="pop_up_box pop_up_boxAudit" style="display: none;">
	<!--黑色遮罩-->
	<div class="sweet-overlay" tabindex="-1"></div>
	<div class="edit_box">
		<p class="add_type_title">审核不通过提示:</p>
		<textarea type="text" class="add_type_input" style="height: 100px" id="warnMsg" placeholder="提示信息">${merchantInfo.realName  } 您好：很遗憾您的资料审核未通过</textarea>
		<div class="btn_box">
			<a href="javascript:void(0)" class="blue">取消</a>
			<a href="javascript:void(0)" onclick="sendMsg(this)" class="red">确认</a>
		</div>
	</div>
</div>