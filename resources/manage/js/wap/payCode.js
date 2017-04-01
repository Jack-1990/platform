/**
 * 跳转微信二维码
 */
function gotoWXCode() {
    var amount = $("#amount").val();
    var merchantCode = $("#merchantCode").val();
    var url = ctx + "/codeToPay!payCode.do?merchantCode=" + merchantCode + "&amount=" + amount;
    window.location = url;
}

$("#buttn_2").click(function () {
    alert("d")
})