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
<%@ page import="com.lpmas.srm.business.*"%>
<%
	List<SupplierPropertyTypeBean> list = (List<SupplierPropertyTypeBean>) request
			.getAttribute("PropertyTypeList");
	AdminUserHelper adminUserHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
	PageBean PAGE_BEAN = (PageBean) request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>) request.getAttribute("CondList");
	int parentPropertyId = (Integer) request.getAttribute("ParentPropertyId");
	SupplierPropertyTypeBean parentPropertyBean = (SupplierPropertyTypeBean) request
			.getAttribute("ParentPropertyBean");
	Map<Integer, List<SupplierTypeBean>> supplierTypeTreeMap = (Map<Integer, List<SupplierTypeBean>>) request
			.getAttribute("supplierTypeTreeMap");
	Map<Integer, List<SupplierPropertyCategoryBean>> supplierPropertyCategoryTreeMap = (Map<Integer, List<SupplierPropertyCategoryBean>>) request
			.getAttribute("supplierPropertyCategoryTreeMap");
	SupplierTypeBusiness supplierTypeBusiness = new SupplierTypeBusiness();
	SupplierPropertyCategoryBusiness supplierPropertyCategoryBusiness = new SupplierPropertyCategoryBusiness();
	int supplierType = ParamKit.getIntParameter(request, "supplierType", 0);
	int supplierPropertyCategory = ParamKit.getIntParameter(request, "supplierPropertyCategory", 0);
