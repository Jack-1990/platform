<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div >
	<div class="shop-nontice">
	<input type="hidden" id="merchantCode" value="${merchant.outMchntId }" />
		<form action="" method="post" id="bindingZFBMerchant" name="bindingZFBMerchant" enctype="multipart/form-data" encoding="multipart/form-data">
					<input type="hidden" name="payChannel.cmbcMchntId" value="${merchant.cmbcMchntId }" /> <input type="hidden" name="payChannel.groupCode"
				value="${merchant.groupCode }" /> <input type="hidden" name="payChannel.operId" value="${merchant.operId }" /> <input type="hidden"
				name="payChannel.id" value="${payChannel.id}" /> <input type="hidden" name="payChannel.txnSeq" value="${payChannel.txnSeq}" /> <input
				type="hidden" name="payChannel.apiCode" value="${payChannel.apiCode}" /> <input type="hidden" name="payChannel.platformId"
				value="${merchant.platformId }" />
			<div class="sales-top">
				<div class="fl">
					<label class="fl">外部商户号:</label>
					<input type="text" class="fl" readonly="readonly" maxlength="64" name="payChannel.merchantCode" value="${merchant.outMchntId }"> <span
						class="fl"><i>*</i></span>
				</div>
				<div class="fl">
					<label class="fl">商户简称:</label>
					<input type="text" name="payChannel.mchntName" maxlength="64" value="${merchant.mchntName }" class="fl"> <span class="fl"><i>*</i></span>
				</div>
				<div class="fl">
					<label class="fl">商户全称:</label>
					<input type="text" name="payChannel.mchntFullName" maxlength="100" value="${merchant.mchntFullName }" class="fl"> <span class="fl"><i>*</i></span>
				</div>
				<div class="fl">
					<label class="fl">父商户:</label>
					<input type="text" name="payChannel.parentMchntId" maxlength="64" value="${merchant.parentMchntId }" class="fl"> <span class="fl"></span>
				</div>
			</div>
			<div class="sales-top">
				<div class="fl">
					<label class="fl">接入类型:</label>
					<select name="payChannel.operateType" class="fl">
						<option value="1" ${payChannel.operateType == 1?'selected="selected"':'' }>间接</option>
						<option value="2" ${payChannel.operateType == 2?'selected="selected"':'' }>直联</option>
					</select>
				</div>
				<div class="fl">
					<label class="fl">通道状态:</label>
					<input type="text" class="fl" readonly="readonly" value="${payChannel != null && payChannel.status == 1?'已绑定':'未绑定' }">
				</div>
				<div class="fl" style="width: 100%;">
					<label class="fl">商户类别:</label>
					<select class="fl"  id="firstCategory" style="width: 130px;" onchange="queryZFBCode(this,0)">
						<option value="">选择一级类目</option>
						<c:forEach var="type" items="${merchantTypeList}">
							<option value="${type}" >${type}</option>
						</c:forEach>
					</select>
					<select class="fl"   id="secondCategory" style="width: 180px; margin-left: 10px;" onchange="queryZFBCode(this,1)">
						<option value="">选择二级类目</option>
					</select>
					<select class="fl" id="thirdCategory" style="width: 230px; margin-left: 10px;" onchange="queryZFBCode(this,2)">
						<option value="">选择三级类目</option>
					</select>
				<div class="fl" style="margin-left: 10px;">
					<label>类目ID:</label>
					<input type="text" id="categoryID" readonly="readonly" name="payChannel.industryId" value="${payChannel.industryId }" placeholder=""> <i>*</i>
				</div>
				</div>
			</div>
			<div class="sales-top">
				<div class="fl">
					<label class="fl">日限额:</label>
					<input type="text" class="fl" name="payChannel.dayLimit" maxlength="12" value="${payChannel.dayLimit }"> <span class="fl"><i>*</i>单位：分</span>
				</div>
				<div class="fl">
					<label class="fl">月限额:</label>
					<input type="text" class="fl" name="payChannel.monthLimit" maxlength="12" value="${payChannel.monthLimit }"> <span class="fl"><i>*</i>单位：分</span>
				</div>
			    <div class="fl">
					<label class="fl">固定比率费率:</label>
					<input type="text" class="fl" name="payChannel.fixFeeRate" onchange="fixFreeRateCheck(this,value)" maxlength="10" value="${payChannel.fixFeeRate }" readonly="readonly"> 
					<span class="fl"><i>*</i>0.50，小数点后精确到2位。两种费率二选一</span>
				</div>
				<div class="fl">
					<label class="fl">特殊费率:</label>
					<input type="text" class="fl" name="payChannel.specFeeRate" maxlength="10" value="${payChannel.specFeeRate }" readonly="readonly">
					<span class="fl"><i>*</i>两种费率二选一</span>
				</div>
				<div class="fl">
					<label class="fl">结算账户:</label>
					<input type="text" class="fl" name="payChannel.account" maxlength="32" value="${payChannel.account }"> <span class="fl"><i>*</i></span>
				</div>
				<div class="fl">
					<label class="fl">账户类型:</label>
					<select class="fl" name="payChannel.acctType">
						<option value="1" ${payChannel.acctType == 1?'selected="selected"':'' }>对私</option>
						<option value="2" ${payChannel.acctType == 2?'selected="selected"':'' }>对公</option>
					</select>
					<span class="fl"><i>*</i>1-对私，2-对公</span>
				</div>
				<div class="fl">
					<label class="fl">开户行号:</label>
					<input type="text" class="fl" name="payChannel.pbcBankId" maxlength="12" value="${payChannel.pbcBankId }"> <span class="fl"><i>*</i></span>
				</div>
				<div class="fl">
					<label class="fl">开户人:</label>
					<input type="text" class="fl" name="payChannel.acctName" maxlength="20" value="${payChannel.acctName }"> <span class="fl"><i>*</i></span>
				</div>
				<div class="fl">
					<label class="fl">通道其他信息:</label>
					<input type="text" class="fl" name="payChannel.message" value="${payChannel.message }"> <span class="fl"></span>
				</div>
			</div>
		</form>
	</div>
	<div class="bind-box textc">
		<a href="javascript:void(0);" onclick="saveBindZFBMerhant()" class="bind-btn">保存</a>
		<a href="javascript:void(0);" onclick="gotoBindZFBMerhant()" class="bind-btn">绑定</a>
	</div>
</div>
