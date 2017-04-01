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
			}

		});
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth();
		var day = date.getDate();
        var days2 = new Date(year, month, 1);
       
		$("#from").datepicker('setDate', days2);
		$("#to").datepicker('setDate', new Date());
	});
</script>


 <!-- 交易查询 -->
 <div class="container-order" >
     <div class="order-tab">
         <ul class="fl">
             <li class="fl current">交易查询</li>
         </ul>
     </div>
     <div class="order-box">
         <div class="today-order">
             <div class="today-order-top">
                 <span>交易时间</span>
                 <input type="text" id="from" name="from" readonly="readonly">&nbsp;至
                 <input type="text" id="to" name="to" readonly="readonly">
                 <a href="javascript:void(0);" class="check" onclick="querytransa(0);">查询</a>
                 <a href="javascript:void(0);" class="gray" onclick="exportTra();">导出</a>
             </div>
             <div class="today-order-table">
                 <table>
                     <thead>
                         <tr>
                             <th>交易时间</th>
                             <th>金额（元）</th>
                             <th>支付通道</th>
                             <th>订单号</th>
                             <th style="width:35%">流水号</th>
                             <th>状态</th>
                             <th style="width:10%">操作</th>
                         </tr>
                     </thead>
                     <tbody id="traTbody">
                     
                     </tbody>
                 </table>
                 <div class="today-order-pagination">
                     <div class="pages fr">
                         <div id="Pagination" class="pagination-box">
                         </div>
                     </div>
                 </div>
             </div>
         </div>
     </div>
 </div>
 <!-- 添加分类 -->
	<div class="pop_up_box pop_up_box3" style="display: none;">
		<!--黑色遮罩-->
		<div class="sweet-overlay" tabindex="-1"></div>
		<div class="edit_box">
			<a href="javascript:void(0)" class="delete">
				<img src="${resourcePath }/images/delete.png" alt="删除">
			</a>
			<p class="add_type_title">添加退款说明:</p>
			<textarea type="text" class="add_type_input" id="refundMsg" style="height: 90px;" placeholder="退款说明"></textarea>
			<div class="btn_box">
				<a href="javascript:void(0)" class="blue">取消</a>
				<a href="javascript:void(0)" onclick="addRefund()" class="red">确认</a>
			</div>
		</div>
	</div>    
	<!--加载中 -->
<div class="import_loading" style="display: none; margin-left:0">
	<div>
		<img alt="" src="${resourcePath}/images/loading.gif">
		<p>正在退款中...</p>
	</div>
</div>
<div class="order-reason-box" style="display: none; margin-left: 0px;">
     <div class="order-reason">
         <div class="order_top">
             <h1>银行交易详情</h1>
             <a href="javascript:void(0);" onclick="closeOrderDetail(this)" >×</a>
         </div>
         <div class="pra2">
             <span class="fl">银行凭证：</span>
           	<p class="fl" id ="yhpz">123</p>
         </div>
         <div class="pra2">
             <span class="fl">银行流水：</span>
           <p class="fl" id ="yhls">123</p>
         </div>
          <div class="pra2">
             <span class="fl">手续费：</span>
          	 <p class="fl" id ="sxf">23</p>
         </div>
          <div class="pra2">
           <span class="fl">交易结果：</span>
           <p class="fl" id ="payResult">123</p>
         </div>
         <div class="pra2">
          <span class="fl">交易备注：</span>
          <p class="fl" id ="remark">123</p>
         </div>
         <div class="btn">
             <a href="javascript:void(0);" onclick="closeOrderDetail(this)">确定</a>
         </div>
     </div>
 </div>

<script type="text/javascript">
 querytransa(0);
</script>

