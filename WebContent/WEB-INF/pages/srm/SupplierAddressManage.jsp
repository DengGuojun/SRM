<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.srm.bean.*"  %>
<%@ page import="com.lpmas.srm.config.*"  %>
<%@ page import="com.lpmas.region.bean.*"  %>
<% 
	SupplierAddressBean bean = (SupplierAddressBean)request.getAttribute("SupplierAddress");
	SupplierInfoBean supplierInfoBean = (SupplierInfoBean)request.getAttribute("SupplierInfo");
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	String countryName = (String)request.getAttribute("CountryName");
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	boolean isFirstAddress = (boolean)request.getAttribute("isFirstAddress");
	request.setAttribute("readOnly", readOnly);
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>供应商地址管理</title>
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
		<li><%=supplierInfoBean.getSupplierName()%>&nbsp;>&nbsp;</li>
		<li><a href="SupplierAddressList.do?supplierId=<%=supplierInfoBean.getSupplierId()%>">地址簿</a>&nbsp;>&nbsp;</li>
		<% if(bean.getAddressId() > 0) {%>
		<li>修改地址簿</li>
		<%}else{ %>
		<li>新建地址簿</li>
		<%}%>
	</ul>
	</div>
	<form id="formData" name="formData" method="post" action="SupplierAddressManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="supplierId" id="supplierId" value="<%=bean.getSupplierId() %>"/>
	  <input type="hidden" name="addressId" id="addressId" value="<%=bean.getAddressId() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>供应商名称：</em>
	      <input type="text"  name="supplierName" id="supplierName" size="30" maxlength="100" value="<%=supplierInfoBean.getSupplierName() %>" checkStr="供应商名称;txt;true;;100" readOnly/>
	    </p>
	    <p>
	      <em class="int_label">国家:</em>    
	      <input readOnly name="country" id="country" value="<%=countryName%>">
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>省:</em>    
     	  <select id="province" name="province" onchange="$('#region').empty();getCityNameList('province','city','')" checkStr="省;txt;true;;200"></select>
     	  <input type="hidden" id="provinceDummy" value="<%=bean.getProvince()%>"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>市:</em>    
     	  <select id="city" name="city" onchange="getRegionNameList('city','region','')" checkStr="市;txt;true;;200"></select>
     	  <input type="hidden" id="cityDummy" value="<%=bean.getCity()%>"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>区:</em>    
     	  <select id="region" name="region" checkStr="区;txt;true;;200"></select>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>地址:</em>    
     	  <input  type="text" name="address" id="address" size="100" maxlength="200" value="<%=bean.getAddress() %>" checkStr="地址;txt;true;;200"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>联系人:</em>    
     	  <input  type="text" name="contactName" id="contactName" size="100" maxlength="200" value="<%=bean.getContactName() %>" checkStr="联系人;txt;true;;200"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>邮政编码:</em>    
     	  <input  type="text" name="zipCode" id="zipCode" size="10" maxlength="10" onkeyup='this.value=this.value.replace(/[^\-?\d.]/g,"")' value="<%=bean.getZipCode() %>" checkStr="邮政编码;digit;true;;10"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>电话号码:</em>    
     	  <input  type="text" name="telephone" id="telephone" size="50" maxlength="50" onkeyup='this.value=this.value.replace(/[^\-?\d.]/g,"")' value="<%=bean.getTelephone() %>" checkStr="电话号码;digit;true;;50"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>手机号码:</em>    
     	  <input  type="text" name="mobile" id="mobile" size="50" maxlength="50" onkeyup='this.value=this.value.replace(/[^\-?\d.]/g,"")' value="<%=bean.getMobile() %>" checkStr="手机号码;digit;true;;50"/>
	    </p>
	    <p>
	      <em class="int_label">传真:</em>    
     	  <input  type="text" name="fax" id="fax" size="50" maxlength="50" onkeyup='this.value=this.value.replace(/[^\-?\d.]/g,"")' value="<%=bean.getFax() %>" checkStr="传真;digit;false;;50"/>
	    </p>
	    <p>
	      <em class="int_label">是否默认地址:</em>    
     	  <input type="checkbox" name="isDefault" id="isDefault" value="<%=Constants.STATUS_VALID %>" <%=(bean.getIsDefault()==Constants.STATUS_VALID)?"checked":"" %>/>
	      <%if(isFirstAddress){ %>
	      <span>(供应商的第一个地址会自动成为默认地址)</span>
	      <%} %>
	    </p>
	    <p>
	      <em class="int_label">有效状态：</em>
	      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label">备注：</em>
	      <textarea  name="memo" id="memo" cols="60" rows="3" checkStr="备注;txt;false;;1000"><%=bean.getMemo() %></textarea>
	    </p>
	  </div>
	  <div class="div_center">
	  <input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  <input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
</body>
<script>
$(document).ready(function() {
	var url='<%=REGION_URL%>/region/RegionAjaxList.do';
	$.getScript(url,function(data){
		getProvinceNameList('country','province','<%=bean.getProvince()%>');
		getCityNameList('provinceDummy','city','<%=bean.getCity()%>');
		getRegionNameList('cityDummy','region','<%=bean.getRegion()%>');
	});
	var readOnly = '${readOnly}';
	if(readOnly=='true') {
		disablePageElement();
	}
});
</script>
</html>