/**
 * //初始化 审核列表
 */
var getOutFlag = false ;
var pageIndex = 0
function initWithdrawCashList(){
	getOutFlag = true ;
	withdrawCashList(0);
}
/**
 * 审核列表信息
 */
function withdrawCashList(pageIndex){
	pageIndex = pageIndex ;
	var url = ctx + "/withdrawCash!withdrawCashList.do";
	var startTime = $("#from").val();
	var endTime = $("#to").val();
	var input = $("#merchant_Input").val();
	$("#auditList").load(url,{"pageIndex":pageIndex,"input":input,"startTime":startTime,"endTime":endTime});
}

/**
 * 搜索按钮
 */
function searchDrawCash(){
	$("#Pagination").html("")
	withdrawCashList(0)
	getOutFlag = false ;
}

/**
 * 提交已支付 申请
 */
var val = "";
function submitPay(e){
	$("#pop_box_alert_case").show();
	val = e
	var merchantCode = $(e).siblings("#merchantCode").val();
	var withdrawAmount = $(e).siblings("#withdrawAmount").val();
	$("#pop_box_alert_case .edit_box>p").text("将"+merchantCode+"商户 申请提现为："+withdrawAmount/100+"元,提交为已提现")
}

/**
 * 确认提交
 */
$(document).on('click',"#pop_box_alert_case .blue",function(){
	var url = ctx + "/withdrawCash!submitPay.do";
	var withdrawCashId = $("#withdrawCashId").val();
	$.post(url,{"withdrawCashId":withdrawCashId},function (data){
		var resultCode = data.resultCode
		if(resultCode == "SUCCESS"){
			$(val).parents("tr").remove();
			$("#pop_box_alert_case").hide();
		}else{
			$("#pop_box_alert_case").hide();
			alert("提交失败！重新提交")
		}
	})
})
/**
 * 取消
 */
$(document).on('click',"#pop_box_alert_case .red",function(){
	$("#pop_box_alert_case").hide();
})

/**
 * 导出数据
 */
function getOutMsg(){
	var startTime = $("#from").val();
	var endTime = $("#to").val();
	var input = $("#merchant_Input").val();
	var parm="?getOutFlag="+getOutFlag+"&pageIndex="+pageIndex+"&input="+input+"&startTime="+startTime+"&startTime="+startTime;
	var url = ctx + "/withdrawCash!getOutMsg.do"+parm;
	window.location.href = url;
}
