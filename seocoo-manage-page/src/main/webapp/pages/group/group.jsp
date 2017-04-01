<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


 <!-- 商户管理 -->
            <div class="container-order" >
                <div class="order-tab">
                    <ul class="fl">
                        <li class="fl current">代理列表</li>
                    </ul>
                </div>
                <div class="order-box">
                    <div class="today-order">
                        <div class="today-order-top">
                            <span><img src="${resourcePath }/images/search.png"></span>
                            <span>代理查询</span>
                            <select id="querySel" onchange="querySeld(this.value)">
                                <option value="groupCode">按代理商编号</option>
                                <option value="fullName">按代理商名称</option>
                            </select>
                            <input type="text" placeholder="按代理商编号" id="queInput">
                            <a href="javascript:void(0);" class="check" onclick="queryGroupList(0);">查询</a>
                            <a href="javascript:void(0);" class="check" style="float:right" onclick="addGroup();">新增</a>
                        </div>
                        <div class="today-order-table">
                            <table>
                                <thead>
                                    <tr>
                                        <th>代理商编号</th>
                                        <th style="width:16%">代理商名称</th>
                                        <th>状态</th>
                                        <th>类别</th>
                                        <th>城市</th>
                                        <th>手机号</th>
                                        <th>创建时间</th>
                                        <th style="width:15%">操作</th>
                                    </tr>
                                </thead>
                                <tbody id="groupTbody">
                                   
                                </tbody>
                            </table>
                            <div class="today-order-pagination">
                                <div class="pages fr">
                                    <div id="Pagination" class="pagination-box">
                                 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
          </div>
            
            <!-- 管理员 -->
            <div class="container-marketing" style="display:none;">
                <!-- 基本设置 -->
                <div>
                    <div class="order-tab">
                        <ul class="fl">
                            <li class="fl current">管理员</li>
                        </ul>
                         <div class="fr">
                        <a href="javascript:void(0);" onclick="loadContent('/group.do')"><span><i class="icon-reply"></i></span>返回</a>
                    </div>
                    </div>
                        <div class="muti-marketing">
                                <div class="shop-password">
                                    <div class="password">
                                        <p>
                                            <label class="textr"><i>*&nbsp;</i>登录名：</label>
                                            <input type="text" placeholder="英文加数字" id="userCode" maxlength="20" onkeyup="checkAccountNum(this,event)">
                                        </p>
                                        <p>
                                            <label class="textr"><i>*&nbsp;</i>密码：</label>
                                            <input type="password" placeholder="英文加数字,长度6-20位" id="password" maxlength="20" onkeyup="checkAccountNum(this,event)">
                                        </p>
                                        <p>
                                            <label class="textr"><i>*&nbsp;</i>确认密码：</label>
                                            <input type="password" placeholder="英文加数字,长度6-20位" id="passwordP" maxlength="20" onkeyup="checkAccountNum(this,event)">
                                        </p>
                                        <p id="submit"> <input type="hidden" id="groupCodeVal" value=""><a href="javascript:void(0);" onclick="saveUser(1);">保存</a></p>
                                    </div>
                                </div>
                        </div>
                </div>
            </div>
      
<script type="text/javascript">
   queryGroupList(0);
</script>

