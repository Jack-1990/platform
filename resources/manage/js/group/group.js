//查询列表
function queryGroupList(index){
	if(index == 0){
		$("#Pagination").html("");//分页清空
	}
	var name=$("#querySel").val();
	var val=$("#queInput").val();
	
	var url  =  ctx + "/group!queryList.do";
	$('#groupTbody').load(url,{
		"pageIndex":index,
		 "name":name,
		 "nameval":val
	});
}

function groupListCallback(page_index, jq){
	queryGroupList(page_index);
}


//新增
function addGroup(){
	loadContent("/group!addGroup.do");
}

//进入代理商商户编辑页面
function gotoGroupMer(groupCode){
	loadContent("/groupMer.do?groupCode="+groupCode);
}


//编辑
function editGroup(id){
	if(strIsNull(id)){
		return false;
	}
	loadContent("/group!editGroup.do?groupId="+id);
}

function querySeld(val){
	var t=$("#querySel option[value='"+val+"']").html();
	$("#queInput").attr("placeholder",t);
}


//绑定账户
function editAccount(groupCode){
	var url=ctx + "/group!queryAccount.do?groupCode="+groupCode;
	$.post(url,{"groupCode":groupCode},
		function(data){
		var result=data.result;
		var groupCode=data.groupCode;
		$("#groupCodeVal").val(groupCode);
		if(result=="SUCCESS"){
			var loginName=data.loginName;
			$("#userCode").attr("readOnly","readOnly");
			$("#userCode").val(loginName);
			$("#submit a").attr("onclick","saveUser(2)");
		}
	})
	$(".container-order").hide();
	$(".container-marketing").show();
}

