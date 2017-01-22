<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.srm.bean.*"  %>
<%@ page import="com.lpmas.srm.config.*"  %>
<% 
	SupplierPropertyCategoryBean bean = (SupplierPropertyCategoryBean)request.getAttribute("SupplierPropertyCategoryBean");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	int categoryId = bean.getCategoryId();
	int parentCategoryId = (Integer)request.getAttribute("ParentCategoryId");
	List<SupplierPropertyCategoryBean> treeList = (List<SupplierPropertyCategoryBean>)request.getAttribute("ParentTreeList");
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	request.setAttribute("readOnly", readOnly);
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>供应商属性类型配置</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="SupplierPropertyCategoryList.do">供应商属性类型分类配置</a>&nbsp;>&nbsp;</li>
			<% for(SupplierPropertyCategoryBean supplierPropertyCategoryBean : treeList) {%>
			<li><a href="SupplierPropertyCategoryList.do?parentCategoryId=<%=supplierPropertyCategoryBean.getCategoryId()%>"><%=supplierPropertyCategoryBean.getCategoryName()%></a>&nbsp;>&nbsp;</li>
			<%} %>
			<% if(categoryId > 0) {%> 
			<li><%=bean.getCategoryName() %>&nbsp;>&nbsp;</li>
			<li>修改供应商属性类型</li>
			<%} else { %>
			<li>新建供应商属性类型</li>
			<%} %>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="SupplierPropertyCategoryManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="categoryId" id="categoryId" value="<%=categoryId %>"/>
	  <input type="hidden" name="parentCategoryId" id="parentCategoryId" value="<%=parentCategoryId %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>供应商属性类型代码：</em>
	      <input type="text" name="categoryCode" id="categoryCode" size="20" maxlength="10" value="<%=bean.getCategoryCode() %>" checkStr="供应商属性类型代码;code;true;;10"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>供应商属性类型名称：</em>
	      <input type="text" name="categoryName" id="categoryName" size="30" maxlength="100" value="<%=bean.getCategoryName() %>" checkStr="供应商属性类型名称;txt;true;;100"/>
	    </p>
	    <p>
	      <em class="int_label">优先级：</em>
	      <input type="text" name="priority" id="priority" size="30" maxlength="100" value="<%=bean.getPriority() %>" checkStr="优先级;num;false;;6"/>
	    </p>
	    <p>
	      <em class="int_label">有效状态：</em>
	      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
	    </p>
	  </div>
	  <div class="div_center">
	  	<input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  	<a href="SupplierPropertyCategoryList.do?parentCategoryId=<%=parentCategoryId %>"><input type="button" name="cancel" id="cancel" value="取消" ></a>
	  </div>
	</form>
</body>
<script>
$(document).ready(function() {
	var readonly = '${readOnly}';
	if(readonly=='true') {
		disablePageElement();
	}
});
</script>
</html>