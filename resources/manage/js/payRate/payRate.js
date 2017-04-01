
// hover出现删除产品属性按钮
$(document).on('mouseenter','.sku-atom',function(){
	$(this).children(".atom-close").css("display", "block");
})
$(document).on('mouseleave','.sku-atom',function(){
	$(this).children(".atom-close").css("display", "none");
})
        
// 添加规格项目
$(document).on("click",".edit_goods .add-sku-group",function() {
	addGoodsSku();
});
/*
 * //添加规格属性值 $(document).on("click",".edit_goods .sku-group-cont .add-sku>a",
 * function() { var showSku = $("<div class='add_goods_sku'><div
 * class='inner'><input type='text' class='ml10'/><a
 * href='javascript:void(0);' class='btn_ok ml10'>确定</a><a
 * href='javascript:void(0);' class='btn_cancel ml10'>取消</a></div><div
 * class='arrow'></div></div>"); var
 * length=$(this).parent(".add-sku").find(".add_goods_sku").length; if(length <=
 * 0) { $(this).parent(".add-sku").append(showSku); }
 * $(this).parent(".add-sku").find(".add_goods_sku").css("display", "block");
 * 
 * });
 */

// 关闭规格修改框
$(document).on("click",".edit_goods .add_goods_sku>div a.btn_cancel",function() {
        $(this).parent("div").parent(".add_goods_sku").hide();
    })
    // hover出现删除产品属性按钮

// //更改规格值
// $(document).on("click",".edit_goods .control-group .sku_name", function() {
// var showSku = $("<div class='add_goods_sku'><div><input type='text'
// class='ml10'/><a href='javascript:void(0);' class='btn_ok ml10'>确定</a><a
// href='javascript:void(0);' class='btn_cancel ml10'>取消</a></div><div
// class='arrow'></div></div>");
// var length=$(this).parent(".sku-sub-group h3").find(".add_goods_sku").length;
// if(length <= 0)
// {
// $(this).parent(".sku-sub-group h3").append(showSku);
// }
// $(this).parent(".sku-sub-group h3").find(".add_goods_sku").css("display",
// "block");
// });


// //更改规格属性值
// $(document).on("click",".edit_goods .sku-atom span", function() {
// var showSku = $("<div class='add_goods_sku'><div class='inner'><input
// type='text' class='ml10'/><a href='javascript:void(0);' class='btn_ok
// ml10'>确定</a><a href='javascript:void(0);' class='btn_cancel
// ml10'>取消</a></div><div class='arrow'></div></div>");
// var length=$(this).parent(".sku-atom").find(".add_goods_sku").length;
// if(length <= 0)
// {
// $(this).parent(".sku-atom").append(showSku);
// }
// $(this).parent(".sku-atom").find(".add_goods_sku").css("display", "block");
// });



// 添加费率规格 -------------------------------------
// 规格-----------------------------------------
function addGoodsSku()
{
	 // 获取费率规格 信息
	 var url = ctx + '/payRate!getDimDic.do';
	 var listView = "";
	 $.post(url,function(data){
		 var dimDicList = data.dimDicList;
		 $.each( dimDicList, function(i, n){
			 listView += "<li class='select2-results-dept-0 select2-result select2-result-selectable' data-code='"+n.code+"' data-name='"+n.name+"' >" +
								"<div class='select2-result-label'>" +
									"<span class='select2-match'></span>" + n.name + "</div>" + "</li>";
			});
			addSkuHtml(listView);
	 })
   }

