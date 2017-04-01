<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=0.5, maximum-scale=0.5, minimum-scale=0.5, width=device-width, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<jsp:include page="../../common.jsp"></jsp:include>
<script type="text/javascript">
	require('js/jquery/jquery-1.10.1.min.js');
</script>
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
                <img src="${resourcePath }/images/H52_01.jpg" width="100%" alt=""></td>
        </tr>
        <tr>
            <td>
                <img src="${resourcePath }/images/H52_02.jpg" width="100%" alt=""></td>
        </tr>
        <tr>
            <td>
                <img src="${resourcePath }/images/H52_03.jpg" width="100%" alt=""></td>
        </tr>
        <tr>
            <td>
                <a href="javascript:void(0);" title="">
                    <img src="${resourcePath }/images/H52_04.jpg" width="100%" alt="" onclick="toRegister()">
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <img src="${resourcePath }/images/H52_05.jpg" alt="" width="100%">
            </td>
        </tr>
    </table>
</body>
<input type="hidden"  id="urlVal"  value="${url}">
<script type="text/javascript">
 function toRegister(){
	 var url=$("#urlVal").val();
	 window.location.href=url;
 }
</script>
</html>