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
	AdminUserHelper adminUserHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
	List<SupplierPropertyCategoryBean> list = (List<SupplierPropertyCategoryBean>) request.getAttribute("SupplierPropertyCategoryList");
	PageBean PAGE_BEAN = (PageBean) request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>) request.getAttribute("CondList");
	int parentCategoryId = (Integer) request.getAttribute("ParentCategoryId");
	List<SupplierPropertyCategoryBean> treeList = (List<SupplierPropertyCategoryBean>) request.getAttribute("ParentTreeList");
	SupplierPropertyCategoryBean parentCategoryBean = (SupplierPropertyCategoryBean) request.getAttribute("ParentCategoryBean");
%>

<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商属性类型分类配置</title>
<link href="<%=STATIC_URL%>/css/main.css" type="text/css"
	rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL%>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/ui.js"></script>
</head>
<body class="article_bg">
	<%
		if (parentCategoryId > 0) {
	%>
	<div class="article_tit">
		<ul class="art_nav">
			<li><a href="SupplierPropertyCategoryList.do">供应商属性类型分类配置</a>&nbsp;>&nbsp;</li>
			<%for (SupplierPropertyCategoryBean supplierPropertyCategoryBean : treeList) {%>
				<li>
				<%if(parentCategoryId !=supplierPropertyCategoryBean.getCategoryId()) {%>
				<a href="SupplierPropertyCategoryList.do?parentCategoryId=<%=supplierPropertyCategoryBean.getCategoryId()%>"><%=supplierPropertyCategoryBean.getCategoryName()%></a>&nbsp;>&nbsp;</li>
				<%} else{ %>
				<%=supplierPropertyCategoryBean.getCategoryName()%>
			<%	}
			}
			%>
		</ul>
	</div>
	<%
		} else {
	%>
	<p class="article_tit">供应商属性类型分类配置</p>
	<%
		}
	%>
	<form name="formSearch" method="post" action="SupplierPropertyCategoryList.do">
		<div class="search_form">
			<em class="em1">供应商属性类型代码：</em> <input type="text" name="categoryCode" id="categoryCode" value="<%=ParamKit.getParameter(request, "categoryCode", "")%>" size="20" /> 
			<em class="em1">供应商属性类型名称：</em> <input type="text" name="categoryName" id="categoryName" value="<%=ParamKit.getParameter(request, "categoryName", "")%>" size="20" /> 
			<em class="em1">有效状态：</em> <select name="status"id="status">
				<%
					int status = ParamKit.getIntParameter(request, "status", Constants.STATUS_VALID);
					for (StatusBean<Integer, String> statusBean : Constants.STATUS_LIST) {
				%>
				<option value="<%=statusBean.getStatus()%>"
					<%=(statusBean.getStatus() == status) ? "selected" : ""%>><%=statusBean.getValue()%></option>
				<%
					}
				%>
			</select> 
			<input type="hidden" name="parentCategoryId" id="parentCategoryId" value="<%=parentCategoryId%>" />
			<%
				if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.SEARCH)) {
			%>
			<input name="" type="submit" class="search_btn_sub" value="查询" />
			<%
				}
			%>
		</div>
		<table width="100%" border="0" cellpadding="0" class="table_style">
			<tr>
				<th>供应商属性类型ID</th>
				<th>供应商属性类型代码</th>
				<th>供应商属性类型名称</th>
				<th>有效状态</th>
				<th>操作</th>
			</tr>
			<%
				for (SupplierPropertyCategoryBean bean : list) {
			%>
			<tr>
				<td><%=bean.getCategoryId()%></td>
				<td><%=bean.getCategoryCode()%></td>
				<td><%=bean.getCategoryName()%></td>
				<td><%=Constants.STATUS_MAP.get(bean.getStatus())%></td>
				<td align="center">
					<a href="/srm/SupplierPropertyCategoryManage.do?categoryId=<%=bean.getCategoryId()%>&parentCategoryId=<%=bean.getParentCategoryId()%>&readOnly=true">查看</a>
					<%if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.UPDATE)) {%>
					| <a href="/srm/SupplierPropertyCategoryManage.do?categoryId=<%=bean.getCategoryId()%>&parentCategoryId=<%=bean.getParentCategoryId()%>&readOnly=false">修改</a>
					<%}%> 
					| <a href="/srm/SupplierPropertyCategoryList.do?parentCategoryId=<%=bean.getCategoryId()%>">查看子分类</a>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</form>
	<ul class="page_info">
		<li class="page_left_btn">
			<%
				if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_CATEGORY, OperationConfig.CREATE)) {
			%>
			<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='SupplierPropertyCategoryManage.do?parentCategoryId=<%=parentCategoryId%>'">
			<%
				}
			%> 
			<%
 			if (parentCategoryId > 0) {
 			%> 
 			<a href="SupplierPropertyCategoryList.do?parentCategoryId=<%=parentCategoryBean.getParentCategoryId()%>"><input type="button" name="button" id="button" value="返回"></a> <%
 			}
			 %>
		</li>
		<%@ include file="../include/page.jsp"%>
	</ul>
</body>
</html>