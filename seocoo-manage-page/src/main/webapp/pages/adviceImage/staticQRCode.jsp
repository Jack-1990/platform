<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<input type="hidden" id="homePageUrl" value="${userpic}"/>
<div  class="add_advert">
    <div class="textc font16 mb20">我的收款码</div>
    <img id="staticQrcode" src="data:image/png;base64,${image}" class="mb10"/>
    <div class="textc"><a href="#" onclick="downloadFile()">下载</a></div>

</div>


<input type="hidden" id="merchantCodePic" value="${merchantCode }">
<script type="text/javascript">
    function downloadFile() {
        // 图片导出为 png 格式
        var type = 'png';
        var imgData = $("#staticQrcode").attr("src");
        console.log(imgData);
        // 加工image data，替换mime type
        imgData = imgData.replace(_fixType(type), 'image/octet-stream');
// 下载后的问题名
        var filename = 'myCode_' + (new Date()).getTime() + '.' + type;
// download
        saveFile(imgData, filename);
    }

    /**
     * 获取mimeType
     * @param  {String} type the old mime-type
     * @return the new mime-type
     */
    var _fixType = function (type) {
        type = type.toLowerCase().replace(/jpg/i, 'jpeg');
        var r = type.match(/png|jpeg|bmp|gif/)[0];
        return 'image/' + r;
    };
    /**
     * 在本地进行文件保存
     * @param  {String} data     要保存到本地的图片数据
     * @param  {String} filename 文件名
     */
    var saveFile = function (data, filename) {
        var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
        save_link.href = data;
        save_link.download = filename;

        var event = document.createEvent('MouseEvents');
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
        save_link.dispatchEvent(event);
    };

</script>