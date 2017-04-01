/**
 * //初始化 直属商户
 */
function initDirectlyMerchantList_s(){
	directlyMerchantList_s(0,"","");
}
/**
 * 直属商户列表信息
 */
function directlyMerchantList_s(pageIndex,select,input){
	var url = ctx + "/groupMer!directlyMerchantList.do";
	var groupCode = $("#groupCode").val();
	$("#merchantList").load(url,{"groupCode":groupCode,"pageIndex":pageIndex,"select":select,"input":input});
}



/***
 * 查询商户
 */
function searchMerchant_s(){
	$("#Pagination").html("")
	var merchant_Select = $("#merchant_Select").val();
	var merchant_Input = $("#merchant_Input").val();
	//判断是直属还是非直属
	var merchantType = $("#merchantType").val();
	//直属
	directlyMerchantList_s(0,merchant_Select,merchant_Input);

}


/**
 * 添加商户列表
 */
function showAddMerchant_s(){
	var url = ctx + "/groupMer!showAddMerchant.do";
	var groupCode = $("#groupCode").val();
	$("#shop_container").load(url,{"groupCode":groupCode});
}

/**
 * 提交商户信息 并保存，
 */
var iscon=false;
function saveMerchant_s(groupCode){
	if(iscon){
		return false;
	}
	iscon=true;
	
	var tf=false;
	$("input[type='text']").each(function(){
		var name=$(this).attr("name");
		if(name !="merchant.parentMchntId" && name !="merchant.identification"){
			var val=$(this).val();
			if(strIsNull(val)){
				tf=true;
				iscon=false;
				alert($(this).siblings("label").html()+"必须填写");
				$(this).focus();
				return false;
			}
		}
	})
	if(tf){
		//保证不往下执行
		return false;
	}
    var merchantLevel =  $("select[name='merchant.merchantLevel']").val();
	if(merchantLevel == 0){
		iscon=false;
		tf=true;
		alert("请选择 商户等级");
        return false;
	}
	if(tf){
		//保证不往下执行
		return false;
	}
	var options = {
		      dataType : "json",
		      async: false,
		      success :showMerSuccess_s,
		      error:showMerFailError_s,
		      timeout: 30000      //限制请求的时间，当请求大于3秒后，跳出请求
	};
    $(".import_loading").show();
	var url = ctx + '/groupMer!saveMerchant.do';
	$(".muti-marketing #addMerchant").attr('action',url);
	jQuery(".muti-marketing #addMerchant").ajaxSubmit(options);
	iscon=false;
}


/**
 * 保存失败
 */

function showMerFailError_s(){
	alert("保存失败");
    $(".import_loading").hide();
}

/**
 * 保存成功
 */
function showMerSuccess_s(){
	var groupCode =$("#groupCode").val();
    $(".import_loading").hide();
	var url = ctx + '/groupMer.do?groupCode='+groupCode;
	$('#shop_container').load(url);	
}


/**
 * 编辑 商户信息
 */
function showEditMerchant_s(e){
	var merchantId = $(e).siblings("#merchantId").val();
	var groupCode = $("#groupCode").val();
	$(".import_loading p").text("正在进行中...");
	$(".import_loading").show();
	var url = ctx + "/groupMer!showEditeMerchant.do";
	$("#shop_container").load(url,{"groupCode":groupCode,"merchantId":merchantId},function () {
        $(".import_loading").hide()
    });
}


//绑定账户
function binMerUser_s(e){
	var merchantId = $(e).siblings("#merchantId").val();
	var groupCode = $("#groupCode").val();
	if(strIsNull(merchantId)){
		alert("门店信息不存在，请刷新页面");
		return false;
	}
	var url = ctx + "/groupMer!binMerUser.do";
	$("#shop_container").load(url,{"groupCode":groupCode,"merchantId":merchantId});
}


/**
 * 保存编辑后的商户信息
 */
