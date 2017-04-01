$(document).ready(function() { 
initMenu();

	//加载第一个菜单
$(".slidebar-list li:eq(0) a").click();
}); 

//所有菜单
var g_menus = [];

function initMenu(){
	
	$.ajax({
		url : ctx+"/anon/checkTicket!getPermissionMenusOnceAll.do",
		type : 'POST',
		dataType : 'json',
		async : false,
		success : function(menus) {
			g_menus=menus;
			//获取父节点
			$.each(menus,function(i,menu){
				if(menu.name.indexOf('支付通道')>=0){
					//查询云巢子菜单
					var roots = findChildMenus(menu.id);
					$('.slidebar-list ul').empty();
					var html="";
					$.each(roots, function(i, root) {
						var iconUrl = root.iconUrl?root.iconUrl : resourcePath+'\/images\/project.png';
						
						html+='<li>'+
		                    '<a href="javascript:void(0);" onclick="loadContent(\''+root.link+'\')">'+
		                        '<label><img src=\"'+iconUrl +'\" alt=""></label>'+
		                        '<span>'+root.name+'</span>'+
		                        '<i class="icon-angle-right"></i>'+
		                    '</a></li>';
					})
					 
 					$('.slidebar-list ul').html(html);
				}
			}) 
			 initMerchant();
		}
	})
}


/**
 * 查找子菜单
 * @param pid
 * @returns
 */
function findChildMenus(pid) {
	var children = [];
	if(!pid) {
		return "";
	}
	$.each(g_menus,function(i,menu){
		if(menu.parentId && menu.parentId == pid) {
			children.push(menu);
		}
	}) 
	return children;
}

/**
 * 加载页面数据
 * @param url
 */
function loadContent(url){
	if(url!=''&&typeof(url)!='undefined'){
		if(url.startWith('/')){
			url=ctx+url;
		}else{
			url=ctx+'/'+url;
		}
		$('#shop_container').load(url);	
	}

}




/**
 * 分页回调函数
 * @param page_index
 * @param jq
 */
function pageselectCallback(page_index, jq){  
//	alert(page_index + "---"+ jq);
    getDataList(page_index);  
} 

/**
 * 生成分页组件
 * @param index
 */
function getDataList(index){  
	var pageIndex = index;  
	var items_per_page = 2;
	var cityCode = $("#citySelect").val();
	var provinceCode = $("#provinceSelect").val();
	$.ajax({
		type: "POST", 
		url: ctx+'/index!listMerchantForPage.do',
		data: "pageIndex="+pageIndex+'&items_per_page='+items_per_page+'&groupCode='+$("#groupCode").val()+'&cityCode='+cityCode+'&provinceCode='+provinceCode,  
		dataType: 'json',  
		success:  function(msg){  
			 var total = msg.total;  
//			 alert(total);
			 $("#merchantListUL").html("");
			 $.each(msg.merchantList,function(i,merchant){
				 $("#merchantListUL").append("<li class=\"pad20\"> <div> <h1 class=\"pad10\">"+merchant.merchantName+"</h1><div class=\"hover_box\"><p><a href=\"account_modify.html\" class=\"modify ptl\">修改</a> <a href=\"javascript:void(0);\" class=\"delete ptl\">删除</a></p></div> </div></li>");
			 });
			//分页
			 if($("#Pagination").html()==""){
				 $("#Pagination").pagination(total, {  
	//                 'items_per_page'      : items_per_page,  
		               'num_display_entries' : 10,  
		               'num_edge_entries'    : 2,  
		               'prev_text'           : "上一页",  
		               'next_text'           : "下一页",  
		               'callback'            : pageselectCallback  
		         });  
			 }
            
//            	$("#Pagination").pagination(15);
            
		}
	})
}



/**
 * 判断首字母
 */
String.prototype.startWith=function(str){  
    if(str==null||str==""||this.length==0||str.length>this.length)  
      return false;  
    if(this.substr(0,str.length)==str)  
      return true;  
    else  
      return false;  
    return true;  
} 

function initMerchant(){
	var url = $(".slidebar-list li:eq(0)").find("a").prop("href");
	$('#shop_container').load(ctx+url.split("'")[1]);	
}



//确认修改
function sureAs(){
	var password=$('#passwordAs').val();
	var newPassword=$('#newPasswordAs').val();
	var newPasswords=$('#newPasswordsAs').val();
	if(password==null||password==""){
		alert("请填写原密码！");
		return false;
	}
	if(newPassword==null||newPassword==""){
		alert("请填写新密码！");
		return false;
	}
	if(newPasswords==null||newPasswords==""){
		alert("请填写再次确认密码！");
		return false;
	}
	if(newPassword.length<6 || newPassword.length>20){
		alert("新密码长度应该在6-20位之间！");
		return false;
	}
	if(newPassword !=newPasswords){
		alert("您输入的账号密码不一致！");
		return false;
	}
	
	$.ajax({
		timeout : 150000,
		async   : true,
		cache   : false,
		dataType: 'json',
		data    :{
			'password':password,
			'newPassword':newPassword
		},
		url     : ctx+'/index!surePassword.do',
		success : function(data){
			var resultCode=data.resultCode;
			if(resultCode=="SUCCESS"){
				alert("修改成功");
			}else if(resultCode=="FAIL"){
				var resultMsg=data.resultMsg;
				alert(resultMsg);
			}
		},
		error : function(){
		}
	});
}


function returnMerAs(){
	window.location.href=ctx+"/index.do"
}


