<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}"  scope="request"/>
<script type="text/javascript">
	var ctx = "${ctx}";	//上下文路径
	var resourcePath = "${resourcePath}";
	document.writeln('<script type="text/javascript" src="'+resourcePath+'/js/global.js?d=' + new Date().getTime() + '" ><\/script>');
</script>