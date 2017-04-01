<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<table>
			<thead>
				<tr>
					<th style="width:18%">外部商户号</th>
					<th style="width:16%">商户名称</th>
					<th>处理状态</th>
					<th>当前级别</th>
					<th>升级级别</th>
					<th>升级方式</th>
					<th>手机号</th>
					<th>升级时间</th>
					<th style="width: 16%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userUpgradeList}" var="userUpgrade" varStatus="vs">

					<tr <c:if test="${vs.index %2 ==0 }">class="even"</c:if>>
						<td>${userUpgrade.merchantCode }</td>
						<td>${userUpgrade.merchantName }</td>
						<td><c:if test="${userUpgrade.applyStatus == 0}">
								审核中
							</c:if> <c:if test="${userUpgrade.applyStatus == 1}">
								重新绑定
							</c:if> <c:if test="${userUpgrade.applyStatus == 2}">
								已完成
							</c:if></td>
						<td>${userUpgrade.fromLevelName }</td>
						<td>${userUpgrade.toLevelName }</td>
						<td><c:if test="${userUpgrade.attrStyle == 'dues'}">
								缴费升级
							</c:if> <c:if test="${userUpgrade.attrStyle == 'member'}">
								发展升级
							</c:if></td>
						<td>${userUpgrade.applyUser }</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${userUpgrade.applyTime }" /></td>
						<td>
						<c:if test="${userUpgrade.applyStatus == 0 }">
								<a href="javascript:void(0);" id="auditMsg" onclick="auditUserUpgrade(this)">审核</a>
								</c:if>
						<c:if test="${userUpgrade.applyStatus == 1}">
								<a href="javascript:void(0);" id="inToBlance" onclick="showAuditbindingBlance(this)">重新绑定</a>
								<a href="javascript:void(0);" id="inToBlance" onclick="finishUserUpgrade(this)">完成</a>
							</c:if>
							<c:if test="${userUpgrade.applyStatus == 2}">
								<a href="javascript:void(0);" id="inToBlance">已完成</a>
							</c:if>
							<input type="hidden" id="applyCode" value="${userUpgrade.applyCode }" />
							<input type="hidden" id="merchantCode" value="${userUpgrade.merchantCode }" />
							</td>
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
			'callback' : pageselectCallbackUserUp
		});
	}

	function pageselectCallbackUserUp(page_index, jq)
	{
		userUpList(page_index);
	}
</script>



