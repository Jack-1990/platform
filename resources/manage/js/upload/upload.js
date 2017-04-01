
var settings={"callback":function(){}};
/**
 * 上传组件
 */
jQuery.extend({          
    seocooUpload:function(options,callback) { 
		//创建form表单
	 	if(typeof(options.formName)=='undefined'||options.formName==''){
	 		//删除已有元素
	 		$('#uploadSImage').remove();
   	   	 	var form='<form action="" method="post" id="uploadSelfImage" name="uploadSelfImage" enctype="multipart/form-data" encoding="multipart/form-data">';
   	   	 		form+='<input type="file"  id="imageSelfFile" name="imageSelfFile" style="display:none;"  onchange="uploadSelfImg();"/>';
   	   	 		form+='<input type="hidden"  id="isPress"  name="isPress" value="0"/>';
   	   	 		form+='</form>';
   	   	 	$("body").append(form);
	   	 }
	 	var res=jQuery.isFunction(options);
	 	if(res){
			callback=options;
		}
    	var defaults={"fileName":"imageSelfFile",
    				  "formName":"uploadSelfImage",
    				  "formAction":ctx+'/upload!uploadImage.do',
    				  "callback":callback};
   	 	 settings= $.extend(defaults,options);  
   	 	 //设置压缩属性
   	 	 if(typeof(options.isPress)!='undefined'&&options.isPress!=''){
   	 		 $('#isPress').val(settings.isPress);
   	 	 }
   	 	
   	 	//触发上传按钮
   	 	$('#'+settings.fileName).click();
     }
}); 

function showFailError(){
	alert("上传失败");
}

//上传
function uploadSelfImg(){
		var file = $('#'+settings.fileName).get(0);
		var size = 1024*1024 // 图片最大为1M
		if(file.files[0]!=undefined &&file.files[0].size > size){
			alert("上传图片最大不能超过1M");
			return false ;
		}
		var advertImgName =$('#'+settings.fileName).val();
		if (advertImgName == "" || advertImgName == null) {
			alert("请选择文件！");
			return;
		}
		var file_name = advertImgName
				.substring(advertImgName.lastIndexOf("\\") + 1);
		var filename_atr = advertImgName.substring(
				advertImgName.lastIndexOf(".") + 1, advertImgName.length);
		var reg1 = "gif,jpg,jpeg,png,bmp,GIF,JPG,JPEG,PNG,BMP";
		if ((reg1.indexOf(filename_atr) < 0)) {
			alert("请选择gif,jpg,jpeg,png,bmp格式的图片文件！");
			$('#file').val('');
			return;
		}
		var options = {
		      dataType : "json",
		      async: false,
		      data :{
		    	  'filename_atr': filename_atr
		      },
		      success :settings.callback,
		      error:showFailError,
		      timeout: 30000      //限制请求的时间，当请求大于3秒后，跳出请求
		  };
		$('#'+settings.formName).attr('action',settings.formAction);
		jQuery('#'+settings.formName).ajaxSubmit(options);
		$('#'+settings.formName).attr('action','');
}