var isconEdi=false;
function saveEditeMerchant_s(groupCode){
	//防止多次点击
	if(isconEdi){
		return false;
	}
	isconEdi=true;
	
	var tf=false;
	$("input[type='text']").each(function(){
		var name=$(this).attr("name");
		if(name !="merchant.parentMchntId"  && name !="merchant.identification"){
			var val=$(this).val();
			if(strIsNull(val)){
				tf=true;
				isconEdi=false;
				alert($(this).siblings("label").html()+"必须填写");
				$(this).focus();
				return false;
			}
		}
	})
	if(tf){
		//保证不往下执行
		return false;
	}
	var merchantLevel =  $("select[name='merchant.merchantLevel']").val();
    if(merchantLevel == 0){
        alert("请选择 商户等级");
        isconEdi=false;
        return false;
    }
	if(tf){
		//保证不往下执行
		return false;
	}
	var options = {
		      dataType : "json",
		      async: false,
		      success :showMerSuccess_s,
		      error:showMerFailError_s,
		      timeout: 150000      //限制请求的时间，当请求大于3秒后，跳出请求
	};
	$(".import_loading").show();
	var url = ctx + '/groupMer!saveEditeMerchant.do';
	$(".muti-marketing #addMerchant").attr('action',url);
	jQuery(".muti-marketing #addMerchant").ajaxSubmit(options);
	iscon=false;
}


//保存账号
function saveMerUser_s(id){
	var userCode=$("#userCode").val();
	var password=$("#password").val();
	var passwordP=$("#passwordP").val();
	
	if(strIsNull(userCode)){
		alert("登录名必须填写");
		$("#userCode").focus();
		return false;
	}
	if(strIsNull(password)){
		alert("密码必须填写");
		$("#password").focus();
		return false;
	}
	if (password.length < 6 || password.length > 20) {
		alert("密码必须长度应在6-20位之间");
		$("#password").focus();
		return false;
	}
	if(strIsNull(passwordP)){
		alert("确认密码必须填写");
		$("#passwordP").focus();
		return false;
	}
	if(passwordP != password){
		alert("两次密码输入不一致");
		$("#passwordP").val("");
		$("#passwordP").focus();
		return false;
	}
	if (password.length < 6 || password.length > 20) {
		alert("密码必须长度应在6-12位之间");
		$("#password").focus();
		return false;
	}
	
	var groupCode=$("#groupCodeVal").val();
	var merchantCode=$("#merchantVal").val();
	
	var url=ctx;
	if(strIsNull(id)){
		url = url+ "/groupMer!saveUser.do";
	}else{
		url = url+ "/groupMer!saveUserEdit.do";
	}
	$.post(url,{"userCode":userCode,"password":password,"groupCode":groupCode,"merchantCode":merchantCode},
		function(data){
		var result=data.result;
		if(result=="SUCCESS"){
			loadContent("/groupMer.do?groupCode="+groupCode);
		}
		if(result=="REPEAT"){
			alert("账户名重复，重新输入账号");
			$("#userCode").val("");
			$("#userCode").focus();
		}
		if(result=="FAIL"){
			alert("保存失败，刷新页面再试");
		}
	})
}

/**
 * 商户---绑定 微信
 */
function showbindingBlance_s(e){
	var merchantId = $(e).siblings("#merchantId").val();
	var groupCode = $("#groupCode").val();
	var url = ctx + "/groupMer!showbindingBlance.do";
	$("#shop_container").load(url,{"groupCode":groupCode,"merchantId":merchantId});
	bindingWXBlance(merchantId)
}

function backGroupList(){
	loadContent("/group.do");
}



/**
 * 商户进件
 */
var merchantId=0;
function inToBlance_s(e){
	 merchantId= $(e).siblings("#merchantId").val();
	$("#pop_box_alert_s").show();
}
/**
 * 确认弹框
 */
$(document).on('click',"#pop_box_alert_s .blue",function(){
	
	var url =  ctx + '/merchant!intoBlance.do';
	$("#pop_box_alert_s").hide();
	$(".import_loading").find("p").text("正在进件中").end().show()
	$.post(url,{"merchantId":merchantId},function(data){
		var resultCode =data.resultCode;
		var resultMsg = data.resultMsg
		if (resultCode == "SUCCESS")
		{
			$(".import_loading").hide();
			initDirectlyMerchantList_s();
			alert("进件成功！")
		}else {
			$(".import_loading").hide();
			alert(resultMsg)
		}
	})
})

$(document).on('click',"#pop_box_alert_s .red",function(){
	$("#pop_box_alert_s").hide();

})

