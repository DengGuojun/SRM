<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.srm.config.*"%>
<%@ page import="com.lpmas.srm.bean.*"%>
<%@ page import="com.lpmas.srm.business.*"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@page import="com.lpmas.framework.util.*"%>
<%@page import="com.lpmas.framework.config.Constants"%>
<%@page import="com.lpmas.framework.bean.StatusBean"%>
<%@page import="com.lpmas.admin.business.AdminUserHelper"%>

<%
	AdminUserHelper adminUserHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
	SupplierPropertyTypeBean propertyTypeBean = (SupplierPropertyTypeBean) request
			.getAttribute("PropertyTypeBean");
	int parentPropertyId = (Integer) request.getAttribute("ParentPropertyId");
	SupplierPropertyTypeBean parentPropertyTypeBean = (SupplierPropertyTypeBean) request
			.getAttribute("ParentPropertyTypeBean");
	String readOnly = ParamKit.getParameter(request, "readOnly", "false").trim();
	request.setAttribute("readOnly", readOnly);
	SupplierTypeBean supplierTypeBean = (SupplierTypeBean) request
			.getAttribute("supplierTypeBean");
	SupplierPropertyCategoryBean supplierPropertyCategoryBean = (SupplierPropertyCategoryBean) request
			.getAttribute("supplierPropertyCategoryBean");
%>

<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商属性配置</title>
<link href="<%=STATIC_URL%>/css/main.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="../js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/ui.js"></script>
<script type='text/javascript'
	src="<%=STATIC_URL%>/js/fancyBox/jquery.fancybox.js"></script>
