//查询列表
function querytransa(index){
	if(index == 0){
		$("#Pagination").html("");//分页清空
	}
	var startDate=$("#from").val();
	var endDate=$("#to").val();
	
	var url  =  ctx + "/transaDetail!queryList.do";
	$('#traTbody').load(url,{
		"pageIndex":index,
		 "startDate":startDate,
		 "endDate":endDate
	});
}

function transaDetailCallback(page_index, jq){
	querytransa(page_index);
}

function exportTra(){
	var startDate=$("#from").val();
	var endDate=$("#to").val();
	
	var parm="?startDate="+startDate+"&endDate="+endDate;
	var url=ctx+'/transaDetail!exportEx.do'+parm;
	window.location.href = url;
}

/**
 * 显示退款弹出框
 */
var merchantSeq = "";
var merchantNo = "";
var refundItem = "";
function showRefund(e){
	refundItem = e ;
	$(".pop_up_box3 .add_type_input").val("");
	merchantSeq = $(e).siblings("#merchantSeq").val();
	merchantNo = $(e).siblings("#merchantNo").val();
	
	$(".pop_up_box3").show();
}
//隐藏弹框
$(document).on('click',".pop_up_box3 .delete",function (){
	$(this).parents(".pop_up_box3").hide();
})
//隐藏弹框
$(document).on('click',".pop_up_box3 .blue",function (){
	$(this).parents(".pop_up_box3").hide();
})
/**
 * 确认退款
 */
function addRefund(){
	var refundMsg =  $(".pop_up_box3 .add_type_input").val();
	if(strIsNull(refundMsg)){
		alert("请填写退款原因！");
		return false ;
	}
	$(".pop_up_box3").hide();
	$(".import_loading").show();
	
	var url = ctx + "/transaDetail!cancelTrans.do"
	$.post(url,{"merchantSeq":merchantSeq,"merchantNo":merchantNo,"reserve":refundMsg},function (data){
		var resultCode =  data.resultCode;
		var resultMsg =  data.resultMsg;
		if(resultCode == "SUCCESS"){
			$(refundItem).parents("td").prev("td").text("已退款");
			$(refundItem).remove();
			alert("退款成功")
			$(".import_loading").hide();
		}else {
			alert(resultMsg)
			$(".import_loading").hide();
		}
	})
	
}

/**
 * 显示支付\退款订单详情
 */
function showOrderDetail(e,type){
	var url = ctx + "/transaDetail!queryPayResult.do";
	merchantSeq = $(e).siblings("#merchantSeq").val();
	merchantNo = $(e).siblings("#merchantNo").val();
	$.post(url,{"merchantSeq":merchantSeq,"merchantNo":merchantNo,"tradeType":type},function(data){
		var resultCode = data.resultCode;
		if (resultCode == "SUCCESS"){
			var PayResultMsg = data.PayResultMsg;
			$(".order-reason-box #yhpz").text(PayResultMsg.bankOrderNo)
			$(".order-reason-box #yhls").text(PayResultMsg.bankTradeNo)
			$(".order-reason-box #sxf").text(PayResultMsg.fee/100)
			switch(PayResultMsg.tradeStatus){
			case 'S':
				$(".order-reason-box #payResult").text("订单交易成功");
				break;
			case 'E':
				$(".order-reason-box #payResult").text("订单失败");
				break;
			case 'R':
				$(".order-reason-box #payResult").text("未支付（待支付)");
				break;
			case 'C':
				$(".order-reason-box #payResult").text("已撤销");
				break;
			case 'T':
				$(".order-reason-box #payResult").text("订单转入退款");
				break;
				
			}
			$(".order-reason-box #remark").text(PayResultMsg.remark)
			$(".order-reason-box").show();//显示弹框
		}else{
			alert(data.resultMsg)
		}
	})
}


/**
 * 关闭订单详情
 */
function closeOrderDetail(e){
	$(e).parents(".order-reason-box").hide();
	
}