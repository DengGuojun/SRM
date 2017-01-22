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
	SupplierPropertyTypeBean supplierPropertyTypeBean = (SupplierPropertyTypeBean) request
			.getAttribute("supplierPropertyTypeBean");
	List<SupplierPropertyOptionBean> list = (List<SupplierPropertyOptionBean>) request
			.getAttribute("PropertyOptionList");
	PageBean PAGE_BEAN = (PageBean) request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>) request.getAttribute("CondList");
	AdminUserHelper adminUserHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
	String propertyId = ParamKit.getParameter(request, "propertyId", "").trim();
	String readOnly = ParamKit.getParameter(request, "readOnly", "false").trim();
	request.setAttribute("readOnly", readOnly);
%>

<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商属性配置选项</title>
<link href="<%=STATIC_URL%>/css/main.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="../js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL%>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()"><img
			src="<%=STATIC_URL%>/images/back_forward.jpg" /></a>
		<ul class="art_nav">
			<li><a href="SupplierPropertyTypeList.do">供应商属性类型配置列表</a>&nbsp;>&nbsp;</li>
			<li><%=supplierPropertyTypeBean.getPropertyName()%>&nbsp;>&nbsp;</li>
			<li>供应商属性选项信息列表</li>
		</ul>
	</div>
	<div class="article_tit">
		<p class="tab">
			<a
				href="SupplierPropertyTypeManage.do?propertyId=<%=propertyId%>&readOnly=<%=readOnly%>">供应商属性信息</a>
			<a
				href="SupplierPropertyOptionList.do?propertyId=<%=propertyId%>&readOnly=<%=readOnly%>">供应商属性选项</a>
		</p>
		<script>
			tabChange('.tab', 'a');
		</script>
	</div>
	<table border=0 width="100%" cellpadding="0" class="table_style">
		<tr>
			<th width="5%">选项ID</th>
			<th width="12%">选项值</th>
			<th width="15%">选项内容</th>
			<th width="15%">有效状态</th>
			<th width="10%"><div align="center">操作</div></th>
		</tr>
		<%
			for (SupplierPropertyOptionBean bean : list) {
		%>
		<tr>
			<td><%=bean.getOptionId()%></td>
			<td><%=bean.getOptionValue()%></td>
			<td><%=bean.getOptionContent()%></td>
			<td><%=Constants.STATUS_MAP.get(bean.getStatus())%></td>
			<td align="center">
			    <%
					if(readOnly.equals("true")){
				%>
			    <a href="/srm/SupplierPropertyOptionManage.do?propertyOptionId=<%=bean.getOptionId()%>&readOnly=<%=readOnly%>">查看</a>
				<%
					}else if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.UPDATE) && readOnly.equals("false")) {
				%>
				<a href="/srm/SupplierPropertyOptionManage.do?propertyOptionId=<%=bean.getOptionId()%>&readOnly=<%=readOnly%>">修改</a>
				<%
					}
				%>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<ul class="page_info">
		<li class="page_left_btn">
			<%
				if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_PROPERTY_TYPE, OperationConfig.UPDATE)
						&& readOnly.equals("false")) {
			%>
			<input name="addBtn" type="button" class="BTN1" value="新增"
			onclick="javascript:document.location='/srm/SupplierPropertyOptionManage.do?propertyId=<%=propertyId%>&readOnly=<%=readOnly%>'">
			<%
				}
			%>
		</li>
		<li class="page_num">
			<%
				if (list != null && list.size() > 0) {
			%> <%@ include
				file="../include/page.jsp"%> <%
 	}
 %>
		</li>
	</ul>
</body>
</html>