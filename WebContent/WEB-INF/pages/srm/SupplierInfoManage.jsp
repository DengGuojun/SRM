<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.srm.bean.*"  %>
<%@ page import="com.lpmas.srm.config.*"  %>
<% 
	SupplierInfoBean bean = (SupplierInfoBean)request.getAttribute("SupplierInfo");
	SupplierTypeBean typeBean = (SupplierTypeBean)request.getAttribute("SupplierType");
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	List<Map.Entry<Integer, SupplierPropertyCategoryBean>> propertyCategoryList = (List<Map.Entry<Integer, SupplierPropertyCategoryBean>>)request.getAttribute("propertyCategoryList");
	request.setAttribute("readOnly", readOnly);
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>供应商管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="SupplierInfoList.do">供应商列表</a>&nbsp;>&nbsp;</li>
			<% if(bean.getSupplierId() > 0) {%>
			<li><%=bean.getSupplierName()%>&nbsp;>&nbsp;</li>
			<li>修改供应商信息</li>
			<%}else{ %>
			<li>新建供应商信息</li>
			<%}%>
		</ul>
	</div>
	<%
		if (bean.getSupplierId() > 0) {
	%>
	<div class="article_tit">
		<p class="tab">
			<a href="SupplierInfoManage.do?supplierId=<%=bean.getSupplierId()%>&readOnly=<%=readOnly%>">基本信息</a>
			<% for(Map.Entry<Integer, SupplierPropertyCategoryBean> mapping:propertyCategoryList){  %>
			<a href="SupplierPropertyManage.do?supplierId=<%=bean.getSupplierId()%>&categoryId=<%=mapping.getKey() %>&readOnly=<%=readOnly%>"><%=mapping.getValue().getCategoryName()%></a>
			<% }%>
		</p>
		<script>
		tabChangeForProperty('.tab', 'a');
		</script>
	</div>
	<%
		}
	%>
	<form id="formData" name="formData" method="post" action="SupplierInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="supplierId" id="supplierId" value="<%=bean.getSupplierId() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>供应商名称：</em>
	      <input type="text"  name="supplierName" id="supplierName" size="50" maxlength="100" value="<%=bean.getSupplierName() %>" checkStr="供应商名称;txt;true;;100"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>供应商类型：</em>
	      <em><%=typeBean.getTypeName() %></em>
	      <input type="hidden"  name="supplierType" id="supplierType" value="<%=typeBean.getTypeId()%>" />
	    </p>
	    <p>
	      <em class="int_label">电子邮箱:</em>    
     	  <input  type="text" name="email" id="email" size="50" maxlength="100" value="<%=bean.getEmail() %>" checkStr="电子邮箱;mail;false;;100"/>
	    </p>
	    <p>
	      <em class="int_label">供应商状态：</em>    
	      	<select name="supplierStatus" id="supplierStatus">
	      		<%for(StatusBean<String, String> statusBean : SupplierInfoConfig.SUPPLIER_STATUS_LIST){ %><option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus().equals(bean.getSupplierStatus()))?"selected":"" %>><%=statusBean.getValue() %></option><%} %>
	      	</select>
	    </p>
	   <%--  <p>
	      <em class="int_label">有效状态：</em>
	      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
	    </p> --%>
	    <input type="hidden" name="status" id="status" value="<%=Constants.STATUS_VALID %>"/>
	    <p class="p_top_border">
	      <em class="int_label">备注：</em>
	      <textarea  name="memo" id="memo" cols="60" rows="3" checkStr="备注;txt;false;;1000"><%=bean.getMemo() %></textarea>
	    </p>
	    <input type="hidden" name="country" id="country" value="<%=bean.getCountry() %>"/>
	    <input type="hidden" name="province" id="province" value="<%=bean.getProvince() %>"/>
	    <input type="hidden" name="city" id="city" value="<%=bean.getCity() %>"/>
	    <input type="hidden" name="region" id="region" value="<%=bean.getRegion() %>"/>
	    <input type="hidden" name="address" id="address" value="<%=bean.getAddress() %>"/>
	    <input type="hidden" name="contactName" id="contactName" value="<%=bean.getContactName() %>"/>
	    <input type="hidden" name="zipCode" id="zipCode" value="<%=bean.getZipCode() %>"/>
	    <input type="hidden" name="telephone" id="telephone" value="<%=bean.getTelephone() %>"/>
	    <input type="hidden" name="mobile" id="mobile" value="<%=bean.getMobile()%>"/>
	    <input type="hidden" name="fax" id="fax" value="<%=bean.getFax() %>"/>
	  </div>
	  <div class="div_center">
	  <input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  <input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
</body>
<script>
$(document).ready(function() {
	var readOnly = '${readOnly}';
	if(readOnly=='true') {
		disablePageElement();
	}
});
</script>
</html>