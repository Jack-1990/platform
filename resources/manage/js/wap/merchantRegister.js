$(function(){
  $(".verification .code").click(function(){
	  createCode();
  })
})


	
var code;//定义一个全局验证码
function createCode(){
	var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');//所有候选组成验证码的字符，也可以用中文的 
	code = '';
	var codeLength=4;
	var codeContent = $(".code");
	for(var i=0;i<codeLength;i++){
		var charIndex =Math.floor(Math.random()*selectChar.length);//随机数
		code +=selectChar[charIndex];//一张验证码有五个字符组成
		codeContent.html(code);//显示验证码
	}
}

function validation(){
	var flag =false;
	var Code = $("#verImgCode").val();
	Code=Code.toUpperCase();
	if(Code !=code && Code.length >0){
		createCode()//如果输入的验证码有误就刷新验证码	
	}else{
		flag=true;	
	}
	return flag;
}




//倒计时时间
var countTime=60;

/**
 * 获取验证码
 */
function getVerifiCode(){
	var phone=$("#phone").val();
	if(strIsNull(phone)){
		alert("请输入手机号");
		$("#phone").focus();
		return false;
	}
	if(checkPhone(phone)){
		alert("请填写正确的手机号");
		$("#phone").focus();
		return false;
	}
	//检验码
	var verImgCode=$("#verImgCode").val();
	if(strIsNull(verImgCode)){
		alert("请输入图片校验码");
		return false;
	}
	if(!validation()){
		alert("校验码错误");
		return false;	
	}
	
	//验证手机号是否注册
	$.ajax({
		timeout : 2500,
		type : "POST",
		url : ctx + "/merchantRegister!checkPhone.do",
		async : true,
		dataType : "json",
		data : {
			"phone" : phone
		},
		success : function(data) {
		   var result=data.resultCode;
		    if("SUCCESS"==result){
		    	$(".message_list .pic a").removeClass("current");
		    	//倒计时
		    	downTime();
		    	//发短信
		    	sendMsg();
		    	//重新生成校验码
//		    	createCode();
		    }
		    else if("FAIL"==result){
		    	alert("此手机号已经注册");
		    }
	     }
	  })

}


/**
 * 时间倒计时
 */
function downTime(){
	if(countTime ==0){
		var msg="获取验证码";
		$(".message_list .dd-padding .pic").find("a").html(msg);
		$(".message_list .dd-padding .pic").find("a").attr("onclick","getVerifiCode();");
		$(".message_list .pic a").addClass("current");
		
		countTime=60;
	}else{
		var msg="剩余"+countTime+"秒可重发";
		$(".message_list .dd-padding .pic").find("a").html(msg);
		$(".message_list .dd-padding .pic").find("a").attr("onclick","");
		
		countTime--;
		setTimeout(function(){
			  downTime();
			},1000)
	}
}


/**
 * 发短信
 */
function sendMsg(){
	var phone=$("#phone").val();
	if(strIsNull(phone)){
		alert("请输入手机号");
		$("#phone").focus();
		return false;
	}
	if(checkPhone(phone)){
		alert("请填写正确的手机号");
		$("#phone").focus();
		return false;
	}
	
	$.ajax({
		timeout : 2500,
		type : "POST",
		url : ctx + "/merchantRegister!sendMsg.do",
		async : true,
		dataType : "json",
		data : {
			"phone" : phone
		},
		success : function(data) {
			 
	      },
	      error : function(){
	    	  
			}
	   })
}


/**
 * 注册
 */
var reg=false;
function register(){
	var phone=$("#phone").val();
	var verifyCode=$("#verifyCode").val();
	var password=$("#password").val();
	var parentUser=$("#parentUser").val();
	
	if(strIsNull(phone)){
		alert("请输入手机号");
		$("#phone").focus();
		return false;
	}
	if(checkPhone(phone)){
		alert("请填写正确的手机号");
		$("#phone").focus();
		return false;
	}
	if(strIsNull(verifyCode)){
		alert("请输入手机验证码");
		$("#verifyCode").focus();
		return false;
	}
	if(strIsNull(password)){
		alert("请输入密码");
		$("#password").focus();
		return false;
	}
	if(password.length <6 || password.length>16 ){
		alert("请设置6~16位密码");
		$("#password").focus();
		return false;
	}
	//避免连击
	if(reg){
		return false;
	}
	reg=true;
	$.ajax({
		timeout : 2500,
		type : "POST",
		url : ctx + "/merchantRegister!register.do",
		async : true,
		dataType : "json",
		data : {
			"phone" : phone,
			"verifyCode" : verifyCode,
			"password" : password,
			"parentUser":parentUser
		},
		success : function(data) {
			    var result=data.resultCode;
			    var resultMsg=data.resultMsg;
			    if("SUCCESS"==result){
			    	var url=ctx + "/merchantRegister!registerSuccess.do";
			    	window.location.href=url;
			    }
			    else if("FAIL"==result){
			    	alert(resultMsg);
			    }
			    reg=false;
	      },
	     error : function(){
	    	   reg=false;
			}
	   })
}




