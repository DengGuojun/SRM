<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ page import="com.lpmas.admin.bean.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.srm.config.*"%>
<%@ page import="com.lpmas.srm.bean.*"%>

<%
	String callbackFun = ParamKit.getParameter(request, "callbackFun", "callbackFun");
	List<SupplierPropertyCategoryBean> topSupplierPropertyCategoryList = (List<SupplierPropertyCategoryBean>) request
			.getAttribute("topSupplierPropertyCategoryList");
%>

<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择属性类型</title>
<link href="<%=STATIC_URL%>/css/main.css" type="text/css"
	rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL%>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/ui.js"></script>
</head>
<body class="article_bg">
	<p>
		<span class="int_label">属性类型：</span> 
		<input type="hidden" id="supplierPropertyCategory" name="supplierPropertyCategory"value="" />
		<input type="hidden" id="supplierPropertyCategoryName" name="supplierPropertyCategoryName"value="" /> 
		<input type="hidden" id="categoryIndex" name="categoryIndex" value="1" /> 
		<select id="categoryIndex_1"onchange="categorySelectionChange(this)">
		<option value="0">请选择类型</option>
			<%
				for (SupplierPropertyCategoryBean categoryBean : topSupplierPropertyCategoryList) {
			%><option value="<%=categoryBean.getCategoryId()%>"><%=categoryBean.getCategoryName()%></option>
			<%
				}
			%>
		</select>
	</p>
	<div class="div_center">
		<input type="submit" name="create" id="create" class="modifysubbtn"
			value="选择" onclick="callbackTo()" />
	</div>
</body>
</html>
<script>
	function categorySelectionChange(obj) {
		var parentCategoryId = $(obj).val();
		$("#supplierPropertyCategory").val(parentCategoryId);
		$("#supplierPropertyCategoryName").val($(obj).find("option:selected").text());
		var index = $(obj).attr("id")
				.substr($(obj).attr("id").indexOf("_") + 1);//获取当前层级数字
		var indexNum = parseInt(index);
		var newIndex = indexNum + 1;//得到新层级的数字
		if (parentCategoryId == 0) {
			//删除后面的层级选择框
			removeCategorySelectBox(newIndex);
		} else if (parentCategoryId == -1) {
			//如果当前层级没有选中，则设置上一层级的值
			var parentIndex = indexNum - 1;
			$("#supplierPropertyCategory").val(
					$("#categoryIndex_" + parentIndex).val());
			$("#supplierPropertyCategoryName").val($("#categoryIndex_" + parentIndex).find("option:selected").text());
			//删除后面的层级选择框
			removeCategorySelectBox(newIndex);
		} else {
			var params = {
				'parentCategoryId' : parentCategoryId
			};
			$.ajax({
						type : 'get',
						url : "/srm/SupplierPropertyCategoryJsonList.do",
						data : params,
						dataType : 'json',
						success : function(data) {
							removeCategorySelectBox(newIndex);
							if (data != null) {
								var items = data.result;
								if (items != null) {
									var element = "<select id=categoryIndex_"+ newIndex + " onchange=categorySelectionChange(this)>";
									element += "<option value = -1>请选择类型</option>";
									for (var i = 0; i < items.length; i++) {
										var item = items[i];
										element += "<option value = '"+item.categoryId+"'>"+ item.categoryName + "</option>";
									};
									element += "</select>";
									$(obj).after(element);
								} 
								$("#categoryIndex").val(newIndex);
							}

						},
						error : function() {
							return;
						}
					});
		}
	}

	function removeCategorySelectBox(startIndex) {
		//删除后面的层级选择框
		var maxIndex = $("#categoryIndex").val();
		var maxIndexNumber = parseInt(maxIndex);
		for (var i = startIndex; i <= maxIndexNumber; i++) {
			$("#categoryIndex_" + i).remove();
		}
	}
	function callbackTo() {
		var categoryId = $("#supplierPropertyCategory").val();
		var categoryName = $("#supplierPropertyCategoryName").val();
		if (categoryId == "0") {
			alert("请选择属性类型");
			return false;
		} else {
			self.parent.<%=callbackFun%>(categoryId,categoryName);
			try {
				self.parent.jQuery.fancybox.close();
			} catch (e) {
				console.log(e);
			}
		}

	}
</script>