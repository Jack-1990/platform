<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
			dateFormat : 'yy-mm-dd',
			firstDay : 1,
			isRTL : false,
			showMonthAfterYear : true,
			yearSuffix : '年'
		};
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);

	});
	$(function() {
		$("#validTime").datepicker({
			defaultDate : "+1w",
			changeMonth : true,
			changeYear : true,
			yearRange:"-200:+10",
			numberOfMonths : 1,
			minDate:new Date(),
			onClose : function(selectedDate) {
				$("#validTime").datepicker("option", "minDate", new Date());
			}

		});
	});
</script>

<!--代理管理-->
        <div class="container-marketing">
                <div class="order-tab">
                    <ul class="fl">
                        <li class="fl current">编辑代理商</li>
                    </ul>
                    <div class="fr">
                        <a href="javascript:void(0);" onclick="loadContent('/group.do')"><span><i class="icon-reply"></i></span>返回</a>
                    </div>
                    <div class="fr">
                        <a href="javascript:void(0);" class="save" onclick="saveGroup(1);">保存</a>
                    </div>
                </div>
                <div>
                    <div class="muti-marketing">
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">代理商编号:</label>
                                <input type="text" class="fl"  value="${group.groupCode}" id="groupCode" readOnly="readOnly">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">代理简称:</label>
                                <input type="text" class="fl" id="shortName" maxlength="40" value="${group.shortName}">
                                <span class="fl"><i></i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">代理全称:</label>
                                <input type="text" class="fl" id="fullName" maxlength="40" value="${group.fullName}">
                                <span class="fl"><i>*</i>请填写营业执照上的全称</span>
                            </div>
                            <div class="fl">
                                <label class="fl">代理层级:</label>
                                <select style="width:210px;margin-left:0px" class="fl" id="groupLevel">
                                   <c:choose>
                                     <c:when test="${isAudito ==1 }">
                                         <option value="1">一级代理商</option>
                                     </c:when>
                                     <c:otherwise>
                                   		   <c:if test="${group.groupLevel <=2 }">  <option value="2" <c:if test="${group.groupLevel==2}">selected</c:if>>二级代理商</option></c:if>
                                           <c:if test="${group.groupLevel <=3 }">  <option value="3" <c:if test="${group.groupLevel==3}">selected</c:if>>三级代理商</option></c:if>
		                                   <c:if test="${group.groupLevel <=4 }">  <option value="4" <c:if test="${group.groupLevel==4}">selected</c:if>>四级代理商</option></c:if>
		                                   <c:if test="${group.groupLevel <=5 }">  <option value="5" <c:if test="${group.groupLevel==5}">selected</c:if>>五级代理商</option></c:if>
                                     </c:otherwise>
                                   </c:choose>
                                </select>
                                <span class="fl"><i>*</i></span>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">省份:</label>
                                <select style="width:120px;" class="fl" id="provinceSelect" onchange="queryCityList(this.value,0)">
                                    <option value="">选择省份</option>
                                   <c:forEach var="province" items="${provinceList}">
						    		   <option value="${province.code}" <c:if test="${province.code eq provinceCode}">selected</c:if>>${province.name}</option>
						    	    </c:forEach>
                                </select>
                                <select style="width:120px;margin-left:10px" class="fl" id="citySelect" onchange="queryCityList(this.value,1)"> 
                                    <option value="">选择市城市</option>
                                     <c:forEach items="${cityList}" var="cityList">
                       					  <option value="${cityList.code}" <c:if test="${cityList.code eq cityCode}">selected</c:if>>${cityList.name}</option>
                    				 </c:forEach>
                                </select>
                                <select style="width:120px;margin-left:10px" class="fl" id="areaSelect" onchange="areaChange(this.value)">
                                    <option value="">选择市县区</option>
                                    <c:forEach items="${areaList}" var="areaList">
                       					  <option value="${areaList.code}" <c:if test="${areaList.code eq areaCode}">selected</c:if>>${areaList.name}</option>
                    				 </c:forEach>
                                </select>
                            </div>
                            <div class="fl">
                                <label class="fl">区域代码:</label>
                                <input type="text" class="fl" id="lantCode" maxlength="6" value="${group.lantCode}" readOnly="readOnly">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">地址:</label>
                                <input type="text" class="fl" style="width:380px" id="address" maxlength="40" value="${group.address}">
                                <i class="fl">*</i>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">是否持证:</label>
                                  <select class="fl" style="width:210px;margin-left:0px" id="certificateFlag">
                                    <option value="0" <c:if test="${group.certificateFlag==0}">selected</c:if>>非持证商户</option>
                                    <option value="1" <c:if test="${group.certificateFlag==1}">selected</c:if>>持证商户</option>
                                </select>
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">营业执照号:</label>
                                <input type="text" class="fl" id="businessCardNumber" maxlength="15" value="${group.businessCardNumber}" onkeyup="checkAccountNum(this,event);">
                                <span class="fl"><i>*</i>若没有，可不填</span>
                            </div>
                            <div class="fl">
                                <label class="fl">营业执照有效期:</label>
                                <c:set var="validTime"><fmt:formatDate value="${group.validTime}" pattern="yyyy-MM-dd" /></c:set>
                                <input type="text" class="fl" id="validTime" maxlength="20" value="${validTime}" readOnly="readOnly">
                                <span class="fl"><i>*</i>营业执照结束日期</span>
                            </div>
                            <div class="fl">
                                <label class="fl">法人/联系人:</label>
                                <input type="text" class="fl" id="linkman" maxlength="40" value="${group.linkman}">
                                <span class="fl"><i>*</i>若没有，可不填</span>
                            </div>
                            <div class="fl">
                                <label class="fl">法人/联系人证件号:</label>
                                <input type="text" class="fl" id="linkmanId" maxlength="40" value="${group.linkmanId}" onkeyup="checkAccountNum(this,event)">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">负责人:</label>
                                <input type="text" class="fl" id="rpName" maxlength="10" value="${group.rpName}">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">负责人手机号:</label>
                                <input type="text" class="fl" id="rptelephone" maxlength="11" value="${group.rptelephone}" onkeyup="checkPhoneNum(this,event);">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">客服电话:</label>
                                <input type="text" class="fl" id="customerTelephone" maxlength="20" value="${group.customerTelephone}" onkeyup="checkPhoneNum(this,event);">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">客户识别码:</label>
                                <input type="text" class="fl" id="customerId" maxlength="40" value="${group.customerId}">
                                <span class="fl"><i></i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">结算方式:</label>
                                <select class="fl" style="width:210px;margin-left:0px" id="accountStyle">
                                    <option value="1" <c:if test="${group.accountStyle==1}">selected</c:if>>自动结算</option>
                                    <option value="2" <c:if test="${group.accountStyle==2}">selected</c:if>>手工提现</option>
                                </select>
                                <span class="fl"><i>*</i></span>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">扩展人员编号:</label>
                                <input type="text" class="fl" value="${group.extensionNumber}" id="extensionNumber" maxlength="15">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">备注:</label>
                                <textarea class="fl" id="remark" maxlength="100">${group.remark}</textarea>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">上传身份证正面:</label>
                                <div class="fl position">
                                    <div class="fl position">
                                      <c:if test="${empty group.idBeforePic}">
                                              <div class="pic" id="groupPic_1" data-num="1" style="display:none">
		                                        <input type="hidden" id="groupPicVal_1" value="${group.idBeforePic}">
		                                        <img src="">
		                                     </div>
                                     </c:if>
                                      <c:if test="${not empty group.idBeforePic}">
                                           <div class="pic" id="groupPic_1" data-num="1" style="display:block">
		                                        <input type="hidden" id="groupPicVal_1" value="${group.idBeforePic}">
		                                        <img src="${resourcePath}/${group.idBeforePic}">
		                                     </div>
                                     </c:if>
                                  
                                    <a href="javascript:void(0);" class="up-btn" onclick="uploadGroupPic(1);">
                                        <img src="${resourcePath}/images/add-pic.png" alt="">
                                        <span>点击上传身份证正面</span>
                                    </a>
                                </div>
                                </div>
                            </div>
                            <div class="fl">
                                <label class="fl">上传身份证反面:</label>
                                <div class="fl position">
                                     <c:if test="${empty group.idAfterPic}">
                                         <div class="pic" id="groupPic_2" data-num="2" style="display:none">
	                                     	<input type="hidden" id="groupPicVal_2" value="${group.idAfterPic}">
	                                        <img src="">
	                                     </div>
                                     </c:if>
                                      <c:if test="${not empty group.idAfterPic}">
                                           <div class="pic" id="groupPic_2" data-num="2" style="display:block">
	                                     	<input type="hidden" id="groupPicVal_2" value="${group.idAfterPic}">
	                                        <img src="${resourcePath}/${group.idAfterPic}">
	                                     </div>
                                     </c:if>
                                     
                                    <a href="javascript:void(0);" class="up-btn" onclick="uploadGroupPic(2);">
                                        <img src="${resourcePath}/images/add-pic.png" alt="">
                                        <span>点击上传身份证反面</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">核实照片:</label>
                                <div class="fl position">
                                      <c:if test="${empty group.checkPic1}">
                                           <div class="pic" id="groupPic_3" data-num="3" style="display:none">
		                                    	<input type="hidden" id="groupPicVal_3" value="${group.checkPic1}">
		                                        <img src="">
		                                     </div>
                                     </c:if>
                                      <c:if test="${not empty group.checkPic1}">
                                            <div class="pic" id="groupPic_3" data-num="3" style="display:block">
		                                    	<input type="hidden" id="groupPicVal_3" value="${group.checkPic1}">
		                                        <img src="${resourcePath}/${group.checkPic1}">
		                                     </div>
                                     </c:if>
                                  
                                    <a href="javascript:void(0);" class="up-btn" onclick="uploadGroupPic(3);">
                                        <img src="${resourcePath}/images/add-pic.png" alt="">
                                        <span>点击上传核实照片</span>
                                    </a>
                                </div>
                            </div>
                            <div class="fl">
                                <label class="fl">核实照片:</label>
                                <div class="fl position">
                                      <c:if test="${empty group.checkPic2}">
                                             <div class="pic" id="groupPic_4" data-num="4" style="display:none">
		                                     	 <input type="hidden" id="groupPicVal_4" value="${group.checkPic2}">
		                                        <img src="">
		                                     </div>
                                     </c:if>
                                      <c:if test="${not empty group.checkPic2}">
                                               <div class="pic" id="groupPic_4" data-num="4" style="display:block">
			                                      <input type="hidden" id="groupPicVal_4" value="${group.checkPic2}">
			                                        <img src="${resourcePath}/${group.checkPic2}">
			                                     </div>
                                     </c:if>
                                  
                                    <a href="javascript:void(0);" class="up-btn" onclick="uploadGroupPic(4);">
                                        <img src="${resourcePath}/images/add-pic.png" alt="">
                                        <span>点击上传核实照片</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="sales-top" style="margin-bottom:0;border-bottom:0;">
                            <div class="fl" style="width:100%;">
                                <label class="fl">上传营业照照片:</label>
                                <div class="fl position" style="width:834px;height:350px;">
                                     <c:if test="${empty group.businessCardPic}">
                                             <div class="pic" id="groupPic_5" data-num="5" style="display:none">
		                                        <input type="hidden" id="groupPicVal_5" value="${group.businessCardPic}">
		                                        <img src="">
		                                     </div>
                                     </c:if>
                                      <c:if test="${not empty group.businessCardPic}">
                                           <div class="pic" id="groupPic_5" data-num="5" style="display:block">
		                                        <input type="hidden" id="groupPicVal_5" value="${group.businessCardPic}">
		                                        <img src="${resourcePath}/${group.businessCardPic}">
		                                     </div>
                                     </c:if>
                                  
                                    <a href="javascript:void(0);" class="up-btn" onclick="uploadGroupPic(5);">
                                        <img src="${resourcePath}/images/add-pic.png" alt="">
                                        <span>点击上传营业照照片</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bind-box textc">
                         <input type="hidden" id="groupId" value="${group.id}">
                        <a href="javascript:void(0);" class="bind-btn" onclick="saveGroup(1);">保存</a>
                    </div>
                </div>
            </div>
	
		
<script type="text/javascript">
$(".sales-top .position .pic").click(function(){
	var type=$(this).attr("data-num");
	uploadGroupPic(type);
})

</script>