/**
 * 下载app
 */
function downApp(){
	    var u = navigator.userAgent;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	    
	    var iosUrl="https://itunes.apple.com/cn/app/yun-fu-quan-min-chuang-fu/id1138176125?mt=8";
	    var androidUrl="http://ahcmbc.guoguof.com/resources/apk/dianha_wallet.apk";
	   
	    if(isAndroid){
	    	window.location.href=androidUrl;
	    }else if(isiOS){
	    	alert("APP正在审核中");
	 	    return false;
	    	window.location.href=iosUrl;
	    }else{
	    	window.location.href=androidUrl;
	    }
}

//检验用户账号
function checkAccountNum(obj,e){
	var pos=getCursortPosition (obj);
	var t=e.keyCode;
	if(t !=37 && t !=39){
		var s1=obj.value.length;
		obj.value=obj.value.replace(/[^\w\-\/]/ig,'');
		obj.value=obj.value.replace(/^ +| +$/g,'');
		obj.value=obj.value.replace(/\s/g,"");
		
		var s2=obj.value.length;
		if(s1 != s2){
			setCaretPosition(obj, pos-1);
		}else{
			setCaretPosition(obj, pos);
		}
	}
}
//只能填写 1-9 和 -的整数
function checkIsNum(obj,e){
	var pos=getCursortPosition (obj);
	var t=e.keyCode;
	if(t !=37 && t !=39){
		var s1=obj.value.length;
		obj.value=obj.value.replace(/[^0-9-]+/,'');
		obj.value=obj.value.trim();
		obj.value=obj.value.replace(/\s/g,"");
		
		var s2=obj.value.length;
		if(s1 != s2){
			setCaretPosition(obj, pos-1);
		}else{
			setCaretPosition(obj, pos);
		}
	}
}


//获取光标位置
function getCursortPosition (ctrl) {
    var CaretPos = 0;   // IE Support
    if (document.selection) {
        ctrl.focus ();
        var Sel = document.selection.createRange ();
        Sel.moveStart ('character', -ctrl.value.length);
        CaretPos = Sel.text.length;
    }
    else if (ctrl.selectionStart || ctrl.selectionStart == '0')
        CaretPos = ctrl.selectionStart;
    return CaretPos;
}

//设置光标位置
function setCaretPosition(ctrl, pos){
    if(ctrl.setSelectionRange)
    {
        ctrl.focus();
        ctrl.setSelectionRange(pos,pos);
    }
    else if (ctrl.createTextRange) {
        var range = ctrl.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
}



//检验 手机号
function checkPhone(phone){
	var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;// 手机号码正则
	if (phone == "" || phone == null) {
		return true;
	}else{
		if(reg.test(phone)) {
			return false;
		}else{
			return true;
		}
	}
}



//判断非空
function strIsNull(str){
	textTf=false;
	if(str=="" || str==null || str==undefined || $.trim(str).length==0 ){
		textTf = true;
	}
    return textTf;
}



var iosUrl;//IOS下载
var andUrl;//安卓下载
function isWeiXin()
{
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}
	
function common(pfid){
		var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		$(".mask img").css("height", h).on("click", function(){
			$(".mask").hide();
		});
		
		var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
		$(".mask img").css("height", h).on("click", function(){
			$(".mask").hide();
		});
		if(isWeiXin())	{
			$(document).on("click", ".android", function(e){
				e.preventDefault();
				$(".mask").show();
			});
			$(document).on("click", ".ios", function(e){
				e.preventDefault();
				$(".mask").show();
			});
		} 
		$('.ios').show();
		$('.android').show();
}
