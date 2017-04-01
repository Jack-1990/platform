/**
 * //初始化 审核列表
 */
var flag = false ;
function initAuditList(){
	auditList(0);
}
/**
 * 审核列表信息
 */
function auditList(pageIndex){
	var url = ctx + "/audit!auditList.do";
	var groupCode = $("#groupCode").val();
	var startTime = $("#from").val();
	var endTime = $("#to").val();
	var select = $("#merchant_Select").val();
	var input = $("#merchant_Input").val();
	$("#auditList").load(url,{"groupCode":groupCode,"pageIndex":pageIndex,"select":select,"input":input,"startTime":startTime,"endTime":endTime});
}

/**
 * 搜索按钮
 */
function searchAudit(){
	$("#Pagination").html("")
	auditList(0,merchant_Select)
}

/**
 * 审核 用户提交信息页面
 */
function auditMsgPage(e){
	var url = ctx + "/audit!auditMsgPage.do";
	var merchantCode = $(e).siblings("#merchantCode").val();
	$("#shop_container").load(url,{"merchantCode":merchantCode});
	
}

/**
 *  审核成功
 */
function auditSuccess(e){
	var url = ctx + "/audit!auditSuccess.do";
	var merchantCode = $(e).siblings("#merchantCode").val();
	var level = $(e).siblings("#level").val();
	$(".import_loading").find("p").text("正在审核中").end().show()
	$.post(url,{"merchantCode":merchantCode,"level":level},function(data){
		var resultCode = data.result
		if(resultCode == "SUCCESS"){
			$(".import_loading").hide();
			var url = ctx + "/audit.do";
			$("#shop_container").load(url,{"merchantCode":merchantCode});
		}
	});
}

/**
 * 商户进件
 */

function inToBlanceAudit(e){
	merchantCode= $(e).siblings("#merchantCode").val();
	$("#pop_box_alert1").show();
}
/**
 * 确认弹框
 */
$(document).on('click',"#pop_box_alert1 .blue",function(){
	var url =  ctx + '/audit!intoBlance.do';
	$("#pop_box_alert1").hide();
	$(".import_loading").find("p").text("正在进件中").end().show()
	$.post(url,{"merchantCode":merchantCode},function(data){
		var resultCode =data.resultCode;
		var resultMsg = data.resultMsg
		if (resultCode == "SUCCESS")
		{
			var currnetPage =  getCurrentPage ();
			if(currnetPage == ""){
				currnetPage = 0
			}
			$(".import_loading").hide();
			auditList(currnetPage);
			alert("进件成功！")
		}else {
			$(".import_loading").hide();
			alert(resultMsg)
		}
	})
})

$(document).on('click',"#pop_box_alert1 .red",function(){
	$("#pop_box_alert1").hide();
})

/**
 * 获取当前页 
 * @returns {String}
 */
function getCurrentPage (){
	var currentPage = "";
	$("#Pagination .current").each(function(){
		var val = $(this).text();
		if(!$(this).hasClass("next") && !$(this).hasClass("prev")){
			currentPage = val;
		}
	})
	return currentPage - 1 ;
}


/**
 * 商户---绑定 微信
 */
function showAuditbindingBlance(e){
	var merchantCode = $(e).siblings("#merchantCode").val();
	var groupCode  = $("#groupCode").val();
	var url = ctx + "/audit!showbindingBlance.do";
	$("#shop_container").load(url,{"merchantCode":merchantCode,"groupCode":groupCode},function(){
		bindingWXBlanceAudit();
	});
}
//加载绑定微信页面
function bindingWXBlanceAudit(){
	var merchantCode = $("#merchantCode").val();
	var cardNumber = $("#cardNumber").val();
	var bankNumber = $("#bankNumber").val();
	var realName = $("#realName").val();
	var groupCode  = $("#groupCode").val();
	var url = ctx + "/audit!showWxPage.do";
	$("#auditMsgBlance").load(url,{"groupCode":groupCode,"merchantCode":merchantCode,"cardNumber":cardNumber,"bankNumber":bankNumber,"realName":realName});
}
//加载绑定支付宝页面
function bindingZFBBlanceAudit(){
	var merchantCode = $("#merchantCode").val();
	var cardNumber = $("#cardNumber").val();
	var bankNumber = $("#bankNumber").val();
	var realName = $("#realName").val();
	var groupCode  = $("#groupCode").val();
	var url = ctx + "/audit!showZFBPage.do";
	$("#auditMsgBlance").load(url,{"groupCode":groupCode,"merchantCode":merchantCode,"cardNumber":cardNumber,"bankNumber":bankNumber,"realName":realName});
}

/**
 * 查看进件后的 会员信息
 */
function showAudit(e){
	var merchantCode = $(e).siblings("#merchantCode").val();
	var url = ctx + "/audit!showAudit.do";
	$("#shop_container").load(url,{"merchantCode":merchantCode});
}

/**
 * 审核不通过
 */
function auditFail (){
	$(".pop_up_boxAudit").show();
}
//未通过信息

function sendMsg(e){
	$(".pop_up_boxAudit").hide();
	var failMsg =  $(e).parents(".btn_box").siblings("textarea").val();
	var merchantCode = $("#merchantCode").val();
	var url = ctx + "/audit!auditFail.do";
	if(!flag){
		flag = true ;
		$.post(url,{"merchantCode":merchantCode,"failMsg":failMsg},function(data){
			var resultCode = data.result
			if(resultCode == "SUCCESS"){
				var url = ctx + "/audit.do";
				$("#shop_container").load(url,{"merchantCode":merchantCode});
			}
			flag = false  ;
		});
	}
}
/**
 * 取消
 */
$(document).on('click',".pop_up_boxAudit .blue",function(){
	$(".pop_up_boxAudit").hide();
})