// 添加新增sku规格页面
function addSkuHtml(listView)
{
    var showSku = " <div class='sku-sub-group'  style='display: block;'> " +
        " <h3 class='sku-group-title'> " +
        "<div class='select2-container js-sku-name select2-dropdown-open select2-container-active' id='s2id_autogen7' style='width: 100px;'>" +
        "<input type='hidden' id='dimCode' name = 'rateDim.dimCode'>"+
        "<input type='hidden' id='dimName' name = 'rateDim.dimName'>"+
			"<a href='javascript:void(0)' onclick='editSkuname(this);' class='sku_name select2-choice'>" +
				"<span class='select2-chosen'></span> <abbr class='select2-search-choice-close'></abbr> <span class='select2-arrow'><b></b></span>" +
			"</a>" +
// "<input type='hidden' name='dimId' value='"+dimId+"'>"+
			"<input class='select2-focusser select2-offscreen' type='text' id='s2id_autogen8'>" +
		"</div>" +
		"<div id='select2-drop-mask' class='select2-drop-mask' style='display: block;'></div>" +
		"<div class='select2-drop select2-display-none select2-with-searchbox select2-drop-active' style='top: 36px; left: 10px; width: 100px; display: block;' id='select2-drop'>" +
			"<ul class='select2-results'>" +
			"<ul class='select2-results'>" + listView + "</ul>" +
			"</ul>" +
		"</div>" +
        "<a class='remove-sku-group'>×</a> " +
        "</h3> " +
        "<div class='sku-group-cont'> " +
          "<div>" +
             "<div class='add-sku'> " +
 // "<input type='hidden' value='"+dimCode+"'>"+
              "<a href='javascript:void(0);'  onclick='showAddSkuAtom(this)' class='blue font16 ml20 pl20'>添加</a> " +
             "</div>" +
          "</div>" +
       "</div> " +
    "</div>";				 

  $('.sku-sub-group.clear').before(showSku);
 }
// 点击 规格 下拉
$(document).on("click",".edit_goods .sku-group-title .select2-results li",function(){
	var code = $(this).data("code");
	var name = $(this).data("name");
	var hasCode = false ;
	$(".sku-sub-group #s2id_autogen7").each(function(i,n){
		var code1 = $(n).attr("data-code");
		if(code1 == code){
			hasCode = true ;
		}
	});
	// 排除自身
	var attrCode =  $(this).parents(".sku-group-title").find("#s2id_autogen7").attr("data-code");
	if (attrCode == code){
		hasCode = false ;
	}
	if(hasCode){
		alert("规格名不能相同！");
		return false ;
	} else {
		// 删除下拉信息
		$(this).parents(".sku-sub-group").find(".sku-atom").remove();
		$(this).parents(".sku-sub-group").find(".add-sku>a").siblings().remove();
		establishSkuTable();
	}
	$(this).parents("#select2-drop").siblings("#s2id_autogen7").attr({"data-code":code,"data-name":name}).find("span.select2-chosen").text(name);
	$(this).parents("#select2-drop").siblings("#s2id_autogen7").find("#dimCode").val(code);
	$(this).parents("#select2-drop").siblings("#s2id_autogen7").find("#dimName").val(name);
	$(this).parents("#select2-drop").siblings("#s2id_autogen7").removeClass("select2-dropdown-open").removeClass("select2-container-active");
	$(this).parents("#select2-drop").siblings("#select2-drop-mask").hide();
	$(this).parents("#select2-drop").hide();
})

// 规格 下拉 失去焦点
$(document).on("click",".edit_goods #select2-drop-mask",function(){
	
	var code =  $(this).siblings("#s2id_autogen7").data("code");
	
	if(code == undefined || code == "" || code == null){
		$(this).parents(".sku-sub-group").remove();
	}
	$(this).siblings("#s2id_autogen7").removeClass("select2-dropdown-open").removeClass("select2-container-active");
	$(this).hide();
	$(this).siblings("#select2-drop").hide();
})



