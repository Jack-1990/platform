<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}/css/jquery-ui.min.css" />
<script>
	jQuery(function() {
		$.datepicker.regional['zh-CN'] = {
			closeText : '关闭',
			prevText : '<上月',
                    nextText: '下月>',
			currentText : '今天',
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
			monthNamesShort : [ '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二' ],
			dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
			dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
			dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
			weekHeader : '周',
			dateFormat : 'yymmdd',
			firstDay : 1,
			isRTL : false,
			showMonthAfterYear : true,
			yearSuffix : '年'
		};
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);

	});
</script>

<div class="container-marketing">
	<div class="order-tab">
		<ul class="fl">
			<li class="fl current">新增商户</li>
		</ul>
		<div class="fr">
                        <a href="javascript:void(0);" onclick="loadContent('/merchant.do')"><span><i class="icon-reply"></i></span>返回</a>
                    </div>
		<div class="fr">
			<a href="javascript:void(0);" class="save" onclick="saveMerchant();">保存</a>
		</div>
	</div>
	<div>
		<div class="muti-marketing">
			<form action="" method="post" id="addMerchant" name="addMerchant" enctype="multipart/form-data" encoding="multipart/form-data">
				<input type="hidden" name="merchant.groupCode" value="${groupCode }" />
				<div class="sales-top">
					<div class="fl">
						<label class="fl">外部商户号:</label>
						<input type="text" class="fl" readonly="readonly" maxlength="64" name="merchant.outMchntId" value="${outMchntId }"> <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">商户简称:</label>
						<input type="text" name="merchant.mchntName"  maxlength="20" class="fl"> <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">商户全称:</label>
						<input type="text" name="merchant.mchntFullName" maxlength="100" class="fl"> <span class="fl"><i>*</i>请填写营业执照上的全称</span>
					</div>
					<div class="fl">
						<label class="fl">父商户:</label>
						<input type="text" name="merchant.parentMchntId" maxlength="64" class="fl"> <span class="fl"></span>
					</div>
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">省份:</label>
						<select id="provinceSelect1"  onchange="queryCityListMer(this,0)" class="fl" style="width:120px;margin-left:0px">
							<option value="">选择省份</option>
							<c:forEach var="province" items="${provinceList}">
								<option value="${province.code}">${province.name}</option>
							</c:forEach>
						</select>
						<select id="citySelect"  onchange="queryCityListMer(this,1)" style="width:120px;margin-left:10px" class="fl">
							<option value="">选择市</option>
						</select>
						<select id="areaSelect"  style="width:120px;margin-left:10px" class="fl" onchange="queryCityListMer(this,2)">
							<option value="">选择区/县</option>
						</select>
						<input type="hidden"  name="merchant.province" />
						<input type="hidden"  name="merchant.city" />
					</div>
					<div class="fl">
						<label class="fl">区域代码:</label>
						<input type="text" class="fl" name="merchant.acdCode" maxlength="10" id="areaCode" readonly="readonly"> <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">地址:</label>
						<input type="text" class="fl" name="merchant.address" maxlength="80" style="width: 320px"> <i class="fl">*</i>
					</div>
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">是否持证:</label>
						<select class="fl" name="merchant.isCert">
							<option value="0">非持证商户</option>
							<option value="1">持证商户</option>
						</select>
						<span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">营业执照号:</label>
						<input type="text" class="fl" maxlength="30"  name="merchant.licId" onchange="value=value.replace(/[^A-Za-z0-9-]/g,'')"> <span class="fl"><i>*</i>若没有，可填默认值-</span>
					</div>
					<div class="fl">
						<label class="fl">营业执照有效期:</label>
						<input type="text" id="validTime" class="fl" maxlength="8" name="merchant.licIdValidity" onchange="value=value.replace(/[^-\d]/g,'')"> <span class="fl"><i>*</i>yyyyMMDdd,若没有，可填默认值-</span>
					</div>
					<div class="fl">
						<label class="fl">法人/联系人:</label>
						<input type="text" class="fl" maxlength="60" onchange="sameUserName(this)" name="merchant.corpName"> <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">法人/联系人证件号:</label>
						<input type="text" class="fl" maxlength="20" name="merchant.idtCard" onchange="value=value.replace(/[^A-Za-z0-9-]/g,'')" > <span class="fl"><i>*</i>若没有，可填默认值-</span>
					</div>
					<div class="fl">
						<label class="fl">负责人:</label>
						<input type="text" class="fl" maxlength="20" name="merchant.contactName"> <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">负责人手机号:</label>
						<input type="text" class="fl" maxlength="11" name="merchant.telephone" onchange="checkPhone(this,value)"> <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">客服电话:</label>
						<input type="text" class="fl" maxlength="15" onkeyup="value=value.replace(/[^\d]/g,'')" name="merchant.servTel"> <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">客户识别码:</label>
						<input type="text" class="fl" maxlength="20" onblur = ""  name="merchant.identification"> <span class="fl"></span>
					</div>
					<div class="fl">
						<label class="fl">结算方式:</label>
						<select class="fl" name="merchant.autoSettle">
							<option selected="selected" value="1">自动结算</option>
							<option value="2">手工提现</option>
						</select>

					</div>
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">商户等级:</label>
						<select class="fl" name="merchant.merchantLevel">
							<option  value="0">选择商户等级</option>
							<c:forEach items="${dimDicInfos}" var="dimDicInfo">
								<option  value="${dimDicInfo.attrCode}">${dimDicInfo.attrName}</option>
							</c:forEach>
						</select>
						<span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">结算账户:</label>
						<input type="text" class="fl" name="merchant.cardNumber" maxlength="32" > <span class="fl"><i>*</i></span>
					</div>
					<div class="fl">
						<label class="fl">开户人:</label>
						<input type="text" class="fl" readonly="readonly" name="merchant.acctName" maxlength="20" > <span class="fl"><i>*</i></span>
					</div>
					<div class="fl" style="width: 100%;">
						<label class="fl">开户行号:</label>
						<div class="fl" style="margin-left: 10px;">
							<select class="fl"  id="bank"  style="width: 130px;" onchange="selectBank(this)">
								<option value="">选择所属银行</option>
								<c:forEach var="bank" items="${banks}">
									<option value="${bank.id}" >${bank.bankName}</option>
								</c:forEach>
							</select>
							<input type="hidden"  name="merchant.affiliatedBank" />
						</div>
						<div class="fl" style="margin-left: 10px;">
						<select id="provinceSelect1_bank"  onchange="queryCityListBank(this,0)" class="fl" style="width:120px;margin-left:0px">
							<option value="">选择省份</option>
							<c:forEach var="province" items="${provinceList}">
								<option value="${province.code}">${province.name}</option>
							</c:forEach>
						</select>
						<select id="citySelect_bank" name="merchant.bankAreaCode"  onchange="queryBankInfo(this)" style="width:120px;margin-left:10px" class="fl">
							<option value="">选择市</option>
						</select>
							<input type="hidden"  name="merchant.bankProvince" />
							<input type="hidden"  name="merchant.bankCity" />
						</div>
						<div class="fl" style="margin-left: 10px;">
						<select class="fl" id="selectBankInfo" style="width: 230px; margin-left: 10px;" onchange="selectBankCode(this)">
							<option value="">选择开户行</option>
						</select>
							<input type="hidden"  name="merchant.bankName" />
						</div>
						<div class="fl" style="margin-left: 10px;">
							<label>开户行号:</label>
							<input type="text" id="bankCode" readonly="readonly" name="merchant.bankNumber" placeholder=""> <i>*</i>
						</div>
					</div>
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">扩展人员编号:</label>
						<input type="text" class="fl" name="merchant.operId" value="${fn:substring(LoginName, 0, 15)}" maxlength="15"> <span class="fl"></span>
					</div>
					<div class="fl">
						<label class="fl">备注:</label>
						<textarea name="merchant.remark" class="fl" maxlength="100"></textarea>
					</div>
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">上传身份证正面:</label>
						<div class="fl position">
							
							<a href="javascript:void(0);" class="up-btn" onclick="uploadImg(this)">
								<img src="${resourcePath}/images/add-pic.png" alt=""> <span>点击上传身份证正面</span>
							</a>
								 <input type='hidden' name="merchant.idBeforePic" id="uploadImage"  />
						</div>
					</div>
					<div class="fl">
						<label class="fl">上传身份证反面:</label>
						<div class="fl position">
							<a href="javascript:void(0);" class="up-btn" onclick="uploadImg(this)">
								<img src="${resourcePath}/images/add-pic.png" alt=""> <span>点击上传身份证反面</span>
							</a>
								<input type='hidden' name="merchant.idAfterPic" id="uploadImage"   />
						</div>
					</div>
				</div>
				<div class="sales-top">
					<div class="fl">
						<label class="fl">核实照片:</label>
						<div class="fl position">
							<a href="javascript:void(0);" class="up-btn" onclick="uploadImg(this)">
								<img src="${resourcePath}/images/add-pic.png" alt=""> <span>点击核实照片</span>
							</a>
								<input type='hidden' name="merchant.checkPic1"  id="uploadImage"  />
						</div>
					</div>
					<div class="fl">
						<label class="fl">核实照片:</label>

						<div class="fl position">
							<a href="javascript:void(0);" class="up-btn" onclick="uploadImg(this)">
								<img src="${resourcePath}/images/add-pic.png" alt=""> <span>点击核实照片</span>
							</a>
								<input type='hidden' name="merchant.checkPic2" id="uploadImage"   />
						</div>
					</div>
				</div>
				<div class="sales-top" style="margin-bottom: 0; border-bottom: 0;">
					<div class="fl" style="width: 100%;">
						<label class="fl">上传营业照照片:</label>
						<div class="fl position" style="width: 834px; height: 350px;">
							<a href="javascript:void(0);" class="up-btn" onclick="uploadImg(this)">
								<img src="${resourcePath}/images/add-pic.png" alt=""> <span>点击上传核实照片</span>
							</a>
								<input type='hidden' name="merchant.businessCardPic" id="uploadImage"   />
						</div>
					</div>
				</div>
		</div>
		</form>
		<div class="bind-box textc">
			<a href="javascript:void(0);"  onclick="saveMerchant()" class="bind-btn">保存</a>
		</div>
	</div>
</div>
<!--加载中 -->
<div class="import_loading" style="display: none;">
	<div>
		<img alt="" src="${resourcePath}/images/loading.gif">
		<p>正在进行中...</p>
	</div>
</div>