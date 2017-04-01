//给Date类型增加格式化原型方法
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"H+" : this.getHours(), // hour
		"h+" : this.getHours(),
		//"h+" : this.getHours()%12, //12 hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/\{(\d+)\}/g, function(m, i) {
		return args[i];
	});
}

String.prototype.replaceAll = function stringReplaceAll(AFindText, ARepText) {
	raRegExp = new RegExp(AFindText, "g");
	return this.replace(raRegExp, ARepText);
};

String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
}
String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
}

$(function(){
	$(document).on("click",".content .split.left",function(){
		$(this).removeClass("left");
		$(this).addClass("right");
		$(".content").animate({
			'margin-left' : 0
		})
	})
	$(document).on("click",".content .split.right",function(){
		$(this).removeClass("right");
		$(this).addClass("left");
		$(".content").animate({
			'margin-left' : $(".sidebar-nav").width()
		})
	})
	
	$(document).on("click",".sidebar-nav .nav-list>li",function(){
		$(".sidebar-nav .nav-list>li").removeClass("active");
		$(this).addClass("active");
		var pText = $(this).closest("ul").prev("a:first").text();
		var text = $(this).find("a:first").text();
		var bread = $(".content .breadcrumb").empty();
		bread.append("<li class='active'>{0}<span class='divider'>/</span></li>".format(pText));
		bread.append("<li class='active'>{0}</li>".format(text));
	})
	
	initAuthUrl();
	initMenu();
	hideBreadcrumb();
	
})


function initMenu(){
	
	var param="loginName="+loginName;
	$.ajax({
		url : ctx + '/rest/getPermissionMenusOnceAll',
		type : 'post',
		data : param,
		dataType : 'json',
		success : function(menus){
			var html = $(".demo-menu.hide").html();
			$(".demo-menu.hide").remove();
			var home = location.href.replace(/\/?#*$/,"");
			var folders = [];
			var roots = findRootMenus(menus);
			$.each(roots,function(i,root){
				if(root.link && root.link.replace(/\/?#*$/,"") === home){
					folders = findChildMenus(menus,root.id);
					return false;
				}
			})
			html = "<div class='menu-wrap' id='menu_wrap_{0}'>" + html + "</div>";
			$.each(folders,function(i,menu){
				var $html = $(html.format(menu.id,menu.name));
				var ul = $html.find("ul.nav-list").empty();
				var children = findChildMenus(menus, menu.id);
				$.each(children,function(j,child){
					var link = child.link;
					if(!link.startWith("http")) {
						if(!link.startWith("/")){
							link = "/" + link;
						}
						link = home + link;
					}
					if(link.indexOf("?") > 0){
						ul.append("<li id='submenu_{0}'><a href='{1}&parentMenu={3}&childMenu={2}' target='main_frame'>{2} <i class='icon-chevron-right'></i></a></li>".format(child.id,link,child.name,menu.name));
					}else{
						ul.append("<li id='submenu_{0}'><a href='{1}?parentMenu={3}&childMenu={2}' target='main_frame'>{2} <i class='icon-chevron-right'></i></a></li>".format(child.id,link,child.name,menu.name));
					}
				})
				$html.find("li:first").css("margin-top","1em");
				$html.find("li:last").css("margin-bottom","1em");
				$(".sidebar-nav").append($html);
			})
			$(".sidebar-nav .nav-header:first").trigger("click");
		}
	})
}

function findRootMenus(menus){
	var roots = [];
	$.each(menus,function(i,menu){
		if(!menu.parent){
			roots.push(menu);
		}
	})
	return roots;
}

function findChildMenus(menus,pid) {
	var children = [];
	if(!pid) {
		return findRootMenus(menus);
	}
	$.each(menus,function(i,menu){
		if(menu.parent && menu.parent.id === pid) {
			children.push(menu);
		}
	}) 
	return children;
}

function initAuthUrl(){
	$.ajax({
		url : ctx + '/anon/getAuthUrl',
		type : 'POST',
		dataType : 'json',
		async:false,
		success : function(data) {
			if(data) {
				$("#auth_link").attr("href",data);
				$("#pwd_link").attr("href",data + '/user/changePwd');
			}
		}
	
	});
}

function hideBreadcrumb(){
	$(".content .breadcrumb").hide();
	$(".content .iframe-wrap").css("margin-top","0").css("padding-top","10px");
}

