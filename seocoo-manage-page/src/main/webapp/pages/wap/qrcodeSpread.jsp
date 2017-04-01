<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<jsp:include page="../../common.jsp"></jsp:include>
<style type="text/css">
    *{
      margin:0;
      padding:0;
    }
    table{
        border-spacing:0;
        border-collapse: collapse;
        }
    img{
    border: 0;
    vertical-align: middle;
}
</style>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<img src="${resourcePath }/images/h51_01.jpg" width="100%" alt=""></td>
	</tr>
	<tr>
		<td style="background:url(${resourcePath }/images/h51_02.jpg) repeat-y;background-size:contain;text-align:center;">
			<img src="data:image/png;base64,${image}" width="60%" alt="" style="border-radius:5px;margin:34px 0;">
		</td>
	</tr>

	<tr>
		<td>
			<img src="${resourcePath }/images/h51_03.jpg" width="100%" alt=""></td>
	</tr>
</table>
</body>
</html>