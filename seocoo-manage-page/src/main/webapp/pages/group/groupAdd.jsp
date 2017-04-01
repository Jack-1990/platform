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
                        <li class="fl current">新增代理商</li>
                    </ul>
                    <div class="fr">
                        <a href="javascript:void(0);" onclick="loadContent('/group.do')"><span><i class="icon-reply"></i></span>返回</a>
                    </div>
                     <div class="fr">
                        <a href="javascript:void(0);" class="save" onclick="saveGroup(0);">保存</a>
                    </div>
                </div>
                <div>
                    <div class="muti-marketing">
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">代理商编号:</label>
                                <input type="text" class="fl"  value="${groupCode}" id="groupCode" readOnly="readOnly">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">代理简称:</label>
                                <input type="text" class="fl" id="shortName" maxlength="40">
                                <span class="fl"><i></i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">代理全称:</label>
                                <input type="text" class="fl" id="fullName" maxlength="40">
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
                                   		   <c:if test="${groupLevel <=2 }">  <option value="2">二级代理商</option></c:if>
                                           <c:if test="${groupLevel <=3 }">  <option value="3">三级代理商</option></c:if>
		                                   <c:if test="${groupLevel <=4 }">  <option value="4">四级代理商</option></c:if>
		                                   <c:if test="${groupLevel <=5 }"> <option value="5">五级代理商</option></c:if>
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
                                    <c:forEach items="${provinceList }" var="pro">
                                       <option value="${pro.code }">${pro.name }</option>
                                    </c:forEach>
                                </select>
                                <select style="width:120px;margin-left:10px" class="fl" id="citySelect" onchange="queryCityList(this.value,1)"> 
                                    <option value="">选择市城市</option>
                                </select>
                                <select style="width:120px;margin-left:10px" class="fl" id="areaSelect" onchange="areaChange(this.value)">
                                    <option value="">选择市县区</option>
                                </select>
                            </div>
                            <div class="fl">
                                <label class="fl">区域代码:</label>
                                <input type="text" class="fl" id="lantCode" maxlength="6" readOnly="readOnly">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">地址:</label>
                                <input type="text" class="fl" style="width:380px" id="address" maxlength="40">
                                <i class="fl">*</i>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">是否持证:</label>
                                  <select class="fl" style="width:210px;margin-left:0px" id="certificateFlag">
                                    <option value="0">非持证商户</option>
                                    <option value="1">持证商户</option>
                                </select>
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">营业执照号:</label>
                                <input type="text" class="fl" id="businessCardNumber" maxlength="15" onkeyup="checkAccountNum(this,event);">
                                <span class="fl"><i>*</i>若没有，可不填</span>
                            </div>
                            <div class="fl">
                                <label class="fl">营业执照有效期:</label>
                                <input type="text" class="fl" id="validTime" maxlength="20" readOnly="readOnly">
                                <span class="fl"><i>*</i>营业执照结束日期</span>
                            </div>
                            <div class="fl">
                                <label class="fl">法人/联系人:</label>
                                <input type="text" class="fl" id="linkman" maxlength="15">
                                <span class="fl"><i>*</i>若没有，可不填</span>
                            </div>
                            <div class="fl">
                                <label class="fl">法人/联系人证件号:</label>
                                <input type="text" class="fl" id="linkmanId" maxlength="40" onkeyup="checkAccountNum(this,event)">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">负责人:</label>
                                <input type="text" class="fl" id="rpName" maxlength="40">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">负责人手机号:</label>
                                <input type="text" class="fl" id="rptelephone" maxlength="11" onkeyup="checkPhoneNum(this,event);">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">客服电话:</label>
                                <input type="text" class="fl" id="customerTelephone" maxlength="20" onkeyup="checkPhoneNum(this,event);">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">客户识别码:</label>
                                <input type="text" class="fl" id="customerId" maxlength="40">
                                <span class="fl"><i></i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">结算方式:</label>
                                <select class="fl" style="width:210px;margin-left:0px" id="accountStyle">
                                    <option value="1">自动结算</option>
                                    <option value="2">手工提现</option>
                                </select>
                                <span class="fl"><i>*</i></span>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">扩展人员编号:</label>
                                <input type="text" class="fl" value="${loginName}" id="extensionNumber" maxlength="20">
                                <span class="fl"><i>*</i></span>
                            </div>
                            <div class="fl">
                                <label class="fl">备注:</label>
                                <textarea class="fl" id="remark" maxlength="100"></textarea>
                            </div>
                        </div>
                        <div class="sales-top">
                            <div class="fl">
                                <label class="fl">上传身份证正面:</label>
                                <div class="fl position">
                                    <div class="fl position">
                                     <div class="pic" id="groupPic_1" data-num="1" style="display:none">
                                        <input type="hidden" id="groupPicVal_1" value="">
                                        <img src="">
                                     </div>
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
                                     <div class="pic" id="groupPic_2" data-num="2" style="display:none">
                                     <input type="hidden" id="groupPicVal_2" value="">
                                        <img src="">
                                     </div>
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
                                    <div class="pic" id="groupPic_3" data-num="3" style="display:none">
                                    <input type="hidden" id="groupPicVal_3" value="">
                                        <img src="">
                                     </div>
                                    <a href="javascript:void(0);" class="up-btn" onclick="uploadGroupPic(3);">
                                        <img src="${resourcePath}/images/add-pic.png" alt="">
                                        <span>点击上传核实照片</span>
                                    </a>
                                </div>
                            </div>
                            <div class="fl">
                                <label class="fl">核实照片:</label>
                                <div class="fl position">
                                      <div class="pic" id="groupPic_4" data-num="4" style="display:none">
                                      <input type="hidden" id="groupPicVal_4" value="">
                                        <img src="">
                                     </div>
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
                                     <div class="pic" id="groupPic_5" data-num="5" style="display:none">
                                     <input type="hidden" id="groupPicVal_5" value="">
                                        <img src="">
                                     </div>
                                    <a href="javascript:void(0);" class="up-btn" onclick="uploadGroupPic(5);">
                                        <img src="${resourcePath}/images/add-pic.png" alt="">
                                        <span>点击上传营业照照片</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bind-box textc">
                        <a href="javascript:void(0);" class="bind-btn" onclick="saveGroup(0);">保存</a>
                    </div>
                </div>
            </div>
	
		
<script type="text/javascript">
$(".sales-top .position .pic").click(function(){
	var type=$(this).attr("data-num");
	uploadGroupPic(type);
})

</script>