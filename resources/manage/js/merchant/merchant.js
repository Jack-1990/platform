var merchantId="";
/**
 * //初始化 直属商户
 */
function initDirectlyMerchantList(){
	directlyMerchantList(0,"","");
}
/**
 * 直属商户列表信息
 */
function directlyMerchantList(pageIndex,select,input){
	var url = ctx + "/merchant!directlyMerchantList.do";
	var groupCode = $("#groupCode").val();
	$("#merchantList").load(url,{"groupCode":groupCode,"pageIndex":pageIndex,"select":select,"input":input});
}

/**
 * 初始化 非直属商户
 */
function initNotDirectlyMerchantList(){
	notDirectlyMerchantList(0,"","");
}

/**
 * 非直属商户列表信息
 */
function notDirectlyMerchantList(pageIndex,select,input){
	var url = ctx + "/merchant!notDirectlyMerchantList.do";
	var groupCode = $("#groupCode").val();
	$("#merchantList").load(url,{"groupCode":groupCode,"pageIndex":pageIndex,"select":select,"input":input});
}

/***
 * 查询商户
 */
function searchMerchant(){
	$("#Pagination").html("")
	var merchant_Select = $("#merchant_Select").val();
	var merchant_Input = $("#merchant_Input").val();
	//判断是直属还是非直属
	var merchantType = $("#merchantType").val();
	if (merchantType == 1)
	{
		//直属
		directlyMerchantList(0,merchant_Select,merchant_Input);
	}else{
		//非直属
		notDirectlyMerchantList(0,merchant_Select,merchant_Input);
	}

}

/**
 * 添加商户列表
 */
function showAddMerchant(){
	var url = ctx + "/merchant!showAddMerchant.do";
	var groupCode = $("#groupCode").val();
	$("#shop_container").load(url,{"groupCode":groupCode});
}

/**
 * 获取城市列表
 * 
 * @param provinceCode
 */
function queryCityListMer(thisHtml, e) {
	var provinceCode = $(thisHtml).val();
	if (e == 0)// 获取省名称
	{
		var provinceName =  $(thisHtml).find("option:selected").text()
		$(thisHtml).siblings("input[name='merchant.province']").val(provinceName)
	}
	if (e ==1)// 获取市名称
	{
		var cityName =  $(thisHtml).find("option:selected").text()
		$(thisHtml).siblings("input[name='merchant.city']").val(cityName)
	}
	if(e == 2) {  // 区列表
		$("#areaCode").val(provinceCode);
		return false ;
	}
	$.ajax({
		url : ctx + '/merchant!queryCityList.do',
		type : 'POST',
		dataType : 'json',
		data : {
			"provinceCode" : provinceCode
		},
		async : false,
		success : function(cityList) {
			if (e == 0) {        //市级列表
				$("#citySelect").html("<option selected=\"selected\" value=\"\">选择市</option>");
				$("#areaSelect").html("<option selected=\"selected\" value=\"\">选择区/县</option>");
				$("#areaCode").val("")
				
				// jquery遍历城市列表
				if(cityList!=null){
					$.each(cityList, function(i, city) {
						$("#citySelect").append("<option value=" + city.code + ">" + city.city + "</option>");
					})
				}
			} else if(e == 1) {  // 区列表
				$("#areaSelect").html("<option selected=\"selected\" value=\"\">选择区/县</option>");
				$("#areaCode").val(provinceCode)
				// jquery遍历区列表
				$.each(cityList, function(i, city) {
					$("#areaSelect").append("<option value=" + city.code + ">" + city.district + "</option>");
				})
			}
			
		}
	})
}
/**
 * 银行地区选择
 * @param thisHtml
 * @param e
 * @returns {boolean}
 */
function queryCityListBank(thisHtml, e) {
    var provinceCode = $(thisHtml).val();
    if (e == 0)// 获取省名称
    {
        var provinceName =  $(thisHtml).find("option:selected").text()
        $(thisHtml).siblings("input[name='merchant.bankProvince']").val(provinceName)
        $("#selectBankInfo").html("<option value=''>选择开户行</option>")
        $("input[name='merchant.bankNumber']").val("")
    }
    if (e ==1)// 获取市名称
    {
        var cityName =  $(thisHtml).find("option:selected").text()
        $(thisHtml).siblings("input[name='merchant.bankCity']").val(cityName)
    }

    $.ajax({
        url : ctx + '/merchant!queryCityList.do',
        type : 'POST',
        dataType : 'json',
        data : {
            "provinceCode" : provinceCode
        },
        async : false,
        success : function(cityList) {
            if (e == 0) {        //市级列表
                $("#citySelect_bank").html("<option selected=\"selected\" value=\"\">选择市</option>");

                // jquery遍历城市列表
                if(cityList!=null){
                    $.each(cityList, function(i, city) {
                        $("#citySelect_bank").append("<option value=" + city.code + ">" + city.city + "</option>");
                    })
                }
            }

        }
    })
}
/**
 * 查询 开户行
 */
