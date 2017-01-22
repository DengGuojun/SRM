<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.srm.config.*"  %>
<%@ page import="com.lpmas.srm.bean.*"  %>
<%
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	SupplierInfoBean supplierInfoBean = (SupplierInfoBean)request.getAttribute("SupplierInfoBean");
	List<SupplierAddressBean> list = (List<SupplierAddressBean>)request.getAttribute("SupplierAddressList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商地址管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<div class="article_tit">
	<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
	<ul class="art_nav">
		<li><a href="SupplierInfoList.do">供应商列表</a>&nbsp;>&nbsp;</li>
		<li><%=supplierInfoBean.getSupplierName()%>&nbsp;>&nbsp;</li>
		<li>地址簿</li>
	</ul>
</div>
<form name="formSearch" method="post" action="SupplierInfoList.do">
  <table width="100%" border="0" cellpadding="0" class="table_style">
    <tr>
      <th width="5%">供应商地址ID</th>
      <th>国家</th>
      <th>省</th>
      <th>市</th>
      <th>区</th>
      <th width="25%">地址</th>
      <th>联系人</th>
      <th width="5%">是否默认地址</th>
      <th>操作</th>
    </tr>
    <%
    for(SupplierAddressBean bean:list){%> 
    <tr>
      <td><%=bean.getAddressId() %></td>
      <td><%=bean.getCountry() %></td>
      <td><%=bean.getProvince() %></td>
      <td><%=bean.getCity() %></td>
      <td><%=bean.getRegion() %></td>
      <td><%=bean.getAddress() %></td>
      <td><%=bean.getContactName() %></td>
      <%if(bean.getIsDefault()==Constants.STATUS_VALID){ %>
      <td>是</td>
      <%}else{ %>
      <td>否</td>
      <%} %>
      <td align="center">
      	<a href="/srm/SupplierAddressManage.do?addressId=<%=bean.getAddressId()%>&readOnly=true">查看</a>
      	<%if(adminHelper.hasPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.UPDATE)) {%>
      	 |
      	<a href="/srm/SupplierAddressManage.do?addressId=<%=bean.getAddressId()%>&readOnly=false">修改</a>
      	<%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
<%if(adminHelper.hasPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.CREATE)) {%>
<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='SupplierAddressManage.do?supplierId=<%=supplierInfoBean.getSupplierId()%>'">
<%} %>
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>