%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商属性类型配置</title>
<link href="<%=STATIC_URL%>/css/main.css" type="text/css"
	rel="stylesheet" />
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
	<%
		if (parentPropertyId > 0) {
	%>
	<div class="article_tit">
		<ul class="art_nav">
			<li><a href="SupplierPropertyTypeList.do">供应商属性类型配置列表</a></li>
			<li>&nbsp;>&nbsp;<%=parentPropertyBean.getPropertyName()%></li>
		</ul>
	</div>
	<%
		} else {
	%>
	<p class="article_tit">供应商属性类型配置列表</p>
	<%
		}
	%>
	<form id="formData" name="formData" action="SupplierPropertyTypeList.do" method="post">
	<input type="hidden" name="parentPropertyId" id="parentPropertyId" value=<%=parentPropertyId%>>
	   <div class="search_form">
			<em class="em1">属性代码：</em> 
			<input type="text" id="propertyCode" name="propertyCode" value="<%=ParamKit.getParameter(request, "propertyCode", "")%>" />
			&nbsp; <em class="em1">属性名称：</em> 
			<input type="text" id="propertyName" name="propertyName" value="<%=ParamKit.getParameter(request, "propertyName", "")%>" />
			&nbsp; <em class="em1">供应商类型：</em> 
			<input type="hidden" id="supplierType" name="supplierType" value="<%=supplierType%>" />
			<input type="text" name="supplierTypeName" id="supplierTypeName" value="<%=ParamKit.getParameter(request, "supplierTypeName", "")%>" readOnly size="10"/> 
			<input type="button" name="checkSupplierType" id="checkSupplierType" value="选择"> 
			&nbsp; <em class="em1">属性类型：</em>
			<input type="hidden" id="supplierPropertyCategory" name="supplierPropertyCategory" value="<%=supplierPropertyCategory%>" /> 
			<input type="text" name="supplierPropertyCategoryName" id="supplierPropertyCategoryName" value="<%=ParamKit.getParameter(request, "supplierPropertyCategoryName", "")%>" readOnly  size="10"/> 
			<input type="button" name="checkSupplierPropertyCategory" id="checkSupplierPropertyCategory" value="选择"> 
			&nbsp; <em class="em1">有效状态：</em> 
			<select id="status" name="status">
				<%
					for (StatusBean statusBean : Constants.STATUS_LIST) {
				%>
				<option value="<%=statusBean.getStatus()%>"
					<%=ParamKit.getIntParameter(request, "status",
						Constants.STATUS_VALID) == ((Integer) statusBean.getStatus()) ? "selected" : ""%>><%=statusBean.getValue()%></option>
				<%
					}
				%>
			</select>
			<%
				if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.SEARCH)) {
			%>
			<input type="submit" name="submit1" value="查询" class="search_btn_sub">
			<input name="" type="button" class="search_btn_sub" value="条件重置"
				onclick='location.href="SupplierPropertyTypeList.do?parentPropertyId=<%=parentPropertyId%>"' />
			<%
				}
			%>
		</div>
		<table border=0 width="100%" cellpadding="0" class="table_style">
			<tr>
				<th width="5%">属性ID</th>
				<th width="15%">属性代码</th>
				<th width="15%">属性名称</th>
				<th width="10%">供应商类型</th>
				<th width="10%">属性类型</th>
				<th width="10%">输入方式</th>
				<th width="10%">是否必填</th>
				<th width="15%"><div align="center">操作</div></th>
			</tr>
			<%
				for (SupplierPropertyTypeBean bean : list) {
			%>
			<tr>
				<td><%=bean.getPropertyId()%></td>
				<td><%=bean.getPropertyCode()%></td>
				<td><%=bean.getPropertyName()%></td>
				<td>
					<%
						for (int i = 0; i < supplierTypeTreeMap.get(bean.getSupplierTypeId()).size(); ++i) {
					%>
					<%
						if (i < supplierTypeTreeMap.get(bean.getSupplierTypeId()).size() - 1) {
					%>
					<%=supplierTypeTreeMap.get(bean.getSupplierTypeId()).get(i).getTypeName()%>-
					<%
						} else {
					%> <%=supplierTypeTreeMap.get(bean.getSupplierTypeId()).get(i).getTypeName()%>
					<%
						}
					%> 
					<%
 	                    }
                    %>

				</td>
				<td>
					<%
						for (int j = 0; j < supplierPropertyCategoryTreeMap.get(bean.getCategoryId()).size(); ++j) {
					%>
					<%
						if (j < supplierPropertyCategoryTreeMap.get(bean.getCategoryId()).size() - 1) {
					%>
					<%=supplierPropertyCategoryTreeMap.get(bean.getCategoryId()).get(j).getCategoryName()%>-
					<%
						} else {
					%> <%=supplierPropertyCategoryTreeMap.get(bean.getCategoryId()).get(j).getCategoryName()%>
					<%
						}
					%> <%
					    }
 				    %>
				</td>
				<td><%=MapKit.getValueFromMap(bean.getInputMethod(), SupplierPropertyTypeConfig.INPUT_METHOD_MAP)%></td>
				<td><%=MapKit.getValueFromMap(bean.getIsRequired(), Constants.SELECT_MAP)%></td>
				<td align="center"><a
					href="/srm/SupplierPropertyTypeManage.do?propertyId=<%=bean.getPropertyId()%>&parentPropertyId=<%=parentPropertyId%>&readOnly=true">查看</a>
					<%
						if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.UPDATE)) {
					%> | <a
					href="/srm/SupplierPropertyTypeManage.do?propertyId=<%=bean.getPropertyId()%>&parentPropertyId=<%=parentPropertyId%>&readOnly=false">修改</a>
					<%
						}
					%> <%
 	                 if (parentPropertyBean == null) {
                    %> | <a
					href="/srm/SupplierPropertyTypeList.do?parentPropertyId=<%=bean.getPropertyId()%>">查看子属性</a>
					<%
						}
					%></td>
			</tr>
			<%
				}
			%>
		</table>
	</form>
	<ul class="page_info">
		<li class="page_left_btn">
			<%
				if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.CREATE)) {
			%> <input name="addBtn" type="button" class="modifysubbtn"
			class="BTN1" value="新建"
			onclick="javascript:document.location='/srm/SupplierPropertyTypeManage.do?parentPropertyId=<%=parentPropertyId%>'">
			<%
				}
			%>
		</li>
		<li class="page_num">
			<%
				if (list != null && list.size() > 0) {
			%> <%@ include file="../include/page.jsp"%> <%
 	}
 %>
		</li>
	</ul>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
	  $("#checkSupplierType").click(function() {
		$.fancybox.open({
			href : 'SupplierTypeSelect.do?callbackFun=selectSupplierType',
		    type : 'iframe',
			width : 560,
			minHeight : 150
						});
	  });
	  $("#checkSupplierPropertyCategory").click(function() {
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
			$("#supplierType").val(supplierTypeId);
			$("#supplierTypeName").val(typeName);
		}
	}
	function selectSupplierPropertyCategory(categoryId, categoryName) {
		if (categoryId != "") {
			$("#supplierPropertyCategory").val(categoryId);
			$("#supplierPropertyCategoryName").val(categoryName);
		}
	}
</script>