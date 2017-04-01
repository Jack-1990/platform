<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="edit_goods" style="min-height: 500px;">
	<div class="control-group">
		<label>规格:</label>
		<div>
			<c:if test="${rateDimList != null && !empty rateDimList}">
				<c:forEach var="rateDim" items="${rateDimList }">
					<div class="sku-sub-group" style="display: block;">
						<h3 class="sku-group-title">
							<div class="select2-container js-sku-name" id="s2id_autogen7" style="width: 100px;" data-code="${rateDim.dimCode }"
								data-name="${rateDim.dimName }">
								<input type="hidden" id="dimCode" name="rateDim.dimCode" value="${rateDim.dimCode }"><input type="hidden" id="dimName" name="rateDim.dimName"
									value="${rateDim.dimName }">
								<a href="javascript:void(0)" onclick="editSkuname(this);" class="sku_name select2-choice">
									<span class="select2-chosen">${rateDim.dimName }</span> <abbr class="select2-search-choice-close"></abbr> <span class="select2-arrow"><b></b></span>
								</a>
								<input class="select2-focusser select2-offscreen" type="text" id="s2id_autogen8">
							</div>
							<div id="select2-drop-mask" class="select2-drop-mask" style="display: none;"></div>
							<div class="select2-drop select2-display-none select2-with-searchbox select2-drop-active"
								style="top: 36px; left: 10px; width: 100px; display: none;" id="select2-drop">
								<ul class="select2-results">
									
								</ul>
							</div>
							<a class="remove-sku-group">×</a>
						</h3>
						<div class="sku-group-cont">
							<div>
								<c:forEach items="${rateDim.rateDimAttrList }" var="rateDimAttr">
									<div class="sku-atom">
										<input type="hidden" id="dimAttrCode" name="rateDimAttr.dimAttrCode" value="${rateDimAttr.dimAttrCode }"><input type="hidden"
											id="dimAttrName" name="rateDimAttr.dimAttrName" value="${rateDimAttr.dimAttrName }"><span
											data-atom-id="${rateDimAttr.dimAttrCode }">${rateDimAttr.dimAttrName }</span>
										<div class="atom-close" onclick="deleteSkuAttr(this)">×</div>
									</div>
								</c:forEach>
								<div class="add-sku">
									<a href="javascript:void(0);" onclick="showAddSkuAtom(this)" class="blue font16 ml20 pl20" style="display: block;">添加</a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
			<div class="sku-sub-group clear">
				<h3>
					<a href="javascript:void(0);" class="add-sku-group">添加规格项目</a>
				</h3>
			</div>
		</div>
	</div>
	<div class="control-group">
		<label>费率设置:</label>
		<div class="sku-stock">
			<table class="table-sku-stock">
				<thead>
					<tr id="skuTitle">
					</tr>
				</thead>
				<tbody id="skuTbody">
					<tr>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="add_goods">
		<p class="step">
			<a href="javascript:void(0)" class="current" onclick="savepayRate()">保存</a>
		</p>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	var rateSkuList = ${rateSkuList};
	
	if(rateSkuList != "" && rateSkuList.length){
		establishSkuTable();
		for(var i =0 ;i<rateSkuList.length ;i++){
			$("#skuTbody").find("input[name='rateSku.skuCode']").each(function(j,n){
				 var skuCode =  $(n).val();       
				if(rateSkuList[i].skuCode == skuCode){
				$(n).next("input").val(rateSkuList[i].intoRate);
				$(n).parents("td").next("td").find("input").val(rateSkuList[i].setRate);
				}
			 })
		}
	}
})
</script>