// 更改规格值
function editSkuname(e)
{
	// 如果下拉为空则 获取
	var hasDrop = $(e).parents("#s2id_autogen7").siblings("#select2-drop").find("li");
	if( hasDrop == undefined ||  hasDrop.length <= 0){
	// 获取费率规格 信息
	 var url = ctx + '/payRate!getDimDic.do';
	 var listView = "";
	 $.post(url,function(data){
		 var dimDicList = data.dimDicList;
		 $.each(dimDicList, function(i, n){
			 listView += "<li class='select2-results-dept-0 select2-result select2-result-selectable' data-code='"+n.code+"' data-name='"+n.name+"' >" +
								"<div class='select2-result-label'>" +
									"<span class='select2-match'></span>" + n.name + "</div>" + "</li>";
			});
		 $(e).parents("#s2id_autogen7").siblings("#select2-drop").find("ul").append(listView);
			$(e).parents("#s2id_autogen7").addClass("select2-dropdown-open").addClass("select2-container-active");
			$(e).parents("#s2id_autogen7").siblings("#select2-drop").show();
			$(e).parents("#s2id_autogen7").siblings("#select2-drop-mask").show();
	 })
	}else{
		$(e).parents("#s2id_autogen7").addClass("select2-dropdown-open").addClass("select2-container-active");
		$(e).parents("#s2id_autogen7").siblings("#select2-drop").show();
		$(e).parents("#s2id_autogen7").siblings("#select2-drop-mask").show();
	}
}
// 点击 删除 规格
$(document).on("click",".edit_goods .remove-sku-group",function(){
	$(this).parents(".sku-sub-group").remove();
	establishSkuTable();
})
// ----------------------------------------------------------------------------------


// ----------------------------------------------规格中的属性------------------------------------



// 填出添加规格属性的编辑窗口
function showAddSkuAtom(e)
{ 
	
	
// var length=$(".add_goods_sku").length;
// if(length>0)
// {
// removeAddGoodsSkuDiv();
// }
//	
// var showSku = $("<div class='add_goods_sku'><div class='inner'><input
// type='text' maxlength='10' class='ml10'/><a href='javascript:void(0);'
// onclick='addSkuAtom(this)' class='btn_ok ml10'>确定</a><a
// href='javascript:void(0);' class='btn_cancel ml10'
// onclick='cancel(this)'>取消</a></div><div class='arrow'></div></div>");
	//
	var sku_code = $(e).parents(".sku-sub-group").find("#s2id_autogen7").attr("data-code");
	var hasDropList = $(e).siblings("#select3-drop-mask");
	// 判断是否已经存在 下拉框
	if(hasDropList == undefined || hasDropList == null || hasDropList.length == 0){
	// 获取规格属性
	var url = ctx + '/payRate!getDimDicInfo.do';
	 var listView = "";
	 $.post(url,{"sku_code":sku_code},function(data){
		 var dimDicInfoList = data.dimDicInfoList;
		 $.each( dimDicInfoList, function(i, n){
			 listView += "<li class='select2-results-dept-0 select2-result select2-result-selectable' data-code='"+n.attrCode+"' data-name='"+n.attrName+"' >" +
								"<div class='select2-result-label'>" +
									"<span class='select2-match'></span>" + n.attrName + "</div>" + "</li>";
			});
		 SkuAttrHtml(e,listView);
	 })
	}else {
		 $(e).siblings().show();// 存在就显示
	}

}
// 规格属性弹框及下拉框 页面
function SkuAttrHtml(e,listView){
	// 弹框
	var skuAttrHtml = "<div class='ui-popover top-center add_goods_sku' style='top: 20px; left: -150px;'>                                                                                "+
	"		<div class='ui-popover-inner' style='width: 390px;'>                                                                                             "+
	"			<div class='select2-container select2-container-multi js-select2' id='s2id_autogen41' style='width: 242px;'>                                   "+
	"				<ul class='select2-choices'>                                                                                                                 "+
// " <li class='select2-search-choice'> "+
// " <div>23323</div> "+
// " <a href='#' onclick='return false;' class='select2-search-choice-close'
// tabindex='-1'></a> "+
// " </li> "+
	"					<li class='select2-search-field'>                                                                                                          "+
	"						<input type='text' autocomplete='off' autocorrect='off' autocapitalize='off' spellcheck='false' class='select2-input' id='s2id_autogen46'"+
	"							tabindex='-1' maxlength='20' style='width: 180px;'>                                                                                    "+
	"					</li>                                                                                                                                      "+
	"				</ul>                                                                                                                                        "+
	"			</div>                                                                                                                                         "+
	"			<input type='hidden' class='js-select2 select2-offscreen' style='width: 242px;' tabindex='-1'>                                                 "+
	"			<button class='ui-btn ui-btn-primary js-save' style='vertical-align: top'onclick='addSkuAtom(this)' >确定</button>                                                        "+
	"			<button class='ui-btn js-cancel' style='vertical-align: top' onclick='cancelSkuAttr(this)'>取消</button>                                                                     "+
	"		</div>                                                                                                                                           "+
	"	<div class='arrow'></div>                                                                                                                          "+
	"</div> <div id='select3-drop-mask' class='select2-drop-mask' style='display: block;'></div>";
	// 下拉框和底图
	skuAttrHtml += "<div class=\'selecttive\' style=\'top: 69px; left: -130px; width: 242px; display: block;\'"+
	"\tid=\'select3-drop\'>                                                                                                                                 "+
	"\t<ul class=\'select2-results\'> "+ listView +
// " <li class='select2-results-dept-0 select2-result
// select2-result-selectable'> "+
// " <div class='select2-result-label'> "+
// " <span class='select2-match'></span>11 "+
// " </div> "+
// " </li> "+
// " <li class='select2-results-dept-0 select2-result
// select2-result-selectable'> "+
// " <div class='select2-result-label'> "+
// " <span class='select2-match'></span>223 "+
// " </div> "+
// " </li> "+
	"\t</ul>                                                                                                                                              "+
	"</div>2-drop select2-drop-multi select2-display-none select2-drop-ac";
	$(e).parent(".add-sku").append(skuAttrHtml);
	$(e).parent(".add-sku").find(".add_goods_sku").css("display", "block");
}


