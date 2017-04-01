<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<jsp:include page="../../common.jsp"></jsp:include>

<script type="text/javascript">
	requireCss('css/style.css');
</script>

</head>
<body style="background:#F3F6F5;">
    <!-- header -->
    <div class="detail">
    	<ul>
    	    <c:forEach var="rate" items="${rateList }">
	    	   <li>
	    			<div class="member">
	    				<img src="${path }${rate.levelPic}" alt="">
	    				<span>${rate.levelName}</span>
	    			</div>
	    			<div class="type-pay">
	    			     <c:forEach var="level" items="${rate.levelList}">
		    			     <div>
		    					<img src="${resourcePath }/images/${level.payChannel}.png" alt="">
		    					<span>${level.payChannelName}</span>
		    				</div>
	    			     </c:forEach>
	    			</div>
	    			<div class="rate">
	    			    <c:forEach var="ve" items="${rate.levelList}">
		    			   <p>${ve.rate }</p>
	    			     </c:forEach>
	    			</div>
	    		</li>
    	    </c:forEach>
    	    
    	</ul>
    </div>
</body>
</html>