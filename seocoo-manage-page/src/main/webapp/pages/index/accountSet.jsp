<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 账号设置 -->
  <div class ="account_box">
  	 <a href="javascript:void(0);" class="return fl" onclick="returnMerAs();"><&nbsp;返回</a>
  	 <div class="account_setting">
  	 	<div class="account_top">
  	 		<div class="account_login">
  	 			<p>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：${user.loginName}</p>
  	 			<p class="textl"><span>修改密码：</span><input type="password" id="passwordAs" placeholder="请输入原密码" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="20"/></p>
  	 			<p class="textl"><span>&nbsp;</span><input type="password" id="newPasswordAs" placeholder="请输入新密码" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="20"/></p>
  	 			<p class="textl"><span>&nbsp;</span><input type="password" id="newPasswordsAs" placeholder="请再次输入新密码" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="20"/></p>
  	 		</div>
  	 	</div>
  	 	<div class="account_bottom clear">
  	 		<p class="indent">如果忘记密码，请联系代理商！</p>
  	 		<p><a href="javascript:void(0);" onclick="sureAs();">确认修改</a></p>
  	 	</div>
  	 </div>
  </div>

    <script type="text/javascript">
    $(function() {
        $(".has-feedback>div>a").click(function() {
            $(".modal-map").slideDown(1000);
        })
    })
    </script>
