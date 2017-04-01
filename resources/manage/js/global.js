var version = "1.01";	//资源文件的版本号，每次发布或者升级都需改变此版本号

/**
 * 加了版本号码的js引用
 * url js的url
*/
function require(url){
	document.writeln('<script type="text/javascript" src="' + resourcePath + '/' + url + '?version=' + version + '" charset="utf-8"><\/script>');
}

/**
 * 加了版本号码的css引用
 * url css的url
*/
function requireCss(url){
	document.writeln('<link type="text/css" rel="stylesheet" href="' + resourcePath + '/' + url + '?version=' + version + '" />');
	
}
require("js/jquery/jquery-1.10.1.min.js");	//jquery.js