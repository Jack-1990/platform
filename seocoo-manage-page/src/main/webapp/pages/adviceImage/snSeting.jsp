<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="range mb10 mr40 pt10" style="overflow: hidden;">
    <div class="range_top">
        <p class="second mt10">
            <span class="fl ml20">生成.net 客户端 SN 码</span>
            <a href="javascript:void(0)" class="fr mr20 mt10 new_staff textc" onclick="saveSnRef()">新增</a>
        </p>
    </div>
    <table class="mt20">
        <thead>
        <tr>
            <th>SN码</th>
            <th>商户编码</th>
            <th>状态</th>
            <th style="width:33%">操作</th>
        </tr>
        </thead>
        <tbody id="snCodeIns">
        <c:if test="${merchantNetSn.merchantCode != null}">
            <tr>
                <td>${merchantNetSn.snCode}</td>
                <td>${merchantNetSn.merchantCode}</td>
                <c:if test="${merchantNetSn.activated == 1}">
                    <td>激活</td>
                </c:if>
                <c:if test="${merchantNetSn.activated == 0}">
                    <td>未激活</td>
                </c:if>
                <td>
                    <a href='javascript:void(0);' class='mr20 delete' onclick='snCodeDel(this)'>删除</a>
                    <c:if test="${merchantNetSn.activated == 0}">
                        <a href='javascript:void(0);' class='ml20 edit' onclick='activatSNCode(this)'>激活</a>
                    </c:if>
                    <c:if test="${merchantNetSn.activated == 1}">
                        <a href='javascript:void(0);' class='ml20 edit' onclick='snCodeEdit(this)'>重置</a>
                    </c:if>
                </td>
            </tr>
        </c:if>
        </tbody>

    </table>
</div>
<input type="hidden" id="merchantCode" value="${merchantCode }">
<script type="text/javascript">
    //新增        生成序列号
    function saveSnRef() {
        var merchantCode = $("#merchantCode").val();
        var url = ctx + "/paySetting!saveSnRef.do";

        $.post(url, {"merchantCode": merchantCode},
            function (data) {
                var resultCode = data.resultCode;
                var resultMsg = data.resultMsg;
                if (resultCode == "SUCCESS") {
                    var snCode = resultMsg.snCode;
                    var activated = resultMsg.activated;
                    if (activated == 0) {
                        activated = "未激活"
                    } else {
                        activated = "激活"
                    }
                    var html = "" +
                        "<tr>" +
                        "<td>" + snCode + "</td>" +
                        "<td>" + merchantCode + "</td>" +
                        "<td>" + activated + "</td>" +
                        "<td>";
                    if (activated == "未激活") {
                        html += "<a href='javascript:void(0);' class='mr20 delete' onclick='snCodeDel(this)'>删除</a>" +
                            "<a href='javascript:void(0);' class='ml20 edit' onclick='activatSNCode(this)'>激活</a>";
                    }
                    if (activated == "激活") {
                        html += "<a href='javascript:void(0);' class='mr20 delete' onclick='snCodeDel(this)'>删除</a>" +
                            "<a href='javascript:void(0);' class='ml20 edit' onclick='snCodeEdit(this)'>重置</a>";
                    }
                    html += "</td>" +
                        "</tr>";
                    $("#snCodeIns").append(html);
                } else {
                    alert(data.resultMsg)
                }
            })
    }
    // 删除 sn
    function snCodeDel() {
        var merchantCode = $("#merchantCode").val();
        var url = ctx + "/paySetting!snCodeDel.do";
        $.post(url, {"merchantCode": merchantCode}, function (data) {
            gotoSNSeting()
        });
    }
    // 重置 SN
    function snCodeEdit() {
        var merchantCode = $("#merchantCode").val();
        var url = ctx + "/paySetting!snCodeEdit.do";
        $.post(url, {"merchantCode": merchantCode}, function (data) {
            gotoSNSeting()
        });
    }
    // 激活 SN
    function activatSNCode() {
        var merchantCode = $("#merchantCode").val();
        var url = ctx + "/paySetting!activatSNCode.do";
        $.post(url, {"merchantCode": merchantCode}, function (data) {
            gotoSNSeting()
        });
    }

    function gotoSNSeting() {
        var merchantCode = $("#merchantCode").val();
        var url = ctx + "/paySetting!snSeting.do?merchantCode=" + merchantCode;
        $('#menuContent').load(url);
    }
</script>