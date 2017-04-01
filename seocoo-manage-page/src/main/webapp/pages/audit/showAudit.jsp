<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.sales-top>div span{
    font-size: 15px;
}
</style>
<div class="container-marketing">
	<div class="order-tab" style="padding-bottom: 15px;padding-top: 10px">
		<div class="fr">
			<a href="javascript:void(0);" onclick="loadContent('/audit.do')">
				<span><i class="icon-reply"></i></span>返回
			</a>
		</div>
	</div>
	<div>
		<div class="muti-marketing">
			<form action="" method="post" id="addMerchant" name="addMerchant" enctype="multipart/form-data" encoding="multipart/form-data">
				<input type="hidden" name="merchant.groupCode" value="${merchant.groupCode }" />
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
						<c:if test="${merchant.telephone == null}">
							<span class="fl">${merchantInfo.createUser }</span>
						</c:if>
						<c:if test="${merchant.telephone != null}">
							<span class="fl">${merchant.telephone }</span>
						</c:if>
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
							<span class="fl"><fmt:formatDate value="${merchantInfo.submitAuditTime }" type="both" /></span>
					</div>
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">是否持证:</label>
						<c:if test="${merchant.isCert != null }">
								<span class="fl">${merchant.isCert == 0?'非持证商户':'持证商户'}</span>
						</c:if>
					</div>
					<div class="fl">
						<label class="fl">营业执照号:</label>
							<span class="fl">${merchant.licId }</span>
					</div>
					<div class="fl">
						<label class="fl">营业执照有效期:</label>
							<span class="fl">${merchant.licIdValidity }</span>
					</div>
					<div class="fl">
						<label class="fl">法人/联系人:</label>
							<span class="fl">${merchantInfo.realName }</span>
					</div>
					<div class="fl">
						<label class="fl">法人/联系人证件号:</label>
						<span class="fl">	${merchantInfo.idNumber }</span>
					</div>
					<div class="fl">
						<label class="fl">负责人:</label>
							<span class="fl">${merchantInfo.realName }</span>
					</div>
					<div class="fl">
						<label class="fl">负责人手机号:</label>
						<c:if test="${merchant.telephone == null}">
							<span class="fl">${merchantInfo.createUser }</span>
						</c:if>
						<c:if test="${merchant.telephone != null}">
							<span class="fl">${merchant.telephone }</span>
						</c:if>
					</div>
					<div class="fl">
						<label class="fl">客服电话:</label>
						<c:if test="${merchant.telephone == null}">
							<span class="fl">${merchantInfo.createUser }</span>
						</c:if>
						<c:if test="${merchant.telephone != null}">
							<span class="fl">${merchant.telephone }</span>
						</c:if>
					</div>
					<div class="fl">
						<label class="fl">客服识别号:</label>
								<span class="fl">${merchant.identification }</span>
					</div>
					<div class="fl">
						<label class="fl">结算方式:</label>
								<span class="fl">${merchant.autoSettle == 1?'自动结算':'手工提现' }</span>
					</div>
					
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">扩展人员编号:</label>
						<span class="fl">	${merchant.operId }</span>
					</div>
					<div class="fl">
						<label class="fl">备注:</label>
						<span class="fl">${merchant.remark }</span>
						<!-- <textarea name="merchant.remark"  value="${merchant.remark }" maxlength="100" class="fl"></textarea> -->
					</div>
				</div>
		</div>
		</form>
	</div>
</div>