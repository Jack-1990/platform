<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

  <!-- 管理员 -->
 <div class="container-marketing">
     <!-- 基本设置 -->
     <div>
         <div class="order-tab">
             <ul class="fl">
                 <li class="fl current">管理员</li>
             </ul>
              <div class="fr">
             <a href="javascript:void(0);" onclick="loadContent('/merchant.do')"><span><i class="icon-reply"></i></span>返回</a>
         </div>
         </div>
             <div class="muti-marketing">
                     <div class="shop-password">
                         <div class="password">
                             <p>
                                 <label class="textr"><i>*&nbsp;</i>登录名：</label>
                                 <input type="text" placeholder="英文加数字" id="userCode" maxlength="20" onkeyup="checkAccountNum(this,event)" value="${user.loginName}" <c:if test="${not empty user.loginName}">readOnly="readOnly"</c:if>>
                             </p>
                             <p>
                                 <label class="textr"><i>*&nbsp;</i>密码：</label>
                                 <input type="password" placeholder="英文加数字,长度6-20位" id="password" maxlength="20" onkeyup="checkAccountNum(this,event)">
                             </p>
                             <p>
                                 <label class="textr"><i>*&nbsp;</i>确认密码：</label>
                                 <input type="password" placeholder="英文加数字,长度6-20位" id="passwordP" maxlength="20" onkeyup="checkAccountNum(this,event)">
                             </p>
                             <p id="submit"> <a href="javascript:void(0);" onclick="saveMerUser('${user.loginName}');">保存</a></p>
                         </div>
                     </div>
                     <input type="hidden" id="groupCodeVal" value="${user.groupCode }"><input type="hidden" id="merchantVal" value="${user.merchantCode }">
             </div>
     </div>
 </div>

<script type="text/javascript">

</script>


