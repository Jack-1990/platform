<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="container-order" style="">
	<div class="order-tab">
		<ul class="fl">
			<li class="fl current" data-type="directly">代理商商户</li>
		</ul>
		<div class="fr">
			<a href="javascript:void(0);" onclick="backGroupList()">
				<span><i class="icon-reply"></i></span>返回
			</a>
		</div>
	</div>
	<div class="order-box" >
	<!-- 直属商户 -->
<div class="today-order">
	<div class="today-order-top">
		<span><img src="${resourcePath}/images/search.png"></span> <span>商户查询</span>
		<select id="merchant_Select">
			<option value="Code">按外部商户号</option>
			<option value="Name">按商户名称</option>
		</select>
		<input type="text" placeholder="" id="merchant_Input" name="">
		<a href="javascript:void(0);" class="check" onclick="searchMerchant_s()">查询</a>
		<c:if test="${canAdd ==0 }">
			<a href="javascript:void(0);" class="check" id="showAddMerchant" style="float: right" onclick="showAddMerchant_s()">新增</a>
		</c:if>
	</div>
	<div class="today-order-table" id="merchantList">
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
<div class="pop_up_box pop_box_alert" id="pop_box_alert_s" style="display: none;">
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
<input type="hidden" id="groupCode" value="${groupCode }" />
<script type="text/javascript">
	$(document).ready(function()
	{
		//初始化 直属商户
		initDirectlyMerchantList_s()
	})
</script>