//保存账号
function saveUser(type){
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
	
	var url=ctx;
	if(type ==1){
		url = url+ "/group!saveUser.do";
	}else if(type ==2){
		url = url+ "/group!saveUserEdit.do";
	}
	$.post(url,{"userCode":userCode,"password":password,"groupCode":groupCode},
		function(data){
		var result=data.result;
		if(result=="SUCCESS"){
			loadContent("/group.do");
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
 * 获取城市列表
 * 
 * @param provinceCode
 */
function queryCityList(provinceCode, e) {
	if(e === 0){
		$("#lantCode").val("");
	}else if(e === 1){
		$("#lantCode").val(provinceCode);
	}
	$.ajax({
		url : ctx + '/group!queryCityList.do',
		type : 'POST',
		dataType : 'json',
		data : {
			"provinceCode" : provinceCode
		},
		async : false,
		success : function(cityList) {
			if (e === 0) {        //市级列表
				$("#citySelect").html("<option selected=\"selected\" value=\"\">选择市</option>");
				$("#areaSelect").html("<option selected=\"selected\" value=\"\">选择区/县</option>");
				// jquery遍历城市列表
				$.each(cityList, function(i, city) {
					$("#citySelect").append("<option value=" + city.code + ">" + city.city + "</option>");
				})
			} else if(e === 1) {  // 区列表
				$("#areaSelect").html("<option selected=\"selected\" value=\"\">选择区/县</option>");
				// jquery遍历区列表
				$.each(cityList, function(i, city) {
					$("#areaSelect").append("<option value=" + city.code + ">" + city.district + "</option>");
				})
			}
		}
	})
}

function areaChange(areaCode){
	$("#lantCode").val(areaCode);
}

//上传图片
function uploadGroupPic(type){
	 $.seocooUpload({"isPress":1},function(data){
			var url=data.url;
			var filename = url.substring(url.lastIndexOf("/") + 1, url.length);
			filename = "/images/upload/" + filename;
			$('#groupPicVal_'+type).val(filename);
			
			$("#groupPic_"+type).find("img").attr("src",url);
			$("#groupPic_"+type).show();
		})
}

/**
 * 保存
 * @returns {Boolean}
 */
function saveGroup(type){
	var groupCode=$("#groupCode").val();
	var shortName=$("#shortName").val();
	var fullName=$("#fullName").val();
	var groupLevel=$("#groupLevel").val();
	var lantCode=$("#lantCode").val();
	var address=$("#address").val();
	var certificateFlag=$("#certificateFlag").val();
	var businessCardNumber=$("#businessCardNumber").val();
	var validTime=$("#validTime").val();
	var linkman=$("#linkman").val();
	var linkmanId=$("#linkmanId").val();
	var rpName=$("#rpName").val();
	var rptelephone=$("#rptelephone").val();
	var customerTelephone=$("#customerTelephone").val();
	var customerId=$("#customerId").val();
	var accountStyle=$("#accountStyle").val();
	var extensionNumber=$("#extensionNumber").val();
	var remark=$("#remark").val();
	
	var idBeforePic=$("#groupPicVal_1").val();
	var idAfterPic=$("#groupPicVal_2").val();
	var checkPic1=$("#groupPicVal_3").val();
	var checkPic2=$("#groupPicVal_4").val();
	var businessCardPic=$("#groupPicVal_5").val();
		
	//检验空值
	if(strIsNull(fullName)){
		alert("代理全称必须填写");
		$("#fullName").focus();
		return false;
	}
	if(strIsNull(lantCode)){
		alert("区域代码必须填写，如您不清楚，可以选择对应省市");
		$("#lantCode").focus();
		return false;
	}
	if(strIsNull(address)){
		alert("地址必须填写");
		$("#address").focus();
		return false;
	}
	if(strIsNull(linkmanId)){
		alert("法人/联系人证件号必须填写");
		$("#linkmanId").focus();
		return false;
	}
	var reglink=/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
	if (!reglink.test(linkmanId)) {
		alert("法人/联系人证件号格式不对");
		$("#linkmanId").focus();
		return false;
	}
	if(strIsNull(rpName)){
		alert("负责人必须填写");
		$("#rpName").focus();
		return false;
	}
	if(strIsNull(rptelephone)){
		alert("负责人手机号必须填写");
		$("#rptelephone").focus();
		return false;
	}
	if(strIsNull(customerTelephone)){
		alert("客服电话必须填写");
		$("#customerTelephone").focus();
		return false;
	}
	if(customerTelephone.length <5 || customerTelephone.length >20){
		alert("客服电话应该在5-20位");
		$("#customerTelephone").focus();
		return false;
	}
	if(strIsNull(extensionNumber)){
		alert("扩展人员编号必须填写");
		$("#extensionNumber").focus();
		return false;
	}
	
	//检验值的正确性
	if(lantCode.length !=6 || isNaN(lantCode)){
		alert("区域代码必须是6位数字");
		$("#lantCode").focus();
		return false;
	}
	
	var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;// 手机号码正则
	if (!reg.test(rptelephone)) {
		alert("负责人手机号码格式不对");
		$("#rptelephone").focus();
		return false;
	}
	
	//赋默认值
	if(strIsNull(businessCardNumber)){
		businessCardNumber="-";
	}
	if(strIsNull(linkman)){
		linkman="-";
	}
	
	var group={
		"groupCode":groupCode,
		"shortName":shortName,	
		"fullName":fullName,	
		"groupLevel":groupLevel,	
		"lantCode":lantCode,	
		"address":address,	
		"certificateFlag":certificateFlag,	
		"businessCardNumber":businessCardNumber,	
		"validTime":validTime,	
		"linkman":linkman,	
		"linkmanId":linkmanId,	
		"rpName":rpName,	
		"rptelephone":rptelephone,	
		"customerTelephone":customerTelephone,	
		"customerId":customerId,	
		"accountStyle":accountStyle,
		"extensionNumber":extensionNumber,	
		"remark":remark,
		"idBeforePic":idBeforePic,	
		"idAfterPic":idAfterPic,	
		"checkPic1":checkPic1,
		"checkPic2":checkPic2,	
		"businessCardPic":businessCardPic
	}
	var groupObj=JSON.stringify(group);
	//console.log("groupObj="+groupObj);
	
	var url=ctx + "";
	if(type ==0){
		url=url + "/group!save.do";
	}else if(type ==1){
		var groupId=$("#groupId").val();
		url=url + "/group!saveEdit.do?groupId="+groupId;
	}
	$.post(url,{"groupObj":groupObj},
		function(data){
		var result=data.result;
		if(result=="SUCCESS"){
			loadContent("/group.do");
		}
	})
}