<link rel="stylesheet"
	href="<%=STATIC_URL%>/js/fancyBox/jquery.fancybox.css" type="text/css"
	media="screen" />
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()"><img
			src="<%=STATIC_URL%>/images/back_forward.jpg" /></a>
		<ul class="art_nav">
			<li><a href="SupplierPropertyTypeList.do">供应商属性类型配置列表</a>&nbsp;>&nbsp;</li>
			<%
				if (parentPropertyTypeBean != null) {
			%>
			<li><%=parentPropertyTypeBean.getPropertyName()%>&nbsp;>&nbsp;</li>
			<%
				}
			%>
			<%
				if (propertyTypeBean.getPropertyId() > 0) {
			%>
			<li><%=propertyTypeBean.getPropertyName()%>&nbsp;>&nbsp;</li>
			<li>修改供应商属性类型配置信息</li>
			<%
				} else {
			%>
			<li>新建供应商属性类型配置信息</li>
			<%
				}
			%>
		</ul>
	</div>
	<%
		if (propertyTypeBean.getPropertyId() > 0
				&& propertyTypeBean.getInputMethod() == SupplierPropertyTypeConfig.INPUT_METHOD_SELECT
				&& "".equals(propertyTypeBean.getFieldSource())) {
	%>
	<div class="article_tit">
		<p class="tab">
			<a
				href="SupplierPropertyTypeManage.do?propertyId=<%=propertyTypeBean.getPropertyId()%>&readOnly=<%=readOnly%>">供应商属性信息</a>
			<a
				href="SupplierPropertyOptionList.do?propertyId=<%=propertyTypeBean.getPropertyId()%>&readOnly=<%=readOnly%>">供应商属性选项</a>
		</p>
		<script>
			tabChange('.tab', 'a');
		</script>
	</div>
	<%
		}
	%>
	<form id="formData" name="formData" method="post"
		action="SupplierPropertyTypeManage.do"
		onsubmit="javascript:return checkForm('formData');">
		<div class="modify_form">
			<input type="hidden" name="parentPropertyId" id="parentPropertyId"
				value="<%=parentPropertyId%>" /> <input type="hidden"
				name="propertyId" id="propertyId"
				value="<%=propertyTypeBean.getPropertyId()%>" />
			<%
				if (parentPropertyId > 0) {
			%>
			<input type="hidden" name="propertyCode"
				value="<%=parentPropertyTypeBean.getPropertyCode()%>" />
			<%
				} else {
			%>
			<p>
				<em class="int_label"><span>*</span>属性代码：</em> <input type="text"
					name="propertyCode" id="propertyCode" size="50"
					value="<%=propertyTypeBean.getPropertyCode()%>" maxlength="50"
					checkStr="属性代码;code;true;;50"
					<%=propertyTypeBean.getPropertyId() > 0 ? "readonly" : ""%> />
			</p>
			<%
				}
			%>
			<p>
				<em class="int_label"><span>*</span>属性名称：</em> <input type="text"
					name="propertyName" id="propertyName"
					value="<%=propertyTypeBean.getPropertyName()%>" maxlength="200"
					checkStr="属性名称;txt;true;;200" />
			</p>
			<%
				if (parentPropertyId > 0) {
			%>
			<input type="hidden" name="supplierTypeId"
				value="<%=parentPropertyTypeBean.getSupplierTypeId()%>" /> <input
				type="hidden" name="categoryId"
				value="<%=parentPropertyTypeBean.getCategoryId()%>" />
			<%
				} else {
			%>
			<p>
				<em class="int_label"><span>*</span>供应商类型：</em>
				<%
					if (propertyTypeBean.getPropertyId() > 0) {
				%>
				<input type="text" name="supplierTypeName" id="supplierTypeName"
					value="<%=supplierTypeBean.getTypeName()%>"
					readOnly /> <input type="hidden" name="supplierTypeId"
					id="supplierTypeId"
					value="<%=propertyTypeBean.getSupplierTypeId()%>" /> <input
					type="button" name="modify" id="modify" value="修改">

				<%
					} else {
				%>
				<input type="text" name="supplierTypeName" id="supplierTypeName"
					value="" readOnly /> <input type="hidden" name="supplierTypeId"
					id="supplierTypeId" value="" /> <input type="button" name="modify"
					id="modify" value="选择">
				<%
					}
				%>
			</p>
			<p>
				<em class="int_label"><span>*</span>属性类型分类：</em>
				<%
					if (propertyTypeBean.getPropertyId() > 0) {
				%>
				<input type="hidden" name="categoryId" id="categoryId"
					value="<%=propertyTypeBean.getCategoryId()%>" /> <input
					type="text" name="categoryName" id="categoryName"
					value="<%=supplierPropertyCategoryBean.getCategoryName()%>"
					readOnly /> <input type="button" name="modifyCategory"
					id="modifyCategory" value="修改">


				<%
					} else {
				%>
				<input type="hidden" id="categoryId" name="categoryId" value="" />
				<input type="text" name="categoryName" id="categoryName" value=""
					readOnly /> <input type="button" name="modifyCategory"
					id="modifyCategory" value="选择">
				<%
					}
				%>
			</p>
			<%
				}
			%>
			<p>
				<em class="int_label"><span>*</span>输入方式：</em> <select
					id="inputMethod" name="inputMethod" checkStr="输入方式;txt;true;;100">
					<option value="">请选择</option>
					<%
						int input = propertyTypeBean.getInputMethod();
						for (StatusBean<Integer, String> inputMethod : SupplierPropertyTypeConfig.INPUT_METHOD_LIST) {
					%>
					<option value="<%=inputMethod.getStatus()%>"
						<%=input == inputMethod.getStatus() ? "selected" : ""%>><%=inputMethod.getValue()%></option>
					<%
						}
					%>
				</select>
			</p>
			<p>
				<em class="int_label">文本框样式：</em> <input type="text"
					name="inputStyle" id="inputStyle"
					value="<%=HtmlStringKit.toHtml(propertyTypeBean.getInputStyle())%>"
					size="50" maxlength="2000" checkStr="文本框样式;txt;false;;2000" />Json格式，如：{"maxlength":"100","size":"50"}
			</p>
			<p>
				<em class="int_label">文本框说明：</em> <input type="text"
					name="inputDesc" id="inputDesc"
					value="<%=propertyTypeBean.getInputDesc()%>" size="50"
					maxlength="2000" checkStr="文本框说明;txt;false;;2000" />
			</p>
			<p>
				<em class="int_label"><span>*</span>数据字段类型：</em> <select
					id="fieldType" name="fieldType" checkStr="数据类型字段;txt;true;;100">
					<option value="">请选择</option>
					<%
						int fieldType = propertyTypeBean.getFieldType();
						for (StatusBean<Integer, String> fieldTypeBean : SupplierPropertyTypeConfig.FIELD_TYPE_LIST) {
					%>
					<option value="<%=fieldTypeBean.getStatus()%>"
						<%=fieldType == fieldTypeBean.getStatus() ? "selected" : ""%>><%=fieldTypeBean.getValue()%></option>
					<%
						}
					%>
				</select>
			</p>
			<p>
				<em class="int_label">数据字段格式：</em> <input type="text"
					name="fieldFormat" id="fieldFormat"
					value="<%=propertyTypeBean.getFieldFormat()%>" size="50"
					maxlength="200" checkStr="数据字段格式;txt;false;;200" />内容为正则表达式，用于输入时限制或提交时校验时使用。
			</p>
			<p>
				<em class="int_label">数据字段来源：</em> <input type="text"
					name="fieldSource" id="fieldSource"
					value="<%=HtmlStringKit.toHtml(propertyTypeBean.getFieldSource())%>"
					size="50" maxlength="200" checkStr="数据字段格式;txt;false;;200" />当输入方式为外部选择框时，需填写数据来源的URL
			</p>
			<p>
				<em class="int_label">默认值：</em> <input type="text"
					name="defaultValue" id="defaultValue"
					value="<%=propertyTypeBean.getDefaultValue()%>" maxlength="2000"
					checkStr="默认值;txt;false;;2000" />
			</p>
			<p>
				<em class="int_label">是否必填：</em> <input type="checkbox"
					id="isRequired" name="isRequired"
					value="<%=Constants.STATUS_VALID%>"
					<%=(propertyTypeBean.getIsRequired() == Constants.STATUS_VALID) ? "checked" : ""%> />
			</p>
			<p>
				<em class="int_label"> 是否可修改：</em> <input type="checkbox"
					id="isModifiable" name="isModifiable"
					value="<%=Constants.STATUS_VALID%>"
					<%=(propertyTypeBean.getIsModifiable() == Constants.STATUS_VALID) ? "checked" : ""%> />
			</p>
			<p>
				<em class="int_label">优先级：</em> <input type="text" name="priority"
					id="priority" value="<%=propertyTypeBean.getPriority()%>"
					maxlength="6" checkStr="优先级;num;false;;6" />
			</p>
			<p>
				<em class="int_label">有效状态：</em> <input type="checkbox" id="status"
					name="status" value="<%=Constants.STATUS_VALID%>"
					<%=(propertyTypeBean.getStatus() == Constants.STATUS_VALID) ? "checked" : ""%> />
			</p>
			<p class="p_top_border">
				<em class="int_label">备注：</em>
				<textarea name="memo" id="memo" cols="60" rows="3"><%=propertyTypeBean.getMemo()%></textarea>
			</p>
		</div>
		<div class="div_center">
			<input type="submit" name="submit" id="submit" class="modifysubbtn"
				value="提交" /> <a href="SupplierPropertyTypeList.do"><input
				type="button" name="cancel" id="cancel" class="modifysubbtn"
				value="返回" /></a>
		</div>
	</form>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		 var readonly = '${readOnly}';
			if (readonly == 'true') {
			  disablePageElement();
			}
		$("#modify").click(function() {
			$.fancybox.open({
		      href : 'SupplierTypeSelect.do?callbackFun=selectSupplierType',
			  type : 'iframe',
			  width : 560,
			  minHeight : 150
			});
		});
		$("#modifyCategory").click(function() {
			$.fancybox.open({
			  href : 'SupplierPropertyCategorySelect.do?callbackFun=selectSupplierPropertyCategory',
			  type : 'iframe',
			  width : 560,
			  minHeight : 150
			});
		});
	});
	function selectSupplierType(supplierTypeId, typeName) {
		if (supplierTypeId != "") {
			$("#supplierTypeId").val(supplierTypeId);
			$("#supplierTypeName").val(typeName);
		}
	}
	function selectSupplierPropertyCategory(categoryId, categoryName) {
		if (categoryId != "") {
			$("#categoryId").val(categoryId);
			$("#categoryName").val(categoryName);
		}
	}
</script>