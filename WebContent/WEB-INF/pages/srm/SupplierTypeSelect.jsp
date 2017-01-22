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
	List<SupplierTypeBean> topSupplierTypeList = (List<SupplierTypeBean>) request.getAttribute("TopSupplierTypeList");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择供应商类型</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
	    <p>
	    		<span class="int_label">供应商类型：</span>  
	    		<input type="hidden" id="currentType" name="currentType" value=""/>
	    		<input type="hidden" id="currentTypeName" name="currentTypeName" value=""/>
	    		<input type="hidden" id="typeIndex" name="typeIndex" value="1"/>  
	      	<select id="index_1" onchange="typeSelectionChange(this)">
	      		<option value="0">请选择类型</option>
	      		<%for(SupplierTypeBean supplierTypeBean : topSupplierTypeList){ %><option value="<%=supplierTypeBean.getTypeId() %>" ><%=supplierTypeBean.getTypeName()%></option>  <%} %>
	      	</select>
	    </p>
	  <div class="div_center">
	  	<input type="submit" name="create" id="create" class="modifysubbtn" value="选择" onclick="callbackTo()" />
	  </div>
</body>
<script>
function typeSelectionChange(obj) {
	var typeId = $(obj).val();
	$("#currentType").val(typeId);
	$("#currentTypeName").val($(obj).find("option:selected").text());
	var index = $(obj).attr("id").substr($(obj).attr("id").indexOf("_") + 1);//获取当前层级数字
	var indexNum = parseInt(index); 
	var newIndex = indexNum + 1;//得到新层级的数字
	if (typeId == 0) {
		//删除后面的层级选择框
		removeSelectBox(newIndex);
	} else if(typeId == -1) {
		//如果当前层级没有选中，则设置上一层级的值
		var parentIndex = indexNum -1;
		$("#currentType").val($("#index_"+parentIndex).val());
		$("#currentTypeName").val($("#index_"+parentIndex).find("option:selected").text());
		//删除后面的层级选择框
		removeSelectBox(newIndex);
	} else {
		var params={  
	        'parentTypeId':typeId  
	    };  
	    $.ajax({
	        type: 'get',
	        url: "/srm/SupplierTypeJsonList.do",
	        data: params,
	        dataType: 'json',
	        success: function(data){
	        		//删除后面的层级选择框
    				removeSelectBox(newIndex);
		      	if(data != null) {
	          		var items=data.result;
		    	    		if (items!=null) {
		    	    			var element = "<select id=index_"+ newIndex + " onchange=typeSelectionChange(this)>";
		    	    			element += "<option value = -1>请选择类型</option>";
		        	 		for(var i =0;i<items.length;i++) {
		          			var item=items[i];
		          			element +="<option value = '"+item.typeId+"'>"+item.typeName+"</option>";
		             	};
		             	element +="</select>";
		             	$(obj).after(element);
		       		} 
		    	    		$("#typeIndex").val(newIndex);
	          	}
		      	
	        },
	        error: function(){
	            return;
	        }
	    });
	}
}

function removeSelectBox(startIndex){
	//删除后面的层级选择框
	var maxIndex = $("#typeIndex").val();
	var maxIndexNumber = parseInt(maxIndex);
	for(var i= startIndex; i<=maxIndexNumber; i++){
		$("#index_"+i).remove();
	}
}

function callbackTo(){
	var typeId = $("#currentType").val();
	var typeName = $("#currentTypeName").val();
	if(typeId == "0"){
		alert("请选择供应商类型");
		return false;
	}else{
		self.parent.<%=callbackFun %>(typeId,typeName);
		try{ self.parent.jQuery.fancybox.close(); }catch(e){console.log(e);}
	}
	
}
</script>
</html>