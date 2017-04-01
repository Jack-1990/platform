
//去掉空格  
function checkBlank(obj,e){
	var pos=getCursortPosition (obj);
	var t=e.keyCode;
	if(t !=37 && t !=39){
		var s1=obj.value.length;
		
		obj.value=obj.value.replace(/^ +| +$/g,'');
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


//只能填写 1-9 和 -的整数
function checkPhoneNum(obj,e){
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

//只能输入正数和小数
function checkPosiDeci(obj,e){
	var pos=getCursortPosition (obj);
	var t=e.keyCode;
	if(t !=37 && t !=39){
		var s1=obj.value.length;
		obj.value = obj.value.replace(/[^\d.]/g,""); //先把非数字的都替换掉，除了数字和. 
		obj.value = obj.value.replace(/^\./g,""); //必须保证第一个为数字而不是. 
		obj.value = obj.value.replace(/\.{2,}/g,"."); //保证只有出现一个.而没有多个. 
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); //保证.只出现一次，而不能出现两次以上 
		
		var s2=obj.value.length;
		if(s1 != s2){
			setCaretPosition(obj, pos-1);
		}else{
			setCaretPosition(obj, pos);
		}
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

//判断非空
function strIsNull(str){
	textTf=false;
	if(str=="" || str==null || str==undefined || $.trim(str).length==0 ){
		textTf = true;
	}
    return textTf;
}