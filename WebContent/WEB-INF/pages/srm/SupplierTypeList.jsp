<%@page import="com.lpmas.srm.config.SrmResource"%>
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
<%@ page import="com.lpmas.srm.bean.*"  %>
<%
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<SupplierTypeBean> list = (List<SupplierTypeBean>)request.getAttribute("SupplierTypeList");
	List<SupplierTypeBean> parentTreeeList=(List<SupplierTypeBean>)request.getAttribute("ParentTreeList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	int parentTypeId = ParamKit.getIntParameter(request, "parentTypeId", 0);
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商类型列表</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
	<%
		if (parentTypeId > 0) {
	%>
	<div class="article_tit">
		<ul class="art_nav">
			<li><a href="SupplierTypeList.do">供应商类型列表</a>&nbsp;>&nbsp;</li>
			<%for (SupplierTypeBean typeBean : parentTreeeList) {%>
				<li>
				<%if(parentTypeId !=typeBean.getTypeId()) {%>
				<a href="SupplierTypeList.do?parentTypeId=<%=typeBean.getTypeId()%>"><%=typeBean.getTypeName()%></a>&nbsp;>&nbsp;</li>
				<%} else{ %>
				<%=typeBean.getTypeName()%>
			<%	}
			}
			%>
		</ul>
	</div>
	<%
		} else {
	%>
	<p class="article_tit">供应商类型列表</p>
	<%
		}
	%>
<form name="formSearch" method="post" action="SupplierTypeList.do">
  <div class="search_form">
  	<em class="em1">供应商类型名称：</em>
    <input type="text" name="typeName" id="typeName" value="<%=ParamKit.getParameter(request, "typeName", "") %>" size="20"/>
    <em class="em1">供应商类型代码：</em>
    <input type="text" name="typeCode" id="typeCode" value="<%=ParamKit.getParameter(request, "typeCode", "") %>" size="20"/>
    <em class="em1">有效状态：</em>
    <select name="status" id="status">
    	<%
    	int status = ParamKit.getIntParameter(request, "status", Constants.STATUS_VALID);
    	for(StatusBean<Integer, String> statusBean:Constants.STATUS_LIST){ %>
          <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus()==status)?"selected":"" %>><%=statusBean.getValue() %></option>
        <%} %>
    </select>
    <input type="hidden" name="parentTypeId" id="parentTypeId" value="<%=parentTypeId%>" />
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0" cellpadding="0" class="table_style">
    <tr>
      <th>类型名称</th>
      <th>类型代码</th>
      <th>状态</th>
      <th width="20%">操作</th>
    </tr>
    <%
    for(SupplierTypeBean bean:list){%> 
    <tr>
      <td><%=bean.getTypeName() %></td>
      <td><%=bean.getTypeCode() %></td>
      <td><%=Constants.STATUS_MAP.get(bean.getStatus())%></td>
      <td align="center">
      	<a href="/srm/SupplierTypeManage.do?typeId=<%=bean.getTypeId() %>&parentTypeId=<%=parentTypeId %>&readOnly=true">查看</a> |
      	<%if(adminHelper.hasPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.UPDATE)){ %>
      	<a href="/srm/SupplierTypeManage.do?typeId=<%=bean.getTypeId() %>&parentTypeId=<%=parentTypeId %>&readOnly=false">修改</a> | 
      	<%} %>
      	<a href="/srm/SupplierTypeList.do?parentTypeId=<%=bean.getTypeId()%>">查看子类型</a>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminHelper.hasPermission(SrmResource.SUPPLIER_TYPE, OperationConfig.CREATE)){ %>
      <input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='SupplierTypeManage.do?parentTypeId=<%=parentTypeId%>'">
    <%} %>	
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>