// 点击下拉框中的数据
$(document).on("click",".edit_goods .sku-group-cont #select3-drop li",function(){
	var attrCode = $(this).data("code");
	var attrName = $(this).data("name");
	var hasAttrCode = false ;
	// 判断输入框是否 已经被选择
	$(this).parents(".add-sku").find("#s2id_autogen41").find("li").each(function(i,n){
		var inputAttrCode = $(n).data("code");
		if(inputAttrCode == attrCode){
			hasAttrCode = true ;
		}
	})
	// 判断已经选择的 规格属性 是否 存在
	$(this).parents(".add-sku").siblings(".sku-atom").each(function(i,n){
		var inputAttrCode = $(n).find("span").data("atom-id");
		if(inputAttrCode == attrCode){
			hasAttrCode = true ;
		}
	})
	if(hasAttrCode){
		alert("规格属性名不能相同！")
		return false ;
	}
	var attrHtml = "	<li class='select2-search-choice' data-code='"+attrCode+"' data-name='"+attrName+"'>                                                                                                         "+
		"						<div>"+attrName+"</div>                                                                                                                         "+
		"						<a href='#' onclick='cancelSkuAttrDrop(this)' class='select2-search-choice-close' tabindex='-1'></a>                                               "+
		"					</li>";				
	$(this).parents(".add-sku").find("#s2id_autogen41 .select2-search-field").before(attrHtml);
	// 跳转下拉框高度
	var height =  $(this).parents(".add-sku").find("#s2id_autogen41").height();
	$(this).parents("#select3-drop").css("top",height)
})

// 点击删除 选择的 下拉内容
function cancelSkuAttrDrop(e){
	$(e).parents(".select2-search-choice").remove();
}

/**
 * 取消规格属性
 */
function cancelSkuAttr(e){
	$(e).siblings("#s2id_autogen41").find(".select2-search-choice").remove();
	$(e).parents(".add_goods_sku").hide().siblings().hide()
	$(e).parents(".add_goods_sku").siblings(".blue").show();
}
$(document).on("click",".edit_goods #select3-drop-mask",function(){
	$(this).siblings(".add_goods_sku").find("#s2id_autogen41 .select2-search-choice").remove();
	$(this).hide().siblings().hide();
	$(this).siblings(".blue").show();
})




