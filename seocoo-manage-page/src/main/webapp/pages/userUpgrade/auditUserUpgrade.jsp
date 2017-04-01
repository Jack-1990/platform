<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<
<style>
.sales-top>div label {
	width: 100px;
}

.sales-top>div span {
	font-size: 15px;
}
</style>
<div class="container-marketing">
	<div class="order-tab">
		<ul class="fl">
			<li class="fl current">升级审核</li>
		</ul>
		<div class="fr">
			<a href="javascript:void(0);" onclick="loadContent('/userUpgrade.do')">
				<span><i class="icon-reply"></i></span>返回
			</a>
		</div>
	</div>
	<div>
		<div class="muti-marketing">
			<input type="hidden" name="merchant.merchantCode" value="${userUpgrade.merchantCode }" />
			<div class="sales-top">
				<div class="fl">
					<label class="fl">外部商户号:</label>
					<span class="fl">${userUpgrade.merchantCode }</span>
				</div>
				<div class="fl">
					<label class="fl">商户简称:</label>
					<span class="fl">${userUpgrade.merchantName }</span>
				</div>
				<div class="fl">
					<label class="fl">手机号:</label>
					<span class="fl">${userUpgrade.applyUser }</span>
				</div>
				<div class="fl">
					<label class="fl">升级方式:</label>
					<span class="fl"><c:if test="${userUpgrade.attrStyle == 'dues'}">
								续费升级
							</c:if> <c:if test="${userUpgrade.attrStyle == 'member'}">
								发展升级
							</c:if></span>
				</div>
				<div class="fl">
					<label class="fl">商户等级:</label>
					<span class="fl">${userUpgrade.fromLevelName }</span>
				</div>
				<div class="fl">
					<label class="fl">升级级别:</label>
					<span class="fl">${userUpgrade.toLevelName }</span>
				</div>
				<c:if test="${userUpgrade.attrStyle == 'member'}">
				<div class="fl">
					<label class="fl">发展升级条件:</label>
					<span class="fl">${userUpgrade.description }</span>
				</div>
				</c:if>
			</div>
			<div class="sales-top">
				<div class="fl">
					<label class="fl" style=" font-size: 18px; ">发展会员:</label>
					<span class="fl">${totalCount}</span>
				</div>
				<div class="fl">
				</div>
				<c:forEach items="${dimDicAttrValueList }" var="dimDicAttrValue">
					<div class="fl">
						<label class="fl">${dimDicAttrValue.attrName }:</label>
						<span class="fl">${dimDicAttrValue.count }</span>
					</div>
				</c:forEach>
			</div>
			<c:if test="${userUpgrade.attrStyle == 'dues'}">
				<div class="sales-top">
					<div class="fl">
						<label class="fl">缴费金额:</label>
						<span class="fl">${userUpgrade.attrValue }</span>
					</div>
						<div class="fl">
					<label class="fl">缴费时间:</label>
					<span class="fl"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${userUpgrade.duesTime }" /></span>
				</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
<div class="bind-box textc">
	<c:if test="${userUpgrade.attrStyle == 'dues'}">
	<a href="javascript:void(0);" onclick="auditUserUpgradeSuccess(this)" class="bind-btn">通过</a>
	</c:if>
	<c:if test="${userUpgrade.attrStyle == 'member'}">
		<a href="javascript:void(0);" onclick="auditUserUpgradeSuccess(this)" class="bind-btn">通过</a>
		<a href="javascript:void(0);" onclick="auditUserUpgradeFail(this)" class="bind-btn">不通过</a>
	</c:if>
	<input type="hidden" id="merchantCode" value="${userUpgrade.merchantCode }" />
	<input type="hidden" id="applyCode" value="${userUpgrade.applyCode }" />
</div>
<!--加载中 -->
<div class="import_loading" style="display: none;margin-left:0">
	<div>
		<img alt="" src="${resourcePath}/images/loading.gif">
		<p>正在进件中...</p>
	</div>
</div>
<!-- 审核不通过 -->
<div class="pop_up_box pop_up_boxUserUp" style="display: none;">
	<!--黑色遮罩-->
	<div class="sweet-overlay" tabindex="-1"></div>
	<div class="edit_box">
		<p class="add_type_title">升级不通过提示:</p>
		<textarea type="text" class="add_type_input" style="height: 100px" id="warnMsg" placeholder="提示信息"> ${userUpgrade.merchantName } 您好：很遗憾您的升级审核未通过</textarea>
		<div class="btn_box">
			<a href="javascript:void(0)" class="blue">取消</a>
			<a href="javascript:void(0)" onclick="sendUserUpMsg(this)" class="red">确认</a>
		</div>
	</div>
</div>