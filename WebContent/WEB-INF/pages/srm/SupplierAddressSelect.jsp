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
	int supplierAddressId = ParamKit.getIntParameter(request, "SupplierAddressId", 0);
	List<SupplierAddressBean> list = (List<SupplierAddressBean>) request.getAttribute("SupplierAddressList");
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改地址</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
<script type="text/javascript">
       document.domain='<%=DOMAIN%>'; 
</script>
</head>
<body class="article_bg">
  <table width="100%" border="0" cellpadding="0" class="table_style">
    <tr>
      <th>选择</th>
      <th>联系人</th>
      <th>联系电话</th>
      <th>地址</th>
    </tr>
    <%
    for(SupplierAddressBean bean:list){%> 
    <tr>
      <td align="center"><input type="radio" name="supplierAddressId" value="<%=bean.getAddressId()%>" <%=supplierAddressId==bean.getAddressId() ? "checked" : ""%>></td>
      <td id="supplierReceiverName_<%=bean.getAddressId()%>"><%=bean.getContactName()%></td>
      <td id="supplierTelephone_<%=bean.getAddressId()%>"><%=bean.getTelephone()%></td>
      <td id="supplierCompleteAddress_<%=bean.getAddressId()%>"><%=bean.getCompleteAddress()%></td>
      <td><input type="hidden" id="supplierAddressJsonStr_<%=bean.getAddressId()%>" value='<%=JsonKit.toJson(bean)%>'></td>
    </tr>	
    <%} %>
  </table>
<ul class="page_info">
<li class="page_left_btn">
  <input type="submit" name="button" id="button" class="modifysubbtn" value="选择" onclick="callbackTo()" />
</li>
</ul>
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

$(document).ready(function() {
	var supplierAddressId = "<%=supplierAddressId%>";
	if(supplierAddressId == -1){
		$("input[name=supplierAddressId]:eq(0)").attr("checked",'checked'); 
	}
	
	
});

</script>
</html>