<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../common.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
*{margin:0;padding:0;}
li{list-style-type: none;}
table{border-collapse:collapse;border-spacing:0;}


</style>
</head>
<body>
<br/>
<div style="float:left;">
连接总数:${fn:length(connections) }<br/>
用户连接:
<br/>
<c:forEach items="${connections}" var="mymap" >
登录用户名:<c:out value="${mymap.key}" />
<br/>
</c:forEach>
</div>

<div style="float:left;margin-left:300px;">
消息队列:
<table>
<c:forEach items="${msgQuee}" var="item">  
	<tr>
		<td>
		<font style="font-weight:bold">${item.key} </font>
		</td>
	    <td>
	   </td>
	</tr>
    <tr>
    <td></td>
    <td>
	   	<ul>
	   	<c:forEach items="${item.value}" var="pushMsg">
	      <li>推送类型:${pushMsg.msgType },推送内容:<c:out value="${pushMsg.msg}" />  </li>
	  	</c:forEach>
	   </ul>
    </td>
   </tr>
    
</c:forEach> 
</table>
</div>

</body>
</html>