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
	String callbackFun = ParamKit.getParameter(request, "callbackFun", "callbackFun");
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<SupplierAddressEntityBean> list = (List<SupplierAddressEntityBean>)request.getAttribute("SupplierAddressEntityList");
	int supplierAddressId = ParamKit.getIntParameter(request, "addressId", 0);
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商地址</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
<script type="text/javascript">
       document.domain='<%=DOMAIN%>'; 
</script>
</head>
<body class="article_bg">
<form name="formSearch" method="post" action="SupplierAddressEntitySelect.do">
  <div class="search_form">
    <em class="em1">供应商名称：</em>
    <input type="text" name="supplierName" id="supplierName" value="<%=ParamKit.getParameter(request, "supplierName", "") %>" size="20"/>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0" cellpadding="0" class="table_style">
    <tr>
      <th>选择</th>
      <th>供应商类型</th>
      <th>供应商</th>
      <th>供应商地址</th>
    </tr>
    <%
    for(SupplierAddressEntityBean bean:list){%> 
    <tr>
      <td align="center"><input type="radio" name="supplierAddressId" value="<%=bean.getAddressId()%>" <%=supplierAddressId==bean.getAddressId() ? "checked" : ""%>></td>
      <td id="supplierType_<%=bean.getAddressId()%>"><%=bean.getSupplierTypeName()%></td>
      <td id="supplierName_<%=bean.getAddressId()%>"><%=bean.getSupplierName()%></td>
      <td id="supplierCompleteAddress_<%=bean.getAddressId()%>"><%=bean.getSupplierAddress()%></td>
      <td><input type="hidden" id="supplierAddressJsonStr_<%=bean.getAddressId()%>" value='<%=JsonKit.toJson(bean)%>'></td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<%@ include file="../include/page.jsp" %>
<li class="page_left_btn">
  <input type="submit" name="button" id="button" class="modifysubbtn" value="选择" onclick="callbackTo()" />
</li>
</ul>
<input type="button" class="btn_fixed" value="选择" onclick="callbackTo()" />
</body>
<script>
function callbackTo(){
	var addressId = $('input:radio[name=supplierAddressId]:checked').val();
	if (typeof(addressId) == 'undefined'){
		alert("请选择供应商地址");
		return;
	}
	var supplierAddressJsonStr = $("#supplierAddressJsonStr_"+addressId).val();
	var echoParameter = '<%=ParamKit.getParameter(request, "echoParameter", "")%>';
	self.parent.<%=callbackFun %>(supplierAddressJsonStr,echoParameter);
	try{ self.parent.jQuery.fancybox.close(); }catch(e){console.log(e);}
}


</script>
</html>