function queryBankInfo(thisHtml) {
	var cityCode= $(thisHtml).val();
	var bankId =  $("#bank").val();
    var cityName =  $(thisHtml).find("option:selected").text()
    $(thisHtml).siblings("input[name='merchant.bankCity']").val(cityName)
	var url = ctx + '/merchant!queryBankInfo.do';
	$.post(url,{"cityCode":cityCode,"bankId":bankId},function (data) {
		var resultCode = data.resultCode;
		if (resultCode == "SUCCESS") {
            $("#selectBankInfo").html("<option selected=\"selected\" value=\"\">选择开户行</option>");
            $.each(data.bankInfos, function(i, bankinfo) {
                $("#selectBankInfo").append("<option value=" + bankinfo.bankCode + ">" + bankinfo.bankName  + "</option>");
            })
        }
    })
}
/**
 * 选择 开户行
 */
function selectBankCode(thisHtml) {
	var bankCode = $(thisHtml).val();
    $("#bankCode").val(bankCode);
    var bankName =  $(thisHtml).find("option:selected").text()
    $(thisHtml).siblings("input[name='merchant.bankName']").val(bankName)
}
/**
 * 选择银行
 */
function selectBank(thisHtml) {
    var bank =  $(thisHtml).find("option:selected").text()
    $(thisHtml).siblings("input[name='merchant.affiliatedBank']").val(bank)
	$("#selectBankInfo").html("<option value=''>选择开户行</option>")
    $("input[name='merchant.bankNumber']").val("")
}
//上传商品图片
function uploadImg(e){
	var thisHtml = $(e);
	$.seocooUpload({"isPress":1},function(data){
		var url=data.url;
		var filename = url.substring(url.lastIndexOf("/") + 1, url.length);
		filename="/images/upload/"+filename;
		$('#picUrl').val(filename);
		//alert($(".goods_pic_edit .fl").find("img").attr("src"));
		$(this).siblings().remove();
		var html = "<div class='pic' onclick='repeatUploadImg(this)' ><img src='"+url+"'></div>"
		$(thisHtml).before(html)
		$(thisHtml).siblings("#uploadImage").val(url)
		
	})
}
//重新上传商品图片
function repeatUploadImg(e){
	var thisHtml = $(e);
	$.seocooUpload({"isPress":1},function(data){
		var url=data.url;
		var filename = url.substring(url.lastIndexOf("/") + 1, url.length);
		filename="/images/upload/"+filename;
		$('#picUrl').val(filename);
		//alert($(".goods_pic_edit .fl").find("img").attr("src"));
		$(this).siblings().remove();
		var html = "<img src='"+url+"'>"
		$(thisHtml).html(html)
		$(thisHtml).siblings("#uploadImage").val(url)
	})
}

/**("#addMerchant select[name='merchant.province']").find("option:selected").text();
 * 提交商户信息 并保存，
 */