// //更改属性值
// function editAttrName(e)
// {
// // var length=$(".add_goods_sku").length;
// // if(length>0)
// // {
// // removeAddGoodsSkuDiv();
// // }
// //
// // var oldName=$(e).html();
// // var showSku = $("<div class='add_goods_sku'><div class='inner'><input
// type='text' maxlength='10' class='ml10' value='"+oldName+"'/><a
// href='javascript:void(0);' onclick='editSkuAtom(this)' class='btn_ok
// ml10'>确定</a><a href='javascript:void(0);' class='btn_cancel ml10'
// onclick='cancel(this)'>取消</a></div><div class='arrow'></div></div>");
// // $(e).parent(".sku-atom").append(showSku);
// // $(e).parent(".sku-atom").find(".add_goods_sku").css("display", "block");
// }


// 添加规格属性
function addSkuAtom(e)
{
	var attrName=$(e).siblings("input").val();
	var skuSeq=1;

	  // 遍历原来的sku的价格和库存值并保存起来
	  var skuJson=[];	
	  $('#skuTbody').children('tr').each(function(j){
		  
			var skuCode=$(this).find('.sku-code').val();
			var price=$(this).find('.stock-price').val();
			var stock=$(this).find('.stock-num').val();
			// alert("skuCode=="+skuCode+" price=="+price+" stock=="+stock);
			if(skuCode != undefined && skuCode  != null && skuCode != ""){
				var str={"skuCode":skuCode,"price":price,"stock":stock}; 
				skuJson.push(str);
			}
	  })
	  // 添加 规格属性
	  var showAttrHtmls = "";
	  $(e).siblings("#s2id_autogen41").find(".select2-search-choice").each(function(i,n){
		  var attrCode =  $(n).data("code");
		  var attrName =  $(n).data("name");
		  showAttrHtmls  += "<div class='sku-atom'><input type='hidden' id='dimAttrCode' name = 'rateDimAttr.dimAttrCode' value='"+attrCode+"' ><input type='hidden' id='dimAttrName' name = 'rateDimAttr.dimAttrName' value='"+attrName+"' ><span data-atom-id='"+attrCode+"' >"+attrName+"</span> <div class='atom-close' onclick='deleteSkuAttr(this)' >×</div></div>"
	  })
	 $(e).parents(".add-sku").before(showAttrHtmls);	
	  
	// 构建新的sku表格
	establishSkuTable();
	
	// 原来的sku属性值对应的价格和库存不变
	for(var i in skuJson){
		// 赋值
		 $('#price_'+skuJson[i].skuCode).val(skuJson[i].price);
		 $('#stock_'+skuJson[i].skuCode).val(skuJson[i].stock);
	} 
	
	// 关闭 弹框
	$(e).siblings("#s2id_autogen41").find(".select2-search-choice").remove();
	$(e).parents(".add_goods_sku").hide().siblings().hide()
	$(e).parents(".add_goods_sku").siblings(".blue").show();
}

// 删除属性
function  deleteSkuAttr(e){
	$(e).parents(".sku-atom").remove();
	 // 遍历原来的sku的价格和库存值并保存起来
	  var skuJson=[];	
	  $('#skuTbody').children('tr').each(function(j){
		  
			var skuCode=$(this).find('.sku-code').val();
			var price=$(this).find('.stock-price').val();
			var stock=$(this).find('.stock-num').val();
			// alert("skuCode=="+skuCode+" price=="+price+" stock=="+stock);
			if(skuCode != undefined && skuCode  != null && skuCode != ""){
				var str={"skuCode":skuCode,"price":price,"stock":stock}; 
				skuJson.push(str);
			}
	  })
	
	establishSkuTable();
	  
	// 原来的sku属性值对应的价格和库存不变
		for(var i in skuJson){
			// 赋值
			 $('#price_'+skuJson[i].skuCode).val(skuJson[i].price);
			 $('#stock_'+skuJson[i].skuCode).val(skuJson[i].stock);
		} 
}

