<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<table>
	<thead>
		<tr>
			<th style="width: 18%">外部商户号</th>
			<th style="width: 16%">商户名称</th>
			<th>提现金额(元)</th>
			<th>开户银行</th>
			<th>银行卡号</th>
			<th>手机号</th>
			<th>申请时间</th>
			<th style="width: 16%">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${withdrawCashPage}" var="withdrawCash" varStatus="vs">

			<tr <c:if test="${vs.index %2 ==0 }">class="even"</c:if>>
				<td>${withdrawCash.merchantCode }</td>
				<td>${withdrawCash.merchantName }</td>
				<td>${withdrawCash.withdrawAmount/100 }</td>
				<td>${withdrawCash.bankName }</td>
				<td>${withdrawCash.cardNumber }</td>
				<td>${withdrawCash.loginName }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${withdrawCash.applyTime }" /></td>
				<td><a href="javascript:void(0);" onclick="submitPay(this)">提交已支付</a> 
				<input type="hidden" id="withdrawCashId" value="${withdrawCash.id }" />
				<input type="hidden" id="merchantCode" value="${withdrawCash.merchantCode }" />
				<input type="hidden" id="withdrawAmount" value="${withdrawCash.withdrawAmount }" /></td>
			</tr>

		</c:forEach>

	</tbody>
</table>
<input type="hidden" id="merchantType" value="1" />
<script type="text/javascript">
	// 分页
	var total = ${totalPage};

	if ($("#Pagination").html() == "")
	{
		$("#Pagination").pagination(total,
		{
			//                 'items_per_page'      : items_per_page,  
			'num_display_entries' : 10,
			'num_edge_entries' : 2,
			'prev_text' : "上一页",
			'next_text' : "下一页",
			'callback' : pageselectCallbackWithDrawCase
		});
	}

	function pageselectCallbackWithDrawCase(page_index, jq)
	{
		withdrawCashList(page_index);
	}
</script>



