<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<table>
			<thead>
				<tr>
					<th style="width:18%">外部商户号</th>
					<th style="width:16%">商户名称</th>
					<th>认证状态</th>
					<th>进件状态</th>
					<th>支付绑定</th>
					<th>手机号</th>
					<th>提交时间</th>
					<th style="width: 16%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${merchantInfo}" var="merchantInfo" varStatus="vs">

					<tr <c:if test="${vs.index %2 ==0 }">class="even"</c:if>>
						<td>${merchantInfo.merchantCode }</td>
						<td>${merchantInfo.realName }</td>
						<td><c:if test="${merchantInfo.certifyStatus == 0}">
								未完善
							</c:if> <c:if test="${merchantInfo.certifyStatus == 1}">
								审核中
							</c:if> <c:if test="${merchantInfo.certifyStatus == 2}">
								已认证
							</c:if></td>
						<td>
						<c:if test="${merchantInfo.flag == null }">
								未进件
							</c:if>
						<c:if test="${merchantInfo.flag == 0}">
								新建
							</c:if> <c:if test="${merchantInfo.flag == 1}">
								已进件
							</c:if> <c:if test="${merchantInfo.flag == 2}">
								已绑定
							</c:if></td>
							<td>
							<c:if test="${merchantInfo.payChannel ==null}">
								未绑定支付通道
							</c:if>
							<c:forEach items="${merchantInfo.payChannel}" var="payChannel">
							<c:if test="${payChannel.apiCode =='0005' && payChannel.status == 1}">
								微信
							</c:if>
							<c:if test="${payChannel.apiCode =='0007' && payChannel.status == 1}">
								支付宝   
							</c:if>
							
							</c:forEach>
						</td>
						<td>${merchantInfo.createUser }</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${merchantInfo.submitAuditTime }" /></td>
						<td>
						<c:if test="${merchantInfo.certifyStatus <= 1 && merchantInfo.flag == null}">
								<a href="javascript:void(0);" id="auditMsg" onclick="auditMsgPage(this)">审核</a>
								</c:if>
						<c:if test="${merchantInfo.flag < 1}">
								<a href="javascript:void(0);" id="inToBlance" onclick="inToBlanceAudit(this)">进件</a>
							</c:if> <c:if test="${merchantInfo.flag >= 1}">
								<a href="javascript:void(0);" id="inTo" onclick="inToBlanceAudit(this)">重新进件</a>
								<a href="javascript:void(0);" onclick="showAuditbindingBlance(this)">绑定</a>
							</c:if> 
							<a href="javascript:void(0);" onclick="showAudit(this)">查看</a>
							<input type="hidden" id="merchantCode" value="${merchantInfo.merchantCode }" />
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
			'callback' : pageselectCallbackAudit
		});
	}

	function pageselectCallbackAudit(page_index, jq)
	{
		auditList(page_index);
	}
</script>



