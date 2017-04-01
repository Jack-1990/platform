<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- app设置 -->
<div class="app sub" id="paySetting">

    <h1>
        <span class="fl">轮播图设置</span>
    </h1>
    <div class="sub_pay_setting">
        <div class="pay_left fl member_pay_left mt20">
            <div class="font14 other textc current">轮播图设置</div>
            <p style="height: 300px;"></p>
        </div>
        <div id="menuContent">
            <div class="add_advert">
                <div class="edit_advert fl">
                    <div class="add_pic">
                        <ul>
                            <c:choose>
                                <c:when test="${size>0 }">
                                    <c:forEach items="${picList }" var="pic" varStatus="vs">
                                        <li class="fl" id="lipic_${vs.index }">
                                            <a href="javascript:void(0);" title="" onclick="uploadImage(${vs.index })">
                                                <img src="${resourcePath }${pic.picUrl}" alt="">
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <li class="fl" id="lipic_0">
                                        <a href="javascript:void(0);" title="" onclick="uploadImage(0)">
                                            <span>点击上传图片</span>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
                <div class="choose_advert fl">
                    <p>
                        轮播图设置：
                        <select onchange="changePicNum(this.value)" id="optSel">
                            <option value="1" <c:if test="${size==1}">selected</c:if>>1</option>
                            <option value="2" <c:if test="${size==2}">selected</c:if>>2</option>
                            <option value="3" <c:if test="${size==3}">selected</c:if>>3</option>
                            <option value="4" <c:if test="${size==4}">selected</c:if>>4</option>
                        </select>
                    </p>
                    <p class="setting">
                        <a href="javascript:void(0);" onclick="saveSet()">保存设置</a>
                    </p>
                    <p class="note">备注：点击图片区域可更换修改轮播图，默认风格开启自定义风格即关闭。上传轮播图建议 尺寸：800*200</p>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="merchantCodePic" value="${merchantCode }">
<script type="text/javascript">
    $(function () {

        // 获取商城设置ul的宽度
        var liwid = $(".edit_advert .add_pic ul li").width() + 2;
        var lilen = $(".edit_advert .add_pic ul li").length;
        var ulwid = $(".edit_advert .add_pic ul").width((liwid * lilen) + "px");

        //开启关闭app默认轮播图设置
        $("#control").click(function () {
            $(".add_advert .switch label").toggleClass("checkbox");
            $(this).toggleClass("control");
            changeSwitch();

        })
    })

//
//    function gotoAdviceImage() {
//        var merchantCode = $("#merchantCode").val();
//        var url = ctx + "/paySetting!adviceImage.do?merchantCode=" + merchantCode;
//        $('#menuContent').load(url);
//    }
    /*
     * 选择  轮播图张数
     */
    function changePicNum(opt) {
        var optval = parseInt(opt);
        var html = "";
        var liLen = parseInt($(".add_advert .add_pic ul li").length);
        if (optval > liLen) {
            var cycle = optval - liLen;
            for (var i = 0; i < cycle; i++) {
                html += "<li class='fl' id='lipic_" + (liLen + i) + "'><a href='javascript:void(0);' onclick='uploadImage(" + (liLen + i) + ")'><span>点击上传图片</span></a></li>";
            }
            $(".add_advert .add_pic ul").append(html);
        }
        if (liLen > optval) {
            $(".add_advert .add_pic ul li").each(function (i) {
                var j = i + 1;
                if (j > optval) {
                    $(this).remove();
                }
            })
        }
        var wid = 201 * optval + "px";
        $(".add_advert .add_pic ul").css("width", wid);
    }


    /*
     *  上传图片
     */
    function uploadImage(i) {
        $.seocooUpload({
            "isPress": 1
        }, function (data) {
            var url = data.url;
            //移除上传提示信息，之前有的图片信息
            $("#lipic_" + i).find("span").remove();
            $("#lipic_" + i).find("img").remove();
            //显示上传的图片
            $("#lipic_" + i).find("a").append("<img src='' alt=''>");
            $("#lipic_" + i).find("img").attr("src", url);
        })
    }


    /*
     * 保存图片
     */
    var tf = true;
    function saveSet() {
        if (!tf) {
            return false;
        }
        var merchantCode = $("#merchantCodePic").val();
        var liLen = parseInt($(".add_advert .add_pic img").length);
        var opt = parseInt($("#optSel").val());
        if (opt > liLen) {
            alert("您选择轮播图设置为" + opt + "张，现在您只上传了" + liLen + "张图片");
            return false;
        }

        var url = "";
        $(".add_advert .add_pic img").each(function () {
            var pic = $(this).attr("src");
            var filename = pic.substring(pic.indexOf("/images"), pic.length);
            url += filename + ",";
        })
        if (url != "" && url != "undefined") {
            tf = false;
            url = url.substring(0, url.length - 1);
            $.post(ctx + '/adviceImage!saveImage.do', {'url': url, 'picNum': opt, 'merchantCode': merchantCode}, function (data) {
                var result = data.result;
                if (result == "SUCCESS") {
                    alert("保存成功");
                }
                tf = true;
            })
        }
    }
</script>