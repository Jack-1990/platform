<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 非直属商户 -->
		<table>
			<thead>
				<tr>
					<th style="width:18%">外部商户号</th>
					<th style="width:16%">商户名称</th>
					<th>状态</th>
					<th>支付绑定</th>
					<th>手机号</th>
					<th>创建时间</th>
					<th style="width: 20%">归属代理商</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${merchantPage}" var="merchant" varStatus="vs">

					<tr <c:if test="${vs.index %2 ==0 }">class="even"</c:if>>
						<td>${merchant.outMchntId }</td>
						<td>${merchant.mchntName }</td>
						<td><c:if test="${merchant.flag == 0}">
								新建
							</c:if> <c:if test="${merchant.flag == 1}">
								已进件
							</c:if> <c:if test="${merchant.flag == 2}">
								已绑定
							</c:if></td>
						<td>
						<c:if test="${merchant.payChannel ==null }">
								未绑定支付通道
						</c:if>
						<c:forEach items="${merchant.payChannel}" var="payChannel">
							<c:if test="${payChannel.apiCode =='0005'}">
								微信
							</c:if>
							<c:if test="${payChannel.apiCode =='0007'}">
								支付宝   
							</c:if>
						</c:forEach></td>
						<td>${merchant.telephone }</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${merchant.createTime }" /></td>
						<td>${merchant.fullName}</td>
					</tr>

				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="merchantType" value="2" />
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
			'callback' : pageselectCallbackMercNotDir
		});
	}

	function pageselectCallbackMercNotDir(page_index, jq)
	{
		var merchant_Select = $("#merchant_Select").val();
		var merchant_Input = $("#merchant_Input").val();
		notDirectlyMerchantList(page_index,merchant_Select,merchant_Input);
	}
</script>