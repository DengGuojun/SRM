<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.srm.bean.*"  %>
<%@ page import="com.lpmas.srm.config.*"  %>
<%@ page import="com.lpmas.srm.business.*"  %>
<%
SupplierInfoBean bean = (SupplierInfoBean)request.getAttribute("SupplierInfo");
String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
request.setAttribute("readOnly", readOnly);
List<SupplierPropertyTypeBean> supplierPropertyTypeList = (List<SupplierPropertyTypeBean>) request.getAttribute("supplierPropertyTypeList");
List<SupplierPropertyBean> supplierPropertyList = (List<SupplierPropertyBean>) request.getAttribute("propertyList");
Map<String, SupplierPropertyBean> supplierPropertyMap = (Map<String, SupplierPropertyBean>) request.getAttribute("propertyTypeMap");
Map<String,SupplierPropertyTypeBean> subPropertyTypeMap = (Map<String,SupplierPropertyTypeBean>)request.getAttribute("subPropertyTypeMap");
Integer categoryId = (Integer)request.getAttribute("categoryId");
List<Map.Entry<Integer, SupplierPropertyCategoryBean>> propertyCategoryList = (List<Map.Entry<Integer, SupplierPropertyCategoryBean>>)request.getAttribute("propertyCategoryList");
SupplierPropertyCategoryBusiness supplierPropertyCategoryBusiness = new SupplierPropertyCategoryBusiness();
Map<Integer, Boolean> count = new HashMap<Integer, Boolean>();
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
	<script type="text/javascript" src="<%=STATIC_URL %>/js/ui.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a>
		<ul class="art_nav">
			<li><a href="SupplierInfoList.do">供应商列表</a>&nbsp;>&nbsp;</li>			
			<li><%=bean.getSupplierName()%>&nbsp;>&nbsp;</li>
			<li>修改供应商信息</li>		
		</ul>
	</div>
    <div class="article_tit">
		<p class="tab">
			<a href="SupplierInfoManage.do?supplierId=<%=bean.getSupplierId()%>&readOnly=<%=readOnly%>">基本信息</a>
			<% for (Map.Entry<Integer, SupplierPropertyCategoryBean> mapping:propertyCategoryList) { %>
			<a href="SupplierPropertyManage.do?supplierId=<%=bean.getSupplierId()%>&categoryId=<%=mapping.getKey() %>&readOnly=<%=readOnly%>"><%=mapping.getValue().getCategoryName()%></a>
			<% }%>
		</p>
		<script>
		tabChangeForProperty('.tab', 'a');
		</script>
	</div>
	<form id="formData" name="formData" method="post" action="SupplierPropertyManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="supplierId" id="supplierId" value="<%=bean.getSupplierId()%>"/>
	  <input type="hidden" name="categoryId" id="categoryId" value="<%=categoryId%>"/>
	  <div>
			<%
		if( supplierPropertyTypeList.size()>0){
			%>
			<div class="modify_form">
			<%for(SupplierPropertyTypeBean typeBean : supplierPropertyTypeList) {%>
				         <%if(!count.containsKey(typeBean.getCategoryId())){ %>
			<p><%=supplierPropertyCategoryBusiness.getSupplierPropertyCategoryByKey(typeBean.getCategoryId()).getCategoryName() %></p>
			<% count.put(typeBean.getCategoryId(), true); %>
			<% } %>
				<%if(typeBean.getParentPropertyId()==0){%>
				<p>
     				<%if(typeBean.getIsRequired()==Constants.STATUS_VALID){ %>
						<em class="int_label"><span>*</span><%=typeBean.getPropertyName()%>
						:
					</em>
					<%}else{ %>
					<em class="int_label"> <%=typeBean.getPropertyName()%>
						:
					</em>
					<%} %>
					<%out.print(PropertyDisplayUtil.displayPropertyInput(typeBean, supplierPropertyMap.get(typeBean.getPropertyCode()),false)); 
					if(subPropertyTypeMap.get(typeBean.getPropertyCode()) != null ){ 
							out.print(PropertyDisplayUtil.displayPropertyInput(subPropertyTypeMap.get(typeBean.getPropertyCode()), supplierPropertyMap.get(typeBean.getPropertyCode()),true));
						}%>
				</p>
					
		
			<%} %>
			<% }%>
			</div>
			<%} %>
			<div class="div_center">
	  			<input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  			<a href="SupplierInfoList.do"><input type="button" name="cancel" id="cancel" value="取消" ></a>
	 		</div>
		
	  </div>
	</form>
</body>
<script>
$(document).ready(function() {
	var readonly = '${readOnly}';
	if(readonly=='true')
	{
		disablePageElement();
	}
});
</script>
</html>