function saveMerchant(){
	var tf=false;
	$("input[type='text']").each(function(){
		var name=$(this).attr("name");
		if(name !="merchant.parentMchntId" && name !="merchant.identification"){
			var val=$(this).val();
			if(strIsNull(val)){
				tf=true;
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
        return false;
	}
	if(tf){
		//保证不往下执行
		return false;

	}
	// 验证 当前代理商 是否 可以 添加商户
	var url = ctx + '/merchant!saveMerchantChecked.do';
	$.post(url,function (data) {
		var resultCode = data.resultCode;
        if (resultCode == "SUCCESS") {
            var options = {
                dataType : "json",
                async: false,
                success :showMerSuccess,
                error:showMerFailError,
                timeout: 30000      //限制请求的时间，当请求大于3秒后，跳出请求
            };
            $(".import_loading").show()
            var url = ctx + '/merchant!saveMerchant.do';
            $(".muti-marketing #addMerchant").attr('action',url);
            jQuery(".muti-marketing #addMerchant").ajaxSubmit(options);
        }else {
          alert(data.resultMsg);
        }

    })

}
/**
 * 保存失败
 */

function showMerFailError(){
	alert("保存失败");
    $(".import_loading").hide();
}

/**
 * 保存成功
 */
function showMerSuccess(){
    $(".import_loading").hide()
	var url = ctx + '/merchant.do';
	$('#shop_container').load(url);	
}

/**
 * 编辑 商户信息
 */
function showEditMerchant(e){
	var merchantId = $(e).siblings("#merchantId").val();
	var groupCode = $("#groupCode").val();
    $(".import_loading p").text("正在进行中...");
    $(".import_loading").show();
	var url = ctx + "/merchant!showEditeMerchant.do";
	$("#shop_container").load(url,{"groupCode":groupCode,"merchantId":merchantId},function () {
        $(".import_loading").hide()
    });
}

function sameUserName(e) {
	var userName = $(e).val();
    $(".sales-top").find("input[name='merchant.acctName']").val(userName);
}
/**
 * 保存编辑后的商户信息
 */
function saveEditeMerchant(){
	var tf=false;
	$("input[type='text']").each(function(){
		var name=$(this).attr("name");
		if(name !="merchant.parentMchntId"  && name !="merchant.identification"){
			var val=$(this).val();
			if(strIsNull(val)){
				tf=true;
				alert($(this).siblings("label").html()+"必须填写");
				$(this).focus();
				return false;
			}
		}
	});
	if(tf){
		//保证不往下执行
		return false;
	}
    var merchantLevel =  $("select[name='merchant.merchantLevel']").val();
    if(merchantLevel == 0){
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
		      success :showMerSuccess,
		      error:showMerFailError,
		      timeout: 150000      //限制请求的时间，当请求大于3秒后，跳出请求
	};
	$(".import_loading").show();
	var url = ctx + '/merchant!saveEditeMerchant.do';
	$(".muti-marketing #addMerchant").attr('action',url);
	jQuery(".muti-marketing #addMerchant").ajaxSubmit(options);
}

/**
 * 商户进件
 */

function inToBlance(e){
	merchantId= $(e).siblings("#merchantId").val();
	$("#pop_box_alert").show();
}
/**
 * 确认弹框
 */
$(document).on('click',"#pop_box_alert .blue",function(){
	var url =  ctx + '/merchant!intoBlance.do';
	$("#pop_box_alert").hide();
	$(".import_loading").find("p").text("正在进件中").end().show()
	$.post(url,{"merchantId":merchantId},function(data){
		var resultCode =data.resultCode;
		var resultMsg = data.resultMsg
		if (resultCode == "SUCCESS")
		{
			var currnetPage =  getCurrentPage ();
			if(currnetPage == "" || currnetPage == -1){
				currnetPage = 0
			}
			$(".import_loading").hide();
			directlyMerchantList(currnetPage);
			alert("进件成功！")
		}else {
			$(".import_loading").hide();
			alert(resultMsg)
		}
	})
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
 * 取消弹框
 */
$(document).on('click',"#pop_box_alert .red",function(){
	$("#pop_box_alert").hide();
})

/**
 * 商户---绑定 微信
 */
function showbindingBlance(e){
	merchantId = $(e).siblings("#merchantId").val();
	var groupCode = $("#groupCode").val();
	var url = ctx + "/merchant!showbindingBlance.do";
	$("#shop_container").load(url,{"groupCode":groupCode,"merchantId":merchantId});
	bindingWXBlance(merchantId)
}
//加载绑定微信页面
function bindingWXBlance(){
	var merchantId = $("#merchantId").val();
	var groupCode = $("#groupCode").val();
	var url = ctx + "/merchant!showWxPage.do";
	$("#payType").load(url,{"groupCode":groupCode,"merchantId":merchantId});
}
//加载绑定支付宝页面
function bindingZFBBlance(){
	var merchantId = $("#merchantId").val();
	var groupCode = $("#groupCode").val();
	var url = ctx + "/merchant!showZFBPage.do";
	$("#payType").load(url,{"groupCode":groupCode,"merchantId":merchantId});
}
/**
 * 商户类别:微信下拉复选
 */
function queryWXCode(thisHtml,e){
	var WXCode = $(thisHtml).val();
	var url = ctx + "/merchant!queryWXCode.do";
	$.post(url,{"WXCode":WXCode,"type":e},function(data){
		var industryList = data.industryList;
		if (e == 0) {        //市级列表
			$("#industry").html("<option selected=\"selected\" value=\"\">选择行业大类</option>");
			$("#category").html("<option selected=\"selected\" value=\"\">选择类目</option>");
			$("#categoryID").val("")
			
			if(industryList!=null){
				$.each(industryList, function(i, industry) {
					$("#categoryID").val("")
					$("#industry").append("<option value=" + industry + ">" + industry + "</option>");
				})
			}
		} else if(e == 1) {  // 区列表
			$("#category").html("<option selected=\"selected\" value=\"\">选择类目</option>");
			// jquery遍历区列表
			$.each(industryList, function(i, industry) {
				$("#category").append("<option value=" + industry + ">" + industry + "</option>");
			})
		}else if(e == 2) {  // 区列表
			$.each(industryList, function(i, industry) {
				$("#categoryID").val(industry)
			})
		}
	})
}
/**
 * 商户类别:支付宝下拉复选
 */
function queryZFBCode(thisHtml,e){
	var ZFBCode = $(thisHtml).val();
	var url = ctx + "/merchant!queryZFBCode.do";
	$.post(url,{"ZFBCode":ZFBCode,"type":e},function(data){
		var industryList = data.industryList;
		if (e == 0) {        //市级列表
			$("#secondCategory").html("<option selected=\"selected\" value=\"\">选择二级类目</option>");
			$("#thirdCategory").html("<option selected=\"selected\" value=\"\">选择三级类目</option>");
			$("#categoryID").val("")
			
			if(industryList!=null){
				$.each(industryList, function(i, industry) {
					$("#categoryID").val("")
					$("#secondCategory").append("<option value=" + industry + ">" + industry + "</option>");
				})
			}
		} else if(e == 1) {  // 区列表
			$("#thirdCategory").html("<option selected=\"selected\" value=\"\">选择三级类目</option>");
			$("#categoryID").val("")
			// jquery遍历区列表
			$.each(industryList, function(i, industry) {
				$("#thirdCategory").append("<option value=" + industry + ">" + industry + "</option>");
			})
		}else if(e == 2) {  // 区列表
			$.each(industryList, function(i, industry) {
				$("#categoryID").val(industry)
			})
		}
	})
}
/**
 * 保存微信绑定信息
 */
var flag_wx = false; // 支付通道
function saveBindWxMerhant(){
	var tf=false;
	$("input[type='text']").each(function(){
		var name=$(this).attr("name");
		if(name !="payChannel.parentMchntId"  && name !="payChannel.message"
			&& name !="payChannel.fixFeeRate" && name !="payChannel.specFeeRate"){
			var val=$(this).val();
			if(strIsNull(val)){
				tf=true;
				isconWx = false;
				alert($(this).siblings("label").html()+"必须填写");
				$(this).focus();
				return false;
			}
		}
		//验证费率在0.38-0.6之间
		if(name =="payChannel.fixFeeRate"){
			var thisHtml = $(this);
			var val = $(this).val();
		var fixFeeChecked  =  fixFreeRateCheck(thisHtml,val);
			if(!fixFeeChecked){
				tf=true;
				isconWx = false;
				return false ;
			}
		}
		//验证特殊费率为数字
		if(name =="payChannel.specFeeRate" && $(this).val()!=""){
			var val=$(this).val();
			if(!(/^[0-9]+(.[0-9]{1,2})?$/.test(val))){ 
				 alert("固定比率费率格式不正确");
				 $(thisHtml).val("");
				 $(thisHtml).focus();
				 tf=true;
				 isconWx = false;
			     return false; 
			 }
		}
	})
	if(tf){
		//保证不往下执行
		return false;
	}
	//费率单独判断
	var fixFee=$("input[name='payChannel.fixFeeRate']").val();
	var specFee=$("input[name='payChannel.specFeeRate']").val();
	if(strIsNull(fixFee) && strIsNull(specFee)){
		tf=true;
		isconWx = false;
		alert("固定比率费率 与 特殊费率  两种费率必须二选一  ");
		$("input[name='payChannel.fixFeeRate']").focus();
		return false;
	}
	if(!strIsNull(fixFee) && !strIsNull(specFee)){
		tf=true;
		isconWx = false;
		alert("固定比率费率 与 特殊费率  两种费率 请二选一填写一项  ");
		$("input[name='payChannel.fixFeeRate']").focus();
		return false;
	}
	if(tf){
		//保证不往下执行
		return false;
	}
    $(".import_loading").find("p").text("正在保存中...").end().show()
	var options = {
		      dataType : "json",
		      async: false,
		      success :function(){
		    	  bindingWXBlance();
		    	  flag_wx = true;
		    	  alert("保存成功")
                  $(".import_loading").hide();
		      },
		      error:showPayFailError,
		      timeout: 30000      //限制请求的时间，当请求大于3秒后，跳出请求
	};
	var url = ctx + '/merchant!saveBindWxMerhant.do';
	$(".shop-nontice #bindingWXMerchant").attr('action',url);
	jQuery(".shop-nontice #bindingWXMerchant").ajaxSubmit(options);
	$(".shop-nontice #bindingWXMerchant").attr('action','');
}
/**
 * 保存支付宝绑定信息
 */
var flag_zfb = false ;
function saveBindZFBMerhant(){

	
	var tf=false;
	$("input[type='text']").each(function(){
		var name=$(this).attr("name");
		if(name !="payChannel.parentMchntId"  && name !="payChannel.message"
			&& name !="payChannel.fixFeeRate" && name !="payChannel.specFeeRate"){
			var val=$(this).val();
			if(strIsNull(val)){
				tf=true;
				isconZFB = false ;
				alert($(this).siblings("label").html()+"必须填写");
				$(this).focus();
				return false;
			}
		}
		//验证费率在0.38-0.6之间
		if(name =="payChannel.fixFeeRate"){
			var thisHtml = $(this);
			var val = $(this).val();
		var fixFeeChecked  =  fixFreeRateCheck(thisHtml,val);
			if(!fixFeeChecked){
				tf=true;
				isconZFB = false ;
				return false ;
			}
		}
		//验证特殊费率为数字
		if(name =="payChannel.specFeeRate" && $(this).val()!=""){
			var val=$(this).val();
			if(!(/^[0-9]+(.[0-9]{1,2})?$/.test(val))){ 
				 alert("固定比率费率格式不正确");
				 $(thisHtml).val("");
				 $(thisHtml).focus();
				 tf=true;
				 isconZFB = false ;
			     return false; 
			 }
		}
	})
	if(tf){
		//保证不往下执行
		return false;
	}
	//费率单独判断
	var fixFee=$("input[name='payChannel.fixFeeRate']").val();
	var specFee=$("input[name='payChannel.specFeeRate']").val();
	var fixFeeChecked =  fixFreeRateCheck($("input[name='payChannel.fixFeeRate']"),fixFee);
	if(!fixFeeChecked){
		return false ;
	}
	if(!tf &&strIsNull(fixFee) && strIsNull(specFee)){
		tf=true;
		isconZFB = false ;
		alert("固定比率费率 与 特殊费率  两种费率必须二选一  ");
		$("input[name='payChannel.fixFeeRate']").focus();
		return false;
	}
	if(!tf &&!strIsNull(fixFee) && !strIsNull(specFee)){
		tf=true;
		isconZFB = false ;
		alert("固定比率费率 与 特殊费率  两种费率 请二选一填写一项  ");
		$("input[name='payChannel.fixFeeRate']").focus();
		return false;
	}
	if(!tf &&!strIsNull(fixFee) && isNaN(fixFee)){
		tf=true;
		isconZFB = false ;
		alert("固定比率费率必须是数字");
		$("input[name='payChannel.fixFeeRate']").focus();
		return false;
	}
	if(!tf &&!strIsNull(specFee) && isNaN(specFee)){
		tf=true;
		isconZFB = false;
		alert("特殊费率 必须是数字");
		$("input[name='payChannel.specFeeRate']").focus();
		return false;
	}
	if(tf){
		//保证不往下执行
		return false;
	}
    $(".import_loading").find("p").text("正在保存中...").end().show()
	var options = {
		      dataType : "json",
		      async: false,
		      success :function(){
		    	  bindingZFBBlance();
		    	  flag_zfb = true ;
                  $(".import_loading").hide();
		    	  alert("保存成功")
		      },
		      error:showPayFailError,
		      timeout: 30000      //限制请求的时间，当请求大于3秒后，跳出请求
	};
	var url = ctx + '/merchant!saveBindZFBMerhant.do';
	$(".shop-nontice #bindingZFBMerchant").attr('action',url);
	jQuery(".shop-nontice #bindingZFBMerchant").ajaxSubmit(options);
	$(".shop-nontice #bindingZFBMerchant").attr('action','');
}

/**
 * 支付通道信息保存失败
 */	
function showPayFailError(){
	alert("保存失败");
    $(".import_loading").hide();
}
/**
 * 执行微信银行绑定
 */
function gotoBindWxMerhant(){
	// 判断 用户是否是先保存在绑定支付通道
	if(flag_wx){
		$(".import_loading").find("p").text("正在绑定中").end().show()
		var groupCode = $("#groupCode").val();
		var merchantCode = $("#merchantCode").val();
		var url = ctx + "/merchant!gotoBindWxMerhant.do";
		$.post(url,{"groupCode":groupCode,"merchantCode":merchantCode},function(data){
			var resultCode =data.resultCode;
			var resultMsg = data.resultMsg
			flag_wx = false ;
			if (resultCode == "SUCCESS")
			{
				$(".import_loading").hide();
				bindingWXBlance();
				alert("绑定成功！")
			}else {
				$(".import_loading").hide();
				alert(resultMsg)
			}
		});
	}else {
		alert("请先保存再绑定！")
	}
	
}

/**
 * 执行支付宝银行绑定
 */
function gotoBindZFBMerhant(){
	// 判断 用户是否是先保存在绑定支付通道
	if(flag_zfb){
		$(".import_loading").find("p").text("正在绑定中").end().show()
		var groupCode = $("#groupCode").val();
		var merchantCode = $("#merchantCode").val();
		var url = ctx + "/merchant!gotoBindZFBMerhant.do";
		$.post(url,{"groupCode":groupCode,"merchantCode":merchantCode},function(data){
			var resultCode =data.resultCode;
			var resultMsg = data.resultMsg
			flag_zfb = false ;
			if (resultCode == "SUCCESS")
			{
				$(".import_loading").hide();
				bindingZFBBlance();
				alert("绑定成功！")
			}else {
				$(".import_loading").hide();
				alert(resultMsg)
			}
		});
	}else {
		alert("请先保存再绑定！")
	}
}

/**
 * 验证手机号
 */
function checkPhone(thisHtml,e){
    if(!(/^1(3|4|5|7|8)\d{9}$/.test(e))){ 
        alert("手机号码有误，请重填");  
        $(thisHtml).val("");
		$(thisHtml).focus();
        return false; 
    } 
}

/**
 * 固定比率费率 检查
 */
function fixFreeRateCheck(thisHtml,e){
	
	 if(!(/^[0-9]+(.[0-9]{1,2})?$/.test(e))){ 
		 alert("固定比率费率格式不正确");
		 $(thisHtml).val("");
		 $(thisHtml).focus();
	        return false; 
	 } else  if(parseFloat(e)<0.35 || parseFloat(e)>0.60){ 
		  alert("固定比率费率在0.35-0.6之间");
		  $(thisHtml).val("");
		  $(thisHtml).focus();
	        return false; 
	}else {
		return true ;
	}
}


//绑定账户
function binMerUser(e){
	var merchantId = $(e).siblings("#merchantId").val();
	var groupCode = $("#groupCode").val();
	if(strIsNull(merchantId)){
		alert("门店信息不存在，请刷新页面");
		return false;
	}
	var url = ctx + "/merchant!binMerUser.do";
	$("#shop_container").load(url,{"groupCode":groupCode,"merchantId":merchantId});
}

//绑定默认收款商户
function groupPayMer(e){
	var merchantId = $(e).siblings("#merchantId").val();
	var groupCode = $("#groupCode").val();
	if(strIsNull(merchantId)){
		alert("门店信息不存在，请刷新页面");
		return false;
	}
	var url = ctx + "/merchant!groupPayMer.do";
	$.post(url,{"groupCode":groupCode,"merchantId":merchantId},function(data){
		var result=data.result;
		if(result == "SUCCESS"){
			$.each($("#merchantList tr"),function(){
				if($(this).find("td:last").find("span").length>0){
					var tdHtml="<a href='javascript:void(0);' onclick='groupPayMer(this)'>默认收款</a>";
					$(this).find("td:last").append(tdHtml);
					$(this).find("td:last").find("span").remove();
				}
			})
			$(e).after("<span>收款商户</span>");
			$(e).remove();
		}
	});
}

//保存账号
function saveMerUser(id){
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
		url = url+ "/merchant!saveUser.do";
	}else{
		url = url+ "/merchant!saveUserEdit.do";
	}
	$.post(url,{"userCode":userCode,"password":password,"groupCode":groupCode,"merchantCode":merchantCode},
		function(data){
		var result=data.result;
		if(result=="SUCCESS"){
			loadContent("/merchant.do");
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
