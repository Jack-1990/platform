<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="order" items="${orderList}" varStatus="vs">
    <tr <c:if test="${vs.index %2 ==0 }">class="even"</c:if>>
        <td><fmt:formatDate value="${order.transTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        <td>${order.amount div 100}</td>
        <td>
           <c:if test="${order.selectTradeType =='API_WXQRCODE'}">微信</c:if>
	       <c:if test="${order.selectTradeType =='API_WXSCAN'}">微信</c:if>
	       <c:if test="${order.selectTradeType =='H5_WXJSAPI'}">微信</c:if>
	       <c:if test="${order.selectTradeType =='API_ZFBQRCODE'}">支付宝</c:if>
	       <c:if test="${order.selectTradeType =='API_ZFBSCAN'}">支付宝</c:if>
	       <c:if test="${order.selectTradeType =='H5_ZFBJSAPI'}">支付宝</c:if>
	       <c:if test="${order.selectTradeType =='H5_ZFBJSAPI'}">QQ钱包</c:if>
	       <c:if test="${order.selectTradeType =='API_QQSCAN'}">QQ钱包</c:if>
	       <c:if test="${order.selectTradeType =='H5_QQJSAPI'}">QQ钱包</c:if>
        </td>
        <td>${order.orderNumber}</td>
        <td>${order.merchantSeq}</td>
        <td>
        <c:if test="${order.status == 1}">已支付</c:if>
         <c:if test="${order.status == 2}">已退款</c:if>
        </td>
        <td><c:if test="${order.isShowTuiKuan && order.status == 1}"><a href="javascript:void(0);" onclick="showRefund(this)">退款</a></c:if>
        <c:if test="${order.status == 1}"><a href="javascript:void(0);" onclick="showOrderDetail(this,1)">详情</a></c:if>
        <c:if test="${order.status == 2}"><a href="javascript:void(0);" onclick="showOrderDetail(this,2)">详情</a></c:if>
        	<input type="hidden" id="merchantSeq" value="${order.merchantSeq}" />
        	<input type="hidden" id="merchantNo" value="${order.merchantNo}" />
        </td>
    </tr>
                         
</c:forEach>
		
 <script type="text/javascript">
	   	     // 分页
	   	     var total =${totalPage};
			 if($("#Pagination").html()==""){
				 $("#Pagination").pagination(total, {  
	//                 'items_per_page'      : items_per_page,  
		               'num_display_entries' : 10,  
		               'num_edge_entries'    : 2,  
		               'prev_text'           : "上一页",  
		               'next_text'           : "下一页",  
		               'callback'            : transaDetailCallback  
		         });  
			 }
			 
</script>