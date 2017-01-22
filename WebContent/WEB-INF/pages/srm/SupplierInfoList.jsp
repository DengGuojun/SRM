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
	AdminUserHelper adminUserHelper = (AdminUserHelper) request.getAttribute("AdminUserHelper");
	List<SupplierInfoBean> list = (List<SupplierInfoBean>)request.getAttribute("SupplierInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	Map<Integer, List<SupplierTypeBean>> supplierTypeTreeMap = (Map<Integer, List<SupplierTypeBean>>) request
			.getAttribute("supplierTypeTreeMap");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/fancyBox/jquery.fancybox.js"></script>
<link rel="stylesheet" href="<%=STATIC_URL %>/js/fancyBox/jquery.fancybox.css" type="text/css" media="screen" />
</head>
<body class="article_bg">
<p class="article_tit">供应商列表</p>
<form name="formSearch" method="post" action="SupplierInfoList.do">
  <div class="search_form">
    <em class="em1">供应商名称：</em>
    <input type="text" name="supplierName" id="supplierName" value="<%=ParamKit.getParameter(request, "supplierName", "") %>" size="20"/>
    <em class="em1">供应商状态：</em>
    <select name="status" id="status">
    	<%
    	int status = ParamKit.getIntParameter(request, "status", Constants.STATUS_VALID);
    	for(StatusBean<Integer, String> statusBean:Constants.STATUS_LIST){ %>
          <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus()==status)?"selected":"" %>><%=statusBean.getValue() %></option>
        <%} %>
    </select>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0" cellpadding="0" class="table_style">
    <tr>
      <th>供应商ID</th>
      <th>供应商名称</th>
      <th>供应商类型</th>
      <th>供应商状态</th>
      <th>有效状态</th>
      <th>操作</th>
    </tr>
    <%
    for(SupplierInfoBean bean:list){%> 
    <tr>
      <td><%=bean.getSupplierId() %></td>
      <td><%=bean.getSupplierName() %></td>
      <td>
					<%
						for (int i = 0; i < supplierTypeTreeMap.get(bean.getSupplierType()).size(); ++i) {
					%>
					<%
						if (i < supplierTypeTreeMap.get(bean.getSupplierType()).size() - 1) {
					%>
					<%=supplierTypeTreeMap.get(bean.getSupplierType()).get(i).getTypeName()%>-
					<%
						} else {
					%> <%=supplierTypeTreeMap.get(bean.getSupplierType()).get(i).getTypeName()%>
					<%
						}
					%> 
					<%
 	                    }
                    %>

	  </td>
      <td><%=MapKit.getValueFromMap(bean.getSupplierStatus(), SupplierInfoConfig.SUPPLIER_STATUS_MAP)%></td>
      <td><%=Constants.STATUS_MAP.get(bean.getStatus())%></td>
      <td align="center">
      	<a href="/srm/SupplierInfoManage.do?supplierId=<%=bean.getSupplierId()%>&readOnly=true">查看</a>
      	<%if (adminUserHelper.hasPermission(SrmResource.SUPPLIER_INFO, OperationConfig.UPDATE)) {%> 
      	| <a href="/srm/SupplierInfoManage.do?supplierId=<%=bean.getSupplierId()%>&readOnly=false">修改</a>
      	<%} %>
      	<%if(adminUserHelper.hasPermission(SrmResource.SUPPLIER_ADDRESS, OperationConfig.SEARCH)) {%>
      	| <a href="/srm/SupplierAddressList.do?supplierId=<%=bean.getSupplierId()%>">查看地址簿</a>
      	<%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
<%if(adminUserHelper.hasPermission(SrmResource.SUPPLIER_INFO, OperationConfig.CREATE)) {%>
  <input type="button" name="create" id="create" value="新建" >
  <%} %>
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
<script type='text/javascript'>
$(document).ready(function() {
	$("#create").click(
		function() {
			$.fancybox.open({
				href : 'SupplierTypeSelect.do?callbackFun=selectSupplierType',
				type : 'iframe',
				width : 560,
				minHeight : 150
		});
	});
});
function selectSupplierType(typeId) {
	var url = "SupplierInfoManage.do?typeId="+ typeId;
	window.location.href= url
}
</script>
</html>