/**
 * 构建sku编辑框
 */
	function  establishSkuTable()
	 {
		var skuOptions= new Array();
		var skuLength=new Array();
		var skuTitle=new Array();
		
		// 构建sku属性
		$('.sku-sub-group').not(".clear").each(function(i)
		   {
			 var title=$(this).find(".sku_name").html();
			 var length=$(this).find('.sku-group-cont .sku-atom').length;
			 // alert("title==="+title+" length==="+length);
			 
			 if(length>0)
			 {
				 skuOptions[i]=new Array();
				 skuLength.push(length);
				 skuTitle.push(title); 
				 $("#goodsStock").css("display", "flex");
				 
				 var dimCode=$(this).find("#s2id_autogen7").data("code"); 
				 // 循环skuoptions
				 $(this).find('.sku-group-cont .sku-atom').each(function(j){
						var optionName=$(this).children('span').html();
						var optionValue=$(this).children('span').attr('data-atom-id');
						
						if(optionName!="")
						skuOptions[i][j]="{'dimCode':'"+dimCode+"', 'attrCode':'"+optionValue+"','attrName':'"+optionName+"'}";
					})
			 }
		    })
		
		// 计算rowspan
		var rowspanArray=new Array();
		for(var z in skuOptions){
			var rowspan=1;
			for(var k in skuLength){
				if(z<k){
					rowspan=rowspan*(skuLength[k]);
				}
			}
			rowspanArray.push(rowspan);
		}
		// 构建table
		var skuHtml='';
		
		// alert("skuOptions===="+skuOptions)
		// 求笛卡尔积
		var result = descartes(skuOptions); 
		
		// alert(result)
		// 形成表标题
		var skutitle='';
		for(var i in skuTitle){
			skutitle+='<th class="text-center">'+skuTitle[i]+'</th>';
		}
		skutitle+='<th class="th-price">代理费率</th>';
		skutitle+='<th class="th-stock">设定费率</th>';
		$('#skuTitle').empty().append(skutitle);
		
		// 形成表数据
		for(var i in result){
			// alert(result[i]);
			skuHtml+='<tr>';
			for(var j in result[i]){
				    // alert(i%rowspanArray[j]);
					if(i%rowspanArray[j]==0){
						
						    // alert(result[i][j]);
						   var skuObj=eval("("+result[i][j]+")"); 
						   skuHtml+='<td rowspan="'+rowspanArray[j]+'">'+skuObj.attrName+'</td>';
					}
			     }
			var skuCodeStr = "";
			   for(var j in result[i]){
				       var sku=eval("("+result[i][j]+")"); 
				       skuCodeStr += sku.dimCode+ sku.attrCode;
			    }
			 
			  // alert("生成前的字符串===="+ skuCodeStr);
			  var skuStr=$.md5(skuCodeStr).toLowerCase();
			  // /alert("生成后的skucode==="+skuStr);
			 
			  
			 skuHtml+='<td><input type="hidden" class="sku-code" name = "rateSku.skuCode"   value="'+skuStr +'"><input type="text" class="stock-price" id="price_'+skuStr+'" name = "rateSku.intoRate"  maxlength="10" onkeyup="addPrice(this);"><span class="error-message" style="display:none;">请输入一个正数</span></td><td><input type="text" class="stock-num" name = "rateSku.setRate"  id="stock_'+skuStr+'" maxlength="9" onkeyup="addPrice(this);"><span class="error-message" style="display:none;">请输入一个正数</span></td>';
			 skuHtml+='</tr>';
		}
		
		 // console.log(skuHtml);
		$('#skuTbody').empty().append(skuHtml);
	}

	/**
	 * 求笛卡尔积
	 * 
	 * @param list
	 * @returns
	 */
	function descartes(list) {
	    // parent上一级索引;count指针计数
	    var point = {};
	    var result = [];
	    var pIndex = null;
	    var tempCount = 0;
	    var temp = [];
	    // 根据参数列生成指针对象
	    for (var index in list) {
	        if (typeof list[index] == 'object') {
	            point[index] = {
	                'parent': pIndex,
	                'count': 0
	            }
	            pIndex = index;
	        }
	    }
	    // 单维度数据结构直接返回
	    if (pIndex == null) {
	        return list;
	    }
	    // 动态生成笛卡尔积
	    while (true) {
	        for (var index in list) {
	            tempCount = point[index]['count'];
	            temp.push(list[index][tempCount]);
	        }
	        // 压入结果数组
	        result.push(temp);
	        temp = [];
	        // 检查指针最大值问题
	        while (true) {
	            if (point[index]['count'] + 1 >= list[index].length) {
	                point[index]['count'] = 0;
	                pIndex = point[index]['parent'];
	                if (pIndex == null) {
	                    return result;
	                }
	                // 赋值parent进行再次检查
	                index = pIndex;
	            } else {
	                point[index]['count']++;
	                break;
	            }
	        }
	    }
	}
