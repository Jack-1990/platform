/**
 * //初始化 审核列表
 */
function initUserUpList(){
	userUpList(0);
}
/**
 * 审核列表信息
 */
function userUpList(pageIndex){
	var url = ctx + "/userUpgrade!userUpList.do";
	var startTime = $("#from").val();
	var endTime = $("#to").val();
	var select = $("#merchant_Select").val();
	var input = $("#merchant_Input").val();
	$("#userUpList").load(url,{"pageIndex":pageIndex,"select":select,"input":input,"startTime":startTime,"endTime":endTime});
}

/**
 * 搜索按钮
 */
function searchUserUp(){
	$("#Pagination").html("")
	userUpList(0,merchant_Select)
}
/**
 * 审核 用户提交信息页面
 */
function auditUserUpgrade(e){
	var url = ctx + "/userUpgrade!auditUserUpgrade.do";
	var applyCode = $(e).siblings("#applyCode").val();
	$("#shop_container").load(url,{"applyCode":applyCode});
	
}

/**
 * 升级审核通过
 */
function auditUserUpgradeSuccess(e){
	var url = ctx + "/userUpgrade!auditUserUpgradeSuccess.do";
	var merchantCode = $(e).siblings("#merchantCode").val();
	var applyCode = $(e).siblings("#applyCode").val();
	$.post(url,{"applyCode":applyCode,"merchantCode":merchantCode},function(data){
		resultCode = data.resultCode
		if (resultCode == "SUCCESS")
		{
			var url = ctx + "/userUpgrade.do";
			$("#shop_container").load(url,{"merchantCode":merchantCode});
			
		}else{
			alert("提交失败")
		}
	});
}

/**
 * 升级审核失败
 */
function auditUserUpgradeFail(){
	$(".pop_up_boxUserUp").show();
	
}
/**
 * 发送 升级未通过 信息
 */
function sendUserUpMsg(e){
	$(".pop_up_boxUserUp").hide();
	var url = ctx + "/userUpgrade!auditUserUpgradeFail.do";
	var merchantCode = $("#merchantCode").val();
	var applyCode = $("#applyCode").val();
	var failMsg =  $(e).parents(".btn_box").siblings("textarea").val();
	$.post(url,{"applyCode":applyCode,"merchantCode":merchantCode,"failMsg":failMsg},function(data){
		resultCode = data.resultCode
		if (resultCode == "SUCCESS")
		{
			var url = ctx + "/userUpgrade.do";
			$("#shop_container").load(url,{"merchantCode":merchantCode});
		}else{
			alert("提交失败")
		}
	});
}

/**
 * 完成会员升级
 */

function finishUserUpgrade(e){
	$(e).removeAttr("onclick");
	var url = ctx + "/userUpgrade!finishUserUpgrade.do";
	var applyCode = $(e).siblings("#applyCode").val();
	var merchantCode = $(e).siblings("#merchantCode").val();
	$.post(url,{"applyCode":applyCode,"merchantCode":merchantCode},function(data){
		resultCode = data.resultCode
		if (resultCode == "SUCCESS")
		{
			$(e).parents("tr").find("td:eq(2)").text("已完成")
			$(e).removeAttr("onclick").text("已完成").siblings().remove();
		}else{
			alert("提交失败");
			$(e).addAttr("onclick","finishUserUpgrade(this)");
		}
	})
}

/**
 * 取消
 */
$(document).on('click',".pop_up_boxUserUp .blue",function(){
	$(".pop_up_boxUserUp").hide();
})