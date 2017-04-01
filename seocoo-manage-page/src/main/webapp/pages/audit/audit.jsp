<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		$("#from").datepicker({
			defaultDate : "+1w",
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			onClose : function(selectedDate) {
				$("#to").datepicker("option", "minDate", selectedDate);
			}

		});
		$("#to").datepicker({
			defaultDate : "+1w",
			changeMonth : true,
			changeYear : true,
			numberOfMonths : 1,
			defaultDate : new Date(),
			onClose : function(selectedDate) {
				$("#from").datepicker("option", "maxDate", selectedDate);
				$("#to").datepicker("option", "maxDate", new Date());
			}

		});
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth();
		var day = date.getDate();
        var days2 = new Date(year, month-1, 1);
       
		$("#from").datepicker('setDate', days2);
		$("#to").datepicker('setDate', new Date());
	});
</script>


<div class="container-order" style="">
	<div class="order-tab">
		<ul class="fl">
		<li class="fl current" data-type="notAudit">待审核</li>
		</ul>
	</div>
	<div class="order-box" >
	<!-- 直属商户 -->
<div class="today-order">
	<div class="today-order-top">
		<span><img src="${resourcePath}/images/search.png"></span>
		<span>交易时间</span>
                 <input type="text" id="from" name="from" readonly="readonly">&nbsp;至
                 <input type="text" id="to" name="to" readonly="readonly">
		<input type="text" placeholder="请输入手机号" maxlength="11" onkeyup="value=value.replace(/[^-\d]/g,'')" id="merchant_Input" name="">
		<select id="merchant_Select">
			<option value="">请选择认证状态</option>
			<option value="Auditing">审核中</option>
			<option value="Audited">已认证</option>
		</select>
		<a href="javascript:void(0);" class="check" onclick="searchAudit()">查询</a>
	</div>
	<div class="today-order-table" id="auditList">
	</div>
	</div>
	<div class="today-order-pagination">
		<!--  分页  -->
		<div class="pages">
			<div id="Pagination"></div>
		</div>
	</div>
</div>
<!--加载中 -->
<div class="import_loading" style="display: none;">
	<div>
		<img alt="" src="${resourcePath}/images/loading.gif">
		<p>正在进件中...</p>
	</div>
</div>

<!-- 编辑弹框 -->
<div class="pop_up_box pop_box_alert" id="pop_box_alert1" style="display: none;">
	<!--黑色遮罩-->
	<div class="sweet-overlay" tabindex="-1"></div>
	<div class="edit_box" style="width: 360px;">
		<p style="color: red; line-height: 2.7em; font-size: 17px; width: 340px">&nbsp;&nbsp;&nbsp;&nbsp;请确认将该商户进件</p>
		<div class="btn_box">
			<a href="javascript:void(0)" class="blue">确定</a>
			<a href="javascript:void(0)" class="red">取消</a>
		</div>
	</div>
</div>
<input type="hidden" id="groupCode" value="${userRelationship.groupCode }" />
<script type="text/javascript">
	//页面标签跳转
	/* $(".container-order .order-tab li").click(function()
	{
		var index = $(".order-tab li").index(this);
		$(this).addClass("current").siblings("li").removeClass("current");
		var type = $(this).data("type");
		//清空数据
		$("#Pagination").html("")
		$("#merchant_Input").val("");
	
		if (type == "directly")
		{
			//初始化直属
			initAuditList()
			$("#showAddMerchant").show();
		} else
		{
			//初始化非直属
			initAuditList()
			$("#showAddMerchant").hide();
		}
	}) */

	$(document).ready(function()
	{
		//初始化 直属商户
		initAuditList()
	})
</script>


