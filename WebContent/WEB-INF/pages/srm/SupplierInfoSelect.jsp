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
	int supplierId = (Integer)request.getAttribute("SupplierId");
	List<SupplierInfoEntityBean> list = (List<SupplierInfoEntityBean>) request.getAttribute("SupplierInfoEntityList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加供应商</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
<script type="text/javascript">
        document.domain='<%=DOMAIN%>'; 
</script>
</head>
<body class="article_bg">
<form name="formSearch" method="post" action="SupplierInfoSelect.do">
  <div class="search_form">
    <input type="text" name="queryParam" id="queryParam" value="<%=ParamKit.getParameter(request, "queryParam", "") %>"  placeholder="请输入供应商名称" size="40"/>
    <input type="hidden" name="supplierId" value="<%=supplierId %>" />
    <input type="hidden" name="callbackFun" value="<%=callbackFun %>" />
    <input name="" type="submit" class="search_btn_sub" value="筛选"/>
  </div>
  <table width="100%" border="0" cellpadding="0" class="table_style">
    <tr>
      <th>选择</th>
      <th>供应商编号</th>
      <th>供应商类型</th>
      <th>供应商</th>
    </tr>
    <%
    for(SupplierInfoEntityBean bean:list){%> 
    <tr>
      <td align="center"><input type="radio" name="supplierId" value="<%=bean.getSupplierId()%>" <%=supplierId==bean.getSupplierId() ? "checked" : ""%>></td>
      <td><%=bean.getSupplierId()%></td>
      <td><%=bean.getSupplierTypeName()%></td>
      <td id="supplierName_<%=bean.getSupplierId()%>"><%=bean.getSupplierName()%></td>
    </tr>
    <td><input type="hidden" id="supplierInfoJsonStr_<%=bean.getSupplierId()%>" value='<%=JsonKit.toJson(bean)%>'></td>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  <input type="submit" name="button" id="button" class="modifysubbtn" value="选择" onclick="callbackTo()" />
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
<script>
function callbackTo(){
	var supplierId = $('input:radio[name=supplierId]:checked').val();
	if (typeof(supplierId) == 'undefined'){
		alert("请选择供应商");
		return;
	}
	var supplierInfoJsonStr = $("#supplierInfoJsonStr_"+supplierId).val();
	var echoParameter = '<%=ParamKit.getParameter(request, "echoParameter", "")%>';
	self.parent.<%=callbackFun%>(supplierInfoJsonStr,echoParameter);
	try{ self.parent.jQuery.fancybox.close(); }catch(e){console.log(e);}
}
</script>
</html>