// 输入价格校验
function addPrice(e)
 { 
	   var tvalue=$(e).val();
	   if(tvalue==""||!isNumber(tvalue))
	   {
			// alert("请输入数字!")
			$(e).siblings(".error-message").css("display", "block");
		    return false;
	   }
	   else
	   {
		   $(e).siblings(".error-message").css("display", "none");
	   }
 } 
// 验证是否是数字 包含小数点
function isNumber(num)
{
	  // var reNum=/^\d*$/;
	  var reNum=/^[0-9]+\.?[0-9]*$/;
	  return(reNum.test(num));
}

/**
 * 保存 费率信息
 */
function savepayRate(){
	 // 获取规格 中的 属性
	 var rateDim = {"rateDim":[]};
	 var dimAttrCodes = [];
	 var dimAttrNames = [];
	 $(".control-group").find(".sku-sub-group").not(".clear").each(function(i,n){
		 var dims = {"dimCodes":"","dimName":"","dimAttrs":[]};
		 var dimCode = $(n).find("#dimCode").val();
		 var dimName = $(n).find("#dimName").val();
		 // 判断是否有子类 如果没有则不添加
		 var flag  = false ;
		 // 规格下的属性
		 $(n).find(".sku-atom").each(function(j,m){
			 var dimAttrCode = $(m).find("#dimAttrCode").val();
			 var dimAttrName = $(m).find("#dimAttrName").val();
			 var dimAttrs = {"dimAttrCode":"","dimAttrName":""};
			 if(dimAttrCode != undefined && dimAttrCode != "" &&　dimAttrCode != null){
				 dimAttrs.dimAttrCode = dimAttrCode;
				 dimAttrs.dimAttrName = dimAttrName;
				 dims.dimAttrs.push(dimAttrs);
				 flag = true ;
			 }
		 })
		 if(flag){
			 dims.dimCodes = dimCode;
			 dims.dimName = dimName;
			 rateDim.rateDim.push(dims);
		 }
	 })
	 
	 // 获取费率规格设置值
	 var rateSku = {"rateSku":[]};
	 $("#skuTbody").find("input[name='rateSku.skuCode']").each(function(i,n){
		var sku = {"skuCode":"","intoRate":"","setRate":""}
		 var skuCode =  $(n).val();
		 var intoRate =  $(n).next("input").val();
		 var setRate =  $(n).parents("td").next("td").find("input").val();
		 sku.skuCode = skuCode;
		 sku.intoRate = intoRate;
		 sku.setRate = setRate;
		 rateSku.rateSku.push(sku)
	 })
	 // 将规格信息转换成 JSON 数据
	 var rateDimJson =  JSON.stringify(rateDim);
	 var rateSkuJson =  JSON.stringify(rateSku);
	 
	 var url = ctx + '/payRate!savePayRate.do';
	 $.post(url,{rateDimJson,rateSkuJson},function(data){
		 var resultCode = data.resultCode;
		 if(resultCode ==  "SUCCESS"){
			 alert("保存成功！")
		 }else{
			 alert("保存失败！")
		 }
	 })
	 
	 
}

function showRateFail(){
	alert("保存失败")
}
function showRateSuccess(){
	alert("保存成功")
}
