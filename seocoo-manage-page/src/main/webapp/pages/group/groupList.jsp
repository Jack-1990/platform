<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="group" items="${groupList}" varStatus="vs">
	<tr  <c:if test="${vs.index %2 ==0 }">class="even"</c:if> >
	   <td>${group.groupCode }</td>
	   <td>${group.fullName }</td>
	   <td><c:if test="${group.status ==0}">正常</c:if></td>
	   <td>
	       <c:if test="${group.groupLevel ==1}">一级代理商</c:if>
	       <c:if test="${group.groupLevel ==2}">二级代理商</c:if>
	       <c:if test="${group.groupLevel ==3}">三级代理商</c:if>
	       <c:if test="${group.groupLevel ==4}">四级代理商</c:if>
	       <c:if test="${group.groupLevel ==5}">五级代理商</c:if>
	       <c:if test="${group.groupLevel ==6}">六级代理商</c:if>
	       <c:if test="${group.groupLevel ==7}">七级代理商</c:if>
	       <c:if test="${group.groupLevel ==8}">八级代理商</c:if>
	       <c:if test="${group.groupLevel ==9}">九级代理商</c:if>
	       <c:if test="${group.groupLevel ==10}">十级代理商</c:if>
	   </td>
	   <td>
	        <c:forEach var="cityList" items="${provinceList}">
		           <c:if test="${cityList.code eq group.lantCode}">
		                    ${cityList.mergerName}
		           </c:if>
	         </c:forEach>
	   </td>
	   <td>${group.rptelephone }</td>
	   <td><fmt:formatDate value="${group.createTime}" pattern="yyyy-MM-dd" /></td>
	   <td><a href="javascript:void(0);" onclick="editGroup('${group.id}');">编辑</a>
	   	   <a href="javascript:void(0);" onclick="editAccount('${group.groupCode}');">管理账户</a>
	   	   <a href="javascript:void(0);" onclick="gotoGroupMer('${group.groupCode}');">管理商户</a>
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
		               'callback'            : groupListCallback  
		         });  
			 }
			 
</script>